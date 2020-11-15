package com.impact.mods.GregTech.gui;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_MultiParallelMachine extends GT_ContainerMetaTile_Machine {

    GT_MetaTileEntity_MultiBlockBase mte;

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
        mte = (GT_MetaTileEntity_MultiBlockBase) mTileEntity.getMetaTileEntity();
    }

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, GT_MetaTileEntity_MultiBlockBase base) {
        super(aInventoryPlayer, aTileEntity);
        mte = base;
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
            if (aSlotIndex == 0) {
                if (mte.getBaseMetaTileEntity().isAllowedToWork()) {
                    GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
                    mte.getBaseMetaTileEntity().disableWorking();
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
                    mte.getBaseMetaTileEntity().enableWorking();
                }
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

}
