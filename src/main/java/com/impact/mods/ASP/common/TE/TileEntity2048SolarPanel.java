package com.impact.mods.asp.common.te;

public class TileEntity2048SolarPanel extends TileEntitySolarPanel {

  public TileEntity2048SolarPanel() {
    super("blockEVSolarPanel.name", 5, 2048);
  }

  public String getInvName() {
    return "EV Solar Panel";
  }
}
