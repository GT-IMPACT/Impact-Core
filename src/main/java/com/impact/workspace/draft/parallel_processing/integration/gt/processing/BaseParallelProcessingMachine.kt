package com.impact.workspace.draft.parallel_processing.integration.gt.processing

import com.impact.workspace.draft.comms.integration.gt.executor.BaseExecutorMachine
import com.impact.workspace.draft.comms.integration.gt.executor.TestExecutorMachine
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

abstract class BaseParallelProcessingMachine<T : BaseExecutorMachine<T>>
    : BaseExecutorMachine<T> {

    constructor(id: Int, name: String, nameRegional: String) : super(id, name, nameRegional)
    constructor(aName: String) : super(aName)

    private var currentPpu: Int = 1
    private var maxPpu: Int = 1

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
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {
        super.writeInfoWaila(nbt)
        nbt.setBoolean("isBound", getLinkComputer() != null)
        nbt.setInteger("currentPpu", currentPpu)
        nbt.setInteger("maxPpu", maxPpu)
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
        }
        super.readInfoWaila(nbt, tt)
    }
}
