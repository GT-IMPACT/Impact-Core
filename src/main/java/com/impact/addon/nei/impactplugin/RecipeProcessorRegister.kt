package com.impact.addon.nei.impactplugin

import com.github.vfyjxf.nee.NotEnoughEnergistics
import com.github.vfyjxf.nee.processor.RecipeProcessor
import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler
import cpw.mods.fml.common.Loader
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import gregtech.api.util.GT_Recipe
import gregtech.nei.GT_NEI_3DPrinter
import gregtech.nei.GT_NEI_SawMill

object RecipeProcessorRegister {

    @JvmStatic
    @SideOnly(Side.CLIENT)
    fun register() {
        if (Loader.isModLoaded("neenergistics")) {
            try {
                NotEnoughEnergistics.logger.info("Found Impact-core, install Impact-core Support")
                // NEE uses the first processor that supplies inputs and outputs.
                RecipeProcessor.recipeProcessors.add(0, AssemblyLineRecipeProcessor())

                RecipeProcessorBase
                    .create(GTMTE_RailAssembler.sTrackAssemblerRecipes.mNEIName)
                    .hasValidClass(NEI_Impact_RailAssembler::class.java)

                RecipeProcessorBase
                    .create(GT_Recipe.GT_Recipe_Map.sSawMillVisual.mNEIName)
                    .hasValidClass(GT_NEI_SawMill::class.java)

                RecipeProcessorBase
                    .create(GT_RecipeMaps.sPyrolyseOven.mNEIName)
                    .hasValidClass(GT_NEI_Pyro::class.java)

                RecipeProcessorBase
                    .create(GT_RecipeMaps.sMESystemProvider.mNEIName)
                    .hasValidClass(NEI_Impact_MEProvider::class.java)

                RecipeProcessorBase
                    .create(GT_Recipe.GT_Recipe_Map.sCyclonRecipes.mNEIName)
                    .hasValidClass(GT_NEI_HeavyMetalCyclone::class.java)

                RecipeProcessorBase
                    .create(GT_Recipe.GT_Recipe_Map.sPrimitiveLine.mNEIName, GT_Recipe.GT_Recipe_Map.sBasicline.mNEIName)
                    .hasValidClass(GT_NEI_3DPrinter::class.java)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
