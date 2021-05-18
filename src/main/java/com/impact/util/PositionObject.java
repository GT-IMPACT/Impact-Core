package com.impact.util;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;

public class PositionObject {
	
	public int xPos = 0;
	public int yPos = 0;
	public int zPos = 0;
	public int dPos = 0;
	public EntityPlayer player = null;
	public String playerName = "";
	public String nameLocation = "";
	
	public PositionObject(IGregTechTileEntity igt) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	public PositionObject(int x, int y, int z) {
		xPos = x;
		yPos = y;
		zPos = z;
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
	
	private PositionObject(PositionObject obj) {
		xPos = obj.xPos;
		yPos = obj.yPos;
		zPos = obj.zPos;
		dPos = obj.dPos;
		player = obj.player;
		playerName = obj.playerName;
		nameLocation = obj.nameLocation;
	}
	
	public static boolean checkComparePosition(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.xPos == obj2.xPos && obj1.yPos == obj2.yPos && obj1.zPos == obj2.zPos;
	}
	
	public static boolean checkComparePositionWithDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos && checkComparePosition(obj1, obj2);
	}
	
	public static boolean checkComparePositionOnlyDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos;
	}
	
	public static IGregTechTileEntity getIGregTechTileEntity(IGregTechTileEntity igt, PositionObject obj) {
		if (obj == null) return null;
		return igt.getIGregTechTileEntity(obj.xPos, obj.yPos, obj.zPos);
	}
	
	public PositionObject copy() {
		return new PositionObject(this);
	}
}