package com.impact.network;

import net.minecraft.item.ItemStack;

public interface IPacketItemStack {
	void update(ItemStack... is);
}
