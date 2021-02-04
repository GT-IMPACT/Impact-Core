package com.impact.common.block.blocks;

import static com.impact.core.Refstrings.MODID;
import static com.impact.util.Utilits.ItemstackMeta;

import com.impact.common.block.itemblock.IB_Util;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class Block_UtilBlock extends Block {

  public static final Block_UtilBlock UtilBlock = new Block_UtilBlock();

  private IIcon iconConcrete;
  private IIcon iconPiston;
  private IIcon iconFullWhite;


  private Block_UtilBlock() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_util";
    UtilBlock.setBlockName(blockName);
    UtilBlock.setHardness(5.0f);
    UtilBlock.setResistance(6.0f);
    GameRegistry.registerBlock(UtilBlock, IB_Util.class, blockName);
    return UtilBlock;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    iconConcrete = ir.registerIcon(MODID + ":" + "Concrete");
    iconPiston = ir.registerIcon(MODID + ":" + "PistonBlock");
    iconFullWhite = ir.registerIcon(MODID + ":" + "FullWhite");
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
    par3List.add(ItemstackMeta(par1, 0));
    par3List.add(ItemstackMeta(par1, 1));
    par3List.add(ItemstackMeta(par1, 2));
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return iconConcrete;
      case 1:
        return iconPiston;
      case 2:
        return iconFullWhite;
      default:
        return null;
    }
  }

  @Override
  public int damageDropped(int meta) {
    return meta;
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
