package com.impact.addon.gt.recipes

import com.impact.impact
import gregtech.api.enums.Materials
import gregtech.api.enums.OrePrefixes
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_OreDictUnificator

fun addPyrolyseRecipes() {

    impact.I_RA.addPyrolyseRecipes(
        GT_ModHandler.getModItem("impact", "impact_item", 42, 39),
        arrayOf(
            Materials.CarbonMonoxide.getGas(72),
            Materials.Hydrogen.getGas(288),
            Materials.Methane.getGas(144),
            Materials.CarbonDioxide.getGas(216),
            Materials.WoodTar.getFluid(1440),
        ),
        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Charcoal, 5)
    )

    impact.I_RA.addPyrolyseRecipes(
        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 16),
        arrayOf(
            Materials.Nitrogen.getGas(72),
            Materials.Hydrogen.getGas(288),
            Materials.Methane.getGas(144),
            Materials.CarbonDioxide.getGas(216),
            Materials.SulfuricTar.getFluid(1440),
        ),
        GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CokeCoal, 20)
    )
}
