package com.impact.mods.GregTech.blocks;

import com.impact.mods.GregTech.blocks.IB_Base;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class IB_Casing_2
        extends IB_Base {
    public IB_Casing_2(Block par1) {
        super(par1);
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        aList.add(this.mNoMobsToolTip);
        aList.add(this.mNoTileEntityToolTip);
        switch (getDamage(aStack)) {
            case 5:
                aList.add(this.mConfiguration3Dprinter3x3);
                break;
            case 6:
                aList.add(this.mConfiguration3Dprinter4x4);
                break;
        }
    }
}
