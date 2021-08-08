package com.impact.mods.gregtech;

import gregtech.api.util.GT_Recipe;

import java.util.HashSet;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_RecipeMaps {

    public static final GT_Recipe.GT_Recipe_Map sDryingOven;

    static {
        sDryingOven = new GT_Recipe.GT_Recipe_Map(new HashSet<>(1000),
                "gt.recipe.dryingoven", "Drying Oven", null, RES_PATH_GUI + "basicmachines/DryingOven",
                1, 2, 1, 0, 1, E, 1, E,
                true, true);

    }
}
