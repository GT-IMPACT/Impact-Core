package com.impact.mixins.ic2;

import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.ItemArmorEnergypack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmorEnergypack.class)
public abstract class ArmorEnergyPack_Mixin extends ItemArmorElectric {
	
	public ArmorEnergyPack_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 1E7D;
	}
}