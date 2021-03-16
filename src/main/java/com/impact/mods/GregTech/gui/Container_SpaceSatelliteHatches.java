package com.impact.mods.GregTech.gui;

import com.impact.mods.GregTech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Receiver;
import com.impact.mods.GregTech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Transmitter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_SpaceSatelliteHatches extends GT_ContainerMetaTile_Machine {

  public int mFrequency = 0;
  public int mTargetX = 0;
  public int mTargetY = 0;
  public int mTargetZ = 0;
  public int mTargetD = 0;
  public int mIsEnable = 0;

  public Container_SpaceSatelliteHatches(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity) {
    super(aInventoryPlayer, aTileEntity);
  }

  @Override
  public void addSlots(InventoryPlayer aPlayerInventory) {
    addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 8, false, false, 1));
    addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 26, false, false, 1));
    addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 8, 55, false, false, 1));
  }

  @Override
  public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
      EntityPlayer aPlayer) {
    if (aSlotIndex < 0) {
      return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
    if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {

      if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_SpaceSatellite_Transmitter) {
        int aFrequency = ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity
            .getMetaTileEntity()).mFrequency;
        if (aSlotIndex == 0) {
          ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity.getMetaTileEntity()).mFrequency += (
              aShifthold == 1 ? 10 : 1);
          return null;
        } else if (aSlotIndex == 1) {
          ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity.getMetaTileEntity()).mFrequency -= (
              aShifthold == 1 ? 10 : 1);
          if (aFrequency < 0) {
            ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity
                .getMetaTileEntity()).mFrequency = 0;
          }
          return null;
        } else if (aSlotIndex == 2) {
          ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity.getMetaTileEntity()).setFrequency(
              ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity.getMetaTileEntity()).mFrequency,
              aPlayer);
        }
      }
      if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_SpaceSatellite_Receiver) {
        int aFrequency = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
            .getMetaTileEntity()).mFrequency;
        if (aSlotIndex == 0) {
          ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).mFrequency += (
              aShifthold == 1 ? 10 : 1);
          return null;
        } else if (aSlotIndex == 1) {
          ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).mFrequency -= (
              aShifthold == 1 ? 10 : 1);
          if (aFrequency < 0) {
            ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).mFrequency = 0;
          }
          return null;
        } else if (aSlotIndex == 2) {
          ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).getFrequency(
              ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).mFrequency,
              aPlayer);
        }
      }
    }
    return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
      return;
    }

    if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_SpaceSatellite_Transmitter) {
      this.mTargetX = (this.mTileEntity.getMetaTileEntity()).getBaseMetaTileEntity().getXCoord();
      this.mTargetY = (this.mTileEntity.getMetaTileEntity()).getBaseMetaTileEntity().getYCoord();
      this.mTargetZ = (this.mTileEntity.getMetaTileEntity()).getBaseMetaTileEntity().getZCoord();
      this.mTargetD = (this.mTileEntity.getMetaTileEntity()).getBaseMetaTileEntity()
          .getWorld().provider.dimensionId;

      this.mFrequency = ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity
          .getMetaTileEntity()).mFrequency;

      mIsEnable =
          ((GTMTE_SpaceSatellite_Transmitter) this.mTileEntity.getMetaTileEntity()).mIsTransmit ? 1
              : 0;
    }

    if (this.mTileEntity.getMetaTileEntity() instanceof GTMTE_SpaceSatellite_Receiver) {
      this.mTargetX = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
          .getMetaTileEntity()).mTargetX;
      this.mTargetY = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
          .getMetaTileEntity()).mTargetY;
      this.mTargetZ = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
          .getMetaTileEntity()).mTargetZ;
      this.mTargetD = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
          .getMetaTileEntity()).mTargetD;

      this.mFrequency = ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity
          .getMetaTileEntity()).mFrequency;

      mIsEnable =
          ((GTMTE_SpaceSatellite_Receiver) this.mTileEntity.getMetaTileEntity()).mIsReceive ? 1 : 0;
    }

    for (Object crafter : this.crafters) {
      ICrafting var1 = (ICrafting) crafter;
      var1.sendProgressBarUpdate(this, 100, this.mTargetX & 0xFFFF);
      var1.sendProgressBarUpdate(this, 101, this.mTargetX >>> 16);

      var1.sendProgressBarUpdate(this, 102, this.mTargetY & 0xFFFF);
      var1.sendProgressBarUpdate(this, 103, this.mTargetY >>> 16);

      var1.sendProgressBarUpdate(this, 104, this.mTargetZ & 0xFFFF);
      var1.sendProgressBarUpdate(this, 105, this.mTargetZ >>> 16);

      var1.sendProgressBarUpdate(this, 106, this.mTargetD & 0xFFFF);
      var1.sendProgressBarUpdate(this, 107, this.mTargetD >>> 16);

      var1.sendProgressBarUpdate(this, 108, this.mFrequency & 0xFFFF);
      var1.sendProgressBarUpdate(this, 109, this.mFrequency >>> 16);

      var1.sendProgressBarUpdate(this, 110, this.mIsEnable);
    }
  }

  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int par1, int par2) {
    super.updateProgressBar(par1, par2);
    switch (par1) {
      case 100:
        this.mTargetX = (this.mTargetX & 0xFFFF0000 | par2);
        break;
      case 101:
        this.mTargetX = (this.mTargetX & 0xFFFF | par2 << 16);
        break;
      case 102:
        this.mTargetY = (this.mTargetY & 0xFFFF0000 | par2);
        break;
      case 103:
        this.mTargetY = (this.mTargetY & 0xFFFF | par2 << 16);
        break;
      case 104:
        this.mTargetZ = (this.mTargetZ & 0xFFFF0000 | par2);
        break;
      case 105:
        this.mTargetZ = (this.mTargetZ & 0xFFFF | par2 << 16);
        break;
      case 106:
        this.mTargetD = (this.mTargetD & 0xFFFF0000 | par2);
        break;
      case 107:
        this.mTargetD = (this.mTargetD & 0xFFFF | par2 << 16);
        break;
      case 108:
        this.mFrequency = (this.mFrequency & 0xFFFF0000 | par2);
        break;
      case 109:
        this.mFrequency = (this.mFrequency & 0xFFFF | par2 << 16);
        break;
      case 110:
        this.mIsEnable = par2;
    }
  }
}
