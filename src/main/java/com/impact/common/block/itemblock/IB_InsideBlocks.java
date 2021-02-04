package com.impact.common.block.itemblock;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class IB_InsideBlocks extends ItemBlock {

  public IB_InsideBlocks(Block block) {
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
    lines.add("Use as GT Casing");
  }
}
