package com.gwppcore.item.Circuit_Programmer;

import com.gwppcore.item.Circuit_Programmer.CircuitProgrammer;
import com.google.common.io.ByteArrayDataInput;
import gregtech.api.net.GT_Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.nio.ByteBuffer;

public class CircuitProgrammerPacket extends GT_Packet {

    private int dimID, playerID;
    private byte chipCfg;
    private boolean hasChip;

    public CircuitProgrammerPacket() {
        super(true);
    }

    public CircuitProgrammerPacket(int dimID, int playerID, boolean hasChip, byte chipCfg) {
        super(false);
        this.dimID = dimID;
        this.playerID = playerID;
        this.hasChip = hasChip;
        this.chipCfg = chipCfg;
    }

    @Override
    public byte getPacketID() {
        return 1;
    }

    @Override
    public byte[] encode() {
        return ByteBuffer.allocate(9).putInt(0, this.dimID).putInt(4, this.playerID).put(8, (this.hasChip ? this.chipCfg : -1)).array();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput byteArrayDataInput) {
        byte[] ret = new byte[9];
        byteArrayDataInput.readFully(ret);
        return new CircuitProgrammerPacket(ByteBuffer.wrap(ret).getInt(0), ByteBuffer.wrap(ret).getInt(4), ByteBuffer.wrap(ret).get(8) > -1, ByteBuffer.wrap(ret).get(8));
    }

    @Override
    public void process(IBlockAccess iBlockAccess) {
        World w = DimensionManager.getWorld(this.dimID);
        if (w != null && w.getEntityByID(this.playerID) instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) w.getEntityByID(this.playerID)).getHeldItem();
            if ((stack != null) && (stack.stackSize > 0)) {
                Item item = stack.getItem();
                if (item instanceof CircuitProgrammer) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    nbt.setBoolean("HasChip", this.hasChip);
                    if (this.hasChip)
                        nbt.setByte("ChipConfig", this.chipCfg);
                    stack.setTagCompound(nbt);
                    ((EntityPlayer) w.getEntityByID(this.playerID)).inventory.setInventorySlotContents(((EntityPlayer) w.getEntityByID(this.playerID)).inventory.currentItem, stack);
                }
            }
        }
    }
}
