package com.impact.addon.gt.api.parallel_system;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class NetworkNodeAccess {

    private NetworkNodeAccess() {
    }

    public static World worldOf(Object node) {
        Object base = node instanceof IMetaTileEntity ? ((IMetaTileEntity) node).getBaseMetaTileEntity() : node;
        return base instanceof TileEntity ? ((TileEntity) base).getWorldObj() : null;
    }

    public static boolean isLoaded(Object node) {
        Object base = node instanceof IMetaTileEntity ? ((IMetaTileEntity) node).getBaseMetaTileEntity() : node;
        if (!(base instanceof TileEntity)) {
            return !(node instanceof IMetaTileEntity);
        }
        TileEntity tile = (TileEntity) base;
        if (node instanceof IMetaTileEntity
                && ((IMetaTileEntity) node).getBaseMetaTileEntity().getMetaTileEntity() != node) {
            return false;
        }
        World world = tile.getWorldObj();
        return world != null && !world.isRemote && !tile.isInvalid()
                && world.blockExists(tile.xCoord, tile.yCoord, tile.zCoord)
                && world.getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord) == tile;
    }

    public static void markConfigurationDirty(IMetaTileEntity node) {
        IGregTechTileEntity base = node.getBaseMetaTileEntity();
        if (base == null) {
            return;
        }
        base.markDirty();
        if (base instanceof TileEntity) {
            TileEntity tile = (TileEntity) base;
            World world = tile.getWorldObj();
            // GregTech's markDirty does not mark the chunk for saving.
            if (world != null && !world.isRemote && world.getChunkProvider() != null
                    && world.getChunkProvider().chunkExists(tile.xCoord >> 4, tile.zCoord >> 4)) {
                world.getChunkFromChunkCoords(tile.xCoord >> 4, tile.zCoord >> 4).setChunkModified();
            }
        }
    }
}
