package com.impact.addon.gt.api.recipe;

import gregtech.api.logic.IProcessingOutputSink;
import gregtech.api.logic.ProcessingPersistence;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/** Pyrolysis releases its first four fractions at seven-second intervals. */
public final class StagedFluidOutputs {

    private static final int INTERVAL = 140;
    private FluidStack[] fractions = new FluidStack[0];
    private int releasedStages;
    private boolean protectedOutput;

    public void start(FluidStack[] outputs, boolean protect) {
        fractions = new FluidStack[Math.min(4, outputs.length)];
        for (int index = 0; index < fractions.length; index++) {
            fractions[index] = outputs[index] == null ? null : outputs[index].copy();
        }
        releasedStages = 0;
        protectedOutput = protect;
    }

    public void tick(int progress, IProcessingOutputSink output) {
        releasedStages = Math.min(fractions.length, Math.max(releasedStages, progress / INTERVAL));
        for (int index = 0; index < releasedStages; index++) {
            FluidStack fraction = fractions[index];
            if (fraction != null && fraction.amount > 0) {
                output.deliver(null, new FluidStack[] { fraction }, false, protectedOutput);
            }
        }
    }

    public FluidStack[] finish(FluidStack[] finalOutputs) {
        List<FluidStack> remaining = new ArrayList<>();
        for (int index = 0; index < releasedStages; index++) {
            FluidStack fraction = fractions[index];
            if (fraction != null && fraction.amount > 0) {
                remaining.add(fraction.copy());
            }
        }
        if (finalOutputs != null) {
            for (FluidStack fluid : finalOutputs) {
                if (fluid != null && fluid.amount > 0) {
                    remaining.add(fluid.copy());
                }
            }
        }
        clear();
        return remaining.toArray(new FluidStack[0]);
    }

    public void clear() {
        fractions = new FluidStack[0];
        releasedStages = 0;
        protectedOutput = false;
    }

    public void save(NBTTagCompound tag) {
        NBTTagCompound stages = new NBTTagCompound();
        ProcessingPersistence.writeFluids(stages, "fractions", fractions);
        stages.setInteger("released", releasedStages);
        stages.setBoolean("protected", protectedOutput);
        tag.setTag("pyrolysisStages", stages);
    }

    public void load(NBTTagCompound tag) {
        NBTTagCompound stages = tag.getCompoundTag("pyrolysisStages");
        fractions = ProcessingPersistence.readFluids(stages, "fractions");
        releasedStages = Math.max(0, Math.min(fractions.length, stages.getInteger("released")));
        protectedOutput = stages.getBoolean("protected");
    }
}
