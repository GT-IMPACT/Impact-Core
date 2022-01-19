package com.impact.mods.gregtech.gui.laptop;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_LapTopSetting extends GT_GUIContainerMetaTile_Machine {
	
	public String mName;
	
	public GUI_LapTopSetting(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String name) {
		super(new Container_LapTopSetting(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "SpaceSatelliteHatches.png");
		mName = name;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (this.mContainer != null) {
			Container_LapTopSetting aContainer = (Container_LapTopSetting) this.mContainer;
			this.fontRendererObj.drawString(GT_LanguageManager.getTranslation("gt.blockmachines." + mName + ".name"), 33, 8, 16448255);
			this.fontRendererObj.drawString("Frequency: " + GT_Utility.parseNumberToString(aContainer.mFrequency), 33, 20, 16448255);
			if (!(aContainer.mTargetX == 0 && aContainer.mTargetY == 0 && aContainer.mTargetZ == 0)) {
				this.fontRendererObj.drawString("Coords:", 33, 30, 16448255);
				this.fontRendererObj.drawString("X: " + aContainer.mTargetX + " Y: " + aContainer.mTargetY + " Z: " + aContainer.mTargetZ, 33, 40, 16448255);
			}
			this.fontRendererObj.drawString("Dim ID: " + aContainer.mTargetD, 33, 50, 16448255);
			this.fontRendererObj.drawString("Satellite connection: " + (aContainer.mIsEnable == 1 ? (EnumChatFormatting.GREEN + "on") : (EnumChatFormatting.RED + "off")), 33, 64, 16448255);
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}