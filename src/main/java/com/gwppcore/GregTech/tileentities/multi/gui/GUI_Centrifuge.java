package com.gwppcore.GregTech.tileentities.multi.gui;

import com.gwppcore.GregTech.tileentities.multi.GT_TileEntity_Centrifuge;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUI_Centrifuge extends GUI_BASE {

    public GUI_Centrifuge(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
    }

    @Override
    String getmMode() {
        return GT_TileEntity_Centrifuge.mModed;
    }
}
