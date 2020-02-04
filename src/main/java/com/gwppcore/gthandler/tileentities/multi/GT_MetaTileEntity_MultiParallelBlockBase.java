/*
 * Thanks bartimaeusnek for this
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

package com.gwppcore.gthandler.tileentities.multi;


import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_VacuumFreezer;

import java.util.HashSet;

import static gregtech.api.enums.GT_Values.V;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase extends GT_MetaTileEntity_MultiBlockBase {

    public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
        super(aName);
    }

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
                base.mMaxProgresstime >>= 1;//this is effect of overclocking
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

}
