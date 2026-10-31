package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import gregtech.api.enums.Casings;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.*;
import static gregtech.api.GregTech_API.*;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.GT_Values.VN;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_BlastSmelter
        extends GT_MetaTileEntity_EnhancedMultiBlockBase<GTMTE_BlastSmelter>
        implements ISurvivalConstructable {
    private static final ICasing CASING = Casings.HSLA;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_BlastSmelter> STRUCTURE;
    private HeatingCoil checkedCoil;

    @Override
    public IStructureDefinition<GTMTE_BlastSmelter> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition
                    .<GTMTE_BlastSmelter>builder()
                    .addShape("main", new String[][] {
                            { " 000 ", " 111 ", " 111 ", " 0~0 ", },
                            { "00000", "1   1", "1   1", "00000", },
                            { "00000", "1   1", "1   1", "00000", },
                            { "00000", "1   1", "1   1", "00000", },
                            { " 000 ", " 111 ", " 111 ", " 000 ", },

                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('0', withHint(GTStructureUtility.buildHatchAdder(GTMTE_BlastSmelter.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.Muffler)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('1', GTStructureUtility.ofHeatingCoil(
                            GTMTE_BlastSmelter::checkCoil, GTMTE_BlastSmelter::constructionCoil))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 2, 3, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 3, 0, budget, environment);
    }

    private int mHeatingCapacity = 0;
    private GT_Recipe cashedRecipe = null;

    public GTMTE_BlastSmelter(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.blastsmelter", aNameRegional);
    }

    public GTMTE_BlastSmelter(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_BlastSmelter(this.mName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Blast Smelter")
                .addPollutionAmount(20 * getPollutionPerTick(null))
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 4")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 5")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 5")
                .addRequirements(SHELL, "Top and bottom layers")
                .addOtherStructurePart("Heating Coils", "Two middle Layers, hollow")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { CASING.getTexture(),
                    TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE_ACTIVE
                            : Textures.BlockIcons.OVERLAY_FRONT_ELECTRIC_BLAST_FURNACE) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "ElectricBlastFurnace.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBlastSmelterRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    public boolean checkRecipe(ItemStack aStack) {
        for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
            ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
            tBus.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tBus)) {
                for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null) {
                        tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
                    }
                }
            }
            ArrayList<ItemStack> tInputList = getStoredInputs();
            int tInputList_sS = tInputList.size();
            for (int i = 0; i < tInputList_sS - 1; i++) {
                for (int j = i + 1; j < tInputList_sS; j++) {
                    if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
                        if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
                            tInputList.remove(j--);
                            tInputList_sS = tInputList.size();
                        } else {
                            tInputList.remove(i--);
                            tInputList_sS = tInputList.size();
                            break;
                        }
                    }
                }
            }
            ItemStack[] tInputs = tBusItems.toArray(new ItemStack[] {});
            ArrayList<FluidStack> tFluidList = getStoredFluids();
            int tFluidList_sS = tFluidList.size();
            for (int i = 0; i < tFluidList_sS - 1; i++) {
                for (int j = i + 1; j < tFluidList_sS; j++) {
                    if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
                        if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
                            tFluidList.remove(j--);
                            tFluidList_sS = tFluidList.size();
                        } else {
                            tFluidList.remove(i--);
                            tFluidList_sS = tFluidList.size();
                            break;
                        }
                    }
                }
            }
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[0]);
            if (tInputList.size() > 0) {
                long tVoltage = getMaxInputVoltage();
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
                GT_Recipe tRecipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), cashedRecipe, false,
                        GT_Values.V[tTier], tFluids, tInputs);
                if ((tRecipe != null) && (this.mHeatingCapacity >= tRecipe.mSpecialValue)
                        && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                    cashedRecipe = tRecipe;
                    this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                    this.mEfficiencyIncrease = 10000;
                    int tHeatCapacityDivTiers = (mHeatingCapacity - tRecipe.mSpecialValue) / 900;
                    byte overclockCount = calculateOverclockednessEBF(tRecipe.mEUt, tRecipe.mDuration, tVoltage);
                    // In case recipe is too OP for that machine
                    if (mMaxProgresstime == Integer.MAX_VALUE - 1 && mEUt == Integer.MAX_VALUE - 1) {
                        return false;
                    }
                    if (this.mEUt > 0) {
                        this.mEUt = (-this.mEUt);
                    }
                    if (tHeatCapacityDivTiers > 0) {
                        this.mEUt = (int) (this.mEUt * (Math.pow(0.95, tHeatCapacityDivTiers)));
                        this.mMaxProgresstime >>= Math.min(tHeatCapacityDivTiers / 2, overclockCount);// extra free
                                                                                                      // overclocking if
                                                                                                      // possible
                        if (this.mMaxProgresstime < 1) {
                            this.mMaxProgresstime = 1;// no eu efficiency correction
                        }
                    }
                    this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                    this.mOutputItems = new ItemStack[] { tRecipe.getOutput(0), tRecipe.getOutput(1) };
                    this.mOutputFluids = new FluidStack[] { tRecipe.getFluidOutput(0) };
                    updateSlots();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calcualtes overclocked ness using long integers
     *
     * @param aEUt      - recipe EUt
     * @param aDuration - recipe Duration
     */
    protected byte calculateOverclockednessEBF(int aEUt, int aDuration, long maxInputVoltage) {
        byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage)), timesOverclocked = 0;
        if (mTier == 0) {
            // Long time calculation
            long xMaxProgresstime = ((long) aDuration) << 1;
            if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
                // make impossible if too long
                mEUt = Integer.MAX_VALUE - 1;
                mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                mEUt = aEUt >> 2;
                mMaxProgresstime = (int) xMaxProgresstime;
            }
            // return 0;
        } else {
            // Long EUt calculation
            long xEUt = aEUt;
            // Isnt too low EUt check?
            long tempEUt = Math.max(xEUt, V[1]);

            mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier - 1]) {
                tempEUt <<= 2;// this actually controls overclocking
                // xEUt *= 4;//this is effect of everclocking
                mMaxProgresstime >>= 1;// this is effect of overclocking
                xEUt = mMaxProgresstime == 0 ? xEUt >> 1 : xEUt << 2;// U know, if the time is less than 1 tick make the
                                                                     // machine use less power
                timesOverclocked++;
            }
            if (xEUt > Integer.MAX_VALUE - 1) {
                mEUt = Integer.MAX_VALUE - 1;
                mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                mEUt = (int) xEUt;
                if (mEUt == 0) {
                    mEUt = 1;
                }
                if (mMaxProgresstime == 0) {
                    mMaxProgresstime = 1;// set time to 1 tick
                }
            }
        }
        return timesOverclocked;
    }

    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        checkedCoil = null;
        mHeatingCapacity = 0;
        boolean formationChecklist = checkPiece("main", 2, 3, 0) && SHELL.isSatisfied(this, 0);
        if (checkedCoil != null) {
            mHeatingCapacity = checkedCoil.getHeatCapacity();
        }

        if (this.mInputBusses.size() > 5) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 5) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 4) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        this.mHeatingCapacity += 100 * (GT_Utility.getTier(getMaxInputVoltage()) - 2);
        return formationChecklist;
    }

    private boolean checkCoil(HeatingCoil coil) {
        if (checkedCoil == null) {
            checkedCoil = coil;
        }
        return checkedCoil == coil;
    }

    private HeatingCoil constructionCoil(ItemStack trigger) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        HeatingCoil existing = HeatingCoil.at(base.getWorld(), base.getXCoord(), base.getYCoord() + 2,
                base.getZCoord());
        return existing != null ? existing : HeatingCoil.fromTier(trigger == null ? 1 : trigger.stackSize);
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 200;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    @Override
    public String[] getInfoData() {
        int mPollutionReduction = 0;
        for (GT_MetaTileEntity_Hatch_Muffler tHatch : mMufflerHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                mPollutionReduction = Math.max(tHatch.calculatePollutionReduction(100), mPollutionReduction);
            }
        }

        long storedEnergy = 0;
        long maxEnergy = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }

        return new String[] {
                StatCollector.translateToLocal("GT5U.multiblock.Progress") + ": " + EnumChatFormatting.GREEN
                        + mProgresstime / 20 + EnumChatFormatting.RESET + " s / " + EnumChatFormatting.YELLOW
                        + mMaxProgresstime / 20 + EnumChatFormatting.RESET + " s",
                StatCollector.translateToLocal("GT5U.multiblock.energy") + ": " + EnumChatFormatting.GREEN
                        + storedEnergy + EnumChatFormatting.RESET + " EU / " + EnumChatFormatting.YELLOW + maxEnergy
                        + EnumChatFormatting.RESET + " EU",
                StatCollector.translateToLocal("GT5U.multiblock.usage") + ": " + EnumChatFormatting.RED + -mEUt
                        + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.multiblock.mei") + ": " + EnumChatFormatting.YELLOW
                        + getMaxInputVoltage() + EnumChatFormatting.RESET + " EU/t(*2A) "
                        + StatCollector.translateToLocal("GT5U.machines.tier") + ": " + EnumChatFormatting.YELLOW
                        + VN[GT_Utility.getTier(getMaxInputVoltage())] + EnumChatFormatting.RESET,
                StatCollector.translateToLocal("GT5U.multiblock.problems") + ": " + EnumChatFormatting.RED
                        + (getIdealStatus() - getRepairStatus()) + EnumChatFormatting.RESET + " "
                        + StatCollector.translateToLocal("GT5U.multiblock.efficiency") + ": "
                        + EnumChatFormatting.YELLOW + mEfficiency / 100.0F + EnumChatFormatting.RESET + " %",
                StatCollector.translateToLocal("GT5U.EBF.heat") + ": " + EnumChatFormatting.GREEN + mHeatingCapacity
                        + EnumChatFormatting.RESET + " K",
                StatCollector.translateToLocal("GT5U.multiblock.pollution") + ": " + EnumChatFormatting.GREEN
                        + mPollutionReduction + EnumChatFormatting.RESET + " %"
        };
    }
}
