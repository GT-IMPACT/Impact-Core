package com.impact.mixins.galacticraft;

import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

@Pseudo
@Mixin(targets = "micdoodle8.mods.galacticraft.core.GalacticraftCore", remap = false)
public interface GalacticraftCoreAccessor {

    @Accessor("fluidFuel")
    static Fluid impact$getFuelFluid() {
        throw new AssertionError("Mixin accessor was not applied");
    }
}
