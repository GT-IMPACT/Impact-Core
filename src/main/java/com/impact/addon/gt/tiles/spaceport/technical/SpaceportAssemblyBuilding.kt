package com.impact.addon.gt.tiles.spaceport.technical

import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import space.impact.api.multiblocks.structure.IStructureDefinition

class SpaceportAssemblyBuilding : GT_MetaTileEntity_MultiParallelBlockBase<SpaceportAssemblyBuilding> {

    companion object {
        private val TEXTURE: ITexture = Textures.BlockIcons.casingTexturePages[3][16]
    }

    constructor(aID: Int) : super(aID, "impact.multis.spaceport.technical", "Spaceport Assembly Building")
    constructor(name: String) : super(name)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = SpaceportAssemblyBuilding(mName)

    override fun getTexture(p0: IGregTechTileEntity?, p1: Byte, p2: Byte, p3: Byte, p4: Boolean, p5: Boolean): Array<ITexture> {
        return arrayOf(TEXTURE)
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {

    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        return true
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isClientSide) return

    }

    override fun createTooltip(): MultiBlockTooltipBuilder? {
        return null
    }

    override fun getStructureDefinition(): IStructureDefinition<SpaceportAssemblyBuilding>? {
        return null
    }
}
