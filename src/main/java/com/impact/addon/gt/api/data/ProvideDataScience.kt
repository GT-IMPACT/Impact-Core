package com.impact.addon.gt.api.data

import com.impact.util.vector.Vector3i
import net.minecraft.nbt.NBTTagCompound

class ProvideDataScience : ProvideData<ScienceData> {

    constructor(content: ScienceData) : super(content)
    constructor(nbt: NBTTagCompound) : super(nbt)

    override fun contentToNBT(): NBTTagCompound {
        val compound = NBTTagCompound()
        compound.setLong("computation", content.content)
        compound.setInteger("id", content.id)
        return compound
    }

    override fun contentFromNBT(nbt: NBTTagCompound): ScienceData {
        val content = nbt.getLong("computation")
        val id = nbt.getInteger("id")
        return ScienceData(content, id)
    }

    override fun extraCheck() = true

    override fun unifyContentWith(content: ScienceData): ScienceData {
        val cnt = this.content
        return cnt.copy(content = cnt.content + content.content)
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

data class ScienceData(
    val content: Long,
    val id: Int,
)
