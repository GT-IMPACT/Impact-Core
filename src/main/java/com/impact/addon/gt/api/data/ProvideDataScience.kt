package com.impact.addon.gt.api.data

import com.impact.util.vector.Vector3i
import net.minecraft.nbt.NBTTagCompound

class ProvideDataScience : ProvideData<Long> {

    constructor(content: Long) : super(content)
    constructor(nbt: NBTTagCompound) : super(nbt)

    override fun contentToNBT(): NBTTagCompound {
        val compound = NBTTagCompound()
        compound.setLong("computation", content)
        return compound
    }

    override fun contentFromNBT(nbt: NBTTagCompound): Long {
        return nbt.getLong("computation");
    }

    override fun extraCheck() = true

    override fun unifyContentWith(content: Long): Long {
        return this.content + content
    }

    fun unifyTraceWith(vararg positions: Vector3i): ProvideDataScience? {
        return super.unifyTrace(positions = positions) as? ProvideDataScience
    }

    fun unifyTraceWith(p: ProvideDataScience?): ProvideDataScience? {
        return super.unifyTrace(p) as? ProvideDataScience
    }

    fun unifyPacketWith(p: ProvideDataScience?): ProvideDataScience? {
        return super.unifyWith(p) as? ProvideDataScience
    }
}
