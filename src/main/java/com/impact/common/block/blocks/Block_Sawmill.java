package com.impact.common.block.blocks;

import com.impact.common.block.itemblock.IB_SawMill;
import com.impact.core.Refstrings;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class Block_Sawmill extends gtUpdateBlockAPI {

  private static final Block_Sawmill instance = new Block_Sawmill();
  @SideOnly(Side.CLIENT)
  protected IIcon[] texture;
  protected String name;
  String[] textureNames;
  @SideOnly(Side.CLIENT)
  private IIcon[] connectedTexture;
  private IIcon sideConv;
  private boolean connectedTex = true;

  private Block_Sawmill() {
    super(Material.iron);
  }

  public static Block registerBlock() {
    final String blockName = "impact_sawmill_block";
    instance.setBlockName(blockName);
    instance.setHardness(5.0f);
    instance.setResistance(6.0f);
    instance.textureNames = new String[]{Refstrings.MODID + ":sawmill/conveyor"};
    GameRegistry.registerBlock(instance, IB_SawMill.class, blockName);
    return instance;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord,
      int aSide) {
      if (worldClient.getBlock(xCoord, yCoord, zCoord) instanceof Block_Sawmill) {
          return true;
      }
    return super.shouldSideBeRendered(worldClient, xCoord, yCoord, zCoord, aSide);
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public IIcon getIcon(int aSide, int aMeta) {
    return aMeta < this.texture.length ? aSide == 1 ? this.texture[aMeta] : sideConv
        : aSide == 1 ? this.texture[0] : sideConv;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon(IBlockAccess worldClient, int xCoord, int yCoord, int zCoord, int aSide) {
      if (!this.connectedTex) {
          return super.getIcon(worldClient, xCoord, yCoord, zCoord, aSide);
      }

    ForgeDirection dir = ForgeDirection.getOrientation(aSide);
    byte sides = 0;
    switch (dir) {
      case UP:
      case DOWN: {
          if (worldClient.getBlock(xCoord, yCoord, zCoord - 1).equals(this)) {
              sides = (byte) (sides | 0b0001);
          }
          if (worldClient.getBlock(xCoord, yCoord, zCoord + 1).equals(this)) {
              sides = (byte) (sides | 0b0010);
          }
          if (worldClient.getBlock(xCoord - 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0100);
          }
          if (worldClient.getBlock(xCoord + 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b1000);
          }
        break;
      }
      case EAST: {
          if (worldClient.getBlock(xCoord, yCoord + 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0001);
          }
          if (worldClient.getBlock(xCoord, yCoord - 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0010);
          }
          if (worldClient.getBlock(xCoord, yCoord, zCoord + 1).equals(this)) {
              sides = (byte) (sides | 0b0100);
          }
          if (worldClient.getBlock(xCoord, yCoord, zCoord - 1).equals(this)) {
              sides = (byte) (sides | 0b1000);
          }
        break;
      }
      case WEST: {
          if (worldClient.getBlock(xCoord, yCoord + 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0001);
          }
          if (worldClient.getBlock(xCoord, yCoord - 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0010);
          }
          if (worldClient.getBlock(xCoord, yCoord, zCoord - 1).equals(this)) {
              sides = (byte) (sides | 0b0100);
          }
          if (worldClient.getBlock(xCoord, yCoord, zCoord + 1).equals(this)) {
              sides = (byte) (sides | 0b1000);
          }
        break;

      }
      case NORTH: {
          if (worldClient.getBlock(xCoord, yCoord + 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0001);
          }
          if (worldClient.getBlock(xCoord, yCoord - 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0010);
          }
          if (worldClient.getBlock(xCoord + 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0100);
          }
          if (worldClient.getBlock(xCoord - 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b1000);
          }
        break;
      }
      case SOUTH: {
          if (worldClient.getBlock(xCoord, yCoord + 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0001);
          }
          if (worldClient.getBlock(xCoord, yCoord - 1, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0010);
          }
          if (worldClient.getBlock(xCoord - 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b0100);
          }
          if (worldClient.getBlock(xCoord + 1, yCoord, zCoord).equals(this)) {
              sides = (byte) (sides | 0b1000);
          }
        break;
      }
      case UNKNOWN:
      default:
        break;
    }
    return aSide == 1 ? this.connectedTexture[sides] : sideConv;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister par1IconRegister) {
    if (!this.connectedTex) {
      this.texture = new IIcon[this.textureNames.length];
      for (int i = 0; i < this.textureNames.length; i++) {
        this.texture[i] = par1IconRegister.registerIcon(this.textureNames[i]);
      }
      return;
    }

    this.texture = new IIcon[this.textureNames.length];
    this.connectedTexture = new IIcon[16];
    for (int i = 0; i < this.textureNames.length; i++) {
      this.texture[i] = par1IconRegister.registerIcon(this.textureNames[i]);
      for (int j = 0; j < 16; j++) {
        this.connectedTexture[j] = par1IconRegister.registerIcon(this.textureNames[i] + "_" + j);
      }
    }
    sideConv = par1IconRegister.registerIcon(Refstrings.MODID + ":sawmill/conveyor_side");
  }

  @Override
  @SideOnly(Side.CLIENT)
  public int getRenderBlockPass() {
    return 1;
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

  @Override
  public int damageDropped(int meta) {
    return meta;
  }
}