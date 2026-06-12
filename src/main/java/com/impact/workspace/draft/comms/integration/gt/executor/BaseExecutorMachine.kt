package com.impact.workspace.draft.comms.integration.gt.executor

import com.impact.addon.waila.WailaProvider
import com.impact.client.render.special.RenderSpecialType
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.network.Net
import com.impact.util.nbt.getUuid
import com.impact.util.nbt.setNbt
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.GregtechMetaTileEntityCommsAccess.Companion.access
import com.impact.workspace.draft.comms.adapters.commsWorldKey
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsComputerBindingProvider
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.common.mappers.CommsPathStreamMapper
import com.impact.workspace.draft.comms.parts.CommsMachinePartProvider
import com.impact.workspace.draft.comms.parts.types.ExecutorCommsMachinePart
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import java.util.UUID

abstract class BaseExecutorMachine<T : BaseExecutorMachine<T>>
    : GTMTE_Impact_BlockBase<T>, CommsMachinePartProvider, CommsActiveProvider, CommsComputerBindingProvider, WailaProvider {

    constructor(id: Int, aName: String, aNameRegional: String) : super(id, aName, aNameRegional)
    constructor(aName: String) : super(aName)

    protected val communicationHatches = arrayListOf<ExecutorCommunicationHatch>()
    private var lastCommsHatchCount = 0

    override val part by lazy {
        ExecutorCommsMachinePart(
            access = baseMetaTileEntity.access,
            activeProvider = this,
            bindingProvider = this,
        )
    }

    private var computerId: UUID? = null

    override fun getLinkComputer(): UUID? {
        return computerId
    }

    override fun addLinkComputer(id: UUID?) {
        computerId = id
        commsMarkUpdateNetwork()
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
        nbt.setInteger("lastCommsHatchCount", lastCommsHatchCount)
        computerId?.setNbt(prefix = "computer", nbt = nbt)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
        part.loadNBTData(nbt)
        lastCommsHatchCount = nbt.getInteger("lastCommsHatchCount")
        computerId = nbt.getUuid(prefix = "computer")
    }

    override fun onScrewdriverRightClick(side: Byte, player: EntityPlayer, x: Float, y: Float, z: Float) {
        super.onScrewdriverRightClick(side, player, x, y, z)

        if (player is EntityPlayerMP) {
            CommsServer.findConnectionPathFromExecutor(
                world = baseMetaTileEntity.commsWorldKey(),
                executorId = part.commsId,
            )?.also { path ->
                Net.renderSpecialClientUpdatePipe.send(player) {
                    int(RenderSpecialType.PathHighlight.ordinal)
                    CommsPathStreamMapper.writePath(this, path)
                }
            }
        }
    }

    override fun onNotePadRightClick(side: Byte, player: EntityPlayer, x: Float, y: Float, z: Float) {
        super.onNotePadRightClick(side, player, x, y, z)

        val stack = player.currentEquippedItem ?: return
        val tag = stack.tagCompound ?: NBTTagCompound()

        if (tag.hasKey("computerId")) {
            tag.getString("computerId")?.also { computerId ->
                val computerId = UUID.fromString(computerId)
                addLinkComputer(computerId)
            }
        }
    }

    protected fun commsMarkUpdateNetwork() {
        CommsServer.getWorld(baseMetaTileEntity.commsWorldKey()).markDirty()
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (!te.isServerSide) return

        if (tick % 40 == 0L && (part.hasConnected != mMachine || lastCommsHatchCount != communicationHatches.size)) {
            part.hasConnected = mMachine
            lastCommsHatchCount = communicationHatches.size
            commsMarkUpdateNetwork()
        }
    }

    override fun isCommsActive(): Boolean {
        return part.hasConnected && lastCommsHatchCount > 0
    }

    fun addCommunicationHatch(te: IGregTechTileEntity?, caseIndex: Short): Boolean {
        val mte = te?.metaTileEntity as? ExecutorCommunicationHatch ?: return false
        mte.updateTexture(caseIndex.toInt())
        te.isActive = true
        return communicationHatches.add(mte)
    }

    override fun clearHatches() {
        communicationHatches.clear()
        super.clearHatches()
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
