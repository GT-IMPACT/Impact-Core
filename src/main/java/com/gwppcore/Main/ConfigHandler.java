package com.gwppcore.main;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;

public class ConfigHandler {

    public static final ConfigHandler CONFIG_HANDLER =new ConfigHandler();

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent aEvent) {
        Error e=new Error();
        e.setStackTrace(new StackTraceElement[]{});

        MinecraftForge.EVENT_BUS.unregister(CONFIG_HANDLER);
    }
}
