package com.impact.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

@ElegantPacket
public class ToServer_ItemStack implements ClientToServerPacket {
	
	public final ItemStack[] is;
	
	public ToServer_ItemStack(ItemStack... is) {
		this.is = is;
	}
	
	@Override
	public void onReceive(EntityPlayerMP entityPlayerMP) {
		if (entityPlayerMP.openContainer instanceof IPacketItemStack) {
			IPacketItemStack cont = (IPacketItemStack) entityPlayerMP.openContainer;
			cont.update(is);
		}
	}
}