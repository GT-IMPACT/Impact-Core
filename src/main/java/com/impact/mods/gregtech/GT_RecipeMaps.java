package com.impact.mods.gregtech;

import gregtech.api.util.GT_Recipe;

import java.util.HashSet;

import static gregtech.api.enums.GT_Values.E;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_RecipeMaps {

    public static final GT_Recipe.GT_Recipe_Map sDryingOven;
    public static final GT_Recipe.GT_Recipe_Map sMESystemProvider;
    public static final GT_Recipe.GT_Recipe_Map sMPContainer;
    public static final GT_Recipe.GT_Recipe_Map sTheMill;

    static {
        sDryingOven = new GT_Recipe.GT_Recipe_Map(new HashSet<>(1000),
                "gt.recipe.dryingoven", "Drying Oven", null, RES_PATH_GUI + "basicmachines/DryingOven",
                1, 2, 1, 0, 1, E, 1, E,
                true, true);
        sMESystemProvider = new GT_Recipe.GT_Recipe_Map(new HashSet<>(1000),
                "impact.recipe.mesystemprovider", "ME System Provider", null, RES_PATH_GUI + "basic/MEProvider.png",
                9, 1, 1, 0, 1, E, 1, E,
                true, false);
        sMPContainer = new GT_Recipe.GT_Recipe_Map(new HashSet<>(1000),
                "impact.recipe.matrixcontainer", "Matrix Pr. Containment", null, RES_PATH_GUI + "basic/MatrixContainment",
                1, 1, 1, 0, 1, E, 1, E,
                false, false);
        sTheMill = new GT_Recipe.GT_Recipe_Map(new HashSet<>(1000),
                "impact.recipe.the_mill", "The Mill", null, RES_PATH_GUI + "basic/Default",
                1, 4, 1, 0, 0, E, 1, E,
                false, true);
    }
}