package com.impact.mixinplugin

import com.gtnewhorizon.gtnhmixins.builders.IBaseTransformer.Phase
import com.gtnewhorizon.gtnhmixins.builders.IMixins
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder

public enum class Mixins(private val mixinBuilder: MixinBuilder) : IMixins {

    LANDING_ROCKET_FUEL(
        MixinBuilder("Preserve rocket fuel after landing")
            .setPhase(Phase.LATE)
            .addRequiredMod(TargetedMod.GALACTICRAFT)
            .addRequiredMod(TargetedMod.GALAXY_SPACE)
            .addCommonMixins(
                "galacticraft.GalacticraftCoreAccessor",
                "galacticraft.LandingFuelMixin",
                "galaxyspace.GSEventHandlerMixin",
            )
            .setApplyIf {
                true
            },
    ),
    TIERED_CANISTER_CAPACITY(
        MixinBuilder("Preserve T2 and T3 canister capacity when changing fluids")
            .setPhase(Phase.LATE)
            .addRequiredMod(TargetedMod.GALACTICRAFT)
            .addRequiredMod(TargetedMod.GALACTICRAFT_PLANETS)
            .addCommonMixins("galacticraft.TieredCanisterMixin")
            .setApplyIf {
                true
            },
    );

    override fun getBuilder(): MixinBuilder {
        return mixinBuilder
    }
}
