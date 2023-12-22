package com.impact.debug.recipes

import gregtech.api.enums.GT_Values
import gregtech.api.enums.Materials

object DebugRecipes {
    fun init() {
        GT_Values.RA.addPrimitiveLineRecipe(
            arrayOf(Materials.Steel.getDust(2), Materials.Steel.getPlates(3)),
            Materials.Steel.getIngots(2),
            null, 20 * 20, 480
        )

        GT_Values.RA.addPrimitiveLineRecipe(
            arrayOf(Materials.Iron.getDust(2), Materials.Iron.getPlates(3)),
            Materials.Iron.getIngots(2),
            null, 20 * 20, 120
        )

        GT_Values.RA.addPrimitiveLineRecipe(
            arrayOf(Materials.Gold.getDust(2), Materials.Gold.getPlates(3)),
            Materials.Gold.getIngots(2),
            null, 20 * 50, 7680
        )
    }
}
