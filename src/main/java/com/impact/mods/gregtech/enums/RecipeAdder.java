package com.impact.mods.gregtech.enums;

import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import net.minecraft.item.ItemStack;

public class RecipeAdder implements IRecipeAdder {

  @Override
  public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration,
      int aEUt) {
    GTMTE_RailAssembler.sTrackAssemblerRecipes.addRecipe(true,
        aInputs, new ItemStack[]{aOutput}, null, null, null, null, aDuration, aEUt, 0);
    return true;
  }
}
