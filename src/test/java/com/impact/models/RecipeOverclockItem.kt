package com.impact.models

import java.io.File

data class RecipeOverclockItem(
    val recipeDuration: Int,
    val recipeVoltage: Int,
    val hatchVoltage: Int,
    val hatchAmperes: Int,
    val maxParallel: Int,
    val currentParallel: Int,
    val resultVoltage: Int,
    val resultProgress: Int,
) {

    fun notParallelFilter(): Boolean {
        return currentParallel == 1
    }

    companion object {
        fun readParams(): List<RecipeOverclockItem> {
            val list = mutableListOf<RecipeOverclockItem>()
            val file = File("src/test/resources/overclock.csv")

            file.readLines().forEach { line ->

                val data = line
                    .split(",")
                    .map { it.toInt() }

                list += RecipeOverclockItem(
                    recipeDuration = data[0],
                    recipeVoltage = data[1],
                    hatchVoltage = data[2],
                    hatchAmperes = data[3],
                    maxParallel = data[4],
                    currentParallel = data[5],
                    resultVoltage = data[6],
                    resultProgress = data[8],
                )
            }
            return list
        }
    }
}
