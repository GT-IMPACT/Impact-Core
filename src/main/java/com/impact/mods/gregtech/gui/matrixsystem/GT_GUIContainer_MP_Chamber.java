package com.impact.mods.gregtech.gui.matrixsystem;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_MP_Chamber extends GT_GUIContainerMetaTile_Machine {

    private final String mName;

    public GT_GUIContainer_MP_Chamber(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
        super(new GT_Container_MP_Chamber(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "MPChamber.png");
        mName = aName;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(mName, 6, 4, 4210752);
        fontRendererObj.drawString(((GT_Container_MP_Chamber) mContainer).amount / 100 + "%", 109, 41, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        double tScale = (double) ((GT_Container_MP_Chamber) mContainer).amount / 10_000D;
        drawTexturedModalRect(x + 90, y + 78 - Math.min(70, (int) (tScale * 70)), 178, 8,
                16, Math.min(70, (int) (tScale * 70)));
        drawTexturedModalRect(x + 90, y + 8, 176, 8, 2, 70);
    }
}