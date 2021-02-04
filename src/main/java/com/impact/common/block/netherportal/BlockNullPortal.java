package com.impact.common.block.netherportal;

import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.world.World;

public class BlockNullPortal extends BlockPortal {

  public void updateTick(World world, int p1, int p2, int p3, Random rand) {
  }

  public boolean func_150000_e(World world, int p1, int p2, int p3) {
    return false;
  }
}