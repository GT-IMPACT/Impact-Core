/*
 * Thanks bartimaeusnek for creating parallel recipes
 *
 * Description:
 *
 * Copyright (c) 2018-2019 bartimaeusnek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.gwppcore.GregTech.tileentities.multi.debug;

import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashSet;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.GT_Values.VN;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase extends GT_MetaTileEntity_MultiBlockBase {

    private GT_Recipe.GT_Recipe_Map mRecipeMap;

    /** === NAMED === */
    public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    /** === NAMED === */
    public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
        super(aName);
    }
    /** === IMPORT CLASS MULTIMACHINE SET PARALLEL === */
    public abstract int Parallel();


    /** === BASIC MULTIBLOCKS PROPERTY === */
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }
    /** === BASIC MULTIBLOCKS PROPERTY === */
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }
    /** === BASIC MULTIBLOCKS PROPERTY === */
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }
    /** === BASIC MULTIBLOCKS PROPERTY === */
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }
    /** === BASIC MULTIBLOCKS PROPERTY === */
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
    /** === DRAIN ENERGY HATCH === */
    public boolean drainEnergyInput(long aEU) {
        if (aEU <= 0)
            return true;
        long allTheEu = 0;
        int hatches = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : this.mEnergyHatches)
            if (GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity(tHatch)) {
                allTheEu += tHatch.getEUVar();
                hatches++;
            }
        if (allTheEu < aEU)
            return false;
        long euperhatch = aEU / hatches;
        HashSet<Boolean> returnset = new HashSet<Boolean>();
        for (GT_MetaTileEntity_Hatch_Energy tHatch : this.mEnergyHatches)
            if (tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(euperhatch, false))
                returnset.add(true);
            else
                returnset.add(false);
        return returnset.size() > 0 && !returnset.contains(false);
    }
    /** === NOMINAL VOLTAGE === */
    public static long getnominalVoltage(GT_MetaTileEntity_MultiBlockBase base) {
        long rVoltage = 0L;
        long rAmperage = 0L;

        for (GT_MetaTileEntity_Hatch_Energy tHatch : base.mEnergyHatches) {
            if (GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity(tHatch)) {
                if (rVoltage == 0 || rVoltage > tHatch.getBaseMetaTileEntity().getInputVoltage())
                    rVoltage = tHatch.getBaseMetaTileEntity().getInputVoltage();
                rAmperage += tHatch.getBaseMetaTileEntity().getInputAmperage();
            }
        }

        return rVoltage * rAmperage;
    }
    /** === OVERCLOCKED PART 1 === */
    public static void calculateOverclockedNessMulti( int aEUt,  int aDuration,  int mAmperage,  long maxInputVoltage, GT_MetaTileEntity_MultiBlockBase base) {
        byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
        if (mTier == 0) {
            //Long time calculation
            long xMaxProgresstime = ((long) aDuration) << 1;
            if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
                //make impossible if too long
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = aEUt >> 2;
                base.mMaxProgresstime = (int) xMaxProgresstime;
            }
        } else {
            //Long EUt calculation
            long xEUt = aEUt;
            //Isnt too low EUt check?
            long tempEUt = Math.max(xEUt, V[1]);

            base.mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier - 1] * mAmperage) {
                tempEUt <<= 2;//this actually controls overclocking
                //xEUt *= 4;//this is effect of everclocking
                base.mMaxProgresstime >>= 0;//this is effect of overclocking
                xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1 : xEUt << 2;//U know, if the time is less than 1 tick make the machine use less power
            }

            while (xEUt > maxInputVoltage) {
                //downclock one notch until we are good again, we have overshot.
                xEUt >>= 2;
                base.mMaxProgresstime <<= 1;
            }

            if (xEUt > Integer.MAX_VALUE - 1) {
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = (int) xEUt;
                if (base.mEUt == 0)
                    base.mEUt = 1;
                if (base.mMaxProgresstime <= 0)
                    base.mMaxProgresstime = 1;//set time to 1 tick
            }
        }
    }
    /** === OVERCLOCKED PART 2 === */
    public static void calculateOverclockedNessMultiPefectOC( int aEUt,  int aDuration,  int mAmperage,  long maxInputVoltage, GT_MetaTileEntity_MultiBlockBase base) {
        byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
        if (mTier == 0) {
            //Long time calculation
            long xMaxProgresstime = ((long) aDuration) << 1;
            if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
                //make impossible if too long
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = aEUt >> 2;
                base.mMaxProgresstime = (int) xMaxProgresstime;
            }
        } else {
            long xEUt = aEUt;
            //Isnt too low EUt check?
            long tempEUt = Math.max(xEUt, V[1]);

            base.mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier - 1] * mAmperage) {
                tempEUt <<= 1;//this actually controls overclocking
                //this is effect of overclocking
                xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1 : xEUt << 1;//U know, if the time is less than 1 tick make the machine use less power
            }

            while (xEUt > maxInputVoltage) {
                //downclock one notch until we are good again, we have overshot.
                xEUt >>= 1;
                base.mMaxProgresstime <<= 1;
            }
            if (xEUt > Integer.MAX_VALUE - 1) {
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = (int) xEUt;
                if (base.mEUt == 0)
                    base.mEUt = 1;
                if (base.mMaxProgresstime <= 0)
                    base.mMaxProgresstime = 1;//set time to 1 tick
            }
        }
    }
    /** === CHECK RECIPE === */




    public boolean checkRecipe(ItemStack itemStack) {
        for (GT_MetaTileEntity_Hatch_InputBus tBus : mInputBusses) {
            ArrayList<ItemStack> tBusItems = new ArrayList<ItemStack>();
            tBus.mRecipeMap = getRecipeMap();
            if (isValidMetaTileEntity(tBus)) {
                for (int i = tBus.getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                    if (tBus.getBaseMetaTileEntity().getStackInSlot(i) != null)
                        tBusItems.add(tBus.getBaseMetaTileEntity().getStackInSlot(i));
                }
            }
            ArrayList<ItemStack> tInputList = this.getStoredInputs();
            ArrayList<FluidStack> tFluidList = this.getStoredFluids();
            ItemStack[] tInputs = tBusItems.toArray(new ItemStack[]{});
            FluidStack[] tFluids = (FluidStack[]) ((FluidStack[]) tFluidList.toArray(new FluidStack[tFluidList.size()]));
            if (tInputList.size() > 0 || tFluidList.size() > 0) {
                long tVoltage = this.getMaxInputVoltage();
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
                GT_Recipe tRecipe;
                tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs );


                if (tRecipe != null) {
                /* TODO FOR "IF" WORLD
                if (GT_Mod.gregtechproxy.mLowGravProcessing && tRecipe.mSpecialValue == -100 &&
                        !isValidForLowGravity(tRecipe, getBaseMetaTileEntity().getWorld().provider.dimensionId))
                    return false;*/
                    ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
                    ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
                    boolean found_Recipe = false;
                    int processed = 0;
                    long nominalV = getnominalVoltage(this);
                    //int tHeatCapacityDivTiers = (this.mHeatingCapacity - tRecipe.mSpecialValue) / 900;
                    //long precutRecipeVoltage = (long) (tRecipe.mEUt * Math.pow(0.95, tHeatCapacityDivTiers));
                    while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < Parallel()) { //THIS PARALLEL
                        if (tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                            found_Recipe = true;
                            for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                                outputItems.add(tRecipe.getOutput(i));
                            }
                            for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) {
                                outputFluids.add(tRecipe.getFluidOutput(i));
                            }
                            ++processed;
                        } else
                            break;
                    }
                    if (found_Recipe) {
                        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
                        this.mEfficiencyIncrease = 10000;
                        long actualEUT = (long) (tRecipe.mEUt) * processed;
                        if (actualEUT > Integer.MAX_VALUE) {
                            byte divider = 0;
                            while (actualEUT > Integer.MAX_VALUE) {
                                actualEUT = actualEUT / 2;
                                divider++;
                            }
                            GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration, 1, nominalV, this);
                        } else
                            GT_MetaTileEntity_MultiParallelBlockBase.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
                        //In case recipe is too OP for that machine
                        if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                            return false;
                        if (this.mEUt > 0) {
                            this.mEUt = (-this.mEUt);
                        }
                        this.mMaxProgresstime = tRecipe.mDuration / Parallel(); //THIS PARALLEL
                        this.mOutputItems = new ItemStack[outputItems.size()];
                        this.mOutputItems = outputItems.toArray(this.mOutputItems);
                        this.mOutputFluids = new FluidStack[outputFluids.size()];
                        this.mOutputFluids = outputFluids.toArray(this.mOutputFluids);
                        this.updateSlots();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** === INFO DATA === */
    @Override
    public String[] getInfoData() {
        long storedEnergy=0;
        long maxEnergy=0;
        for(GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy+=tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy+=tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        EnumChatFormatting GREEN = EnumChatFormatting.GREEN;
        EnumChatFormatting RESET = EnumChatFormatting.RESET;
        EnumChatFormatting YELLOW = EnumChatFormatting.YELLOW;
        EnumChatFormatting RED = EnumChatFormatting.RED;
        return new String[]{
                "Progress: " + GREEN + mProgresstime/20 + RESET +" s / " + mMaxProgresstime/20 + RESET +" s",
                "Storage: " + GREEN + storedEnergy + RESET + " / " + RESET + YELLOW+ maxEnergy + RESET +" EU" ,
                "Usage Energy: " + RED + -mEUt + RESET +  " EU/t",
                "Max Voltage: " + YELLOW +getMaxInputVoltage() + RESET + " EU/t " + YELLOW + VN[GT_Utility.getTier(getMaxInputVoltage())]+ RESET,
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F  + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F  + " %" + RESET),
                "Parallel Point: "+ YELLOW + Parallel() + " / 256",
                "Pollution: " + RED + Parallel()*50 + RESET
        };
    }
}
