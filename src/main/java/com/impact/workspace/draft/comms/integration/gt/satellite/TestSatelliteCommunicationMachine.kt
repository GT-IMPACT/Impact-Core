package com.impact.workspace.draft.comms.integration.gt.satellite

import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility

class TestSatelliteCommunicationMachine : BaseSatelliteCommunicationMachine<TestSatelliteCommunicationMachine> {

    constructor(id: Int, aNameRegional: String) : super(id, "impact.multis.test_satellite_comms", aNameRegional)
    constructor(aName: String) : super(aName)

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean
    ): Array<ITexture?> {
        return if (aSide == aFacing) {
            val overlay = if (aActive) Textures.BlockIcons.MP1a else Textures.BlockIcons.MP1
            arrayOf(INDEX_CASE, TextureFactory.of(overlay))
        } else {
            arrayOf(INDEX_CASE)
        }
    }

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity {
        return TestSatelliteCommunicationMachine(mName)
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        var formationCheckList = checkPiece(2, 2, 1)

        noMaintenance()

        return formationCheckList
    }

    override fun checkRecipe(aStack: ItemStack?): Boolean {
        this.mMaxProgresstime = 10
        this.mEfficiency = 10000
        this.mEfficiencyIncrease = 10000
        this.mEUt = 0
        return true
    }

    override fun construct(stackSize: ItemStack?, hintsOnly: Boolean) {
        buildPiece(stackSize, hintsOnly, 2, 2, 1)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("sps_test").apply {
            addTypeMachine("name", "Test Comms Satellite")
            signAndFinalize()
        }
    }

    override fun getStructureDefinition(): IStructureDefinition<TestSatelliteCommunicationMachine?>? {
        // TODO move to companion object
        return StructureDefinition.builder<TestSatelliteCommunicationMachine>()
            .addShape(
                "main", arrayOf(
                    arrayOf(" A A ", " A A ", " A A ", " A A "),
                    arrayOf(" A A ", "AAAAA", "AA~AA", "AAAAA"),
                    arrayOf(" A A ", "AAAAA", "AAAAA", "AAAAA"),
                    arrayOf(" A A ", " A A ", " A A ", " A A "),
                )
            )
            .addElement('A', StructureUtility.ofBlock(CASING, CASING_META))
            .build();
    }

    companion object {
        val CASING: Block = Casing_Helper.sCasePage8_3
        val CASING_META: Int = 5
        val INDEX_CASE: ITexture? = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64]
        val CASING_TEXTURE_ID: Int = CASING_META + 64 + 128 * 8
    }
}
