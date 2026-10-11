package com.impact.mods.gregtech

import com.google.common.io.ByteStreams
import com.impact.mods.gregtech.gui.regulatechest.Container_ValueRegulateChest
import com.impact.mods.gregtech.tileentities.basic.GTMTE_RegulateDigitalChest
import cpw.mods.fml.common.Loader
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.init.Bootstrap
import net.minecraft.launchwrapper.LaunchClassLoader
import net.minecraft.nbt.NBTTagCompound
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy
import java.net.URLClassLoader

class RegulateDigitalChestTest {
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
    fun acceptsPacketAndPersistsCapacity() = check("acceptsPacketAndPersistsCapacity")

    @Test
    fun reconstructsSignedProgressUpdates() = check("reconstructsSignedProgressUpdates")

    class Scenarios {
        init {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", File("."), emptyList<String>())
            Bootstrap.func_151354_b()
        }

        private fun chest() = GTMTE_RegulateDigitalChest("test.regulate.chest", 1, emptyArray(), emptyArray())

        fun acceptsPacketAndPersistsCapacity() {
            val chest = chest()
            var dirty = false
            val tile = Proxy.newProxyInstance(javaClass.classLoader, arrayOf(IGregTechTileEntity::class.java)) { _, method, _ ->
                when (method.name) {
                    "isServerSide" -> true
                    "markDirty" -> { dirty = true; null }
                    else -> null
                }
            } as IGregTechTileEntity
            chest.setBaseMetaTileEntity(tile)
            for ((requested, expected) in listOf(128 to 128, 100_000 to 100_000, 4_000_000 to 4_000_000,
                0 to 1, -1 to 1, Int.MAX_VALUE to 4_000_000)) {
                val output = ByteStreams.newDataOutput().apply { writeInt(requested) }
                chest.receive(ByteStreams.newDataInput(output.toByteArray()))
                assertEquals(expected, chest.maxItemCount)
                val saved = NBTTagCompound()
                chest.saveNBTData(saved)
                assertEquals(expected, chest().apply { loadNBTData(saved) }.maxItemCount)
            }
            assertTrue(dirty)
            assertEquals(64, chest().apply { loadNBTData(NBTTagCompound()) }.maxItemCount)
        }

        fun reconstructsSignedProgressUpdates() {
            val chest = chest()
            val tile = Proxy.newProxyInstance(javaClass.classLoader, arrayOf(IGregTechTileEntity::class.java)) { _, method, _ ->
                when (method.name) {
                    "getMetaTileEntity" -> chest
                    "isClientSide" -> true
                    else -> null
                }
            } as IGregTechTileEntity
            val container = Container_ValueRegulateChest(null, tile)
            assertFalse(container.capacitySynced)
            for (capacity in listOf(64, 128, 32_768, 65_535, 65_536, 100_000, 4_000_000)) {
                container.updateProgressBar(100, capacity.toShort().toInt())
                container.updateProgressBar(101, (capacity ushr 16).toShort().toInt())
                assertEquals(capacity, container.mMaxCapacity)
                assertTrue(container.capacitySynced)
            }
        }
    }
}
