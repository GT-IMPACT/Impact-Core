package com.impact.workspace.draft.comms.parts

import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import net.minecraft.nbt.NBTTagCompound
import java.util.UUID

abstract class CommsMachinePart(
    protected val access: CommsMachineAccess,
) {
    final override fun toString(): String {
        return "${javaClass.simpleName}[$commsId]"
    }

    var commsId: UUID = UUID.randomUUID()
        private set

    private var registered = false

    protected open val commsWorld
        get() = access.commsWorld

    protected open val commsPos
        get() = access.commsPos

    fun validate() {
        if (!access.commsIsServerSide) return
        if (registered) return

        handleLoad()
        registered = true
    }

    fun onChunkUnload() {
        if (!access.commsIsServerSide) return
        if (!registered) return

        handleUnload()
        registered = false
    }

    fun invalidate() {
        if (!access.commsIsServerSide) return
        if (!registered) return

        handleRemove()
        registered = false
    }

    open fun loadNBTData(nbt: NBTTagCompound) {
        commsId = nbt.getUuid("commsId") ?: UUID.randomUUID()

        registered = false
        handleRead()
    }

    open fun saveNBTData(nbt: NBTTagCompound) {
        commsId.setNbt("commsId", nbt)
    }

    protected abstract fun handleLoad()

    protected abstract fun handleUnload()

    protected abstract fun handleRemove()

    protected open fun handleRead() {}
}
