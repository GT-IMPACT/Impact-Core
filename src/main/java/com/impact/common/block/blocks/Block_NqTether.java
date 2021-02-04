package com.impact.common.block.blocks;

import com.impact.common.te.TE_NqTether;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Block_NqTether extends gtUpdateBlockAPI {

  public static final Block_NqTether INSTANCE = new Block_NqTether();

  private IIcon on;
  private IIcon off;

  private Block_NqTether() {
    super(Material.glass);
  }

  public static Block registerBlock() {
    final String blockName = "impact_nqtether_block";
    INSTANCE.setBlockName(blockName);
    INSTANCE.setHardness(15.0f);
    INSTANCE.setResistance(15.0f);
    GameRegistry.registerBlock(INSTANCE, blockName);

    return INSTANCE;
  }

  @Override
  public void registerBlockIcons(IIconRegister ir) {
    on = ir.registerIcon("impact:CoreOn");
    off = ir.registerIcon("impact:CoreOff");
  }

  public IIcon getIcon(int aSide, int aMeta) {
    return this.off;
  }

  @Override
  public IIcon getIcon(IBlockAccess aWorld, int posX, int posY, int posZ, int aSide) {
      if (aWorld.getBlock(posX, posY + 2, posZ) == Block_QuantumStuff.INSTANCE) {
          return this.on;
      }
      if (aWorld.getBlock(posX, posY, posZ - 2) == Block_QuantumStuff.INSTANCE
          || aWorld.getBlock(posX, posY, posZ + 2) == Block_QuantumStuff.INSTANCE
          || aWorld.getBlock(posX - 2, posY, posZ) == Block_QuantumStuff.INSTANCE
          || aWorld.getBlock(posX + 2, posY, posZ) == Block_QuantumStuff.INSTANCE
      ) {
          return this.on;
      }
    return this.off;
  }

  @Override
  public TileEntity createTileEntity(World world, int par1) {
    return new TE_NqTether();
  }

  @Override
  public boolean hasTileEntity(int metadata) {
    return true;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

}
