package com.impact.mods.GregTech.tileentities.multi.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUI_NotMultiMachine extends GUI_BASE {

    String mModed;
    public GUI_NotMultiMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile,
                               String mMode) {
        super(aInventoryPlayer, aTileEntity, aName, aTextureFile);
        mModed = mMode;
    }

    @Override
    String getmMode() {
        return mModed;
    }

}
