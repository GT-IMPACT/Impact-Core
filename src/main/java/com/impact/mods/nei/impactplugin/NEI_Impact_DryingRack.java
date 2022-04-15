package com.impact.mods.nei.impactplugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.*;
import com.impact.common.te.TE_DryingRack;
import com.impact.core.Refstrings;
import com.impact.impact;
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tconstruct.plugins.nei.RecipeHandlerDryingRack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;

public class NEI_Impact_DryingRack extends TemplateRecipeHandler {
	
	public List<TE_DryingRack.DryingRackRecipes.DryingRecipe> recipes;
	public int tier;
	protected boolean ttDisplayed = false;
	
	public NEI_Impact_DryingRack() {
		this.recipes = TE_DryingRack.DryingRackRecipes.recipes;
		this.transferRects.add(new RecipeTransferRect(new Rectangle(68, 20, 22, 15), getOverlayIdentifier()));
		if (!NEI_Impact_Config.sIsAdded) {
			FMLInterModComms.sendRuntimeMessage(impact.instance, "NEIPlugins", "register-crafting-handler", "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
			GuiCraftingRecipe.craftinghandlers.add(this);
			GuiUsageRecipe.usagehandlers.add(this);
		}
		
		HandlerInfo handlerInfo = new HandlerInfo.Builder(getOverlayIdentifier(), "Impact-Core", Refstrings.MODID)
				.setDisplayStack(new ItemStack(Items.leather))
				.setMaxRecipesPerPage(4).setHeight(80).setWidth(200).setShiftY(6).build();
		handlerMap.put(handlerInfo.getHandlerName(), handlerInfo);
	}
	
	public static void drawText(int aX, int aY, String aString, int aColor) {
		Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
	}
	
	public TemplateRecipeHandler newInstance() {
		return new NEI_Impact_DryingRack();
	}
	
	@Override
	public void drawExtras(int recipe) {
		int time = ((NEI_Impact_DryingRack.CachedDefaultRecipe)this.arecipes.get(recipe)).time;
		int seconds = time / 20;
		GuiDraw.drawStringC(time + " ticks (" + seconds + " secs)", 81, 40, 8421504, false);
	}
	
	public String getOverlayIdentifier() {
		return "impact_drying_rack";
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 160, 65);
	}
	
	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	@Override
	public String getRecipeName() {
		return "Drying Rack";
	}
	
	@Override
	public String getGuiTexture() {
		return "impact:textures/gui/dryingrack.png";
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
			for (TE_DryingRack.DryingRackRecipes.DryingRecipe recipe : recipes) {
				this.arecipes.add(new CachedDefaultRecipe(recipe));
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
		
		for (TE_DryingRack.DryingRackRecipes.DryingRecipe recipe : recipes) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(recipe);
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
		for (TE_DryingRack.DryingRackRecipes.DryingRecipe recipe : recipes) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(recipe);
			for (ItemStack tStack : tInputs) {
				if (tNEIRecipe.contains(tNEIRecipe.mInputs, tStack)) {
					this.arecipes.add(tNEIRecipe);
					break;
				}
			}
		}
	}
	
	public class CachedDefaultRecipe extends CachedRecipe {
		
		public final List<PositionedStack> mOutputs = new ArrayList<>();
		public final List<PositionedStack> mInputs = new ArrayList<>();
		public int time;
		
		public CachedDefaultRecipe(TE_DryingRack.DryingRackRecipes.DryingRecipe recipe) {
			super();
			if (recipe.input != null) {
				this.mInputs.add(new FixedPositionedStack(recipe.input, 44, 18));
			}
			if (recipe.result != null) {
				this.mOutputs.add(new FixedPositionedStack(recipe.result, 98, 18));
			}
			this.time = recipe.time;
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