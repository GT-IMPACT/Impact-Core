package com.impact.workspace.draft.comms.integration.gt.executor

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.util.multis.GT_StructureUtility
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility

class TestExecutorMachine
    : BaseExecutorMachine<TestExecutorMachine> {

    constructor(id: Int, aNameRegional: String) : super(id, "impact.multis.test_executor_comms", aNameRegional)
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
        return TestExecutorMachine(mName)
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        var formationCheckList = checkPiece(1, 1, 0)

        noMaintenance()

        if (communicationHatches.size > 1) {
            formationCheckList = false
        }

        return formationCheckList
    }

    override fun construct(stackSize: ItemStack?, hintsOnly: Boolean) {
        buildPiece(stackSize, hintsOnly, 1, 1, 0)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("exec_test").apply {
            addTypeMachine("name", "Test Comms Executor")
            signAndFinalize();
        }
    }

    override fun getStructureDefinition(): IStructureDefinition<TestExecutorMachine?>? {
        // TODO move to companion object
        return StructureDefinition.builder<TestExecutorMachine>()
            .addShape(
                "main", arrayOf(
                    arrayOf(" ABBA", "  BBA", " AAAA"),
                    arrayOf(" ABBA", "AA  A", "AAAAA"),
                    arrayOf("AABBA", "AAAAA", "AAAAA"),
                )
            )
            .addElement(
                'A', GT_StructureUtility.ofHatchAdderOptional(
                    TestExecutorMachine::addCommunicationHatch,
                    CASING_TEXTURE_ID,
                    ImpactAPI.RED,
                    CASING,
                    CASING_META,
                )
            )
            .addElement('B', StructureUtility.ofBlockAnyMeta(ItemRegistery.IGlassBlock))
            .build()
    }

    companion object {
        val CASING: Block = Casing_Helper.sCaseCore1
        val CASING_META = 9
        val INDEX_CASE: ITexture? = Textures.BlockIcons.casingTexturePages[3][CASING_META]
        val CASING_TEXTURE_ID = CASING_META + 128 * 3
    }
}