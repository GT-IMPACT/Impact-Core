package com.impact.mods.nei.impactplugin.ores;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.*;
import com.impact.core.Refstrings;
import com.impact.impact;
import com.impact.mods.gregtech.enums.DropsBlock;
import com.impact.mods.gregtech.items.tools.GTMG_Tool_WorkRadius;
import com.impact.mods.nei.impactplugin.NEI_Impact_Config;
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static codechicken.nei.recipe.GuiRecipeTab.handlerMap;
import static gregtech.api.enums.Materials.Sulfur;
import static gregtech.api.enums.OrePrefixes.crushed;
import static gregtech.api.enums.OrePrefixes.drop;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

public class NEI_Impact_HammerDrop extends TemplateRecipeHandler {
	
	public List<DropsBlock> ores;
	
	public NEI_Impact_HammerDrop(List<DropsBlock> ores) {
		this.ores = ores;
		
		this.transferRects.add(new RecipeTransferRect(new Rectangle(5, 0, Minecraft.getMinecraft().fontRenderer.getStringWidth("Show All"), 10), getOverlayIdentifier()));
		
		if (!NEI_Impact_Config.sIsAdded) {
			FMLInterModComms.sendRuntimeMessage(impact.instance, "NEIPlugins", "register-crafting-handler", "impact@" + getRecipeName() + "@" + getOverlayIdentifier());
			GuiCraftingRecipe.craftinghandlers.add(this);
			GuiUsageRecipe.usagehandlers.add(this);
		}
		
		HandlerInfo handlerInfo = new HandlerInfo.Builder(getOverlayIdentifier(), "Impact-Core", Refstrings.MODID)
				.setDisplayStack(GTMG_Tool_WorkRadius.INSTANCE.getToolWithStats(GTMG_Tool_WorkRadius.FORGE_HAMMER, 1, Materials.Flint, Materials.Wood, null))
				.setMaxRecipesPerPage(5).setHeight(64).setWidth(166).setShiftY(6).build();
		handlerMap.put(handlerInfo.getHandlerName(), handlerInfo);
	}
	
	public TemplateRecipeHandler newInstance() {
		return new NEI_Impact_HammerDrop(ores);
	}
	
	public static void drawText(int aX, int aY, String aString, int aColor) {
		Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
	}
	
	@Override
	public void drawExtras(int recipe) {
		drawText(5, 0, "Show All", new Color(84, 81, 81).hashCode());
		drawText(5, 38, "Advanced Harvester Mode", new Color(84, 81, 81).hashCode());
	}
	
	public String getOverlayIdentifier() {
		return "drops_hammer";
	}
	
	@Override
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 80);
		GuiDraw.changeTexture("impact:textures/gui/widgets/sprites.png");
		GuiDraw.drawTexturedModalRect(5+18, 10, 0, 0, 16, 16);
	}
	
	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	@Override
	public String getRecipeName() {
		return "Drops Hammer";
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
					currenttip.add(YELLOW + "Chance drop: " + ((FixedPositionedStack) tStack).mChance / 100 + "." + (((FixedPositionedStack) tStack).mChance % 100 < 10 ? "0"
							+ ((FixedPositionedStack) tStack).mChance % 100 : Integer.valueOf(((FixedPositionedStack) tStack).mChance % 100)) + "%");
					break;
				}
			}
			for (PositionedStack tStack : tRecipe.mOutputs) {
				if (aStack == tStack.item) {
					if ((ItemList.Display_Fluid.isStackEqual(tStack.item, true, true)) || (tStack.item.stackSize != 0)) {
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
			for (DropsBlock drops : ores) {
				this.arecipes.add(new CachedDefaultRecipe(drops));
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
		
		for (DropsBlock drops : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(drops);
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
		
		for (DropsBlock drops : ores) {
			CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(drops);
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
		
		public CachedDefaultRecipe(DropsBlock ore) {
			super();
			for (int i = 0; i < ore.drop.size(); i++) {
				if (ore.drop.get(i) != null) {
					this.mOutputs.add(new FixedPositionedStack(ore.drop.get(i), 59 + i * 18, 10, (int) (ore.chance[i] * 10000f)));
					
				}
			}
			ItemStack dropBlockFirst = ore.block.getDrops(impact.proxy.getClientWorld(), 0, 0, 0, ore.meta, 0).get(0);
			this.mOutputs.add(new FixedPositionedStack(dropBlockFirst, 5+18+18, 10, (int) (ore.chance[0] * 10000f)));
			this.mInputs.add(new FixedPositionedStack(new ItemStack(ore.block, 1, ore.meta), 5, 10));
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