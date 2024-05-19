package com.impact.mods.gregtech.tileentities.multi.ores

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.multis.GT_StructureUtility.ofFrame
import com.impact.util.multis.GT_StructureUtility.ofHatchAdder
import com.impact.util.multis.WorldProperties.chunk
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.GregTech_API
import gregtech.api.enums.Materials
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output
import gregtech.api.render.TextureFactory
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fluids.FluidStack
import space.gtimpact.virtual_world.api.TypeFluidVein
import space.gtimpact.virtual_world.api.VirtualAPI
import space.gtimpact.virtual_world.config.Config
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility.*
import kotlin.math.log
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.random.Random

class GTMTEOilDrilling : GTMTE_Impact_BlockBase<GTMTEOilDrilling> {

    companion object {
        private val INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16]
        private val DEFINITION = StructureDefinition.builder<GTMTEOilDrilling>()
            .addShape(
                "main", arrayOf(
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " FA AF ", " FA~AF ", " F   F ", " F   F "),
                    arrayOf("       ", "       ", "       ", "  F F  ", " FAAAF ", " F   F ", " F   F ", " F   F ", "FAAAAAF", "FAAAAAF", "F     F", "F     F"),
                    arrayOf("  FAF  ", "  F F  ", "  F F  ", " FF FF ", " AAAAA ", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA", "       ", "       "),
                    arrayOf("  AAA  ", "   A   ", "   E   ", "   G   ", " AAGAA ", "   G   ", "   G   ", "   G   ", "DAAGAAB", "AAAGAAA", "   G   ", "   G   "),
                    arrayOf("  FAF  ", "  F F  ", "  F F  ", " FF FF ", " AAAAA ", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA", "       ", "       "),
                    arrayOf("       ", "       ", "       ", "  F F  ", " FAAAF ", " F   F ", " F   F ", " F   F ", "FAAAAAF", "FAAAAAF", "F     F", "F     F"),
                    arrayOf("       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " FACAF ", " FAAAF ", " F   F ", " F   F "),
                )
            )
            .addElement('A', ofChain(ofBlock(GregTech_API.sBlockCasings2, 0), ofHatchAdder(GTMTEOilDrilling::checkHatch, 16, ImpactAPI.GRAY)))
            .addElement('B', ofHatchAdder(GTMTEOilDrilling::checkHatch, 16, ImpactAPI.BLACK)) //Output Oil
            .addElement('C', ofHatchAdder(GTMTEOilDrilling::checkMixHatch, 16, ImpactAPI.RED)) //Input Oil Dirt Mix
            .addElement('D', ofHatchAdder(GTMTEOilDrilling::checkHatch, 16, ImpactAPI.BLUE)) //Input Water
            .addElement('E', ofHatchAdder(GTMTEOilDrilling::checkMixHatch, 16, ImpactAPI.GREEN)) // Output Oil Dirt Mix
            .addElement('F', lazy { _ -> ofFrame(Materials.Steel) })
            .addElement('G', ofChain(ofBlock(Blocks.air, 0), ofBlock(ItemRegistery.CollisionBlock, 0)))
            .build()
    }

    private var mixInHatch: GT_MetaTileEntity_Hatch_Input? = null
    private var mixOutHatch: GT_MetaTileEntity_Hatch_Output? = null

    private var boostCoefficient: Int = 1
    private var currentChunk: Chunk? = null

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.fluid.oil_drilling", aNameRegional)
    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?): IMetaTileEntity = GTMTEOilDrilling(mName)

