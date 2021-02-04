package com.impact.common.block.itemblock;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class IB_Ceramic extends ItemBlock {

  public IB_Ceramic(Block block) {
    super(block);
  }

  @Override
  public int getMetadata(int meta) {
    return meta;
  }

  @Override
  public boolean getHasSubtypes() {
    return true;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return super.getUnlocalizedName() + "." + stack.getItemDamage();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
      boolean advancedTooltips) {
    switch (stack.getItemDamage()) {
      case 0:
        lines.add("Yttria-Stabilized Zirconia Electrolyte");
        lines.add("For Tier 1 SOFC");
        break;
      case 1:
        lines.add("Gadolinium Doped Ceria Electrolyte");
        lines.add("For Tier 2 SOFC");
        break;
      case 2:
        lines.add("Lanthanum Strontium Cobalt Ferrite Electrolyte");
        lines.add("For Tier 3 SOFC");
        break;
    }
  }
}
