package com.impact.util.multis

import com.impact.addon.gt.api.multis.IMachineRecipe
import com.impact.core.Config
import gregtech.api.enums.GT_Values
import gregtech.api.util.GT_Utility
import javax.annotation.Nonnegative
import kotlin.math.max

object OverclockCalculate {

    @JvmStatic
    fun calculateOverclockedNessBasic(
        @Nonnegative aEUt: Int,
        @Nonnegative aDuration: Int,
        @Nonnegative mAmperage: Int,
        @Nonnegative maxInputVoltage: Long,
        base: IMachineRecipe,
    ) {
        val mTier = max(0.0, GT_Utility.getTier(maxInputVoltage).toDouble()).toInt()
        if (mTier == 0) {

            val xMaxProgresstime = aDuration.toLong() shl 1

            if (xMaxProgresstime > Int.MAX_VALUE - 1) {
                base.eUt = Int.MAX_VALUE - 1
                base.maxProgressTime = Int.MAX_VALUE - 1
            } else {
                base.eUt = aEUt shr 2
                base.maxProgressTime = xMaxProgresstime.toInt()
            }
        } else {
            var xEUt = aEUt.toLong()

            var tempEUt = max(xEUt.toDouble(), GT_Values.V[1].toDouble()).toLong()
            base.maxProgressTime = aDuration

            while (tempEUt <= GT_Values.V[mTier - 1] * mAmperage) {
                tempEUt = tempEUt shl 2
                base.maxProgressTime = base.maxProgressTime shr 1
                xEUt = if (base.maxProgressTime <= 0) xEUt shr 1 else xEUt shl 2
            }

            while (xEUt > maxInputVoltage) {
                xEUt = xEUt shr 2
                base.maxProgressTime = base.maxProgressTime shl 1
            }

            if (xEUt > Int.MAX_VALUE - 1) {
                base.eUt = Int.MAX_VALUE - 1
                base.maxProgressTime = Int.MAX_VALUE - 1
            } else {
                base.eUt = xEUt.toInt()
                if (base.eUt == 0) base.eUt = 1
                if (base.maxProgressTime <= 0) base.maxProgressTime = Config.MAX_TICK_RATE
            }
        }
    }
}