@file:Suppress("UNCHECKED_CAST")

package com.impact.addon.waila

import net.minecraft.nbt.NBTTagCompound

interface WailaProvider {

    fun writeInfoWaila(nbt: NBTTagCompound)

    fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>)
}
