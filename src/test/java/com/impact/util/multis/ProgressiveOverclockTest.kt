package com.impact.util.multis

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProgressiveOverclockTest {

    /**
     * Проверяет, что нулевой EU/t рецепта считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenRecipeEUtIsZero() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 0,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что отрицательный EU/t рецепта считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenRecipeEUtIsNegative() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = -1,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что нулевая длительность рецепта считается невалидной.
     */
    @Test
    fun shouldReturnNullWhenDurationIsZero() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 0,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что отрицательная длительность рецепта считается невалидной.
     */
    @Test
    fun shouldReturnNullWhenDurationIsNegative() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = -1,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что нулевое количество параллелей считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenParallelsIsZero() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = 0,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что отрицательное количество параллелей считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenParallelsIsNegative() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = -1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что нулевое напряжение считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenMaxVoltageIsZero() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = 0L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что отрицательное напряжение считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenMaxVoltageIsNegative() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = -2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что нулевой ампераж считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenAmperageIsZero() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 0
        )

        assertNull(result)
    }

    /**
     * Проверяет, что отрицательный ампераж считается невалидным.
     */
    @Test
    fun shouldReturnNullWhenAmperageIsNegative() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 100,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = -1
        )

        assertNull(result)
    }

    /**
     * Проверяет, что рецепт не запускается, если реальной энергии не хватает даже на параллельность.
     */
    @Test
    fun shouldReturnNullWhenNotEnoughEnergyForBaseParallels() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 3000,
            recipeDuration = 100,
            parallels = 2,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNull(result)
    }

    /**
     * Проверяет, что рецепт без оверклока возвращает базовые значения.
     */
    @Test
    fun shouldReturnBaseValuesWhenNoOverclockIsPossible() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2048,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(2048, result!!.eut)
        assertEquals(400, result.duration)
        assertEquals(1, result.parallels)
        assertEquals(0, result.overclocks)
        assertEquals(2048, result.realRecipeEUt)
        assertEquals(2048L, result.simulatedRecipeEUt)
        assertFalse(result.wasVoltageReduced)
    }

    /**
     * Проверяет, что рецепт 960 EU/t симулируется как 1025 EU/t.
     */
    @Test
    fun shouldSimulate960EUtAs1025EUt() {
        val simulated = ProgressiveOverclock.simulatedRecipeEUt(960L)

        assertEquals(1025L, simulated)
    }

    /**
     * Проверяет, что рецепт ровно в половину тира поднимается выше половины тира.
     */
    @Test
    fun shouldSimulateHalfTierRecipeAsAboveHalfTier() {
        val simulated = ProgressiveOverclock.simulatedRecipeEUt(1024L)

        assertEquals(1025L, simulated)
    }

    /**
     * Проверяет, что рецепт выше половины тира не изменяется симуляцией.
     */
    @Test
    fun shouldNotChangeRecipeAboveHalfTier() {
        val simulated = ProgressiveOverclock.simulatedRecipeEUt(1025L)

        assertEquals(1025L, simulated)
    }

    /**
     * Проверяет, что рецепт ровно на границе тира не изменяется симуляцией.
     */
    @Test
    fun shouldNotChangeRecipeAtTierVoltage() {
        val simulated = ProgressiveOverclock.simulatedRecipeEUt(2048L)

        assertEquals(2048L, simulated)
    }

    /**
     * Проверяет, что дешёвый EV-рецепт не получает лишний оверклок из-за реального EU/t.
     */
    @Test
    fun shouldPreventExtraOverclockForBrokenLowEUtRecipe() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 960,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(960, result!!.eut)
        assertEquals(400, result.duration)
        assertEquals(0, result.overclocks)
        assertEquals(1025L, result.simulatedRecipeEUt)
    }

    /**
     * Проверяет, что рецепт выше половины тира не оверклокается, если для него не хватает мощности.
     */
    @Test
    fun shouldNotOverclockRecipeAboveHalfTierWhenEnergyIsNotEnough() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 1025,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(1025, result!!.eut)
        assertEquals(400, result.duration)
        assertEquals(0, result.overclocks)
        assertEquals(1025L, result.simulatedRecipeEUt)
    }

    /**
     * Проверяет, что рецепт выше половины тира оверклокается, если хватает мощности.
     */
    @Test
    fun shouldOverclockRecipeAboveHalfTierWhenEnergyIsEnough() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 1025,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 8192L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(4100, result!!.eut)
        assertEquals(200, result.duration)
        assertEquals(1, result.overclocks)
        assertEquals(1025L, result.simulatedRecipeEUt)
    }

    /**
     * Проверяет, что несколько оверклоков применяются последовательно
     * и ограничиваются симулированным EU/t.
     */
    @Test
    fun shouldApplyMultipleOverclocks() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 33,
            recipeDuration = 800,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(528, result!!.eut)
        assertEquals(200, result.duration)
        assertEquals(2, result.overclocks)
        assertEquals(65L, result.simulatedRecipeEUt)
    }

    /**
     * Проверяет, что длительность не становится меньше одного тика.
     */
    @Test
    fun shouldClampDurationToOneTick() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 1,
            recipeDuration = 1,
            parallels = 1,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(1, result!!.duration)
        assertEquals(0, result.overclocks)
    }

    /**
     * Проверяет, что оверклок останавливается при достижении одного тика.
     */
    @Test
    fun shouldStopOverclockWhenDurationReachesOneTick() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 1,
            recipeDuration = 2,
            parallels = 1,
            maxVoltage = 2_147_483_648L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(1, result!!.duration)
        assertEquals(1, result.overclocks)
    }

    /**
     * Проверяет, что параллельность учитывается в реальном EU/t.
     */
    @Test
    fun shouldMultiplyEUtByParallels() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 400,
            parallels = 4,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(1600, result!!.eut)
        assertEquals(200, result.duration)
        assertEquals(1, result.overclocks)
        assertEquals(4, result.parallels)
    }

    /**
     * Проверяет, что оверклок не делается, если следующая ступень не помещается по симулированному EU/t.
     */
    @Test
    fun shouldNotOverclockWhenSimulatedEUtDoesNotFit() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 960,
            recipeDuration = 400,
            parallels = 4,
            maxVoltage = 2048L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(3840, result!!.eut)
        assertEquals(400, result.duration)
        assertEquals(0, result.overclocks)
        assertEquals(1025L, result.simulatedRecipeEUt)
    }

    /**
     * Проверяет, что большой ампераж позволяет сделать несколько оверклоков,
     * но проверка всё равно идёт по симулированному EU/t.
     */
    @Test
    fun shouldOverclockWithHighAmperageBudget() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 960,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 8192L,
            amperage = 64
        )

        assertNotNull(result)
        assertEquals(245760, result!!.eut)
        assertEquals(25, result.duration)
        assertEquals(4, result.overclocks)
        assertEquals(960, result.realRecipeEUt)
        assertEquals(1025L, result.simulatedRecipeEUt)
        assertFalse(result.wasVoltageReduced)
    }

    /**
     * Проверяет, что реальный EU/t не переполняет Int при оверклоке.
     */
    @Test
    fun shouldNotOverclockWhenNextRealEUtWouldOverflowInt() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 600_000_000,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = 2_147_483_648L,
            amperage = 2
        )

        assertNotNull(result)
        assertEquals(600_000_000, result!!.eut)
        assertEquals(400, result.duration)
        assertEquals(0, result.overclocks)
    }

    /**
     * Проверяет, что базовый EU/t выше Int.MAX_VALUE режется через увеличение времени.
     */
    @Test
    fun shouldReduceVoltageWhenBaseParallelEUtExceedsIntMax() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2_000_000_000,
            recipeDuration = 100,
            parallels = 2,
            maxVoltage = Long.MAX_VALUE,
            amperage = Int.MAX_VALUE
        )

        assertNotNull(result)
        assertEquals(1_000_000_000, result!!.eut)
        assertEquals(200, result.duration)
        assertEquals(-1, result.overclocks)
        assertTrue(result.wasVoltageReduced)
    }

    /**
     * Проверяет, что округление вверх при снижении EU/t работает корректно.
     */
    @Test
    fun shouldUseCeilDivisionWhenReducingVoltage() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2_147_483_647,
            recipeDuration = 100,
            parallels = 2,
            maxVoltage = Long.MAX_VALUE,
            amperage = Int.MAX_VALUE
        )

        assertNotNull(result)
        assertEquals(1_073_741_824, result!!.eut)
        assertEquals(200, result.duration)
        assertEquals(-1, result.overclocks)
        assertTrue(result.wasVoltageReduced)
    }

    /**
     * Проверяет, что при невозможности безопасно увеличить время возвращается null.
     */
    @Test
    fun shouldReturnNullWhenReducedVoltageWouldOverflowDuration() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2_000_000_000,
            recipeDuration = Int.MAX_VALUE,
            parallels = 2,
            maxVoltage = Long.MAX_VALUE,
            amperage = Int.MAX_VALUE
        )

        assertNull(result)
    }

    /**
     * Проверяет, что maxEUt безопасно насыщается при переполнении Long.
     */
    @Test
    fun shouldHandleMaxEUtLongOverflowBySaturation() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 100,
            recipeDuration = 400,
            parallels = 1,
            maxVoltage = Long.MAX_VALUE,
            amperage = Int.MAX_VALUE
        )

        assertNotNull(result)
        assertTrue(result!!.eut > 0)
    }

    /**
     * Проверяет UIV-напряжение 33m.
     */
    @Test
    fun shouldWorkWithUIVVoltage() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2048,
            recipeDuration = 1024,
            parallels = 1,
            maxVoltage = 33_554_432L,
            amperage = 2
        )

        assertNotNull(result)
        assertTrue(result!!.overclocks > 0)
        assertTrue(result.eut <= Int.MAX_VALUE)
    }

    /**
     * Проверяет UMV-напряжение 134m.
     */
    @Test
    fun shouldWorkWithUMVVoltage() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2048,
            recipeDuration = 1024,
            parallels = 1,
            maxVoltage = 134_217_728L,
            amperage = 2
        )

        assertNotNull(result)
        assertTrue(result!!.overclocks > 0)
        assertTrue(result.eut <= Int.MAX_VALUE)
    }

    /**
     * Проверяет UXV-напряжение 536m.
     */
    @Test
    fun shouldWorkWithUXVVoltage() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2048,
            recipeDuration = 1024,
            parallels = 1,
            maxVoltage = 536_870_912L,
            amperage = 2
        )

        assertNotNull(result)
        assertTrue(result!!.overclocks > 0)
        assertTrue(result.eut <= Int.MAX_VALUE)
    }

    /**
     * Проверяет MAX-напряжение выше Int.MAX_VALUE.
     */
    @Test
    fun shouldWorkWithMaxVoltageAboveIntMax() {
        val result = ProgressiveOverclock.calculateOverclock(
            recipeEUt = 2048,
            recipeDuration = 1024,
            parallels = 1,
            maxVoltage = 2_147_483_648L,
            amperage = 2
        )

        assertNotNull(result)
        assertTrue(result!!.overclocks > 0)
        assertTrue(result.eut <= Int.MAX_VALUE)
    }
}
