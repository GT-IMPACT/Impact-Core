package com.impact.common.te;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TE_SpaceElevatorTether extends TileEntity {

    public int getBeamHeight() {
        if (worldObj == null) {
            return 0;
        }
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (!(tile instanceof IGregTechTileEntity)) {
            return 0;
        }
        IGregTechTileEntity controller = (IGregTechTileEntity) tile;
        if (!(controller.getMetaTileEntity() instanceof GTMTE_SpaceElevator) || !controller.isActive()) {
            return 0;
        }
        return worldObj.provider.dimensionId == 0 ? 256 : -256;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared() {
        // 4k is standard, 65k is what the vanilla beacon uses
        return 65536.0D;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        // Make it so the beam is still rendered even when the source block is out of sight
        return INFINITE_EXTENT_AABB;
    }
}
