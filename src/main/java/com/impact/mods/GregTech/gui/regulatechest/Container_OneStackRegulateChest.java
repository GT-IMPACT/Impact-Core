package com.impact.mods.gregtech.gui.regulatechest;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class Container_OneStackRegulateChest extends GT_ContainerMetaTile_Machine {
	
	
	public Container_OneStackRegulateChest(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
		super(inventoryPlayer, te);
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(mTileEntity, j + i * 9, 8 + j * 18, 17 + i * 18));
			}
		}
	}
	
	@Override
	public int getSlotCount() {
		return 27;
	}
	
	@Override
	public int getShiftClickSlotCount() {
		return 27;
	}
}