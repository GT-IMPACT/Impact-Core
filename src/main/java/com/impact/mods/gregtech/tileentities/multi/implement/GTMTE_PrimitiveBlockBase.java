package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_InputBus;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_OutputBus;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;

public abstract class GTMTE_PrimitiveBlockBase<T extends GTMTE_PrimitiveBlockBase<T>>
        extends GT_MetaTileEntity_EnhancedMultiBlockBase<T>
        implements ISurvivalConstructable {
    public final ArrayList<GT_MetaTileEntity_Primitive_Hatch_Output> mOutputHatches1 = new ArrayList<>();
    public final ArrayList<GT_MetaTileEntity_Primitive_InputBus> mInputBusses1 = new ArrayList<>();
    public final ArrayList<GT_MetaTileEntity_Primitive_OutputBus> mOutputBusses1 = new ArrayList<>();

    protected GTMTE_PrimitiveBlockBase(int id, String name, String regionalName) {
        super(id, name, regionalName);
    }

    protected GTMTE_PrimitiveBlockBase(String name) {
        super(name);
    }

    @Override
    protected void setupMachine(IGregTechTileEntity tile, ItemStack trigger) {
        mOutputHatches1.clear();
        mInputBusses1.clear();
        mOutputBusses1.clear();
        super.setupMachine(tile, trigger);
    }

    private boolean dumpFluid(FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
        for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
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
                if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch
                        .getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
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
                if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch
                        .getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
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

    public boolean addOutput(FluidStack aLiquid) {
        if (aLiquid == null) {
            return false;
        }
        FluidStack copiedFluidStack = aLiquid.copy();
        if (!dumpFluid(copiedFluidStack, true)) {
            dumpFluid(copiedFluidStack, false);
        }
        return false;
    }

    protected void addFluidOutputs(FluidStack[] mOutputFluids2) {
        for (FluidStack outputFluidStack : mOutputFluids2) {
            addOutput(outputFluidStack);
        }
    }

    public boolean depleteInput(FluidStack aLiquid) {
        if (aLiquid == null) {
            return false;
        }
        for (GT_MetaTileEntity_Hatch_Input tHatch : mInputHatches) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                FluidStack tLiquid = tHatch.getFluid();
                if (tLiquid != null && tLiquid.isFluidEqual(aLiquid)) {
                    tLiquid = tHatch.drain(aLiquid.amount, false);
                    if (tLiquid != null && tLiquid.amount >= aLiquid.amount) {
                        tLiquid = tHatch.drain(aLiquid.amount, true);
                        return tLiquid != null && tLiquid.amount >= aLiquid.amount;
                    }
                }
            }
        }
        return false;
    }

    public boolean addOutput(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) {
            return false;
        }
        aStack = GT_Utility.copy(aStack);
        // FluidStack aLiquid = GT_Utility.getFluidForFilledItem(aStack, true);
        // if (aLiquid == null) {
        boolean outputSuccess = true;
        while (outputSuccess && aStack.stackSize > 0) {
            outputSuccess = false;
            ItemStack single = aStack.splitStack(1);
            for (GT_MetaTileEntity_Hatch_OutputBus tHatch : mOutputBusses) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch)) {
                    for (int i = tHatch.getSizeInventory() - 1; i >= 0 && !outputSuccess; i--) {
                        if (tHatch.getBaseMetaTileEntity().addStackToSlot(i, single)) {
                            outputSuccess = true;
                        }
                    }
                }
            }
            for (GT_MetaTileEntity_Primitive_OutputBus tHatch : mOutputBusses1) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch)) {
                    for (int i = tHatch.getSizeInventory() - 1; i >= 0 && !outputSuccess; i--) {
                        if (tHatch.getBaseMetaTileEntity().addStackToSlot(i, single)) {
                            outputSuccess = true;
                        }
                    }
                }
            }
            for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch) && tHatch.outputsItems()) {
                    if (tHatch.getBaseMetaTileEntity().addStackToSlot(1, single)) {
                        outputSuccess = true;
                    }
                }
            }
            for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
                if (!outputSuccess && isValidMetaTileEntity(tHatch) && tHatch.outputsItems()) {
                    if (tHatch.getBaseMetaTileEntity().addStackToSlot(1, single)) {
                        outputSuccess = true;
                    }
                }
            }
        }
        // }else {
        // for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
        // if (isValidMetaTileEntity(tHatch) &&
        // GT_ModHandler.isSteam(aLiquid)?tHatch.outputsSteam():tHatch.outputsLiquids())
        // {
        // int tAmount = tHatch.fill(aLiquid, false);
        // if (tAmount >= aLiquid.amount) {
        // return tHatch.fill(aLiquid, true) >= aLiquid.amount;
        // }
        // }
        // }
        // }
        return outputSuccess;
    }

    public boolean depleteInput(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) {
            return false;
        }
        FluidStack aLiquid = GT_Utility.getFluidForFilledItem(aStack, true);
        if (aLiquid != null) {
            return depleteInput(aLiquid);
        }
        for (GT_MetaTileEntity_Hatch_Input tHatch : mInputHatches) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                if (GT_Utility.areStacksEqual(aStack, tHatch.getBaseMetaTileEntity().getStackInSlot(0))) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(0).stackSize >= aStack.stackSize) {
                        tHatch.getBaseMetaTileEntity().decrStackSize(0, aStack.stackSize);
                        return true;
                    }
                }
            }
        }
        for (GT_MetaTileEntity_Hatch_InputBus tHatch : mInputBusses) {
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

    public ArrayList<ItemStack> getStoredOutputs() {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        // for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
        // if (isValidMetaTileEntity(tHatch)) {
        // rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(1));
        // }
        // }
        for (GT_MetaTileEntity_Hatch_OutputBus tHatch : mOutputBusses) {
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
        }
        for (GT_MetaTileEntity_Primitive_OutputBus tHatch : mOutputBusses1) {
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
        }
        return rList;
    }

    public ArrayList<FluidStack> getStoredFluids() {
        ArrayList<FluidStack> rList = new ArrayList<FluidStack>();
        for (GT_MetaTileEntity_Hatch_Input tHatch : mInputHatches) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch) && tHatch.getFillableStack() != null) {
                rList.add(tHatch.getFillableStack());
            }
        }
        return rList;
    }

    public ArrayList<ItemStack> getStoredInputs() {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        // for (GT_MetaTileEntity_Hatch_Input tHatch : mInputHatches) {
        // tHatch.mRecipeMap = getRecipeMap();
        // if (isValidMetaTileEntity(tHatch) &&
        // tHatch.getBaseMetaTileEntity().getStackInSlot(0) != null) {
        // rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(0));
        // }
        // }
        for (GT_MetaTileEntity_Hatch_InputBus tHatch : mInputBusses) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(i) != null) {
                        rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                    }
                }
            }
        }
        for (GT_MetaTileEntity_Primitive_InputBus tHatch : mInputBusses1) {
            tHatch.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tHatch)) {
                for (int i = tHatch.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tHatch.getBaseMetaTileEntity().getStackInSlot(i) != null) {
                        rList.add(tHatch.getBaseMetaTileEntity().getStackInSlot(i));
                    }
                }
            }
        }
        return rList;
    }

    public void updateSlots() {
        for (GT_MetaTileEntity_Hatch_Input tHatch : mInputHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                tHatch.updateSlots();
            }
        }
        for (GT_MetaTileEntity_Hatch_InputBus tHatch : mInputBusses) {
            if (isValidMetaTileEntity(tHatch)) {
                tHatch.updateSlots();
            }
        }
        for (GT_MetaTileEntity_Primitive_InputBus tHatch : mInputBusses1) {
            if (isValidMetaTileEntity(tHatch)) {
                tHatch.updateSlots();
            }
        }
    }

    public boolean addPrimInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_InputBus) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            ((GT_MetaTileEntity_Primitive_InputBus) aMetaTileEntity).mRecipeMap = getRecipeMap();
            return mInputBusses1.add((GT_MetaTileEntity_Primitive_InputBus) aMetaTileEntity);
        }
        return false;
    }

    public boolean addPrimOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
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
