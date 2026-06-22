package com.impact.workspace.draft.parallel_processing.integration.gt.processing

import com.impact.workspace.draft.comms.integration.gt.executor.BaseExecutorMachine
import com.impact.workspace.draft.comms.integration.gt.executor.TestExecutorMachine
import com.impact.workspace.draft.parallel_processing.common.ParallelLinkStatus
import com.impact.workspace.draft.parallel_processing.common.ParallelProcessingServer
import com.impact.workspace.draft.parallel_processing.integration.gt.processing.hatch.InputParallelComputingHatch
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import java.util.UUID

abstract class BaseParallelProcessingMachine<T : BaseExecutorMachine<T>>
    : BaseExecutorMachine<T> {

    constructor(id: Int, name: String, nameRegional: String) : super(id, name, nameRegional)
    constructor(aName: String) : super(aName)

    internal val parallelMachineId: UUID
        get() = part.commsId

    private var currentPpu: Int = 1
    private var maxPpu: Int = 1
    private var lastReceivedPpuTick: Long = Long.MIN_VALUE
    private var autoLinkStatus = ParallelLinkStatus.NO_INPUT_HATCH

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aSide: Byte, aFacing: Byte, aColorIndex: Byte,
        aActive: Boolean, aRedstone: Boolean,
    ): Array<ITexture?> {
        return if (aSide == aFacing) {
            val overlay = if (aActive) Textures.BlockIcons.MP1a else Textures.BlockIcons.MP1
            arrayOf(TestExecutorMachine.INDEX_CASE, TextureFactory.of(overlay))
        } else {
            arrayOf(TestExecutorMachine.INDEX_CASE)
        }
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        ParallelProcessingServer.registerMachine(this)
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)

        if (!te.isServerSide) return

        if (tick % 40 == 0L) {
            maxPpu = getRequiredPpu()
            autoLinkStatus = ParallelProcessingServer.refreshMachineLink(this)
        }

        if (lastReceivedPpuTick + 40L < tick) {
            currentPpu = 1
        }
    }

    override fun inValidate() {
        ParallelProcessingServer.unregisterMachine(this)
        super.inValidate()
    }

    override fun onRemoval() {
        ParallelProcessingServer.unregisterMachine(this)
        super.onRemoval()
    }

    override fun onNotePadRightClick(side: Byte, player: EntityPlayer, x: Float, y: Float, z: Float) {
    }

    override fun addLinkComputer(id: UUID?) {
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        super.writeInfoWaila(nbt)
        nbt.setBoolean("isBound", getLinkComputer() != null)
        nbt.setInteger("currentPpu", currentPpu)
        nbt.setInteger("maxPpu", maxPpu)
        nbt.setByte("parallelLinkStatus", autoLinkStatus.ordinal.toByte())
    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {
        val isBound = nbt.getBoolean("isBound")
        if (isBound) {
            tt += buildString {
                append("Parallel: ")
                append(EnumChatFormatting.GREEN)
                append(nbt.getInteger("currentPpu"))
                append(EnumChatFormatting.RESET)
                append(" / ")
                append(EnumChatFormatting.YELLOW)
                append(nbt.getInteger("maxPpu"))
                append(EnumChatFormatting.RESET)
                append(" PPU")
            }
        } else {
            val linkStatus = ParallelLinkStatus.entries[nbt.getByte("parallelLinkStatus").toInt()]
            val status = linkStatus.name.replace("_", " ")
            tt += "Parallel Link: ${EnumChatFormatting.RED}${status}"
        }
        super.readInfoWaila(nbt, tt)
    }

    internal fun getRequiredPpu(): Int {
        val hatch = communicationHatches
            .firstOrNull() as? InputParallelComputingHatch ?: return 1
        return hatch.getMaxPpu()
    }

    internal fun applyParallelLinkedComputer(id: UUID?) {
        setLinkComputerInternal(id)
    }

    internal fun applyReceivedPpu(ppu: Int) {
        currentPpu = ppu.coerceAtMost(maxPpu)
        lastReceivedPpuTick = baseMetaTileEntity.world.totalWorldTime
    }
}
