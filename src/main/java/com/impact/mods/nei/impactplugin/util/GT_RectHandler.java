package com.impact.mods.nei.impactplugin.util;

import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_RectHandler implements IContainerInputHandler, IContainerTooltipHandler {
	
	public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
		return false;
	}
	
	public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyCode) {
		return false;
	}
	
	public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip) {
		
		return currenttip;
	}
	
	public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip) {
		return currenttip;
	}
	
	public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip) {
		return currenttip;
	}
	
	public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode) {
		return false;
	}
	
	public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {
	}
	
	public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
	}
	
	public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {
	}
	
	public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
		return false;
	}
	
	public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
	}
	
	public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {
	}
}
