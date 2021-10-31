package com.impact.mods.gregtech.gui.matrixsystem;

import com.impact.mods.gregtech.gui.slot.CustomSlot;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_Hatch_MESystemMPChamber;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class GT_Container_MP_Chamber extends GT_ContainerMetaTile_Machine {

    public int amount;

    public GT_Container_MP_Chamber(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new CustomSlot(this.mTileEntity, 0, 70, 8, 8));
        addSlotToContainer(new CustomSlot(this.mTileEntity, 1, 70, 62, 8));
    }

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
        this.amount = ((GTMTE_Hatch_MESystemMPChamber) mTileEntity.getMetaTileEntity()).getMPSummary();

        for (Object crafter : this.crafters) {
            ICrafting var1 = (ICrafting) crafter;
            var1.sendProgressBarUpdate(this, 100, amount & 65535);
            var1.sendProgressBarUpdate(this, 101, amount >>> 16);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                amount = amount & -65536 | par2;
                break;
            case 101:
                amount = amount & 65535 | par2 << 16;
                break;
        }
    }
}