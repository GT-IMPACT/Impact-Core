package com.impact.api.position

import net.minecraft.nbt.NBTTagCompound

interface IPosition {
    val x: Int
    val y: Int
    val z: Int
    val d: Int

    fun saveToNBT(): NBTTagCompound
    fun isEquals(pos: IPosition): Boolean
}