package com.impact.addon.gt.api.data

import com.impact.util.vector.Vector3i
import net.minecraft.nbt.NBTTagCompound
import java.util.*

abstract class ProvideData<CONTENT> {

    companion object {
        private const val MAX_HISTORY = 64
    }

    private val trace: LinkedHashSet<Vector3i> = LinkedHashSet()

    @JvmField
    protected var content: CONTENT

    constructor(content: CONTENT) {
        this.content = content
    }

    constructor(nbt: NBTTagCompound) {
        content = getContentInit(nbt)
        for (i in 0 until nbt.getByte("history")) {
            trace.add(
                Vector3i(
                    nbt.getInteger("x$i"),
                    nbt.getInteger("y$i"),
                    nbt.getInteger("z$i")
                )
            )
        }
    }

    private fun getContentInit(nbt: NBTTagCompound): CONTENT {
        return contentFromNBT(nbt.getCompoundTag("content"))
    }

    fun toNbt(): NBTTagCompound {
        val nbt = NBTTagCompound()
        val contentTag = contentToNBT()
        if (contentTag != null) {
            nbt.setTag("content", contentTag)
        }
        nbt.setByte("history", trace.size.toByte())
        for ((i, v) in trace.withIndex()) {
            nbt.setInteger("x$i", v.x)
            nbt.setInteger("y$i", v.y)
            nbt.setInteger("z$i", v.z)
        }
        return nbt
    }

    protected abstract fun contentToNBT(): NBTTagCompound?
    protected abstract fun contentFromNBT(nbt: NBTTagCompound): CONTENT
    protected abstract fun unifyContentWith(content: CONTENT): CONTENT
    abstract fun extraCheck(): Boolean

    operator fun contains(v: Vector3i): Boolean {
        return trace.contains(v)
    }

    fun check(): Boolean {
        return trace.size <= MAX_HISTORY
    }

    protected fun unifyTrace(vararg positions: Vector3i): ProvideData<CONTENT>? {
        Collections.addAll(trace, *positions)
        return if (check() && extraCheck()) this else null
    }

    protected fun unifyTrace(p: ProvideData<CONTENT>?): ProvideData<CONTENT>? {
        if (p == null) return this
        trace.addAll(p.trace)
        return if (check() && extraCheck()) this else null
    }

    protected fun unifyWith(p: ProvideData<CONTENT>?): ProvideData<CONTENT>? {
        if (p == null) return this
        trace.addAll(p.trace)
        if (check() && extraCheck()) {
            content = unifyContentWith(p.content)
            return this
        }
        return null
    }

    fun contentIfNotInTrace(pos: Vector3i?): CONTENT? {
        return if (trace.contains(pos)) null
        else getContent()
    }

    open fun getContent(): CONTENT? {
        return content
    }

    open fun getContentString(): String? {
        return content.toString()
    }

    fun getTraceSize(): Int {
        return trace.size
    }
}
