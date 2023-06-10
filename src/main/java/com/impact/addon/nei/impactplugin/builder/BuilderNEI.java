package com.impact.addon.nei.impactplugin.builder;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.HandlerInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.impact.addon.nei.impactplugin.NEI_Impact_SingleTemplate;
import com.impact.impact;
import com.impact.addon.nei.impactplugin.NEI_Impact_Config;
import com.impact.addon.nei.impactplugin.util.DrawText;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.common.items.ItemDebug;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.List;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;

public class BuilderNEI {
	
	private final Map<ItemStack, int[]> inStack = new LinkedHashMap<>();
	private final Map<ItemStack, int[]> outStack = new LinkedHashMap<>();
	private List<String> tooltip = new ArrayList<>();
	private final List<DrawText> drawText = new ArrayList<>();
	private String overlayIdentifier = "";
	private String guiTextureName = "";
	private String recipeName = "";
	private Rectangle rectangle;
	private HandlerInfo handlerInfo;
	
	public Map<ItemStack, int[]> getInStack() {
		return inStack;
	}
	
	public Map<ItemStack, int[]> getOutStack() {
		return outStack;
	}
	
	public List<String> getTooltip() {
		return tooltip;
	}
	
	public void setTooltip(List<String> tooltip) {
		this.tooltip = tooltip;
	}
	
	public List<DrawText> getDrawText() {
		return drawText;
	}
	
	public String getOverlayIdentifier() {
		return overlayIdentifier;
	}
	
	public void setOverlayIdentifier(String overlayIdentifier) {
		this.overlayIdentifier = overlayIdentifier;
	}
	
	public String getGuiTextureName() {
		return guiTextureName;
	}
	
	public void setGuiTextureName(String guiTextureName) {
		this.guiTextureName = guiTextureName;
	}
	
	public String getRecipeName() {
		return recipeName;
	}
	
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public HandlerInfo getHandlerInfo() {
		return handlerInfo;
	}
	
	public void setHandlerInfo(HandlerInfo handlerInfo) {
		this.handlerInfo = handlerInfo;
	}
	
	public static BuilderNEI start() {
		return new BuilderNEI();
	}
	
	public void build() {
		TemplateRecipeHandler template = new NEI_Impact_SingleTemplate(this);
		if (!NEI_Impact_Config.sIsAdded) {
			FMLInterModComms.sendRuntimeMessage(impact.instance, "NEIPlugins", "register-crafting-handler", "impact@" + recipeName + "@" + overlayIdentifier);
			GuiCraftingRecipe.craftinghandlers.add(template);
			GuiUsageRecipe.usagehandlers.add(template);
		}
	}
	
	public BuilderNEI addInput(ItemStack stack, int x, int y) {
		stack = stack == null ? new ItemStack(ItemDebug.getInstance(), 1) : stack;
		inStack.put(stack, new int[]{x, y});
		return this;
	}
	
	public BuilderNEI addOutput(ItemStack stack, int x, int y) {
		stack = stack == null ? new ItemStack(ItemDebug.getInstance(), 1) : stack;
		outStack.put(stack, new int[]{x, y});
		return this;
	}
	
	public BuilderNEI addNameOverlayID(String name) {
		overlayIdentifier = name;
		return this;
	}
	
	public BuilderNEI addRectangleRecipe(int x, int y, int w, int h) {
		rectangle = new Rectangle(x, y, w, h);
		return this;
	}
	
	public BuilderNEI addGuiTexture(String name) {
		guiTextureName = name;
		return this;
	}
	
	public BuilderNEI addRecipeName(String name) {
		recipeName = name;
		return this;
	}
	
	public BuilderNEI addTooltip(List<String> tooltip) {
		this.tooltip = tooltip;
		return this;
	}
	
	public BuilderNEI addText(int x, int y, Color color, String text) {
		drawText.add(new DrawText(x, y, color, text));
		return this;
	}
	
	public BuilderNEI addHandlerInfo(HandlerInfo handlerInfo) {
		this.handlerInfo = handlerInfo;
		handlerMap.put(handlerInfo.getHandlerName() , handlerInfo);
		return this;
	}
}