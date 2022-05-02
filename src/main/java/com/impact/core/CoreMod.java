package com.impact.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name("IMPACTCORE")
public class CoreMod implements IFMLLoadingPlugin {
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return "com.impact.loader.DepLoader";
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
	
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
