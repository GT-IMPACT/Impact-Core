package com.impact.workspace.draft.parallel_processing.integration.gt.computing.hatch

import com.impact.addon.waila.WailaProvider
import com.impact.mods.gregtech.enums.Texture
import com.impact.util.Utilits
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.nbt.NBTTagCompound

class OutputParallelComputingHatch : BaseParallelComputingHatch, WailaProvider {

    constructor(id: Int, nameRegional: String, tier: Int, maxPpu: Int)
            : super(id, "impact.machine.pp.out.$tier", nameRegional, tier, 0, desc(maxPpu)) {
        maxPpuTransfer = maxPpu
    }

    constructor(name: String, description: Array<String>, textures: Array<Array<Array<ITexture>>>, tier: Int, maxPpu: Int)
            : super(name, tier, 0, description, textures) {
        maxPpuTransfer = maxPpu
    }

    internal val maxPpuTransfer: Int

    override fun newMetaEntity(te: IGregTechTileEntity): IMetaTileEntity {
        return OutputParallelComputingHatch(mName, mDescriptionArray, mTextures, mTier.toInt(), maxPpuTransfer)
    }

    override fun getTexturesActive(base: ITexture): Array<ITexture?> {
        return arrayOf(base, TextureFactory.of(Texture.Icons.RACK_OVERLAY_ACTIVE))
    }

    override fun getTexturesInactive(base: ITexture): Array<ITexture?> {
        return arrayOf(base, TextureFactory.of(Texture.Icons.RACK_OVERLAY))
    }

    override fun writeInfoWaila(nbt: NBTTagCompound) {

    }

    override fun readInfoWaila(nbt: NBTTagCompound, tt: MutableList<String>) {

    }

    override fun saveNBTData(nbt: NBTTagCompound) {
        super.saveNBTData(nbt)
    }

    override fun loadNBTData(nbt: NBTTagCompound) {
        super.loadNBTData(nbt)
    }

    override fun getInventoryStackLimit(): Int {
        return 1
    }

    override fun isOutputFacing(aSide: Byte): Boolean {
        return false
    }

    override fun isInputFacing(aSide: Byte): Boolean {
        return aSide == baseMetaTileEntity.frontFacing
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
    }

    override fun inValidate() {
        removeBinding()
        super.inValidate()
    }

    override fun onRemoval() {
        removeBinding()
        super.onRemoval()
    }

    fun removeBinding() {
        // TODO Remove link
    }

    companion object {
        private fun desc(maxPpu: Int) = arrayOf(
            Utilits.impactTag(),
            "PPU Transmitter",
            "Transmitted up to $maxPpu PPU",
            "Used in parallel computers",
        )
    }
}
