package com.impact.mixinplugin;

import cpw.mods.fml.relauncher.FMLLaunchHandler;

import java.util.Arrays;
import java.util.List;

import static com.impact.mixinplugin.Side.SERVER;
import static com.impact.mixinplugin.TargetedMod.*;

public enum Mixin {
	
	//
	// IMPORTANT: Do not make any references to any mod from this file. This file is loaded quite early on and if
	// you refer to other mods you load them as well. The consequence is: You can't inject any previously loaded classes!
	// Exception: Tags.java, as long as it is used for Strings only!
	//
	
//	TileEntityFluidInterface_Mixin("extracells.TileEntityFluidInterface_Mixin", EXTRACELLS, VANILLA),
	ImpactAPI_Mixin("impactapi.ImpactAPI_Mixin", IMPACTAPI, GREGTECH, VANILLA),
	InvSlotConsumable_Mixin("ic2.InvSlotConsumable_Mixin", IC2, VANILLA),
	MovableTileRegistry_Mixin("ae2.MovableTileRegistry_Mixin", AE, GREGTECH, VANILLA),
	ForgeHooksClient_Mixin("vanilla.ForgeHooksClient_Mixin", VANILLA),
	BlockHopper_Mixin("vanilla.BlockHopper_Mixin", VANILLA),
	OreDictionaryArbiter_Mixin("cofh.OreDictionaryArbiter_Mixin", COFH, VANILLA),
	
	;
	public final String mixinClass;
	public final List<TargetedMod> targetedMods;
	private final Side side;
	
	Mixin(String mixinClass, Side side, TargetedMod... targetedMods) {
		this.mixinClass = mixinClass;
		this.targetedMods = Arrays.asList(targetedMods);
		this.side = side;
	}
	
	Mixin(String mixinClass, TargetedMod... targetedMods) {
		this.mixinClass = mixinClass;
		this.targetedMods = Arrays.asList(targetedMods);
		this.side = Side.BOTH;
	}
	
	public boolean shouldLoad(List<TargetedMod> loadedMods) {
		return (side == Side.BOTH
				|| side == SERVER && FMLLaunchHandler.side().isServer()
				|| side == Side.CLIENT && FMLLaunchHandler.side().isClient())
				&& loadedMods.containsAll(targetedMods);
	}
}

enum Side {
	BOTH,
	CLIENT,
	SERVER;
}