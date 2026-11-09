package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.recipe.check.CheckRecipeResult;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.BiomeDictionary;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.enums.GT_Values.VN;
import static net.minecraftforge.common.BiomeDictionary.Type.OCEAN;
import static space.impact.structures.structure.StructureUtility.defer;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_DrillerWater extends GTMTE_DrillerBase<GTMTE_DrillerWater> {
    private static final ICasing CASING = Casings.SOLID_STEEL;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 16)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.RegularEnergy, ImpactHatchElement.FluidOutput)
            .build();
    private static IStructureDefinition<GTMTE_DrillerWater> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_DrillerWater> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_DrillerWater>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", "     ", "     ", "     ", "     ", "     ", "CC~CC", "F   F", "F   F" },
                            { "     ", "     ", "     ", " CCC ", " F F ", " F F ", "CCCCC", "     ", "     " },
                            { "  F  ", "  F  ", "  F  ", " CCC ", "     ", "     ", "CC CC", "     ", "     " },
                            { "     ", "     ", "     ", " CCC ", " F F ", " F F ", "CCCCC", "     ", "     " },
                            { "     ", "     ", "     ", "     ", "     ", "     ", "CCCCC", "F   F", "F   F" }
                    })
                    .addElement('C', defer(machine -> withHint(GTStructureUtility.buildHatchAdder(GTMTE_DrillerWater.class)
                            .allow(ImpactHatchElement.InputBus)
                            .autoPlace(ImpactHatchElement.InputBus)
                            .onlyAcceptIf(machine::acceptsPlacementTier)
                            .buildAndChain(SHELL, controller -> controller.casingCount++), HintColor.GREEN)))
                    .addElement('F', GTStructureUtility.ofFrame(Materials.Steel))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 2, 6, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 6, 0, budget, environment);
    }

    public GTMTE_DrillerWater(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.waterdrill", aNameRegional);
    }

    public GTMTE_DrillerWater(String aName) {
        super(aName);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Water Pump")
                .addInfo("Pumps water from bedrock; oceans produce Salt Water.")
                .addInfo("Each hatch tier doubles output and quadruples EU/t; biome coefficients affect output.")
                .addInfo("Mining pipes: controller or optional Input Bus.")
                .addControlInfo("Biome coefficients (later matches take priority):")
                .addControlInfo("Water: 1000")
                .addControlInfo("Coniferous: 175")
                .addControlInfo("Jungle: 350")
                .addControlInfo("Swamp: 800")
                .addControlInfo("Snowy: 300")
                .addControlInfo("Beach: 170")
                .addControlInfo("Plains or Forest: 250")
                .addControlInfo("Hills, Mountains, Savanna, Sandy or Mesa: 100")
                .addControlInfo("Ocean: 20; overrides all categories above")
                .beginStructureBlock(5, 9, 5, false)
                .addController("front center of layer 3")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1), "exactly 19; support frame and mast")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "1-4 standard, LV+; casing positions; same tier as Output Hatches")
                .addHatchInfo("Output Hatches", "1-4; casing positions")
                .addHatchInfo("Input Bus", "optional, up to 1; casing positions")
                .addStructureHint("Casings or hatches/buses", HintColor.GREEN)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_DrillerWater(mName);
    }


    @Override
    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        casingCount = 0;
        return super.checkMachine(controller, stack)
                && HatchCounts.energyHatches(this) <= 4
                && mMaintenanceHatches.size() <= 1
                && HatchCounts.inputBuses(this) <= 1
                && mOutputHatches.size() <= 4
                && haveMatchingHatchTiers()
                && SHELL.isSatisfied(this, casingCount);
    }

    private boolean acceptsPlacementTier(IMetaTileEntity hatch) {
        if (!(hatch instanceof GT_MetaTileEntity_Hatch_Energy)
                && !(hatch instanceof GT_MetaTileEntity_Hatch_Output)) {
            return true;
        }
        int tier = ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier;
        int existingTier = !mEnergyHatches.isEmpty() ? mEnergyHatches.get(0).mTier
                : !mOutputHatches.isEmpty() ? mOutputHatches.get(0).mTier : tier;
        return tier >= getMinTier() && tier == existingTier;
    }

    private boolean haveMatchingHatchTiers() {
        if (mEnergyHatches.isEmpty() || mOutputHatches.isEmpty()) {
            return false;
        }
        int tier = mEnergyHatches.get(0).mTier;
        return tier >= getMinTier()
                && mEnergyHatches.stream().allMatch(hatch -> hatch.mTier == tier)
                && mOutputHatches.stream().allMatch(hatch -> hatch.mTier == tier);
    }

    @Override
    protected boolean checkHatches() {
        return !mMaintenanceHatches.isEmpty() && !mOutputHatches.isEmpty() && !mEnergyHatches.isEmpty();
    }

    @Override
    protected long getDrillingEUt() {
        return Math.multiplyExact(7L, 1L << (Math.max(0, getTierEnergyHatch()) * 2));
    }

    @Override
    protected int getDrillingDuration() {
        return 20;
    }

    @Override
    protected CheckRecipeResult prepareAtBottom(DrillingPlan plan) {
        switch (plan.lowerPipe(true)) {
            case 0:
                plan.nextState = STATE_DOWNWARD;
                return CheckRecipeResult.SUCCESS;
            case 3:
                plan.nextState = STATE_UPWARD;
                return CheckRecipeResult.SUCCESS;
            default:
                break;
        }
        if (reachingVoidOrBedrock()) {
            long amount = Math.multiplyExact(getWaterOutputPerTick(), 20L);
            Math.toIntExact(amount);
            FluidStack water = BiomeDictionary.isBiomeOfType(getBaseMetaTileEntity().getBiome(), OCEAN)
                    ? Materials.SaltWater.getFluid(amount) : GT_ModHandler.getWater(amount);
            plan.fluids = new FluidStack[] { water };
        }
        plan.nextState = getCurrentEfficiency(null) == 10000 ? STATE_AT_BOTTOM : STATE_UPWARD;
        return CheckRecipeResult.SUCCESS;
    }

    @Override
    protected ICasing getCasing() {
        return CASING;
    }

    @Override
    protected Materials getFrameMaterial() {
        return Materials.Steel;
    }

    @Override
    protected int getMinTier() {
        return 1;
    }

    @Override
    protected int getWaterInBiomes() {
        return BiomeDictionary.isBiomeOfType(getBaseMetaTileEntity().getBiome(), OCEAN) ? 20 : super.getWaterInBiomes();
    }

    private long getWaterOutputPerTick() {
        return (1L << Math.max(0, getTierEnergyHatch())) * 2L * getWaterInBiomes() * 2;
    }

    @Override
    public String[] getInfoData() {
        EnumChatFormatting GREEN = EnumChatFormatting.GREEN;
        EnumChatFormatting RESET = EnumChatFormatting.RESET;
        EnumChatFormatting YELLOW = EnumChatFormatting.YELLOW;
        EnumChatFormatting RED = EnumChatFormatting.RED;
        return new String[] {
                "Water Drilling: " + YELLOW + getWaterOutputPerTick() + RESET + " L/t",
                "Biome Coefficient: " + GREEN + getWaterInBiomes() + RESET + " L/t",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t " + YELLOW
                        + VN[GT_Utility.getTier(getMaxInputVoltage())] + RESET,
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }
}
