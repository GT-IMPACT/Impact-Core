package com.impact.mods.gregtech.tileentities.multi.implement;

import gregtech.api.logic.FuelProcessingLogic;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/** The liquid naquadah family shares its fuel admission and warmup lifecycle. */
public abstract class GTMTE_Impact_FuelGeneratorBase<T extends GTMTE_Impact_FuelGeneratorBase<T>>
        extends GTMTE_Impact_BlockBase<T> {

    public int fuelConsumption;

    protected GTMTE_Impact_FuelGeneratorBase(int id, String name, String regionalName) {
        super(id, name, regionalName);
    }

    protected GTMTE_Impact_FuelGeneratorBase(String name) {
        super(name);
    }

    protected abstract long getFuelCycleGeneration();

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new FuelProcessingLogic(this::getRecipeMap, recipe -> {
            FluidStack fuel = GT_Utility.getFluidForFilledItem(recipe.getRepresentativeInput(0), true);
            if (fuel == null || recipe.mSpecialValue <= 0) {
                return null;
            }
            fuel.amount = recipe.mSpecialValue;
            return new FuelProcessingLogic.FuelCycle(
                    getFuelCycleGeneration(),
                    20,
                    fuel,
                    new FluidStack[0],
                    new FluidStack[0]
            );
        });
    }

    @Override
    protected void startCycle(ProcessingLogic.Result result, int efficiency) {
        super.startCycle(result, mEfficiency);
        fuelConsumption = ((FuelProcessingLogic) getProcessingLogic()).getConsumption();
        mEfficiencyIncrease = 500;
    }

    @Override
    public boolean checkRecipe(ItemStack stack) {
        boolean started = super.checkRecipe(stack);
        if (!started && mMaxProgresstime == 0 && !isWaitingForOutput()) {
            mEfficiency = 0;
        }
        return started;
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        super.saveNBTData(tag);
        tag.setInteger("fuelConsumption", fuelConsumption);
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        super.loadNBTData(tag);
        fuelConsumption = tag.getInteger("fuelConsumption");
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }
}
