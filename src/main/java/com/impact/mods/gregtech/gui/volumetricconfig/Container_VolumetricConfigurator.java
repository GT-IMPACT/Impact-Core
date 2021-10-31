package com.impact.mods.gregtech.gui.volumetricconfig;

import com.impact.mods.gregtech.gui.slot.CustomSlot;
import com.impact.mods.gregtech.tileentities.basic.GTMTE_VolumetricConfigurator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_VolumetricConfigurator extends GT_ContainerMetaTile_Machine {
	
	public int exampleCapacity;
	public boolean slot;
	
	public Container_VolumetricConfigurator(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
		super(inventoryPlayer, te);
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new CustomSlot(mTileEntity, 0, 80, 15, 1));
		addSlotToContainer(new Slot(mTileEntity, 1, 60, 41));
		addSlotToContainer(new Slot(mTileEntity, 2, 100, 41));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public int getSlotCount() {
		return 3;
	}
	
	@Override
	public int getShiftClickSlotCount() {
		return 0;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
		this.exampleCapacity = ((GTMTE_VolumetricConfigurator) mTileEntity.getMetaTileEntity()).exampleCapacity;
		this.slot = ((GTMTE_VolumetricConfigurator) mTileEntity.getMetaTileEntity()).mInventory[0] != null;
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, exampleCapacity & 65535);
			var1.sendProgressBarUpdate(this, 101, exampleCapacity >>> 16);
			var1.sendProgressBarUpdate(this, 102, slot ? 1 : 0);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
			case 100:
				exampleCapacity = exampleCapacity & -65536 | data;
				break;
			case 101:
				exampleCapacity = exampleCapacity & 65535 | data << 16;
				break;
			case 102:
				slot = (data != 0);
				break;
		}
	}
}