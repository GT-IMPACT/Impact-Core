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
	
	InvSlotConsumable_Mixin("ic2.InvSlotConsumable_Mixin", IC2, VANILLA),
	NanoSaber_Mixin("ic2.NanoSaber_Mixin", IC2),
	MiningLaser_Mixin("ic2.MiningLaser_Mixin", IC2),
	ToolPainter_Mixin("ic2.ToolPainter_Mixin", IC2),
	BlockBase_Mixin("ic2.BlockBase_Mixin", IC2),
	ArmorQuantumSuit_Mixin("ic2.ArmorQuantumSuit_Mixin", IC2),
	ArmorNanoSuit_Mixin("ic2.ArmorNanoSuit_Mixin", IC2),
	ArmorLappack_Mixin("ic2.ArmorLappack_Mixin", IC2),
	ArmorJetpackElectric_Mixin("ic2.ArmorJetpackElectric_Mixin", IC2),
	ArmorEnergyPack_Mixin("ic2.ArmorEnergyPack_Mixin", IC2),
	ArmorAdvBatPack_Mixin("ic2.ArmorAdvBatPack_Mixin", IC2),
	ArmorBatPack_Mixin("ic2.ArmorBatPack_Mixin", IC2),
	
	MovableTileRegistry_Mixin("ae2.MovableTileRegistry_Mixin", AE, GREGTECH, VANILLA),
	
	ForgeHooksClient_Mixin("vanilla.ForgeHooksClient_Mixin", VANILLA),
	BlockHopper_Mixin("vanilla.BlockHopper_Mixin", VANILLA),
	
	OreDictionaryArbiter_Mixin("cofh.OreDictionaryArbiter_Mixin", COFH, VANILLA),
	CoFHCoreUpdate_Mixin_Mixin("cofh.CoFHCoreUpdate_Mixin", COFH, VANILLA),
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