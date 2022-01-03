package com.impact.mixins.cofh;

import cofh.mod.updater.UpdateCheckThread;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(UpdateCheckThread.class)
public class CoFHCoreUpdate_Mixin {
	/**
	 * @author mitchej123
	 * @reason Update URL is long since gone
	 */
	@Overwrite(remap = false)
	public void run() {
		// Do nothing
	}
}