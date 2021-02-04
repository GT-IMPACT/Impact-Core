package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_Ceramic;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Block_Ceramic extends gtUpdateBlockAPI {

  private static final Block_Ceramic instance = new Block_Ceramic();

  private IIcon iconStorage0;
  private IIcon iconStorage1;
  private IIcon iconStorage2;

  private Block_Ceramic() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_ceramic_block";
    instance.setBlockName(blockName);
    instance.setHardness(5.0f);
    instance.setResistance(6.0f);
    GameRegistry.registerBlock(instance, IB_Ceramic.class, blockName);
    return instance;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    iconStorage0 = ir.registerIcon("impact:CeramicBlock0");
    iconStorage1 = ir.registerIcon("impact:CeramicBlock1");
    iconStorage2 = ir.registerIcon("impact:CeramicBlock2");

  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
    for (int i = 0; i <= 2; i++) {
      list.add(new ItemStack(par1, 1, i));
    }
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return iconStorage0;
      case 1:
        return iconStorage1;
      case 2:
        return iconStorage2;
      default:
        return null;
    }
  }

}
