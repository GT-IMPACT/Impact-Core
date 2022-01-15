package com.impact.mixins.ic2;

import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ItemNanoSaber;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(ItemNanoSaber.class)
public class NanoSaber_Mixin extends ItemElectricTool {
	
	public NanoSaber_Mixin() {
		super(null, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		this.maxCharge = 25000000;
		return maxCharge;
	}
}
