package com.impact.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public interface IZTPacket {
    void processData(final EntityPlayer p0, final ByteBufInputStream p1) throws IOException;

    void appendData(final ByteBuf p0) throws IOException;
}
