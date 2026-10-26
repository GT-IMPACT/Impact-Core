package com.impact.mixins.galacticraft;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = {
        "micdoodle8.mods.galacticraft.core.entities.EntityLanderBase",
        "micdoodle8.mods.galacticraft.core.tile.TileEntityParaChest"
}, remap = false)
public abstract class LandingFuelMixin {

    @Inject(method = "checkFluidTankTransfer", at = @At("HEAD"))
    private void impact$restoreRocketFuel(int slot, FluidTank tank, CallbackInfo callback) {
        FluidStack fluid = tank.getFluid();
        if (fluid == null || !"heliumhydrogen".equals(fluid.getFluid().getName())) {
            return;
        }

        // Repair reserves already converted by GalaxySpace, including saved landing vehicles and parachests.
        // GC still handles canister capacity and drains the actual transferred amount.
        Fluid fuel = GalacticraftCoreAccessor.impact$getFuelFluid();
        tank.setFluid(new FluidStack(fuel, fluid.amount, fluid.tag));
    }
}
