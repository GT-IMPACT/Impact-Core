package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import net.minecraft.nbt.NBTTagCompound;
import gregtech.api.util.OverclockCalculator;
import com.impact.addon.gt.api.recipe.StagedFluidOutputSink;
import com.impact.addon.gt.api.recipe.StagedFluidOutputs;
import gregtech.api.logic.IProcessingOutputSink;
import gregtech.api.logic.ProcessingLogic;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
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

public class GTMTE_AdvancedPyrolyse extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedPyrolyse> {


    private static final ICasing CASING = Casings.HSLA;

    private int casingCount;
    private final StagedFluidOutputs stagedOutputs = new StagedFluidOutputs();

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 39)
            .atLeast(HatchElement.Maintenance, ImpactHatchElement.Muffler).build();
    private static IStructureDefinition<GTMTE_AdvancedPyrolyse> definition;

    public GTMTE_AdvancedPyrolyse(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.advpyrolyse", aNameRegional);
    }

    public GTMTE_AdvancedPyrolyse(String aName) {
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
        return new GTMTE_AdvancedPyrolyse(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_AdvancedPyrolyse> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_AdvancedPyrolyse>builder()
                    .addShape("main", new String[][] {
                            { "EEE EEEEE", "~EE EEEEE", "B B B   B" },
                            { "EEE EEEEE", "EDDDD   E", "    CCCCC" },
                            { "EEE EEEEE", "EEE EEEEE", "B B B   B" }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', onElementPass(machine -> machine.casingCount++, CASING.asElement()))
                    .addElement('D', Casings.STEEL_PIPE.asElement())
                    .addElement('B', ofFrame(Materials.HSLA))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvancedPyrolyse.class)
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
                .addInfo("Input/output of products depends of energy hatch, time does not change")
                .addInfo("Parallel recipes: 2x per energy hatch tier (LV - 2x, MV - 4x, HV - 6x and etc)")
                .addInfo("Pollution per second: 100 x tier energy hatch")
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Maintenance Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Muffler Hatch", "Any casing position; exactly 1")
                .addOtherStructurePart("Input Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Output Hatch", "Any casing; maximum 1")
                .addCasingInfoMin(SHELL.getCasing(), SHELL.getMinimumCasings(), false)
                .addOtherStructurePart(Casings.STEEL_PIPE, "Middle line", 4)
                .addOtherStructurePart("HSLA Frame", "Bottom angles")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureInfo("Hatches replacing casings: at most 7")
                .addSubChannel(GTStructureUtility.HATCH_CHANNEL, "Installs maintenance, energy, input, output and muffler hatches from your inventory in the marked casing positions.")
                .toolTipFinisher();
    }


    @Override
    protected IProcessingOutputSink createProcessingOutputs() {
        return StagedFluidOutputSink.create(this);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()
                .setRecipeMapSupplier(this::getRecipeMap)
                .setMaxParallelSupplier(() -> tierHatch() * 2)
                .setOutputChances(false)
                .setCalculatorSupplier(() -> new OverclockCalculator()
                        .setMaxOverclocks(0).setEUtModifier(tierHatch() / 2.0));
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sPyrolyseOven;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMapForNEI() {
        return GT_RecipeMaps.sPyrolyseOven;
    }

    public int tierHatch() {
        int Tier = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                Tier = tHatch.mTier;
            }
        }
        setParallel(Math.max(Tier, 1));
        return Math.max(Tier, 1);
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
        return 200;
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS | ProcessingLogic.LOCK_RECIPE;
    }

    @Override
    public boolean hasShowConnect() {
        return false;
    }
}
