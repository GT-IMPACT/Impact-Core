package com.impact.mods.gregtech.gui.regulatechest;


import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_OneStackRegulateChest extends GT_GUIContainerMetaTile_Machine {
	
	public String mName;
	
	public GUI_OneStackRegulateChest(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String name) {
		super(new Container_OneStackRegulateChest(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "OneStackRegulateChest.png");
		mName = name;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(mName, 8, 6, 4210752);
		fontRendererObj.drawString("Inventory", 8, 73, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
