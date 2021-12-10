package com.impact.mods.railcraft.carts.base;

import mods.railcraft.api.core.items.IStackFilter;
import net.minecraft.item.ItemStack;

public class ArrayStackFilter implements IStackFilter {
	
	private final ItemStack[] stacks;
	
	public ArrayStackFilter(ItemStack... stacks) {
		this.stacks = stacks;
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		if (stacks.length == 0 || !hasFilter()) {
			return true;
		}
		return InvTools.isItemEqual(stack, stacks);
	}
	
	public ItemStack[] getStacks() {
		return stacks;
	}
	
	public boolean hasFilter() {
		for (ItemStack filter : stacks) {
			if (filter != null) {
				return true;
			}
		}
		return false;
	}
}