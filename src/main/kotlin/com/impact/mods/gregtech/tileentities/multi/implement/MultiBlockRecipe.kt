package com.impact.mods.gregtech.tileentities.multi.implement

import com.impact.util.Utilits
import com.impact.util.multis.OverclockCalculate
import com.impact.util.multis.WorldProperties
import com.impact.util.recipe.RecipeHelper
import gregtech.api.enums.GT_Values
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.util.GT_Recipe
import gregtech.api.util.GT_Utility
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import kotlin.math.abs

class MultiBlockRecipe<MULTIS : GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>>(val multis: GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>) {

    companion object {
        private const val MAX_TICK_FOR_RECIPE = 2
        private const val DEFAULT_OVERCLOCK_EU = 4
        private const val DEFAULT_OVERCLOCK_TIME = 2
        private const val EFFICIENCY_100 = 10000
        private const val EFFICIENCY_PER_100 = 1000
    }

    var fluidsIn: Array<FluidStack>? = null
    var itemsIn: Array<ItemStack>? = null
    var voltageIn: Long = 0
    var tierFromVoltage: Int = 0
    var parallel: Int = 1
    var preFoundRecipe = false
    var finallyFoundRecipe = false

    init {
        multis.mCheckParallelCurrent = 0
    }

    fun sortItemsVanila() {
        val inputList: ArrayList<ItemStack> = multis.storedInputs
        var inputSize = inputList.size

        for (i in 0 until inputSize - 1) {
            for (j in i.unaryPlus() until inputSize) {
                if (GT_Utility.areStacksEqual(inputList[i], inputList[j])) {
                    if (inputList[i].stackSize >= inputList[j].stackSize) {
                        inputList.removeAt(j.unaryMinus())
                        inputSize = inputList.size
                    } else {
                        inputList.removeAt(i.unaryMinus())
                        inputSize = inputList.size
                        break
                    }
                }
            }
        }
        itemsIn = inputList.toTypedArray()
    }

    fun sortFluidsVanila() {
        val inputList: ArrayList<FluidStack> = multis.storedFluids
        var inputSize = inputList.size
        for (i in 0 until inputSize - 1) {
            for (j in i.unaryPlus() until inputSize) {
                if (GT_Utility.areFluidsEqual(inputList[i], inputList[j])) {
                    if (inputList[i].amount >= inputList[j].amount) {
                        inputList.removeAt(j.unaryMinus())
                        inputSize = inputList.size
                    } else {
                        inputList.removeAt(i.unaryMinus())
                        inputSize = inputList.size
                        break
                    }
                }
            }
        }
        fluidsIn = inputList.toTypedArray()
    }

    fun sortFluids() {
        fluidsIn = multis.storedFluids.toTypedArray()
    }

    fun <T : GT_MetaTileEntity_Hatch> sortItems(bus: T? = null) {
        itemsIn = if (bus != null) {
            val tBusItems = ArrayList<ItemStack>()
            bus.mInventory
                .reversed()
                .forEach { it != null && tBusItems.add(it) }
//            for (i in bus.baseMetaTileEntity.sizeInventory - 1 downTo 0) {
//                if (bus.baseMetaTileEntity.getStackInSlot(i) != null) {
//                    tBusItems.add(bus.baseMetaTileEntity.getStackInSlot(i))
//                }
//            }
            tBusItems.toTypedArray()
        } else {
            multis.storedInputs.toTypedArray()
        }
    }

    // TODO: 20.02.2022 Проверить
    fun checkSizeHatches() {
        preFoundRecipe = (itemsIn?.isNotEmpty() == true) || (fluidsIn?.isNotEmpty() == true)
    }

    fun checkVoltage(vanila: Boolean = true) {
        if (preFoundRecipe) {
            if (vanila) {
                voltageIn = multis.maxInputVoltageVanila
                tierFromVoltage = 1.coerceAtLeast(GT_Utility.getTier(voltageIn).toInt())
            } else {
                voltageIn = multis.maxInputVoltage
                tierFromVoltage = 1.coerceAtLeast(GT_Utility.getTier(voltageIn).toInt())
            }
        }
    }

    fun checkRecipeMap(aDontCheckStackSizes: Boolean = true): GT_Recipe? {
        val recipe = Utilits.findRecipe(
            multis.recipeMap,
            multis.baseMetaTileEntity,
            false,
            aDontCheckStackSizes,
            GT_Values.V[tierFromVoltage],
            fluidsIn,
            itemsIn
        )
        preFoundRecipe = recipe != null
        return recipe
    }

    fun checkInputEquals(
        recipe: GT_Recipe?,
        aDecreaseStackSizeBySuccess: Boolean = true,
        aDontCheckStackSizes: Boolean = false
    ) {
        finallyFoundRecipe =
            Utilits.checkInputs(recipe, aDecreaseStackSizeBySuccess, aDontCheckStackSizes, fluidsIn, itemsIn)
    }

