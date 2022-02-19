package com.impact.mods.gregtech.tileentities.multi.implement

import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity
import com.impact.util.Utilits
import com.impact.util.multis.WorldProperties
import gregtech.api.enums.GT_Values
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.util.GT_Recipe
import gregtech.api.util.GT_Utility
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import java.util.*

class MultiBlockRecipe<MULTIS : GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>>(val multis: GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>) {

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

    @JvmOverloads
    fun <T : GT_MetaTileEntity_Hatch> sortItems(bus: T? = null) {
        itemsIn = if (bus != null) {
            val tBusItems: ArrayList<ItemStack> = ArrayList()
            if (isValidMetaTileEntity(bus)) {
                for (i in bus.baseMetaTileEntity.sizeInventory - 1 downTo 0) {
                    if (bus.baseMetaTileEntity.getStackInSlot(i) != null) {
                        tBusItems.add(bus.baseMetaTileEntity.getStackInSlot(i))
                    }
                }
            }
            tBusItems.toTypedArray()
        } else {
            multis.storedInputs.toTypedArray()
        }
    }

    fun checkSizeHatches() {
        if (itemsIn != null || fluidsIn != null) {
            preFoundRecipe = itemsIn!!.isNotEmpty() || fluidsIn!!.isNotEmpty()
        }
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

    fun checkRecipeMap(): GT_Recipe? =
        Utilits.findRecipe(
            multis.recipeMap,
            multis.baseMetaTileEntity,
            false,
            false,
            GT_Values.V[tierFromVoltage],
            fluidsIn,
            itemsIn
        )

    fun checkInputEquals(
        recipe: GT_Recipe?,
        aDecreaseStackSizeBySuccess: Boolean = false,
        aDontCheckStackSizes: Boolean = true
    ) {
        finallyFoundRecipe =
            Utilits.checkInputs(recipe, aDecreaseStackSizeBySuccess, aDontCheckStackSizes, fluidsIn, itemsIn)

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
        efficiency: Int = 10000 - (multis.idealStatus - multis.repairStatus) * 1000,
        efficiencyIncrease: Int = 10000
    ) {
        multis.mEfficiency = efficiency
        multis.mEfficiencyIncrease = efficiencyIncrease
    }

    fun calcEU(recipe: GT_Recipe) {
        var tEUt = recipe.mEUt
        var maxProgressTime = recipe.mDuration

        while (tEUt <= GT_Values.V[tierFromVoltage - 1] && maxProgressTime > 2) {
            tEUt *= 4
            maxProgressTime /= 2
        }
        if (maxProgressTime < 2) {
            maxProgressTime = 2
            tEUt = recipe.mEUt * recipe.mDuration / 2
        }
        multis.mEUt = -tEUt
        multis.mMaxProgresstime = maxProgressTime
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