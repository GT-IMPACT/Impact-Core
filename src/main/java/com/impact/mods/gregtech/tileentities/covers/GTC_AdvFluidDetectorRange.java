package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.covers.CoverContext;

public class GTC_AdvFluidDetectorRange extends DetectorCover {

    public GTC_AdvFluidDetectorRange(CoverContext context) {
        super(context);
    }

    @Override
    public boolean isRange() {
        return true;
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
        if (timer % 20 != 0) {
            return;
        }
        FluidDetectorTankData tanks = FluidDetectorTankData.read(getTile());
        if (tanks.capacity == 0) {
            getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) 0);
            return;
        }
        long start = (long) ((double) getStartPercent() / 100 * tanks.capacity);
        long end = (long) ((double) getEndPercent() / 100 * tanks.capacity);
        boolean active = start <= tanks.amount && tanks.amount <= end;
        getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) (active ? 15 : 0));
    }
}
