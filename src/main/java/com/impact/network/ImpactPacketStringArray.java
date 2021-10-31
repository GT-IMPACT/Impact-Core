package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.mods.gregtech.gui.aerostat.GUI_SelectAerostat;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class ImpactPacketStringArray extends ImpactPacket {
    
    private List<String> names;

    public ImpactPacketStringArray() {
    }

    public ImpactPacketStringArray(List<String> names) {
        this.names = new ArrayList<>();
        this.names.addAll(names);
    }

    @Override
    public int getPacketID() {
        return 6;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10 + 32 * names.size());
        tOut.writeInt(names.size());
        for (String name : names) {
            tOut.writeUTF(name);
        }
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        int namesSize = aData.readInt();
        List<String> newStr = new ArrayList<>();
        for (int i = 0; i < namesSize; i++) {
            newStr.add(aData.readUTF());
        }
        return new ImpactPacketStringArray(newStr);
    }

    @Override
    public void process() {
        if (Minecraft.getMinecraft().currentScreen instanceof GUI_SelectAerostat) {
            GUI_SelectAerostat gui = (GUI_SelectAerostat) Minecraft.getMinecraft().currentScreen;
            gui.names.clear();
            gui.names.addAll(names);
        }
    }
}