package com.impact.mixins.ic2;

import ic2.core.item.armor.ItemArmorAdvBatpack;
import ic2.core.item.armor.ItemArmorElectric;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmorAdvBatpack.class)
public abstract class ArmorAdvBatPack_Mixin extends ItemArmorElectric {
	
	public ArmorAdvBatPack_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 600000D;
	}
}