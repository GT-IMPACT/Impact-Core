package com.impact.mods.railcraft.carts.item.events;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


public abstract class Module {
	
	private Boolean isActive = true;
	
	public abstract String getModuleName();
	
	public Boolean areRequirementsMet() {
		return true;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public void init(FMLPreInitializationEvent event) {
	}
	
	public void load(FMLInitializationEvent event) {
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}
}