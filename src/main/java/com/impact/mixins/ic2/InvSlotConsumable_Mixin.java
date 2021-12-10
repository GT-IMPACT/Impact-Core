package com.impact.mixins.ic2;

import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableClass;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InvSlotConsumableClass.class)
public class InvSlotConsumable_Mixin extends InvSlotConsumable {
	
	public InvSlotConsumable_Mixin() {
		super(null, "", 0, 0);
	}
	
	@Override
	public ItemStack damage(int amount, boolean simulate) {
		return super.damage(0, simulate);
	}
	
	@Shadow(remap = false)
	public boolean accepts(ItemStack itemStack) {
		throw new IllegalStateException("Mixin failed to shadow testClass()");
	}
}