package com.impact.mods.GregTech.enums;

import net.minecraft.item.ItemStack;

public interface IRecipeAdder {

  public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration,
      int aEUt);

  public boolean addVeinOres(ItemStack[] aOutputs, int aBiomeNumber);

}
