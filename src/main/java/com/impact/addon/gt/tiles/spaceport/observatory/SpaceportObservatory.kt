package com.impact.addon.gt.tiles.spaceport.observatory

import com.impact.addon.gt.block.ScienceDataBlock
import com.impact.addon.gt.tiles.data.DataHatchScienceInput
import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.blocks.Build_Casing_Helper
import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.util.multis.GT_StructureUtility.ofHatchAdder
import com.impact.util.multis.GT_StructureUtility.ofHatchAdderOptional
import com.impact.util.string.MultiBlockTooltipBuilder
import com.impact.util.vector.Structure
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility.*

class SpaceportObservatory : GTMTE_Impact_BlockBase<SpaceportObservatory> {

    companion object {
        private val CASE_BLOCK = Casing_Helper.sCaseCore3
        private val CASE_META = Build_Casing_Helper.OBSERVATORY.meta
        private val INDEX_CASE = Build_Casing_Helper.OBSERVATORY.idCasing
    }

    private val dataIn = HashSet<DataHatchScienceInput>()
    private var isOpenDome: Boolean = false
    private var surfaceContent: Long = 0
    private var atmosphericContent: Long = 0

    constructor(aID: Int) : super(aID, "impact.multis.spaceport.observatory", "Spaceport Observatory")
    constructor(name: String) : super(name)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = SpaceportObservatory(mName)

    override fun getTexture(te: IGregTechTileEntity?, side: Byte, facing: Byte, colorIndex: Byte, active: Boolean, redstone: Boolean): Array<ITexture> {
        return if (side == facing) arrayOf(
            TextureFactory.of(Texture.Icons.OBSERVATORY_CASING),
            TextureFactory.of(if (active) Texture.Icons.OBSERVATORY_OVERLAY_ACTIVE else Texture.Icons.OBSERVATORY_OVERLAY)
        )
        else arrayOf(TextureFactory.of(Texture.Icons.OBSERVATORY_CASING))
    }

    private fun addToDataIn(te: IGregTechTileEntity?, index: Short): Boolean {
        if (te == null) return false
        val aMetaTileEntity = te.metaTileEntity ?: return false
        if (aMetaTileEntity is GT_MetaTileEntity_Hatch) aMetaTileEntity.updateTexture(index.toInt())
        if (aMetaTileEntity is DataHatchScienceInput) return dataIn.add(aMetaTileEntity)
        return false
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 7, 6, 5)
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        dataIn.clear()
        noMaintenance()
        val isCheck = checkPiece(7, 6, 5)
        return isCheck && dataIn.firstOrNull()?.getColor() != dataIn.lastOrNull()?.getColor()
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setBoolean("isOpenDome", isOpenDome)
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        isOpenDome = aNBT.getBoolean("isOpenDome")
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isClientSide) return

        if (tick % 60 == 0L) {
            if (!isOpenDome) stopMachine()
        }
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        val b = MultiBlockTooltipBuilder("spaceport_observatory")
        b.addTypeMachine("name", "Observatory")
            .addSeparator()
            .addController()
            .sizeStructure(3, 3, 3)
            .addOtherStructurePartAny("case.0", "Steel Frame Box")
            .signAndFinalize()
        return b
    }

    override fun onScrewdriverRightClick(aSide: Byte, aPlayer: EntityPlayer?, aX: Float, aY: Float, aZ: Float) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ)
        changeStateDome(!isOpenDome)
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {

        dataIn.forEach { hatch->
            hatch.data?.getContent()?.also { data ->
                when(data.id) {
                    0 -> surfaceContent += data.content
                    1 -> atmosphericContent += data.content
                }
            }
        }

        mEfficiency = 10000
        mMaxProgresstime = 20
        return isOpenDome
    }

    private fun changeStateDome(isOpen: Boolean) {
        isOpenDome = isOpen
        val te = baseMetaTileEntity
        val vec = Structure.goBuild(te, 0, 6, -2)
        if (isOpenDome) {
            Structure.setBlock(te, vec, ItemRegistery.IGlassBlock, 0)
            te.enableWorking()
        } else {
            Structure.setBlock(te, vec, CASE_BLOCK, CASE_META)
        }
    }

    override fun getStructureDefinition(): IStructureDefinition<SpaceportObservatory>? {
        return StructureDefinition.builder<SpaceportObservatory>()
            .addShape(
                "main", arrayOf(
                    arrayOf("               ", "               ", "               ", "               ", "     EEEEE     ", "     EEEEE     ", "     EE EE     ", "     EE EE     ", "     EEEEE     "),
                    arrayOf("               ", "               ", "               ", "     EEEEE     ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EEEEEEEEE   "),
                    arrayOf("               ", "               ", "      EEE      ", "   EEE   EEE   ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ", "  EEEEEEEEEEE  "),
                    arrayOf("               ", "               ", "    EEEEEEE    ", "  EE       EE  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("               ", "     EEEEE     ", "   EE     EE   ", "  E         E  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("      EEE      ", "    EEEEEEE    ", "   E  HHH  E   ", " EE   HCH   EE ", "EEE   HCH     E", "EEE   HCH     E", "EEE   H~H     E", "EEE   HHH     E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEEEE     ", "    EECCCEE    ", "  EE HCCCH EE  ", " E   HCCCH   E ", "EEE  HCCCH    E", "FGA  HCCCH    E", "EGA  HCCCH    E", "EEE  HEEEH    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEBEE     ", "    EECDCEE    ", "  EE HCDCH EE  ", " E   CCDCC   E ", "EEE  CCDCC    E", "EEE  CCDCC    E", "EEE  CCDCC    E", "EEE  HEEEH    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEEEE     ", "    EECCCEE    ", "  EE HCCCH EE  ", " E   HCCCH   E ", "EEE  HCCCH    E", "FGA  HCCCH    E", "EGA  HCCCH    E", "EEE  HEEEH    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("      EEE      ", "    EEEEEEE    ", "   E  HHH  E   ", " EE   HCH   EE ", "EEE   HCH     E", "EEE   HCH     E", "EEE   HCH     E", "EEE   HHH     E", "EEEEEEEEEEEEEEE"),
                    arrayOf("               ", "     EEEEE     ", "   EE     EE   ", "  E         E  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("               ", "               ", "    EEEEEEE    ", "  EE       EE  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("               ", "               ", "      EEE      ", "   EEE   EEE   ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ", "  EEEEEEEEEEE  "),
                    arrayOf("               ", "               ", "               ", "     EEEEE     ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EEEEEEEEE   "),
                    arrayOf("               ", "               ", "               ", "               ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     "),
                )
            )
            .addElement('A', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
            .addElement('B', ofChain(ofBlock(CASE_BLOCK, CASE_META), ofBlockAnyMeta(ItemRegistery.IGlassBlock)))
            .addElement('C', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
            .addElement('D', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
            .addElement('E', ofBlock(CASE_BLOCK, CASE_META))
            .addElement('F', ofHatchAdder(SpaceportObservatory::addToDataIn, INDEX_CASE, ImpactAPI.RED))
            .addElement('G', ofBlock(ScienceDataBlock.INSTANCE, ScienceDataBlock.OBSERVATORY_PROCESSOR))
            .addElement('H', ofHatchAdderOptional(SpaceportObservatory::addToDataIn, INDEX_CASE, ImpactAPI.L_GRAY, CASE_BLOCK, CASE_META))
            .build()
    }
}
