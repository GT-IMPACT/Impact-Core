package com.impact.mixins.cofh;

import cofh.core.util.oredict.OreDictionaryArbiter;
import cpw.mods.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OreDictionaryArbiter.class)
public class OreDictionaryArbiter_Mixin {
	
	@Inject(method = "initialize", at = @At("HEAD"), remap = false, cancellable = true)
	private static void initialize(CallbackInfo ci) {
		ci.cancel();
	}
}
