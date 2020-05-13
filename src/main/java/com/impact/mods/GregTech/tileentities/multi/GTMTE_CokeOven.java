package com.impact.mods.GregTech.tileentities.multi;

import com.impact.mods.GregTech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.GregTech.tileentities.hatches.GT_MetaTileEntity_Primitive_InputBus;
import com.impact.mods.GregTech.tileentities.hatches.GT_MetaTileEntity_Primitive_OutputBus;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_CokeOven;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GTMTE_CokeOven extends GT_MetaTileEntity_MultiBlockBase {
    public ArrayList<GT_MetaTileEntity_Primitive_Hatch_Output> mOutputHatches1 = new ArrayList<GT_MetaTileEntity_Primitive_Hatch_Output>();
    public ArrayList<GT_MetaTileEntity_Primitive_InputBus> mInputBusses1 = new ArrayList<GT_MetaTileEntity_Primitive_InputBus>();
    public ArrayList<GT_MetaTileEntity_Primitive_OutputBus> mOutputBusses1 = new ArrayList<GT_MetaTileEntity_Primitive_OutputBus>();

    public GTMTE_CokeOven(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_CokeOven(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_CokeOven(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Coke Oven",
                "Size(WxHxD): 3x3x3 (Hollow), Controller (Front centered)",
                "1x Primitive Input Bus (Any casing)",
                "1x Primitive Output Bus (Any casing)",
                "1x Primitive Output Hatch (Any casing)",
                "Coke Oven Bricks for the rest"
        };
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK_ACTIVE : Textures.BlockIcons.OVERLAY_COKE_OVEN_BRICK)};
        }
        return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][53]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_CokeOven(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "CokeOven.png", GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes.mNEIName);
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
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
            GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes.findRecipe(getBaseMetaTileEntity(), false,
                    false, 0, fluids, inputs);
            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
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
                addFluidOutputs1(recipe.mFluidOutputs);
                this.updateSlots();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isAllowedToWork()) {
            if (aTick == 60)
            checkRecipe(mInventory[1]);
        }
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        mInputBusses1.clear();
        mOutputBusses1.clear();
        mOutputHatches1.clear();
        if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
            return false;
        }
        int tAmount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int h = -1; h < 2; h++) {
                    if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
                        IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                        if ((!addPrimInputToMachineList(tTileEntity, 181)) && (!addPrimOutputToMachineList(tTileEntity, 181))) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings8) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 5) {
                                return false;
                            }

                            mWrench = true;
                            mScrewdriver = true;
                            mSoftHammer = true;
                            mHardHammer = true;
                            mSolderingTool = true;
                            mCrowbar = true;
                            tAmount++;
                        }
                    }
                }
            }
        }
        return tAmount >= 16;
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

    private boolean dumpFluid(FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
        for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
            if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
                continue;
            }
            if (GT_ModHandler.isSteam(copiedFluidStack)) {
                if (!tHatch.outputsSteam()) {
                    continue;
                }
            } else {
                if (!tHatch.outputsLiquids()) {
                    continue;
                }
                if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch.getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
                    continue;
                }
            }
            int tAmount = tHatch.fill(copiedFluidStack, false);
            if (tAmount >= copiedFluidStack.amount) {
                boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
                tHatch.onEmptyingContainerWhenEmpty();
                return filled;
            } else if (tAmount > 0) {
                copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
                tHatch.onEmptyingContainerWhenEmpty();
            }
        }
        return false;
    }

    public boolean addOutput1(FluidStack aLiquid) {
        if (aLiquid == null) {
            return false;
        } else {
            FluidStack copiedFluidStack = aLiquid.copy();
            if (!this.dumpFluid(copiedFluidStack, true)) {
                this.dumpFluid(copiedFluidStack, false);
            }

            return false;
        }
    }

    public boolean addOutput1(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return false;
        aStack = GT_Utility.copy(aStack);
//		FluidStack aLiquid = GT_Utility.getFluidForFilledItem(aStack, true);
//		if (aLiquid == null) {
        boolean outputSuccess = true;
        while (outputSuccess && aStack.stackSize > 0) {
            outputSuccess = false;
            ItemStack single = aStack.splitStack(1);
            for (GT_MetaTileEntity_Primitive_OutputBus tHatch : mOutputBusses1) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch)) {
                    for (int i = tHatch.getSizeInventory() - 1; i >= 0 && !outputSuccess; i--) {
                        if (tHatch.getBaseMetaTileEntity().addStackToSlot(i, single)) outputSuccess = true;
                    }
                }
            }
            for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch) && tHatch.outputsItems()) {
                    if (tHatch.getBaseMetaTileEntity().addStackToSlot(1, single)) outputSuccess = true;
                }
            }

        }
        return outputSuccess;
    }

    public boolean depleteInput(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return false;
        FluidStack aLiquid = GT_Utility.getFluidForFilledItem(aStack, true);
        if (aLiquid != null) return depleteInput(aLiquid);
        for (GT_MetaTileEntity_Primitive_InputBus tHatch : mInputBusses1) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (GT_Utility.areStacksEqual(aStack, tHatch.getBaseMetaTileEntity().getStackInSlot(i))) {
                        if (tHatch.getBaseMetaTileEntity().getStackInSlot(0).stackSize >= aStack.stackSize) {
                            tHatch.getBaseMetaTileEntity().decrStackSize(0, aStack.stackSize);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected void addFluidOutputs1(FluidStack[] mOutputFluids2) {
        FluidStack[] var2 = mOutputFluids2;
        int var3 = mOutputFluids2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            FluidStack outputFluidStack = var2[var4];
            this.addOutput1(outputFluidStack);
        }

    }

    public ArrayList<ItemStack> getStoredOutputs() {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        for (GT_MetaTileEntity_Primitive_OutputBus tHatch : mOutputBusses1) {
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
        }
        return rList;
    }

    public ArrayList<ItemStack> getStoredInputs() {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        for (GT_MetaTileEntity_Primitive_InputBus tHatch : mInputBusses1) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(i) != null)
                        rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
        }
        return rList;
    }

    public void updateSlots() {
        for (GT_MetaTileEntity_Primitive_InputBus tHatch : mInputBusses1)
            if (isValidMetaTileEntity(tHatch)) tHatch.updateSlots();
    }

    public boolean addToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_InputBus)
            return mInputBusses1.add((GT_MetaTileEntity_Primitive_InputBus) aMetaTileEntity);
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_Hatch_Output)
            return mOutputHatches1.add((GT_MetaTileEntity_Primitive_Hatch_Output) aMetaTileEntity);
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_OutputBus)
            return mOutputBusses1.add((GT_MetaTileEntity_Primitive_OutputBus) aMetaTileEntity);

        return false;
    }

    public boolean addPrimInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_InputBus) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            ((GT_MetaTileEntity_Primitive_InputBus) aMetaTileEntity).mRecipeMap = getRecipeMap();
            return mInputBusses1.add((GT_MetaTileEntity_Primitive_InputBus) aMetaTileEntity);
        }
        return false;
    }

    public boolean addPrimOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_Hatch_Output) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mOutputHatches1.add((GT_MetaTileEntity_Primitive_Hatch_Output) aMetaTileEntity);
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_OutputBus) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mOutputBusses1.add((GT_MetaTileEntity_Primitive_OutputBus) aMetaTileEntity);
        }
        return false;
    }

}