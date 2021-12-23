package com.impact.recipe.json;

import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class RecipeJsonCrafing {
	
	public ItemStack[] inputItem;
	public ItemStack outputItem;
	
	public RecipeJsonCrafing(ItemStack[] aInputs, ItemStack aOutputs) {
		this.inputItem   = aInputs;
		this.outputItem  = aOutputs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (hashCode() == o.hashCode()) return true;
		RecipeJsonCrafing that = (RecipeJsonCrafing) o;
		return Arrays.equals(inputItem, that.inputItem) && outputItem.equals(that.outputItem);
	}
	
	@Override
	public int hashCode() {
		int result = Objects.hash(outputItem);
		result = 31 * result + Arrays.hashCode(inputItem);
		return result;
	}
}