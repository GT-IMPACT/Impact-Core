package com.impact.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class ToServer_String implements ClientToServerPacket {
	
	public final String[] str;
	
	public ToServer_String(String... str) {
		this.str = str;
	}
	
	@Override
	public void onReceive(EntityPlayerMP entityPlayerMP) {
		if (entityPlayerMP.openContainer instanceof IPacketString) {
			IPacketString cont = (IPacketString) entityPlayerMP.openContainer;
			cont.update(str);
		}
	}
}