package com.impact.mods.gregtech.tileentities.multi.parallelsystem

import net.minecraft.entity.player.EntityPlayer

interface ICommunicatorConnect {

    fun onConnected(frequency: Int, player: EntityPlayer)

    fun updateFrequency(frequency: Int)

    fun getFrequency(): Int

    fun onFindConnect(frequency: Int, player: EntityPlayer)
}