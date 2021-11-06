package com.impact.mods.gregtech.gui.filler;

import com.impact.mods.gregtech.gui.base.GUI_BASE;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public class GUI_Construction_Laser extends GUI_BASE {
	
	public GUI_Construction_Laser(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(aName, "Filler.png", new Container_Construction_Laser(aInventoryPlayer, aTileEntity));
		mName = aName;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		int clr = new Color(250, 248, 248).hashCode();
		fontRendererObj.drawString(mName, 9, 7, clr);
		if (this.mContainer != null) {
			Container_Construction_Laser cont = (Container_Construction_Laser) this.mContainer;
			fontRendererObj.drawString("- Information Panel -", 9, 17, clr);
			if (!cont.isDone) {
				fontRendererObj.drawString("Current Height: " + GREEN + cont.yCurrent + RESET, 9, 17 + 10, clr);
			}
			fontRendererObj.drawString("Status: " + (!cont.isDone ? GREEN + "Work" : RED + "Not Work"), 9, 17 + 20, clr);
		}
	}
	
	protected void drawGuiBG(boolean extendsBaseGui, boolean needProgressBar) {
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
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
			} else {
				drawTexturedModalRect(x + 152, y + 61, 236, 9, 16, 16);
			}
			
			Container_Construction_Laser cont = (Container_Construction_Laser) this.mContainer;
			if (cont.saveMode) {
				drawTexturedModalRect(x + 7, y + 61, 195, 0, 18, 18);
			}
			if (cont.killMobs) {
				drawTexturedModalRect(x + 25, y + 61, 177, 0, 18, 18);
			}
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
				getTooltip(mouseX, mouseY, 8, 62, 16, 16, new String[]{
						"Mode: Save TileEntities",
				});
				getTooltip(mouseX, mouseY, 26, 62, 16, 16, new String[]{
						"Kill all mobs in area",
				});
			}
		}
	}
}
