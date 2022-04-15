package com.impact.mods.nei.impactplugin

import codechicken.nei.PositionedStack
import codechicken.nei.recipe.IRecipeHandler
import com.github.vfyjxf.nee.processor.GregTech5RecipeProcessor.getFluidFromDisplayStack
import com.github.vfyjxf.nee.processor.IRecipeProcessor
import com.github.vfyjxf.nee.processor.RecipeProcessor
import com.impact.mods.nei.impactplugin.util.FixedPositionedStack
import gregtech.api.util.GT_Utility

class RecipeProcessorBase(
    private val idOverlays: Set<String>,
) : IRecipeProcessor {

    companion object {
        fun create(vararg ids: String): RecipeProcessorBase = RecipeProcessorBase(ids.toSet())
    }

    private var validClass: Class<*>? = null

    fun hasValidClass(clazz: String): RecipeProcessorBase {
        validClass = Class.forName(clazz)
        RecipeProcessor.recipeProcessors.add(this)
        return this
    }

    override fun getRecipeProcessorId(): String = "ImpactProcessor"

    override fun getAllOverlayIdentifier(): Set<String> {
        return idOverlays
    }

    override fun getRecipeInput(recipe: IRecipeHandler, recipeIndex: Int, identifier: String): List<PositionedStack> {
        val recipeInputs = mutableListOf<PositionedStack>()
        validClass?.let { clazz ->
            if (clazz.isInstance(recipe)) {
                recipeInputs += recipe.getIngredientStacks(recipeIndex)
                recipeInputs.removeIf { GT_Utility.getFluidFromDisplayStack(it.items[0]) != null || it.item.stackSize == 0 }
            }
        }
        return recipeInputs
    }

    override fun getRecipeOutput(recipe: IRecipeHandler, recipeIndex: Int, identifier: String): List<PositionedStack> {
        val recipeOutputs = mutableListOf<PositionedStack>()
        validClass?.let { clazz ->
            if (clazz.isInstance(recipe)) {
                recipeOutputs += recipe.getOtherStacks(recipeIndex)
                recipeOutputs.removeIf { getFluidFromDisplayStack(it.items[0]) != null }
                recipeOutputs.removeIf { it is FixedPositionedStack && it.mChance != 10000 && it.mChance > 0 }
            }
        }
        return recipeOutputs
    }
}