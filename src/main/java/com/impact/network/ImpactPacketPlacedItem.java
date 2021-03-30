package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.common.block.itemblock.IB_IGlass;
import com.impact.common.te.TilePlacedItem;
import com.impact.loader.ItemRegistery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;

public class ImpactPacketPlacedItem extends ImpactPacket {

    private int dimID, playerID;
    byte side = 0;
    int blockX = 0;
    int blockY = 0;
    int blockZ = 0;


    public ImpactPacketPlacedItem() {
    }

    public ImpactPacketPlacedItem(byte side, int x, int y, int z, int dimID, int playerID) {
        this.side = side;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketPlacedItem(byte side, int x, int y, int z, EntityPlayer p) {
        this.side = side;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 3;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeByte(side);
        tOut.writeInt(blockX);
        tOut.writeInt(blockY);
        tOut.writeInt(blockZ);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketPlacedItem(aData.readByte(), aData.readInt(), aData.readInt(), aData.readInt(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World world = DimensionManager.getWorld(dimID);
        if (world != null && world.getEntityByID(playerID) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) world.getEntityByID(playerID);
            ForgeDirection dir = ForgeDirection.getOrientation(side);
            int x = blockX + dir.offsetX;
            int y = blockY + dir.offsetY;
            int z = blockZ + dir.offsetZ;
            if (!world.isAirBlock(x, y, z) || player.getHeldItem() == null) {
                return;
            }
            ItemStack stack = player.getHeldItem();

            world.setBlock(x, y, z, ItemRegistery.placedItem, side, 2);
            TilePlacedItem tile = (world.getTileEntity(x, y, z) != null && world
                .getTileEntity(x, y, z) instanceof TilePlacedItem) ? (TilePlacedItem) world
                .getTileEntity(x, y, z) : null;

            if (tile == null) {
                world.setBlockToAir(x, y, z);
                return;
            }

            tile.setStack(stack.copy());
            player.destroyCurrentEquippedItem();
        }
    }
}