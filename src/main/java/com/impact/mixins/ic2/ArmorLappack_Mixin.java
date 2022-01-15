package com.impact.mixins.ic2;

import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.ItemArmorLappack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmorLappack.class)
public abstract class ArmorLappack_Mixin extends ItemArmorElectric {
	
	public ArmorLappack_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 2.0E7D;
	}
}