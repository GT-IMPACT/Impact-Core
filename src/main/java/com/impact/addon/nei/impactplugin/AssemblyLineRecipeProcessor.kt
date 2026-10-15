package com.impact.addon.nei.impactplugin

import codechicken.nei.PositionedStack
import codechicken.nei.recipe.IRecipeHandler
import com.github.vfyjxf.nee.config.NEEConfig
import com.github.vfyjxf.nee.processor.IRecipeProcessor
import gregtech.api.util.GT_Recipe
import gregtech.nei.GT_NEI_AssLineHandler

class AssemblyLineRecipeProcessor : IRecipeProcessor {
    override fun getRecipeProcessorId(): String = "GregTech5"

    override fun getAllOverlayIdentifier(): Set<String> =
        setOf(GT_Recipe.GT_Recipe_Map.sAssemblylineVisualRecipes.mNEIName)

    override fun getRecipeInput(recipe: IRecipeHandler, recipeIndex: Int, identifier: String): List<PositionedStack> {
        if (recipe !is GT_NEI_AssLineHandler) return emptyList()
        // The handler displays mSpecialItems (the data stick) in this dedicated slot.
        // Keep data sticks in regular ingredient slots, as well as fluid display stacks.
        return recipe.getIngredientStacks(recipeIndex).filterNot {
            (it.relx == 138 && it.rely == 36) ||
                (it.item.stackSize == 0 && !NEEConfig.includeNonConsumableIngredients)
        }
    }

    override fun getRecipeOutput(recipe: IRecipeHandler, recipeIndex: Int, identifier: String): List<PositionedStack> =
        if (recipe is GT_NEI_AssLineHandler) recipe.getOtherStacks(recipeIndex).toList() else emptyList()

    override fun mergeStacks(recipe: IRecipeHandler, recipeIndex: Int, identifier: String): Boolean = false
}
