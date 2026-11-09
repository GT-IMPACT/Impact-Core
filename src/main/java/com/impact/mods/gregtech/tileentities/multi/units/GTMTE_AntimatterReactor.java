package com.impact.mods.gregtech.tileentities.multi.units;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.ProcessingInputs;
import gregtech.api.logic.ProcessingPersistence;
import gregtech.api.logic.FuelProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import com.impact.addon.gt.api.recipe.ContinuousFluidOutputs;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import gregtech.api.enums.Casings;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sAntimatterReactorFuels;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AntimatterReactor extends GTMTE_Impact_BlockBase<GTMTE_AntimatterReactor> {

    private static final StructureRequirements HATCHES_H = StructureRequirements.builder(Casings.RADIATION_PROOF, 0)
            .build();
    private static final StructureRequirements HATCHES_I = StructureRequirements.builder(Casings.INTERMIX_CHAMBER, 0)
            .build();
    private static final StructureRequirements HATCHES_J = StructureRequirements.builder(Casings.RADIATION_PROOF, 0)
            .build();
    private static final StructureRequirements HATCHES_K = StructureRequirements.builder(Casings.INTERMIX_CHAMBER, 0)
            .build();
    private static final StructureRequirements HATCHES_L = StructureRequirements.builder(Casings.RADIATION_PROOF, 0)
            .build();

    protected int mColantConsumption = 0, mPerFluidAmount;
    private FluidStack[] mInputFluids = new FluidStack[0];
    private FluidStack runningProduct;
    private boolean protectRunningOutput;
    private final ContinuousFluidOutputs continuousOutputs = new ContinuousFluidOutputs();

    public GTMTE_AntimatterReactor(int aID, String aNameRegional) {
        super(aID, "impact.multis.antimatterreactor", aNameRegional);
    }

    public GTMTE_AntimatterReactor(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AntimatterReactor(mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        ITexture[] sTexture;
        if (aSide == aFacing) {
            sTexture = new ITexture[] {
                    TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                            Dyes.getModulation(-1, Dyes._NULL.mRGBa)),
                    TextureFactory.of(Textures.BlockIcons.OVERLAY_FUSION1) };
        } else {
            if (!aActive) {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            } else {
                sTexture = new ITexture[] { TextureFactory.of(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW,
                        Dyes.getModulation(-1, Dyes._NULL.mRGBa)) };
            }
        }
        return sTexture;
    }


    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new FuelProcessingLogic(this::getRecipeMap, recipe -> {
            if (recipe.mSpecialValue <= 0 || recipe.mFluidInputs.length == 0
                    || recipe.mFluidInputs[0] == null || recipe.mFluidOutputs.length == 0) {
                return null;
            }
            return new FuelProcessingLogic.FuelCycle(recipe, 2097152, Math.multiplyExact(recipe.mSpecialValue, 20));
        });
    }

    @Override
    protected CheckRecipeResult validateProcessing() {
        if (!continuousOutputs.isEmpty()) {
            return CheckRecipeResult.WAITING_FOR_OUTPUT;
        }
        return super.validateProcessing();
    }

    @Override
    protected void startCycle(ProcessingLogic.Result result, int efficiency) {
        super.startCycle(result, 10000);
        GT_Recipe recipe = ((FuelProcessingLogic) getProcessingLogic()).getCurrentRecipe();
        mInputFluids = ProcessingInputs.copyFluids(recipe.mFluidInputs);
        mPerFluidAmount = mInputFluids.length;
        mColantConsumption = mInputFluids[0].amount;
        runningProduct = result.fluids.length == 0 || result.fluids[0] == null ? null : result.fluids[0].copy();
        protectRunningOutput = (getProcessingModes() & ProcessingLogic.PROTECT_FLUIDS) != 0;
    }

    @Override
    protected void completeRecipe() {
        mOutputFluids = continuousOutputs.finish(mOutputFluids);
        super.completeRecipe();
    }

    @Override
    public void onPostTick(IGregTechTileEntity tile, long tick) {
        super.onPostTick(tile, tick);
        if (tile.isServerSide()) {
            continuousOutputs.flush(createProcessingOutputs());
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        continuousOutputs.save(aNBT);
        aNBT.setBoolean("protectRunningOutput", protectRunningOutput);
        ProcessingPersistence.writeFluids(aNBT, "runningProduct", new FluidStack[] { runningProduct });
        aNBT.setInteger("mPerFluidAmount", mPerFluidAmount);
        for (int i = 0; i < mPerFluidAmount; i++) {
            if (mInputFluids[i] != null && mInputFluids[i].amount > 0) {
                aNBT.setTag("mInputFluids" + (i == 0 ? "" : i), mInputFluids[i].writeToNBT(new NBTTagCompound()));
            }
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        continuousOutputs.load(aNBT);
        protectRunningOutput = aNBT.getBoolean("protectRunningOutput");
        FluidStack[] products = aNBT.hasKey("runningProduct")
                ? ProcessingPersistence.readFluids(aNBT, "runningProduct")
                : mOutputFluids == null ? new FluidStack[0] : ProcessingInputs.copyFluids(mOutputFluids);
        runningProduct = products.length == 0 ? null : products[0];
        mPerFluidAmount = aNBT.getInteger("mPerFluidAmount");
        mInputFluids = new FluidStack[mPerFluidAmount];
        for (int i = 0; i < mPerFluidAmount; i++) {
            mInputFluids[i] = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mInputFluids" + (i == 0 ? "" : i)));
        }
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return sAntimatterReactorFuels;
    }

    @Override
    public boolean onRunningTick(ItemStack stack) {
        if (lEUt <= 0 || mInputFluids.length == 0 || mInputFluids[0] == null) {
            stopMachine();
            return false;
        }
        ProcessingInputs coolant = collectProcessingInputs().get(0).copy();
        if (!coolant.consumeFluid(mInputFluids[0]) || !coolant.commit()) {
            stopMachine();
            return false;
        }
        addEnergyOutput(lEUt * mEfficiency / 10000);
        continuousOutputs.emit(runningProduct, protectRunningOutput, createProcessingOutputs());
        updateSlots();
        markDirty();
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    private static IStructureDefinition<GTMTE_AntimatterReactor> structureDefinition;

    @Override
    public IStructureDefinition<GTMTE_AntimatterReactor> getStructureDefinition() {
        if (structureDefinition == null) {

            structureDefinition = StructureDefinition.<GTMTE_AntimatterReactor>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "               ", "               ", "               ", "               ", "               ", "               ", "     FAAAF     ", "     AJ~LA     ", "     FAAAF     ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "    F     F    ", "    ADDDDDA    ", "    F     F    ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "   F       F   ", "   AD     DA   ", "   F       F   ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "  F         F  ", "  AD       DA  ", "  F         F  ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", " F           F ", " AD         DA ", " F           F ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "F      B      F", "AD    BBB    DA", "F      B      F", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "    FFFFFFF    ", "   F       F   ", "  F         F  ", " F           F ", "F             F", "F      C      F", "A     BBB     A", "AD   BBEBB   DA", "A     BBB     A", "F      C      F", "F             F", " F           F ", "  F         F  ", "   F       F   ", "    FFFFFFF    " },
                            { "    AAAAAAA    ", "   A       A   ", "  A  DDDDD  A  ", " A  D     D  A ", "A  D       D  A", "A D   ICK   D A", "A D  BBEBB  D A", "ADD  BE EB  DDA", "A D  BBEBB  D A", "A D   ICK   D A", "A  D       D  A", " A  D     D  A ", "  A  DDDDD  A  ", "   A       A   ", "    AAAAAAA    " },
                            { "    FFFFFFF    ", "   F       F   ", "  F         F  ", " F           F ", "F             F", "F      C      F", "A     BBB     A", "AD   BBEBB   DA", "A     BBB     A", "F      C      F", "F             F", " F           F ", "  F         F  ", "   F       F   ", "    FFFFFFF    " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "F      B      F", "AD    BBB    DA", "F      B      F", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", " F           F ", " AD         DA ", " F           F ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "  F         F  ", "  AD       DA  ", "  F         F  ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "   F       F   ", "   AD     DA   ", "   F       F   ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "    F     F    ", "    ADDDDDA    ", "    F     F    ", "               ", "               ", "               ", "               ", "               ", "               " },
                            { "               ", "               ", "               ", "               ", "               ", "               ", "     FAAAF     ", "     AAHAA     ", "     FAAAF     ", "               ", "               ", "               ", "               ", "               ", "               " }
                    })
                    .addElement('A', Casings.RADIATION_PROOF.asElement())
                    .addElement('B', Casings.FUSION_MK_II.asElement())
                    .addElement('C', Casings.INTERMIX_CHAMBER.asElement())
                    .addElement('D', Casings.DYSON_RING.asElement())
                    .addElement('E', Casings.CORE_CHAMBER.asElement())
                    .addElement('F', Casings.MAGNETIC_COIL.asElement())
                    .addElement('H', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(ImpactHatchElement.Dynamo, ImpactHatchElement.LaserOutput)
                            .autoPlace(ImpactHatchElement.PowerOutput)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 7)
                            .buildAndChain(HATCHES_H), HintColor.YELLOW))
                    .addElement('I', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Input)
                            .autoPlace(HatchElement.Input)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_I), HintColor.BLUE))
                    .addElement('J', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Input)
                            .autoPlace(HatchElement.Input)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_J), HintColor.BLUE))
                    .addElement('K', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Output)
                            .autoPlace(HatchElement.Output)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_K), HintColor.ORANGE))
                    .addElement('L', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Output)
                            .autoPlace(HatchElement.Output)
                            .onlyAcceptIf(hatch -> !(hatch instanceof GT_MetaTileEntity_TieredMachineBlock)
                                    || HatchElement.Maintenance.matches(hatch) || ImpactHatchElement.Muffler.matches(hatch)
                                    || ((GT_MetaTileEntity_TieredMachineBlock) hatch).mTier >= 8)
                            .buildAndChain(HATCHES_L), HintColor.ORANGE))
                    .build();

        }
        return structureDefinition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addInfo("REBIRTH IN A NEW ERA!!!")
                .addMachineType("Antimatter Reactor")
                .addInfo("Output: %s EU/t.", 2097152)
                .addInfo("Uses Hydrogen Pods and AntiHydrogen Pods as fuel.")
                .addInfo("Cooling: 4 L/t nitrogen; produces nitrogen plasma.")
                .beginStructureBlock(15, 15, 15, false)
                .addController("front center of layer 8")
                .addStructureSection("Components")
                .addOtherStructurePart(Casings.RADIATION_PROOF, "90 fixed; fill unused front and rear hatch positions")
                .addOtherStructurePart(Casings.INTERMIX_CHAMBER, "6 fixed; fill unused core input/output positions")
                .addOtherStructurePart(Casings.MAGNETIC_COIL, "exactly 116; outer frame")
                .addOtherStructurePart(Casings.DYSON_RING, "exactly 60; inner rings")
                .addOtherStructurePart(Casings.FUSION_MK_II, "exactly 40; around the core chambers")
                .addOtherStructurePart(Casings.CORE_CHAMBER, "exactly 6; around the core")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Input Hatches", "optional, up to 2; UV+; left of the controller or core")
                .addHatchInfo("Input Bus", "optional, up to 1; UV+; left of the controller or core")
                .addHatchInfo("Output Hatches", "optional, up to 2; UV+; right of the controller or core")
                .addHatchInfo("Output Bus", "optional, up to 1; UV+; right of the controller or core")
                .addHatchInfo("Dynamo Hatch", "optional, up to 1; ZPM+; rear center of layer 8")
                .addStructureHint("Left: casings or input hatches/buses", HintColor.BLUE)
                .addStructureHint("Right: casings or output hatches/buses", HintColor.ORANGE)
                .addStructureHint("Rear center: casing or Dynamo Hatch", HintColor.YELLOW)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;

        boolean check = checkPiece(7, 7, 0)
                && HatchCounts.inputHatches(this) <= 2 && mOutputHatches.size() <= 2 && HatchCounts.inputBuses(this) <= 1 && mOutputBusses.size() <= 1 && HatchCounts.dynamoHatches(this) <= 1;

        for (GT_MetaTileEntity_Hatch_Dynamo mDynamoHatch : this.mDynamoHatches) {
            if (mDynamoHatch.mTier < 7) { // ZPM
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_DynamoMulti mDynamoHatch : this.mDynamoHatchesMulti) {
            if (mDynamoHatch.mTier < 7) { // ZPM
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_DynamoMulti mDynamoHatch : this.mLaserOut) {
            if (mDynamoHatch.mTier < 7) { // ZPM
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Energy mEnergyHatch : this.mEnergyHatches) {
            if (mEnergyHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_InputBus mInputBuss : this.mInputBusses) {
            if (mInputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_OutputBus mOutputBuss : this.mOutputBusses) {
            if (mOutputBuss.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Input mInputHatch : this.mInputHatches) {
            if (mInputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        for (GT_MetaTileEntity_Hatch_Output mOutputHatch : this.mOutputHatches) {
            if (mOutputHatch.mTier < 8) {
                check = false;
                break;
            }
        }

        check &= HatchCounts.inputsAtLeastTier(this, 8);

        return check;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 7, 7, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 7, 7, 0, budget, environment);
    }

    @Override
    public int getSupportedProcessingModes() {
        return super.getSupportedProcessingModes() & ~ProcessingLogic.SEPARATE_INPUTS;
    }
}
