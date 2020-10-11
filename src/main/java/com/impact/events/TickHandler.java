package com.impact.events;

import com.impact.util.PlayerPos;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("ALL")
public class TickHandler {

    private HashMap<UUID, PlayerPos> lastPlayerPosition = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.SERVER || event.phase == TickEvent.Phase.START)
            return;

        EntityPlayerMP player = (EntityPlayerMP) event.player;

        PlayerPos before = lastPlayerPosition.get(player.getPersistentID());
        PlayerPos current = new PlayerPos(event.player);

        if (before != null && !player.isDead && player.worldObj != null && !before.equals(current)) {

            PlayerMoveEvent moveEvent = new PlayerMoveEvent(player, before, current);
            MinecraftForge.EVENT_BUS.post(moveEvent);

            if (moveEvent.isCanceled() && event.side == Side.SERVER) {

                if (current.dim == 1) {

                    player.travelToDimension(1);

                    ChunkCoordinates coordinates = player.getBedLocation(0);
                    if (coordinates == null) {
                        coordinates = player.worldObj.getSpawnPoint();
                    }

                    player.setPositionAndUpdate(coordinates.posX, coordinates.posY + 1, coordinates.posZ);

                } else {
                    MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, before.getDim());
                    player.playerNetServerHandler.setPlayerLocation(before.getX(), before.getY(), before.getZ(), before.getYaw(), before.getPitch());
                }
            }
        }

        lastPlayerPosition.put(player.getPersistentID(), new PlayerPos(event.player));

    }

    @SubscribeEvent
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        EntityPlayerMP player = (EntityPlayerMP) e.entityPlayer;
        if (e.before.dim != e.entityPlayer.dimension) {
            if (e.entityPlayer.dimension == -1) {
                player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Teleport to " + EnumChatFormatting.BOLD + "The Nether" + EnumChatFormatting.RESET + EnumChatFormatting.RED + " is disable"));
                e.setCanceled(true);
            }
            if (e.entityPlayer.dimension == 1) {
                player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Teleport to " + EnumChatFormatting.BOLD + "The End" + EnumChatFormatting.RESET + EnumChatFormatting.RED + " is disable"));
                e.setCanceled(true);
            }
        }
    }
}
