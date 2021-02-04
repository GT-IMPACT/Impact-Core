package com.impact.common.block.blocks;

import static com.impact.core.Refstrings.MODID;
import static com.impact.util.Utilits.ItemstackMeta;

import com.impact.common.block.itemblock.IB_IGlass;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class Block_IGlass extends gtUpdateBlockAPI {

  public static final Block_IGlass IGlass = new Block_IGlass();

  private IIcon iconGlass0;   // white
  private IIcon iconGlass1;   // orange
  private IIcon iconGlass2;   // magenta
  private IIcon iconGlass3;   // light blue
  private IIcon iconGlass4;   // yellow
  private IIcon iconGlass5;   // lime
  private IIcon iconGlass6;   // pink
  private IIcon iconGlass7;   // gray
  private IIcon iconGlass8;   // light gray
  private IIcon iconGlass9;   // cyan
  private IIcon iconGlass10;  // purple
  private IIcon iconGlass11;  // blue
  private IIcon iconGlass12;  // brown
  private IIcon iconGlass13;  // green
  private IIcon iconGlass14;  // red
  private IIcon iconGlass15;  // black

  private Block_IGlass() {
    super(Material.glass);
  }

  public static Block registerBlock() {
    final String blockName = "impact_glass";
    IGlass.setBlockName(blockName);
    IGlass.setHardness(5.0f);
    IGlass.setResistance(6.0f);
    IGlass.setHarvestLevel("wrench", 0);
    IGlass.setStepSound(Block.soundTypeGlass);
    GameRegistry.registerBlock(IGlass, IB_IGlass.class, blockName);
    return IGlass;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    iconGlass0 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB0");
    iconGlass1 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB1");
    iconGlass2 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB2");
    iconGlass3 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB3");
    iconGlass4 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB4");
    iconGlass5 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB5");
    iconGlass6 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB6");
    iconGlass7 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB7");
    iconGlass8 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB8");
    iconGlass9 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB9");
    iconGlass10 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB10");
    iconGlass11 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB11");
    iconGlass12 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB12");
    iconGlass13 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB13");
    iconGlass14 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB14");
    iconGlass15 = ir.registerIcon(MODID + ":" + "glass/" + "blockGB15");
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
    par3List.add(ItemstackMeta(par1, 13));
    par3List.add(ItemstackMeta(par1, 14));
    par3List.add(ItemstackMeta(par1, 15));
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return iconGlass0;
      case 1:
        return iconGlass1;
      case 2:
        return iconGlass2;
      case 3:
        return iconGlass3;
      case 4:
        return iconGlass4;
      case 5:
        return iconGlass5;
      case 6:
        return iconGlass6;
      case 7:
        return iconGlass7;
      case 8:
        return iconGlass8;
      case 9:
        return iconGlass9;
      case 10:
        return iconGlass10;
      case 11:
        return iconGlass11;
      case 12:
        return iconGlass12;
      case 13:
        return iconGlass13;
      case 14:
        return iconGlass14;
      case 15:
        return iconGlass15;
      default:
        return null;
    }
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord,
      int aSide) {
      if (worldClient.getBlock(xCoord, yCoord, zCoord).equals(this)) {
          return false;
      }
    return super.shouldSideBeRendered(worldClient, xCoord, yCoord, zCoord, aSide);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public int getRenderBlockPass() {
    return 1;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  protected boolean canSilkHarvest() {
    return false;
  }
}
