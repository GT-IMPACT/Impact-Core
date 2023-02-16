package com.impact.api.recipe

import com.impact.core.Config
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase
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

class MultiBlockRecipeBuilder<R : GTMTE_Impact_BlockBase<*>>(val machine: R) {

    companion object {
        private const val MAX_TICK_FOR_RECIPE = 2
        private const val DEFAULT_OVERCLOCK_EU = 4
        private const val DEFAULT_OVERCLOCK_TIME = 2
        private const val EFFICIENCY_100 = 10000
        private const val EFFICIENCY_PER_100 = 1000
    }

    private val itemHatches: ArrayList<GT_MetaTileEntity_Hatch> = arrayListOf()

    //Int - index bus, ItemsStackList - items in bus
    private val inputs: HashMap<Int, List<ItemStack>> = hashMapOf()
    private val outputs: ArrayList<ItemStack> = arrayListOf()

    private val inputsF: ArrayList<FluidStack> = arrayListOf()
    private val outputsF: ArrayList<FluidStack> = arrayListOf()

    private var recipeOk: Boolean = false
    private var voltageIn: Long = 0L
    private var tierFromVoltage: Int = 0
    private var recipe: GT_Recipe? = null

    fun updateHatches() {
        updateItemHatches()
    }

    private fun updateItemHatches() {
        val itemH = mutableListOf<GT_MetaTileEntity_Hatch>()
        itemH += machine.mInputBusses
        itemH += machine.mInputBusHatches
        itemH += machine.mInputHatches

        itemHatches.clear()
        itemHatches += itemH.sortedBy { it.idHatch }
    }

    fun start(): MultiBlockRecipeBuilder<R> {
        machine.updateSlots()
        recipeOk = false
        voltageIn = 0L
        tierFromVoltage = 0
        recipe = null
        inputs.clear()
        outputs.clear()
        inputsF.clear()
        outputsF.clear()
        return this
    }

    fun clear(): MultiBlockRecipeBuilder<R> {
        machine.updateSlots()
        recipeOk = false
        voltageIn = 0L
        tierFromVoltage = 0
        recipe = null
        outputs.clear()
        outputsF.clear()

        if (machine is GT_MetaTileEntity_MultiParallelBlockBase<*>) {
            machine.mCheckParallelCurrent = 0
        }

        return this
    }

    fun checkItemsByGT(): MultiBlockRecipeBuilder<R> {
        val inputList: ArrayList<ItemStack> = machine.storedInputs
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
        inputs.clear()
        inputs[0] = inputList
        return this
    }

    fun checkFluidsByGT(): MultiBlockRecipeBuilder<R> {
        val inputList: ArrayList<FluidStack> = machine.storedFluids
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
        inputsF.clear()
        inputsF += inputList
        return this
    }

    fun checkItemsByNotNull(): MultiBlockRecipeBuilder<R> {
        inputs.clear()
        inputs[0] = machine.storedInputs.filterNotNull()
        return this
    }

    fun checkFluidsByNotNull(): MultiBlockRecipeBuilder<R> {
        inputsF.clear()
        inputsF += machine.storedFluids.filterNotNull()
        return this
    }

    fun checkItemsBySeparateBus(): MultiBlockRecipeBuilder<R> {
        inputs.clear()
        val isSeparated = machine.isSeparated()
        if (isSeparated) {
            itemHatches.forEachIndexed { index, bus ->
                inputs[index] = bus.mInventory.filterNotNull().reversed()
            }
        } else {
            inputs[0] = machine.storedInputs.filterNotNull()
        }
        return this
    }

    fun startSeparateRecipe(): Int {
        return inputs.keys.size
    }

    @JvmOverloads
    fun checkSizeHatches(
        needCheckFluid: Boolean,
        needCheckItems: Boolean,
        indexBus: Int = -1,
    ): MultiBlockRecipeBuilder<R> {
        val realIndexBus = if (indexBus == -1) 0 else indexBus
        val isItemValid = inputs.isNotEmpty() && !inputs[realIndexBus].isNullOrEmpty()
        val isFluidValid = inputsF.isNotEmpty()
        recipeOk = when {
            needCheckFluid && needCheckItems -> isFluidValid || isItemValid
            needCheckFluid -> inputsF.isNotEmpty()
            needCheckItems -> isItemValid
            else -> isFluidValid || isItemValid
        }
        return this
    }

