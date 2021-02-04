package com.impact.mods.GregTech.enums;

import com.impact.mods.GregTech.tileentities.multi.GTMTE_RailAssembler;
import com.impact.recipes.RegisterOreVein;
import net.minecraft.item.ItemStack;

public class RecipeAdder implements IRecipeAdder {

  @Override
  public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration,
      int aEUt) {
    GTMTE_RailAssembler.sTrackAssemblerRecipes.addRecipe(true,
        aInputs, new ItemStack[]{aOutput}, null, null, null, null, aDuration, aEUt, 0);
    return true;
  }

  @Override
  public boolean addVeinOres(ItemStack[] aOutputs, int aBiomeNumber) {
    RegisterOreVein.sVeinOres.addRecipe(false,
        null, aOutputs, null, null, null, null, 0, 0, aBiomeNumber);
    return true;
  }
}