    fun checkInputEqualsParallel(
        recipe: GT_Recipe,
        aDecreaseStackSizeBySuccess: Boolean = true,
        aDontCheckStackSizes: Boolean = false,
        enabledChance: Boolean = false,
    ) {
        finallyFoundRecipe = false
        if (!preFoundRecipe) return
        val outputFluids = ArrayList<FluidStack>()
        val tOut = arrayOfNulls<ItemStack>(recipe.mOutputs.size)
        while ((!fluidsIn.isNullOrEmpty() || !itemsIn.isNullOrEmpty()) && multis.mCheckParallelCurrent < multis.mParallel) {
            if ((recipe.mEUt * (multis.mCheckParallelCurrent + 1L)) < voltageIn &&
                Utilits.checkInputs(recipe, aDecreaseStackSizeBySuccess, aDontCheckStackSizes, fluidsIn, itemsIn)
            ) {
                finallyFoundRecipe = true
                for (h in recipe.mOutputs.indices) {
                    if (recipe.getOutput(h) != null) {
                        tOut[h] = recipe.getOutput(h).copy()
                        tOut[h]!!.stackSize = 0
                    }
                }
                for (i in recipe.mFluidOutputs.indices) {
                    outputFluids.add(recipe.getFluidOutput(i))
                }
                ++multis.mCheckParallelCurrent
            } else {
                break
            }
        }
        multis.mOutputItems = RecipeHelper.resizeItemStackSizeChance(tOut, recipe, multis, enabledChance)
        multis.mOutputFluids = outputFluids.toTypedArray()
    }

    fun worldProperties(recipe: GT_Recipe, needCleanRoom: Boolean = false, needLowGravity: Boolean = false) {
        if (needCleanRoom) {
            finallyFoundRecipe = WorldProperties.needCleanroom(recipe, multis)
        }
        if (needLowGravity) {
            finallyFoundRecipe = WorldProperties.needSpace(recipe, multis)
        }
    }

    fun setEfficiency(
        efficiency: Int = EFFICIENCY_100 - (multis.idealStatus - multis.repairStatus) * EFFICIENCY_PER_100,
        efficiencyIncrease: Int = EFFICIENCY_100
    ) {
        multis.mEfficiency = efficiency
        multis.mEfficiencyIncrease = efficiencyIncrease
    }

    fun calcEU(recipe: GT_Recipe) {
        var tEUt = recipe.mEUt
        var maxProgressTime = recipe.mDuration

        while (tEUt <= GT_Values.V[tierFromVoltage - 1] && maxProgressTime > MAX_TICK_FOR_RECIPE) {
            tEUt *= DEFAULT_OVERCLOCK_EU
            maxProgressTime /= DEFAULT_OVERCLOCK_TIME
        }
        if (maxProgressTime < MAX_TICK_FOR_RECIPE) {
            maxProgressTime = MAX_TICK_FOR_RECIPE
            tEUt = recipe.mEUt * recipe.mDuration / DEFAULT_OVERCLOCK_TIME
        }
        multis.mEUt = -abs(tEUt)
        multis.mMaxProgresstime = maxProgressTime
    }

    fun calcEUParallel(recipe: GT_Recipe) {
        var tEUt = recipe.mEUt.toLong() * multis.mCheckParallelCurrent
        if (tEUt > Int.MAX_VALUE) {
            var divider = 0
            while (tEUt > Int.MAX_VALUE) {
                tEUt /= DEFAULT_OVERCLOCK_TIME
                divider++
            }
            OverclockCalculate.calculateOverclockedNessMulti(
                (tEUt / (divider * DEFAULT_OVERCLOCK_TIME)).toInt(),
                recipe.mDuration * (divider * DEFAULT_OVERCLOCK_TIME),
                1, voltageIn, multis
            )
        } else {
            OverclockCalculate.calculateOverclockedNessMulti(
                tEUt.toInt(), recipe.mDuration, 1, voltageIn, multis
            )
        }

        finallyFoundRecipe = multis.mMaxProgresstime == Int.MAX_VALUE - 1 && multis.mEUt == Int.MAX_VALUE - 1

        if (finallyFoundRecipe) {
            multis.mMaxProgresstime = RecipeHelper.calcTimeParallel(multis)
            multis.mEUt = if (multis.mEUt > 0) -multis.mEUt else multis.mEUt
            multis.mEUt = -abs(multis.mEUt)
        }
    }

    fun setOutputs(recipe: GT_Recipe, default: Boolean = false, af: AdditionalFun? = null) {
        if (default) {
            multis.mOutputItems = recipe.mOutputs
            multis.mOutputFluids = recipe.mFluidOutputs
            af?.get()
        } else {
            af?.get()
        }
    }

    fun interface AdditionalFun {
        fun get()
    }
}