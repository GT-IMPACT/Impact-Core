package com.impact.addon.gt.api.satellite

import com.impact.addon.gt.api.position.IPosition

object SatelliteNetworkManager {

    private val SATELLITE_LIST: HashSet<ISatellite> = HashSet()

    fun reload() {
        SATELLITE_LIST.clear()
    }

    fun addSatelliteToWorld(satellite: ISatellite): Boolean {
        return SATELLITE_LIST.add(satellite)
    }

    fun removeSatelliteFromWorld(satellite: ISatellite): Boolean {
        return SATELLITE_LIST.remove(satellite)
    }

    fun getSatelliteInWorld(pos: IPosition): ISatellite? {
        for (iSatellite in SATELLITE_LIST) {
            if (pos.isEquals(iSatellite.getPosition())) {
                return iSatellite
            }
        }
        return null
    }
}