package com.impact.mods.gregtech.gui.spacesatellite;

import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Transmitter;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.ICommunicatorConnect;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.ISatelliteConnect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_SpaceSatelliteHatches extends GT_ContainerMetaTile_Machine {
	
	public int mFrequency = 0;
	public int mTargetX = 0;
	public int mTargetY = 0;
	public int mTargetZ = 0;
	public int mTargetD = 0;
	public int mIsEnable = 0;
	
	public Container_SpaceSatelliteHatches(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity);
	}
	
	@Override
	public void addSlots(InventoryPlayer aPlayerInventory) {
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 8, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 26, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 55, false, false, 1));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
		if (aSlotIndex < 0) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		
		Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
		if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {
			
			if (this.mTileEntity.getMetaTileEntity() instanceof ISatelliteConnect) {
				
				ISatelliteConnect transmitter = (ISatelliteConnect) mTileEntity.getMetaTileEntity();
				
				int aFrequency = transmitter.getFrequency();
				if (aSlotIndex == 0) {
					aFrequency += (aShifthold == 1 ? 10 : 1);
					transmitter.updateFrequency(aFrequency);
					return null;
				} else if (aSlotIndex == 1) {
					aFrequency -= (aShifthold == 1 ? 10 : 1);
					if (aFrequency < 0) {
						aFrequency = 0;
					}
					transmitter.updateFrequency(aFrequency);
					return null;
				} else if (aSlotIndex == 2) {
					transmitter.onFindConnect(aFrequency, aPlayer);
				}
			}
			if (this.mTileEntity.getMetaTileEntity() instanceof ICommunicatorConnect) {
				
				ICommunicatorConnect receiver = (ICommunicatorConnect) mTileEntity.getMetaTileEntity();
				
				int aFrequency = receiver.getFrequency();
				if (aSlotIndex == 0) {
					aFrequency += (aShifthold == 1 ? 10 : 1);
					receiver.updateFrequency(aFrequency);
					return null;
				} else if (aSlotIndex == 1) {
					aFrequency -= (aShifthold == 1 ? 10 : 1);
					if (aFrequency < 0) {
						aFrequency = 0;
					}
					receiver.updateFrequency(aFrequency);
					return null;
				} else if (aSlotIndex == 2) {
					receiver.onConnected(aFrequency, aPlayer);
				}
			}
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
			return;
		}
		
		if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_SpaceSatellite_Transmitter) {
			
			GTMTE_SpaceSatellite_Transmitter transmitter = (GTMTE_SpaceSatellite_Transmitter) mTileEntity.getMetaTileEntity();
			
			this.mTargetX = mTileEntity.getXCoord();
			this.mTargetY = mTileEntity.getYCoord();
			this.mTargetZ = mTileEntity.getZCoord();
			this.mTargetD = mTileEntity.getWorld().provider.dimensionId;
			
			this.mFrequency = transmitter.getFrequency();
			this.mIsEnable = transmitter.mIsTransmit ? 1 : 0;
		}
		
		if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_CommunicationTower_Receiver) {
			
			GTMTE_CommunicationTower_Receiver transmitter = (GTMTE_CommunicationTower_Receiver) mTileEntity.getMetaTileEntity();
			
			this.mTargetX = transmitter.mTargetX;
			this.mTargetY = transmitter.mTargetY;
			this.mTargetZ = transmitter.mTargetZ;
			this.mTargetD = transmitter.mTargetD;
			
			this.mFrequency = transmitter.mFrequency;
			this.mIsEnable = transmitter.mIsReceive ? 1 : 0;
		}
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, this.mTargetX & 0xFFFF);
			var1.sendProgressBarUpdate(this, 101, this.mTargetX >>> 16);
			
			var1.sendProgressBarUpdate(this, 102, this.mTargetY & 0xFFFF);
			var1.sendProgressBarUpdate(this, 103, this.mTargetY >>> 16);
			
			var1.sendProgressBarUpdate(this, 104, this.mTargetZ & 0xFFFF);
			var1.sendProgressBarUpdate(this, 105, this.mTargetZ >>> 16);
			
			var1.sendProgressBarUpdate(this, 106, this.mTargetD & 0xFFFF);
			var1.sendProgressBarUpdate(this, 107, this.mTargetD >>> 16);
			
			var1.sendProgressBarUpdate(this, 108, this.mFrequency & 0xFFFF);
			var1.sendProgressBarUpdate(this, 109, this.mFrequency >>> 16);
			
			var1.sendProgressBarUpdate(this, 110, this.mIsEnable);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		switch (par1) {
			case 100:
				this.mTargetX = (this.mTargetX & 0xFFFF0000 | par2);
				break;
			case 101:
				this.mTargetX = (this.mTargetX & 0xFFFF | par2 << 16);
				break;
			case 102:
				this.mTargetY = (this.mTargetY & 0xFFFF0000 | par2);
				break;
			case 103:
				this.mTargetY = (this.mTargetY & 0xFFFF | par2 << 16);
				break;
			case 104:
				this.mTargetZ = (this.mTargetZ & 0xFFFF0000 | par2);
				break;
			case 105:
				this.mTargetZ = (this.mTargetZ & 0xFFFF | par2 << 16);
				break;
			case 106:
				this.mTargetD = (this.mTargetD & 0xFFFF0000 | par2);
				break;
			case 107:
				this.mTargetD = (this.mTargetD & 0xFFFF | par2 << 16);
				break;
			case 108:
				this.mFrequency = (this.mFrequency & 0xFFFF0000 | par2);
				break;
			case 109:
				this.mFrequency = (this.mFrequency & 0xFFFF | par2 << 16);
				break;
			case 110:
				this.mIsEnable = par2;
		}
	}
}
