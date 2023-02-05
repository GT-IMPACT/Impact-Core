package com.impact.mods.gregtech.tileentities.multi.parallelsystem

import net.minecraft.entity.player.EntityPlayer

interface ISatelliteConnect {

    fun onFindConnect(frequency: Int, player: EntityPlayer): Boolean

    fun getFrequency(): Int

    fun updateFrequency(frequency: Int)

    fun onCheckConnect(frequency: Int): Boolean
}