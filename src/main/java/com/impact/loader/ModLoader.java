package com.impact.loader;

import com.impact.mods.gregtech.tectech.Holo_Impact;
import com.impact.mods.gregtech.tectech.Holo_Vanila_GregTech;
import cpw.mods.fml.common.Loader;

public class ModLoader implements Runnable {
	
	@Override
	public void run() {
		if (Loader.isModLoaded("impactapi")) {
			new Holo_Impact().run();
			new Holo_Vanila_GregTech().run();
		}
	}
}