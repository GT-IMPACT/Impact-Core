package com.impact.common.managers

import com.impact.addon.gt.api.aerostat.IAeroStat
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraft.world.World

object AeroStateNetworkManager {

    private val DATA = hashMapOf<Key, IAeroStat>()
    private var isModified = false

    @JvmStatic
    fun onWorldTick(e: TickEvent.WorldTickEvent, tick: Long) {
        if (isModified && tick % 20 == 0L) {
            updateNetwork(e.world)
            isModified = false
        }
    }

    @JvmStatic
    fun updateNetwork(world: World) {
        DATA.forEach { (key, stat) ->
            val data = DATA
                .filterValues { it.owner == key.owner }.values.toList()
            stat.updateState(data)
        }
    }

    @JvmStatic
    fun markUpdate() {
        isModified = true
    }

    @JvmStatic
    fun addAeroState(owner: String, pos: IAeroStat, uuid: String) {
        val key = Key(pos.name, owner, uuid)
        DATA[key] = pos
        markUpdate()
    }

    @JvmStatic
    fun onClear() {
        DATA.clear()
    }

    @JvmStatic
    fun onRemove(owner: String, pos: IAeroStat, uuid: String) {
        val key = Key(pos.name, owner, uuid)
        DATA.remove(key)
        markUpdate()
    }
}

data class Key(
    val name: String,
    val owner: String,
    val uuid: String,
)
