package com.impact.addon.nei.impactplugin.util;

import java.util.List;

import codechicken.nei.recipe.StackInfo;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

public final class FluidRecipeLookup {

    private FluidRecipeLookup() {
    }

    public static void addFluidStacks(ItemStack stack, List<ItemStack> candidates) {
        FluidStack fluid = GT_Utility.getFluidForFilledItem(stack, true);
        if (fluid == null) {
            fluid = StackInfo.getFluid(stack);
        }
        if (fluid == null) {
            return;
        }

        ItemStack displayStack = GT_Utility.getFluidDisplayStack(fluid, false);
        if (displayStack != null) {
            candidates.add(displayStack);
        }
        for (FluidContainerData container : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (container.fluid.isFluidEqual(fluid)) {
                candidates.add(GT_Utility.copy(container.filledContainer));
            }
        }
    }
}
