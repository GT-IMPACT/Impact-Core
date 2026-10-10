package com.impact.common.te;

import com.impact.common.block.blocks.Block_NqTether;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TE_NqTether extends TileEntity {

  private boolean coreActive;

  @Override
  public void updateEntity() {
    if (worldObj == null || !worldObj.isRemote) return;
    // The core is two blocks away, outside the normal block-update render range.
    boolean active = Block_NqTether.INSTANCE.getIcon(worldObj, xCoord, yCoord, zCoord, 0)
        != Block_NqTether.INSTANCE.getIcon(0, 0);
    if (active != coreActive) {
      coreActive = active;
      worldObj.markBlockRangeForRenderUpdate(xCoord - 2, yCoord - 2, zCoord - 2,
          xCoord + 2, yCoord + 2, zCoord + 2);
    }
  }


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
