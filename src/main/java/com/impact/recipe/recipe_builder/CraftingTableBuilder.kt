package com.impact.recipe.recipe_builder

import com.impact.util.files.JsonUtils.recipeDefaultGson
import gregtech.api.util.GT_ModHandler
import net.minecraft.item.ItemStack

class CraftingTableBuilder {

    companion object {
        private const val INDEX_1 = 'A'
        private const val INDEX_2 = 'B'
        private const val INDEX_3 = 'C'
        private const val INDEX_4 = 'D'
        private const val INDEX_5 = 'E'
        private const val INDEX_6 = 'F'
        private const val INDEX_7 = 'G'
        private const val INDEX_8 = 'H'
        private const val EMPTY = ' '
    }

    private val gson = recipeDefaultGson

    private val chars = ArrayList<Char>(9)
    private val items = ArrayList<ItemStack?>(9)
    private val result = ArrayList<ItemStack?>(1)
    private val recipeInput = ArrayList<Any>()

    init {
        repeat(9) {
            chars += EMPTY
            items += null
        }
        result += null
    }

    private fun charOfIndex(index: Int = -1): Char {
        return when (index) {
            1 -> INDEX_1
            2 -> INDEX_2
            3 -> INDEX_3
            4 -> INDEX_4
            5 -> INDEX_5
            6 -> INDEX_6
            7 -> INDEX_7
            8 -> INDEX_8
            else -> EMPTY
        }
    }

    fun addItem(stack: ItemStack, index: Int): CraftingTableBuilder {
        if (index in 0..8) {
            items[index] = stack
            chars[index] = charOfIndex(index)
        }
        return this
    }

    fun addItems(stacks: Iterable<ItemStack?>): CraftingTableBuilder {
        stacks.forEachIndexed { index, stack ->
            stack?.also { addItem(stack, index) }
        }
        return this
    }

    fun addResult(stack: ItemStack?): CraftingTableBuilder {
        result[0] = stack
        return this
    }

    fun createRecipe() {
        chars.chunked(3).forEach {
            recipeInput += it.joinToString("")
        }
        items.forEachIndexed { index, stack ->
            stack?.also {
                recipeInput += chars[index]
                recipeInput += stack
            }
        }
        val inputData = Array(recipeInput.size) { recipeInput[it] }
        result[0]?.also {
            GT_ModHandler.addCraftingRecipe(it, inputData)
        }
    }

    fun saveRecipe() {
        createRecipe()
        //TODO save
    }
}
