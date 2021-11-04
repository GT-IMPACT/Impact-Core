package com.impact.network.toclient.primitives.string;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;

@ElegantPacket
public class StringArray_Packet implements ServerToClientPacket {
	
	public final String[] obj;
	
	public StringArray_Packet(String... obj) {
		this.obj = obj;
	}
	
	@Override
	public void onReceive(Minecraft mc) {
		if (mc.currentScreen instanceof StringArrayPacket) {
			StringArrayPacket gui = (StringArrayPacket) mc.currentScreen;
			gui.updateFields(obj);
		}
	}
}