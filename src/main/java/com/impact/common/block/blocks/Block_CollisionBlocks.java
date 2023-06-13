package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_InsideBlocks;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block_CollisionBlocks extends GTUpdateBlockAPI {

  private static final Block_CollisionBlocks instance = new Block_CollisionBlocks();

  private IIcon MoonDigSide, MoonDigTop;

  private Block_CollisionBlocks() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_collision_block";
    instance.setBlockName(blockName);
    instance.setHardness(1.0f);
    instance.setResistance(1.0f);
    GameRegistry.registerBlock(instance, IB_InsideBlocks.class, blockName);
    return instance;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    MoonDigTop = ir.registerIcon("impact:CycloneTop");
    MoonDigSide = ir.registerIcon("impact:CycloneActive");
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public void getSubBlocks(Item item, CreativeTabs tab, List l) {
    //for (byte i = 0; i < 1; i++)
    l.add(new ItemStack(item, 1, 0));
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    switch (meta) {
      case 0:
        return (side < 2) ? MoonDigTop : MoonDigSide;
      default:
        return null;
    }
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  /**
   * Returns a bounding box from the pool of bounding boxes (this means this box can change after
   * the pool has been cleared to be reused)
   */

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    int meta = world.getBlockMetadata(x, y, z);
    if (meta == 0) {
      /* Возвращаем ограничивающую рамку с указанными границами. Args: minX, minY, minZ, maxX, maxY, maxZ */
      return AxisAlignedBB
          .getBoundingBox((double) x + 0.200F, (double) y + this.minY, (double) z + 0.200F,
              (double) x + 0.800F, (double) y + this.maxY, (double) z + 0.800F);
    } else {
      return AxisAlignedBB
          .getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ,
              (double) x + this.maxX, ((float) y + this.maxY), (double) z + this.maxZ);
    }
  }

  /**
   * Updates the blocks bounds based on its current state. Args: world, x, y, z
   */

  @Override
  public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    if (world.getBlockMetadata(x, y, z) == 0) {
      /* Устанавливаем границы блока.  minX, minY, minZ, maxX, maxY, maxZ */
      this.setBlockBounds(0.200F, 0.0F, 0.200F, 0.800F, 1.0F, 0.800F);
    } else {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
  }

  /**
   * Sets the block's bounds for rendering it as an item
   */
  public void setBlockBoundsForItemRender() {
    this.setBlockBounds(0.200F, 0.0F, 0.200F, 0.800F, 1.0F, 0.800F);
  }
  
  @Override
  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    return new ArrayList<>();
  }
}
