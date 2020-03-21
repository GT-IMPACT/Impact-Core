package com.impact.GregTech.tileentities.multi.gui;

import com.impact.GregTech.tileentities.multi.GT_TileEntity_PressBendExtrud;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUI_PressBendExtrud extends GUI_BASE {

    public GUI_PressBendExtrud(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
    }

    @Override
    String getmMode() {
        return GT_TileEntity_PressBendExtrud.mModed;
    }
}
