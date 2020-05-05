package com.impact.mods.GregTech.casings.Page3_16_31;

import com.impact.mods.GregTech.casings.GT_Item_Casings_Abstract;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Item_Case2
        extends GT_Item_Casings_Abstract {
    public GT_Item_Case2(Block par1) {
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
