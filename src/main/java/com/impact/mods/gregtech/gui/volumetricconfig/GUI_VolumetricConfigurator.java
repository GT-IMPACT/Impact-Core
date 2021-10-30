package com.impact.mods.gregtech.gui.volumetricconfig;


import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_VolumetricConfigurator extends GT_GUIContainerMetaTile_Machine {
	
	public String mName;
	
	public GUI_VolumetricConfigurator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String name) {
		super(new Container_VolumetricConfigurator(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "VolumetricConfigurator.png");
		mName = name;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		Container_VolumetricConfigurator cont = ((Container_VolumetricConfigurator) mContainer);
		fontRendererObj.drawString(mName, 8, 4, 4210752);
		fontRendererObj.drawString("Last Configuration: " + cont.exampleCapacity, 8, 62, 4210752);
		fontRendererObj.drawString("Inventory", 8, 73, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		Container_VolumetricConfigurator cont = ((Container_VolumetricConfigurator) mContainer);
		if (cont.slot) {
			drawTexturedModalRect(x + 84, y + 25, 84, 20, 11, 5);
		}
	}
}