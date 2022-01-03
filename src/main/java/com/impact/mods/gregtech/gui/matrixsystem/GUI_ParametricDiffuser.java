package com.impact.mods.gregtech.gui.matrixsystem;

import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;


public class GUI_ParametricDiffuser extends GT_GUIContainerMT_Machine {

	String mName = "";

	public GUI_ParametricDiffuser(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new GT_Container_ParametricDiffuser(aInventoryPlayer, aTileEntity),
				RES_PATH_GUI + "multimachines/ParametricDiffuser.png");
		mName = aName;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(mName, 8, 8, 16448255);
		
		if (this.mContainer != null) {

			GT_Container_ParametricDiffuser container = (GT_Container_ParametricDiffuser) this.mContainer;

			if (((mContainer).mDisplayErrorCode & 64) != 0) {
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 30, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "Incomplete Structure", 25, 40, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 50, 0);
			}
			
			if ((mContainer).mDisplayErrorCode == 0) {
				if ((mContainer).mActive == 0) {
					fontRendererObj.drawString("Progress:" + EnumChatFormatting.RED + " not working", 8, 22, 16448255);
				} else {
					fontRendererObj.drawString("Progress:" + EnumChatFormatting.GREEN + " active", 8, 22, 16448255);
				}
				fontRendererObj.drawString("Generated MP:", 8, 42, 16448255);
				fontRendererObj.drawString("" + EnumChatFormatting.GREEN + container.mMPGenerate, 8, 52, 16448255);

				fontRendererObj.drawString("MP Stabilizer:", 8, 72, 16448255);
				fontRendererObj.drawString("" + (container.checkStabilizer ? EnumChatFormatting.GREEN + "Active" : EnumChatFormatting.RED + "Inactive"), 8, 82, 16448255);
//				new Color(0x050303);
				fontRendererObj.drawString("MP Beam", 6, 118, 0x050303);
				fontRendererObj.drawString("Beam Power", 6, 141, 0x050303);

			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			if (mContainer.mTileEntity != null) {
				getTooltip(mouseX, mouseY, 155, 5, 16, 16, new String[]{
						"Start/Stop machine",
						EnumChatFormatting.DARK_GRAY + "Button color:",
						EnumChatFormatting.RED + "    Any Problem",
						EnumChatFormatting.YELLOW + "    Ready to start",
						EnumChatFormatting.GREEN + "    Starting",
				});
			}
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		if (this.mContainer != null) {
			GT_Container_ParametricDiffuser container = (GT_Container_ParametricDiffuser) mContainer;
			if ((mContainer).mDisplayErrorCode == 0) {
				if ((mContainer).mActive == 0) {
					drawTexturedModalRect(x + 155, y + 4, 236, 24, 16, 16);
				} else {
					drawTexturedModalRect(x + 155, y + 4, 236, 39, 16, 16);
				}
				double tScale = (double) container.mMPGenerate / (double) (200 * container.mPeakBeamMP);
				drawTexturedModalRect(x + 7, y + 129, 1, 240, Math.min(144, (int) (tScale * 144)), 7);

				tScale = (double) container.mPeakBeamMP / 10D;
				drawTexturedModalRect(x + 7, y + 152, 1, 240, Math.min(144, (int) (tScale * 144)), 7);
			}
		}
	}
	
	public String trans(String aKey, String aEnglish) {
		return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
	}
}