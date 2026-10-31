package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_PrimitiveBlockBase;
import gregtech.api.enums.Casings;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import gregtech.common.GT_Pollution;
import gregtech.common.tileentities.machines.multi.*;
import java.util.ArrayList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.GregTech_API.*;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_CokeOven extends GTMTE_PrimitiveBlockBase<GTMTE_CokeOven> {
    private static final ICasing CASING = Casings.COKE_OVEN_BRICKS;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 16).build();
    private int casingCount;

    private static IStructureDefinition<GTMTE_CokeOven> STRUCTURE;

    @Override
    public IStructureDefinition<GTMTE_CokeOven> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_CokeOven>builder()
                    .addShape("main", new String[][] {
                            { "CCC", "C~C", "CCC" },
                            { "CCC", "C-C", "CCC" },
                            { "CCC", "CCC", "CCC" },
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_CokeOven.class)
                            .allow(ImpactHatchElement.PrimitiveInput, ImpactHatchElement.PrimitiveOutput)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.GREEN))
                    .build();
        }
        return STRUCTURE;
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }

    private GT_Recipe cashedRecipe = null;

    public GTMTE_CokeOven(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.cokeoven", aNameRegional);
    }

    public GTMTE_CokeOven(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_CokeOven(this.mName);
    }

    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addMachineType("Coke Oven")
                .addPollutionAmount(25)
                .addSeparator()
                .addController("See the structure hologram")
                .addCasingInfoMin("Coke Oven Bricks", 16, false)
                .addOtherStructurePart("Primitive Input Bus", "Maximum 1")
                .addOtherStructurePart("Primitive Output Bus", "Maximum 1")
                .addOtherStructurePart("Primitive Output Hatch", "Maximum 1")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
            byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] { CASING.getTexture(), TextureFactory.of(
                    aActive ? Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK_ACTIVE
                            : Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
            IGregTechTileEntity aBaseMetaTileEntity) {
        return null;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return null;
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> direction.offsetY == 0 && rotation.isNotRotated() && flip.isNotFlipped();
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<ItemStack> tInputList = getStoredInputs();
        int tInputList_sS = tInputList.size();
        for (int i = 0; i < tInputList_sS - 1; i++) {
            for (int j = i + 1; j < tInputList_sS; j++) {
                if (GT_Utility.areStacksEqual(tInputList.get(i), tInputList.get(j))) {
                    if ((tInputList.get(i)).stackSize >= (tInputList.get(j)).stackSize) {
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
        tInputList.add(mInventory[1]);
        ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);

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
        FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

        if (inputs.length > 0 || fluids.length > 0) {
            long voltage = getMaxInputVoltage();
            GT_Recipe recipe = getRecipeMap()
                    .findRecipe(getBaseMetaTileEntity(), cashedRecipe, false,
                            false, 0, fluids, inputs);
            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
                cashedRecipe = recipe;
                this.mEfficiency = 10000;
                this.mEfficiencyIncrease = 10000;

                int maxProgresstime = recipe.mDuration;
                this.mEUt = 0;
                this.mMaxProgresstime = maxProgresstime;
                mOutputItems = new ItemStack[recipe.mOutputs.length];
                for (int i = 0; i < recipe.mOutputs.length; i++) {
                    if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
                        this.mOutputItems[i] = recipe.getOutput(i);
                    }
                }
                this.mOutputFluids = recipe.mFluidOutputs;
                this.updateSlots();
                this.mWrench = true;
                this.mScrewdriver = true;
                this.mSoftHammer = true;
                this.mHardHammer = true;
                this.mSolderingTool = true;
                this.mCrowbar = true;
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings({ "deprecation" })
    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (this.mMaxProgresstime > 0 && (getBaseMetaTileEntity().getTimer() % 20L == 0L)) {
            GT_Pollution.addPollution(
                    this.getBaseMetaTileEntity().getWorld(),
                    new ChunkPosition(this.getBaseMetaTileEntity().getXCoord(),
                            this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord()),
                    25);
        }
        return super.onRunningTick(aStack);
    }

    public boolean checkMachine(IGregTechTileEntity base, ItemStack trigger) {
        mInputBusses1.clear();
        mOutputBusses1.clear();
        mOutputHatches1.clear();
        casingCount = 0;
        boolean formed = checkPiece("main", 1, 1, 0) && SHELL.isSatisfied(this, casingCount);
        if (formed) {
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
        }
        return formed;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}