    @JvmOverloads
    fun checkVoltage(
        isGTVoltage: Boolean = false
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this

        if (isGTVoltage) {
            voltageIn = machine.maxInputVoltageVanila
            tierFromVoltage = 1.coerceAtLeast(GT_Utility.getTier(voltageIn).toInt())
        } else {
            voltageIn = machine.maxInputVoltage
            tierFromVoltage = 1.coerceAtLeast(GT_Utility.getTier(voltageIn).toInt())
        }
        return this
    }

    @JvmOverloads
    fun checkRecipeMap(
        indexBus: Int = -1,
        checkStackSize: Boolean = true,
        cashedRecipe: GT_Recipe? = null
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this

        val listItems =  if (indexBus == -1) {
            inputs.flatMap { it.value }.toTypedArray()
        } else {
            inputs[indexBus]?.toTypedArray() ?: emptyArray()
        }

        recipe = Utilits.findRecipe(
            machine.recipeMap,
            machine.baseMetaTileEntity,
            cashedRecipe,
            false,
            !checkStackSize,
            GT_Values.V[tierFromVoltage],
            inputsF.toTypedArray(),
            listItems,
        )
        recipeOk = recipe != null
        return this
    }

    @JvmOverloads
    fun checkInputEquals(
        indexBus: Int = -1,
        enabledChance: Boolean = false,
        decreaseStackSizeBySuccess: Boolean = true,
        checkStackSize: Boolean = true
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        val recipe = recipe ?: return this

        val listItems =  if (indexBus == -1) {
            inputs.flatMap { it.value }.toTypedArray()
        } else {
            inputs[indexBus]?.toTypedArray() ?: emptyArray()
        }

        val inputFl = inputsF.toTypedArray()

        val isConfirm = Utilits.checkInputs(
            recipe,
            !decreaseStackSizeBySuccess,
            !checkStackSize,
            inputFl,
            listItems,
        )

        if (isConfirm) {
            for (h in 0 until recipe.mOutputs.size) {
                val out = recipe.getOutput(h)
                if (out != null) {
                    if (enabledChance) {
                        if (machine.baseMetaTileEntity.getRandomNumber(10000) < recipe.getOutputChance(h)) {
                            outputs += out.copy()
                        }
                    } else {
                        outputs += out.copy()
                    }
                }
            }

            for (i in 0 until recipe.mFluidOutputs.size) {
                outputsF += recipe.getFluidOutput(i)
            }
        }

        recipeOk = if (isConfirm) {
            Utilits.checkInputs(
                recipe,
                decreaseStackSizeBySuccess,
                !checkStackSize,
                inputFl,
                listItems,
            )
        } else false

        return this
    }

    @JvmOverloads
    fun checkInputEqualsParallel(
        indexBus: Int = -1,
        enabledChance: Boolean = false,
        decreaseStackSizeBySuccess: Boolean = true,
        checkStackSize: Boolean = true
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        if (machine !is GT_MetaTileEntity_MultiParallelBlockBase<*>) return this
        val recipe = recipe ?: return this

        val listItems = if (indexBus == -1) {
            inputs.flatMap { it.value }.toTypedArray()
        } else {
            inputs[indexBus]?.toTypedArray() ?: emptyArray()
        }

        val isValidFluid = inputsF.isNotEmpty()
        val isValidItems = inputs.isNotEmpty()

        for (currentParallel in 1..machine.parallel) {
            if (!(isValidFluid || isValidItems)) break

            val isValidVoltage = (recipe.mEUt * (currentParallel)) < voltageIn
            if (!isValidVoltage) break

            val isValidInputs = Utilits.checkInputs(
                recipe,
                decreaseStackSizeBySuccess,
                !checkStackSize,
                inputsF.toTypedArray(),
                listItems,
            )
            if (!isValidInputs) break

            machine.mCheckParallelCurrent = currentParallel

            for (h in 0 until recipe.mOutputs.size) {
                val out = recipe.getOutput(h)
                if (out != null) {
                    if (enabledChance) {
                        if (machine.baseMetaTileEntity.getRandomNumber(10000) < recipe.getOutputChance(h)) {
                            outputs += out.copy()
                        }
                    } else {
                        outputs += out.copy()
                    }
                }
            }

            for (i in 0 until recipe.mFluidOutputs.size) {
                outputsF += recipe.getFluidOutput(i)
            }
        }
        return this
    }

