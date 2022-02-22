package com.impact.mods.gregtech.tileentities.multi.implement

import gregtech.api.enums.ItemList
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch
import gregtech.api.util.GT_Recipe

object RecipeBuilder {

    @JvmStatic
    fun bussesIsNoEmpty(hatches: List<GT_MetaTileEntity_Hatch>): Boolean =
        hatches.any { it.mInventory.any { stack -> stack != null && stack.item != ItemList.Display_Fluid.item } }

    @JvmStatic
    fun <MULTIS : GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>> check3DPrinterRecipe(multis: MULTIS): Boolean {
        var checkRecipe: Boolean
        val busses = ArrayList<GT_MetaTileEntity_Hatch>()
        busses.apply {
            addAll(multis.mInputBusses)
            addAll(multis.mInputBusHatches)
            addAll(multis.mInputHatches)
        }
        val separation = (multis.mInputBusses.isNotEmpty() || multis.mInputBusHatches.isNotEmpty()) && multis.modeBuses == 0

        val preCheck = bussesIsNoEmpty(busses)
        if (!preCheck) return false

        var recipe: MultiBlockRecipe<MULTIS>
        var gtRecipe: GT_Recipe?
        for (mInputBuss: GT_MetaTileEntity_Hatch in busses) {
            recipe = MultiBlockRecipe(multis)
            recipe.apply {
                sortItems(if (separation) mInputBuss else null)
                sortFluids()
                checkSizeHatches()
                checkVoltage(vanila = true)
                gtRecipe = checkRecipeMap()
                gtRecipe?.apply {
                    checkInputEquals(recipe = this)
                    setEfficiency()
                    calcEU(this)
                    setOutputs(recipe = this, default = true) { multis.updateSlots() }
                }
                checkRecipe = finallyFoundRecipe
            }
            if (checkRecipe) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun <MULTIS : GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>> checkLowTierMachineRecipe(multis: MULTIS): Boolean {
        var checkRecipe: Boolean
        val busses = ArrayList<GT_MetaTileEntity_Hatch>()
        busses.apply {
            addAll(multis.mInputBusses)
            addAll(multis.mInputBusHatches)
            addAll(multis.mInputHatches)
        }

        val checkItemHatches = (multis.mInputBusses.isNotEmpty() || multis.mInputBusHatches.isNotEmpty())
        val separation = checkItemHatches && multis.modeBuses == 0
        val preCheck = (bussesIsNoEmpty(busses) || !checkItemHatches)

        if (!preCheck) return false

        var recipe: MultiBlockRecipe<MULTIS>
        var gtRecipe: GT_Recipe?
        for (mInputBuss: GT_MetaTileEntity_Hatch in busses) {
            recipe = MultiBlockRecipe(multis)
            recipe.apply {
                sortItems(if (separation) mInputBuss else null)
                sortFluids()
                checkSizeHatches()
                checkVoltage(vanila = true)
                gtRecipe = checkRecipeMap(aDontCheckStackSizes = false)
                gtRecipe?.apply {
                    checkInputEquals(recipe = this)
                    setEfficiency()
                    calcEU(recipe = this)
                    setOutputs(recipe = this, default = true) { multis.updateSlots() }
                }
                checkRecipe = finallyFoundRecipe
            }
            if (checkRecipe) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun <MULTIS : GT_MetaTileEntity_MultiParallelBlockBase<MULTIS>> checkParallelMachinesRecipe(
        multis: MULTIS,
        aDontCheckStackSizes: Boolean = false,
        enabledChance: Boolean = false,
    ): Boolean {
        var checkRecipe: Boolean
        val busses = ArrayList<GT_MetaTileEntity_Hatch>()
        busses.apply {
            addAll(multis.mInputBusses)
            addAll(multis.mInputBusHatches)
            addAll(multis.mInputHatches)
        }
        val checkItemHatches = (multis.mInputBusses.isNotEmpty() || multis.mInputBusHatches.isNotEmpty())
        val separation = checkItemHatches && multis.modeBuses == 0
        val preCheck = (bussesIsNoEmpty(busses) || !checkItemHatches)
        if (!preCheck) return false

        if (multis.sParallHatchesIn.isNotEmpty() && multis.mRecipeCheckParallel) {
            return false
        }

        var recipe: MultiBlockRecipe<MULTIS>
        var gtRecipe: GT_Recipe?

        for (mInputBuss: GT_MetaTileEntity_Hatch in busses) {
            recipe = MultiBlockRecipe(multis)
            recipe.apply {
                sortItems(if (separation) mInputBuss else null)
                sortFluids()
                checkSizeHatches()
                checkVoltage(vanila = false)
                gtRecipe = checkRecipeMap(aDontCheckStackSizes = aDontCheckStackSizes)
                gtRecipe?.apply {
                    worldProperties(recipe = this, needCleanRoom = true, needLowGravity = true)
                    checkInputEqualsParallel(recipe = this)
                    setEfficiency()
                    calcEUParallel(recipe = this)
                    setOutputs(recipe = this, default = false, enabledChance = enabledChance) { multis.updateSlots() }
                }
                checkRecipe = finallyFoundRecipe
            }
            if (checkRecipe) {
                return true
            }
        }
        return false
    }

}