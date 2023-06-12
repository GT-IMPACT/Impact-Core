package com.impact.addon.gt.tiles.spaceport.launch

import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.util.multis.GT_StructureUtility
import com.impact.util.string.MultiBlockTooltipBuilder
import com.impact.util.vector.Structure
import com.impact.util.vector.Structure.goBuild
import gregtech.api.enums.Materials
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.render.TextureFactory
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility
import space.impact.api.multiblocks.structure.StructureUtility.transpose

class SpaceportLaunchPad : GT_MetaTileEntity_MultiParallelBlockBase<SpaceportLaunchPad> {

    companion object {
        private val TEXTURE: ITexture = Textures.BlockIcons.casingTexturePages[3][16]
    }

    var isTopOk = false
    var isMiddleOk = false
    var isBottomOk = false

    constructor(aID: Int) : super(aID, "impact.multis.spaceport.launch_pad", "Spaceport Launch Pad")
    constructor(name: String) : super(name)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = SpaceportLaunchPad(mName)

    override fun getTexture(p0: IGregTechTileEntity?, side: Byte, facing: Byte, p3: Byte, active: Boolean, p5: Boolean): Array<ITexture> {
        return if (side == facing)
            arrayOf(
                TEXTURE, TextureFactory.of(
                    if (active) Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE
                    else Texture.Icons.OVERLAY_BASIC_MINER
                )
            )
        else arrayOf(TEXTURE)
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 7, 36, 10)
    }

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        noMaintenance()
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
        if (tick % 100 == 0L) {
            println(tick)
            if (tick > 300) {
                if (!isTopOk && isMiddleOk && isBottomOk) {
                    buildRocket("spaceship", ItemStack(Items.leather, 1), false, 5, 38, -2)
                    isTopOk = true
                    return
                }

                if (!isTopOk && !isMiddleOk && isBottomOk) {
                    buildRocket("stage_2", ItemStack(Items.leather, 1), false, 5, 38, -2)
                    isMiddleOk = true
                    return
                }

                if (!isTopOk && !isMiddleOk && !isBottomOk) {
                    buildRocket("stage_1", ItemStack(Items.leather, 1), false, 5, 38, -2)
                    isBottomOk = true
                    return
                }
            }
        }
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {
        return true
    }

    fun buildRocket(stage: String, trigger: ItemStack, hintOnly: Boolean, horizontalOffset: Int, verticalOffset: Int, depthOffset: Int): Boolean {
        val tTile = baseMetaTileEntity
        return rocketShape.buildOrHints(this, trigger, stage, tTile.world, extendedFacing, tTile.xCoord, tTile.yCoord.toInt(), tTile.zCoord, horizontalOffset, verticalOffset, depthOffset, hintOnly)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        val b = MultiBlockTooltipBuilder("spaceport_launch_pad")
        b.addTypeMachine("name", "Launch Pad")
            .addSeparator()
            .addController()
            .sizeStructure(3, 3, 3)
            .addOtherStructurePartAny("case.0", "Steel Frame Box")
            .signAndFinalize()
        return b
    }

    fun clearRocket(te: IGregTechTileEntity) {
        for (x in -4..4) {
            for (z in -4..4) {
                for (y in 1..40) {
                    val vec = goBuild(te, x, y, z)
                    Structure.setAir(te, vec)
                }
            }
        }
    }

    val rocketShape = StructureDefinition.builder<SpaceportLaunchPad>()
        .addShape(
            "spaceship", transpose(
                arrayOf(
                    arrayOf("           ", "           ", "           ", "           ", "    CCC    ", "    CCC    ", "    CCC    ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CCC    ", "   CCCCC   ", "   CCCCC   ", "   CCCCC   ", "    CCC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CCC    ", "   CCCCC   ", "   CCCCC   ", "   CCCCC   ", "    CCC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "    CCC    ", "   C   C   ", "  C     C  ", "  C     C  ", "  C     C  ", "   C   C   ", "    CCC    ", "           ", "           "),
                    arrayOf("           ", "           ", "    CCC    ", "   C   C   ", "  C     C  ", "  C     C  ", "  C     C  ", "   C   C   ", "    CCC    ", "           ", "           "),
                    arrayOf("           ", "           ", "    CCC    ", "   C   C   ", "  C     C  ", "  C     C  ", "  C     C  ", "   C   C   ", "    CCC    ", "           ", "           "),
                    arrayOf("           ", "           ", "    CCC    ", "   CCCCC   ", "  CC   CC  ", "  CC   CC  ", "  CC   CC  ", "   CCCCC   ", "    CCC    ", "           ", "           "),
                    arrayOf("           ", "           ", "     C     ", "    DDD    ", "   DDDDD   ", "  CDDDDDC  ", "   DDDDD   ", "    DDD    ", "     C     ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                )
            )
        )
        .addShape(
            "stage_2", transpose(arrayOf(
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    DDD    ", "   DDDDD   ", "   DDDDD   ", "   DDDDD   ", "    DDD    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CCC    ", "   C   C   ", "   C   C   ", "   C   C   ", "    CCC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CCC    ", "   C   C   ", "   C   C   ", "   C   C   ", "    CCC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    DDD    ", "   D   D   ", "   D   D   ", "   D   D   ", "    DDD    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CCC    ", "   C   C   ", "   C   C   ", "   C   C   ", "    CCC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   C   C   ", "   D   D   ", "   C   C   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   CDDDC   ", "   DDDDD   ", "   CDDDC   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                )
            )
        )
        .addShape(
            "stage_1", transpose(arrayOf(
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "           ", "    CDC    ", "   CDDDC   ", "   DDDDD   ", "   CDDDC   ", "    CDC    ", "           ", "           ", "           "),
                    arrayOf("           ", "           ", "     D     ", "    CCC    ", "   C   C   ", "  DC   CD  ", "   C   C   ", "    CCC    ", "     D     ", "           ", "           "),
                    arrayOf("           ", "           ", "     D     ", "   DCCCD   ", "   C   C   ", "  DC   CD  ", "   C   C   ", "   DCCCD   ", "     D     ", "           ", "           "),
                    arrayOf("           ", "     D     ", "    DDD    ", "   DCCCD   ", "  DC   CD  ", " DDC   CDD ", "  DC   CD  ", "   DCCCD   ", "    DDD    ", "     D     ", "           "),
                    arrayOf("     D     ", "     D     ", "    DDD    ", "   DCCCD   ", "  DC   CD  ", "DDDC   CDDD", "  DC   CD  ", "   DCCCD   ", "    DDD    ", "     D     ", "     D     "),
                    arrayOf("     D     ", "    DDD    ", "   DDDDD   ", "  DDCCCDD  ", " DDC   CDD ", "DDDC   CDDD", " DDC   CDD ", "  DDCCCDD  ", "   DDDDD   ", "    DDD    ", "     D     "),
                    arrayOf("     D     ", "    DDD    ", "   DDDDD   ", "  DDDDDDD  ", " DDCDDDDDD ", "DDDDDDDDDDD", " DDDDDDDDD ", "  DDDDDDD  ", "   DDDDD   ", "    DDD    ", "     D     "),
                    arrayOf("     D     ", "    DDD    ", "   DDDDD   ", "  DDDDDDD  ", " DDD   DDD ", "DDDD   DDDD", " DDD   DDD ", "  DDDDDDD  ", "   DDDDD   ", "    DDD    ", "     D     "),
                    arrayOf("     D     ", "    DDD    ", "           ", "    DDD    ", " D D   D D ", "DD D   D DD", " D D   D D ", "    DDD    ", "           ", "    DDD    ", "     D     "),
                    arrayOf("           ", "     D     ", "           ", "     D     ", "    DDD    ", " D DDDDD D ", "    DDD    ", "     D     ", "           ", "     D     ", "           "),
                )
            )
        )
        .addElement('C', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
        .addElement('D', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.GRAY))
        .build()

    override fun getStructureDefinition(): IStructureDefinition<SpaceportLaunchPad>? {
        return StructureDefinition.builder<SpaceportLaunchPad>()
            .addShape(
                "main", arrayOf(
                    arrayOf("     EEEEE     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     E E E     ", "     E E E     ", "     E E E     ", "     EEEEE     ", "     EE EE     ", "     EE EE     "),
                    arrayOf("     E   E     ", "               ", "     E   E     ", "               ", "     EC CE     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "     E   E     ", "     E   E     "),
                    arrayOf("     E   E     ", "               ", "     E   E     ", "               ", "     ECCCE     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               "),
                    arrayOf("     E   E     ", "               ", "     E   E     ", "               ", "     ECCCE     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "               ", "               ", "               ", "     E   E     ", "     E   E     ", "     E   E     "),
                    arrayOf("     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     EE EE     ", "     E E E     ", "     EE EE     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     E   E     ", "     E   E     ", "     E   E     ", "     EEEEE     ", "     EE EE     ", "     EE EE     "),
                    arrayOf("      E E      ", "      E E      ", "      E E      ", "      E E      ", "      ECE      ", "      EEE      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "      EEE      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "      EEE      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "      C~C      ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "      CCC      ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "       E       ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "  CC       CC  ", "  CC       CC  ", "  CC       CC  "),
                    arrayOf("               ", "               ", "               ", "      E E      ", "      ECE      ", "      EEE      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      E E      ", "       E       ", "      E E      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "   DDD   DDD   ", "CCCCCCCCCCCCCCC", "  CC       CC  ", "  CC       CC  ", "  CC       CC  "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "      EFE      ", "      FEF      ", "      EFE      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BBB BBB    ", "   DDDD DDDD   ", "CCCCCCCCCCCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BAAAAAB    ", "   DDDD DDDD   ", "CCCCCCC CCCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BA   AB    ", "    DD   DD    ", "CCCCCC   CCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "     A   A     ", "               ", "CCCCC     CCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BA   AB    ", "    DD   DD    ", "CCCCCC   CCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BAAAAAB    ", "   DDDD DDDD   ", "CCCCCCC CCCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "    BBB BBB    ", "   DDDD DDDD   ", "CCCCCCCCCCCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "   DDD   DDD   ", "CCCCCCCCCCCCCCC", "  CC       CC  ", "  CC       CC  ", "  CC       CC  "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "  CC       CC  ", "  CC       CC  ", "  CC       CC  "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "               ", "               ", "               "),
                    arrayOf("               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "CCCCCCCCCCCCCCC", "               ", "               ", "               "),
                )
            )
            .addElement('E', StructureUtility.lazy { _ -> GT_StructureUtility.ofFrame(Materials.Steel) })
            .addElement('F', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.BLUE)) //hatches
            .addElement('C', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.L_GRAY))
            .addElement('A', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.WHITE))
            .addElement('B', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.GRAY))
            .addElement('D', StructureUtility.ofBlock(ImpactAPI.getBlockHint(), ImpactAPI.BLACK))
            .build()
    }
}
