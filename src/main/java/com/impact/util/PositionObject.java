package com.impact.util;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class PositionObject {
	
	public int xPos;
	public int yPos;
	public int zPos;
	public int dPos;
	public String nameLocation = "";
	
	public PositionObject(IGregTechTileEntity igt) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
	}
	
	public PositionObject(IGregTechTileEntity igt, String name) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		nameLocation = name;
	}
	
	public void setNameLocation(String aName) {
		nameLocation = aName;
	}
}
