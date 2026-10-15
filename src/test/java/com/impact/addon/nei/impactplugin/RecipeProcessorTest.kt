package com.impact.addon.nei.impactplugin

import codechicken.nei.PositionedStack
import com.github.vfyjxf.nee.processor.RecipeProcessor
import cpw.mods.fml.common.Loader
import gregtech.api.util.GT_Recipe
import gregtech.api.util.GT_Utility
import gregtech.nei.GT_NEI_AssLineHandler
import gregtech.nei.GT_NEI_SawMill
import gregtech.nei.NEI_GT_Config
import net.minecraft.init.Bootstrap
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.launchwrapper.LaunchClassLoader
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.net.URLClassLoader

class RecipeProcessorTest {
    private fun check(name: String) {
        val loader = javaClass.classLoader as URLClassLoader
        LaunchClassLoader(loader.urLs).use { forge ->
            forge.addClassLoaderExclusion("org.junit.")
            forge.addClassLoaderExclusion("kotlin.")
            val scenarios = forge.loadClass("${javaClass.name}\$Scenarios").getDeclaredConstructor().newInstance()
            try {
                scenarios.javaClass.getMethod(name).invoke(scenarios)
            } catch (failure: InvocationTargetException) {
                throw failure.targetException
            }
        }
    }

    @Test
    fun excludesOnlyAssemblyLineSpecialSlot() = check("excludesOnlyAssemblyLineSpecialSlot")

    @Test
    fun acceptsCurrentImpactHandlerClass() = check("acceptsCurrentImpactHandlerClass")

    @Test
    fun preservesSawMillWaterInput() = check("preservesSawMillWaterInput")

    @Test
    fun preservesCycloneFluidInputsAndOutputs() = check("preservesCycloneFluidInputsAndOutputs")

    class Scenarios {
        init {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", File("."), emptyList<String>())
            Bootstrap.func_151354_b()
            Loader::class.java.getDeclaredField("namedMods").apply { isAccessible = true }
                .set(Loader.instance(), emptyMap<String, cpw.mods.fml.common.ModContainer>())
            gregtech.common.items.GT_FluidDisplayItem()
            NEI_GT_Config.sIsAdded = true
            NEI_Impact_Config.sIsAdded = true
        }

        fun excludesOnlyAssemblyLineSpecialSlot() {
            val dataStick = Item()
            val material = PositionedStack(ItemStack(Item(), 4), 12, 0, false)
            val regularDataStick = PositionedStack(ItemStack(dataStick), 30, 0, false)
            val specialDataStick = PositionedStack(ItemStack(dataStick), 138, 36, false)
            val fluidSlot = PositionedStack(ItemStack(Item()), 102, 36, false)
            val inputs = listOf(material, regularDataStick, specialDataStick, fluidSlot)
            val output = PositionedStack(ItemStack(Item()), 138, 0, false)
            val handler = object : GT_NEI_AssLineHandler(GT_Recipe.GT_Recipe_Map.sAssemblylineVisualRecipes) {
                override fun getIngredientStacks(recipe: Int) = inputs
                override fun getOtherStacks(recipe: Int) = listOf(output)
            }
            val processor = AssemblyLineRecipeProcessor()
            val id = handler.overlayIdentifier

            assertEquals(setOf(id), processor.allOverlayIdentifier)
            assertEquals(listOf(material, regularDataStick, fluidSlot), processor.getRecipeInput(handler, 0, id))
            assertEquals(listOf(output), processor.getRecipeOutput(handler, 0, id))
            assertFalse(processor.mergeStacks(handler, 0, id))
            assertEquals(4, inputs.size)
        }

