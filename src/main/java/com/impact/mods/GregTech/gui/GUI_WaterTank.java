package com.impact.mods.GregTech.gui;

import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_WaterTank extends GT_GUIContainerMetaTile_Machine {

    private final String mName;

    public GUI_WaterTank(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
        super(new GT_Container_BasicTank(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "WaterTank.png");
        mName = aName;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRendererObj.drawString(mName, 8, 6, 16448255);
        if (mContainer != null) {
            //fontRendererObj.drawString("Liquid Amount", 10, 20, 16448255);
            fontRendererObj.drawString("Water Amount", 100, 34, 16448255);
            fontRendererObj.drawString(GT_Utility.parseNumberToString(((GT_Container_BasicTank) mContainer).mContent)+"/16,000", 100, 45, 16448255);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        //if (this.mContainer != null) {
        int tScale = ((GT_Container_BasicTank) mContainer).mContent;
        byte b = 4;
            if (tScale>=0 && tScale<=1000) {
                drawTexturedModalRect(x + 27, y + 65,      176, 100,    48, 4);
            } else if (tScale>1000 && tScale<=2000) {
                drawTexturedModalRect(x + 27, y + 65-4,    176, 100-4,    48, 8);
            } else if (tScale>2000 && tScale<=3000) {
                drawTexturedModalRect(x + 27, y + 65-8,    176, 100-8,    48, 12);
            } else if (tScale>3000 && tScale<=4000) {
                drawTexturedModalRect(x + 27, y + 65-12,    176, 100-12,    48, 16);
            } else if (tScale>4000 && tScale<=5000) {
                drawTexturedModalRect(x + 27, y + 65-16,    176, 100-16,    48, 20);
            } else if (tScale>5000 && tScale<=6000) {
                drawTexturedModalRect(x + 27, y + 65-20,    176, 100-20,    48, 24);
            } else if (tScale>6000 && tScale<=7000) {
                drawTexturedModalRect(x + 27, y + 65-24,    176, 100-24,    48, 28);
            } else if (tScale>7000 && tScale<=8000) {
                drawTexturedModalRect(x + 27, y + 65-28,    176, 100-28,    48, 32);
            } else if (tScale>8000 && tScale<=10000) {
                drawTexturedModalRect(x + 27, y + 65-32,    176, 100-32,    48, 36);
            } else if (tScale>10000 && tScale<=13000) {
                drawTexturedModalRect(x + 27, y + 65-36,    176, 100-36,    48, 40);
            } else if (tScale>13000 && tScale<=14000) {
                drawTexturedModalRect(x + 27, y + 65-40,    176, 100-40,    48, 44);
            } else if (tScale>14000 && tScale<=15000) {
                drawTexturedModalRect(x + 27, y + 65-44,    176, 100-44,    48, 48);
            } else if (tScale>15000 && tScale<=16000) {
                drawTexturedModalRect(x + 27, y + 65-48,    176, 100-48,    48, 52);




            }   else drawTexturedModalRect(x + 27, y + 65, 0, 0, 0, 0);

                drawTexturedModalRect(x + 27, y + 17, 176, 0,   48, 52);
    }
}
