package com.impact.util.fluid

import appeng.api.config.Actionable
import appeng.util.item.AEFluidStack
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank
import com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch
import cpw.mods.fml.common.Loader
import gregtech.api.GregTech_API
import gregtech.api.interfaces.ITexture
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase
import gregtech.api.util.GT_Config
import net.minecraft.init.Bootstrap
import net.minecraft.launchwrapper.LaunchClassLoader
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy
import java.net.URLClassLoader

/** Регрессионные проверки общего хранилища, I/O-хэтча и вывода жидкостей. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MultiFluidHandlerTest {
    private lateinit var forgeLoader: LaunchClassLoader
    private lateinit var scenarios: Any

    /** Загружает сценарии через загрузчик Forge 1.7, сохраняя общий runtime Kotlin и JUnit. */
    @BeforeAll
    fun initializeFluids() {
        val classLoader = javaClass.classLoader as URLClassLoader
        forgeLoader = LaunchClassLoader(classLoader.urLs).apply {
            addClassLoaderExclusion("org.junit.")
            addClassLoaderExclusion("kotlin.")
        }
        val scenarioClass = forgeLoader.loadClass("${javaClass.name}\$Scenarios")
        scenarios = scenarioClass.getDeclaredConstructor().newInstance()
        scenarioClass.getMethod("initialize").invoke(scenarios)
    }

    /** Освобождает загрузчик после завершения проверок, включая неудачную инициализацию. */
    @AfterAll
    fun closeLoader() {
        if (::forgeLoader.isInitialized) forgeLoader.close()
    }

    /** Вызывает изолированный сценарий и передаёт JUnit исходную причину ошибки. */
    private fun check(name: String) {
        try {
            scenarios.javaClass.getMethod(name).invoke(scenarios)
        } catch (failure: InvocationTargetException) {
            throw failure.targetException
        }
    }

    /** Проверяет сохранность других жидкостей после полного извлечения одной. */
    @Test
    fun preservesOtherFluidsWhenOneIsDrained() = check("preservesOtherFluidsWhenOneIsDrained")

    /** Проверяет оба формата NBT, настройки и фильтр. */
    @Test
    fun restoresWrappedControllerNbtAndHandlerNbt() = check("restoresWrappedControllerNbtAndHandlerNbt")

    /** Проверяет ограничение по ёмкости и явное включение уничтожения избытка. */
    @Test
    fun acceptsOnlyFreeSpaceUnlessVoidingIsEnabled() = check("acceptsOnlyFreeSpaceUnlessVoidingIsEnabled")

    /** Проверяет отсутствие изменений и уведомлений при симуляции извлечения. */
    @Test
    fun simulationDoesNotRemoveEmptyFluidOrNotifyWatchers() = check("simulationDoesNotRemoveEmptyFluidOrNotifyWatchers")

    /** Проверяет безопасную обработку отсутствующих слотов и пустого NBT. */
    @Test
    fun missingSlotsAndEmptyNbtAreSafe() = check("missingSlotsAndEmptyNbtAreSafe")

    /** Проверяет сохранность жидкости при уменьшении ёмкости и последующей вставке. */
    @Test
    fun shrinkingCapacityDoesNotDrainFluidDuringFill() = check("shrinkingCapacityDoesNotDrainFluidDuringFill")

    /** Проверяет доступ I/O-хэтча к общему хранилищу без предметных слотов. */
    @Test
    fun ioHatchUsesSharedStorageAndHasNoItemSlots() = check("ioHatchUsesSharedStorageAndHasNoItemSlots")

    /** Проверяет возврат остатка при частичном заполнении через ME-сеть. */
    @Test
    fun meInsertionReturnsPartialRemainderWithoutVoiding() = check("meInsertionReturnsPartialRemainderWithoutVoiding")

    /** Проверяет выгрузку выбранной жидкости через заблокированный выходной хэтч. */
    @Test
    fun lockedOutputPreservesAllOtherFluids() = check("lockedOutputPreservesAllOtherFluids")

    /** Сценарии с настоящими классами Forge, загружаемые отдельно от среды JUnit. */
    class Scenarios {
        /** Подготавливает реестры Minecraft и минимальные данные загрузчика Forge. */
        fun initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", File("."), emptyList<String>())
            Bootstrap.func_151354_b()
        }

        /** Создаёт разблокированное хранилище на 25 типов по 64 ведра. */
        private fun tank() = MultiFluidHandler(64_000, 25).apply { setLock(false) }

        /** Подключает тестовый I/O-хэтч напрямую, обходя инициализацию ME-сети. */
        private fun hatch(tank: MultiFluidHandler) =
            GTMTE_TankHatch("test.tank.hatch", 5, emptyArray<String>(), emptyArray<Array<Array<ITexture>>>()).apply {
                mfh = tank
            }

        /** Полностью извлекает воду и проверяет сохранность лавы. */
        fun preservesOtherFluidsWhenOneIsDrained() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 64_000), true)
            tank.pushFluid(FluidStack(FluidRegistry.LAVA, 64_000), true)
            assertEquals(64_000, tank.pullFluid(FluidStack(FluidRegistry.WATER, 64_000), true))
            assertEquals(1, tank.getFluids().size)
            assertEquals(64_000, tank.getFluid(0).amount)
            assertEquals(FluidRegistry.LAVA, tank.getFluid(0).getFluid())
        }

        /** Восстанавливает плоское и вложенное состояния, включая фильтр и режим избытка. */
        fun restoresWrappedControllerNbtAndHandlerNbt() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 8_000), true)
            tank.pushFluid(FluidStack(FluidRegistry.LAVA, 16_000), true)
            tank.setVoidExcess(true)
            val saved = tank.saveNBTData(NBTTagCompound())
            val filter = NBTTagCompound().apply {
                setBoolean("block", false)
                setTag("0", FluidStack(FluidRegistry.WATER, 0).writeToNBT(NBTTagCompound()))
            }
            saved.setTag("filter", filter)
            val controller = NBTTagCompound().apply {
                setInteger("capacityPerFluid", tank.capacity)
                setTag("fluids", saved)
            }
            for (data in listOf(saved, controller)) {
                val restored = tank().apply { loadNBTData(data) }
                assertEquals(64_000, restored.capacity)
                assertTrue(restored.voidExcess)
                assertEquals(2, restored.getFluids().size)
                assertEquals(8_000, restored.getFluid(0).amount)
                assertEquals(16_000, restored.getFluid(1).amount)
                assertEquals(0, restored.pushFluid(FluidStack(FluidRegistry.LAVA, 1_000), true))
                assertEquals(1_000, restored.pushFluid(FluidStack(FluidRegistry.WATER, 1_000), true))
            }
        }

        /** Проверяет симуляцию, частичное заполнение и уничтожение избытка полного бака. */
        fun acceptsOnlyFreeSpaceUnlessVoidingIsEnabled() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 63_000), true)
            val input = FluidStack(FluidRegistry.WATER, 8_000)
            assertEquals(1_000, tank.pushFluid(input, false))
            assertEquals(63_000, tank.getFluid(0).amount)
            assertEquals(1_000, tank.pushFluid(input, true))
            assertEquals(0, tank.pushFluid(input, true))
            tank.setVoidExcess(true)
            assertTrue(tank.couldPush(input))
            assertEquals(8_000, tank.pushFluid(input, true))
            assertEquals(64_000, tank.getFluid(0).amount)
        }

        /** Симулирует извлечение из пустого слота и проверяет список и наблюдателя. */
        fun simulationDoesNotRemoveEmptyFluidOrNotifyWatchers() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 0), true)
            var notifications = 0
            tank.addFluidWatcher { _, _ -> notifications++ }
            assertEquals(0, tank.pullFluid(FluidStack(FluidRegistry.WATER, 1_000), false))
            assertEquals(0, tank.pullFluid(FluidStack(FluidRegistry.WATER, 1_000), 0, false))
            assertEquals(1, tank.getFluids().size)
            assertEquals(0, notifications)
        }

        /** Обращается к отсутствующему слоту и загружает пустое состояние. */
        fun missingSlotsAndEmptyNbtAreSafe() {
            val tank = tank()
            assertNull(tank.getFluid(24))
            assertEquals(0, tank.pullFluid(FluidStack(FluidRegistry.WATER, 1_000), 24, true))
            assertEquals(0, tank.pushFluid(FluidStack(FluidRegistry.WATER, 1_000), 24, true))
            tank.loadNBTData(NBTTagCompound())
            assertTrue(tank.getFluids().isEmpty())
        }

        /** Уменьшает ёмкость заполненного бака и проверяет, что вставка не отнимает жидкость. */
        fun shrinkingCapacityDoesNotDrainFluidDuringFill() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 64_000), true)
            tank.capacityPerFluid = 32_000
            assertEquals(0, tank.pushFluid(FluidStack(FluidRegistry.WATER, 1_000), true))
            assertEquals(64_000, tank.getFluid(0).amount)
        }

        /** Проверяет обе перегрузки заполнения, симуляцию извлечения и блокировку I/O-хэтча. */
        fun ioHatchUsesSharedStorageAndHasNoItemSlots() {
            val tank = tank()
            val hatch = hatch(tank)
            assertEquals(0, hatch.sizeInventory)
            assertFalse(hatch.doesEmptyContainers())
            assertFalse(hatch.doesFillContainers())
            assertEquals(64_000, hatch.fill(FluidStack(FluidRegistry.WATER, 64_000), true))
            assertEquals(0, hatch.fill(null, FluidStack(FluidRegistry.WATER, 8_000), true))
            assertEquals(64_000, tank.getFluid(0).amount)
            assertEquals(8_000, hatch.drain(8_000, false).amount)
            assertEquals(64_000, tank.getFluid(0).amount)
            assertEquals(8_000, hatch.drain(8_000, true).amount)
            assertEquals(56_000, tank.getFluid(0).amount)
            assertNull(hatch.drain(null, FluidStack(FluidRegistry.LAVA, 1_000), true))
            tank.setLock(true)
            assertEquals(0, hatch.fill(FluidStack(FluidRegistry.WATER, 1_000), true))
            assertNull(hatch.drain(1_000, true))
        }

        /** Проверяет остаток ME-вставки, неизменность входа и явное уничтожение избытка. */
        fun meInsertionReturnsPartialRemainderWithoutVoiding() {
            val tank = tank()
            tank.pushFluid(FluidStack(FluidRegistry.WATER, 63_000), true)
            val hatch = hatch(tank)
            val input = AEFluidStack.create(FluidStack(FluidRegistry.WATER, 8_000))
            assertEquals(7_000L, hatch.injectItems(input, Actionable.SIMULATE, null).stackSize)
            assertEquals(63_000, tank.getFluid(0).amount)
            assertEquals(7_000L, hatch.injectItems(input, Actionable.MODULATE, null).stackSize)
            assertEquals(8_000L, input.stackSize)
            assertEquals(64_000, tank.getFluid(0).amount)
            tank.setVoidExcess(true)
            assertNull(hatch.injectItems(input, Actionable.MODULATE, null))
            assertEquals(64_000, tank.getFluid(0).amount)
        }

        /** Выгружает 64 ведра воды порциями по восемь, сохраняя остальные жидкости мультитанка. */
        fun lockedOutputPreservesAllOtherFluids() {
            GregTech_API.sMachineFile = GT_Config(object : Configuration() {
                override fun load() {}
                override fun save() {}
            })
            val controllers = listOf<GT_MetaTileEntity_MultiBlockBase>(
                object : GTMTE_MultiTank("test.multi.tank") { override fun run() {} },
                object : GTMTE_SingleTank("test.single.tank") { override fun run() {} },
            )
            for (controller in controllers) {
                val tank = tank()
                tank.pushFluid(FluidStack(FluidRegistry.WATER, 64_000), true)
                val multiTank = controller is GTMTE_MultiTank
                if (multiTank) {
                    tank.pushFluid(FluidStack(FluidRegistry.LAVA, 64_000), true)
                    for (name in listOf("tank_test_oil", "tank_test_acid")) {
                        FluidRegistry.registerFluid(Fluid(name))
                        tank.pushFluid(FluidStack(FluidRegistry.getFluid(name), 64_000), true)
                    }
                } else {
                    tank.maxDistinctFluids = 1
                }
                val controllerClass = controller.javaClass.superclass
                controllerClass.getDeclaredField("mfh").apply {
                    isAccessible = true
                    set(controller, tank)
                }
                val output = GT_MetaTileEntity_Hatch_Output(
                    "test.output", 0, emptyArray<String>(), emptyArray<Array<Array<ITexture>>>(),
                )
                output.baseMetaTileEntity = Proxy.newProxyInstance(
                    IGregTechTileEntity::class.java.classLoader,
                    arrayOf(IGregTechTileEntity::class.java),
                ) { _, method, _ ->
                    when {
                        method.name == "getMetaTileEntity" -> output
                        method.returnType == Boolean::class.javaPrimitiveType -> false
                        else -> null
                    }
                } as IGregTechTileEntity
                output.mMode = 8
                output.setLockedFluid(FluidRegistry.WATER)
                controller.mOutputHatches.add(output)
                val transfer = controllerClass.getDeclaredMethod("outputFluid", FluidStack::class.java).apply {
                    isAccessible = true
                }
                repeat(8) {
                    for (stored in tank.getFluids().toList()) transfer.invoke(controller, stored)
                    assertEquals(8_000, output.fluidAmount)
                    // Имитируем расход содержимого выходного хэтча на заполнение капсул.
                    output.setDrainableStack(null)
                }
                assertEquals(if (multiTank) 3 else 0, tank.getFluids().size)
                for (stored in tank.getFluids()) assertEquals(64_000, stored.amount)
            }
        }
    }
}
