package com.impact.network;

import com.impact.lib.Refstrings;
import com.impact.network.msg.CTTClientSyncMessage;
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