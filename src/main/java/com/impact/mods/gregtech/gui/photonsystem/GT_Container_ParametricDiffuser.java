package com.impact.mods.gregtech.gui.photonsystem;

import com.impact.mods.gregtech.tileentities.multi.photonsystem.GTMTE_ParametricDiffuser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_ParametricDiffuser extends GT_ContainerMetaTile_Machine {

	public boolean checkStabilizer;
	public int mPeakBeamPhotons;
	public int mPhotonsGenerate;

	public GT_Container_ParametricDiffuser(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity, false);
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 5, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 37, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 55, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 73, false, false, 1));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
		if (aSlotIndex < 0 || aSlotIndex > 5) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
		if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
			if (aSlotIndex == 0) {
				if (mTileEntity.isAllowedToWork()) {
					GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
					mTileEntity.disableWorking();
				} else {
					GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
					mTileEntity.enableWorking();
				}
			}
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
		GTMTE_ParametricDiffuser diffuser = ((GTMTE_ParametricDiffuser) mTileEntity.getMetaTileEntity());
		mPeakBeamPhotons = diffuser.mPeakBeamPhotons;
		mPhotonsGenerate = diffuser.mPhotonsGenerate;
		checkStabilizer = diffuser.mPhotonStabilizer != null && diffuser.mPhotonStabilizer.getBaseMetaTileEntity().isActive();
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, mPeakBeamPhotons & 65535);
			var1.sendProgressBarUpdate(this, 101, mPeakBeamPhotons >>> 16);
			var1.sendProgressBarUpdate(this, 102, mPhotonsGenerate & 65535);
			var1.sendProgressBarUpdate(this, 103, mPhotonsGenerate >>> 16);
			var1.sendProgressBarUpdate(this, 104, checkStabilizer ? 1 : 0);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
			case 100:
				mPeakBeamPhotons = mPeakBeamPhotons & -65536 | data;
				break;
			case 101:
				mPeakBeamPhotons = mPeakBeamPhotons & 65535 | data << 16;
			case 102:
				mPhotonsGenerate = mPhotonsGenerate & -65536 | data;
				break;
			case 103:
				mPhotonsGenerate = mPhotonsGenerate & 65535 | data << 16;
			case 104:
				checkStabilizer = (data != 0);
				break;
		}
	}
}
