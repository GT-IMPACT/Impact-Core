package com.impact.mods.modChest.BASE;


import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@ChestContainer
public class Container_BaseChest extends Container {
	public TE_BaseChest TE;
	public InventoryPlayer mIP;
	private int mSlot; /**Общее количество слотов*/
	private int mX; /**Количество слотов в ряду*/
	private int mY; /**Количество слотов в столбце*/
	private int mPosXInv; /**Начала координаты инвентаря игрока по X*/
	private int mPosYInv; /**Начала координаты инвентаря игрока по Y*/

	public Container_BaseChest(TE_BaseChest aTE, InventoryPlayer aIP) {
		Slots();
		aTE.openInventory();
		mIP = aIP;
	}

	public void RegisterContainer(TE_BaseChest aTE, int aContValueSlot, int aContValueSlotX, int aContValueSlotY, int aContPlayerSlotX, int aContPlayerSlotY) {
		mSlot = aContValueSlot;
		mX = aContValueSlotX;
		mY = aContValueSlotY;
		mPosXInv = aContPlayerSlotX;
		mPosYInv = aContPlayerSlotY;
		TE = aTE;
	}

	public void Slots() {
		for (int y = 0; y < mY; y++)
			for (int x = 0; x < mX; x++)
				addSlotToContainer(new Slot(TE, y * mX + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(mIP, 9 + y * 9 + x, mPosXInv + (18 * x), mPosYInv + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(mIP, i, mPosXInv + (18 * i), mPosYInv + 58));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return TE.isUseableByPlayer(entityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer,  int slot) {
		ItemStack itemstack = null;
		Slot actualSlot = (Slot) this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > (mSlot-1) ) {
				if (!mergeItemStack(itemstack1, 0, mSlot, false))
					return null;
			} else if (!mergeItemStack(itemstack1, mSlot, mSlot + 36, true))
				return null;
			if (itemstack1.stackSize == 0)
				actualSlot.putStack(null);
			else
				actualSlot.onSlotChanged();
		}
		return itemstack;
	}

	public void onContainerClosed(EntityPlayer entityPlayer) {
		super.onContainerClosed(entityPlayer);
		TE.closeInventory();
	}
}