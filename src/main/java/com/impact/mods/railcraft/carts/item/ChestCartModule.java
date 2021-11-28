package com.impact.mods.railcraft.carts.item;

import com.impact.mods.railcraft.carts.item.events.Module;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ChestCartModule extends Module {
	
	@Override
	public String getModuleName() {
		return "ChestUp";
	}
	
	@Override
	public Boolean areRequirementsMet() {
		return Loader.isModLoaded("chestup");
	}
	
	@Override
	public void load(FMLInitializationEvent event) {
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		ChestCartItems.init();
		ChestCartItems.registerItems();
		ChestCartItems.registerRecipes();
		ChestCartEntities.init();
	}
}