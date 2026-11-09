package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import gregtech.api.logic.FuelProcessingLogic;
import gregtech.api.logic.ProcessingLogic;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.CeramicBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

abstract class GTMTE_SOFC<T extends GTMTE_SOFC<T>>
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<T>
        implements ISurvivalConstructable {

    private static final ICasing[] CASINGS = {
            Casings.CLEAN_STAINLESS_STEEL, Casings.STABLE_TITANIUM, Casings.ROBUST_TUNGSTENSTEEL
    };
    private IStructureDefinition<T> structure;
    protected int casingCount;

    public final int[] OXYGEN_PER_SEC = new int[] { 400, 1200, 2000 };
    public final int[] EU_PER_TICK = new int[] { 2048, 8192, 32768 };

    public final String[] CERAMIC_STRING = new String[] { "YSZ Ceramic Unit", "GDC Ceramic Unit", "LSCF Ceramic Unit" };
    public int fuelConsumption = 0;

    public GTMTE_SOFC(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_SOFC(String aName) {
        super(aName);
    }

    abstract int getTier();

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        int tier = getTier();
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addInfo("Generates electricity from gas fuels without pollution.")
                .addMachineType("Generator, Steam Producer")
                .addInfo("Oxygen consumption: %s L/s.", OXYGEN_PER_SEC[tier])
                .addInfo("Power output at full efficiency: %s EU/t.", EU_PER_TICK[tier])
                .addInfo(tier == 0 ? "Steam output after full warmup: %s L/s." : "Superheated steam output after full warmup: %s L/s.", calculateSteamOutput())
                .beginStructureBlock(3, 3, 5, false)
                .addController("front center of layer 2")
                .addStructureSection("Components")
                .addStructurePartInfo(CASINGS[tier], "%s+; fill unused casing positions", requirements().getMinimumCasings())
                .addOtherStructurePart(new ItemStack(CeramicBlock, 1, tier), "exactly 3; center of layer 2, between the front and rear walls")
                .addOtherStructurePart(Ic2Items.reinforcedGlass, "exactly 6; on both sides of the ceramic units")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Input Hatches", "2+; casing positions; fuel and oxygen")
                .addHatchInfo("Output Hatches", "optional; casing positions; steam output")
                .addHatchInfo("Dynamo Hatches", "optional, up to 2 standard and 1 multi-amp; rear wall")
                .addStructureHint("Casings or input/output/maintenance hatches", HintColor.GREEN)
                .addStructureHint("Rear wall: casings or input/output/maintenance/dynamo hatches", HintColor.LIME)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[] {
                CASINGS[getTier()].getTexture(),
                TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER)
        } : new ITexture[] { CASINGS[getTier()].getTexture() };
    }


    public int calculateSteamOutput() {
        if (EU_PER_TICK[getTier()] > 2048) {
            return EU_PER_TICK[getTier()] * 35 / 100 * 20;
        } else {
            return EU_PER_TICK[getTier()] * 35 / 100 * 2 * 20;
        }
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack stack) {
        return true;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new FuelProcessingLogic(this::getRecipeMap, recipe -> {
            FluidStack fuel = GT_Utility.getFluidForFilledItem(recipe.getRepresentativeInput(0), true);
            if (fuel == null || recipe.mSpecialValue <= 0) {
                return null;
            }
            int tier = getTier();
            fuel.amount = Math.multiplyExact(EU_PER_TICK[tier], 20) / recipe.mSpecialValue;
            FluidStack[] outputs = new FluidStack[0];
            if (mEfficiency == getMaxEfficiency(null)) {
                FluidStack steam = tier == 0 ? GT_ModHandler.getSteam(calculateSteamOutput())
                        : FluidRegistry.getFluidStack("ic2superheatedsteam", calculateSteamOutput());
                outputs = new FluidStack[] { steam };
            }
            return new FuelProcessingLogic.FuelCycle(
                    EU_PER_TICK[tier],
                    20,
                    fuel,
                    new FluidStack[] { Materials.Oxygen.getGas(OXYGEN_PER_SEC[tier]) },
                    outputs
            );
        });
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sTurbineFuels;
    }

    @Override
    protected void startCycle(ProcessingLogic.Result result, int efficiency) {
        super.startCycle(result, mEfficiency);
        mEfficiencyIncrease = 1000;
        fuelConsumption = ((FuelProcessingLogic) getProcessingLogic()).getConsumption();
    }

    @Override
    public boolean checkRecipe(ItemStack stack) {
        boolean started = super.checkRecipe(stack);
        if (!started && mMaxProgresstime == 0 && !isWaitingForOutput()) {
            mEfficiency = 0;
        }
        return started;
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_FLUIDS;
    }

    private StructureRequirements requirements() {
        return StructureRequirements.builder(CASINGS[getTier()], 18)
                .atLeast(HatchElement.Maintenance)
                .atLeast(ImpactHatchElement.FluidInput, 2).build();
    }

    @Override
    public IStructureDefinition<T> getStructureDefinition() {
        if (structure == null) {
            structure = StructureDefinition.<T>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "CCC", "C~C", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "DDD", "DDD", "DDD" }
                    })
                    .addElement('C', withHint(new HatchElementBuilder<T>()
                            .allow(HatchElement.Input, HatchElement.Output).autoPlace(ImpactHatchElement.FluidInput, ImpactHatchElement.FluidOutput).buildAndChain(requirements(), machine -> machine.casingCount++),
                            HintColor.GREEN))
                    .addElement('D', withHint(new HatchElementBuilder<T>()
                            .allow(HatchElement.Input, HatchElement.Output, ImpactHatchElement.Dynamo).autoPlace(ImpactHatchElement.FluidInput, ImpactHatchElement.FluidOutput, ImpactHatchElement.Dynamo)
                            .buildAndChain(requirements(), machine -> machine.casingCount++), HintColor.LIME))
                    .addElement('E', ceramicElement())
                    .addElement('G', ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .build();
        }
        return structure;
    }

    private IStructureElement<T> ceramicElement() {
        return ofBlock(CeramicBlock, getTier());
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget, ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        casingCount = 0;
        return checkPiece("main", 1, 1, 0)
                && requirements().isSatisfied(this, casingCount)
                && mMaintenanceHatches.size() == 1
                && mDynamoHatches.size() <= 2 && mDynamoHatchesMulti.size() <= 1;
    }

    public String[] getInfoData() {

        return new String[] {
                StatCollector.translateToLocal("GT5U.engine.output") + ": " + EnumChatFormatting.GREEN
                        + this.mEUt * this.mEfficiency / 10000 + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.engine.efficiency") + ": " + EnumChatFormatting.YELLOW
                        + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
                (getTier() == 0 ? "Output Steam: " : "Output SH Steam: ") + (((float) this.mEfficiency / 100.0F == 100)
                        ? EnumChatFormatting.GREEN + "" + calculateSteamOutput() + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.GREEN + "0" + EnumChatFormatting.RESET + " L/s"),
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s"),
                "Oxygen supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + OXYGEN_PER_SEC[getTier()] + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s")
        };
    }

    @Override
    public int getMaxEfficiency(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack stack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack stack) {
        return false;
    }
}
