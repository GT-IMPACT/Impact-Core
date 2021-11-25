package com.impact.mods.nei.impactplugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.impact.mods.nei.impactplugin.builder.BuilderNEI;
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class NEI_Impact_SingleTemplate extends TemplateRecipeHandler {
	
	public BuilderNEI builder;
	
	public NEI_Impact_SingleTemplate(BuilderNEI builder) {
		this.builder = builder;
		this.transferRects.add(new RecipeTransferRect(builder.getRectangle(), getOverlayIdentifier()));
	}
	
	public static void drawText(int aX, int aY, String aString, int aColor) {
		Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
	}
	
	@Override
	public void drawExtras(int recipe) {
		builder.getDrawText().forEach(drawText -> drawText(drawText.x, drawText.y, drawText.text, drawText.color.hashCode()));
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack aResult) {
		ArrayList<ItemStack> tResults = new ArrayList<>();
		tResults.add(aResult);
		tResults.add(GT_OreDictUnificator.get(true, aResult));
		CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe();
		for (ItemStack tStack : tResults) {
			if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack)) {
				this.arecipes.add(tNEIRecipe);
				break;
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack aInput) {
		ArrayList<ItemStack> tInputs = new ArrayList<>();
		tInputs.add(aInput);
		tInputs.add(GT_OreDictUnificator.get(false, aInput));
		CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe();
		for (ItemStack tStack : tInputs) {
			if (tNEIRecipe.contains(tNEIRecipe.mInputs, tStack)) {
				this.arecipes.add(tNEIRecipe);
				break;
			}
		}
	}
	
	@Override
	public String getGuiTexture() {
		return builder.getGuiTextureName();
	}
	
	@Override
	public String getOverlayIdentifier() {
		return builder.getOverlayIdentifier();
	}
	
	@Override
	public String getRecipeName() {
		return builder.getRecipeName();
	}
	
	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 79);
	}
	
	@Override
	public TemplateRecipeHandler newInstance() {
		return new NEI_Impact_SingleTemplate(builder);
	}
	@Override
	public int recipiesPerPage() {
		return 2;
	}
	
	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
		currenttip.addAll(builder.getTooltip());
		return currenttip;
	}
	
	public class CachedDefaultRecipe extends TemplateRecipeHandler.CachedRecipe {
		
		public final List<PositionedStack> mOutputs = new ArrayList<>();
		public final List<PositionedStack> mInputs = new ArrayList<>();
		
		public CachedDefaultRecipe() {
			super();
			
			if (builder.getInStack().size() > 0) {
				builder.getInStack().forEach((stack, cords) -> this.mInputs.add(new FixedPositionedStack(stack, cords[0], cords[1])));
			}
			
			if (builder.getOutStack().size() > 0) {
				builder.getOutStack().forEach((stack, cords) -> this.mOutputs.add(new FixedPositionedStack(stack, cords[0], cords[1])));
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
			return this.mOutputs;
		}
	}
}