    @JvmOverloads
    fun checkWorldProperties(
        needCleanRoom: Boolean = false,
        needLowGravity: Boolean = false
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        val recipe = recipe ?: return this

        if (needCleanRoom) {
            recipeOk = WorldProperties.needCleanroom(recipe, machine)
            if (!recipeOk) return this
        }
        if (needLowGravity) {
            recipeOk = WorldProperties.needSpace(recipe, machine)
            if (!recipeOk) return this
        }
        return this
    }

    @JvmOverloads
    fun checkEfficiency(
        efficiency: Int = EFFICIENCY_100 - (machine.idealStatus - machine.repairStatus) * EFFICIENCY_PER_100,
        efficiencyIncrease: Int = EFFICIENCY_100
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this

        machine.mEfficiency = efficiency
        machine.mEfficiencyIncrease = efficiencyIncrease
        return this
    }

    fun checkConsumption(): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        val recipe = recipe ?: return this

        var tEUt = recipe.mEUt
        var maxProgressTime = recipe.mDuration

        while (tEUt <= GT_Values.V[tierFromVoltage - 1] && maxProgressTime > MAX_TICK_FOR_RECIPE) {
            tEUt *= DEFAULT_OVERCLOCK_EU
            maxProgressTime /= DEFAULT_OVERCLOCK_TIME
        }

        if (maxProgressTime < Config.MAX_TICK_RATE) {
            maxProgressTime = Config.MAX_TICK_RATE
            tEUt = recipe.mEUt * recipe.mDuration / DEFAULT_OVERCLOCK_TIME
        }

        machine.mEUt = -abs(tEUt)
        machine.mMaxProgresstime = maxProgressTime

        return this
    }

    fun checkConsumptionParallel(): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        if (machine !is GT_MetaTileEntity_MultiParallelBlockBase<*>) return this
        val recipe = recipe ?: return this

        var tEUt = recipe.mEUt.toLong() * machine.mCheckParallelCurrent
        if (tEUt > Int.MAX_VALUE) {
            var divider = 0
            while (tEUt > Int.MAX_VALUE) {
                tEUt /= DEFAULT_OVERCLOCK_TIME
                divider++
            }
            OverclockCalculate.calculateOverclockedNessMulti(
                (tEUt / (divider * DEFAULT_OVERCLOCK_TIME)).toInt(),
                recipe.mDuration * (divider * DEFAULT_OVERCLOCK_TIME),
                1, voltageIn, machine
            )
        } else {
            OverclockCalculate.calculateOverclockedNessMulti(
                tEUt.toInt(), recipe.mDuration, 1, voltageIn, machine
            )
        }

        recipeOk = !(machine.mMaxProgresstime == Int.MAX_VALUE - 1 && machine.mEUt == Int.MAX_VALUE - 1)

        if (recipeOk) {
            machine.mMaxProgresstime = RecipeHelper.calcTimeParallel(machine)
            machine.mEUt = if (machine.mEUt > 0) -machine.mEUt else machine.mEUt
        }
        return this
    }

    @JvmOverloads
    fun checkOutputs(
        default: Boolean = false,
        af: AdditionalFun? = null
    ): MultiBlockRecipeBuilder<R> {
        if (!recipeOk) return this
        val recipe = recipe ?: return this

        if (default) {
            machine.mOutputItems = recipe.mOutputs
            machine.mOutputFluids = recipe.mFluidOutputs
            af?.get()
        } else {
            machine.mOutputItems = outputs.sortedItems().toTypedArray()
            machine.mOutputFluids = outputsF.sortedFluids().toTypedArray()
            af?.get()
        }
        machine.updateSlots()
        return this
    }

    fun build() : Boolean = recipeOk

    private fun List<ItemStack>?.sortedItems(): List<ItemStack> {
        if (this.isNullOrEmpty()) return emptyList()

        val sortedList = mutableListOf<ItemStack>()
        for (item in this) {
            while (item.maxStackSize < item.stackSize) {
                val candidate = item.copy()
                candidate.stackSize = candidate.maxStackSize
                item.stackSize = item.stackSize - item.maxStackSize
                sortedList += candidate
            }
        }
        sortedList += this
        return sortedList.filter { it.stackSize > 0 }
    }

    private fun List<FluidStack>?.sortedFluids(): List<FluidStack> {
        if (this.isNullOrEmpty()) return emptyList()

        val sortedList = mutableListOf<FluidStack>()
        for (fluid in this) {
            if (sortedList.contains(fluid)) {
                val id = sortedList.indexOf(fluid)
                sortedList[id].amount += fluid.amount
            } else {
                sortedList.add(fluid)
            }
        }
        return sortedList
    }

    fun interface AdditionalFun {
        fun get()
    }
}