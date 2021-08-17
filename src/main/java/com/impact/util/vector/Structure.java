package com.impact.util.vector;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;

import java.util.ArrayList;

public class Structure {

    public static boolean doCheck(boolean checker, IGregTechTileEntity iAm, Vector3ic offset, Block block, int meta) {
        if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) != block) && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) != meta)) checker = false;
        return checker;
    }

    public static boolean doCheck(boolean checker, IGregTechTileEntity iAm, Vector3ic offset, Block block) {
        if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) != block)) checker = false;
        return checker;
    }

    public static boolean doSizeHatchesEqual(boolean checker, ArrayList<?> hatches, int amount) {
        if (hatches.size() == amount) checker = true;
        return checker;
    }

    public static boolean doSizeHatchesLess(boolean checker, ArrayList<?> hatches, int amount) {
        if (hatches.size() < amount) checker = true;
        return checker;
    }

    public static boolean doSizeHatchesGreater(boolean checker, ArrayList<?> hatches, int amount) {
        if (hatches.size() > amount) checker = true;
        return checker;
    }

    public static void setBlock(IGregTechTileEntity te, int x, int y, int z, Block block, int meta) {
        int posX = te.getXCoord() + x;
        int posY = te.getYCoord() + y;
        int posZ = te.getZCoord() + z;
        te.getWorld().setBlock(posX, posY, posZ, block, meta, 2);
    }

    public static void setBlock(IGregTechTileEntity te, Vector3ic offset, Block block, int meta) {
        te.getWorld().setBlock(offset.x(), offset.y(), offset.z(), block, meta, 2);
    }

    public static void setBlock(IGregTechTileEntity te, int x, int y, int z, Block block) {
        setBlock(te, x, y, z, block, 0);
    }
}