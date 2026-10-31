package com.impact.mods.gregtech.tileentities.multi.ores

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.blocks.ImpactHatchElement
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.multis.WorldProperties.chunk
import gregtech.api.enums.Casings
import gregtech.api.enums.HatchElement
import gregtech.api.enums.Materials
import gregtech.api.interfaces.ICasing
import gregtech.api.interfaces.IHatchElement
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.render.TextureFactory
import gregtech.api.util.GTStructureUtility
import gregtech.api.util.GTStructureUtility.ofFrame
import gregtech.api.util.MultiblockTooltipBuilder
import gregtech.api.util.StructureRequirements
import kotlin.math.min
import net.minecraft.item.ItemStack
import net.minecraft.util.StatCollector
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fluids.FluidStack
import space.impact.structures.hint.HintColor
import space.impact.structures.structure.IStructureDefinition
import space.impact.structures.structure.ISurvivalBuildEnvironment
import space.impact.structures.structure.StructureDefinition
import space.impact.structures.structure.StructureUtility.isAir
import space.impact.structures.structure.StructureUtility.ofBlock
import space.impact.structures.structure.StructureUtility.ofChain
import space.impact.structures.structure.StructureUtility.withHint

class GTMTEOilDrilling : GTMTE_Impact_BlockBase<GTMTEOilDrilling> {

    companion object {

        private val CASING: ICasing = Casings.SOLID_STEEL
        private val STANDARD_HATCHES: Array<IHatchElement> = arrayOf(
            HatchElement.Maintenance,
            HatchElement.Input,
            HatchElement.Output,
            HatchElement.Energy,
            ImpactHatchElement.Dynamo,
            ImpactHatchElement.Muffler,
            ImpactHatchElement.LaserInput,
            ImpactHatchElement.LaserOutput,
        )

        private val SERVICE_HATCH: IHatchElement = object : IHatchElement {

            override fun matches(tile: IMetaTileEntity?): Boolean {
                return STANDARD_HATCHES.any {
                    it.matches(tile)
                }
            }

            override fun count(machine: GT_MetaTileEntity_MultiBlockBase): Int {
                return (machine as? GTMTEOilDrilling)?.serviceHatchCount ?: 0
            }

            override fun add(
                machine: GT_MetaTileEntity_MultiBlockBase,
                tile: IGregTechTileEntity,
                textureIndex: Int,
            ): Boolean {
                val controller = machine as? GTMTEOilDrilling ?: return false
                if (!controller.addToMachineList(
                        tile,
                        textureIndex,
                    )
                ) {
                    return false
                }
                controller.serviceHatchCount++
                return true
            }

            override fun getDescription(): String {
                return StatCollector.translateToLocal("impact.structure.hatch.drilling_service")
            }
        }

        private val MIX_HATCH: IHatchElement = object : IHatchElement {

            override fun matches(tile: IMetaTileEntity?): Boolean {
                return tile is GT_MetaTileEntity_Hatch_Input || tile is GT_MetaTileEntity_Hatch_Output
            }

            override fun count(machine: GT_MetaTileEntity_MultiBlockBase): Int {
                return (machine as? GTMTEOilDrilling)?.mixHatchCount ?: 0
            }

            override fun add(
                machine: GT_MetaTileEntity_MultiBlockBase,
                tile: IGregTechTileEntity,
                textureIndex: Int,
            ): Boolean {
                val controller = machine as? GTMTEOilDrilling ?: return false
                val hatch = tile.metaTileEntity
                when (hatch) {
                    is GT_MetaTileEntity_Hatch_Input -> {
                        hatch.updateTexture(textureIndex)
                        controller.mixInHatch = hatch
                    }
                    is GT_MetaTileEntity_Hatch_Output -> {
                        hatch.updateTexture(textureIndex)
                        controller.mixOutHatch = hatch
                    }
                    else -> {
                        return false
                    }
                }
                controller.mixHatchCount++
                return true
            }

            override fun getDescription(): String {
                return StatCollector.translateToLocal("impact.structure.hatch.drilling_mix")
            }
        }

        private val SHELL: StructureRequirements = StructureRequirements.builder(
            CASING,
            0,
        ).build()
        private val SERVICE_POSITIONS: StructureRequirements = StructureRequirements.builder(
            CASING,
            0,
        ).atLeast(
            SERVICE_HATCH,
            2,
        ).build()
        private val MIX_POSITIONS: StructureRequirements = StructureRequirements.builder(
            CASING,
            0,
        ).atLeast(
            MIX_HATCH,
            2,
        ).build()

        private val DEFINITION: IStructureDefinition<GTMTEOilDrilling> by lazy {
            StructureDefinition.builder<GTMTEOilDrilling>()
                .addShape(
                    "main",
                    arrayOf(
                        arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " FA AF ", " FA~AF ", " F   F ", " F   F "),
                        arrayOf("       ", "       ", "       ", "  F F  ", " FAAAF ", " F   F ", " F   F ", " F   F ", "FAAAAAF", "FAAAAAF", "F     F", "F     F"),
                        arrayOf("  FAF  ", "  F F  ", "  F F  ", " FF FF ", " AAAAA ", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA", "       ", "       "),
                        arrayOf("  AAA  ", "   A   ", "   E   ", "   G   ", " AAGAA ", "   G   ", "   G   ", "   G   ", "DAAGAAB", "AAAGAAA", "   G   ", "   G   "),
                        arrayOf("  FAF  ", "  F F  ", "  F F  ", " FF FF ", " AAAAA ", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA", "       ", "       "),
                        arrayOf("       ", "       ", "       ", "  F F  ", " FAAAF ", " F   F ", " F   F ", " F   F ", "FAAAAAF", "FAAAAAF", "F     F", "F     F"),
                        arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " FACAF ", " FAAAF ", " F   F ", " F   F "),
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
                        GTStructureUtility.buildHatchAdder(GTMTEOilDrilling::class.java)
                            .allow(*STANDARD_HATCHES)
                            .buildAndChain(SHELL),
                        HintColor.ORANGE,
                    ),
                )
                .addElement(
                    'B',
                    GTStructureUtility.buildHatchAdder(GTMTEOilDrilling::class.java)
                        .hint(HintColor.BLACK)
                        .preferForPlacement {
                            it is GT_MetaTileEntity_Hatch_Output
                        }
                        .build(SERVICE_POSITIONS),
                )
                .addElement(
                    'C',
                    GTStructureUtility.buildHatchAdder(GTMTEOilDrilling::class.java)
                        .hint(HintColor.RED)
                        .preferForPlacement {
                            it is GT_MetaTileEntity_Hatch_Input
                        }
                        .build(MIX_POSITIONS),
                )
                .addElement(
                    'D',
                    GTStructureUtility.buildHatchAdder(GTMTEOilDrilling::class.java)
                        .hint(HintColor.BLUE)
                        .preferForPlacement {
                            it is GT_MetaTileEntity_Hatch_Input
                        }
                        .build(SERVICE_POSITIONS),
                )
                .addElement(
                    'E',
                    GTStructureUtility.buildHatchAdder(GTMTEOilDrilling::class.java)
                        .hint(HintColor.GREEN)
                        .preferForPlacement {
                            it is GT_MetaTileEntity_Hatch_Output
                        }
                        .build(MIX_POSITIONS),
                )
                .addElement(
                    'F',
                    ofFrame(Materials.Steel),
                )
                .addElement(
                    'G',
                    ofChain(
                        isAir(),
                        ofBlock(
                            ItemRegistery.CollisionBlock,
                            0,
                        ),
                    ),
                )
                .build()
        }
    }

