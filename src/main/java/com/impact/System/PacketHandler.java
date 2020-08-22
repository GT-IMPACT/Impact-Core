package com.impact.System;

import com.impact.impact;
import com.impact.util.IZTPacket;
import com.impact.util.ToggleMetaData;
import cpw.mods.fml.common.network.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import java.io.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import cpw.mods.fml.common.network.internal.*;
import io.netty.buffer.*;
import java.util.*;

public class PacketHandler {
    private static final List<Class> packetCarrier;

    @SubscribeEvent
    public void onServerPacket(final FMLNetworkEvent.ServerCustomPacketEvent event) throws IOException {
        final ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
        final EntityPlayer entityPlayer = ((NetHandlerPlayServer)event.handler).playerEntity;
        final int packetId = bbis.readInt();
        try {
            final IZTPacket packetClass = (IZTPacket) PacketHandler.packetCarrier.get(packetId).newInstance();
            packetClass.processData(entityPlayer, bbis);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        bbis.close();
    }

    public static void sendPacketToServer(final IZTPacket packet) {
        final ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(PacketHandler.packetCarrier.indexOf(packet.getClass()));
        try {
            packet.appendData(buffer);
        }
        catch (IOException ex) {}
        impact.channel.sendToServer(new FMLProxyPacket(new C17PacketCustomPayload("Impact", buffer)));
    }

    static {
        (packetCarrier = new ArrayList<>()).add(ToggleMetaData.class);
    }
}
