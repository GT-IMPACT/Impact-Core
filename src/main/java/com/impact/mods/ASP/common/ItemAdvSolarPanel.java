package com.impact.mods.asp.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemAdvSolarPanel extends ItemBlock {

  private List<String> itemNames;

  public ItemAdvSolarPanel(Block b) {
    super(b);
    this.setMaxDamage(0);
    this.setHasSubtypes(true);
    this.itemNames = new ArrayList();
    this.addItemsNames();
  }

  public int getMetadata(int i) {
    return i;
  }

  public String getUnlocalizedName(ItemStack itemstack) {
    return this.itemNames.get(itemstack.getItemDamage());
  }

  public void addItemsNames() {
    this.itemNames.add("blockULVSolarPanel");
    this.itemNames.add("blockLVSolarPanel");
    this.itemNames.add("blockMVSolarPanel");
    this.itemNames.add("blockHVSolarPanel");
    this.itemNames.add("blockEVSolarPanel");
    this.itemNames.add("blockIVSolarPanel");
    this.itemNames.add("blockLuvSolarPanel");
    this.itemNames.add("blockZPMSolarPanel");
    this.itemNames.add("blockUVSolarPanel");
    this.itemNames.add("blockUHVSolarPanel");
    this.itemNames.add("blockUEVSolarPanel");
  }

  @SuppressWarnings("unchecked")
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
    super.addInformation(aStack, aPlayer, aList, aF3_H);
    aList.add("Blocked Solar Panel");
  }

  @SideOnly(Side.CLIENT)
  public EnumRarity getRarity(ItemStack itemstack) {
    return EnumRarity.common;
  }
}

