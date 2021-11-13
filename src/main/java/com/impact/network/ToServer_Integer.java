package com.impact.network;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class ToServer_Integer implements ClientToServerPacket {
	
	public final int[] integer;
	
	public ToServer_Integer(int... integer) {
		this.integer = integer;
	}
	
	@Override
	public void onReceive(EntityPlayerMP entityPlayerMP) {
		if (entityPlayerMP.openContainer instanceof IPacketInteger) {
			IPacketInteger cont = (IPacketInteger) entityPlayerMP.openContainer;
			cont.update(integer);
		}
	}
}