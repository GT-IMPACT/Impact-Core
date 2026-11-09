package com.impact.mods.gregtech.tileentities.multi.matrixsystem

import gregtech.api.logic.ProcessingLogic
import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.blocks.ImpactCasings
import com.impact.mods.gregtech.blocks.ImpactHatchElement
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.ImpactMultiblockTooltipBuilder
import cpw.mods.fml.common.Optional
import gregtech.api.enums.HatchElement
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ICasing
import gregtech.api.interfaces.IHatchElement
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.render.TextureFactory
import gregtech.api.util.GTStructureUtility
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import gregtech.api.util.MultiblockTooltipBuilder
import gregtech.api.util.StructureRequirements
import mcp.mobius.waila.api.IWailaConfigHandler
import mcp.mobius.waila.api.IWailaDataAccessor
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector
import net.minecraft.util.StatCollector.translateToLocalFormatted
import net.minecraft.world.World
import space.impact.structures.hint.HintColor
import space.impact.structures.structure.IStructureDefinition
import space.impact.structures.structure.ISurvivalBuildEnvironment
import space.impact.structures.structure.StructureDefinition
import space.impact.structures.structure.StructureUtility
import space.impact.structures.structure.StructureUtility.withHint

class GTMTEMESystemProvider : GT_MetaTileEntity_MultiParallelBlockBase<GTMTEMESystemProvider> {

    @Optional.Method(modid = "Waila")
    public override fun getWailaBody(
        stack: ItemStack,
        tooltip: MutableList<String>,
        accessor: IWailaDataAccessor,
        config: IWailaConfigHandler,
    ) {
        super.getWailaBody(
            stack,
            tooltip,
            accessor,
            config,
        )
        tooltip.add(
            translateToLocalFormatted(
                "impact.waila.matrix.stored",
                accessor.nbtData.getInteger("mMP"),
            )
        )
    }

    @Optional.Method(modid = "Waila")
    public override fun getWailaNBTData(
        player: EntityPlayerMP,
        tile: TileEntity,
        tag: NBTTagCompound,
        world: World,
        x: Int,
        y: Int,
        z: Int,
    ) {
        super.getWailaNBTData(
            player,
            tile,
            tag,
            world,
            x,
            y,
            z,
        )
        tag.setInteger(
            "mMP",
            mMatrixParticlesSummary,
        )
    }

    private var casingCount: Int = 0

    companion object {

        private val CASING: ICasing = ImpactCasings.ME_CASING
        private val MP_CHAMBER: IHatchElement = object : IHatchElement {

            override fun matches(tile: IMetaTileEntity?): Boolean {
                return tile is GTMTE_Hatch_MESystemMPChamber
            }

            override fun count(machine: GT_MetaTileEntity_MultiBlockBase): Int {
                return (machine as? GTMTEMESystemProvider)?.mpChambers?.size ?: 0
            }

            override fun add(
                machine: GT_MetaTileEntity_MultiBlockBase,
                tile: IGregTechTileEntity,
                textureIndex: Int,
            ): Boolean {
                val controller = machine as? GTMTEMESystemProvider ?: return false
                val hatch = tile.metaTileEntity as? GTMTE_Hatch_MESystemMPChamber ?: return false
                hatch.updateTexture(textureIndex)
                return controller.mpChambers.add(hatch)
            }

            override fun getDescription(): String {
                return StatCollector.translateToLocal("impact.structure.hatch.matrix_chamber")
            }
        }

        private val SHELL: StructureRequirements = StructureRequirements.builder(
            CASING,
            39,
        )
            .atLeast(
                MP_CHAMBER,
                HatchElement.Maintenance,
            )
            .build()

        private val DEFINITION: IStructureDefinition<GTMTEMESystemProvider> by lazy {
            StructureDefinition.builder<GTMTEMESystemProvider>()
                .addShape(
                    "main",
                    arrayOf(
                        arrayOf("ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "),
                        arrayOf("ADDDA ", "A   AA", "A   A~", "AAAAAA"),
                        arrayOf("ADDDA ", "A   AA", "A   AA", "AAAAAA"),
                        arrayOf("ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "),
                    ),
                )
                .addChannel(
                    GTStructureUtility.HATCH_CHANNEL,
                    1,
                    1,
                )
                .addElement(
                    'A',
                    withHint(
                        GTStructureUtility.buildHatchAdder(GTMTEMESystemProvider::class.java)
                            .allow(
                                HatchElement.Maintenance,
                                ImpactHatchElement.InputBus,
                                HatchElement.OutputBus,
                                HatchElement.Energy,
                                ImpactHatchElement.LaserInput,
                            )
                            .autoPlace(
                                ImpactHatchElement.PowerInput,
                                HatchElement.Maintenance,
                                ImpactHatchElement.InputBus,
                                HatchElement.OutputBus,
                            )
                            .buildAndChain(SHELL) { machine ->
                                machine.casingCount++
                            },
                        HintColor.GREEN,
                    ),
                )
                .addElement(
                    'D',
                    StructureUtility.ofBlockAnyMeta(ItemRegistery.IGlassBlock),
                )
                .build()
        }

    }

