package com.impact.addon.gt.api.recipe;

import gregtech.api.logic.IProcessingOutputSink;
import gregtech.api.logic.ProcessingOutputs;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output_ME;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public final class StagedFluidOutputSink implements IProcessingOutputSink {

    private final ProcessingOutputs outputs;

    private StagedFluidOutputSink(ProcessingOutputs outputs) {
        this.outputs = outputs;
    }

    public static IProcessingOutputSink create(GT_MetaTileEntity_MultiBlockBase machine) {
        ProcessingOutputs outputs = new ProcessingOutputs(machine);
        if (machine.mOutputHatches.size() == 1) {
            GT_MetaTileEntity_Hatch_Output hatch = machine.mOutputHatches.get(0);
            if (!(hatch instanceof GT_MetaTileEntity_Hatch_Output_ME) && !hatch.isFluidLocked()) {
                return new StagedFluidOutputSink(outputs);
            }
        }
        return outputs;
    }

    @Override
    public CheckRecipeResult canFit(ItemStack[] items, FluidStack[] fluids, boolean protectItems, boolean protectFluids) {
        CheckRecipeResult result = outputs.canFit(items, null, protectItems, false);
        if (!result.wasSuccessful() || !protectFluids || fluids == null) {
            return result;
        }

        // The same tank can be drained between fractions. Each one still has to fit the hatch.
        FluidStack[] fraction = new FluidStack[1];
        for (FluidStack fluid : fluids) {
            if (fluid == null || fluid.amount <= 0) {
                continue;
            }
            fraction[0] = fluid;
            result = outputs.canFit(null, fraction, false, true);
            if (!result.wasSuccessful()) {
                return result;
            }
        }
        return result;
    }

    @Override
    public boolean areFluidOutputsEmpty() {
        return outputs.areFluidOutputsEmpty();
    }

    @Override
    public boolean deliver(ItemStack[] items, FluidStack[] fluids, boolean protectItems, boolean protectFluids) {
        return outputs.deliver(items, fluids, protectItems, protectFluids);
    }
}
