package com.impact.recipe.json;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Objects;

public class RecipeJson {
	
	public final ItemStack[] inputItem, outputItem;
	public final FluidStack[] inputFluid, outputFluid;
	public final int time, voltage, special;
	public final int[] chance;
	public final boolean optimize;
	
	public RecipeJson(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, int[] aOutputChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs,
					  int aDuration, int aEUt, int aSpecialValue) {
		this.inputItem   = aInputs;
		this.outputItem  = aOutputs;
		this.inputFluid  = aFluidInputs;
		this.outputFluid = aFluidOutputs;
		this.time        = aDuration;
		this.voltage     = aEUt;
		this.special     = aSpecialValue;
		this.chance      = aOutputChances;
		this.optimize    = aOptimize;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (hashCode() == o.hashCode()) return true;
		RecipeJson that = (RecipeJson) o;
		return time == that.time && voltage == that.voltage &&
				Arrays.equals(inputItem, that.inputItem) && Arrays.equals(outputItem, that.outputItem) &&
				Arrays.equals(inputFluid, that.inputFluid) && Arrays.equals(outputFluid, that.outputFluid);
	}
	
	@Override
	public int hashCode() {
		int result = Objects.hash(time, voltage);
		result = 31 * result + Arrays.hashCode(inputItem);
		result = 31 * result + Arrays.hashCode(outputItem);
		result = 31 * result + Arrays.hashCode(inputFluid);
		result = 31 * result + Arrays.hashCode(outputFluid);
		return result;
	}
}