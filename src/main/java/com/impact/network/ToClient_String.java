package com.impact.network;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;

@ElegantPacket
public class ToClient_String implements ServerToClientPacket {
	
	public final String[] obj;
	
	public ToClient_String(String... obj) {
		this.obj = obj;
	}
	
	@Override
	public void onReceive(Minecraft mc) {
		if (mc.currentScreen instanceof IPacketString) {
			IPacketString gui = (IPacketString) mc.currentScreen;
			gui.update(obj);
		}
	}
}