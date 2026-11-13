package com.impact.addon.gt.api.parallel_system

import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/** Registration and updates run on the server thread without loading chunks. */
public object SatelliteNetworkLogic {

    private val towers: MutableSet<INetworkTower> = HashSet()
    private val coveringTowers: MutableMap<INetworkMachine, MutableSet<INetworkTower>> = HashMap()
    private val dirtyTowers: MutableSet<INetworkTower> = LinkedHashSet()
    private val machinesByChunk: MutableMap<World, MutableMap<Long, MutableSet<INetworkMachine>>> = HashMap()

    public fun onStartServer() {
        onStopServer()
    }

    public fun onStopServer() {
        for (chunks in machinesByChunk.values) {
            for (machines in chunks.values) {
                for (machine in machines) {
                    machine.isSatelliteConnected = false
                }
            }
        }
        for (tower in towers) {
            tower.isSatelliteConnected = false
            tower.replaceConnections(emptySet())
        }
        towers.clear()
        dirtyTowers.clear()
        machinesByChunk.clear()
        coveringTowers.clear()
    }

    public fun registerMachine(machine: INetworkMachine) {
        if (machine is INetworkTower) {
            createTower(machine)
            return
        }
        val tile = tileOf(machine) ?: return
        if (!NetworkNodeAccess.isLoaded(machine) || !machine.hasShowConnect()) {
            machine.isSatelliteConnected = false
            return
        }
        val chunks = machinesByChunk.getOrPut(
            key = tile.worldObj,
        ) {
            HashMap()
        }
        val machines = chunks.getOrPut(
            key = chunkKey(
                x = tile.xCoord shr 4,
                z = tile.zCoord shr 4,
            ),
        ) {
            HashSet()
        }
        // A replacement at the same address must not retain the old instance.
        for (previous in machines.toList()) {
            val previousTile = tileOf(previous)
            if (previous !== machine && previousTile != null && previousTile.xCoord == tile.xCoord &&
                previousTile.yCoord == tile.yCoord && previousTile.zCoord == tile.zCoord
            ) {
                machines.remove(previous)
                detachMachine(previous)
            }
        }
        if (machines.add(machine)) {
            markNearbyTowers(tile)
        }
    }

    public fun removeMachine(machine: INetworkMachine) {
        if (machine is INetworkTower) {
            removeTower(machine)
            return
        }
        val tile = tileOf(machine)
        if (tile != null) {
            val chunks = machinesByChunk[tile.worldObj]
            val key = chunkKey(
                x = tile.xCoord shr 4,
                z = tile.zCoord shr 4,
            )
            val machines = chunks?.get(key)
            machines?.remove(machine)
            if (machines?.isEmpty() == true) {
                chunks.remove(key)
            }
            if (chunks?.isEmpty() == true) {
                machinesByChunk.remove(tile.worldObj)
            }
        }
        detachMachine(machine)
    }

    public fun reloadNetwork() {
        dirtyTowers.addAll(towers)
    }

    public fun onServerTick() {
        val updates = dirtyTowers.toList()
        dirtyTowers.clear()
        for (tower in updates) {
            if (tower in towers) {
                try {
                    tower.onUpdateConnections()
                } catch (failure: RuntimeException) {
                    NetworkNodeLifecycle.reportFailure(
                        tower,
                        failure,
                    )
                }
            }
        }
    }

    public fun createTower(tower: INetworkTower) {
        if (NetworkNodeAccess.isLoaded(tower) && towers.add(tower)) {
            dirtyTowers.add(tower)
        }
    }

    public fun removeTower(tower: INetworkTower) {
        towers.remove(tower)
        dirtyTowers.remove(tower)
        tower.isSatelliteConnected = false
        replaceCoverage(
            tower = tower,
            machines = emptySet(),
        )
    }

    public fun refreshTower(tower: INetworkTower) {
        val machines = HashSet<INetworkMachine>()
        val tile = tileOf(tower)
        val chunks = if (tile != null) machinesByChunk[tile.worldObj] else null
        if (tile != null && chunks != null) {
            for (x in -8..8) {
                for (z in -8..8) {
                    val key = chunkKey(
                        x = (tile.xCoord shr 4) + x,
                        z = (tile.zCoord shr 4) + z,
                    )
                    val candidates = chunks[key] ?: continue
                    for (machine in candidates) {
                        if (machine.hasShowConnect() && NetworkNodeAccess.isLoaded(machine)) {
                            machines.add(machine)
                        }
                    }
                }
            }
        }
        replaceCoverage(
            tower = tower,
            machines = machines,
        )
    }

