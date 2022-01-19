package com.impact.mods.gregtech.gui.laptop;

import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
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

public class Container_LapTopSetting extends GT_ContainerMetaTile_Machine {
	
	public int mFrequency = 0, mTargetX = 0, mTargetY = 0, mTargetZ = 0, mTargetD = 0, mIsEnable = 0;
	
	public Container_LapTopSetting(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
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
			
			if (mTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_MultiParallelBlockBase<?>) {
				GT_MetaTileEntity_MultiParallelBlockBase<?> base = (GT_MetaTileEntity_MultiParallelBlockBase<?>) mTileEntity.getMetaTileEntity();
				int aFrequency = base.mFrequency;
				if (aSlotIndex == 0) {
					base.mFrequency += (aShifthold == 1 ? 10 : 1);
					return null;
				} else if (aSlotIndex == 1) {
					base.mFrequency -= (aShifthold == 1 ? aFrequency - 10 < 0 ? 0 : 10 : aFrequency - 1 < 0 ? 0 : 1);
					return null;
				} else if (aSlotIndex == 2) {
					if (base instanceof GTMTE_TowerCommunication) {
						base.setFrequency(base.mFrequency, aPlayer);
					} else {
						base.getFrequency(base.mFrequency, aPlayer);
					}
				}
				return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
			}
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if ((this.mTileEntity.isClientSide()) && (this.mTileEntity.getMetaTileEntity() == null)) {
			return;
		}
		if (this.mTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_MultiParallelBlockBase<?>) {
			this.mTargetX   = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mTargetX;
			this.mTargetY   = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mTargetY;
			this.mTargetZ   = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mTargetZ;
			this.mTargetD   = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mTargetD;
			this.mFrequency = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mFrequency;
			this.mIsEnable  = ((GT_MetaTileEntity_MultiParallelBlockBase<?>) this.mTileEntity.getMetaTileEntity()).mIsConnect ? 1 : 0;
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
			
			var1.sendProgressBarUpdate(this, 110, this.mIsEnable & 0xFFFF);
			var1.sendProgressBarUpdate(this, 111, this.mIsEnable >>> 16);
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
				this.mIsEnable = (this.mIsEnable & 0xFFFF0000 | par2);
				break;
			case 111:
				this.mIsEnable = (this.mIsEnable & 0xFFFF | par2 << 16);
				break;
		}
	}
}