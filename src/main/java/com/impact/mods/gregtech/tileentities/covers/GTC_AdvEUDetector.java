package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.covers.CoverContext;

public class GTC_AdvEUDetector extends DetectorCover {

    public GTC_AdvEUDetector(CoverContext context) {
        super(context);
    }

    @Override
    public boolean isRange() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return false;
    }

    @Override
    public int getMinimumTickRate() {
        return 20;
    }

    @Override
    public void doCoverThings(byte redstone, long timer) {
        EnergyDetectorData tanks = EnergyDetectorData.read(getTile());
        int percent = (int) ((double) tanks.stored / tanks.capacity * 100d);
        boolean active = setting > 0 ? percent >= setting : percent <= Math.abs(setting);
        getTile().setOutputRedstoneSignal((byte) coverSide.ordinal(), (byte) (active ? 15 : 0));
    }
}