    private fun replaceCoverage(
        tower: INetworkTower,
        machines: Set<INetworkMachine>,
    ) {
        val affected = HashSet(tower.connections)
        for (machine in affected) {
            val sources = coveringTowers[machine] ?: continue
            sources.remove(tower)
            if (sources.isEmpty()) {
                coveringTowers.remove(machine)
            }
        }
        tower.replaceConnections(machines)
        for (machine in machines) {
            coveringTowers.getOrPut(key = machine) {
                HashSet()
            }.add(tower)
        }
        affected.addAll(machines)
        for (machine in affected) {
            refreshMachineStatus(machine)
        }
    }

    private fun detachMachine(machine: INetworkMachine) {
        val sources = coveringTowers.remove(machine) ?: emptySet()
        for (tower in sources) {
            val remaining = HashSet(tower.connections)
            remaining.remove(machine)
            tower.replaceConnections(remaining)
        }
        machine.isSatelliteConnected = false
    }

    public fun getMachineStatus(machine: INetworkMachine): NetworkStatus {
        val sources = coveringTowers[machine]
        if (sources.isNullOrEmpty()) {
            return NetworkStatus.NO_COVERAGE
        }
        var reason = NetworkStatus.TOWER_OFFLINE
        for (tower in sources) {
            if (!NetworkNodeAccess.isLoaded(tower)) {
                continue
            }
            if (tower.isSatelliteConnected) {
                return NetworkStatus.CONNECTED
            }
            if (tower is IHandledNetworkNode) {
                val towerStatus = NetworkNodeLifecycle.getStatus(tower)
                when (towerStatus) {
                    NetworkStatus.LEGACY_SATELLITE_UNBOUND -> {
                        reason = towerStatus
                    }
                    NetworkStatus.SATELLITE_UNBOUND,
                    NetworkStatus.SATELLITE_UNLOADED,
                    NetworkStatus.SATELLITE_MISSING,
                    NetworkStatus.SATELLITE_TRANSMITTER_MISSING,
                    NetworkStatus.SATELLITE_OFFLINE,
                    -> {
                        if (reason == NetworkStatus.TOWER_OFFLINE || towerStatus.ordinal < reason.ordinal &&
                            reason != NetworkStatus.LEGACY_SATELLITE_UNBOUND
                        ) {
                            reason = towerStatus
                        }
                    }
                    else -> {
                    }
                }
            }
        }
        return reason
    }

    public fun updateStatusConnections(tower: INetworkTower) {
        for (machine in tower.connections) {
            refreshMachineStatus(machine)
        }
    }

    private fun refreshMachineStatus(machine: INetworkMachine) {
        val connected = getMachineStatus(machine) == NetworkStatus.CONNECTED
        NetworkNodeLifecycle.runFor(machine) {
            if (machine.isSatelliteConnected != connected) {
                machine.isSatelliteConnected = connected
            }
        }
    }

    private fun markNearbyTowers(tile: TileEntity) {
        for (tower in towers) {
            val towerTile = tileOf(tower) ?: continue
            if (towerTile.worldObj === tile.worldObj &&
                kotlin.math.abs((towerTile.xCoord shr 4) - (tile.xCoord shr 4)) <= 8 &&
                kotlin.math.abs((towerTile.zCoord shr 4) - (tile.zCoord shr 4)) <= 8
            ) {
                dirtyTowers.add(tower)
            }
        }
    }

    private fun tileOf(machine: INetworkMachine): TileEntity? {
        return if (machine is IMetaTileEntity) {
            machine.baseMetaTileEntity as? TileEntity
        } else {
            machine as? TileEntity
        }
    }

    private fun chunkKey(
        x: Int,
        z: Int,
    ): Long {
        return (x.toLong() shl 32) xor (z.toLong() and 0xffffffffL)
    }
}
