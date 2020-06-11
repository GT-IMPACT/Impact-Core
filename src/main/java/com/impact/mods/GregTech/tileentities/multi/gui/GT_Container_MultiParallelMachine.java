package com.impact.mods.GregTech.tileentities.multi.gui;

import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaPipeEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GT_Container_MultiParallelMachine extends GT_ContainerMetaTile_Machine {
    public final boolean allowedToWorkButton;

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, boolean enablePowerButton, int itsEnabledButtonNonCheck) {
        super(aInventoryPlayer, aTileEntity);
        allowedToWorkButton = enablePowerButton;
    }

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
        allowedToWorkButton = true;
    }

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, boolean bindInventory, boolean enablePowerButton) {
        super(aInventoryPlayer, aTileEntity, bindInventory);
        allowedToWorkButton = enablePowerButton;
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 152, 62, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 0 || aSlotIndex > 2) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
        if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
            GT_MetaTileEntity_MultiParallelBlockBase mte = (GT_MetaTileEntity_MultiParallelBlockBase) mTileEntity.getMetaTileEntity();
            if (aSlotIndex == 0) {
                if (allowedToWorkButton) {
                    if (mte.getBaseMetaTileEntity().isAllowedToWork()) {
                        mte.getBaseMetaTileEntity().disableWorking();

                    } else {
                        mte.getBaseMetaTileEntity().enableWorking();
                    }
                }
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

}
