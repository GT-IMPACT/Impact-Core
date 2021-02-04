package com.impact.events;

import com.impact.impact;
import com.impact.util.IZTPacket;
import com.impact.util.ToggleMetaData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class PacketHandler {

  private static final List<Class> packetCarrier;

  static {
    (packetCarrier = new ArrayList<>()).add(ToggleMetaData.class);
  }

  public static void sendPacketToServer(final IZTPacket packet) {
    final ByteBuf buffer = Unpooled.buffer();
    buffer.writeInt(PacketHandler.packetCarrier.indexOf(packet.getClass()));
    try {
      packet.appendData(buffer);
    } catch (IOException ex) {
    }
    impact.channel.sendToServer(new FMLProxyPacket(new C17PacketCustomPayload("Impact", buffer)));
  }

  @SubscribeEvent
  public void onServerPacket(final FMLNetworkEvent.ServerCustomPacketEvent event)
      throws IOException {
    final ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
    final EntityPlayer entityPlayer = ((NetHandlerPlayServer) event.handler).playerEntity;
    final int packetId = bbis.readInt();
    try {
      final IZTPacket packetClass = (IZTPacket) PacketHandler.packetCarrier.get(packetId)
          .newInstance();
      packetClass.processData(entityPlayer, bbis);
    } catch (Exception e) {
      e.printStackTrace();
    }
    bbis.close();
  }
}
