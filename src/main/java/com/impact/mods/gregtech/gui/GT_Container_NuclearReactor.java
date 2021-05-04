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
  public int mRodUp;
  public int mTemp;
  public int mMaxTemp;
  public int mInput;
  public int mOutput;
  public boolean isFastDecay;
  public boolean isMoxFuel;
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
      try {
        int rodZero = mte.getRodPosition().length > 0 ? mte.getRodPosition()[0] : 0;
        if (aSlotIndex == 0) {
          if (mte.getBaseMetaTileEntity().isAllowedToWork()) {
            GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
            //mte.setRodPosition(10);
            GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodPosition()));
            mte.getBaseMetaTileEntity().disableWorking();
            mte.mFirstStart = false;
          } else {
            GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
            GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodPosition()));
            mte.getBaseMetaTileEntity().enableWorking();
            mte.mFirstStart = true;
          }
        }
        if (aSlotIndex == 1) {
          mte.setRodPosition(rodZero - 1);
          GT_Utility.sendChatToPlayer(aPlayer, "All Rods Up 10%");
          GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodPosition()));
        }
        if (aSlotIndex == 2) {
          mte.setRodPosition(rodZero + 1);
          GT_Utility.sendChatToPlayer(aPlayer, "All Rods Down 10%");
          GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodPosition()));
        }
        if (aSlotIndex == 3) {
          mte.setRodPosition(rodZero - 10);
          GT_Utility.sendChatToPlayer(aPlayer, "All Rods Up Completely");
          GT_Utility.sendChatToPlayer(aPlayer, "" + Arrays.toString(mte.getRodPosition()));
        }
      } catch (Exception ignored) {
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
    this.mInput = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).getInputFluid().amount;
    this.mOutput = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).getOutputFluid().amount;
    this.mHatchesRodPosition = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).getRodPosition();
    this.isFastDecay = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).isFastDecay;
    this.isMoxFuel = ((GTMTE_NuclearReactorBase) mTileEntity.getMetaTileEntity()).isMoxFuel;

    for (Object crafter : this.crafters) {
      ICrafting var1 = (ICrafting) crafter;
      var1.sendProgressBarUpdate(this, 100, mMaxTemp & 65535);
      var1.sendProgressBarUpdate(this, 101, mMaxTemp >>> 16);
      var1.sendProgressBarUpdate(this, 102, mTemp & 65535);
      var1.sendProgressBarUpdate(this, 103, mTemp >>> 16);
      var1.sendProgressBarUpdate(this, 104, mInput & 65535);
      var1.sendProgressBarUpdate(this, 105, mInput >>> 16);
      var1.sendProgressBarUpdate(this, 106, isFastDecay ? 1 : 0);
      var1.sendProgressBarUpdate(this, 107, isMoxFuel ? 1 : 0);
      var1.sendProgressBarUpdate(this, 108, mOutput & 65535);
      var1.sendProgressBarUpdate(this, 109, mOutput >>> 16);

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
        mInput = mInput & -65536 | data;
        break;
      case 105:
        mInput = mInput & 65535 | data << 16;
        break;
      case 106:
        isFastDecay = (data != 0);
        break;
      case 107:
        isMoxFuel = (data != 0);
        break;
      case 108:
        mOutput = mOutput & -65536 | data;
        break;
      case 109:
        mOutput = mOutput & 65535 | data << 16;
        break;
    }
  }

}
