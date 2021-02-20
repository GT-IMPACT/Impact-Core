package com.impact.mods.GregTech.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.net.GT_Packet_Block_Event_Four_Int;
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
    addSlotToContainer(new Slot(mTileEntity, 0, 69, 28));
    addSlotToContainer(new Slot(mTileEntity, 1, 91, 28));
    addSlotToContainer(new Slot(mTileEntity, 2, 69, 50));
    addSlotToContainer(new Slot(mTileEntity, 3, 91, 50));
  }

  @Override
  public void onContainerClosed(EntityPlayer par1EntityPlayer) {
    super.onContainerClosed(par1EntityPlayer);
    int b1 = 0;
    if (getInventory() != null) {
      for (int i = 0; i < 4; i++) {
        if (getStackSize(getSlot(i).getStack()) == 1) {
          b1 += 1 << i;
        } else {
          b1 += 0;
        }
      }
    }
    GT_Values.NW.sendPacketToAllPlayersInRange(mTileEntity.getWorld(),
        new GT_Packet_Block_Event_Four_Int(
            mTileEntity.getXCoord(), mTileEntity.getYCoord(), mTileEntity.getZCoord(),
            mTileEntity.getWorld().provider.dimensionId,
            b1, 0, 0, 0),
        mTileEntity.getXCoord(), mTileEntity.getZCoord());
    mTileEntity.issueTextureUpdate();
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
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
    if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) {
      return;
    }
//        heat = ((GT_MetaTileEntity_Hatch_Rack) mTileEntity.getMetaTileEntity()).heat > 0;

//        for (Object crafter : crafters) {
//            ICrafting var1 = (ICrafting) crafter;
//            var1.sendProgressBarUpdate(this, 100, heat ? 1 : 0);
//        }

  }

  @Override
  @SideOnly(Side.CLIENT)
  public void updateProgressBar(int par1, int par2) {
    super.updateProgressBar(par1, par2);
//        if (par1 == 100) {
//            heat = par2 != 0;
//        }
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