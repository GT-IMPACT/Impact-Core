package com.impact.mods.gregtech.tileentities.basic

import cpw.mods.fml.common.Loader
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.init.Bootstrap
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.IFluidHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import java.lang.reflect.Proxy

public class DebugTankScenarios {

    private val fluid: Fluid = Fluid("debug_tank_test_fluid")
    private lateinit var world: DebugTankTestWorld
    private var dirtyCalls: Int = 0

    public fun initialize() {
        Loader.injectData(
            "7",
            "10",
            "99",
            "99",
            "1.7.10",
            "9.05",
            File("."),
            emptyList<String>(),
        )
        Bootstrap.func_151354_b()
        FluidRegistry.registerFluid(fluid)
    }

    private fun base(target: IFluidHandler?): IGregTechTileEntity {
        world = DebugTankTestWorld()
        dirtyCalls = 0
        return Proxy.newProxyInstance(
            IGregTechTileEntity::class.java.classLoader,
            arrayOf(IGregTechTileEntity::class.java),
        ) { _, method, _ ->
            when (method.name) {
                "isServerSide" -> {
                    true
                }
                "getFrontFacing" -> {
                    2.toByte()
                }
                "getBackFacing" -> {
                    3.toByte()
                }
                "getITankContainerAtSide" -> {
                    target
                }
                "getWorld" -> {
                    world
                }
                "getXCoord", "getZCoord" -> {
                    0
                }
                "setMetaTileEntity" -> {
                    null
                }
                "markDirty" -> {
                    dirtyCalls++
                    null
                }
                else -> {
                    throw AssertionError("Unexpected call: ${method.name}")
                }
            }
        } as IGregTechTileEntity
    }

    private fun tank(): GTMTE_DebugTank {
        val tank = GTMTE_DebugTank(
            "test",
            5,
            "",
            null,
        )
        tank.baseMetaTileEntity = base(null)
        return tank
    }

    public fun firstFillImmediatelyCreatesFullTankWithoutNeighbor() {
        val tank = tank()
        val input = FluidStack(
            fluid,
            1_000,
        )
        assertFalse(world.tankChunk.isModified)
        assertEquals(
            1_000,
            tank.fill(
                input,
                true,
            ),
        )
        assertEquals(
            1,
            dirtyCalls,
        )
        assertTrue(world.tankChunk.isModified)
        assertEquals(
            2_000_000_000,
            tank.fluidAmount,
        )
        assertEquals(
            1_000,
            input.amount,
        )
        tank.onPostTick(
            tank.baseMetaTileEntity,
            1L,
        )
        assertEquals(
            2_000_000_000,
            tank.fluidAmount,
        )
    }

    public fun simulatedFillDoesNotSelectFluid() {
        val tank = tank()
        assertEquals(
            1_000,
            tank.fill(
                FluidStack(
                    fluid,
                    1_000,
                ),
                false,
            ),
        )
        assertNull(tank.fluid)
        assertEquals(
            0,
            dirtyCalls,
        )
        assertFalse(world.tankChunk.isModified)
    }

    public fun repeatedFullDrainsDoNotExhaustOrChangeFluid() {
        val tank = tank()
        tank.fill(
            FluidStack(
                fluid,
                1_000,
            ),
            true,
        )
        repeat(3) {
            val drained = tank.drain(
                Int.MAX_VALUE,
                true,
            )
            assertEquals(
                2_000_000_000,
                drained.amount,
            )
            assertSame(
                fluid,
                drained.getFluid(),
            )
            drained.amount = 0
            assertEquals(
                2_000_000_000,
                tank.fluidAmount,
            )
        }
        assertEquals(
            123,
            tank.drain(
                123,
                false,
            ).amount,
        )
        assertNull(
            tank.drain(
                0,
                true,
            ),
        )
        assertNull(
            tank.drain(
                -1,
                true,
            ),
        )
    }

    public fun containerAssignmentImmediatelyFillsTankAndCopiesInput() {
        val tank = tank()
        val input = FluidStack(
            fluid,
            1_000,
        )
        tank.setFillableStack(input)
        assertEquals(
            2_000_000_000,
            tank.fluidAmount,
        )
        assertEquals(
            1_000,
            input.amount,
        )
    }

    public fun loadingOldPartiallyFilledTankRestoresFullAmount() {
        val nbt = NBTTagCompound()
        nbt.setTag(
            "mFluid",
            FluidStack(
                fluid,
                1_000,
            ).writeToNBT(NBTTagCompound()),
        )
        val tank = tank()
        tank.loadNBTData(nbt)
        assertEquals(
            2_000_000_000,
            tank.fluidAmount,
        )
        val saved = NBTTagCompound()
        tank.saveNBTData(saved)
        val restored = tank()
        restored.loadNBTData(saved)
        assertSame(
            fluid,
            restored.fluid.getFluid(),
        )
        assertEquals(
            2_000_000_000,
            restored.fluidAmount,
        )
    }

    public fun outputToAlwaysAcceptingNeighborIsBoundedPerTick() {
        var committed = 0
        val target = Proxy.newProxyInstance(
            IFluidHandler::class.java.classLoader,
            arrayOf(IFluidHandler::class.java),
        ) { _, method, arguments ->
            assertEquals(
                "fill",
                method.name,
            )
            if (arguments[2] as Boolean) {
                committed++
                assertEquals(
                    1,
                    committed,
                    "Only one transfer per tick",
                )
            }
            (arguments[1] as FluidStack).amount
        } as IFluidHandler
        val tank = tank()
        tank.baseMetaTileEntity = base(target)
        tank.fill(
            FluidStack(
                fluid,
                1_000,
            ),
            true,
        )
        tank.onPostTick(
            tank.baseMetaTileEntity,
            8L,
        )
        assertEquals(
            1,
            committed,
        )
        assertEquals(
            2_000_000_000,
            tank.fluidAmount,
        )
    }
}