    private var mixInHatch: GT_MetaTileEntity_Hatch_Input? = null
    private var mixOutHatch: GT_MetaTileEntity_Hatch_Output? = null

    private var serviceHatchCount: Int = 0
    private var mixHatchCount: Int = 0

    private var boostCoefficient: Int = 1
    private var currentChunk: Chunk? = null

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.fluid.oil_drilling", aNameRegional)
    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = GTMTEOilDrilling(mName)

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
                Texture.Icons.OVERLAY_OIL_DRILL_ACTIVE
            } else {
                Texture.Icons.OVERLAY_OIL_DRILL
            }
            arrayOf(
                CASING.texture,
                TextureFactory.of(overlay),
            )
        } else {
            arrayOf(CASING.texture)
        }
    }

    public override fun construct(
        stack: ItemStack,
        hintOnly: Boolean,
    ) {
        buildPiece(
            stack,
            hintOnly,
            3,
            9,
            0,
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
            3,
            9,
            0,
            budget,
            environment,
        )
    }






    override fun getPollutionPerTick(aStack: ItemStack?): Int {
        return 25
    }

    public override fun clearHatches() {
        super.clearHatches()
        serviceHatchCount = 0
        mixHatchCount = 0
        mixInHatch = null
        mixOutHatch = null
    }

    public override fun machineStructure(thisController: IGregTechTileEntity): Boolean {

        val size = thisController.chunk.chunkTileEntityMap.values
            .count { it is IGregTechTileEntity && it.metaTileEntity is GTMTEOilDrilling }

        if (size > 1) return false

        val isBuild = checkPiece(
            3,
            9,
            0,
        )
        boostCoefficient = min(
            tierEnergyHatch,
            14,
        ) * 2
        return isBuild && hasRequireHatches(
            energy = 1,
            maintenance = 1,
            outputHatch = 4,
            inputHatch = 2,
            muffler = 1
        )
    }

    public override fun createTooltip(): MultiblockTooltipBuilder {
        return MultiblockTooltipBuilder(mName).apply {
            addMachineType("Underground Pump")
                .addController("See the structure hologram")
                .addInfo("Extracts underground fluids from a current vein")
                .addInfo("There can only be one machine in a Chunk")
                .addInfo("For operation of the machine, it is required to connect Input Hatch and Output Hatch by pipe to distill Dirty Fluid")
                .addStructureHint("Input Hatch for Fluid Dirty Mix", HintColor.RED)
                .addStructureHint("Output Hatch for Fluid Dirty Mix", HintColor.GREEN)
                .addStructureHint("Input Hatch for Water", HintColor.BLUE)
                .addStructureHint("Output Hatch for Oil/Gas/Others", HintColor.BLACK)
                .addOtherStructurePart("Steel Frame Box", "At least 72")
                .addOtherStructurePart("Solid Steel Machine Casing", "At least 84")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 2")
                .addStructureHint("Casing or compatible hatch", HintColor.ORANGE)
                .toolTipFinisher()
        }
    }

    public override fun getStructureDefinition(): IStructureDefinition<GTMTEOilDrilling> {
        return DEFINITION
    }

    override fun checkRecipe(itemStack: ItemStack?): Boolean {
        if (mixInHatch == null && mixOutHatch == null) return false
        mEfficiency = getCurrentEfficiency(null)
        mEfficiencyIncrease = 10000
        val tier = min(tierEnergyHatch, 14)
        mEUt = 3 * (2 shl (tier shl 1))
        if (mEUt > 0) mEUt = -mEUt
        mProgresstime = 0
        mMaxProgresstime = 20 * 20
        return true
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        currentChunk = te.chunk
    }

    override fun onRemoval() {
        baseMetaTileEntity?.also { te ->
            te.isActive = false
            createDrill(te)
            mMufflerHatches.forEach { it.baseMetaTileEntity.isActive = te.isActive }
        }
        super.onRemoval()
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
//        if (te.isServerSide) {
//            if (te.isActive && tick % 20 == 0L) currentChunk?.also(::runningLogic)
//            if (tick % 100 == 0L) createDrill(te)
//        }
    }

    private fun createDrill(drilling: IGregTechTileEntity) {
        mixOutHatch?.also {
            val te = it.baseMetaTileEntity
            if (drilling.isActive) {
                for (dist in 1..255) {
                    if (te.getAirAtSideAndDistance(0.toByte(), dist)) {
                        te.world.setBlock(te.xCoord, te.yCoord - dist, te.zCoord, ItemRegistery.CollisionBlock, 0, 3)
                    } else if (te.getBlockAtSideAndDistance(0.toByte(), dist) != ItemRegistery.CollisionBlock) {
                        break
                    }
                }
            } else {
                for (dist in 1..255) {
                    if (te.getBlockAtSideAndDistance(0.toByte(), dist) == ItemRegistery.CollisionBlock) {
                        te.world.setBlockToAir(te.xCoord, te.yCoord - dist, te.zCoord)
                    } else break
                }
            }
        }
    }

