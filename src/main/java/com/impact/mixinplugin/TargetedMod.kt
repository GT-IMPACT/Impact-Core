package com.impact.mixinplugin

import com.gtnewhorizon.gtnhmixins.builders.ITargetMod
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder

public enum class TargetedMod(private val targetBuilder: TargetModBuilder) : ITargetMod {

    GALACTICRAFT(
        TargetModBuilder()
            .setModId("GalacticraftCore"),
    ),
    GALAXY_SPACE(
        TargetModBuilder()
            .setModId("GalaxySpace"),
    ),
    GALACTICRAFT_PLANETS(
        TargetModBuilder()
            .setModId("GalacticraftMars"),
    );

    override fun getBuilder(): TargetModBuilder {
        return targetBuilder
    }
}
