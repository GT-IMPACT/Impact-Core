package com.impact.mods.nei.impactplugin.ores;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.*;
import com.impact.core.Refstrings;
import com.impact.impact;
import com.impact.mods.nei.impactplugin.NEI_Impact_Config;
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;
import static gregtech.api.enums.Materials.Sulfur;
import static gregtech.api.enums.OrePrefixes.crushed;

public class NEI_Impact_OreBiomes extends TemplateRecipeHandler {
	
	public List<OreBiome> ores;
	
	public NEI_Impact_OreBiomes(List<OreBiome> ores) {
		this.ores = ores;
		
		this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(4, 0, 50, 16), getOverlayIdentifier()));
		
		if (!NEI_Impact_Config.sIsAdded) {
			FMLInterModComms.sendRuntimeMessage(impact.instance, "NEIPlugins", "register-crafting-handler", "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
			GuiCraftingRecipe.craftinghandlers.add(this);
			GuiUsageRecipe.usagehandlers.add(this);
		}
		
		HandlerInfo handlerInfo = new HandlerInfo.Builder(getOverlayIdentifier(), "Impact-Core", Refstrings.MODID)
				.setDisplayStack(GT_OreDictUnificator.get(crushed, Sulfur, 1)).setMaxRecipesPerPage(2).setHeight(145).setWidth(166).setShiftY(6).build();
		handlerMap.put(handlerInfo.getHandlerName(), handlerInfo);
	}
	
	public TemplateRecipeHandler newInstance() {
		return new NEI_Impact_OreBiomes(ores);
	}
	
	public static void drawText(int aX, int aY, String aString, int aColor) {
		Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
	}
	
	@Override
	public void drawExtras(int recipe) {
		
		if (ores.get(recipe) == null) return;
		
		int clr = Color.BLACK.hashCode();
		String name = ores.get(recipe).biomeGenBase == null ? "Any Biomes" : ores.get(recipe).biomeGenBase.biomeName;
		String vName = ores.get(recipe).name;
		int tier = ores.get(recipe).tier;
		
		drawText(4, 12, "Vein Name: " +  vName, clr);
		drawText(4, 85, "Biome Name: " +  name, clr);
		drawText(4, 95, "Tier: " + tier, clr);
		
		drawText(4, 0, "Show All", new Color(84, 81, 81).hashCode());
	}
	
	public String getOverlayIdentifier() {
		return "ores_biomes";
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 80);
	}
	
	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	@Override
	public String getRecipeName() {
		return "Biomes Ores";
	}
	
	@Override
	public String getGuiTexture() {
		return "impact:textures/gui/guiBase.png";
	}
	
	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
		TemplateRecipeHandler.CachedRecipe tObject = this.arecipes.get(aRecipeIndex);
		if ((tObject instanceof CachedDefaultRecipe)) {
			CachedDefaultRecipe tRecipe = (CachedDefaultRecipe) tObject;
			for (PositionedStack tStack : tRecipe.mOutputs) {
				if (aStack == tStack.item) {
					if ((!(tStack instanceof FixedPositionedStack)) || (((FixedPositionedStack) tStack).mChance <= 0) || (((FixedPositionedStack) tStack).mChance == 10000)) {
						break;
					}
					currenttip.add(((FixedPositionedStack) tStack).mChance / 100 + "." + (((FixedPositionedStack) tStack).mChance % 100 < 10 ? "0"
							+ ((FixedPositionedStack) tStack).mChance % 100 : Integer.valueOf(((FixedPositionedStack) tStack).mChance % 100)) + "%");
					break;
				}
			}
			for (PositionedStack tStack : tRecipe.mOutputs) {
				if (aStack == tStack.item) {
					if ((gregtech.api.enums.ItemList.Display_Fluid.isStackEqual(tStack.item, true, true)) || (tStack.item.stackSize != 0)) {
						break;
					}
					currenttip.add("Does not get consumed in the process");
					break;
				}
			}
		}
		return currenttip;
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getOverlayIdentifier())) {
			for (OreBiome oreBiome : ores) {
				this.arecipes.add(new CachedDefaultRecipe(oreBiome));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack aResult) {
		ArrayList<ItemStack> tResults = new ArrayList<>();
		tResults.add(aResult);
		tResults.add(GT_OreDictUnificator.get(true, aResult));
		
		for (OreBiome oreBiome : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(oreBiome);
			for (ItemStack tStack : tResults) {
				if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack)) {
					this.arecipes.add(tNEIRecipe);
					break;
				}
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack aResult) {
		ArrayList<ItemStack> tInputs = new ArrayList<>();
		tInputs.add(aResult);
		tInputs.add(GT_OreDictUnificator.get(false, aResult));
		
		for (OreBiome oreBiome : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(oreBiome);
			for (ItemStack tStack : tInputs) {
				if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack)) {
					this.arecipes.add(tNEIRecipe);
					break;
				}
			}
		}
	}
	
	public class CachedDefaultRecipe extends TemplateRecipeHandler.CachedRecipe {
		
		public final List<PositionedStack> mOutputs = new ArrayList<>();
		public final List<PositionedStack> mInputs = new ArrayList<>();
		
		public CachedDefaultRecipe(OreBiome ore) {
			super();
			int x = 0;
			for (int i = 0; i < ore.stacks.size(); i++) {
				if (i % 8 == 0) x++;
				if (ore.stacks.get(i) != null)
					this.mOutputs.add(new FixedPositionedStack(ore.stacks.get(i), 5 + i * 18, 5 + x * 18, ore.chance[i]));
			}
		}
		
		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(cycleticks / 10, this.mInputs);
		}
		
		@Override
		public PositionedStack getResult() {
			return null;
		}
		
		@Override
		public List<PositionedStack> getOtherStacks() {
			return getCycledIngredients(cycleticks / 10, this.mOutputs);
		}
	}
}