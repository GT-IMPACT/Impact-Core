package com.impact.network.special;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

@ElegantPacket
public class SupplierAEPacket implements ServerToClientPacket {
	public final int slotID;
	public final ItemStack itemStack;
	public final int makeOrderAmount, orderCount;
	
	public SupplierAEPacket(int slotID, ItemStack stack, int makeOrderAmount, int orderCount) {
		this.slotID          = slotID;
		this.itemStack       = stack;
		this.makeOrderAmount = makeOrderAmount;
		this.orderCount      = orderCount;
	}
	
	@Override
	public void onReceive(Minecraft minecraft) {
		if (minecraft.currentScreen instanceof IAESupplierPacker) {
			IAESupplierPacker gui = (IAESupplierPacker) minecraft.currentScreen;
			gui.update(slotID, itemStack, makeOrderAmount, orderCount);
		}
	}
}