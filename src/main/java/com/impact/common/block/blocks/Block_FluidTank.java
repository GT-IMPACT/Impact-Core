package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_FluidTank;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Block_FluidTank extends gtUpdateBlockAPI {

  private static final Block_FluidTank instance = new Block_FluidTank();

  private IIcon iconStorage0;
  private IIcon iconStorage1;
  private IIcon iconStorage2;
  private IIcon iconStorage3;
  private IIcon iconStorage4;
  private IIcon iconStorage5;
  private IIcon iconStorage6;
  private IIcon iconStorage7;

  private Block_FluidTank() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_fluidtankblock_block";
    instance.setBlockName(blockName);
    instance.setHardness(5.0f);
    instance.setResistance(6.0f);
    GameRegistry.registerBlock(instance, IB_FluidTank.class, blockName);
    return instance;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    iconStorage0 = ir.registerIcon("impact:FluidTankBlock0");
    iconStorage1 = ir.registerIcon("impact:FluidTankBlock1");
    iconStorage2 = ir.registerIcon("impact:FluidTankBlock2");
    iconStorage3 = ir.registerIcon("impact:FluidTankBlock3");
    iconStorage4 = ir.registerIcon("impact:FluidTankBlock4");
    iconStorage5 = ir.registerIcon("impact:FluidTankBlock5");
    iconStorage6 = ir.registerIcon("impact:FluidTankBlock6");
    iconStorage7 = ir.registerIcon("impact:FluidTankBlock7");
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list) {
      for (int i = 0; i <= 7; i++) {
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
      case 3:
        return iconStorage3;
      case 4:
        return iconStorage4;
      case 5:
        return iconStorage5;
      case 6:
        return iconStorage6;
      case 7:
        return iconStorage7;
      default:
        return null;
    }
  }

}
