package com.impact.mods.nei.impactplugin;

import com.github.vfyjxf.nee.NotEnoughEnergistics;
import cpw.mods.fml.common.Loader;

import static com.github.vfyjxf.nee.processor.RecipeProcessor.recipeProcessors;

public class RecipeProcessorLoader {

    public static void init() {
        if (Loader.isModLoaded("neenergistics")) {
            NotEnoughEnergistics.logger.info("Found Impact-core, install Impact-core Support");
            recipeProcessors.add(new ImpactRecipeProcessor());
        }
    }
}