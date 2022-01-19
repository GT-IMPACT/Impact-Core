package com.impact.network.special;

import baubles.api.BaublesApi;
import com.impact.common.armor.MaskOfVision;
import com.impact.impact;
import com.impact.util.vector.Vector3i;
import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

@ElegantPacket
public class ToClient_LaserPush implements ServerToClientPacket {
	
	public final Vector3i vec1, vec2;
	public final int color;
	
	public ToClient_LaserPush(Vector3i vec1, Vector3i vec2, int color) {
		this.vec1  = vec1;
		this.vec2  = vec2;
		this.color = color;
	}
	
	@Override
	public void onReceive(Minecraft minecraft) {
		boolean mask = false;
		EntityClientPlayerMP thePlayer = minecraft.thePlayer;
		if (thePlayer != null) {
			ItemStack is = thePlayer.getCurrentArmor(3);
			if (is == null || !(is.getItem() instanceof MaskOfVision)) {
				IInventory handler = BaublesApi.getBaubles(thePlayer);
				if (handler != null) {
					for (int i = 0; i < handler.getSizeInventory(); ++i) {
						is = handler.getStackInSlot(i);
						if (is != null && is.getItem() instanceof MaskOfVision) {
							mask = true;
							break;
						}
					}
				}
			} else {
				mask = true;
			}
			if (mask) {
				impact.proxy.beam(thePlayer.worldObj, vec1, vec2, 1, color, false, 1, 20);
			}
		}
	}
}
