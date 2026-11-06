package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import gregtech.api.recipe.check.CheckRecipeResult;
import net.minecraft.nbt.NBTTagCompound;
import gregtech.api.util.OverclockCalculator;
import com.impact.addon.gt.api.recipe.StagedFluidOutputs;
import gregtech.api.logic.ProcessingLogic;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GTStructureUtility.ofFrame;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Pyrolyse extends GTMTE_Impact_BlockBase<GTMTE_Pyrolyse> {

    private static final ICasing CASING = Casings.SOLID_STEEL;

    private int casingCount;
    private final StagedFluidOutputs stagedOutputs = new StagedFluidOutputs();

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 39)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_Pyrolyse> definition;

    public GTMTE_Pyrolyse(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.pyrolyse", aNameRegional);
    }

    public GTMTE_Pyrolyse(String aName) {
        super(aName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Pyrolyse(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_Pyrolyse> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_Pyrolyse>builder()
                    .addShape("main", new String[][] {
                            { "EEE EEEEE", "~EE EEEEE", "B B B   B" },
                            { "EEE EEEEE", "ECCCC   E", "    DDDDD" },
                            { "EEE EEEEE", "EEE EEEEE", "B B B   B" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', Casings.STEEL_PIPE.asElement())
                    .addElement('D', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('B', ofFrame(Materials.Steel))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Pyrolyse.class)
                            .allow(HatchElement.Input, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    HatchElement.Output)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 0, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 0, 1, 0, budget, environment);
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Pyrolyse oven")
                .addInfo("Converts hydrocarbons into gases, wood tar and solid fuels")
                .addInfo("The process emits gases throughout the entire time:")
                .addPollutionAmount(100)
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addCasingInfoMin(SHELL.getCasing(), SHELL.getMinimumCasings(), false)
                .addOtherStructurePart(Casings.STEEL_PIPE, "Middle line", 4)
                .addOtherStructurePart("Steel Frame", "Bottom angles")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 7")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs maintenance, energy, input, output and muffler hatches from your inventory in the marked casing positions.")
                .toolTipFinisher();
    }


    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()
                .setRecipeMapSupplier(this::getRecipeMap)
                .setOutputChances(false)
                .setItemOutputLimit(1)
                .setRecipeValidator(recipe -> recipe.mEUt <= 20
                        ? CheckRecipeResult.SUCCESS : CheckRecipeResult.INSUFFICIENT_VOLTAGE)
                .setCalculatorSupplier(() -> new OverclockCalculator().setMaxOverclocks(0))
                .setEUtAfterOverclock(eut -> 20)
                .setDurationAfterOverclock(duration -> 800);
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sPyrolyseOven;
    }

    @Override
    public boolean onRunningTick(ItemStack stack) {
        if (!super.onRunningTick(stack)) {
            return false;
        }
        stagedOutputs.tick(mProgresstime, createProcessingOutputs());
        markDirty();
        return true;
    }

    @Override
    protected void startCycle(ProcessingLogic.Result result, int efficiency) {
        super.startCycle(result, efficiency);
        stagedOutputs.start(result.fluids, (getProcessingModes() & ProcessingLogic.PROTECT_FLUIDS) != 0);
        mOutputFluids = result.fluids.length > 4 && result.fluids[4] != null
                ? new FluidStack[] { result.fluids[4].copy() } : new FluidStack[0];
    }

    @Override
    protected void completeRecipe() {
        mOutputFluids = stagedOutputs.finish(mOutputFluids);
        super.completeRecipe();
    }

    @Override
    public void stopMachine() {
        stagedOutputs.clear();
        super.stopMachine();
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        super.saveNBTData(tag);
        stagedOutputs.save(tag);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        super.loadNBTData(tag);
        stagedOutputs.load(tag);
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        casingCount = 0;

        boolean formationChecklist = checkPiece(0, 1, 0) && SHELL.isSatisfied(this, casingCount);

        if (HatchCounts.inputBuses(this) > 1) {
            formationChecklist = false;
        }
        if (HatchCounts.inputHatches(this) > 1) {
            formationChecklist = false;
        }
        if (this.mOutputHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (HatchCounts.energyHatches(this) > 1) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }
        if (this.mMufflerHatches.size() != 1) {
            formationChecklist = false;
        }
        return formationChecklist;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 5;
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS | ProcessingLogic.LOCK_RECIPE;
    }
}
