package com.impact.mods.nei.impactplugin;

import com.github.vfyjxf.nee.NotEnoughEnergistics;

import static com.github.vfyjxf.nee.processor.RecipeProcessor.recipeProcessors;

public class RecipeProcessorLoader {

    public void init() {
        NotEnoughEnergistics.logger.info("Found Impact-core ,install Impact-core Support");
        recipeProcessors.add(new ImpactRecipeProcessor());
    }
}
