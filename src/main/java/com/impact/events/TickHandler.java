package com.impact.events;

import com.impact.addon.gt.api.other.IIndicatorProvider;
import com.impact.common.managers.AeroStateNetworkManager;
import com.impact.core.Config;
import com.impact.util.files.JsonWorld;
import com.impact.util.vector.PlayerPos;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.blocks.GT_Block_Machines;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import space.gtimpact.virtual_world.api.ObjectIndicator;
import space.gtimpact.virtual_world.api.VirtualAPI;

import java.util.HashMap;
import java.util.UUID;

import static com.impact.core.Config.DisableNether;
import static com.impact.core.Config.DisableTheEnd;

@SuppressWarnings("ALL")
public class TickHandler {

    private HashMap<UUID, PlayerPos> lastPlayerPosition = new HashMap<>();
    public long worldTicker = 0;

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.SERVER || event.phase == TickEvent.Phase.START) {
            return;
        }
        if (DisableTheEnd) {
            if (event.player instanceof EntityPlayerMP) {
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
        }
    }

    @SubscribeEvent
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (e.entityPlayer instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) e.entityPlayer;
            if (e.before.dim != e.entityPlayer.dimension) {
                if (e.entityPlayer.dimension == -1 && DisableNether) {
                    player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Teleport to " + EnumChatFormatting.BOLD + "The Nether" + EnumChatFormatting.RESET + EnumChatFormatting.RED + " is disabled"));
                    e.setCanceled(true);
                }
                if (e.entityPlayer.dimension == 1 && DisableTheEnd) {
                    player.addChatComponentMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Teleport to " + EnumChatFormatting.BOLD + "The End" + EnumChatFormatting.RESET + EnumChatFormatting.RED + " is disabled"));
                    e.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent e) {
        worldTicker++;

        if (worldTicker >= Long.MAX_VALUE)
            worldTicker = 0L;

        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            if (e.world.provider.dimensionId == 0) {
                if (worldTicker % (1200 * Config.saveTime) == 0L) {
                    JsonWorld.saveAsync();
                }
            }
        }

        AeroStateNetworkManager.onWorldTick(e, worldTicker);
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent e) {
        if (!e.world.isRemote) {
            if (e.placedBlock instanceof GT_Block_Machines) {
                TileEntity te = e.world.getTileEntity(e.x, e.y, e.z);
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    IMetaTileEntity mte = gte.getMetaTileEntity();
                    if (mte instanceof IIndicatorProvider && ((IIndicatorProvider) mte).hasIndicator()) {
                        VirtualAPI.addCustomObject(e.world, (ObjectIndicator) mte, e.x, e.z);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.BreakEvent e) {
        if (!e.world.isRemote) {
            if (e.block instanceof GT_Block_Machines) {
                TileEntity te = e.world.getTileEntity(e.x, e.y, e.z);
                if (te instanceof IGregTechTileEntity) {
                    IGregTechTileEntity gte = (IGregTechTileEntity) te;
                    IMetaTileEntity mte = gte.getMetaTileEntity();
                    if (mte instanceof IIndicatorProvider && ((IIndicatorProvider) mte).hasIndicator()) {
                        VirtualAPI.removeCustomObject(e.world, (ObjectIndicator) mte, e.x, e.z);
                    }
                }
            }
        }
    }
}
