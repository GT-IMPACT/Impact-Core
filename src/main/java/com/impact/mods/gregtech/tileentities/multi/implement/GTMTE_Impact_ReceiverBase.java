package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.addon.gt.api.parallel_system.INetworkMachine;
import com.impact.addon.gt.api.parallel_system.SatelliteNetworkLogic;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class GTMTE_Impact_ReceiverBase<MULTIS extends GTMTE_Impact_ReceiverBase<MULTIS>> extends GTMTE_Impact_BlockBase<MULTIS> implements INetworkMachine {
	
	protected boolean isSatelliteConnected;

	public GTMTE_Impact_ReceiverBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GTMTE_Impact_ReceiverBase(int aID, String aName, String aNameRegional, int slots) {
		super(aID, aName, aNameRegional, slots);
	}
	
	public GTMTE_Impact_ReceiverBase(String aName, int slots) {
		super(aName, slots);
	}
	
	public GTMTE_Impact_ReceiverBase(String aName) {
		super(aName);
	}
	
	@Override
	public void inValidate() {
		if (getBaseMetaTileEntity().isServerSide()) removeConnect();
		super.inValidate();
	}

	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		if (aBaseMetaTileEntity.isServerSide()) createConnect();
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("isConnected", isSatelliteConnected);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		isSatelliteConnected = aNBT.getBoolean("isConnected");
	}

	@Override
	public boolean isSatelliteConnected() {
		return isSatelliteConnected;
	}

	@Override
	public void setSatelliteConnected(boolean connect) {
		isSatelliteConnected = connect;
	}

	public abstract boolean hasConnectForce();
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (hasConnectForce() && !isSatelliteConnected()) {
			IGregTechTileEntity te = getBaseMetaTileEntity();
			if (te != null) {
				te.disableWorking();
			}
		}
		return super.onRunningTick(aStack);
	}

	@Override
	public void createConnect() {
		SatelliteNetworkLogic.INSTANCE.reloadNetwork();
	}

	@Override
	public void removeConnect() {
		SatelliteNetworkLogic.INSTANCE.reloadNetwork();
	}
}
