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
    );

    override fun getBuilder(): MixinBuilder {
        return mixinBuilder
    }
}
