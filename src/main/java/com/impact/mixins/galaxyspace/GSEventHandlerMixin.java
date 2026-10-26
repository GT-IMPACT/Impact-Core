package com.impact.mixins.galaxyspace;

import com.impact.mixins.galacticraft.GalacticraftCoreAccessor;
import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "galaxyspace.core.events.GSEventHandler", remap = false)
public abstract class GSEventHandlerMixin {

    @Redirect(
            method = {"onEntityUpdate", "onInteract"},
            at = @At(
                    value = "FIELD",
                    target = "Lgalaxyspace/core/registers/fluids/GSFluids;HeliumHydrogen:Lnet/minecraftforge/fluids/Fluid;"),
            require = 2,
            expect = 2,
            allow = 2)
    private Fluid impact$keepLandingRocketFuel() {
        // IMPACT uses the same rocket fuel for every tier, including the reserve returned after landing.
        return GalacticraftCoreAccessor.impact$getFuelFluid();
    }
}
