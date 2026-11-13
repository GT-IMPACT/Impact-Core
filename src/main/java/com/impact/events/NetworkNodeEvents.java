package com.impact.events;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.ArrayList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public final class NetworkNodeEvents {

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            NetworkNodeLifecycle.onServerTick();
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (!event.world.isRemote) {
            for (Object tile : new ArrayList<>(event.getChunk().chunkTileEntityMap.values())) {
                load((TileEntity) tile);
            }
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        if (!event.world.isRemote) {
            for (Object tile : new ArrayList<>(event.getChunk().chunkTileEntityMap.values())) {
                unload((TileEntity) tile);
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (!event.world.isRemote) {
            NetworkNodeLifecycle.unloadWorld(event.world);
        }
    }

    public static void load(TileEntity tile) {
        Object node = node(tile);
        if (node instanceof IHandledNetworkNode) {
            NetworkNodeLifecycle.load((IHandledNetworkNode) node);
        }
    }

    public static void unload(TileEntity tile) {
        Object node = node(tile);
        if (node instanceof IHandledNetworkNode) {
            NetworkNodeLifecycle.unload((IHandledNetworkNode) node);
        }
    }

    private static Object node(TileEntity tile) {
        return tile instanceof IGregTechTileEntity ? ((IGregTechTileEntity) tile).getMetaTileEntity() : tile;
    }
}
