package com.impact.util.multis

object ProgressiveOverclock {

    data class OverclockResult(
        val eut: Int,
        val duration: Int,
        val parallels: Int,
        val overclocks: Int,
        val realRecipeEUt: Int,
        val simulatedRecipeEUt: Long,
        val wasVoltageReduced: Boolean
    )

    private const val BASE_VOLTAGE = 8L
    private const val VOLTAGE_STEP = 4L

    private fun safeMul(a: Long, b: Long): Long {
        if (a == 0L || b == 0L) return 0L
        if (a > Long.MAX_VALUE / b) return Long.MAX_VALUE
        return a * b
    }

    private fun ceilDiv(a: Long, b: Long): Long {
        return (a + b - 1) / b
    }

    private fun tierForEUt(eut: Long): Int {
        var tier = 0
        var voltage = BASE_VOLTAGE

        while (eut > voltage && voltage <= Long.MAX_VALUE / VOLTAGE_STEP) {
            voltage *= VOLTAGE_STEP
            tier++
        }

        return tier
    }

    private fun voltageForTier(tier: Int): Long {
        var voltage = BASE_VOLTAGE

        repeat(tier) {
            if (voltage > Long.MAX_VALUE / VOLTAGE_STEP) {
                return Long.MAX_VALUE
            }
            voltage *= VOLTAGE_STEP
        }

        return voltage
    }

    fun simulatedRecipeEUt(recipeEUt: Long): Long {
        val tier = tierForEUt(recipeEUt)
        val tierVoltage = voltageForTier(tier)

        val halfAmp = tierVoltage / 2
        val minSimulated = halfAmp + 1

        return maxOf(recipeEUt, minSimulated)
    }

    fun calculateOverclock(
        recipeEUt: Int,
        recipeDuration: Int,
        parallels: Int,
        maxVoltage: Long,
        amperage: Int = 2,
    ): OverclockResult? {
        if (recipeEUt <= 0) return null
        if (recipeDuration <= 0) return null
        if (parallels <= 0) return null
        if (maxVoltage <= 0) return null
        if (amperage <= 0) return null

        val maxEUt = safeMul(maxVoltage, amperage.toLong())

        var realTotalEUt = safeMul(recipeEUt.toLong(), parallels.toLong())
        var simulatedTotalEUt = safeMul(
            simulatedRecipeEUt(recipeEUt.toLong()),
            parallels.toLong()
        )

        var duration = recipeDuration
        var overclocks = 0
        var wasVoltageReduced = false

        // Реальной энергии не хватает даже на параллели
        if (realTotalEUt > maxEUt) return null

        while (duration > 1) {
            // Проверяем OC по симулированному EU/t
            if (simulatedTotalEUt > maxEUt / 4) break

            // Но реальный EU/t тоже не должен переполнить Int
            if (realTotalEUt > Int.MAX_VALUE.toLong() / 4) break

            realTotalEUt *= 4
            simulatedTotalEUt *= 4

            duration = maxOf(1, duration / 2)
            overclocks++
        }

        // Если даже базовая параллельность не помещается в Int,
        // режем реальный EU/t и увеличиваем время.
        while (realTotalEUt > Int.MAX_VALUE.toLong()) {
            realTotalEUt = ceilDiv(realTotalEUt, 4)

            val newDuration = duration.toLong() * 2
            if (newDuration > Int.MAX_VALUE) return null

            duration = newDuration.toInt()
            overclocks--
            wasVoltageReduced = true
        }

        return OverclockResult(
            eut = realTotalEUt.toInt(),
            duration = duration,
            parallels = parallels,
            overclocks = overclocks,
            realRecipeEUt = recipeEUt,
            simulatedRecipeEUt = simulatedRecipeEUt(recipeEUt.toLong()),
            wasVoltageReduced = wasVoltageReduced,
        )
    }
}
