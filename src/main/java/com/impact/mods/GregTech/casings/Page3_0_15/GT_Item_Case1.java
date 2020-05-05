package com.impact.mods.GregTech.casings.Page3_0_15;

import com.impact.mods.GregTech.casings.GT_Item_Casings_Abstract;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Item_Case1
        extends GT_Item_Casings_Abstract {
    public GT_Item_Case1(Block par1) {
        super(par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        aList.add(this.mNoMobsToolTip);
        aList.add(this.mNoTileEntityToolTip);
        switch (getDamage(aStack)) {
            case 0:
                aList.add(this.mUpgrade1Tooltip);
                break;
            case 1:
                aList.add(this.mUpgrade2Tooltip);
                break;
            case 2:
                aList.add(this.mUpgrade3Tooltip);
                break;
            case 3:
                aList.add(this.mUpgrade4Tooltip);
                break;

        }
    }
}
