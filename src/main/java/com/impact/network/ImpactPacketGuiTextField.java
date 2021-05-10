package com.impact.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.impact.mods.gregtech.gui.regulatechest.Container_ValueRegulateChest;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RegulateDigitalChest;
import gregtech.api.net.GT_Packet;
import gregtech.common.items.GT_VolumetricFlask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class ImpactPacketGuiTextField extends ImpactPacket {
    private int capacity, dimID, playerID;

    public ImpactPacketGuiTextField() {
    }

    public ImpactPacketGuiTextField(int capacity, int dimID, int playerID) {
        this.capacity = capacity;
        this.dimID = dimID;
        this.playerID = playerID;
    }

    public ImpactPacketGuiTextField(int capacity, EntityPlayer p) {
        this.capacity = capacity;
        this.dimID = p.worldObj.provider.dimensionId;
        this.playerID = p.getEntityId();
    }

    @Override
    public int getPacketID() {
        return 5;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(capacity);
        tOut.writeInt(dimID);
        tOut.writeInt(playerID);
        return tOut.toByteArray();
    }

    @Override
    public ImpactPacket decode(ByteArrayDataInput aData) {
        return new ImpactPacketGuiTextField(aData.readInt(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process() {
        World w = DimensionManager.getWorld(dimID);
        if (w != null && w.getEntityByID(playerID) instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) w.getEntityByID(playerID);
            if (player.openContainer instanceof Container_ValueRegulateChest) {
                Container_ValueRegulateChest container = (Container_ValueRegulateChest) player.openContainer;
                if (container.mTileEntity.getMetaTileEntity() instanceof GTMTE_RegulateDigitalChest) {
                    GTMTE_RegulateDigitalChest te = (GTMTE_RegulateDigitalChest) container.mTileEntity.getMetaTileEntity();
                    te.mMaxItemCount = Math.max(capacity, 64);
                }
            }
        }
    }
}