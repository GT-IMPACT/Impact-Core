package com.impact.GregTech.tileentities.multi.gui;

import com.impact.GregTech.tileentities.multi.GT_TileEntity_Wire;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUI_Wire extends GUI_BASE {

    public GUI_Wire(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
    }

    @Override
    String getmMode() {
        return GT_TileEntity_Wire.mModed;
    }
}
