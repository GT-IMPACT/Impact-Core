package com.impact.mods.gregtech.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class CustomSlot extends Slot {

  public int mMaxStacksize;

  public CustomSlot(IInventory inventory, int id, int x, int y, int stackLimit) {
    super(inventory, id, x, y);
    mMaxStacksize = stackLimit;
  }

  @Override
  public int getSlotStackLimit() {
    return mMaxStacksize;
  }
}
