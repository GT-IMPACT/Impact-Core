package com.impact.mods.nei.impactplugin.ores;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.*;
import com.impact.common.oregeneration.OreVein;
import com.impact.core.Refstrings;
import com.impact.impact;
import com.impact.mods.nei.impactplugin.NEI_Impact_Config;
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;
import static gregtech.api.enums.Materials.Redstone;
import static gregtech.api.enums.OrePrefixes.*;

public class NEI_Impact_DimOres extends TemplateRecipeHandler {
	public List<OreBuilderNEI.DimOre> ores;
	public int tier;
	protected boolean ttDisplayed = false;
	
	public NEI_Impact_DimOres(List<OreBuilderNEI.DimOre> ores, int tier) {
		this.ores = ores;
		this.tier = tier;
		
		this.transferRects.add(new RecipeTransferRect(new Rectangle(4, 0, 50, 16), getOverlayIdentifier()));
		
		if (!NEI_Impact_Config.sIsAdded) {
			FMLInterModComms.sendRuntimeMessage(impact.instance, "NEIPlugins", "register-crafting-handler", "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
			GuiCraftingRecipe.craftinghandlers.add(this);
			GuiUsageRecipe.usagehandlers.add(this);
		}
		
		HandlerInfo handlerInfo = new HandlerInfo.Builder(getOverlayIdentifier(), "Impact-Core", Refstrings.MODID)
				.setDisplayStack(tier == 0
						? GT_OreDictUnificator.get(crushed, Redstone, 1)
						: new ItemStack(Blocks.redstone_ore))
				.setMaxRecipesPerPage(2).setHeight(165).setWidth(200).setShiftY(6).build();
		handlerMap.put(handlerInfo.getHandlerName(), handlerInfo);
	}
	
	public static void drawText(int aX, int aY, String aString, int aColor) {
		Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
	}
	
	protected static int calculateMaxW(List<String> L) {
		int w = 0;
		FontRenderer font = GuiDraw.fontRenderer;
		for (String s : L) {
			w = Math.max(font.getStringWidth(s), w);
		}
		return w;
	}
	
	public TemplateRecipeHandler newInstance() {
		return new NEI_Impact_DimOres(ores, tier);
	}
	
	@Override
	public void drawExtras(int recipe) {
		
		CachedDefaultRecipe cache = (CachedDefaultRecipe) arecipes.get(recipe);
		OreBuilderNEI.DimOre oreBuilderNEI = cache.ore;
		
		if (oreBuilderNEI == null) return;
		
		int clr = Color.BLACK.hashCode();
		String name = oreBuilderNEI.dimName;
		
		drawText(4, 0, "Show All", new Color(84, 81, 81).hashCode());
		drawText(4, 10, "Dim Name: " + name, clr);
		drawText(164 - GuiDraw.fontRenderer.getStringWidth("Use Shift"), 0, "Use Shift", new Color(84, 81, 81).hashCode());
		List<String> dims = new ArrayList<>();
		for (int i = 0; i < oreBuilderNEI.veins.size(); i++) {
			dims.add((i + 1) + ". " + oreBuilderNEI.veins.get(i).nameVein);
		}
		ttDisplayed = false;
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if (dims.size() >= 15) {
				List<String> dims2 = dims.subList(15, dims.size());
				dims = dims.subList(0, 15);
				int w = calculateMaxW(dims);
				GuiDraw.drawMultilineTip(w + 15, 0, dims2);
			}
			GuiDraw.drawMultilineTip(0, 0, dims);
			ttDisplayed = true;
		}
	}
	
	public String getOverlayIdentifier() {
		return "impact_ores_small_" + tier;
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
		return "Dimension List" + (tier == 0 ? " Small" : "") + " Veins";
	}
	
	@Override
	public String getGuiTexture() {
		return "impact:textures/gui/guiBase.png";
	}
	
	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
		CachedRecipe tObject = this.arecipes.get(aRecipeIndex);
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
			for (PositionedStack tStack : tRecipe.mInputs) {
				if (aStack == tStack.item) {
					if (ItemList.Display_Fluid.isStackEqual(tStack.item, true, true)) {
						currenttip.add("Per 1 cycle");
					}
				}
			}
		}
		return currenttip;
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals(getOverlayIdentifier())) {
			for (OreBuilderNEI.DimOre oreBuilderNEI : ores) {
				this.arecipes.add(new CachedDefaultRecipe(oreBuilderNEI));
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
		
		for (OreBuilderNEI.DimOre oreBuilderNEI : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(oreBuilderNEI);
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
		for (OreBuilderNEI.DimOre oreBuilderNEI : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(oreBuilderNEI);
			for (ItemStack tStack : tInputs) {
				if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack) || tNEIRecipe.contains(tNEIRecipe.mInputs, tStack)) {
					this.arecipes.add(tNEIRecipe);
					break;
				}
			}
		}
	}
	
	public class CachedDefaultRecipe extends CachedRecipe {
		
		public final List<PositionedStack> mOutputs = new ArrayList<>();
		public final List<PositionedStack> mInputs = new ArrayList<>();
		OreBuilderNEI.DimOre ore;
		
		public CachedDefaultRecipe(OreBuilderNEI.DimOre ore) {
			super();
			this.ore = ore;
			List<ItemStack> s = new ArrayList<>();
			for (OreVein vein : ore.veins) {
				for (ItemStack stack : vein.ores) {
					if (stack != null) {
						if (!s.contains(stack)) {
							s.add(stack);
							break;
						}
					}
				}
			}
			int y = 0;
			int count = 0;
			for (int i = 0; i < s.size(); i++) {
				if (s.get(i) != null) {
					this.mOutputs.add(new FixedPositionedStack(s.get(i), 4 + i % 9 * 18, 20 + y * 18));
				}
				count++;
				if (count == 9) {
					y++;
					count = 0;
				}
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