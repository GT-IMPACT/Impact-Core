package com.impact.mods.gregtech.gui.base;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;


public class GUI_BASE extends GT_GUIContainerMT_Machine {
	
	public String mMode;
	public String mName = "";
	
	public GUI_BASE(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile, String aModeString) {
		super(new GTC_ImpactBase(aInventoryPlayer, aTileEntity),RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
		mName = aName;
		mMode = aModeString;
	}
	
	public GUI_BASE(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new GTC_ImpactBase(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "multimachines/MultiParallelBlockGUI.png");
		mName = aName;
		mMode = "";
	}
	
	public GUI_BASE(String aName, String aTextureFile, GTC_ImpactBase aContainer) {
		super(aContainer, RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
		mName = aName;
	}
	
	public GUI_BASE(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
		super(new GTC_ImpactBase(aInventoryPlayer, aTileEntity),RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
		mName = aName;
		mMode = "";
	}
	
	public GUI_BASE(String aName, String aTextureFile, GT_ContainerMetaTile_Machine c) {
		super(c, RES_PATH_GUI + "multimachines/" + (aTextureFile == null ? "MultiblockDisplay" : aTextureFile));
		mName = aName;
		mMode = "";
	}
	
	protected void drawWorkBar(boolean extendBaseGui) {
		if (extendBaseGui) {
			if ((mContainer).mDisplayErrorCode == 0) {
				if ((mContainer).mActive == 0) {
					fontRendererObj.drawString("Progress:" + EnumChatFormatting.RED + " not working", 10, 22, 16448255);
				} else {
					double tScale = ((double) this.mContainer.mProgressTime / (double) this.mContainer.mMaxProgressTime) * 100;
					if ((int) tScale > 0 && (int) tScale < 100) {
						fontRendererObj.drawString("Progress: " + EnumChatFormatting.GREEN + this.mContainer.mProgressTime / 20 + EnumChatFormatting.WHITE
								+ " / " + EnumChatFormatting.YELLOW + this.mContainer.mMaxProgressTime / 20 + EnumChatFormatting.WHITE + " sec", 10, 22, 16448255);
						fontRendererObj.drawString(GT_Utility.formatNumbers((int) tScale) + "%", 71, 56, 16448255);
					}
				}
			}
		}
	}
	
	protected void needTools(boolean extendBaseGui) {
		if (extendBaseGui) {
			if (((mContainer).mDisplayErrorCode & 64) != 0) {
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 30, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "Incomplete Structure", 25, 40, 0);
				fontRendererObj.drawString(EnumChatFormatting.RED + "==================", 25, 50, 0);
			} else {
				if (((this.mContainer).mDisplayErrorCode & 1) != 0) {
					this.fontRendererObj.drawString(this.trans("132", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Wrench"), 10, 20, 0);
				}
				
				if (((this.mContainer).mDisplayErrorCode & 2) != 0) {
					this.fontRendererObj.drawString(this.trans("133", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Screwdriver"), 10, 29, 0);
				}
				
				if (((this.mContainer).mDisplayErrorCode & 4) != 0) {
					this.fontRendererObj.drawString(this.trans("134", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " SoftHammer"), 10, 38, 0);
				}
				
				if (((this.mContainer).mDisplayErrorCode & 8) != 0) {
					this.fontRendererObj.drawString(this.trans("135", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Hammer"), 10, 47, 0);
				}
				
				if (((this.mContainer).mDisplayErrorCode & 16) != 0) {
					this.fontRendererObj.drawString(this.trans("136", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Soldering"), 10, 56, 0);
				}
				
				if (((this.mContainer).mDisplayErrorCode & 32) != 0) {
					this.fontRendererObj.drawString(this.trans("137", EnumChatFormatting.WHITE + "Need" + EnumChatFormatting.RED + " Crowbar"), 10, 65, 0);
				}
			}
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(mName, 10, 8, 16448255);
		if (this.mContainer != null) {
			needTools(true);
			drawWorkBar(true);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			if (mContainer.mTileEntity != null) {
				getTooltip(mouseX, mouseY, 152, 62, 16, 16, new String[]{
						"Start/Stop machine",
						EnumChatFormatting.DARK_GRAY + "Button color:",
						EnumChatFormatting.RED + "    Any Problem",
						EnumChatFormatting.YELLOW + "    Ready to start",
						EnumChatFormatting.GREEN + "    Starting",
				});
			}
		}
	}
	
	protected void drawGuiBG(boolean extendsBaseGui, boolean needProgressBar) {
		if (extendsBaseGui) {
			int x = (this.width - this.xSize) / 2;
			int y = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
			if (this.mContainer != null) {
				if ((mContainer).mDisplayErrorCode == 0) {
					if ((mContainer).mActive == 0) {
						drawTexturedModalRect(x + 152, y + 61, 236, 24, 16, 16);
						drawTexturedModalRect(x + 157, y + 16, 238, 0, 9, 9);
					} else {
						drawTexturedModalRect(x + 152, y + 61, 236, 39, 16, 16);
					}
					drawTexturedModalRect(x + 157, y + 16, 247, 0, 9, 9);
					if (needProgressBar) {
						double tScale = (double) this.mContainer.mProgressTime / (double) this.mContainer.mMaxProgressTime;
						drawTexturedModalRect(x + 22, y + 55, 0, 232, Math.min(113, (int) (tScale * 113)), 9);
						drawTexturedModalRect(x + 19, y + 52, 0, 241, 119, 15);
					}
				} else {
					drawTexturedModalRect(x + 152, y + 61, 236, 9, 16, 16);
				}
			}
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		drawGuiBG(true, true);
	}
	
	public String trans(String aKey, String aEnglish) {
		return GT_LanguageManager.addStringLocalization("Interaction_DESCRIPTION_Index_" + aKey, aEnglish, false);
	}
}
