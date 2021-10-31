package com.impact.mods.gregtech.gui.nuclear;

import com.impact.mods.gregtech.gui.slot.CustomSlot;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_Reactor_Rod extends GT_ContainerMetaTile_Machine {
	
	public int mDownRod, mID;
	
	public GT_Container_Reactor_Rod(InventoryPlayer aInventoryPlayer,
									IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity);
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new CustomSlot(this.mTileEntity, 0, 90, 8, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 70, 8, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 70, 62, false, false, 1));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
							   EntityPlayer aPlayer) {
		MetaTileEntity mte = (MetaTileEntity) this.mTileEntity.getMetaTileEntity();
		GTMTE_Reactor_Rod_Hatch rod_hatch = (GTMTE_Reactor_Rod_Hatch) mte;
		
		if (aSlotIndex < 1) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
		if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {
			
			switch (aSlotIndex) {
				case 1:
					rod_hatch.mDownRod -= 1;
					if (rod_hatch.mDownRod < 0) {
						rod_hatch.mDownRod = 0;
					}
					if (aShifthold == 1) {
						rod_hatch.mDownRod = 0;
					}
					return null;
				case 2:
					rod_hatch.mDownRod += (aShifthold == 1 ? 10 : 1);
					if (rod_hatch.mDownRod > 10) {
						rod_hatch.mDownRod = 10;
					}
					if (aShifthold == 1) {
						rod_hatch.mDownRod = 10;
					}
					return null;
			}
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public int getSlotCount() {
		return 1;
	}
	
	@Override
	public int getShiftClickSlotCount() {
		return 1;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
		this.mDownRod = ((GTMTE_Reactor_Rod_Hatch) mTileEntity.getMetaTileEntity()).mDownRod;
		this.mID = ((GTMTE_Reactor_Rod_Hatch) mTileEntity.getMetaTileEntity()).mIDhatch;
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, mDownRod & 65535);
			var1.sendProgressBarUpdate(this, 101, mDownRod >>> 16);
			var1.sendProgressBarUpdate(this, 102, mID & 65535);
			var1.sendProgressBarUpdate(this, 103, mID >>> 16);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		switch (par1) {
			case 100:
				mDownRod = mDownRod & -65536 | par2;
				break;
			case 101:
				mDownRod = mDownRod & 65535 | par2 << 16;
				break;
			case 102:
				mID = mID & -65536 | par2;
				break;
			case 103:
				mID = mID & 65535 | par2 << 16;
				break;
		}
	}
	
}
