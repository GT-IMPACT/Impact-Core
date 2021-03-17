package com.impact.mods.gregtech.enums;

import net.minecraft.item.ItemStack;

public interface IRecipeAdder {

  public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration,
      int aEUt);
}
