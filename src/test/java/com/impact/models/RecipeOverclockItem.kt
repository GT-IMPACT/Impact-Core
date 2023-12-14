package com.impact.models

import java.io.File

data class RecipeOverclockItem(
    val recipeDuration: Int,
    val recipeVoltage: Int,
    val hatchVoltage: Int,
    val hatchAmperes: Int,
    val parallel: Int,
    val resultVoltage: Int,
    val resultProgress: Int,
) {
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
                    parallel = data[4],
                    resultVoltage = data[5],
                    resultProgress = data[6],
                )
            }
            return list
        }
    }
}
