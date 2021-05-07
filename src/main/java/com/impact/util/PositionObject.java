package com.impact.util;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;

public class PositionObject {
	
	public int xPos;
	public int yPos;
	public int zPos;
	public int dPos;
	public EntityPlayer player;
	public String playerName;
	public String nameLocation = "";
	
	public PositionObject(IGregTechTileEntity igt) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	public PositionObject(IGregTechTileEntity igt, String name) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		nameLocation = name;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	public void setNameLocation(String aName) {
		nameLocation = aName;
	}
}
