package com.impact.network.ZTPacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;

public interface IZTPacket {

  void processData(final EntityPlayer p0, final ByteBufInputStream p1) throws IOException;

  void appendData(final ByteBuf p0) throws IOException;
}
