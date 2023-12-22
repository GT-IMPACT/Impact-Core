package com.impact.mods.gregtech.tileentities.multi.ores

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.enums.Texture
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.multis.GT_StructureUtility.ofFrame
import com.impact.util.multis.GT_StructureUtility.ofHatchAdder
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
import gregtech.api.util.GT_Utility.getTier
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.world.chunk.Chunk
import net.minecraftforge.fluids.FluidStack
import space.gtimpact.virtual_world.api.VirtualAPI.extractFluidFromChunk
import space.impact.api.ImpactAPI
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility.*
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
            .addElement('B', ofChain(ofBlock(Blocks.air, 0), ofHatchAdder(GTMTEOilDrilling::checkHatch, 16, ImpactAPI.BLACK))) //Optional Output Oil
            .addElement('C', ofHatchAdder(GTMTEOilDrilling::checkMixHatch, 16, ImpactAPI.RED)) //Input Oil Dirt Mix
            .addElement('D', ofChain(ofBlock(Blocks.air, 0), ofHatchAdder(GTMTEOilDrilling::checkHatch, 16, ImpactAPI.BLUE))) //Optional Input Water
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

    override fun machineStructure(thisController: IGregTechTileEntity?): Boolean {
        val isBuild = checkPiece(3, 9, 0)
        boostCoefficient = (getTier(maxInputVoltage) * 1.75).toInt()
        return isBuild && hasRequireHatches(
            energy = 2,
            maintenance = 1,
            outputHatch = 4,
            inputHatch = 2,
            muffler = 1
        )
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("oil_drilling_machine").apply {
            addTypeMachine("name", "Oil Drilling")
                .addSeparator()
                .addController()
                .addRedHint("Input Hatch for Oil Dirt Mix")
                .addGreenHint("Output Hatch for Oil Dirt Mix")
                .addEnergyHatch(2)
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
        mEUt = 0
        mProgresstime = 0
        mMaxProgresstime = 20 * 20
        return true
    }

    override fun onFirstTick(te: IGregTechTileEntity) {
        super.onFirstTick(te)
        init(te)
    }

    private fun init(te: IGregTechTileEntity) {
        currentChunk = te.world.getChunkFromBlockCoords(te.xCoord, te.zCoord)
    }

    override fun onPostTick(te: IGregTechTileEntity, tick: Long) {
        super.onPostTick(te, tick)
        if (te.isServerSide && tick % 100 == 0L) {
            if (te.isActive) currentChunk?.also(::runningLogic)
            createDrill(te)
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
        val (vein, reduceCoefficient) = extractFluidFromChunk(chunk)
        if (vein != null && reduceCoefficient > 0) {
            val waterConsume = 1000L * boostCoefficient / 2 // LV = 1000 * 0.5 = 500L
            var outputOil = 0L
            mixOutHatch?.also { mixOut ->
                val isBoost = depleteInput(Materials.Water.getFluid(waterConsume))
                val boost = boostCoefficient * if (isBoost) 2 else 1
                outputOil = boost * 100L / 2  // LV = 0.5 * 100 = 50 or 1 * 100 = 100L
                addOutputMix(mixOut, Materials.MixDirtOil.getFluid(outputOil))
                if (isBoost) {
                    val countWaterOutput = (waterConsume * if (mixInHatch != null) .3 else .6).toLong()
                    addOutput(Materials.Water.getFluid(countWaterOutput))
                }
            }
            mixInHatch?.also { mixIn ->
                if (outputOil > 0) {
                    val currentOil = mixIn.fluid
                    var tLiquid = currentOil
                    if (tLiquid != null && tLiquid.isFluidEqual(currentOil)) {
                        tLiquid = mixIn.drain(currentOil.amount, false)
                        if (tLiquid.amount >= currentOil.amount) mixIn.drain(currentOil.amount, true)
                    }
                    val countOilOutput = outputOil * Random.nextDouble(.3, 1.0)
                    FluidStack(vein.fluid, countOilOutput.toInt()).also(::addOutput)
                }
            }
        }
    }

    private fun addOutputMix(output: GT_MetaTileEntity_Hatch_Output, fluid: FluidStack) {
        val copiedFluidStack: FluidStack = fluid.copy()
        output.dumpFluid(copiedFluidStack)
    }

    private fun GT_MetaTileEntity_Hatch_Output.dumpFluid(fluid: FluidStack): Boolean {
        val tAmount: Int = fill(fluid, false)
        if (tAmount >= fluid.amount) {
            val filled: Boolean = fill(fluid, true) >= fluid.amount
            onEmptyingContainerWhenEmpty()
            return filled
        } else if (tAmount > 0) {
            fluid.amount = fluid.amount - fill(fluid, true)
            onEmptyingContainerWhenEmpty()
        }
        return false
    }
}