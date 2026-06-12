package com.impact.workspace.draft.comms.parts.types

import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsComputerBindingProvider
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.node.CommsGroundNodeType
import com.impact.workspace.draft.comms.node.types.CommsExecutorNode
import com.impact.workspace.draft.comms.parts.GroundCommsMachinePart
import net.minecraft.nbt.NBTTagCompound
import java.util.UUID

class ExecutorCommsMachinePart(
    access: CommsMachineAccess,
    private val activeProvider: CommsActiveProvider,
    private val bindingProvider: CommsComputerBindingProvider,
) : GroundCommsMachinePart(access), CommsExecutorNode {

    override val commsType: CommsGroundNodeType
        get() = CommsGroundNodeType.EXECUTOR

    override val unlocalizedName: String
        get() = access.unlocalizedName

    override val commsActive: Boolean
        get() = activeProvider.isCommsActive()

    override var commsNetworkState = CommsNetworkState.DISCONNECTED

    override val boundComputerId: UUID?
        get() = bindingProvider.getLinkComputer()

    internal var hasConnected: Boolean = false

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        hasConnected = nbt.getBoolean("hasConnected")
    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        nbt.setBoolean("hasConnected", hasConnected)
    }
}