        fun acceptsCurrentImpactHandlerClass() {
            val input = PositionedStack(ItemStack(Item(), 4), 12, 0, false)
            val catalyst = PositionedStack(ItemStack(Item(), 0), 30, 0, false)
            val output = PositionedStack(ItemStack(Item()), 120, 0, false)
            val handler = object : NEI_Impact_MEProvider(com.impact.mods.gregtech.GT_RecipeMaps.sMESystemProvider) {
                override fun getIngredientStacks(recipe: Int) = listOf(input, catalyst)
                override fun getOtherStacks(recipe: Int) = listOf(output)
            }
            val processor = RecipeProcessorBase.create(handler.overlayIdentifier)
                .hasValidClass(NEI_Impact_MEProvider::class.java)
            try {
                assertEquals(listOf(input), processor.getRecipeInput(handler, 0, handler.overlayIdentifier))
                assertEquals(listOf(output), processor.getRecipeOutput(handler, 0, handler.overlayIdentifier))
            } finally {
                RecipeProcessor.recipeProcessors.remove(processor)
            }
        }

        fun preservesSawMillWaterInput() {
            val water = FluidStack(FluidRegistry.WATER, 1000)
            val recipe = GT_Recipe(
                arrayOf(ItemStack(Items.stick)), arrayOf(ItemStack(Items.paper)), null, intArrayOf(10000),
                arrayOf(water), emptyArray(), 1, 1, 0
            )
            val handler = GT_NEI_SawMill(GT_Recipe.GT_Recipe_Map.sSawMillVisual)
            handler.arecipes.add(handler.CachedDefaultRecipe(recipe))
            val processor = RecipeProcessorBase.create(handler.overlayIdentifier)
                .hasValidClass(GT_NEI_SawMill::class.java)
            try {
                val inputs = processor.getRecipeInput(handler, 0, handler.overlayIdentifier)
                assertEquals(2, inputs.size)
                assertEquals(Items.stick, inputs[0].item.item)
                val transferredWater = GT_Utility.getFluidFromDisplayStack(inputs[1].item)
                assertNotNull(transferredWater)
                assertTrue(water.isFluidEqual(transferredWater))
                assertEquals(1000, transferredWater.amount)
                assertEquals(2, handler.getIngredientStacks(0).size)
            } finally {
                RecipeProcessor.recipeProcessors.remove(processor)
            }
        }

        fun preservesCycloneFluidInputsAndOutputs() {
            val fluidInputs = arrayOf(FluidStack(FluidRegistry.WATER, 1000), FluidStack(FluidRegistry.LAVA, 500))
            val fluidOutputs = arrayOf(FluidStack(FluidRegistry.WATER, 250), FluidStack(FluidRegistry.LAVA, 125))
            val recipe = GT_Recipe(
                arrayOf(ItemStack(Items.stick)), arrayOf(ItemStack(Items.paper)), null, intArrayOf(10000),
                fluidInputs, fluidOutputs, 1, 1, 0
            )
            val handler = GT_NEI_HeavyMetalCyclone(GT_Recipe.GT_Recipe_Map.sCyclonRecipes)
            handler.arecipes.add(handler.CachedDefaultRecipe(recipe))
            val processor = RecipeProcessorBase.create(handler.overlayIdentifier)
                .hasValidClass(GT_NEI_HeavyMetalCyclone::class.java)
            try {
                val inputs = processor.getRecipeInput(handler, 0, handler.overlayIdentifier)
                val outputs = processor.getRecipeOutput(handler, 0, handler.overlayIdentifier)
                assertEquals(3, inputs.size)
                assertEquals(3, outputs.size)
                assertEquals(Items.stick, inputs[0].item.item)
                assertEquals(Items.paper, outputs[0].item.item)
                for ((expected, stacks) in listOf(fluidInputs to inputs, fluidOutputs to outputs)) {
                    for (i in expected.indices) {
                        val actual = GT_Utility.getFluidFromDisplayStack(stacks[i + 1].item)
                        assertNotNull(actual)
                        assertTrue(expected[i].isFluidEqual(actual))
                        assertEquals(expected[i].amount, actual.amount)
                    }
                }
                assertEquals(3, handler.getOtherStacks(0).size)
            } finally {
                RecipeProcessor.recipeProcessors.remove(processor)
            }
        }
    }
}
