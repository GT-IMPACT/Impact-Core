package com.impact.mods.gregtech.gui.parallelcomputer;

import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public class Container_SuperParallelComputer extends GTC_ImpactBase {

  GTMTE_ParallelComputer mte;
  public int mCurrentParallelCapacity, mMaxParallelCapacity;

  public Container_SuperParallelComputer(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity) {
    super(aInventoryPlayer, aTileEntity);
  }

  public Container_SuperParallelComputer(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity, GTMTE_ParallelComputer base) {
    super(aInventoryPlayer, aTileEntity);
  }

  @Override
  public void addSlots(InventoryPlayer aInventoryPlayer) {
    super.addSlots(aInventoryPlayer);
  }

  @Override
  public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
      EntityPlayer aPlayer) {
    return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) {
      return;
    }
    mCurrentParallelCapacity = ((GTMTE_ParallelComputer) mTileEntity
        .getMetaTileEntity()).mCurrentCapacityPP;
    mMaxParallelCapacity = ((GTMTE_ParallelComputer) mTileEntity
        .getMetaTileEntity()).mMaxCapacityPP;

    for (Object crafter : this.crafters) {
      ICrafting id = (ICrafting) crafter;
      id.sendProgressBarUpdate(this, 100, mCurrentParallelCapacity & 65535);
      id.sendProgressBarUpdate(this, 101, mCurrentParallelCapacity >>> 16);
      id.sendProgressBarUpdate(this, 102, mMaxParallelCapacity & 65535);
      id.sendProgressBarUpdate(this, 103, mMaxParallelCapacity >>> 16);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void updateProgressBar(int id, int upd) {
    super.updateProgressBar(id, upd);
    switch (id) {
      case 100:
        mCurrentParallelCapacity = mCurrentParallelCapacity & -65536 | upd;
        break;
      case 101:
        mCurrentParallelCapacity = mCurrentParallelCapacity & 65535 | upd << 16;
        break;
      case 102:
        mMaxParallelCapacity = mMaxParallelCapacity & -65536 | upd;
        break;
      case 103:
        mMaxParallelCapacity = mMaxParallelCapacity & 65535 | upd << 16;
        break;
    }
  }
}
