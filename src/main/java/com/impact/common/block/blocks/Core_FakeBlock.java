package com.impact.common.block.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class Core_FakeBlock extends Core_GTBlocks {

  String[] textureNames;
  @SideOnly(Side.CLIENT)
  private IIcon[] texture;
  private String name;

  public Core_FakeBlock(String name, String[] texture, int MassiveForReg) {
    super(name, texture, null, Material.glass);
    setHarvestLevel("pickaxe", 1);
    setHardness(1);
    setResistance(1);
    setStepSound(Block.soundTypeStone);
    this.name = name;
    this.textureNames = texture;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord,
      int aSide) {
      if (worldClient.getBlock(xCoord, yCoord, zCoord) instanceof Core_FakeBlock) {
          return false;
      }
    return super.shouldSideBeRendered(worldClient, xCoord, yCoord, zCoord, aSide);
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
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord, int aSide) {
    return super.getIcon(worldClient, xCoord, yCoord, zCoord, aSide);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public int getRenderBlockPass() {
    return 1;
  }

  @Override
  public int getRenderType() {
    return 0;
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
