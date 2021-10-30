package com.impact.hooks;

import appeng.core.features.registries.MovableTileRegistry;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Andrey Solodovnikov (https://github.com/AndreySolodovnikov)
 */
public class AE2Spatial_Hook {
	
	@Hook(returnCondition = ReturnCondition.ALWAYS, injectOnLine = 83)
	public static boolean askToMove(MovableTileRegistry registry, final TileEntity te) {
		if (te instanceof IGregTechTileEntity) {
			return true;
		}
		te.invalidate();
		return true;
	}
}