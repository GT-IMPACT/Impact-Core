package com.impact.addon.gt.tiles.spaceport.observatory

import appeng.tile.networking.TileCableBus
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility.ofBlock
import space.impact.api.multiblocks.structure.StructureUtility.ofSpecificTileAdder

class SpaceportObservatory : GT_MetaTileEntity_MultiParallelBlockBase<SpaceportObservatory> {

    companion object {
        private val TEXTURE: ITexture = Textures.BlockIcons.casingTexturePages[3][16]
    }

    constructor(aID: Int) : super(aID, "impact.multis.spaceport.observatory", "Spaceport Observatory")
    constructor(name: String) : super(name)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = SpaceportObservatory(mName)

    override fun getTexture(p0: IGregTechTileEntity?, p1: Byte, p2: Byte, p3: Byte, p4: Boolean, p5: Boolean): Array<ITexture> {
        return arrayOf(TEXTURE)
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 7, 6, 5)
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
        val b = MultiBlockTooltipBuilder("spaceport_observatory")
        b.addTypeMachine("name", "Observatory")
            .addSeparator()
            .addController()
            .sizeStructure(3, 3, 3)
            .addOtherStructurePartAny("case.0", "Steel Frame Box")
            .signAndFinalize()
        return b
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
                    arrayOf("      EEE      ", "    EEEEEEE    ", "   E  EEE  E   ", " EE   ECE   EE ", "E     ECE     E", "E     ECE     E", "E     E~E     E", "E     EEE     E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEEEE     ", "    EECCCEE    ", "  EE ECCCE EE  ", " E   ECCCE   E ", "E    ECCCE    E", "F    FCCCE    E", "E    ECCCE    E", "E    EEEEF    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEDEE     ", "    EECDCEE    ", "  EE ECDCE EE  ", " E   CCDCC   E ", "E    CCDCC    E", "E    CCDCC    E", "E    CCDCC    E", "E    EEEEE    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("     EEEEE     ", "    EECCCEE    ", "  EE ECCCE EE  ", " E   ECCCE   E ", "E    ECCCE    E", "F    FCCCE    E", "E    ECCCE    E", "E    EEEEF    E", "EEEEEEEEEEEEEEE"),
                    arrayOf("      EEE      ", "    EEEEEEE    ", "   E  EEE  E   ", " EE   ECE   EE ", "E     ECE     E", "E     ECE     E", "E     FCF     E", "E     FEF     E", "EEEEEEEEEEEEEEE"),
                    arrayOf("               ", "     EEEEE     ", "   EE     EE   ", "  E         E  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("               ", "               ", "    EEEEEEE    ", "  EE       EE  ", " E           E ", " E           E ", " E           E ", " E           E ", " EEEEEEEEEEEEE "),
                    arrayOf("               ", "               ", "      EEE      ", "   EEE   EEE   ", "  E         E  ", "  E         E  ", "  E         E  ", "  E         E  ", "  EEEEEEEEEEE  "),
                    arrayOf("               ", "               ", "               ", "     EEEEE     ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EE     EE   ", "   EEEEEEEEE   "),
                    arrayOf("               ", "               ", "               ", "               ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     ", "     EEEEE     "),
                )
            )
            .addElement('B', ofBlock(Blocks.dirt, 0))
            .addElement('C', ofBlock(Blocks.glass, 0))
            .addElement('D', ofBlock(Blocks.stained_glass, 15))
            .addElement('E', ofBlock(ImpactAPI.getBlockHint(), 0))
            .addElement('F', ofBlock(Blocks.obsidian, 0))
            .build()
    }
}

val AtmosphericExplorer = arrayOf( //O - Output Observatory, x = 1, y = 2, z = 0
    arrayOf(
        "     DO ",
        "D~   DD ",
        "DD      "
    ),
    arrayOf(
        " DDDDDD ",
        "DD  DDDD",
        "DD  DDDD"
    ),
    arrayOf(
        " DDDDDD ",
        "DD  DDDD",
        "DD  DDDD"
    ),
)

val SurfaceExplorer = arrayOf( //O - Output Observatory, x = 2, y = 2, z = 1
    arrayOf(
        arrayOf(
            "     EEE",
            "EO   ECE",
            "EE   ECE",
            "EE   EEE",
        ), arrayOf(
            "     EEE",
            "EE   EDC",
            "EEE~EEDC",
            "EEEEEEEE",
        ), arrayOf(
            "     EEE",
            "     ECE",
            " EEEEECE",
            " EEEEEEE",
        ),
    )
)
