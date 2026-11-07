package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.covers.CoverContext;

public class GTC_AdvFluidDetectorBound extends DetectorCover {

    public GTC_AdvFluidDetectorBound(CoverContext context) {
        super(context);
    }

    @Override
    public boolean isRange() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return true;
    }

    @Override
    public int getMinimumTickRate() {
        return 5;
    }

    @Override
    public void doCoverThings(byte redstone, long timer) {
        FluidDetectorTankData tanks = FluidDetectorTankData.read(getTile());
        if (tanks.capacity == 0) {
            getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) 0);
            return;
        }
        if (tanks.capacity < setting) {
            setting = (int) tanks.capacity;
        }
        boolean active = setting > 0 ? tanks.amount >= setting : tanks.amount <= Math.abs((long) setting);
        getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) (active ? 15 : 0));
    }
}
