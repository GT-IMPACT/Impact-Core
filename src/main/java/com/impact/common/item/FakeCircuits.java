package com.impact.common.item;

import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FakeCircuits extends Item {

  public static int x = 10; //количество предметов

  private static FakeCircuits Core_Items2 = new FakeCircuits();
  private final IIcon[] icons = new IIcon[x + 1];

  private FakeCircuits() {
  }

  public static FakeCircuits getInstance() {
    return Core_Items2;
  }

  public void registerItem() {
    super.setHasSubtypes(true);
    final String unlocalizedName = "impact_fake";
    super.setUnlocalizedName(unlocalizedName);
    super.setMaxStackSize(64);
    GameRegistry.registerItem(getInstance(), unlocalizedName);
  }

  @Override
  public void registerIcons(IIconRegister reg) {
      for (int i = 0; i <= x; i++) {
          icons[i] = reg.registerIcon(Refstrings.MODID + ":impact_metaitem.02/" + i);
      }
  }

  @Override
  public IIcon getIconFromDamage(int meta) {
    return icons[meta];
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    for (int i = 0; i < icons.length; i++) {
      list.add(new ItemStack(item, 1, i));
    }
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return super.getUnlocalizedName() + "." + stack.getItemDamage();
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
    list.add("Its Fake Circuit for recipes info");
  }

  @Override
  public double getDurabilityForDisplay(ItemStack stack) {
    return 0.0d;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
    return false;
  }

  public ItemStack getStackFromDamage(int meta) {
    return new ItemStack(getInstance(), 1, meta);
  }

  public ItemStack getRecipe(int meta, int amount) {
    return new ItemStack(getInstance(), amount, meta);
  }

}
