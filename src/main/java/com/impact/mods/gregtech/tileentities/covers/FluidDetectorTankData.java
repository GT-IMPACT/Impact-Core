package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

final class FluidDetectorTankData {
    final long capacity;
    final long amount;

    private FluidDetectorTankData(long capacity, long amount) {
        this.capacity = capacity;
        this.amount = amount;
    }

    static FluidDetectorTankData read(ICoverable tile) {
        long capacity = 0;
        long amount = 0;
        if (tile instanceof IFluidHandler) {
            FluidTankInfo[] tanks = ((IFluidHandler) tile).getTankInfo(ForgeDirection.UNKNOWN);
            if (tanks != null) {
                for (FluidTankInfo tank : tanks) {
                    if (tank != null && tank.capacity > 0) {
                        capacity += tank.capacity;
                        if (tank.fluid != null) {
                            amount += tank.fluid.amount;
                        }
                    }
                }
            }
        }
        return new FluidDetectorTankData(capacity, amount);
    }

    long guiCapacity() {
        // GUI percentage calculations must also work on blocks without tanks.
        return Math.max(1L, capacity);
    }
}
