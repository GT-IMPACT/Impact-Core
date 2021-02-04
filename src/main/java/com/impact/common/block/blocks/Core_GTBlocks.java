package com.impact.common.block.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

abstract class Core_GTBlocks extends gtUpdateBlockAPI {

  @SideOnly(Side.CLIENT)
  protected IIcon[] texture;
  protected String name;
  String[] textureNames;

  public Core_GTBlocks(String name, String[] texture) {
    super(Material.anvil);
    this.setHardness(15.0F);
    this.setResistance(30.0F);
    this.name = name;
    this.textureNames = texture;
  }

  public Core_GTBlocks(String name, String[] texture, CreativeTabs tabs) {
    super(Material.anvil);
    this.setHardness(15.0F);
    this.setResistance(30.0F);
    this.name = name;
    this.textureNames = texture;
    this.setCreativeTab(tabs);
  }

  public Core_GTBlocks(String name, String[] texture, CreativeTabs tabs, Material material) {
    super(material);
    this.setHardness(15.0F);
    this.setResistance(30.0F);
    this.name = name;
    this.textureNames = texture;
    this.setCreativeTab(tabs);
  }

  public Core_GTBlocks(String name, String[] texture, int MassiveForReg) {
    super(Material.anvil);
    setHarvestLevel("pickaxe", 1);
    setHardness(1);
    setResistance(1);
    setStepSound(Block.soundTypeStone);
    this.name = name;
    this.textureNames = texture;
  }

  @Override
  public int damageDropped(int meta) {
    return meta;
  }

  @Override
  @SideOnly(Side.CLIENT)
  @SuppressWarnings("unchecked")
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    for (int i = 0; i < this.textureNames.length; i++) {
      list.add(new ItemStack(item, 1, i));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int side, int meta) {
    return meta < this.texture.length ? this.texture[meta] : this.texture[0];
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister par1IconRegister) {
    this.texture = new IIcon[this.textureNames.length];
    for (int i = 0; i < this.textureNames.length; i++) {
      this.texture[i] = par1IconRegister.registerIcon(this.textureNames[i]);
    }
  }

  @Override
  public String getUnlocalizedName() {
    return this.name;
  }

  @Override
  public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
    return false;
  }

  @Override
  public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
    return false;
  }

  @Override
  public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
    return false;
  }

}
