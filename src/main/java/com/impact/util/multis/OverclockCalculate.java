package com.impact.util.multis;

import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.util.GT_Utility;

import javax.annotation.Nonnegative;

import static gregtech.api.enums.GT_Values.V;

public class OverclockCalculate {
	
	public static void calculateOverclockedNessMulti(@Nonnegative int aEUt,
			@Nonnegative int aDuration, @Nonnegative int mAmperage,
			@Nonnegative long maxInputVoltage, GTMTE_Impact_BlockBase<?> base) {
		
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
				xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1
						: xEUt << 2;//U know, if the time is less than 1 tick make the machine use less power
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
				if (base.mEUt == 0) {
					base.mEUt = 1;
				}
				if (base.mMaxProgresstime <= 0) {
					base.mMaxProgresstime = 1;//set time to 1 tick
				}
			}
		}
	}
	
	//TODO: 20.02.2021 Future
	public static void calculateOverclockedNew(@Nonnegative int aEUt, @Nonnegative int aDuration, @Nonnegative int mAmperage,
			@Nonnegative long maxInputVoltage, GTMTE_Impact_BlockBase<?> base) {
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
				tempEUt = (tempEUt << 2) * 3 / 2;
				base.mMaxProgresstime >>= 2; //this is effect of overclocking
				xEUt = base.mMaxProgresstime <= 0 ? (xEUt >> 2) * 3 / 2 : (xEUt << 2) * 3 / 2;
				//U know, if the time is less than 1 tick make the machine use less power
			}
			while (xEUt > maxInputVoltage) {
				//downclock one notch until we are good again, we have overshot.
				xEUt = (xEUt << 2) * 3 / 2;
				base.mMaxProgresstime <<= 2;
			}
			if (xEUt > Integer.MAX_VALUE - 1) {
				base.mEUt = Integer.MAX_VALUE - 1;
				base.mMaxProgresstime = Integer.MAX_VALUE - 1;
			} else {
				base.mEUt = (int) xEUt;
				if (base.mEUt == 0) {
					base.mEUt = 1;
				}
				if (base.mMaxProgresstime <= 0) {
					base.mMaxProgresstime = 1;
				}
			}
		}
	}
	
	public static void calculateOverclockedNessMultiPefectOC(int aEUt, int aDuration, int mAmperage,
			long maxInputVoltage, GTMTE_Impact_BlockBase<?> base) {
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
				xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1
						: xEUt << 1;//U know, if the time is less than 1 tick make the machine use less power
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
				if (base.mEUt == 0) {
					base.mEUt = 1;
				}
				if (base.mMaxProgresstime <= 0) {
					base.mMaxProgresstime = 1;//set time to 1 tick
				}
			}
		}
	}
	
}
