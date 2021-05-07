package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import com.impact.mods.gregtech.gui.aerostat.GUI_SelectAerostat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ImpactPacketStringGui extends ImpactPacket {

    private int dimID, playerID;
    private String mOwnerName;

    public ImpactPacketStringGui() {
    }

    public ImpactPacketStringGui(String OwnerName, int dimID, int playerID) {
        this.mOwnerName = OwnerName;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketStringGui(String OwnerName, EntityPlayer p) {
        this.mOwnerName = OwnerName;
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
        tOut.writeUTF(mOwnerName);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketStringGui(aData.readUTF(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        if (Minecraft.getMinecraft().currentScreen instanceof GUI_SelectAerostat) {
            GUI_SelectAerostat gui = (GUI_SelectAerostat) Minecraft.getMinecraft().currentScreen;
            gui.playerName = mOwnerName;
        }
    }
}