package com.impact.common.te;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TE_NqTether extends TileEntity {

    private boolean coreActive;

    public boolean shouldRenderBeam() {
        if (worldObj == null || worldObj.getBlock(xCoord, yCoord + 2, zCoord) != Block_QuantumStuff.INSTANCE) {
            return false;
        }
        // Liquid Naquadah keeps its core, but must never render the vertical tether beam.
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = worldObj.getTileEntity(xCoord + direction.offsetX * 3,
                    yCoord + 2 + direction.offsetY * 3, zCoord + direction.offsetZ * 3);
            if (tile instanceof IGregTechTileEntity) {
                IGregTechTileEntity controller = (IGregTechTileEntity) tile;
                if (controller.getMetaTileEntity() instanceof GTMTE_LiquidNqGenerator
                        && controller.getBackFacing() == direction.getOpposite().ordinal()) {
                    return false;
                }
            }
        }
        return true;
    }

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
