package com.impact.common.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TE_NqTether extends TileEntity {


  @Override
  @SideOnly(Side.CLIENT)
  public double getMaxRenderDistanceSquared() {
    // 4k is standard, 65k is what the vanilla beacon uses
    return 8000.0D;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getRenderBoundingBox() {
    // Make it so the beam is still rendered even when the source block is out of sight
    return INFINITE_EXTENT_AABB;
  }
}
