package com.impact.workspace.draft.comms.integration.gt.computer

import com.impact.loader.ItemRegistery
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

class TestComputerCommunicationMachine
    : BaseComputerCommunicationMachine<TestComputerCommunicationMachine> {

    constructor(id: Int, aNameRegional: String) : super(id, "impact.multis.test_computer_comms", aNameRegional)
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
        return TestComputerCommunicationMachine(mName)
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        var formationCheckList = checkPiece(0, 2, 0)

        noMaintenance()

        return formationCheckList
    }

    override fun construct(stackSize: ItemStack?, hintsOnly: Boolean) {
        buildPiece(stackSize, hintsOnly, 0, 2, 0)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("psc_test").apply {
            addTypeMachine("name", "Test Comms Computer")
            signAndFinalize();
        }
    }

    override fun getStructureDefinition(): IStructureDefinition<TestComputerCommunicationMachine?>? {
        // TODO move to companion object
        return StructureDefinition.builder<TestComputerCommunicationMachine>()
            .addShape(
                "main", arrayOf(
                    arrayOf("AA", "AA", "~A", "AA"),
                    arrayOf("AA", "BB", "BB", "AA"),
                    arrayOf("AA", "BB", "BB", "AA"),
                    arrayOf("AA", "AA", "AA", "AA"),
                )
            )
            .addElement('A', StructureUtility.ofBlock(CASING, CASING_META))
            .addElement('B', StructureUtility.ofBlock(ItemRegistery.InsideBlock, 2))
            .build();
    }

    companion object {
        val CASING: Block = Casing_Helper.sCasePage8_3
        val CASING_META: Int = 7
        val INDEX_CASE: ITexture? = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64]
        val CASING_TEXTURE_ID: Int = CASING_META + 64 + 128 * 8
    }
}
