package com.impact.mixinplugin

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader
import com.gtnewhorizon.gtnhmixins.LateMixin
import com.gtnewhorizon.gtnhmixins.builders.IMixins

@LateMixin
public class ImpactLateMixins : ILateMixinLoader {

    override fun getMixinConfig(): String {
        return "mixins.impact.late.json"
    }

    override fun getMixins(loadedMods: Set<String>): List<String> {
        return IMixins.getLateMixins(
            Mixins::class.java,
            loadedMods,
        )
    }
}
