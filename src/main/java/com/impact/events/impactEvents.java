package com.impact.events;


import com.impact.common.block.itemblock.IB_IGlass;
import com.impact.client.gui.ImpactGuiMainMenu;
import com.impact.util.ToggleMetaData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class impactEvents {

    public static List<String> bannedEntities = Arrays.asList("Creeper", "Skeleton", "Spider", "Giant", "Zombie", "Slime", "Ghast", "PigZombie", "Enderman", "Silverfish", "Blaze", "LavaSlime", "WitherBoss", "Bat", "Witch", "MushroomCow", "SnowMan", "GalacticraftCore.EvolvedSpider", "GalacticraftCore.EvolvedZombie", "GalacticraftCore.EvolvedCreeper", "GalacticraftCore.EvolvedSkeleton", "GalacticraftCore.EvolvedSkeletonBoss", "GalacticraftCore.EvolvedBossBlaze", "GalacticraftCore.EvolvedBossGhast", "GalacticraftCore.EvolvedCrystalBoss", "GalacticraftCore.EvolvedBossSlime", "GalacticraftCore.EvolvedBossWolf", "GalacticraftCore.EvolvedFireCreeper", "GalacticraftCore.EvolvedFireSkeleton", "GalacticraftCore.EvolvedFireSpider", "GalacticraftCore.EvolvedEnderman", "GalacticraftCore.Tentacles", "GalacticraftCore.EvolvedColdBlaze", "GalacticraftCore.Crawler", "GalacticraftCore.EvolvedBlaze", "GalacticraftCore.AlienVillager", "GalacticraftMars.CreeperBoss", "CaveSpider");

    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.entity != null && !bannedEntities.isEmpty() && bannedEntities.contains(EntityList.getEntityString(event.entity))) {
            event.world.removeEntity(event.entity);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent event) {
        if (event.gui instanceof net.minecraft.client.gui.GuiMainMenu) {
            event.gui = new ImpactGuiMainMenu();
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
        event.player.addChatMessage(new ChatComponentText(" "));
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + "Welcome to IMPACT"));
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Please bring comments to " + EnumChatFormatting.AQUA + "https://discord.gg/bMf2qvd"));
        event.player.addChatMessage(new ChatComponentText(" "));
        event.player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "=================================================="));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(final MouseEvent event) {
        final EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
        if (Keyboard.isKeyDown(184) || Keyboard.isKeyDown(56) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)) {
            final ItemStack itemStack = entityPlayer.getHeldItem();
            if (itemStack != null && itemStack.getItem() instanceof IB_IGlass) {
                if (event.dwheel != 0) PacketHandler.sendPacketToServer(new ToggleMetaData(event.dwheel > 0));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent aEvent) {
        Error e = new Error();
        e.setStackTrace(new StackTraceElement[]{});

        try {
            Class.forName("net.minecraftxray.loader.XRayForgeTweaker");
            Minecraft.getMinecraft().crashed(new CrashReport("", e));
            return;
        } catch (Exception E) {}

        try {
            Class.forName("de.Kradxn.Xray.mod_Xray");
            Minecraft.getMinecraft().crashed(new CrashReport("", e));
            return;
        } catch (Exception E) {}

    }


}
