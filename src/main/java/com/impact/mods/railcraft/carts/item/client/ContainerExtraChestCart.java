package com.impact.mods.railcraft.carts.item.client;

import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@ChestContainer(isLargeChest = false)
public abstract class ContainerExtraChestCart extends Container {
	
	private IInventory cart;
	private int ySize;
	private int xSize;
	private int columns;
	
	public ContainerExtraChestCart() {
	
	}
	
	public ContainerExtraChestCart(IInventory invPlayer, IInventory cart, int xSize, int ySize,
								   int rows) {
		this(invPlayer, cart, xSize, ySize, rows, 9);
	}
	
	public ContainerExtraChestCart(IInventory invPlayer, IInventory cart, int xSize, int ySize,
								   int rows, int columns) {
		this.cart    = cart;
		this.ySize   = ySize;
		this.xSize   = xSize;
		this.columns = columns;
		int leftCol = (xSize - 162) / 2 + 1;
		int hotbarSlot;
		int playerInvCol;
		
		switch (rows) {
			case 5:
				for (hotbarSlot = 0; hotbarSlot < rows; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < columns; ++playerInvCol) {
						addSlotToContainer(
								new Slot(cart, playerInvCol + hotbarSlot * columns, 8 + playerInvCol * 18,
										18 + hotbarSlot * 18
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 3; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < 9; ++playerInvCol) {
						addSlotToContainer(
								new Slot(invPlayer, playerInvCol + hotbarSlot * 9 + 9, leftCol + playerInvCol * 18,
										ySize - (4 - hotbarSlot) * 18 - 6
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
					addSlotToContainer(
							new Slot(invPlayer, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 20));
				}
				return;
			
			case 7:
				for (hotbarSlot = 0; hotbarSlot < rows; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < columns; ++playerInvCol) {
						addSlotToContainer(
								new Slot(cart, playerInvCol + hotbarSlot * columns, 8 + playerInvCol * 18,
										18 + hotbarSlot * 18
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 3; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < 9; ++playerInvCol) {
						addSlotToContainer(
								new Slot(invPlayer, playerInvCol + hotbarSlot * 9 + 9, leftCol + playerInvCol * 18,
										ySize - (4 - hotbarSlot) * 18 - 36
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
					addSlotToContainer(
							new Slot(invPlayer, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 50));
				}
				return;
			
			default:
				for (hotbarSlot = 0; hotbarSlot < rows; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < columns; ++playerInvCol) {
						addSlotToContainer(
								new Slot(cart, playerInvCol + hotbarSlot * columns, 8 + playerInvCol * 18,
										18 + hotbarSlot * 18
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 3; ++hotbarSlot) {
					for (playerInvCol = 0; playerInvCol < 9; ++playerInvCol) {
						addSlotToContainer(
								new Slot(invPlayer, playerInvCol + hotbarSlot * 9 + 9, leftCol + playerInvCol * 18,
										ySize - (4 - hotbarSlot) * 18 - 56
								));
					}
				}
				
				for (hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
					addSlotToContainer(
							new Slot(invPlayer, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 70));
				}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return cart.isUseableByPlayer(entityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = (Slot) inventorySlots.get(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			if (i < getCart().getSizeInventory()) {
				if (!mergeItemStack(stack, getCart().getSizeInventory(), 36 + getCart().getSizeInventory(),
						false
				)) {
					return null;
				}
			} else if (!mergeItemStack(stack, 0, getCart().getSizeInventory(), false)) {
				return null;
			}
			if (stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			
			slot.onPickupFromSlot(player, stack);
			return result;
		}
		return null;
	}
	
	public IInventory getCart() {
		return cart;
	}
	
	public void setCart(IInventory cart) {
		this.cart = cart;
	}
	
	public int getySize() {
		return ySize;
	}
	
	public void setySize(int ySize) {
		this.ySize = ySize;
	}
	
	public int getxSize() {
		return xSize;
	}
	
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}
	
	@ChestContainer.RowSizeCallback
	public int getNumColumns() {
		return this.columns;
	}
	
}