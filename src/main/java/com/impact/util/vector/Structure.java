package com.impact.util.vector;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class Structure {

    public static boolean doCheck(boolean checker, IGregTechTileEntity iAm, Vector3ic offset, Block block, int meta) {
        if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) != block) && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) != meta))
            checker = false;
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
        setBlock(te, offset.x(), offset.y(), offset.z(), block, meta);
    }

    public static Block getBlock(IGregTechTileEntity te, Vector3ic offset) {
        return te.getBlockOffset(offset.x(), offset.y(), offset.z());
    }

    public static Block getBlock(IGregTechTileEntity te, int x, int y, int z) {
        return te.getBlockOffset(x, y, z);
    }

    public static void setAir(IGregTechTileEntity te, Vector3ic offset) {
        setBlock(te, offset.x(), offset.y(), offset.z(), Blocks.air, 0);
    }

    public static void setBlock(IGregTechTileEntity te, int x, int y, int z, Block block) {
        setBlock(te, x, y, z, block, 0);
    }

    public static IGregTechTileEntity getIGTE(IGregTechTileEntity iAm, Vector3ic offset) {
        return iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
    }

    public static TileEntity getTE(IGregTechTileEntity iAm, Vector3ic offset) {
        return iAm.getTileEntityOffset(offset.x(), offset.y(), offset.z());
    }

    public static Vector3ic getForgeDirection(IGregTechTileEntity iAm) {
        return new Vector3i(ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX, ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY, ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);
    }

    public static Vector3ic goBuild(IGregTechTileEntity iAm, int x, int y, int z) {
        Vector3ic forgeDirection = Structure.getForgeDirection(iAm);
        final Vector3i offset = new Vector3i();
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x; offset.y = y; offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x; offset.y = y; offset.z = -z;
        }
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z; offset.y = y; offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z; offset.y = y; offset.z = x;
        }
        if (forgeDirection.y() == -1) {
            offset.x = x; offset.y = z; offset.z = y;
        }
        return offset;
    }
}