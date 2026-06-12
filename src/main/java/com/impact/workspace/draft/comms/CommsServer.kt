package com.impact.workspace.draft.comms

import com.impact.workspace.draft.comms.common.CommsConnectionPath
import com.impact.workspace.draft.comms.common.CommsVisibleMachine
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.satellite.CommsSatelliteEndpoint
import com.impact.workspace.draft.comms.satellite.CommsSatelliteRegistry
import java.util.UUID

object CommsServer {

    private val satellites = CommsSatelliteRegistry()
    private val worlds = LinkedHashMap<CommsWorldKey, CommsWorldNetwork>()

    private var running = false

    fun serverAboutToStart() {
        worlds.values.forEach { it.shutdown() }
        satellites.clear()
        worlds.clear()
        running = false
    }

    fun serverStarting() {
        running = true
    }

    fun serverStopping() {
        worlds.values.forEach { it.shutdown() }
        worlds.clear()
        satellites.clear()
        running = false
    }

    fun worldLoad(world: CommsWorldKey) {
        getWorld(world)
    }

    fun worldUnload(world: CommsWorldKey) {
        worlds.remove(world)?.shutdown()
    }

    fun nodeLoad(node: CommsGroundNode) {
        getWorld(node.commsWorld).register(node)
    }

    fun nodeUnload(node: CommsGroundNode) {
        getWorld(node.commsWorld).unload(node.commsId)
    }

    fun nodeRemove(node: CommsGroundNode) {
        getWorld(node.commsWorld).remove(node.commsId)
    }

    fun satelliteLoad(satellite: CommsSatelliteEndpoint) {
        check(running) { "CommsServer is not running" }

        satellites.register(satellite)
        markAllWorldsDirty()
    }

    fun satelliteUnload(id: UUID) {
        check(running) { "CommsServer is not running" }

        satellites.unload(id)
        markAllWorldsDirty()
    }

    fun satelliteRemove(id: UUID) {
        check(running) { "CommsServer is not running" }

        satellites.remove(id)
        markAllWorldsDirty()
    }

    fun tick() {
        if (!running) return

        if (satellites.consumeDirty()) {
            markAllWorldsDirty()
        }

        worlds.values.forEach { it.tick() }
    }

    fun hasConnection(
        world: CommsWorldKey,
        computerId: UUID,
        executorId: UUID
    ): Boolean {
        return getWorld(world).hasConnection(computerId, executorId)
    }

    fun findConnectionPathFromExecutor(
        world: CommsWorldKey,
        executorId: UUID,
    ): CommsConnectionPath? {
        return getWorld(world).findConnectionPathFromExecutor(
            executorId = executorId
        )
    }

    fun getWorld(world: CommsWorldKey): CommsWorldNetwork {
        return worlds.getOrPut(world) {
            CommsWorldNetwork(world, satellites)
        }
    }

    private fun markAllWorldsDirty() {
        worlds.values.forEach { it.markDirty() }
    }

    fun getMachinesInTowerRadius(
        world: CommsWorldKey,
        playerId: UUID,
        towerId: UUID,
    ): List<CommsVisibleMachine> {
        return getWorld(world).getMachinesInTowerRadius(
            towerId = towerId,
            playerId = playerId
        )
    }
}
