package com.impact.mods.gregtech.gui.parallelcomputer;

import com.impact.mods.gregtech.gui.slot.CustomSlot;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_Rack extends GT_ContainerMetaTile_Machine {

  public GT_Container_Rack(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
    super(aInventoryPlayer, aTileEntity);
  }

  @Override
  public void addSlots(InventoryPlayer aInventoryPlayer) {
    addSlotToContainer(new CustomSlot(this.mTileEntity, 0, 69, 28, 1));
    addSlotToContainer(new CustomSlot(this.mTileEntity, 1, 91, 28, 1));
    addSlotToContainer(new CustomSlot(this.mTileEntity, 2, 69, 50, 1));
    addSlotToContainer(new CustomSlot(this.mTileEntity, 3, 91, 50, 1));
  }

  public int getStackSize(ItemStack aInv) {
    if (aInv == null || aInv.stackSize <= 0) {
      return 0;
    }
    return 1;
  }

  @Override
  public int getSlotCount() {
    return 4;
  }

  @Override
  public int getShiftClickSlotCount() {
    return 4;
  }

  @Override
  public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
      EntityPlayer aPlayer) {
    if (mActive != 0) {
      return null;
    }
    return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer aPlayer, int aSlotIndex) {
    if (mActive != 0) {
      return null;
    }
    return super.transferStackInSlot(aPlayer, aSlotIndex);
  }

  @Override
  public boolean canDragIntoSlot(Slot par1Slot) {
    if (mActive != 0) {
      return false;
    }
    return super.canDragIntoSlot(par1Slot);
  }

  @Override
  public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
    if (mActive != 0) {
      return;
    }
    super.putStacksInSlots(par1ArrayOfItemStack);
  }

  @Override
  protected boolean mergeItemStack(ItemStack aStack, int aStartIndex, int aSlotCount,
      boolean par4) {
    if (mActive != 0) {
      return false;
    }
    return super.mergeItemStack(aStack, aStartIndex, aSlotCount, par4);
  }

  @Override
  public void putStackInSlot(int par1, ItemStack par2ItemStack) {
    if (mActive != 0) {
      return;
    }
    super.putStackInSlot(par1, par2ItemStack);
  }
}