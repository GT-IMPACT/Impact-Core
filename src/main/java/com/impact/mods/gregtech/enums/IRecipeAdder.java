package com.impact.mods.gregtech.enums;

import net.minecraft.item.ItemStack;

public interface IRecipeAdder {

  boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt);

  boolean addDryingOvenRecipe(ItemStack aInput, ItemStack[] aOutputs, int aDuration, int aEUt);
}
