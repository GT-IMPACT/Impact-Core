package com.impact.mixins.ic2;

import ic2.core.item.armor.ItemArmorBatpack;
import ic2.core.item.armor.ItemArmorElectric;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmorBatpack.class)
public abstract class ArmorBatPack_Mixin extends ItemArmorElectric {
	
	public ArmorBatPack_Mixin() {
		super(null, null, 0, 0, 0, 0);
	}
	
	@Override
	public double getMaxCharge(ItemStack itemStack) {
		return 60000D;
	}
}