package com.impact.mixins.galacticraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = {
        "micdoodle8.mods.galacticraft.planets.asteroids.items.ItemCanisterLiquidOxygen2",
        "micdoodle8.mods.galacticraft.planets.asteroids.items.ItemCanisterLiquidOxygen3",
        "micdoodle8.mods.galacticraft.planets.asteroids.items.ItemCanisterOil2",
        "micdoodle8.mods.galacticraft.planets.asteroids.items.ItemCanisterOil3"
}, remap = false)
public abstract class TieredCanisterMixin {

    @Redirect(
            method = "fill",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;",
                    ordinal = 1,
                    remap = true),
            require = 1,
            allow = 1)
    private Item impact$keepCanisterTier(
            ItemStack candidate,
            ItemStack container,
            FluidStack resource,
            boolean doFill) {
        // GC searches its entire item registry by fluid, so T2 and T3 otherwise share the same candidates.
        // Returning null skips this candidate at the following instanceof check without changing the stack.
        return candidate.getMaxDamage() == container.getMaxDamage() ? candidate.getItem() : null;
    }
}
