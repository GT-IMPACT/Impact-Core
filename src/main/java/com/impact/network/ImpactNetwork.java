package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;
import java.util.List;

@ChannelHandler.Sharable
public class ImpactNetwork extends MessageToMessageCodec<FMLProxyPacket, ImpactPacket> {

    static public ImpactNetwork INSTANCE;
    private final EnumMap<Side, FMLEmbeddedChannel> mChannel;
    private final ImpactPacket[] mSubChannels;

    public ImpactNetwork() {
        INSTANCE = this;
        this.mChannel = NetworkRegistry.INSTANCE.newChannel("ImpactNetwork", this, new HandlerShared());
        this.mSubChannels = new ImpactPacket[]{
                new ImpactPacketGTScanner(),
        };
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ImpactPacket msg, List<Object> out) throws Exception {
        out.add(new FMLProxyPacket(Unpooled.buffer().writeByte(msg.getPacketID()).writeBytes(msg.encode()).copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception {
        ByteArrayDataInput aData = ByteStreams.newDataInput(msg.payload().array());
        out.add(this.mSubChannels[aData.readByte()].decode(aData));
    }

    public void sendToPlayer(ImpactPacket aPacket, EntityPlayerMP aPlayer) {
        this.mChannel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.mChannel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPlayer);
        this.mChannel.get(Side.SERVER).writeAndFlush(aPacket);
    }

    public void sendToServer(ImpactPacket aPacket) {
        this.mChannel.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.mChannel.get(Side.CLIENT).writeAndFlush(aPacket);
    }

    @ChannelHandler.Sharable
    static final class HandlerShared extends SimpleChannelInboundHandler<ImpactPacket> {
        protected void channelRead0(ChannelHandlerContext ctx, ImpactPacket aPacket)
                throws Exception {
            //EntityPlayer aPlayer = GT_Values.GT.getThePlayer();
            aPacket.process();
        }
    }

}
