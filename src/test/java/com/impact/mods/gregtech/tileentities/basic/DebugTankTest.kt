package com.impact.mods.gregtech.tileentities.basic

import net.minecraft.launchwrapper.LaunchClassLoader
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.lang.reflect.InvocationTargetException
import java.net.URLClassLoader

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DebugTankTest {

    private lateinit var loader: LaunchClassLoader
    private lateinit var scenarios: Any

    @BeforeAll
    fun initialize() {
        loader = LaunchClassLoader((javaClass.classLoader as URLClassLoader).urLs)
        loader.addClassLoaderExclusion("org.junit.")
        loader.addClassLoaderExclusion("kotlin.")
        val scenarioClass = loader.loadClass("${javaClass.name.substringBeforeLast('.')}.DebugTankScenarios")
        scenarios = scenarioClass.getConstructor().newInstance()
        scenarioClass.getMethod("initialize").invoke(scenarios)
    }

    @AfterAll
    fun closeLoader() {
        if (::loader.isInitialized) {
            loader.close()
        }
    }

    private fun check(method: String) {
        try {
            scenarios.javaClass.getMethod(method).invoke(scenarios)
        } catch (failure: InvocationTargetException) {
            throw failure.targetException
        }
    }

    @Test
    fun firstFillImmediatelyCreatesFullTankWithoutNeighbor() {
        check("firstFillImmediatelyCreatesFullTankWithoutNeighbor")
    }

    @Test
    fun simulatedFillDoesNotSelectFluid() {
        check("simulatedFillDoesNotSelectFluid")
    }

    @Test
    fun repeatedFullDrainsDoNotExhaustOrChangeFluid() {
        check("repeatedFullDrainsDoNotExhaustOrChangeFluid")
    }

    @Test
    fun containerAssignmentImmediatelyFillsTankAndCopiesInput() {
        check("containerAssignmentImmediatelyFillsTankAndCopiesInput")
    }

    @Test
    fun loadingOldPartiallyFilledTankRestoresFullAmount() {
        check("loadingOldPartiallyFilledTankRestoresFullAmount")
    }

    @Test
    fun outputToAlwaysAcceptingNeighborIsBoundedPerTick() {
        check("outputToAlwaysAcceptingNeighborIsBoundedPerTick")
    }
}
