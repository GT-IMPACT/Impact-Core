package com.impact.mods.nei.impactplugin;

import com.github.vfyjxf.nee.NotEnoughEnergistics;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RecipeProcessorLoader {
	public static void init() {
		if (Loader.isModLoaded("neenergistics")) {
			NotEnoughEnergistics.logger.info("Found Impact-core, install Impact-core Support");
			RecipeProcessorRegister.INSTANCE.register();
		}
	}
}