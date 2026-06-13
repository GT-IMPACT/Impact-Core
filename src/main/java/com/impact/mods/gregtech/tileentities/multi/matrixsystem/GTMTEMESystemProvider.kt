package com.impact.mods.gregtech.tileentities.multi.matrixsystem

import com.impact.loader.ItemRegistery
import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.blocks.Build_Casing_Helper
import com.impact.mods.gregtech.blocks.Casing_Helper
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase
import com.impact.mods.gregtech.gui.base.GUI_BASE
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
import com.impact.mods.gregtech.tileentities.multi.structure.RequiresHatches.hasRequireHatches
import com.impact.util.multis.GT_StructureUtility.ofHatchAdderOptional
import com.impact.util.multis.OverclockCalculate.calculateOverclockedNessBasic
import com.impact.util.string.MultiBlockTooltipBuilder
import gregtech.api.enums.GT_Values
import gregtech.api.enums.Textures
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.render.TextureFactory
import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import space.impact.api.multiblocks.structure.IStructureDefinition
import space.impact.api.multiblocks.structure.StructureDefinition
import space.impact.api.multiblocks.structure.StructureUtility
import kotlin.math.max

class GTMTEMESystemProvider : GT_MetaTileEntity_MultiParallelBlockBase<GTMTEMESystemProvider> {

