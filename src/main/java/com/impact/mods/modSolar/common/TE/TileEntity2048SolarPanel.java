package com.impact.mods.modSolar.common.TE;

public class TileEntity2048SolarPanel extends TileEntitySolarPanel {
    public TileEntity2048SolarPanel() {
        super("blockEVSolarPanel.name", 5, 2048);
    }

    public String getInvName() {
        return "EV Solar Panel";
    }
}
