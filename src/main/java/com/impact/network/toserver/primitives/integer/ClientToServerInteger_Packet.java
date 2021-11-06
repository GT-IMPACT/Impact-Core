package com.impact.network.toserver.primitives.integer;

import hohserg.elegant.networking.api.ClientToServerPacket;
import hohserg.elegant.networking.api.ElegantPacket;
import net.minecraft.entity.player.EntityPlayerMP;

@ElegantPacket
public class ClientToServerInteger_Packet implements ClientToServerPacket {
	
	public final int[] integer;
	
	public ClientToServerInteger_Packet(int... integer) {
		this.integer = integer;
	}
	
	@Override
	public void onReceive(EntityPlayerMP entityPlayerMP) {
		if (entityPlayerMP.openContainer instanceof ClientToServer_Integer) {
			ClientToServer_Integer cont = (ClientToServer_Integer) entityPlayerMP.openContainer;
			cont.updateField(integer);
		}
	}
}