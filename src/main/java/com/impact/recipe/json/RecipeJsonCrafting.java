package com.impact.recipe.json;

import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class RecipeJsonCrafting {
	
	public ItemStack[] inputItem;
	public ItemStack outputItem;
	
	public RecipeJsonCrafting(ItemStack[] aInputs, ItemStack aOutputs) {
		this.inputItem   = aInputs;
		this.outputItem  = aOutputs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (hashCode() == o.hashCode()) return true;
		RecipeJsonCrafting that = (RecipeJsonCrafting) o;
		return Arrays.equals(inputItem, that.inputItem) && outputItem.equals(that.outputItem);
	}
	
	@Override
	public int hashCode() {
		int result = Objects.hash(outputItem);
		result = 31 * result + Arrays.hashCode(inputItem);
		return result;
	}
	
	public static String[] createShape(String[] remove) {
		String shapePref = "012~345~678";
			for (String s1 : remove) {
				if (!s1.isEmpty()) shapePref = shapePref.replaceAll(s1, " ");
			}
		return shapePref.split("~");
	}
	
	public static Object[] buildRecipeString(RecipeJsonCrafting recipe) {
		StringBuilder builderInputs = new StringBuilder();
		StringBuilder builderRemoveShapeChars = new StringBuilder();
		for (int i = 0; i < recipe.inputItem.length; i++) {
			ItemStack in = recipe.inputItem[i];
			if (in != null) builderInputs.append(i).append("~");
			else builderRemoveShapeChars.append(i).append("~");
		}
		String[] charsIn = builderInputs.toString().split("~");
		String[] shapeTotal = createShape(builderRemoveShapeChars.toString().split("~"));
		Object[] inputs = new Object[charsIn.length];
		Object[] inputsChar = new Object[charsIn.length];
		for (int i = 0; i < charsIn.length; i++) {
			int slot = Integer.parseInt(charsIn[i]);
			inputs[i] = charsIn[i].toCharArray()[0];
			inputsChar[i] = recipe.inputItem[slot];
		}
		Object[] inputsToObj = new Object[inputs.length * 2];
		int id = 0;
		for (Object o : inputs) { inputsToObj[id] = o; id += 2; }
		id = 1;
		for (Object o : inputsChar) { inputsToObj[id] = o; id += 2; }
		Object[] shape = new Object[inputsToObj.length + 3];
		shape[0] = shapeTotal[0];
		shape[1] = shapeTotal[1];
		shape[2] = shapeTotal[2];
		System.arraycopy(inputsToObj, 0, shape, 3, shape.length - 3);
		return shape;
	}
}