package com.impact.common.managers

import com.impact.addon.gt.api.aerostat.IAeroStat
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.world.World

public object AeroStateNetworkManager {

    private val stationsById: MutableMap<String, IAeroStat> = LinkedHashMap()

    @JvmStatic
    public fun onWorldTick(event: TickEvent.WorldTickEvent) {
        if (event.world.isRemote || event.phase != TickEvent.Phase.END) {
            return
        }
        if (event.world.totalWorldTime % 20L == 0L) {
            updateNetwork(event.world)
        }
    }

    @JvmStatic
    public fun updateNetwork(world: World) {
        if (world.isRemote) {
            return
        }
        val iterator = stationsById.values.iterator()
        while (iterator.hasNext()) {
            if (!iterator.next().isValid()) {
                iterator.remove()
            }
        }

        val stationsByOwner = HashMap<String, MutableList<IAeroStat>>()
        for (station in stationsById.values) {
            if (station.pos.d == world.provider.dimensionId) {
                stationsByOwner.getOrPut(station.owner) {
                    ArrayList()
                }.add(station)
            }
        }
        for (stations in stationsByOwner.values) {
            for (station in stations) {
                station.updateState(stations)
            }
        }
    }

    @JvmStatic
    public fun getStation(id: String): IAeroStat? {
        return stationsById[id]
    }

    @JvmStatic
    public fun register(station: IAeroStat) {
        if (station.owner.isNotEmpty() && station.uuid.isNotEmpty()) {
            stationsById[station.uuid] = station
        }
    }

    @JvmStatic
    public fun onClear() {
        stationsById.clear()
    }

    @JvmStatic
    public fun unregister(station: IAeroStat) {
        if (stationsById[station.uuid] === station) {
            stationsById.remove(station.uuid)
        }
    }
}
