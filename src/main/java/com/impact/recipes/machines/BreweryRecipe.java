package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class BreweryRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addBrewingRecipeCustom(CoreItems2.getRecipe(152, 1), Materials.GrowthMediumRaw.getFluid(2000), Materials.Bacteria.getFluid(2000), 600, 1920, false);

        GT_Values.RA.addBrewingRecipeCustom(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.AlienOrganic, 1), Materials.Bacteria.getFluid(1000), Materials.AlienBiomass.getFluid(1000), 200, 122880, false);

    }
}
