package com.impact.mixins.ic2;

import ic2.api.item.IElectricItem;
import ic2.core.item.armor.ItemArmorJetpackElectric;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemArmorJetpackElectric.class)
public abstract class ArmorJetpackElectric_Mixin implements IElectricItem {
	
	@Override
	public double getMaxCharge(final ItemStack itemStack) {
		return 60000D;
	}
	
	@Override
	public double getTransferLimit(final ItemStack itemStack) {
		return 30;
	}
}