package com.impact.mods.gregtech.gui.filler;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Filler;
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

public class Container_Construction_Laser extends GT_ContainerMetaTile_Machine {
	
	public boolean saveMode;
	public boolean killMobs;
	public boolean isDone;
	public int yCurrent;
	
	public Container_Construction_Laser(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity);
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new Slot(mTileEntity, 0, 44, 62));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1, 152, 62, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 8, 62, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 3, 26, 62, false, false, 1));
		
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
		if (aSlotIndex < 1 || aSlotIndex > 4) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
		if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
			GTMTE_Filler filler = (GTMTE_Filler) mTileEntity.getMetaTileEntity();
			if (aSlotIndex == 1) {
				if (filler.getBaseMetaTileEntity().isAllowedToWork()) {
					GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
					filler.getBaseMetaTileEntity().disableWorking();
					return null;
				} else {
					GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
					filler.getBaseMetaTileEntity().enableWorking();
					return null;
				}
			} else if (aSlotIndex == 2) {
				filler.saveTiles = !filler.saveTiles;
				GT_Utility.sendChatToPlayer(aPlayer, "Save TileEntities: " + (filler.saveTiles ?"Enabled" : "Disabled"));
				return null;
			} else if (aSlotIndex == 3) {
				filler.killMobs();
				GT_Utility.sendChatToPlayer(aPlayer, "All mobs in area killed");
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
		if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
			return;
		}
		
		GTMTE_Filler filler = (GTMTE_Filler) mTileEntity.getMetaTileEntity();
		
		saveMode = filler.saveTiles;
		killMobs = filler.killMobs;
		yCurrent = filler.curY;
		isDone = filler.isDone;
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, saveMode ? 1 : 0);
			var1.sendProgressBarUpdate(this, 101, killMobs ? 1 : 0);
			var1.sendProgressBarUpdate(this, 102, isDone ? 1 : 0);
			var1.sendProgressBarUpdate(this, 103, this.yCurrent & 0xFFFF);
			var1.sendProgressBarUpdate(this, 104, this.yCurrent >>> 16);
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		switch (par1) {
			case 100:
				this.saveMode = par2 != 0;
				break;
			case 101:
				this.killMobs = par2 != 0;
				break;
			case 102:
				this.isDone = par2 != 0;
				break;
			case 103:
				this.yCurrent = (this.yCurrent & 0xFFFF0000 | par2);
				break;
			case 104:
				this.yCurrent = (this.yCurrent & 0xFFFF | par2 << 16);
				break;
		}
	}
}