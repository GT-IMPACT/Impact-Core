package com.impact.network.special;

import net.minecraft.item.ItemStack;

public interface IAESupplierPacker {
	void update(int slotID, ItemStack stack, int makeOrderAmount, int orderCount);
}