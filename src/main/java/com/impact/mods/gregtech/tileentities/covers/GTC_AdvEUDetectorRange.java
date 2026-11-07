package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.covers.CoverContext;

public class GTC_AdvEUDetectorRange extends DetectorCover {

    public GTC_AdvEUDetectorRange(CoverContext context) {
        super(context);
    }

    @Override
    public boolean isRange() {
        return true;
    }

    @Override
    public boolean isFluid() {
        return false;
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
        EnergyDetectorData tanks = EnergyDetectorData.read(getTile());
        // Preserve the existing energy-range conversion while migrating the cover API.
        int start = (int) ((double) getStartPercent() / 100 * tanks.capacity);
        int end = (int) ((double) getEndPercent() / 100 * tanks.capacity);
        boolean active = start <= tanks.stored && tanks.stored <= end;
        getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) (active ? 15 : 0));
    }
}