    override fun getTexture(aBaseMetaTileEntity: IGregTechTileEntity?, aSide: Byte, aFacing: Byte, aColorIndex: Byte, aActive: Boolean, aRedstone: Boolean): Array<ITexture> {
        return if (aSide == aFacing)
            arrayOf(INDEX_CASE, TextureFactory.of(if (aActive) Texture.Icons.OVERLAY_OIL_DRILL_ACTIVE else Texture.Icons.OVERLAY_OIL_DRILL))
        else
            arrayOf(INDEX_CASE)
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 3, 9, 0)
    }

    private fun checkMixHatch(te: IGregTechTileEntity?, index: Short): Boolean {
        val mte = te?.metaTileEntity ?: return false
        val color = index.toInt()
        return when (mte) {
            is GT_MetaTileEntity_Hatch_Input -> {
                mte.updateTexture(color)
                mixInHatch = mte
                true
            }

            is GT_MetaTileEntity_Hatch_Output -> {
                mte.updateTexture(color)
                mixOutHatch = mte
                true
            }

            else -> false
        }
    }

    private fun checkHatch(te: IGregTechTileEntity?, index: Short): Boolean {
        val color = index.toInt()
        return addToMachineList(te, color)
    }

    override fun getPollutionPerTick(aStack: ItemStack?): Int {
        return 25
    }

    override fun machineStructure(thisController: IGregTechTileEntity): Boolean {

        val size = thisController.chunk.chunkTileEntityMap.values
            .count { it is IGregTechTileEntity && it.metaTileEntity is GTMTEOilDrilling }

        if (size > 1) return false

        val isBuild = checkPiece(3, 9, 0)
        boostCoefficient = min(tierEnergyHatch, 14) * 2
        return isBuild && hasRequireHatches(
            energy = 1,
            maintenance = 1,
            outputHatch = 4,
            inputHatch = 2,
            muffler = 1
        )
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("oil_drilling_machine").apply {
            addTypeMachine("name", "Underground Pump")
                .addController()
                .addInfo("info.0", "Extracts underground fluids from a current vein")
                .addInfo("info.1", "There can only be one machine in a Chunk")
                .addInfo("info.2", "For operation of the machine, it is required to connect Input Hatch and Output Hatch by pipe to distill Dirty Fluid")
                .addRedHint("Input Hatch for Fluid Dirty Mix")
                .addGreenHint("Output Hatch for Fluid Dirty Mix")
                .addBlueHint("Input Hatch for Water")
                .addBlackHint("Output Hatch for Oil/Gas/Others")
                .addOtherStructurePartAny("structure.1", "Steel Frame Box", 72)
                .addOtherStructurePartAny("structure.2", "Solid Steel Machine Casing", 84)
                .addEnergyHatch(1)
                .addMaintenanceHatch()
                .addMuffler()
                .addOutputHatch(4)
                .addInputHatch(2)
                .signAndFinalize()
        }
    }

    override fun getStructureDefinition(): IStructureDefinition<GTMTEOilDrilling> = DEFINITION

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
        if (te.isServerSide) {
            if (te.isActive && tick % 20 == 0L) currentChunk?.also(::runningLogic)
            if (tick % 100 == 0L) createDrill(te)
        }
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

    private fun runningLogic(chunk: Chunk) {
        val vein = VirtualAPI.extractFluidFromVein(chunk, 1)
        if (vein != null && vein.size > 0) {
            val waterConsume = (boostCoefficient shr 1) * when (vein.typeVein) {
                TypeFluidVein.LP -> Config.countWaterForLPDrill
                TypeFluidVein.MP -> Config.countWaterForMPDrill
                TypeFluidVein.HP -> Config.countWaterForHPDrill
            }
            var outputOil = 0L
            mixOutHatch?.also { mixOut ->
                val isPressured = depleteInput(Materials.Water.getFluid(waterConsume.toLong()))
                if (!isPressured) stopMachine()
                val tier = boostCoefficient / 2
                outputOil = ((boostCoefficient shl 6) * (tier shl 2) * (log(max(2.0, tier.toDouble()), 2.0).pow(.5))).toLong()
                addOutputMix(mixOut, Materials.MixDirtOil.getFluid(outputOil))
                val countWaterOutput = (waterConsume * .4).toLong()
                addOutput(Materials.Water.getFluid(countWaterOutput))
            }
            mixInHatch?.also { mixIn ->
                if (outputOil > 0) {
                    val currentOil = mixIn.fluid
                    var tLiquid = currentOil
                    if (tLiquid != null && tLiquid.isFluidEqual(currentOil)) {
                        tLiquid = mixIn.drain(currentOil.amount, false)
                        if (tLiquid.amount >= currentOil.amount) mixIn.drain(currentOil.amount, true)
                    }
                    val countOilOutput = outputOil * Random.nextDouble(.4, .8)
                    FluidStack(vein.vein.fluid, countOilOutput.toInt()).also(::addOutput)
                }
            }
        } else stopMachine()
    }

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