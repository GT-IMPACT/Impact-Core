package com.impact.mods.gregtech.gui.nuclear;

import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;


public class GUI_NuclearReactor extends GT_GUIContainerMT_Machine {
	
	String mName = "";
	
	public GUI_NuclearReactor(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity,
							  String aName,
							  String aTextureFile) {
		super(new GT_Container_NuclearReactor(aInventoryPlayer, aTileEntity),
				RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay"
						: aTextureFile));
		mName = aName;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(mName, 10, 8, 16448255);
		
		if (this.mContainer != null) {
			
			GT_Container_NuclearReactor container = (GT_Container_NuclearReactor) this.mContainer;
			fontRendererObj
					.drawString(EnumChatFormatting.YELLOW + (container.isFastDecay ? "Fast Decay Mode" : "Default Mode"), 10, 70, 16448255);
			
			if (((mContainer).mDisplayErrorCode & 64) != 0) {
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 30, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "Incomplete Structure", 25, 40, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 50, 0);
			}
			
			if ((mContainer).mDisplayErrorCode == 0) {
				if ((mContainer).mActive == 0) {
					fontRendererObj
							.drawString("Progress:" + EnumChatFormatting.RED + " not working", 10, 22, 16448255);
				}
				
				if (container.isMoxFuel) {
					fontRendererObj
							.drawString(EnumChatFormatting.RED + "Mox Fuel", 10, 148, 16448255);
				}
				
				double tScale = (double) container.mTemp / (double) container.mMaxTemp;
				tScale = tScale <= 0 ? 0 : tScale;
				int temperature = Math.min(((int) (100 * tScale)), 100);
				fontRendererObj
						.drawString("Temp: " + temperature + "%", 96, 148, 16448255);
				
				fontRendererObj
						.drawString("Input " + (container.isFastDecay ? "Coolant:" : " Water:"), 10, 95, 16448255);
				fontRendererObj
						.drawString(container.mInput < 0 ? "All Rods UP" : NumberFormat.getNumberInstance().format(container.mInput / 8.0) + " L/t", 10, 105, 16448255);
				
				fontRendererObj
						.drawString("Output " + (container.isFastDecay ? "Hot Coolant:" : container.isMoxFuel ? "SH Steam:" : "Steam:"), 10, 120, 16448255);
				fontRendererObj
						.drawString(container.mOutput < 0 ? "All Rods UP" : (NumberFormat.getNumberInstance().format(container.mOutput / 8.0) + " L/t"), 10, 130, 16448255);
				
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			GT_Container_NuclearReactor container = (GT_Container_NuclearReactor) this.mContainer;
			if (mContainer.mTileEntity != null) {
				getTooltip(mouseX, mouseY, 155, 5, 16, 16, new String[]{
						"Start/Stop machine",
						EnumChatFormatting.DARK_GRAY + "Button color:",
						EnumChatFormatting.RED + "    Any Problem",
						EnumChatFormatting.YELLOW + "    Ready to start",
						EnumChatFormatting.GREEN + "    Starting",
				});
				getTooltip(mouseX, mouseY, 155, 37, 16, 16, new String[]{
						"Up all rods (10%)",
						
				});
				getTooltip(mouseX, mouseY, 155, 55, 16, 16, new String[]{
						"Down all rods (10%)",
						
						
				});
				getTooltip(mouseX, mouseY, 155, 73, 16, 16, new String[]{
						"Stop temperature",
						container.isFastDecay ? EnumChatFormatting.DARK_GRAY + "No usage" : container.stopTemp ? EnumChatFormatting.RED + "Enabled" : EnumChatFormatting.DARK_GRAY + "Disabled"
				});
				double tScale = (double) container.mTemp / (double) container.mMaxTemp;
				tScale = tScale <= 0 ? 0 : tScale;
				float temperature = (float) Math.min((100 * tScale), 100);
				getTooltip(mouseX, mouseY, 159, 114, 30, 8, new String[]{
						"" + temperature + "%"
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
			GT_Container_NuclearReactor container = (GT_Container_NuclearReactor) this.mContainer;
			if ((mContainer).mDisplayErrorCode == 0) {
				if ((mContainer).mActive == 0) {
					drawTexturedModalRect(x + 155, y + 4, 236, 24, 16, 16);
					drawTexturedModalRect(x + 141, y + 8, 238, 0, 9, 9);
				} else {
					drawTexturedModalRect(x + 155, y + 4, 236, 39, 16, 16);
				}
				
				if (container.stopTemp) {
					drawTexturedModalRect(x + 155, y + 73, 212, 0, 16, 16);
				} else {
					drawTexturedModalRect(x + 155, y + 73, 212, 15, 16, 16);
				}
				
				drawTexturedModalRect(x + 141, y + 8, 247, 0, 9, 9);
				
				double tScale = (double) container.mTemp / (double) container.mMaxTemp;
				drawTexturedModalRect(x + 159, y + 144 - (int) Math.min(30D, tScale * 30D), 228, 30 - (int) Math.min(30D, tScale * 30D), 8, 30);
			} else {
				drawTexturedModalRect(x + 155, y + 4, 236, 9, 16, 16);
			}
		}
	}
	
	public String trans(String aKey, String aEnglish) {
		return GT_LanguageManager
				.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
	}
}