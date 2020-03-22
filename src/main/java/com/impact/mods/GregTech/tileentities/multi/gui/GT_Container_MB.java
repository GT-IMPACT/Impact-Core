package com.impact.mods.GregTech.tileentities.multi.gui;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_Container_MB extends GT_ContainerMetaTile_Machine {

    public GT_Container_MB(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public GT_Container_MB(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, boolean bindInventory) {
        super(aInventoryPlayer, aTileEntity, bindInventory);
    }

}
