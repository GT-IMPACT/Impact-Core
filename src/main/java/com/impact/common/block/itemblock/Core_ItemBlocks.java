package com.impact.common.block.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Core_ItemBlocks extends ItemBlock {

  public Core_ItemBlocks(Block par1) {
    super(par1);
    this.setMaxDamage(0);
    this.setHasSubtypes(true);

  }

  @Override
  public int getMetadata(int aMeta) {
    return aMeta;
  }

  @Override
  public String getUnlocalizedName(ItemStack aStack) {
    return this.field_150939_a.getUnlocalizedName() + "." + this.getDamage(aStack);
  }

  @Override
  @SideOnly(Side.CLIENT)
  @SuppressWarnings("unchecked")
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(ItemStack stack, int pass) {
    return this.field_150939_a.getIcon(0, stack.getItemDamage());
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem,
      int useRemaining) {
    return this.getIcon(stack, renderPass);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
    return this.field_150939_a.getIcon(0, p_77618_2_);
  }

}
