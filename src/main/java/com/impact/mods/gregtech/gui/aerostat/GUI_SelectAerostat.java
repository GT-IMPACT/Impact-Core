package com.impact.mods.gregtech.gui.aerostat;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.gui.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.LinkedHashMap;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GUI_SelectAerostat extends GT_GUIContainerMT_Machine {
	
	public String mName;
	public String mStationName = "";
	public String playerName = "";
	public LinkedHashMap<String, PositionObject> map = new LinkedHashMap<>();
	
	public GUI_SelectAerostat(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new Countainer_SelectAerostat(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "AerostatSelect.png");
		mName = aName;
	}
	
	public String getNameLocation(int id, EnumChatFormatting color, boolean general) {
		int idd = 1;
		
		for (String name : mapPlayer().keySet()) {
			if (idd == id) {
				if (color == EnumChatFormatting.RESET) {
					return name;
				}
				PositionObject thisPos = new PositionObject(mContainer.mTileEntity);
				PositionObject newPos = mapPlayer().get(name);
				int distance = Utilits.distanceBetween3D(thisPos.xPos, newPos.xPos, thisPos.yPos, newPos.yPos, thisPos.zPos, newPos.zPos);
				return color + "" + (general ? EnumChatFormatting.BOLD + "> " + color : "") + name + color + " " + (distance + "m");
			}
			idd++;
		}
		return "";
	}
	
	public LinkedHashMap<String, PositionObject> mapPlayer() {
		LinkedHashMap<String, PositionObject> newMap = new LinkedHashMap<>();
		for (String name : Impact_API.sAerostat.keySet()) {
			PositionObject pos = Impact_API.sAerostat.get(name);
			if (pos.playerName.equals(playerName)) {
				newMap.put(name, pos);
			}
		}
		return newMap;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		if (mContainer != null) {
			Countainer_SelectAerostat container = (Countainer_SelectAerostat) this.mContainer;
			if (mContainer.mTileEntity != null) {
				getTooltip(mouseX, mouseY, 9, 158 - 60, 60, 14, new String[]{
						"Gas Amount: ",
						NumberFormat.getNumberInstance().format(container.curBuffer) + " / " +
								NumberFormat.getNumberInstance().format(GTMTE_Aerostat.MAX_BUFFER) + " L"
				});
			}
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		//this.fontRendererObj.drawString(mName, 33, 8, 16448255);
		Countainer_SelectAerostat container = (Countainer_SelectAerostat) this.mContainer;
		
		this.fontRendererObj.drawString(mName, 33, 8, 16448255);
		this.fontRendererObj.drawString("Owner: " + EnumChatFormatting.GREEN + playerName, 33, 18, 16448255);
		
		if (container.idLocation > 0) {
			this.fontRendererObj.drawString("Station Select:", 33, 28, 16448255);
			
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 5, EnumChatFormatting.DARK_GRAY, false), 33, 50 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 4, EnumChatFormatting.DARK_GRAY, false), 33, 60 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 3, EnumChatFormatting.DARK_GRAY, false), 33, 70 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 2, EnumChatFormatting.DARK_GRAY, false), 33, 80 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 1, EnumChatFormatting.DARK_GRAY, false), 33, 90 - 10, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation + 0, EnumChatFormatting.GOLD, true), 33, 100 - 5, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 1, EnumChatFormatting.DARK_GRAY, false), 33, 110, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 2, EnumChatFormatting.DARK_GRAY, false), 33, 120, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 3, EnumChatFormatting.DARK_GRAY, false), 33, 130, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 4, EnumChatFormatting.DARK_GRAY, false), 33, 140, 16448255);
			this.fontRendererObj.drawString(getNameLocation(container.idLocation - 5, EnumChatFormatting.DARK_GRAY, false), 33, 150, 16448255);
		} else {
			this.fontRendererObj.drawString(EnumChatFormatting.RED + "Station without name:", 33, 25, 16448255);
		}
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		Countainer_SelectAerostat container = (Countainer_SelectAerostat) mContainer;
		double tScale = (double) container.curBuffer / (double) GTMTE_Aerostat.MAX_BUFFER;
		drawTexturedModalRect(x + 9, y + 158 - (int) Math.min(60D, tScale * 60D), 242, 60 - (int) Math.min(60D, tScale * 60D), 14, 60);
		
	}
}