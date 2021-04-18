package com.impact.mods.gregtech.gui;

import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_NuclearReactor extends GT_ContainerMetaTile_Machine {

  GTMTE_NuclearReactorBase mte;
  public int mRodDown;
  public int mRodUpFull;
  public int mRodUp;
  public int mTemp;
  public int mMaxTemp;
  public int mCurrFluid;
  public boolean isFastDecay;
  public int[] mHatchesRodPosition;

  public GT_Container_NuclearReactor(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity) {
    super(aInventoryPlayer, aTileEntity, false);
    mte = (GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity();
  }

  public GT_Container_NuclearReactor(InventoryPlayer aInventoryPlayer,
      IGregTechTileEntity aTileEntity, GTMTE_NuclearReactorBase base) {
    super(aInventoryPlayer, aTileEntity);
    mte = base;
  }

  @Override
  public void addSlots(InventoryPlayer aInventoryPlayer) {
    addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 5, false, false, 1));
    addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 37, false, false, 1));
    addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 55, false, false, 1));
    addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 73, false, false, 1));
  }

  @Override
  public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
      EntityPlayer aPlayer) {
    if (aSlotIndex < 0 || aSlotIndex > 5) {
      return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }
    Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
    if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
      if (aSlotIndex == 0) {
        if (mte.getBaseMetaTileEntity().isAllowedToWork()) {
          GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
          mte.setRodUp(10);
          GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodStatus()));
          mte.getBaseMetaTileEntity().disableWorking();
          mte.mFirstStart = false;
        } else {
          GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
          GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodStatus()));
          mte.getBaseMetaTileEntity().enableWorking();
          mte.mFirstStart = true;
        }
      }
      if (aSlotIndex == 1) {
        mte.setRodUp(1);
        GT_Utility.sendChatToPlayer(aPlayer, "All Rods Up 10%");
        GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodStatus()));
      }
      if (aSlotIndex == 2) {
        mte.setRodDown(1);
        GT_Utility.sendChatToPlayer(aPlayer, "All Rods Down 10%");
        GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodStatus()));
      }
      if (aSlotIndex == 3) {
        mte.setRodUp(10);
        GT_Utility.sendChatToPlayer(aPlayer, "All Rods Up Completely");
        GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodStatus()));
      }
    }
    return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
  }

  @Override
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
    this.mMaxTemp = (int) ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).mMaxTemp;
    this.mTemp = (int) ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).mCurrentTemp;
    this.mCurrFluid = (int) ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).mCurrentOutput;
    this.mHatchesRodPosition = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).getRodStatus();
    this.isFastDecay = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).isFastDecay;

    for (Object crafter : this.crafters) {
      ICrafting var1 = (ICrafting) crafter;
      var1.sendProgressBarUpdate(this, 100, mMaxTemp & 65535);
      var1.sendProgressBarUpdate(this, 101, mMaxTemp >>> 16);
      var1.sendProgressBarUpdate(this, 102, mTemp & 65535);
      var1.sendProgressBarUpdate(this, 103, mTemp >>> 16);
      var1.sendProgressBarUpdate(this, 104, mCurrFluid & 65535);
      var1.sendProgressBarUpdate(this, 105, mCurrFluid >>> 16);
      var1.sendProgressBarUpdate(this, 106, isFastDecay ? 1 : 0);
//      for (int i = 0; i < mHatchesRodPosition.length; i++) {
//        var1.sendProgressBarUpdate(this, 106 + i, mHatchesRodPosition[i]);
//      }
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void updateProgressBar(int id, int data) {
    super.updateProgressBar(id, data);
    switch (id) {
      case 100:
        mMaxTemp = mMaxTemp & -65536 | data;
        break;
      case 101:
        mMaxTemp = mMaxTemp & 65535 | data << 16;
        break;
      case 102:
        mTemp = mTemp & -65536 | data;
        break;
      case 103:
        mTemp = mTemp & 65535 | data << 16;
        break;
      case 104:
        mCurrFluid = mCurrFluid & -65536 | data;
        break;
      case 105:
        mCurrFluid = mCurrFluid & 65535 | data << 16;
        break;
      case 106:
        isFastDecay = (data != 0);
        break;
    }
//    for (int i = 0; i < mHatchesRodPosition.length; i++) {
//      if (id == (106 + i)) {
//        mHatchesRodPosition[i] = data;
//      }
//    }
  }

}
