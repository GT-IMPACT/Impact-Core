package com.impact.util.network;

import com.impact.System.Refstrings;
import com.impact.util.network.msg.CTTClientSyncMessage;
import eu.usrv.yamcore.network.PacketDispatcher;

public class CoreModDispatcher extends PacketDispatcher
{

    public CoreModDispatcher()
    {
        super(Refstrings.MODID);
    }

    @Override
    public void registerPackets()
    {
        registerMessage(CTTClientSyncMessage.CTTClientSyncMessageHandler.class, CTTClientSyncMessage.class);
    }
}