package com.impact.mods.gregtech.blocks.itemblocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IB_Casing_1
    extends IB_Base {

  public IB_Casing_1(Block par1) {
    super(par1);
  }

  @SuppressWarnings("unchecked")
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    if (getDamage(aStack) > 3) {
      aList.add(this.mNoMobsToolTip);
      aList.add(this.mNoTileEntityToolTip);
    }
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
