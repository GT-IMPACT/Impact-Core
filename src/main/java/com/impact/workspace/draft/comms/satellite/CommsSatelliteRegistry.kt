package com.impact.workspace.draft.comms.satellite

import com.impact.impact
import com.impact.workspace.draft.comms.common.CommsNetworkState
import java.util.UUID

class CommsSatelliteRegistry {

    private val satellites = LinkedHashMap<UUID, CommsSatelliteEndpoint>()

    private var dirty = false

    fun register(satellite: CommsSatelliteEndpoint) {
        if (satellite.commsRemoved) return

        satellite.commsOnline = true
        satellite.commsNetworkState = CommsNetworkState.CONNECTED
        satellites[satellite.commsId] = satellite
        dirty = true
    }

    fun unload(id: UUID) {
        val satellite = satellites[id] ?: return

        satellite.commsOnline = false
        satellite.commsNetworkState = CommsNetworkState.DISCONNECTED
        dirty = true
    }

    fun remove(id: UUID) {
        val satellite = satellites[id] ?: return

        satellite.commsOnline = false
        satellite.commsRemoved = true
        satellite.commsNetworkState = CommsNetworkState.DISCONNECTED
        satellites.remove(id)
        dirty = true
    }

    fun get(id: UUID): CommsSatelliteEndpoint? {
        return satellites[id]
    }

    fun aliveSatellites(): List<CommsSatelliteEndpoint> {
        satellites.entries.removeIf { !it.value.isCommsAlive() }
        return satellites.values.toList()
    }

    fun consumeDirty(): Boolean {
        val result = dirty
        dirty = false
        return result
    }

    fun clear() {
        satellites.clear()
        dirty = false
    }
}