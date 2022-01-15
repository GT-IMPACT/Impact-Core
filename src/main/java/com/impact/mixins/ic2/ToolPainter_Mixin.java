package com.impact.mixins.ic2;

import ic2.core.item.ItemIC2;
import ic2.core.item.tool.ItemToolPainter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemToolPainter.class)
public abstract class ToolPainter_Mixin extends ItemIC2 {
	
	@Final
	@Shadow(remap = false)
	public int color;
	
	public ToolPainter_Mixin() {
		super(null);
	}
	
	@Override
	public int getMaxDamage() {
		return 99;
	}
}