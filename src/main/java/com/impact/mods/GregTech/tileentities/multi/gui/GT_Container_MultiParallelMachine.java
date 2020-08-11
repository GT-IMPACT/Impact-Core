package com.impact.mods.GregTech.tileentities.multi.gui;

import com.impact.mods.GregTech.tileentities.multi.debug.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_MultiParallelMachine extends GT_ContainerMetaTile_Machine {

    public GT_Container_MultiParallelMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
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
