package com.impact.util;

import com.impact.common.block.itemblock.IB_IGlass;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.io.IOException;

public class ToggleMetaData implements IZTPacket {
    private boolean incDamage = false;

    public ToggleMetaData() {
    }

    public ToggleMetaData(boolean incDamage) {
        this.incDamage = incDamage;
    }

    @Override
    public void processData(EntityPlayer player, ByteBufInputStream buf) throws IOException {
        ItemStack stack = player.getHeldItem();
        if (stack != null) {
            boolean incDmg = buf.readBoolean();

            if (stack.getItem() instanceof IB_IGlass) {
                int maxDmg = 15;
                int newDamage = stack.getItemDamage();
                newDamage = newDamage + (incDmg ? 1 : -1);
                if (newDamage > maxDmg)
                    newDamage = 0;
                else if (newDamage < 0)
                    newDamage = maxDmg;

                stack.setItemDamage(newDamage);
            }
        }
    }

    @Override
    public void appendData(ByteBuf buffer) throws IOException {
        buffer.writeBoolean(this.incDamage);
    }
}