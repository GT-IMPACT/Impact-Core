package com.impact.System;

import com.impact.System.CoreModConfig;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class LoginHandler
{
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + "Welcome to IMPACT"/* + EnumChatFormatting.BOLD + EnumChatFormatting.GREEN + CoreModConfig.ModPackVersion*/) );
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This is a conversion patch #1") );
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Please bring comments to " + EnumChatFormatting.AQUA + "https://discord.gg/bMf2qvd" ));
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
    }
}