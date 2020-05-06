package com.impact.recipes;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

import static gregtech.api.util.GT_ModHandler.*;

public class AfterGregTechPostLoadRecipes implements Runnable {
    @Override
    public void run() {

        //removeRecipe(ItemStack aInput);

        //removeFurnaceSmelting(ItemStack aInput)

        //removeRecipeByOutput();

        removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CokeCoal, 1), true, false, false);
    }
}
