package com.impact.mods.gregtech.gui.nuclear;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_Reactor_Rod extends GT_GUIContainerMetaTile_Machine {
	
	private final String mName;
	
	public GT_GUIContainer_Reactor_Rod(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new GT_Container_Reactor_Rod(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "ReactorRodHatch.png");
		mName = aName;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(mName, 6, 4, 4210752);
		fontRendererObj.drawString(((GT_Container_Reactor_Rod) mContainer).mDownRod * 10 + "%", 109, 41, 4210752);
		fontRendererObj.drawString("ID: " + (((GT_Container_Reactor_Rod) mContainer).mID + 1), 40, 41, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		double tScale = (double) ((GT_Container_Reactor_Rod) mContainer).mDownRod / 10D;
		drawTexturedModalRect(x + 90, y + 26, 178, 26, 16, Math.min(52, (int) (tScale * 52)));
		drawTexturedModalRect(x + 90, y + 26, 176, 26, 2, 52);
	}
}