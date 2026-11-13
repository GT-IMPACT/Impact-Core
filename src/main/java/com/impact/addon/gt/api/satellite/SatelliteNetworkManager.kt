package com.impact.addon.gt.api.satellite

import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess
import com.impact.addon.gt.api.position.IPosition

public object SatelliteNetworkManager {

    private var revision: Long = 0

    public fun getRevision(): Long {
        return revision
    }

    public fun markChanged() {
        revision++
    }

    private val satellitesByAddress: MutableMap<List<Int>, ISatellite> = HashMap()

    public fun reload() {
        val satellites = satellitesByAddress.values.toList()
        satellitesByAddress.clear()
        markChanged()
        for (satellite in satellites) {
            satellite.updateConnectionStatus(false)
            for (receiver in satellite.getConnections().toList()) {
                receiver.onDisconnect()
            }
        }
    }

    public fun addSatelliteToWorld(satellite: ISatellite): Boolean {
        if (!NetworkNodeAccess.isLoaded(satellite)) {
            return false
        }
        val key = address(satellite.getPosition())
        val previous = satellitesByAddress.put(
            key,
            satellite,
        )
        if (previous != null && previous !== satellite) {
            previous.updateConnectionStatus(false)
            for (receiver in previous.getConnections().toList()) {
                receiver.onDisconnect()
            }
        }
        if (previous !== satellite) {
            markChanged()
        }
        return previous !== satellite
    }

    public fun removeSatelliteFromWorld(satellite: ISatellite): Boolean {
        val key = address(satellite.getPosition())
        if (satellitesByAddress[key] !== satellite) {
            return false
        }
        satellitesByAddress.remove(key)
        markChanged()
        return true
    }

    public fun getSatelliteInWorld(pos: IPosition): ISatellite? {
        val satellite = satellitesByAddress[address(pos)] ?: return null
        return if (NetworkNodeAccess.isLoaded(satellite)) {
            satellite
        } else {
            null
        }
    }

    private fun address(pos: IPosition): List<Int> {
        return listOf(
            pos.d,
            pos.x,
            pos.y,
            pos.z,
        )
    }
}
