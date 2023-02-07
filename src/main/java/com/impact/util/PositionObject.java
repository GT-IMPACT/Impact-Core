package com.impact.util;

import com.impact.api.position.IPosition;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public class PositionObject implements IPosition {
	
	public int xPos = 0;
	public int yPos = 0;
	public int zPos = 0;
	public int dPos = 0;
	public EntityPlayer player = null;
	public String playerName = "";
	public String nameLocation = "";
	
	/**
	 * @param igt - IGregTechTileEntity
	 */
	public PositionObject(IGregTechTileEntity igt) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	/**
	 * @param x - x Position
	 * @param y - y Position
	 * @param z - z Position
	 */
	public PositionObject(int x, int y, int z) {
		xPos = x;
		yPos = y;
		zPos = z;
	}
	
	public PositionObject(int x, int y, int z, int d) {
		xPos = x;
		yPos = y;
		zPos = z;
		dPos = d;
	}
	
	/**
	 * only 4 coords!
	 * @param coords - x, y, z, d Position
	 */
	public PositionObject(int[] coords) {
		xPos = coords[0];
		yPos = coords[1];
		zPos = coords[2];
		dPos = coords[3];
	}
	
	/**
	 * @param igt - IGregTechTileEntity
	 * @param name - Name Position
	 */
	public PositionObject(IGregTechTileEntity igt, String name) {
		xPos = igt.getXCoord();
		yPos = igt.getYCoord();
		zPos = igt.getZCoord();
		dPos = igt.getWorld().provider.dimensionId;
		nameLocation = name;
		player = igt.getWorld().getPlayerEntityByName(igt.getOwnerName());
		playerName = igt.getOwnerName();
	}
	
	/**
	 * @param obj - PositionObject copy method
	 */
	private PositionObject(PositionObject obj) {
		xPos = obj.xPos;
		yPos = obj.yPos;
		zPos = obj.zPos;
		dPos = obj.dPos;
		player = obj.player;
		playerName = obj.playerName;
		nameLocation = obj.nameLocation;
	}
	
	public Vector3ic toVec3() {
		return new Vector3i(xPos, yPos, zPos);
	}
	
	public Vector3i toVec3i() {
		return new Vector3i(xPos, yPos, zPos);
	}
	
	/**
	 * @return Integer Array with coords
	 */
	public int[] getCoords() {
		return new int[] {xPos, yPos, zPos, dPos};
	}
	
	/**
	 * @return PositionObject copy
	 */
	public PositionObject copy() {
		return new PositionObject(this);
	}
	
	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePosition(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.xPos == obj2.xPos && obj1.yPos == obj2.yPos && obj1.zPos == obj2.zPos;
	}
	
	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePositionWithDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos && checkComparePosition(obj1, obj2);
	}
	
	/**
	 * @param obj1 - PositionObject source
	 * @param obj2 - PositionObject target
	 * @return boolean
	 */
	public static boolean checkComparePositionOnlyDim(PositionObject obj1, PositionObject obj2) {
		if (obj1 == null || obj2 == null) return false;
		return obj1.dPos == obj2.dPos;
	}
	
	/**
	 * @param igt - IGregTechTileEntity
	 * @param obj - PositionObject source
	 * @return IGregTechTileEntity
	 */
	public static IGregTechTileEntity getIGregTechTileEntity(IGregTechTileEntity igt, PositionObject obj) {
		if (obj == null) return null;
		return igt.getIGregTechTileEntity(obj.xPos, obj.yPos, obj.zPos);
	}
	
	@Override
	public int getX() {
		return xPos;
	}
	
	@Override
	public int getY() {
		return yPos;
	}
	
	@Override
	public int getZ() {
		return zPos;
	}
	
	@Override
	public int getD() {
		return dPos;
	}
	
	@NotNull
	@Override
	public NBTTagCompound saveToNBT() {
		NBTTagCompound pos = new NBTTagCompound();
		pos.setInteger("xPos", xPos);
		pos.setInteger("yPos", yPos);
		pos.setInteger("zPos", zPos);
		pos.setInteger("dPos", dPos);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("position_object", pos);
		return tag;
	}
	
	@NotNull
	public static IPosition loadFromNBT(@NotNull NBTTagCompound nbt) {
		NBTTagCompound pos = nbt.getCompoundTag("position_object");
		int xPos = pos.getInteger("xPos");
		int yPos = pos.getInteger("yPos");
		int zPos = pos.getInteger("zPos");
		int dPos = pos.getInteger("dPos");
		return new PositionObject(xPos, yPos, zPos, dPos);
	}
	
	@Override
	public boolean isEquals(@NotNull IPosition pos) {
		return getX() == pos.getX() && getY() == pos.getY()
				&& getZ() == pos.getZ() && getD() == pos.getD();
	}
	
	
}