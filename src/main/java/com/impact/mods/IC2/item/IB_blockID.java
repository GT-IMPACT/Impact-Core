package com.impact.mods.IC2.item;

import ic2.core.item.block.ItemBlockIC2;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class IB_blockID extends ItemBlockIC2 {
    public IB_blockID(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int i) {
        return i;
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }

    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        int meta = itemStack.getItemDamage();
        switch(meta) {
            case 0:
                info.add("Region work: 11x11x7");
                info.add("Capacity: 100,000 EU");
                info.add("Passive consume: 1 EU/s");
                break;
        }
    }

}