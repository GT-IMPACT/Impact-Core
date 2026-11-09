package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import gregtech.api.enums.Casings;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GTStructureChannels;
import gregtech.api.util.GT_Recipe;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.util.OverclockCalculator;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.enums.GT_Values.VN;

public class GTMTE_BlastSmelter
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<GTMTE_BlastSmelter>
        implements ISurvivalConstructable {
    private static final ICasing CASING = Casings.HSLA;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 21)
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
                    .addChannel(GTStructureChannels.COIL, 1, 10)
                    .addElement('0', GTStructureUtility.buildHatchAdder(GTMTE_BlastSmelter.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.Muffler)
                            .autoPlace(HatchElement.Input, HatchElement.Output, HatchElement.Energy, ImpactHatchElement.Muffler)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.GREEN))
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
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Blast Smelter")
                .addInfo("Heating capacity depends on the coils and total input voltage.")
                .addInfo("Each 900 K above the recipe requirement multiplies EU/t by 0.95.")
                .addInfo("Each 1800 K above the recipe requirement upgrades one available overclock.")
                .addInfo("Upgraded overclock: 4x EU/t, 1/4 recipe time.")
                .addInfo("Pollution: %s per second before muffler reduction.", getPollutionPerTick(null) * 20)
                .beginStructureBlock(5, 4, 5, false)
                .addController("front center of the bottom layer")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart("Heating Coils", "exactly 24 of the same tier; layers 2 and 3")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; top and bottom layers")
                .addHatchInfo("Energy Hatches", "optional, up to 4 total; top and bottom layers")
                .addHatchInfo("Input Buses", "optional, up to 5; top and bottom layers")
                .addHatchInfo("Input Hatches", "optional; top and bottom layers")
                .addHatchInfo("Output Buses", "optional; top and bottom layers")
                .addHatchInfo("Output Hatches", "optional, up to 5; top and bottom layers")
                .addHatchInfo("Muffler Hatch", "optional, up to 1; top and bottom layers")
                .addStructureHint("Top and bottom: casings or hatches/buses", HintColor.GREEN)
                .addSubChannel(GTStructureChannels.COIL, "1-10; default: stack size; existing coils take priority.")
                .addHatchChannelInfo()
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

    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()
                .setRecipeMapSupplier(this::getRecipeMap)
                .setItemOutputLimit(2)
                .setFluidOutputLimit(1)
                .setOutputChances(false)
                .setCalculatorFactory(recipe -> new OverclockCalculator()
                        .setHeat(recipe.mSpecialValue, mHeatingCapacity, true, true));
    }

    /**
     * Calcualtes overclocked ness using long integers
     *
     * @param aEUt      - recipe EUt
     * @param aDuration - recipe Duration
     */


    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        casingCount = 0;
        checkedCoil = null;
        mHeatingCapacity = 0;
        boolean formationChecklist = checkPiece("main", 2, 3, 0)
                && mMufflerHatches.size() <= 1 && SHELL.isSatisfied(this, casingCount);
        if (checkedCoil != null) {
            mHeatingCapacity = checkedCoil.getHeatCapacity();
        }

        if (HatchCounts.inputBuses(this) > 5) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 5) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 4) {
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
        return existing != null ? existing : HeatingCoil.fromTier(GTStructureChannels.getValue(trigger, GTStructureChannels.COIL));
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