    companion object {
        private val CASING_META: Int = Build_Casing_Helper.ME_CASING.meta
        private val INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32]
        private val CASING_TEXTURE_ID = Build_Casing_Helper.ME_CASING.idCasing
        private val DEFINITION = StructureDefinition.builder<GTMTEMESystemProvider>()
            .addShape(
                "main", arrayOf(
                    arrayOf("ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "),
                    arrayOf("ADDDA ", "A   AA", "A   A~", "AAAAAA"),
                    arrayOf("ADDDA ", "A   AA", "A   AA", "AAAAAA"),
                    arrayOf("ADDDA ", "ADDDA ", "ADDDA ", "AAAAA ")
                )
            )
            .addElement(
                'A', ofHatchAdderOptional(
                    GTMTEMESystemProvider::checkHatch,
                    CASING_TEXTURE_ID,
                    Casing_Helper.sCaseCore3,
                    CASING_META,
                    Casing_Helper.sCaseCore3,
                    CASING_META
                )
            )
            .addElement('D', StructureUtility.ofBlockAnyMeta(ItemRegistery.IGlassBlock))
            .build()
    }

    private val mpChambers = ArrayList<GTMTE_Hatch_MESystemMPChamber>()
    var mSpeedUp: Int = 0
    var mMatrixParticlesSummary: Int = 0

    constructor(aID: Int, aNameRegional: String) : super(aID, "impact.multis.mesystemprovider", aNameRegional)

    constructor(aName: String) : super(aName)

    override fun newMetaEntity(aTileEntity: IGregTechTileEntity?) = GTMTEMESystemProvider(mName)

    private fun checkHatch(te: IGregTechTileEntity?, index: Short): Boolean {
        val mte = te?.metaTileEntity as? GT_MetaTileEntity_Hatch ?: return false

        return if (mte is GTMTE_Hatch_MESystemMPChamber) {
            mte.updateTexture(index.toInt())
            mpChambers.add(mte)
        } else {
            addMaintenanceToMachineList(te, CASING_TEXTURE_ID) ||
                    addInputToMachineList(te, CASING_TEXTURE_ID) ||
                    addOutputToMachineList(te, CASING_TEXTURE_ID) ||
                    addEnergyInputToMachineList(te, CASING_TEXTURE_ID)
        }
    }

    override fun construct(stack: ItemStack, hintOnly: Boolean) {
        buildPiece(stack, hintOnly, 5, 2, 1)
    }

    override fun machineStructure(te: IGregTechTileEntity): Boolean {
        mpChambers.clear()
        mSpeedUp = 1
        var formationChecklist = checkPiece(5, 2, 1)

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

    override fun getStructureDefinition(): IStructureDefinition<GTMTEMESystemProvider> = DEFINITION

    override fun getTexture(
        aBaseMetaTileEntity: IGregTechTileEntity?,
        aSide: Byte,
        aFacing: Byte,
        aColorIndex: Byte,
        aActive: Boolean,
        aRedstone: Boolean
    ): Array<ITexture?> {
        return if (aSide == aFacing) {
            val texture = if (aActive) Textures.BlockIcons.MP1a else Textures.BlockIcons.MP1
            arrayOf(INDEX_CASE, TextureFactory.of(texture))
        } else {
            arrayOf(INDEX_CASE)
        }
    }

    override fun getClientGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any {
        return GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, localName)
    }

    override fun getServerGUI(aID: Int, aPlayerInventory: InventoryPlayer?, aBaseMetaTileEntity: IGregTechTileEntity?): Any {
        return GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity)
    }

    override fun createTooltip(): MultiBlockTooltipBuilder {
        return MultiBlockTooltipBuilder("me_system_provider").apply {
            addInfo("info.0", "Consume Stable Matrix Particles")
            addTypeMachine("name", "ME System Provider")
            addInfo("info.1", "Crafting ME Components")
            addSeparator()
            addController()
            addEnergyHatch(4)
            addMaintenanceHatch()
            addInputBus(2)
            addOutputBus(2)
            addCasingInfo("case", "ME Construction Casing")
            addOtherStructurePart("other.2", "Matrix Particles Chamber", "other.3", "Any Casing (max x1)")
            addOtherStructurePart("other.4", "I-Glass", "other.5", "Glass for structure")
            signAndFinalize()
        }
    }

    override fun getRecipeMap(): GT_Recipe_Map {
        return GT_RecipeMaps.sMESystemProvider
    }

    override fun checkRecipe(aStack: ItemStack?): Boolean {
        val tInputs: Array<ItemStack?>?

        val tInputList = this.getStoredInputs()
        tInputs = tInputList.toTypedArray<ItemStack?>()

        if (tInputList.isNotEmpty()) {
            val nominalV = getMaxInputVoltage()
            val tTier = max(1, GT_Utility.getTier(nominalV).toInt()).toByte()
            val tRecipe = recipeMap.findRecipe(baseMetaTileEntity, false, GT_Values.V[tTier.toInt()], null, *tInputs)
            if (tRecipe != null && (mMatrixParticlesSummary - tRecipe.mSpecialValue >= 0)) {
                val outputItems = java.util.ArrayList<ItemStack?>()
                var foundRecipe = false
                var processed = 0
                while ((this.getStoredFluids().size or this.getStoredInputs().size) > 0 && processed < 1) {
                    if ((tRecipe.mEUt * (processed + 1L)) < nominalV && tRecipe.isRecipeInputEqual(true, null, *tInputs)) {
                        foundRecipe = true

                        for (i in tRecipe.mOutputs.indices) {
                            outputItems.add(tRecipe.getOutput(i))
                        }
                        ++processed
                    } else {
                        break
                    }
                }
                if (foundRecipe) {
                    this.mEfficiency = (10000 - (this.idealStatus - this.repairStatus) * 1000)
                    this.mEfficiencyIncrease = 10000
                    val actualEUT = (tRecipe.mEUt).toLong() * processed
                    mMatrixParticlesSummary -= tRecipe.mSpecialValue * processed

                    calculateOverclockedNessBasic(actualEUT.toInt(), tRecipe.mDuration, 1, nominalV, this)

                    this.mMaxProgresstime /= this.mSpeedUp
                    if (this.mMaxProgresstime < 1) this.mMaxProgresstime = 1
                    if (this.mMaxProgresstime == Int.MAX_VALUE - 1 && this.mEUt == Int.MAX_VALUE - 1) return false
                    if (this.mEUt > 0) this.mEUt = (-this.mEUt)

                    this.mOutputItems = outputItems.toTypedArray<ItemStack?>()
                    this.updateSlots()
                    return true
                }
            }
        }
        return false
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
