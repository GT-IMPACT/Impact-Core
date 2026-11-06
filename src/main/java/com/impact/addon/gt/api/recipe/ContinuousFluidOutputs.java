package com.impact.addon.gt.api.recipe;

import gregtech.api.logic.IProcessingOutputSink;
import gregtech.api.logic.ProcessingPersistence;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Retains protected products while combustion or residual cooling continues. */
public final class ContinuousFluidOutputs {

    private final List<FluidStack> remaining = new ArrayList<>();

    public void emit(FluidStack fluid, boolean protect, IProcessingOutputSink output) {
        flush(output);
        if (fluid == null || fluid.amount <= 0) {
            return;
        }
        FluidStack product = fluid.copy();
        output.deliver(null, new FluidStack[] { product }, false, protect);
        if (product.amount > 0) {
            for (FluidStack stored : remaining) {
                if (stored.isFluidEqual(product)) {
                    int accepted = Math.min(product.amount, Integer.MAX_VALUE - stored.amount);
                    stored.amount += accepted;
                    product.amount -= accepted;
                    if (product.amount == 0) {
                        return;
                    }
                }
            }
            remaining.add(product);
        }
    }

    public boolean flush(IProcessingOutputSink output) {
        output.deliver(null, remaining.toArray(new FluidStack[0]), false, true);
        remaining.removeIf(fluid -> fluid.amount <= 0);
        return remaining.isEmpty();
    }

    public boolean isEmpty() {
        return remaining.isEmpty();
    }

    public FluidStack[] finish(FluidStack[] finalOutputs) {
        List<FluidStack> outputs = new ArrayList<>(remaining);
        if (finalOutputs != null) {
            outputs.addAll(Arrays.asList(finalOutputs));
        }
        remaining.clear();
        return outputs.toArray(new FluidStack[0]);
    }

    public void save(NBTTagCompound tag) {
        ProcessingPersistence.writeFluids(tag, "continuousFluidOutputs", remaining.toArray(new FluidStack[0]));
    }

    public void load(NBTTagCompound tag) {
        remaining.clear();
        for (FluidStack fluid : ProcessingPersistence.readFluids(tag, "continuousFluidOutputs")) {
            if (fluid != null && fluid.amount > 0) {
                remaining.add(fluid);
            }
        }
    }
}
