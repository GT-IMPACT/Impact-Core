package com.impact.addon.gt.tiles.spaceport.observatory

import com.impact.addon.gt.tiles.data.DataHatchScienceOutput
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.multis.GT_StructureUtility
import com.impact.util.multis.GT_StructureUtility.ofHatchAdder
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility

class ObservatoryAtmosphericExplorer : GTMTE_Impact_BlockBase<ObservatoryAtmosphericExplorer> {

    companion object {
        private val TEXTURE = Textures.BlockIcons.casingTexturePages[3][16]
        private const val INDEX_CASE = 0
    }

    private val dataOut = HashSet<DataHatchScienceOutput>()

    constructor(aID: Int) : super(aID, "impact.multis.spaceport.observatory.atmospheric", "Spaceport Observatory Atmospheric Explorer")
    constructor(name: String) : super(name)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = ObservatoryAtmosphericExplorer(mName)

    override fun getTexture(p0: IGregTechTileEntity?, p1: Byte, p2: Byte, p3: Byte, p4: Boolean, p5: Boolean): Array<ITexture?> {
        return arrayOf(TEXTURE)
    }

    private fun addToDataOut(te: IGregTechTileEntity?, index: Short): Boolean {
        if (te == null) return false
        val aMetaTileEntity = te.metaTileEntity ?: return false
        if (aMetaTileEntity is GT_MetaTileEntity_Hatch) aMetaTileEntity.updateTexture(index.toInt())
        if (aMetaTileEntity is DataHatchScienceOutput) return dataOut.add(aMetaTileEntity)
        return false
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 1, 1, 0)
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

    override fun createTooltip(): MultiBlockTooltipBuilder {
        val b = MultiBlockTooltipBuilder("spaceport_observatory_atmospheric")
        b.addTypeMachine("name", "Observatory")
            .addSeparator()
            .addController()
            .sizeStructure(3, 3, 3)
            .addOtherStructurePartAny("case.0", "Steel Frame Box")
            .signAndFinalize()
        return b
    }

    override fun getStructureDefinition(): IStructureDefinition<ObservatoryAtmosphericExplorer> {
        return StructureDefinition.builder<ObservatoryAtmosphericExplorer>()
            .addShape(
                "main", arrayOf(
                    arrayOf("     DO ", "D~   DD ", "DD      "),
                    arrayOf(" DDDDDD ", "DD  DDDD", "DD  DDDD"),
                    arrayOf(" DDDDDD ", "DD  DDDD", "DD  DDDD"),
                )
            )
            .addElement('D', StructureUtility.ofBlock(Blocks.diamond_block, 0))
            .addElement('O', ofHatchAdder(ObservatoryAtmosphericExplorer::addToDataOut, INDEX_CASE, ImpactAPI.RED))
            .build()
    }
}