//    private fun runningLogic(chunk: Chunk) {
//        val vein = VirtualAPI.extractFluidFromVein(chunk, 1)
//        if (vein != null && vein.size > 0) {
//            val waterConsume = (boostCoefficient shr 1) * when (vein.typeVein) {
//                TypeFluidVein.LP -> Config.countWaterForLPDrill
//                TypeFluidVein.MP -> Config.countWaterForMPDrill
//                TypeFluidVein.HP -> Config.countWaterForHPDrill
//            }
//            var outputOil = 0L
//            mixOutHatch?.also { mixOut ->
//                val isPressured = depleteInput(Materials.Water.getFluid(waterConsume.toLong()))
//                if (!isPressured) stopMachine()
//                val tier = boostCoefficient / 2
//                outputOil = ((boostCoefficient shl 6) * (tier shl 2) * (log(max(2.0, tier.toDouble()), 2.0).pow(.5))).toLong()
//                addOutputMix(mixOut, Materials.MixDirtOil.getFluid(outputOil))
//                val countWaterOutput = (waterConsume * .4).toLong()
//                addOutput(Materials.Water.getFluid(countWaterOutput))
//            }
//            mixInHatch?.also { mixIn ->
//                if (outputOil > 0) {
//                    val currentOil = mixIn.fluid
//                    var tLiquid = currentOil
//                    if (tLiquid != null && tLiquid.isFluidEqual(currentOil)) {
//                        tLiquid = mixIn.drain(currentOil.amount, false)
//                        if (tLiquid.amount >= currentOil.amount) mixIn.drain(currentOil.amount, true)
//                    }
//                    val countOilOutput = outputOil * Random.nextDouble(.4, .8)
//                    FluidStack(vein.vein.fluid, countOilOutput.toInt()).also(::addOutput)
//                }
//            }
//        } else stopMachine()
//    }

    private fun addOutputMix(output: GT_MetaTileEntity_Hatch_Output, fluid: FluidStack) {
        val copiedFluidStack: FluidStack = fluid.copy()
        output.dumpFluid(copiedFluidStack)
    }

    private fun GT_MetaTileEntity_Hatch_Output.dumpFluid(fluid: FluidStack): Boolean {
        val tAmount: Int = fill(fluid, false)
        if (tAmount >= fluid.amount) {
            val filled = fill(fluid, true) >= fluid.amount
            onEmptyingContainerWhenEmpty()
            return filled
        } else if (tAmount > 0) {
            fluid.amount -= fill(fluid, true)
            onEmptyingContainerWhenEmpty()
        }
        return false
    }

    override fun hasSeparate(): Boolean {
        return false
    }

    override fun hasIndicator(): Boolean {
        return true
    }
}
