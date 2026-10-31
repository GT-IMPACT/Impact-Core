package com.impact.mods.gregtech.tileentities.multi.structure

import com.impact.mods.gregtech.blocks.HatchCounts
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase

object RequiresHatches {

    @JvmStatic
    @JvmOverloads
    fun GTMTE_Impact_BlockBase<*>.hasRequireHatches(
        energy: Int = 0,
        dynamo: Int = 0,
        inputBuss: Int = 0,
        outputBuss: Int = 0,
        inputHatch: Int = 0,
        outputHatch: Int = 0,
        maintenance: Int = 0,
        muffler: Int = 0,
    ): Boolean {
        return HatchCounts.energyHatches(this) <= energy &&
                HatchCounts.dynamoHatches(this) <= dynamo &&
                HatchCounts.inputBuses(this) <= inputBuss &&
                mOutputBusses.size <= outputBuss &&
                HatchCounts.inputHatches(this) <= inputHatch &&
                mOutputHatches.size <= outputHatch &&
                mMaintenanceHatches.size <= maintenance &&
                mMufflerHatches.size <= muffler
    }

    @JvmStatic
    @JvmOverloads
    fun GT_MetaTileEntity_MultiParallelBlockBase<*>.hasRequireParallelHatches(
        energy: Int = 0,
        dynamo: Int = 0,
        inputBuss: Int = 0,
        outputBuss: Int = 0,
        inputHatch: Int = 0,
        outputHatch: Int = 0,
        maintenance: Int = 0,
        muffler: Int = 0,
        parallelIn: Int = 0,
        parallelOut: Int = 0,
    ): Boolean {
        return hasRequireHatches(
            energy = energy,
            dynamo = dynamo,
            inputBuss = inputBuss,
            outputBuss = outputBuss,
            inputHatch = inputHatch,
            outputHatch = outputHatch,
            maintenance = maintenance,
            muffler = muffler,
        ) && sParallHatchesIn.size <= parallelIn && sParallHatchesOut.size <= parallelOut
    }
}
