package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.api.satellite.IDistributor;
import com.impact.api.satellite.IReceiver;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public abstract class GTMTE_Impact_ReceiverBase<MULTIS extends GTMTE_Impact_ReceiverBase<MULTIS>> extends GTMTE_Impact_BlockBase<MULTIS> implements IReceiver {
	
	protected boolean isConnected;
	protected IDistributor distributor = null;
	
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
		onDisconnect();
		super.inValidate();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("isConnected", isConnected);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		isConnected = aNBT.getBoolean("isConnected");
	}
	
	@Override
	public void updateConnectionStatus(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	@Override
	public boolean getConnectionStatus() {
		return isConnected;
	}
	
	@Override
	public boolean isValid() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		return te != null && !te.isDead();
	}
	
	public abstract boolean hasConnectForce();
	
	@Override
	public boolean onRunningTick(ItemStack aStack) {
		if (hasConnectForce() && !getConnectionStatus()) {
			IGregTechTileEntity te = getBaseMetaTileEntity();
			if (te != null) {
				te.disableWorking();
			}
		}
		return super.onRunningTick(aStack);
	}
	
	@Override
	public void onDisconnect() {
		if (distributor != null) {
			distributor.disconnect(this);
			distributor = null;
			isConnected = false;
		}
	}
	
	@Override
	public void createConnect(@NotNull IDistributor distributor) {
		this.distributor = distributor;
	}
}
