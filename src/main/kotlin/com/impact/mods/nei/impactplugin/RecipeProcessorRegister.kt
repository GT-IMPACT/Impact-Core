package com.impact.mods.nei.impactplugin

import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler
import gregtech.api.util.GT_Recipe

object RecipeProcessorRegister {

    fun register() {
        try {
            RecipeProcessorBase.create(GTMTE_RailAssembler.sTrackAssemblerRecipes.mNEIName)
                .hasValidClass("com.impact.mods.nei.impactplugin.NEI_Impact_RailAssembler")
            RecipeProcessorBase.create(GT_Recipe.GT_Recipe_Map.sSawMillVisual.mNEIName)
                .hasValidClass("gregtech.nei.GT_NEI_SawMill")
            RecipeProcessorBase.create(GT_Recipe.GT_Recipe_Map.sPyrolyseBasicVisual.mNEIName)
                .hasValidClass("gregtech.nei.GT_NEI_Pyro")
            RecipeProcessorBase.create(GT_RecipeMaps.sMESystemProvider.mNEIName)
                .hasValidClass("com.impact.mods.nei.impactplugin.NEI_Impact_MEProvider")
            RecipeProcessorBase.create(GT_Recipe.GT_Recipe_Map.sCyclonRecipes.mNEIName)
                .hasValidClass("com.impact.mods.nei.impactplugin.GT_NEI_HeavyMetalCyclone")
            RecipeProcessorBase.create(GT_Recipe.GT_Recipe_Map.sPrimitiveLine.mNEIName,GT_Recipe.GT_Recipe_Map.sBasicline.mNEIName)
                .hasValidClass("gregtech.nei.GT_NEI_3DPrinter")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}