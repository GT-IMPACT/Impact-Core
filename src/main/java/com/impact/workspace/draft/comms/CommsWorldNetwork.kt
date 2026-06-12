package com.impact.workspace.draft.comms

import com.impact.workspace.draft.comms.common.CommsConnectionPath
import com.impact.workspace.draft.comms.common.CommsLink
import com.impact.workspace.draft.comms.common.CommsLinkType
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.common.CommsPathPoint
import com.impact.workspace.draft.comms.common.CommsPathPointType
import com.impact.workspace.draft.comms.common.CommsVisibleComputerForLink
import com.impact.workspace.draft.comms.common.CommsVisibleMachine
import com.impact.workspace.draft.comms.common.CommsVisibleTower
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.node.CommsOwnedNode
import com.impact.workspace.draft.comms.node.types.CommsComputerNode
import com.impact.workspace.draft.comms.node.types.CommsExecutorNode
import com.impact.workspace.draft.comms.node.types.CommsTowerNode
import com.impact.workspace.draft.comms.satellite.CommsSatelliteEndpoint
import com.impact.workspace.draft.comms.satellite.CommsSatelliteRegistry
import com.impact.workspace.draft.security.SecurityApi
import com.impact.workspace.draft.security.common.SecurityAction
import scala.collection.parallel.`ThreadPoolTasks$class`.executor

import java.util.UUID

