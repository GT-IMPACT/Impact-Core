package com.impact.workspace.draft.comms.integration.gt.computer

import com.impact.addon.waila.WailaProvider
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.GregtechMetaTileEntityCommsAccess.Companion.access
import com.impact.workspace.draft.comms.adapters.commsWorldKey
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.parts.CommsMachinePartProvider
import com.impact.workspace.draft.comms.parts.types.ComputerCommsMachinePart
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

abstract class BaseComputerCommunicationMachine<T : BaseComputerCommunicationMachine<T>>
    : GTMTE_Impact_BlockBase<T>, CommsMachinePartProvider, CommsActiveProvider, WailaProvider {

    constructor(id: Int, aName: String, aNameRegional: String) : super(id, aName, aNameRegional)
    constructor(aName: String) : super(aName)

    override val part by lazy {
        ComputerCommsMachinePart(
            access = baseMetaTileEntity.access,
            activeProvider = this,
        )
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        part.validate()
    }

    override fun inValidate() {
        part.invalidate()
        super.inValidate()
    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
        part.saveNBTData(nbt)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        part.loadNBTData(nbt)
    }

    protected fun commsMarkUpdateNetwork() {
        CommsServer.getWorld(baseMetaTileEntity.commsWorldKey()).markDirty()
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (!te.isServerSide) return

        if (tick % 40 == 0L && part.hasConnected != mMachine) {
            part.hasConnected = mMachine
            commsMarkUpdateNetwork()
        }
    }

    override fun isCommsActive(): Boolean {
        return part.hasConnected
    }

    override fun onNotePadRightClick(side: Byte, player: EntityPlayer, x: Float, y: Float, z: Float) {
        super.onNotePadRightClick(side, player, x, y, z)

        val stack = player.currentEquippedItem ?: return
        val tag = stack.tagCompound ?: NBTTagCompound()

        tag.setString("computerId", part.commsId.toString())
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        nbt.setByte("status", part.commsNetworkState.ordinal.toByte())
        part.commsId.setNbt("commsId", nbt)
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val commsId = nbt.getUuid("commsId")
        tt += "ID: " + EnumChatFormatting.YELLOW + commsId.toString().substring(0, 8)

        val statusOrdinal = nbt.getByte("status").toInt()
        val status = CommsNetworkState.entries.find { it.ordinal == statusOrdinal } ?: return

        val color = when (status) {
            CommsNetworkState.DISCONNECTED -> EnumChatFormatting.RED
            CommsNetworkState.CONNECTING -> EnumChatFormatting.YELLOW
            CommsNetworkState.CONNECTED -> EnumChatFormatting.GREEN
        }

        tt += "Status: $color$status"
    }
}
