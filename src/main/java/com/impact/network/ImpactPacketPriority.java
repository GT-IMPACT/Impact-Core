package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.client.gui.ContainterPriority;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ImpactPacketPriority extends ImpactPacket {

    private int dimID, playerID;
    private String priority;

    public ImpactPacketPriority() {
    }

    public ImpactPacketPriority(String priority, int dimID, int playerID) {
        this.priority = priority;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketPriority(String priority, EntityPlayer p) {
        this.priority = priority;
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 1;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeUTF(priority);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketPriority(aData.readUTF(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World w = DimensionManager.getWorld(dimID);
        if (w != null && w.getEntityByID(playerID) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) w.getEntityByID(playerID);
            if (player.openContainer instanceof ContainterPriority) {
                ContainterPriority container = (ContainterPriority) player.openContainer;
                container.setPriority(Integer.parseInt(this.priority), player);
            }
        }
    }
}