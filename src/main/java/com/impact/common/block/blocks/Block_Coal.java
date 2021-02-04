package com.impact.common.block.blocks;

import static com.impact.core.Refstrings.MODID;
import static com.impact.util.Utilits.ItemstackMeta;

import com.impact.common.block.itemblock.IB_Coal;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class Block_Coal extends gtUpdateBlockAPI {

  public static final Block_Coal CoalBlock = new Block_Coal();

  private IIcon CokeCoal0;
  private IIcon CoalCoke1;
  private IIcon CoalCoke2;
  private IIcon CoalCoke3;
  private IIcon CoalCoke4;

  private IIcon Charcoal1;
  private IIcon Charcoal2;
  private IIcon Charcoal3;
  private IIcon Charcoal4;

  private IIcon Coal1;
  private IIcon Coal2;
  private IIcon Coal3;
  private IIcon Coal4;


  private Block_Coal() {
    super(Material.rock);
  }

  public static Block registerBlock() {
    final String blockName = "impact_coal";
    CoalBlock.setBlockName(blockName);
    CoalBlock.setHardness(5.0f);
    CoalBlock.setResistance(6.0f);
    GameRegistry.registerBlock(CoalBlock, IB_Coal.class, blockName);
    return CoalBlock;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    CokeCoal0 = ir.registerIcon(MODID + ":" + "CokeCoal");
    CoalCoke1 = ir.registerIcon(MODID + ":" + "CompressedCoalCoke");
    CoalCoke2 = ir.registerIcon(MODID + ":" + "DoubleCompressedCoalCoke");
    CoalCoke3 = ir.registerIcon(MODID + ":" + "TripleCompressedCoalCoke");
    CoalCoke4 = ir.registerIcon(MODID + ":" + "QuadrupleCompressedCoalCoke");

    Charcoal1 = ir.registerIcon(MODID + ":" + "CompressedCharcoal");
    Charcoal2 = ir.registerIcon(MODID + ":" + "DoubleCompressedCharcoal");
    Charcoal3 = ir.registerIcon(MODID + ":" + "TripleCompressedCharcoal");
    Charcoal4 = ir.registerIcon(MODID + ":" + "QuadrupleCompressedCharcoal");

    Coal1 = ir.registerIcon(MODID + ":" + "CompressedCoal");
    Coal2 = ir.registerIcon(MODID + ":" + "DoubleCompressedCoal");
    Coal3 = ir.registerIcon(MODID + ":" + "TripleCompressedCoal");
    Coal4 = ir.registerIcon(MODID + ":" + "QuadrupleCompressedCoal");
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
    par3List.add(ItemstackMeta(par1, 0));
    par3List.add(ItemstackMeta(par1, 1));
    par3List.add(ItemstackMeta(par1, 2));
    par3List.add(ItemstackMeta(par1, 3));
    par3List.add(ItemstackMeta(par1, 4));
    par3List.add(ItemstackMeta(par1, 5));
    par3List.add(ItemstackMeta(par1, 6));
    par3List.add(ItemstackMeta(par1, 7));
    par3List.add(ItemstackMeta(par1, 8));
    par3List.add(ItemstackMeta(par1, 9));
    par3List.add(ItemstackMeta(par1, 10));
    par3List.add(ItemstackMeta(par1, 11));
    par3List.add(ItemstackMeta(par1, 12));
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return CokeCoal0;
      case 1:
        return CoalCoke1;
      case 2:
        return CoalCoke2;
      case 3:
        return CoalCoke3;
      case 4:
        return CoalCoke4;
      case 5:
        return Charcoal1;
      case 6:
        return Charcoal2;
      case 7:
        return Charcoal3;
      case 8:
        return Charcoal4;
      case 9:
        return Coal1;
      case 10:
        return Coal2;
      case 11:
        return Coal3;
      case 12:
        return Coal4;
      default:
        return null;
    }
  }

}
