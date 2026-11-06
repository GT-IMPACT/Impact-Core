package com.impact.addon.gt.api.recipe;

import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_OutputBus;
import gregtech.api.logic.IProcessingFluidOutput;
import gregtech.api.logic.ProcessingOutputs;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity;

/** Supplies primitive hatch access to GT's shared capacity and delivery checks. */
public final class PrimitiveProcessingOutputs {

    private PrimitiveProcessingOutputs() {
    }

    public static ProcessingOutputs create(
            GT_MetaTileEntity_MultiBlockBase machine,
            List<GT_MetaTileEntity_Primitive_OutputBus> buses,
            List<GT_MetaTileEntity_Primitive_Hatch_Output> hatches
    ) {
        ProcessingOutputs outputs = new ProcessingOutputs(machine);
        for (GT_MetaTileEntity_Primitive_OutputBus bus : buses) {
            outputs.addInventory(bus, 0, bus.mInventory.length);
        }
        for (GT_MetaTileEntity_Primitive_Hatch_Output hatch : hatches) {
            if (!isValidMetaTileEntity(hatch)) {
                continue;
            }
            if (hatch.outputsItems()) {
                outputs.addInventory(hatch, 1, 1);
            }
            outputs.addFluidOutput(new IProcessingFluidOutput() {
                @Override
                public FluidStack getStoredFluid() {
                    return hatch.getFillableStack();
                }

                @Override
                public int getCapacity() {
                    return hatch.getCapacity();
                }

                @Override
                public boolean accepts(FluidStack fluid) {
                    if (!isValidMetaTileEntity(hatch)) {
                        return false;
                    }
                    if (GT_ModHandler.isSteam(fluid)) {
                        return hatch.outputsSteam();
                    }
                    return hatch.outputsLiquids() && (!hatch.isFluidLocked() || hatch.getLockedFluidName() == null
                            || hatch.getLockedFluidName().equals(fluid.getUnlocalizedName()));
                }

                @Override
                public boolean isRestricted() {
                    return hatch.mMode != 0;
                }

                @Override
                public int fill(FluidStack fluid, boolean execute) {
                    int accepted = hatch.fill(fluid, execute);
                    if (execute && accepted > 0) {
                        hatch.onEmptyingContainerWhenEmpty();
                        hatch.getBaseMetaTileEntity().markDirty();
                    }
                    return accepted;
                }
            });
        }
        return outputs;
    }
}