    private val mpChambers = ArrayList<GTMTE_Hatch_MESystemMPChamber>()
    var mSpeedUp: Int = 0
    var mMatrixParticlesSummary: Int = 0

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.mesystemprovider", aNameRegional)

    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?) = GTMTEMESystemProvider(mName)

    public override fun construct(
        stack: ItemStack,
        hintOnly: Boolean,
    ) {
        buildPiece(
            stack,
            hintOnly,
            5,
            2,
            1,
        )
    }

    public override fun survivalConstruct(
        trigger: ItemStack,
        budget: Int,
        environment: ISurvivalBuildEnvironment,
    ): Int {
        return survivalBuildPiece(
            "main",
            trigger,
            5,
            2,
            1,
            budget,
            environment,
        )
    }


    public override fun machineStructure(te: IGregTechTileEntity): Boolean {
        casingCount = 0
        mpChambers.clear()
        mSpeedUp = 1
        var formationChecklist = checkPiece(
            5,
            2,
            1,
        ) && SHELL.isSatisfied(
            this,
            casingCount,
        )

        if (mpChambers.size != 1) {
            formationChecklist = false
        }

        if (!formationChecklist) {
            mSpeedUp = 1
        }

        val finally = formationChecklist && hasRequireHatches(
            energy = 4,
            inputBuss = 2,
            outputBuss = 2,
            maintenance = 1,
        )

        if (!finally) {
            mSpeedUp = 1
        }

        return finally
    }

    public override fun getStructureDefinition(): IStructureDefinition<GTMTEMESystemProvider> {
        return DEFINITION
    }

    public override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean,
    ): Array<ITexture> {
        return if (aSide == aFacing) {
            val overlay = if (aActive) {
                Textures.BlockIcons.MP1a
            } else {
                Textures.BlockIcons.MP1
            }
            arrayOf(
                CASING.texture,
                TextureFactory.of(overlay),
            )
        } else {
            arrayOf(CASING.texture)
        }
    }


    public override fun createTooltip(): MultiblockTooltipBuilder {
        return ImpactMultiblockTooltipBuilder(mName)
            .useCompactStructure()
            .addMachineType("ME System Provider")
            .addInfo("Crafts ME components using stable Matrix Particles.")
            .beginStructureBlock(6, 4, 4, false)
            .addController("right edge of layer 2, 1 block behind the front edge")
            .addStructureSection("Components")
            .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.minimumCasings)
            .addOtherStructurePart("I-Glass", "exactly 24; roof and front/back walls; any color")
            .addStructureSection("Hatches and buses")
            .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions",)
            .addHatchInfo("Energy Hatches", "optional, up to 4 total; casing positions")
            .addHatchInfo("Input Buses", "optional, up to 2; casing positions")
            .addHatchInfo("Output Buses", "optional, up to 2; casing positions")
            .addHatchInfo("Matrix Particles Chamber", "exactly 1; casing positions")
            .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
            .addHatchChannelInfo()
            .toolTipFinisher()
    }

    override fun getRecipeMap(): GT_Recipe_Map {
        return GT_RecipeMaps.sMESystemProvider
    }

    override fun getRecipeMapForNEI(): GT_Recipe_Map {
        return GT_RecipeMaps.sMESystemProvider
    }

    override fun createProcessingLogic(): ProcessingLogic {
        return super.createProcessingLogic()
            .setOperationLimit { recipe ->
                if (recipe.mSpecialValue <= 0) {
                    Int.MAX_VALUE
                } else {
                    mMatrixParticlesSummary / recipe.mSpecialValue
                }
            }
            .setRecipeCommitHandler { recipe, operations ->
                mMatrixParticlesSummary -= Math.multiplyExact(
                    recipe.mSpecialValue.coerceAtLeast(0),
                    operations,
                )
            }
    }

    override fun onPostTick(iAm: IGregTechTileEntity, aTick: Long) {
        super.onPostTick(iAm, aTick)
        if (iAm.isServerSide && aTick % 8 == 0L) {
            if (mpChambers.isNotEmpty()) {
                val ch = mpChambers[0]
                if (ch.mpSummary >= 1000) {
                    if ((mMatrixParticlesSummary + 1000) <= 100000) {
                        mMatrixParticlesSummary += 1000
                        ch.subMPSummary(1000)
                    }
                }
            }
        }
    }

    override fun saveNBTData(aNBT: NBTTagCompound) {
        super.saveNBTData(aNBT)
        aNBT.setInteger("mMatrixParticlesSummary", mMatrixParticlesSummary)
        aNBT.setInteger("mSpeedUp", mSpeedUp)
    }

    override fun loadNBTData(aNBT: NBTTagCompound) {
        super.loadNBTData(aNBT)
        mMatrixParticlesSummary = aNBT.getInteger("mMatrixParticlesSummary")
        mSpeedUp = aNBT.getInteger("mSpeedUp")
    }

    override fun getInfoData(): Array<String> {
        return arrayOf(
            "Usage Energy: " + EnumChatFormatting.RED + -mEUt + EnumChatFormatting.RESET + " EU/t",
            "Max Voltage: " + EnumChatFormatting.YELLOW + getMaxInputVoltage() + EnumChatFormatting.RESET + " EU/t ",
            "Maintenance: " + (if (super.getRepairStatus() == super.getIdealStatus()) EnumChatFormatting.GREEN.toString() + "Good " + EnumChatFormatting.YELLOW + mEfficiency / 100.0f + " %" + EnumChatFormatting.RESET else EnumChatFormatting.RED.toString() + "Has Problems " + mEfficiency / 100.0f + " %" + EnumChatFormatting.RESET),
        )
    }

    override fun getSupportedProcessingModes(): Int {
        return super.getSupportedProcessingModes() and ProcessingLogic.SEPARATE_INPUTS.inv()
    }

    override fun hasShowConnect(): Boolean {
        return false
    }
}
