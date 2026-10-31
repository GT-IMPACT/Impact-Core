package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import gregtech.common.gui.GT_GUIContainer_AntimatterReactor;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.GregTech_API.*;
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
    private FluidStack[] mInputFluids;

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

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_AntimatterReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
                "AntimatterReactor.png", sAntimatterReactorFuels.mNEIName);
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
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<ItemStack> tInputList = getStoredInputs();
        for (int i = 0; i < tInputList.size() - 1; i++) {
            for (int j = i + 1; j < tInputList.size(); j++) {
                if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
                    if (tInputList.get(i).stackSize >= tInputList.get(j).stackSize) {
                        tInputList.remove(j--);
                    } else {
                        tInputList.remove(i--);
                        break;
                    }
                }
            }
        }
        ItemStack[] tInputs = Arrays.copyOfRange(tInputList.toArray(new ItemStack[0]), 0, 2);

        ArrayList<FluidStack> tFluidList = getStoredFluids();
        for (int i = 0; i < tFluidList.size() - 1; i++) {
            for (int j = i + 1; j < tFluidList.size(); j++) {
                if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
                    if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
                        tFluidList.remove(j--);
                    } else {
                        tFluidList.remove(i--);
                        break;
                    }
                }
            }
        }
        FluidStack[] tFluids = Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);

        if (tInputList.size() > 1) {
            GT_Recipe tRecipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), cashedRecipe, false, Long.MAX_VALUE,
                    tFluids, tInputs);
            if (tRecipe == null) {
                return false;
            }
            if (tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                cashedRecipe = tRecipe;
                this.mEUt = 2097152;
                this.mMaxProgresstime = tRecipe.mSpecialValue * 20;
                this.mEfficiency = 10000;
                this.mEfficiencyIncrease = 10000;
                this.mInputFluids = tRecipe.mFluidInputs;
                this.mPerFluidAmount = tRecipe.mFluidInputs.length;
                this.mOutputFluids = tRecipe.mFluidOutputs;
                this.mOutputItems = tRecipe.mOutputs;
                this.mColantConsumption = tRecipe.mFluidInputs[0].amount;
                updateSlots();
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
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
    public boolean onRunningTick(ItemStack aStack) {
        if (mEUt > 0 && mInputFluids != null) {
            if (depleteInput(mInputFluids[0])) {
                addEnergyOutput(((long) mEUt * mEfficiency) / 10000);
                addOutput(mOutputFluids[0]);
                return true;
            }
        }
        stopMachine();
        return false;
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
                            .buildAndChain(HATCHES_H), HintColor.YELLOW))
                    .addElement('I', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Input)
                            .buildAndChain(HATCHES_I), HintColor.RED))
                    .addElement('J', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Input)
                            .buildAndChain(HATCHES_J), HintColor.BLUE))
                    .addElement('K', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Output)
                            .buildAndChain(HATCHES_K), HintColor.ORANGE))
                    .addElement('L', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AntimatterReactor.class)
                            .allow(HatchElement.Output)
                            .buildAndChain(HATCHES_L), HintColor.GREEN))
                    .build();

        }
        return structureDefinition;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("REBIRTH IN A NEW ERA!!!")
                .addMachineType("Antimatter Reactor")
                .addInfo("2,097,152 EU/t and 4 L/t plasma (in cooling)")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(15, 15, 15, false)
                .addOtherStructurePart("Input Hatch", "(UV or better); maximum 2")
                .addOtherStructurePart("Output Hatch", "(UV or better); maximum 2")
                .addOtherStructurePart("Input Bus", "(UV or better); maximum 1")
                .addOtherStructurePart("Output Bus", "(UV or better); maximum 1")
                .addOtherStructurePart("Dynamo Hatch", "(ZPM or better), back centered; maximum 1")
                .addCasingInfoMin("Radiation Proof Casing", 89, false)
                .addCasingInfoMin("Intermix Chamber Casing", 6, false)
                .addCasingInfoMin("Magnetic Coil Block", 116, false)
                .addCasingInfoMin("Dyson Ring Casing", 60, false)
                .addCasingInfoMin("Fusion Machine Casing MK II", 40, false)
                .addCasingInfoMin("Core Chamber Casing", 6, false)

                .addStructureHint("Input Hatch or Intermix Chamber Casing", HintColor.RED)
                .addStructureHint("Output Hatch or Intermix Chamber Casing", HintColor.ORANGE)
                .addStructureHint("Input Bus or Radiation Proof Casing", HintColor.BLUE)
                .addStructureHint("Dynamo Hatch or Radiation Proof Casing", HintColor.YELLOW)
                .addStructureHint("Output Bus or Radiation Proof Casing", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;

        boolean check = checkPiece(7, 7, 0);

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
    public boolean hasSeparate() {
        return false;
    }
}
