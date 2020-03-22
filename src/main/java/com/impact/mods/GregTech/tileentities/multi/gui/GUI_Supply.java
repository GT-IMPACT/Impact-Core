package com.impact.mods.GregTech.tileentities.multi.gui;

import com.impact.mods.GregTech.tileentities.multi.GT_TileEntity_Supply;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUI_Supply extends GUI_BASE {

    public GUI_Supply(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
    }

    @Override
    String getmMode() {
        return GT_TileEntity_Supply.mModed;
    }
}
