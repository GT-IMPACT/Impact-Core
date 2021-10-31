package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.common.block.itemblock.IB_IGlass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ImpactPacketMetaDataPacket extends ImpactPacket {

    private int dimID, playerID;
    private boolean incDamage = false;

    public ImpactPacketMetaDataPacket() {
    }

    public ImpactPacketMetaDataPacket(boolean incDamage, int dimID, int playerID) {
        this.incDamage = incDamage;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketMetaDataPacket(boolean incDamage, EntityPlayer p) {
        this.incDamage = incDamage;
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 2;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeBoolean(incDamage);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketMetaDataPacket(aData.readBoolean(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World w = DimensionManager.getWorld(dimID);
        if (w != null && w.getEntityByID(playerID) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) w.getEntityByID(playerID);
            ItemStack stack = player.getHeldItem();
            if (stack != null) {
                if (stack.getItem() instanceof IB_IGlass) {
                    int maxDmg = 15;
                    int newDamage = stack.getItemDamage();
                    newDamage = newDamage + (incDamage ? 1 : -1);
                    if (newDamage > maxDmg) {
                        newDamage = 0;
                    } else if (newDamage < 0) {
                        newDamage = maxDmg;
                    }
                    stack.setItemDamage(newDamage);
                }
            }
        }
    }
}