class CommsWorldNetwork(
    val world: CommsWorldKey,
    private val satellites: CommsSatelliteRegistry,
) {
    private val towers = LinkedHashMap<UUID, CommsTowerNode>()
    private val computers = LinkedHashMap<UUID, CommsComputerNode>()
    private val executors = LinkedHashMap<UUID, CommsExecutorNode>()

    private val links = LinkedHashSet<CommsLink>()

    private var active = true
    private var dirty = false

    fun register(node: CommsGroundNode) {
        check(active) { "CommsWorldNetwork is inactive: $world" }

        if (node.commsWorld != world) {
            error("Node world ${node.commsWorld} does not match network world $world")
        }

        if (node.commsRemoved) return

        node.commsLoaded = true

        when (node) {
            is CommsTowerNode -> towers[node.commsId] = node
            is CommsComputerNode -> computers[node.commsId] = node
            is CommsExecutorNode -> executors[node.commsId] = node
            else -> error("Unknown ground comms node: $node")
        }

        dirty = true
    }

    fun unload(id: UUID) {
        val node = findGroundNode(id) ?: return

        node.commsLoaded = false
        links.removeIf { it.has(id) }

        dirty = true
    }

    fun remove(id: UUID) {
        val node = findGroundNode(id) ?: return

        node.commsLoaded = false
        node.commsRemoved = true

        towers.remove(id)
        computers.remove(id)
        executors.remove(id)

        links.removeIf { it.has(id) }

        dirty = true
    }

    fun markDirty() {
        dirty = true
    }

    fun tick() {
        if (!active) return
        if (!dirty) return

        rebuild()
        dirty = false
    }

    fun hasConnection(
        computerId: UUID,
        executorId: UUID
    ): Boolean {
        tick()

        val computer = computers[computerId] ?: return false
        val executor = executors[executorId] ?: return false

        if (!computer.isCommsAlive()) return false
        if (!executor.isCommsAlive()) return false

        val computerTowerIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_COMPUTER }
            .filter { it.to == computerId }
            .map { it.from }
            .toSet()

        if (computerTowerIds.isEmpty()) return false

        val executorTowerIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_EXECUTOR }
            .filter { it.to == executorId }
            .map { it.from }
            .toSet()

        if (executorTowerIds.isEmpty()) return false

        val computerSatelliteIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_SATELLITE }
            .filter { it.from in computerTowerIds }
            .map { it.to }
            .toSet()

        if (computerSatelliteIds.isEmpty()) return false

        val executorSatelliteIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_SATELLITE }
            .filter { it.from in executorTowerIds }
            .map { it.to }
            .toSet()

        return computerSatelliteIds.any { it in executorSatelliteIds }
    }

    fun findConnectionPathFromExecutor(
        executorId: UUID
    ): CommsConnectionPath? {
        tick()

        val executor = executors[executorId] ?: return null
        if (!executor.isCommsOnline()) return null

        val computerId = executor.boundComputerId ?: return null

        return findConnectionPath(
            computerId = computerId,
            executorId = executorId
        )
    }

    fun findConnectionPath(
        computerId: UUID,
        executorId: UUID
    ): CommsConnectionPath? {
        tick()

        val computer = computers[computerId] ?: return null
        val executor = executors[executorId] ?: return null

        if (!computer.isCommsOnline()) return null
        if (!executor.isCommsOnline()) return null

        val computerTowerIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_COMPUTER }
            .filter { it.to == computerId }
            .map { it.from }
            .toList()

        val executorTowerIds = links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_EXECUTOR }
            .filter { it.to == executorId }
            .map { it.from }
            .toList()

        for (executorTowerId in executorTowerIds) {
            val executorTower = towers[executorTowerId] ?: continue
            if (!executorTower.isCommsOnline()) continue

            val executorTowerSatellites = satellitesOfTower(executorTowerId)

            for (computerTowerId in computerTowerIds) {
                val computerTower = towers[computerTowerId] ?: continue
                if (!computerTower.isCommsOnline()) continue

                val computerTowerSatellites = satellitesOfTower(computerTowerId)

                val sharedSatelliteId = executorTowerSatellites
                    .firstOrNull { it in computerTowerSatellites }
                    ?: continue

                val satellite = satellites.get(sharedSatelliteId)
                if (satellite == null || !satellite.isCommsOnline()) continue

                return CommsConnectionPath(
                    executor = executor.toPathPoint(),
                    executorTower = executorTower.toPathPoint(),
                    satellite = satellite.toPathPoint(),
                    computerTower = computerTower.toPathPoint(),
                    computer = computer.toPathPoint()
                )
            }
        }

        return null
    }

    private fun satellitesOfTower(towerId: UUID): Set<UUID> {
        return links
            .asSequence()
            .filter { it.type == CommsLinkType.TOWER_TO_SATELLITE }
            .filter { it.from == towerId }
            .map { it.to }
            .toSet()
    }

    private fun CommsComputerNode.toPathPoint(): CommsPathPoint {
        return CommsPathPoint(
            id = commsId,
            type = CommsPathPointType.COMPUTER,
            world = commsWorld,
            pos = commsPos
        )
    }

    private fun CommsExecutorNode.toPathPoint(): CommsPathPoint {
        return CommsPathPoint(
            id = commsId,
            type = CommsPathPointType.EXECUTOR,
            world = commsWorld,
            pos = commsPos
        )
    }

    private fun CommsTowerNode.toPathPoint(): CommsPathPoint {
        return CommsPathPoint(
            id = commsId,
            type = CommsPathPointType.TOWER,
            world = commsWorld,
            pos = commsPos
        )
    }

    private fun CommsSatelliteEndpoint.toPathPoint(): CommsPathPoint {
        return CommsPathPoint(
            id = commsId,
            type = CommsPathPointType.SATELLITE,
            world = null,
            pos = null
        )
    }

    fun snapshotLinks(): Set<CommsLink> {
        tick()
        return links.toSet()
    }

    fun clear() {
        towers.clear()
        computers.clear()
        executors.clear()
        links.clear()
        dirty = false
    }

    fun shutdown() {
        clear()
        active = false
    }

    private fun rebuild() {
        links.clear()

        purgeDeadGroundNodes()

        val aliveSatellites = satellites.aliveSatellites()

        for (tower in towers.values) {

            tower.commsNetworkState = CommsNetworkState.CONNECTING

            for (satellite in aliveSatellites) {
                if (!satellite.canAcceptCommsTower(tower)) continue
                if (!canLink(tower, satellite)) continue

                links += CommsLink(
                    from = tower.commsId,
                    to = satellite.commsId,
                    type = CommsLinkType.TOWER_TO_SATELLITE
                )
            }

            for (computer in computers.values) {

                if (!canLink(tower, computer)) continue

                computer.commsNetworkState = CommsNetworkState.CONNECTING

                if (!tower.canReachCommsNode(computer)) continue

                links += CommsLink(
                    from = tower.commsId,
                    to = computer.commsId,
                    type = CommsLinkType.TOWER_TO_COMPUTER
                )
            }

            for (executor in executors.values) {

                if (!canLink(tower, executor)) continue

                executor.commsNetworkState = CommsNetworkState.CONNECTING

                if (!tower.canReachCommsNode(executor)) continue

                links += CommsLink(
                    from = tower.commsId,
                    to = executor.commsId,
                    type = CommsLinkType.TOWER_TO_EXECUTOR
                )
            }

            if (!tower.isCommsOnline()) {
                tower.commsNetworkState = CommsNetworkState.DISCONNECTED
                continue
            }

            tower.commsNetworkState = CommsNetworkState.CONNECTED
        }

        for (computer in computers.values) {
            if (links.any { it.type == CommsLinkType.TOWER_TO_COMPUTER && it.to == computer.commsId }) {
                computer.commsNetworkState = CommsNetworkState.CONNECTED
            } else {
                computer.commsNetworkState = CommsNetworkState.DISCONNECTED
            }
        }

        for (executor in executors.values) {
            if (links.any { it.type == CommsLinkType.TOWER_TO_EXECUTOR && it.to == executor.commsId }) {
                executor.commsNetworkState = CommsNetworkState.CONNECTED
            } else {
                executor.commsNetworkState = CommsNetworkState.DISCONNECTED
            }
        }
    }

    private fun findGroundNode(id: UUID): CommsGroundNode? {
        return towers[id] ?: computers[id] ?: executors[id]
    }

    private fun purgeDeadGroundNodes() {
        towers.entries.removeIf { !it.value.isCommsAlive() }
        computers.entries.removeIf { !it.value.isCommsAlive() }
        executors.entries.removeIf { !it.value.isCommsAlive() }

        links.removeIf { link ->
            val fromAlive = findGroundNode(link.from)?.isCommsAlive() == true
            val toAlive =
                findGroundNode(link.to)?.isCommsAlive() == true ||
                        satellites.get(link.to)?.isCommsAlive() == true

            !fromAlive || !toAlive
        }
    }

    fun getMachinesInTowerRadius(towerId: UUID, playerId: UUID): List<CommsVisibleMachine> {
        tick()

        val tower = towers[towerId] ?: return emptyList()
        if (!tower.isCommsAlive()) return emptyList()
        if (!SecurityApi.can(player1 = playerId, player2 = tower.commsOwnerId)) return emptyList()

        val result = ArrayList<CommsVisibleMachine>()

        for (computer in computers.values) {
            if (!tower.canReachCommsNode(computer)) continue
            if (!SecurityApi.can(player1 = playerId, player2 = computer.commsOwnerId)) continue

            result += CommsVisibleMachine(
                id = computer.commsId,
                type = computer.commsType,
                pos = computer.commsPos,
                distanceSquared = tower.commsPos.distanceSquaredTo(computer.commsPos),
                connected = links.any {
                    it.type == CommsLinkType.TOWER_TO_COMPUTER &&
                            it.from == tower.commsId &&
                            it.to == computer.commsId
                },
                dx = computer.commsPos.x - tower.commsPos.x,
                dz = computer.commsPos.z - tower.commsPos.z,
                dy = computer.commsPos.y - tower.commsPos.y,
                displayName = computer.unlocalizedName,
            )
        }

        for (executor in executors.values) {
            if (!tower.canReachCommsNode(executor)) continue
            if (!SecurityApi.can(player1 = playerId, player2 = executor.commsOwnerId)) continue

            result += CommsVisibleMachine(
                id = executor.commsId,
                type = executor.commsType,
                pos = executor.commsPos,
                distanceSquared = tower.commsPos.distanceSquaredTo(executor.commsPos),
                connected = links.any {
                    it.type == CommsLinkType.TOWER_TO_EXECUTOR &&
                            it.from == tower.commsId &&
                            it.to == executor.commsId
                },
                dx = executor.commsPos.x - tower.commsPos.x,
                dz = executor.commsPos.z - tower.commsPos.z,
                dy = executor.commsPos.y - tower.commsPos.y,
                displayName = executor.unlocalizedName,
            )
        }

        return result.sortedWith(
            compareBy<CommsVisibleMachine> { it.type.ordinal }
                .thenBy { it.distanceSquared }
        )
    }

    fun visibleTowersFor(
        playerId: UUID
    ): List<CommsVisibleTower> {
        tick()

        return towers.values.mapNotNull { tower ->

            if (!tower.isCommsAlive()) return@mapNotNull null
            if (!SecurityApi.can(player1 = playerId, player2 = tower.commsOwnerId)) return@mapNotNull null

            CommsVisibleTower(
                id = tower.commsId,
                pos = tower.commsPos,
                active = tower.isCommsOnline(),
                connectedMachines = countTowerMachines(tower.commsId),
            )
        }
    }

    private fun countTowerMachines(towerId: UUID): Int {
        return links.count {
            it.from == towerId && (it.type == CommsLinkType.TOWER_TO_COMPUTER || it.type == CommsLinkType.TOWER_TO_EXECUTOR)
        }
    }

    private fun canLink(
        a: CommsOwnedNode,
        b: CommsOwnedNode,
    ): Boolean {
        return SecurityApi.can(
            player1 = a.commsOwnerId,
            player2 = b.commsOwnerId,
            action = SecurityAction.CONNECT,
        )
    }

    fun visibleComputersForExecutor(
        playerId: UUID,
        executorId: UUID
    ): List<CommsVisibleComputerForLink> {
        val executor = executors[executorId] ?: return emptyList()
        if (!SecurityApi.can(player1 = playerId, player2 = executor.commsOwnerId)) return emptyList()

        return computers.values
            .mapNotNull { computer ->

                if (!SecurityApi.can(player1 = playerId, player2 = computer.commsOwnerId)) return@mapNotNull null

                val connection = findConnectionPath(computerId = computer.commsId, executorId = executor.commsId)

                CommsVisibleComputerForLink(
                    id = computer.commsId,
                    pos = computer.commsPos,
                    active = computer.isCommsOnline(),
                    linked = executor.boundComputerId == computer.commsId,
                    routeAvailable = connection != null,
                    distanceSquared = executor.commsPos.distanceSquaredTo(computer.commsPos),
                )
            }
            .sortedWith(
                compareByDescending<CommsVisibleComputerForLink> { it.linked }
                    .thenByDescending { it.routeAvailable }
                    .thenByDescending { it.active }
                    .thenBy { it.distanceSquared }
            )
    }
}
