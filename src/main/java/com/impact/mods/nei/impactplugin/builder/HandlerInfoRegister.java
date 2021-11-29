package com.impact.mods.nei.impactplugin.builder;

import codechicken.nei.recipe.HandlerInfo;
import com.impact.core.Refstrings;
import com.impact.mods.gregtech.GT_ItemList;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;

public class HandlerInfoRegister {
	
	public HandlerInfoRegister(String nameHandler, ItemStack displayStack, int maxPages, int h, int w, int shift) {
		HandlerInfo info = new HandlerInfo.Builder(nameHandler, "Impact-Core", Refstrings.MODID)
				.setDisplayStack(displayStack).setMaxRecipesPerPage(maxPages).setHeight(h).setWidth(w).setShiftY(shift).build();
		handlerMap.put(info.getHandlerName(), info);
	}
	
	public HandlerInfoRegister(GT_Recipe.GT_Recipe_Map nameHandler, ItemList displayStack) {
		HandlerInfo info = new HandlerInfo.Builder(nameHandler.mUnlocalizedName, "Impact-Core", Refstrings.MODID)
				.setDisplayStack(displayStack.get(1)).setMaxRecipesPerPage(2).setHeight(135).setWidth(166).setShiftY(6).build();
		handlerMap.put(info.getHandlerName(), info);
	}
	
	public HandlerInfoRegister(GT_Recipe.GT_Recipe_Map nameHandler, GT_ItemList displayStack) {
		HandlerInfo info = new HandlerInfo.Builder(nameHandler.mUnlocalizedName, "Impact-Core", Refstrings.MODID)
				.setDisplayStack(displayStack.get(1)).setMaxRecipesPerPage(2).setHeight(135).setWidth(166).setShiftY(6).build();
		handlerMap.put(info.getHandlerName(), info);
	}
	
	public HandlerInfoRegister(String nameHandler, ItemStack displayStack) {
		HandlerInfo info = new HandlerInfo.Builder(nameHandler, "Impact-Core", Refstrings.MODID)
				.setDisplayStack(displayStack).setMaxRecipesPerPage(2).setHeight(135).setWidth(166).setShiftY(6).build();
		handlerMap.put(info.getHandlerName(), info);
	}
	
	public HandlerInfoRegister(GT_Recipe.GT_Recipe_Map nameHandler, ItemStack displayStack) {
		HandlerInfo info = new HandlerInfo.Builder(nameHandler.mUnlocalizedName, "Impact-Core", Refstrings.MODID)
				.setDisplayStack(displayStack).setMaxRecipesPerPage(2).setHeight(135).setWidth(166).setShiftY(6).build();
		handlerMap.put(info.getHandlerName(), info);
	}
}