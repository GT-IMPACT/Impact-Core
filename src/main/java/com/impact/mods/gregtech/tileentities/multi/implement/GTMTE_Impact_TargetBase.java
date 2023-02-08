package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.util.PositionObject;
import net.minecraft.nbt.NBTTagCompound;

public abstract class GTMTE_Impact_TargetBase<MULTIS extends GTMTE_Impact_TargetBase<MULTIS>> extends GTMTE_Impact_BlockBase<MULTIS> {
	
	protected int mTargetX = 0;
	protected int mTargetY = 0;
	protected int mTargetZ = 0;
	protected int mTargetD = 0;
	protected boolean isConnected = false;
	
	public GTMTE_Impact_TargetBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GTMTE_Impact_TargetBase(int aID, String aName, String aNameRegional, int slots) {
		super(aID, aName, aNameRegional, slots);
	}
	
	public GTMTE_Impact_TargetBase(String aName, int slots) {
		super(aName, slots);
	}
	
	public GTMTE_Impact_TargetBase(String aName) {
		super(aName);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mTargetX", mTargetX);
		aNBT.setInteger("mTargetY", mTargetY);
		aNBT.setInteger("mTargetZ", mTargetZ);
		aNBT.setInteger("mTargetD", mTargetD);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mTargetX = aNBT.getInteger("mTargetX");
		mTargetY = aNBT.getInteger("mTargetY");
		mTargetZ = aNBT.getInteger("mTargetZ");
		mTargetD = aNBT.getInteger("mTargetD");
	}
	
	protected void setCoord(PositionObject pos) {
		mTargetX = pos.xPos;
		mTargetY = pos.yPos;
		mTargetZ = pos.zPos;
		mTargetD = pos.dPos;
	}
}
