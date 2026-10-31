package com.impact.mods.gregtech.tileentities.multi.matrixsystem

import com.impact.addon.gt.api.recipe.MultiBlockRecipeBuilder
import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.blocks.ImpactCasings
import com.impact.mods.gregtech.blocks.ImpactHatchElement
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase
import com.impact.mods.gregtech.gui.base.GUI_BASE
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
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
import net.minecraft.entity.player.InventoryPlayer
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
            0,
        )
            .atLeast(MP_CHAMBER)
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
                                HatchElement.Input,
                                HatchElement.Output,
                                HatchElement.Energy,
                                ImpactHatchElement.LaserInput,
                            )
                            .buildAndChain(SHELL),
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
        mpChambers.clear()
        mSpeedUp = 1
        var formationChecklist = checkPiece(
            5,
            2,
            1,
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

    override fun getClientGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any {
        return GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, localName)
    }

    override fun getServerGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any {
        return GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity)
    }

    public override fun createTooltip(): MultiblockTooltipBuilder {
        return MultiblockTooltipBuilder(mName).apply {
            addInfo("Consume Stable Matrix Particles")
            addMachineType("ME System Provider")
            addInfo("Crafting ME Components")
            addSeparator()
            addController("See the structure hologram")
            addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
            addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
            addOtherStructurePart("Input Bus", "Any casing; maximum 2")
            addOtherStructurePart("Output Bus", "Any casing; maximum 2")
            addRequirements(SHELL, "Any casing position")
            addOtherStructurePart("Matrix Particles Chamber", "Any Casing (max x1)")
            addOtherStructurePart("I-Glass", "Glass for structure")
            addStructureHint("Casing or compatible hatch", HintColor.GREEN)
            toolTipFinisher()
        }
    }

    override fun getRecipeMap(): GT_Recipe_Map {
        return GT_RecipeMaps.sMESystemProvider
    }

    override fun checkRecipe(recipeBuilder: MultiBlockRecipeBuilder<*>?, indexBus: Int): Boolean {
        return recipeBuilder!!
            .checkSizeHatches(needCheckFluid = false, needCheckItems = true, indexBus = indexBus)
            .checkVoltage()
            .checkRecipeMap(indexBus = indexBus)
            .checkInputEquals(indexBus = indexBus, enabledChance = false) { recipe ->
                mMatrixParticlesSummary - recipe.mSpecialValue >= 0
            }
            .checkEfficiency()
            .checkConsumption()
            .checkOutputs(default = true, indexBus = indexBus) { recipe ->
                mMatrixParticlesSummary -= recipe.mSpecialValue
            }
            .build()
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

    override fun hasSeparate(): Boolean {
        return false
    }

    override fun hasShowConnect(): Boolean {
        return false
    }
}
