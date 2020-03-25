package com.impact.mods.modChest.BASE;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class Container_BaseChest extends Container
{
	public TE_BaseChest TEBaseChest;

	public Container_BaseChest(@Nonnull final TE_BaseChest TEBaseChest, final InventoryPlayer inventoryPlayer)
	{
		for (int y = 0; y < getY(); y++)
			for (int x = 0; x < getX(); x++)
				addSlotToContainer(new Slot(TEBaseChest, y * getX() + x, 8 + (18 * x), 18 + (18 * y)));
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(inventoryPlayer, 9 + y * 9 + x, getPosXInv() + (18 * x), getPosYInv() + (18 * y)));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, getPosXInv() + (18 * i), getPosYInv()+58));
		(this.TEBaseChest = TEBaseChest).openInventory();
	}

	@Override
	public final boolean canInteractWith(final EntityPlayer entityPlayer)
	{
		return TEBaseChest.isUseableByPlayer(entityPlayer);
	}

	@Override
	public final ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int slot)
	{
		ItemStack itemstack = null;
		final Slot actualSlot = (Slot) this.inventorySlots.get(slot);
		if (actualSlot != null && actualSlot.getHasStack()) {
			ItemStack itemstack1 = actualSlot.getStack();
			itemstack = itemstack1.copy();
			if (slot > (getSlot()-1) ) {
				if (!mergeItemStack(itemstack1, 0, getSlot(), false))
					return null;
			} else if (!mergeItemStack(itemstack1, getSlot(), getSlot() + 36, true))
				return null;
			if (itemstack1.stackSize == 0)
				actualSlot.putStack(null);
			else
				actualSlot.onSlotChanged();
		}
		return itemstack;
	}

	public final void onContainerClosed(final EntityPlayer entityPlayer)
	{
		super.onContainerClosed(entityPlayer);
		TEBaseChest.closeInventory();
	}
	public abstract int getSlot();
	protected abstract int getX();
	protected abstract int getY();
	protected abstract int getPosXInv();
	protected abstract int getPosYInv();
}