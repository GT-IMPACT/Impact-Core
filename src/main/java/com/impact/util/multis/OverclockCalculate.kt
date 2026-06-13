package com.impact.util.multis

import com.impact.addon.gt.api.multis.IMachineRecipe
import com.impact.core.Config
import gregtech.api.enums.GT_Values
import gregtech.api.util.GT_Utility
import javax.annotation.Nonnegative
import kotlin.math.max

object OverclockCalculate {

    data class ResultCalculate(
        val eut: Int,
        val time: Int,
    )

    fun calculateOverclockedNessBasicResult(
        @Nonnegative aEUt: Int,
        @Nonnegative aDuration: Int,
        @Nonnegative mAmperage: Int,
        @Nonnegative maxInputVoltage: Long,
    ): ResultCalculate {

        var maxProgressTime: Int
        var eUt: Int

        val mTier = max(0.0, GT_Utility.getTier(maxInputVoltage).toDouble()).toInt()
        if (mTier == 0) {

            val xMaxProgresstime = aDuration.toLong() shl 1

            if (xMaxProgresstime > Int.MAX_VALUE - 1) {
                eUt = Int.MAX_VALUE - 1
                maxProgressTime = Int.MAX_VALUE - 1
            } else {
                eUt = aEUt shr 2
                maxProgressTime = xMaxProgresstime.toInt()
            }
        } else {
            var xEUt = aEUt.toLong()

            var tempEUt = max(xEUt, GT_Values.V[1])
            maxProgressTime = aDuration

            while (tempEUt <= GT_Values.V[mTier - 1] * mAmperage) {
                tempEUt = tempEUt shl 2
                maxProgressTime = maxProgressTime shr 1
                xEUt = if (maxProgressTime <= 0) xEUt shr 1 else xEUt shl 2
            }

            while (xEUt > maxInputVoltage) {
                xEUt = xEUt shr 2
                maxProgressTime = maxProgressTime shl 1
            }

            if (xEUt > Int.MAX_VALUE - 1) {
                eUt = Int.MAX_VALUE - 1
                maxProgressTime = Int.MAX_VALUE - 1
            } else {
                eUt = xEUt.toInt()
                if (eUt == 0) eUt = 1
                if (maxProgressTime <= 0) maxProgressTime = Config.MAX_TICK_RATE
            }
        }

        return ResultCalculate(
            eut = eUt,
            time = maxProgressTime,
        )
    }

    @JvmStatic
    fun calculateOverclockedNessBasic(
        @Nonnegative aEUt: Int,
        @Nonnegative aDuration: Int,
        @Nonnegative mAmperage: Int,
        @Nonnegative maxInputVoltage: Long,
        base: IMachineRecipe,
    ) {
        val result = calculateOverclockedNessBasicResult(
            aEUt = aEUt,
            aDuration = aDuration,
            mAmperage = mAmperage,
            maxInputVoltage = maxInputVoltage,
        )

        base.eUt = result.eut
        base.maxProgressTime = result.time
    }
}