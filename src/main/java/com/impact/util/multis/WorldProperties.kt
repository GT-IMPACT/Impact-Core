package com.impact.util.multis;

import gregtech.GT_Mod;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Recipe;

import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine.isValidForLowGravity;

public class WorldProperties {
	
	public static boolean needCleanroom(GT_Recipe tRecipe, GT_MetaTileEntity_MultiBlockBase base) {
		boolean isNotCleanroom = true;
		if (tRecipe.mSpecialValue == -200 && (base.mCleanroom == null
				|| base.mCleanroom.mEfficiency == 0)) {
			isNotCleanroom = false;
		}
		return isNotCleanroom;
	}
	
	public static boolean needSpace(GT_Recipe tRecipe, GT_MetaTileEntity_MultiBlockBase base) {
		boolean isNotSpace = true;
		if (GT_Mod.gregtechproxy.mLowGravProcessing && (tRecipe.mSpecialValue == -100)
				&& !isValidForLowGravity(tRecipe,
				base.getBaseMetaTileEntity().getWorld().provider.dimensionId)) {
			isNotSpace = false;
		}
		return isNotSpace;
	}
	
}
