package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.api.parallelsystem.IParallelIn;
import com.impact.api.parallelsystem.IParallelOut;
import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.*;
import com.impact.util.PositionObject;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

import static com.impact.core.Refstrings.MODID;

public abstract class GT_MetaTileEntity_MultiParallelBlockBase<MULTIS extends GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>>
		extends GTMTE_Impact_ReceiverBase<MULTIS> {
	
	public final HashSet<GTMTE_ParallelHatch_Input> sParallHatchesIn = new HashSet<>();
	public final HashSet<GTMTE_ParallelHatch_Output> sParallHatchesOut = new HashSet<>();
	public final HashSet<GTMTE_ComputerRack> sComputerRack = new HashSet<>();
	
	public boolean isConnectParallel = false;
	public int mParallel = 0;
	public int mCheckParallelCurrent = 0;
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final int aID, final String aName, final String aNameRegional, int slots) {
		super(aID, aName, aNameRegional, slots);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final String aName, int slots) {
		super(aName, slots);
	}
	
	public GT_MetaTileEntity_MultiParallelBlockBase(final String aName) {
		super(aName);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setInteger("mParallel", this.mParallel);
		aNBT.setInteger("modeBuses", this.modeBuses);
		aNBT.setInteger("mCheckParallelCurrent", this.mCheckParallelCurrent);
		super.saveNBTData(aNBT);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		this.mParallel             = aNBT.getInteger("mParallel");
		this.modeBuses             = aNBT.getInteger("modeBuses");
		this.mCheckParallelCurrent = aNBT.getInteger("mCheckParallelCurrent");
		super.loadNBTData(aNBT);
	}
	
	@Override
	public String[] addInfoData() {
		final ArrayList<String> ll = new ArrayList<>();
		ll.add(mParallel > 1 ? "Parallel Point: " + mParallel : "Parallel not found");
		final String[] a = new String[ll.size()];
		return ll.toArray(a);
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		PositionObject pos = new PositionObject(iAm);
		if (!aPlayer.isSneaking()) {
			if (sParallHatchesIn.size() > 0 || iAm.getMetaTileEntity() instanceof GTMTE_ParallelComputer) {
				aPlayer.openGui(MODID, GUIHandler.GUI_ID_LapTop, iAm.getWorld(), pos.xPos, pos.yPos, pos.zPos);
			}
		}
	}
	
	public boolean addParallHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Input) {
				((GTMTE_ParallelHatch_Input) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sParallHatchesIn.add((GTMTE_ParallelHatch_Input) aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GTMTE_ParallelHatch_Output) {
				((GTMTE_ParallelHatch_Output) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sParallHatchesOut.add((GTMTE_ParallelHatch_Output) aMetaTileEntity);
			} else {
				return false;
			}
		}
	}
	
	public boolean addRackHatch(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_ComputerRack) {
				((GTMTE_ComputerRack) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sComputerRack.add((GTMTE_ComputerRack) aMetaTileEntity);
			} else {
				return false;
			}
		}
	}
	
	public int getParallelCurrent() {
		return mParallel;
	}
	
	public int getParallel() {
		return mParallel;
	}
	
	public void setParallel(int setParallel) {
		mParallel = setParallel;
	}
	
	@Override
	public boolean hasConnectForce() {
		return false;
	}
	
	@Override
	public void clearHatches() {
		super.clearHatches();
		sParallHatchesOut.clear();
		sParallHatchesIn.clear();
		sComputerRack.clear();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 20 == 0) {
//			connectParallelHatches();
//			connectParallelComputer(iAm);
		}
	}


//	public void connectParallelHatches() {
//		int maxParallel = 1;
//		boolean isDebug = false;
//		if (sParallHatchesIn.size() > 0) {
//			for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) {
//				maxParallel = ph.getMaxParallel();
//				setRecipeCheckParallel(ph.getTrueRecipe());
//				isDebug        = ph.isDebug;
//				ph.machineName = getLocalName();
//			}
//			if (isDebug) {
//				setRecipeCheckParallel(true);
//				isConnected = true;
//				setParallel(maxParallel);
//				return;
//			}
//			if (getRecipeCheckParallel() || !isConnected) {
//				maxParallel = 1;
//			}
//		}
//		setParallel(maxParallel);
//	}

//	public void connectParallelComputer(IGregTechTileEntity iAm) {
//		isConnected = false;
//		boolean isDebug = false;
//		if (sParallHatchesIn.size() > 0 || iAm.getMetaTileEntity() instanceof GTMTE_ParallelComputer) {
//			for (GTMTE_ParallelHatch_Input ph : sParallHatchesIn) isDebug = ph.isDebug;
//			if (isDebug) {
//				isConnected = true;
//				return;
//			}
//			tile = iAm.getIGregTechTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
//			if (tile != null && tile.getMetaTileEntity() instanceof GTMTE_TowerCommunication) {
//				GTMTE_TowerCommunication tower = (GTMTE_TowerCommunication) tile.getMetaTileEntity();
//				if (tower.getBaseMetaTileEntity().isActive() && mFrequency == tower.mFrequency) {
//					isConnected = tower.isConnected;
//				}
//			}
//		}
//	}
}