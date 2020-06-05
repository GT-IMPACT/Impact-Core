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
package com.impact.mods.GregTech.tileentities.multi.debug;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoTunnel;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.tileentities.multi.gui.GT_Container_MultiParallelMachine;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnegative;
import java.io.IOException;
import java.util.*;

import static com.mojang.realmsclient.gui.ChatFormatting.*;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.GT_Values.VN;
import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase extends GT_MetaTileEntity_MultiBlockBase implements ITecTechEnabledMulti {

    public int mParallel = 1;
    @SuppressWarnings("rawtypes")
    public ArrayList TTTunnels = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    public ArrayList TTMultiAmp = new ArrayList<>();

    private final Set<GT_MetaTileEntity_Hatch_EnergyMulti> mEnergyHatchesTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_DynamoMulti> mDynamoHatchesTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_EnergyTunnel> mEnergyTunnelsTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_DynamoTunnel> mDynamoTunnelsTT = new HashSet<>();

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        return TecTechUtils.addEnergyInputToMachineList(this, aTileEntity, aBaseCasingIndex);
    }

    @Override
    public boolean drainEnergyInput(long aEU) {
        return TecTechUtils.drainEnergyMEBFTecTech(this, aEU);
    }

    @Override
    public long getMaxInputVoltage() {
        return TecTechUtils.getMaxInputVoltage(this);
    }


    /**
     * @param aID           - ID Machine
     * @param aName         - Named Machine
     * @param aNameRegional - Localized Named Machine
     */
    public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    /**
     * === NAMED ===
     */
    public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
        super(aName);
    }

    /**
     * === NOMINAL VOLTAGE ===
     */
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

    /**
     * === OVERCLOCKED PART 1 ===
     */
    public static void calculateOverclockedNessMulti(@Nonnegative int aEUt, @Nonnegative int aDuration, @Nonnegative int mAmperage, @Nonnegative long maxInputVoltage, GT_MetaTileEntity_MultiParallelBlockBase base) {
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
                base.mMaxProgresstime >>= 1;//this is effect of overclocking
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

    /**
     * === OVERCLOCKED PART 2 ===
     */
    public static void calculateOverclockedNessMultiPefectOC(int aEUt, int aDuration, int mAmperage, long maxInputVoltage, GT_MetaTileEntity_MultiParallelBlockBase base) {
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

    /**
     * === GUI CONTAINER ===
     */
    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity, true, 1);
    }

    /**
     * === BASIC MULTIBLOCKS PROPERTY ===
     */
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    /**
     * === BASIC MULTIBLOCKS PROPERTY ===
     */
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    /**
     * === BASIC MULTIBLOCKS PROPERTY ===
     */
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    /**
     * === BASIC MULTIBLOCKS PROPERTY ===
     */
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    /**
     * === BASIC MULTIBLOCKS PROPERTY ===
     */
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    public static ItemStack[] clean(final ItemStack[] v) {
        List<ItemStack> list = new ArrayList<ItemStack>(Arrays.asList(v));
        list.removeAll(Collections.singleton(null));
        return list.toArray(new ItemStack[list.size()]);
    }

    public int mInfoParallel;

    public boolean impactRecipe(ItemStack itemStack) {
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
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);
            if (tInputList.size() > 0 || tFluidList.size() > 0) {
                long tVoltage = this.getMaxInputVoltage();
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
                GT_Recipe tRecipe;
                tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

                if (tRecipe != null) {

                    if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100) && !isValidForLowGravity(tRecipe, getBaseMetaTileEntity().getWorld().provider.dimensionId))
                        return false;

                    if (tRecipe.mSpecialValue == -200 && (mCleanroom == null || mCleanroom.mEfficiency == 0))
                        return false;

                    ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
                    ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();
                    boolean found_Recipe = false;
                    int processed = 0;
                    long nominalV = getnominalVoltage(this);
                    while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < 1) {
                        if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
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

                        calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, getRecipeMap().mAmperage, nominalV, this);

                        if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                            return false;
                        if (this.mEUt > 0) {
                            this.mEUt = (-this.mEUt);
                        }
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

    public boolean impactRecipe(ItemStack itemStack, int aParallel, boolean aChance) {
        if (getTierEnergyHatch() >= 7 && aParallel >= 64) {
            this.mParallel = 16;
        } else {
            this.mParallel = aParallel;
        }
        mInfoParallel = this.mParallel;

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
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

            if (tInputList.size() > 0 || tFluidList.size() > 0) {

                GT_Recipe tRecipe;

                long nominalV = TecTechUtils.getnominalVoltageTT(this);
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));

                tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

                if (tRecipe != null) {

                    if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100) && !isValidForLowGravity(tRecipe, getBaseMetaTileEntity().getWorld().provider.dimensionId)) return false;

                    if (tRecipe.mSpecialValue == -200 && (mCleanroom == null || mCleanroom.mEfficiency == 0)) return false;

                    ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
                    ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();

                    boolean found_Recipe = false;
                    int processed = 0;

                    ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];

                    while ((tFluidList.size() > 0 || tInputList.size()> 0) && processed < mParallel) {
                        if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                            found_Recipe = true;
                            for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                                if (tRecipe.getOutput(h) != null) {
                                    tOut[h] = tRecipe.getOutput(h).copy();
                                    tOut[h].stackSize = 0;
                                }
                            }

                            for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) outputFluids.add(tRecipe.getFluidOutput(i));


                            ++processed;

                        } else break;
                    }

                    if (aChance) {
                        for (int f = 0; f < tOut.length; f++) {
                            if (tRecipe.mOutputs[f] != null && tOut[f] != null)
                                for (int g = 0; g < processed; g++)
                                    if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f))
                                        tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                        }
                    }

                    tOut = clean(tOut);

                    List<ItemStack> overStacks = new ArrayList<ItemStack>();

                    for (int f = 0; f < tOut.length; f++) {
                        while (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
                            if (tOut[f] != null) {
                                ItemStack tmp = tOut[f].copy();
                                tmp.stackSize = tmp.getMaxStackSize();
                                tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
                                overStacks.add(tmp);
                            }
                        }
                    }

                    if (overStacks.size() > 0) {
                        ItemStack[] tmp = new ItemStack[overStacks.size()];
                        tmp = overStacks.toArray(tmp);
                        tOut = ArrayUtils.addAll(tOut, tmp);
                    }

                    List<ItemStack> tSList = new ArrayList<ItemStack>();

                    for (ItemStack tS : tOut) {
                        if (tS.stackSize > 0) tSList.add(tS);
                    }

                    tOut = tSList.toArray(new ItemStack[tSList.size()]);

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
                                calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
                            } else
                                calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);


                            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;

                            if (this.mEUt > 0) this.mEUt = (-this.mEUt);

                            int TimeProgress;
                            switch (mParallel) {
                                default:
                                    TimeProgress = this.mMaxProgresstime;
                                    break;
                                case 16:
                                    TimeProgress = this.mMaxProgresstime / 2;
                                    break;
                                case 64:
                                    TimeProgress = this.mMaxProgresstime / 3;
                                    break;
                                case 256:
                                    TimeProgress = this.mMaxProgresstime / 4;
                                    break;
                            }

                            this.mMaxProgresstime = TimeProgress;
                            if (this.mMaxProgresstime < 1) this.mMaxProgresstime = 1;

                            this.mOutputItems = tOut;
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

    public boolean impactRecipe(ItemStack itemStack, int aParallel) {
        if (getTierEnergyHatch() >= 7 && aParallel >= 64) {
            this.mParallel = 16;
        } else {
            this.mParallel = aParallel;
        }
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
            FluidStack[] tFluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

            if (tInputList.size() > 0 || tFluidList.size() > 0) {

                GT_Recipe tRecipe;

                long nominalV = TecTechUtils.getnominalVoltageTT(this);
                byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));

                tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], tFluids, tInputs);

                if (tRecipe != null) {

                    if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100) && !isValidForLowGravity(tRecipe, getBaseMetaTileEntity().getWorld().provider.dimensionId)) return false;

                    if (tRecipe.mSpecialValue == -200 && (mCleanroom == null || mCleanroom.mEfficiency == 0)) return false;

                    ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();
                    ArrayList<FluidStack> outputFluids = new ArrayList<FluidStack>();

                    boolean found_Recipe = false;
                    int processed = 0;

                    ItemStack[] tOut = new ItemStack[tRecipe.mOutputs.length];

                    while ((tFluidList.size() > 0 || tInputList.size()> 0) && processed < mParallel) {
                        if ((tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe.isRecipeInputEqual(true, tFluids, tInputs)) {
                            found_Recipe = true;
                            for (int h = 0; h < tRecipe.mOutputs.length; h++) {
                                if (tRecipe.getOutput(h) != null) {
                                    tOut[h] = tRecipe.getOutput(h).copy();
                                    tOut[h].stackSize = 0;
                                }
                            }

                            for (int i = 0; i < tRecipe.mFluidOutputs.length; i++) outputFluids.add(tRecipe.getFluidOutput(i));


                            ++processed;

                        } else break;
                    }

                    for (int f = 0; f < tOut.length; f++) {
                        if (tRecipe.mOutputs[f] != null && tOut[f] != null)
                            for (int g = 0; g < processed; g++)
                                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(f)) tOut[f].stackSize += tRecipe.mOutputs[f].stackSize;
                    }

                    tOut = clean(tOut);

                    List<ItemStack> overStacks = new ArrayList<ItemStack>();

                    for (int f = 0; f < tOut.length; f++) {
                        while (tOut[f].getMaxStackSize() < tOut[f].stackSize) {
                            if (tOut[f] != null) {
                                ItemStack tmp = tOut[f].copy();
                                tmp.stackSize = tmp.getMaxStackSize();
                                tOut[f].stackSize = tOut[f].stackSize - tOut[f].getMaxStackSize();
                                overStacks.add(tmp);
                            }
                        }
                    }

                    if (overStacks.size() > 0) {
                        ItemStack[] tmp = new ItemStack[overStacks.size()];
                        tmp = overStacks.toArray(tmp);
                        tOut = ArrayUtils.addAll(tOut, tmp);
                    }

                    List<ItemStack> tSList = new ArrayList<ItemStack>();

                    for (ItemStack tS : tOut) {
                        if (tS.stackSize > 0) tSList.add(tS);
                    }

                    tOut = tSList.toArray(new ItemStack[tSList.size()]);

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
                            calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
                        } else
                            calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);


                        if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;

                        if (this.mEUt > 0) this.mEUt = (-this.mEUt);

                        int TimeProgress;
                        switch (aParallel) {
                            default:
                                TimeProgress = this.mMaxProgresstime;
                                break;
                            case 16:
                                TimeProgress = this.mMaxProgresstime / 2;
                                break;
                            case 64:
                                TimeProgress = this.mMaxProgresstime / 3;
                                break;
                            case 256:
                                TimeProgress = this.mMaxProgresstime / 4;
                                break;
                        }

                        this.mMaxProgresstime = TimeProgress;
                        if (this.mMaxProgresstime < 1) this.mMaxProgresstime = 1;

                        this.mOutputItems = tOut;
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

    /**
     * === INFO DATA ===
     */
    @Override
    public String[] getInfoData() {
        long storedEnergy = 0;
        long maxEnergy = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                storedEnergy += tHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyTunnel tEHatch : mEnergyTunnelsTT) {
            if (isValidMetaTileEntity(tEHatch)) {
                storedEnergy += tEHatch.getBaseMetaTileEntity().getStoredEU();
                maxEnergy += tEHatch.getBaseMetaTileEntity().getEUCapacity();
            }
        }
        return new String[]{
                "Progress: " + GREEN + mProgresstime / 20 + RESET + " s / " + mMaxProgresstime / 20 + RESET + " s",
                "Storage: " + GREEN + storedEnergy + RESET + " / " + RESET + YELLOW + maxEnergy + RESET + " EU",
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
                "Parallel Point: " + YELLOW + mParallel + "",
                "Pollution: " + RED + getPollutionPerTick(null) + RESET
        };
    }

    public void TThatches(){
        mEnergyHatchesTT.clear();
        mDynamoHatchesTT.clear();
        mEnergyTunnelsTT.clear();
        mDynamoTunnelsTT.clear();
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // В любом направлении по оси Z
        if(forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if(forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // В любом направлении по оси X
        if(forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if(forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // в любом направлении по оси Y
        if(forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }


    public int getTierEnergyHatch(){
        int aTier = 0;
        for (GT_MetaTileEntity_Hatch_Energy tEHatch : mEnergyHatches)
            if (isValidMetaTileEntity(tEHatch)) aTier = tEHatch.mTier;
        for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesTT)
            if (isValidMetaTileEntity(tEHatch)) aTier = tEHatch.mTier;
        for (GT_MetaTileEntity_Hatch_EnergyTunnel tEHatch : mEnergyTunnelsTT)
            if (isValidMetaTileEntity(tEHatch)) aTier = tEHatch.mTier;
        return aTier;
    }

    @Override
    public List<GT_MetaTileEntity_Hatch_Energy> getVanillaEnergyHatches() {
        return this.mEnergyHatches;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getTecTechEnergyTunnels() {
        return TTTunnels;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List getTecTechEnergyMultis() {
        return TTMultiAmp;
    }
}
