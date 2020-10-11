package com.impact.common.block.netherportal;

import net.minecraft.block.BlockPortal;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNullPortal extends BlockPortal {

    public void updateTick(World world, int p1, int p2, int p3, Random rand) {
    }

    public boolean func_150000_e(World world, int p1, int p2, int p3) {
        return false;
    }
}