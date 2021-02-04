package com.impact.common.item;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.GT_Values;
import ic2.core.IC2;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class WoodBrickFormTool extends Item {

  public static WoodBrickFormTool WoodBrickFormTool = new WoodBrickFormTool();
  private IIcon icons;

  private WoodBrickFormTool() {
  }

  public static WoodBrickFormTool getInstance() {
    return WoodBrickFormTool;
  }

  public void registerItem() {
    super.setHasSubtypes(true);
    final String unlocalizedName = "impact_WoodBrickFormTool";
    super.setUnlocalizedName(unlocalizedName);
    super.setMaxStackSize(1);
    super.setMaxDamage(99);
    super.setNoRepair();
    GameRegistry.registerItem(getInstance(), unlocalizedName);
  }

  public boolean hasContainerItem(ItemStack stack) {
    return true;
  }

  public ItemStack getContainerItem(ItemStack stack) {
    ItemStack ret = stack.copy();
    ret.attemptDamageItem(1, IC2.random);
    return ret;
  }

  public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
    return false;
  }

  @Override
  public void registerIcons(IIconRegister reg) {
    icons = reg.registerIcon(Refstrings.MODID + ":impact_metatool.01/4");
  }

  @Override
  public IIcon getIconFromDamage(int meta) {
    return icons;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    for (int i = 0; i < 1; i++) {
      list.add(new ItemStack(item, 1, i));
    }
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return super.getUnlocalizedName();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
    list.add("Reusable: " + (stack.getMaxDamage() - stack.getItemDamage() + 1));
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    return 0.0d;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    return false;
  }

  public ItemStack getRecipe(int amount) {
    return new ItemStack(getInstance(), amount, 0);
  }

  public ItemStack get() {
    return new ItemStack(getInstance(), 1, GT_Values.W);
  }
}
