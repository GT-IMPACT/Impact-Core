package com.impact.util.multis

import com.impact.models.RecipeMachineModel
import com.impact.models.RecipeOverclockItem
import com.impact.models.RecipeOverclockItem.Companion.readParams
import com.impact.util.recipe.RecipeHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test

class OverclockCalculateTest {

    val model = RecipeMachineModel()

    @BeforeEach
    fun setUp() {
        model.maxProgressTime = 0
        model.eUt = 0
    }

    @RepeatedTest(value = 9)
    fun calculateOverclockedNessMultiNotParallel(repetitionInfo: RepetitionInfo) {

        val item = NOT_PARALEL[repetitionInfo.currentRepetition - 1]

        Assertions.assertNotNull(item)

        val eU = item.recipeVoltage
        val duration = item.recipeDuration
        val maxVoltage = item.hatchVoltage.toLong() * item.hatchAmperes

        OverclockCalculate.calculateOverclockedNessBasic(eU, duration, 1, maxVoltage, model)

        Assertions.assertEquals(model.eUt, item.resultVoltage)
        Assertions.assertEquals(model.maxProgressTime, item.resultProgress)
    }

    @Test
    fun calculateOverclockedNessMulti() {

        val eU = 7680
        val duration = 50
        val maxVoltage = 32768 * 2L

        OverclockCalculate.calculateOverclockedNessBasic(eU, duration, 1, maxVoltage, model)

        Assertions.assertEquals(model.eUt, 30720)
        Assertions.assertEquals(model.maxProgressTime, 24)
    }

    @RepeatedTest(value = 50)
    fun calculateOverclockedNessMultiarallel(repetitionInfo: RepetitionInfo) {

        val item = PARALLEL[repetitionInfo.currentRepetition - 1]

        Assertions.assertNotNull(item)

        val model2 = model.copy(currentParallel = item.currentParallel, maxParallel = item.maxParallel)

        val eU = item.recipeVoltage * item.currentParallel
        val duration = item.recipeDuration
        val maxVoltage = item.hatchVoltage.toLong() * item.hatchAmperes

        OverclockCalculate.calculateOverclockedNessBasic(eU, duration, 1, maxVoltage, model2)

        model2.maxProgressTime = RecipeHelper.calcTimeParallel(model2)

        Assertions.assertEquals(model2.eUt, item.resultVoltage)
        Assertions.assertEquals(model2.maxProgressTime, item.resultProgress)
    }

    companion object {
        private val NOT_PARALEL: List<RecipeOverclockItem> = readParams().filter { it.notParallelFilter() }
        private val PARALLEL: List<RecipeOverclockItem> = readParams()
    }
}
