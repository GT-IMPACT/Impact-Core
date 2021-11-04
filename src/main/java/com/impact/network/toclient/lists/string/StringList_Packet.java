package com.impact.network.toclient.lists.string;

import hohserg.elegant.networking.api.ElegantPacket;
import hohserg.elegant.networking.api.ServerToClientPacket;
import net.minecraft.client.Minecraft;

import java.util.List;

@ElegantPacket
public class StringList_Packet implements ServerToClientPacket {
	
	public final List<String> obj;
	
	public StringList_Packet(List<String> obj) {
		this.obj = obj;
	}
	
	@Override
	public void onReceive(Minecraft mc) {
		if (mc.currentScreen instanceof StringListPacket) {
			StringListPacket gui = (StringListPacket) mc.currentScreen;
			gui.updateField(obj);
		}
	}
}