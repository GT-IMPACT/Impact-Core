package com.impact.mods.gregtech.gui.aerostat;

import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.network.IPacketString;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static net.minecraft.util.EnumChatFormatting.*;

public class GUI_SelectAerostat extends GT_GUIContainerMT_Machine implements IPacketString {
	
	public String mName;
	public String mStationName = "";
	public String playerName = "";
	public LinkedHashMap<String, PositionObject> map = new LinkedHashMap<>();
	public List<String> names = new ArrayList<>();
	
	public GUI_SelectAerostat(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName) {
		super(new Countainer_SelectAerostat(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "AerostatSelect.png");
		mName = aName;
	}
	
	public String getNameLocation(int id, EnumChatFormatting color, boolean general) {
		Countainer_SelectAerostat container = (Countainer_SelectAerostat) this.mContainer;
		int idd = 1;
		for (String name : names) {
			if (idd == id) {
				if (color == EnumChatFormatting.RESET) return name;
				return color + "" + (general ? EnumChatFormatting.BOLD + "> " + color : "") + name + color + (general ? " " + container.distance + "m" : "");
			}
			idd++;
		}
		return "";
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
		
		this.fontRendererObj.drawString(mName + " " + GREEN + mStationName, 33, 8, 16448255);
		this.fontRendererObj.drawString("Owner: " + YELLOW + playerName, 33, 18, 16448255);
		
		if (!mStationName.isEmpty()) {
			if (!names.isEmpty()) {
				this.fontRendererObj.drawString("Station Select:", 33, 28, 16448255);
				this.fontRendererObj.drawString("Need Gas to move: " + container.distance * 25 + "L", 33, 38, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation + 4, DARK_GRAY, false), 33, 60 - 10, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation + 3, DARK_GRAY, false), 33, 70 - 10, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation + 2, DARK_GRAY, false), 33, 80 - 10, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation + 1, DARK_GRAY, false), 33, 90 - 10, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation + 0, GOLD, true), 33, 100 - 5, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation - 1, DARK_GRAY, false), 33, 110, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation - 2, DARK_GRAY, false), 33, 120, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation - 3, DARK_GRAY, false), 33, 130, 16448255);
				this.fontRendererObj.drawString(getNameLocation(container.idLocation - 4, DARK_GRAY, false), 33, 140, 16448255);
			} else {
				this.fontRendererObj.drawString(EnumChatFormatting.RED + "No Stations", 33, 70, 16448255);
				this.fontRendererObj.drawString(EnumChatFormatting.RED + "in radius 512 blocks! ", 33, 80, 16448255);
			}
		} else {
			this.fontRendererObj.drawString(EnumChatFormatting.RED + "Station without name!", 33, 70, 16448255);
			this.fontRendererObj.drawString(EnumChatFormatting.RED + "Station no valid!", 33, 90, 16448255);
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
	
	@Override
	public final void update(String... obj) {
		this.playerName = obj[0];
		this.mStationName = obj[1];
		names.addAll(Arrays.asList(obj).subList(2, obj.length));
	}
}