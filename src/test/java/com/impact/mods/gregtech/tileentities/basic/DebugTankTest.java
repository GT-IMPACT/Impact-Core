package com.impact.mods.gregtech.tileentities.basic;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.init.Bootstrap;
import cpw.mods.fml.common.Loader;
import java.io.File;
import java.net.URLClassLoader;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DebugTankTest {
    private LaunchClassLoader loader;
    private Object scenarios;

    @BeforeAll
    void initialize() throws Exception {
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        Class<?> scenarioClass = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios = scenarioClass.getConstructor().newInstance();
        scenarioClass.getMethod("initialize").invoke(scenarios);
    }

    @AfterAll
    void closeLoader() throws Exception {
        if (loader != null) loader.close();
    }

    private void check(String method) throws Throwable {
        try {
            scenarios.getClass().getMethod(method).invoke(scenarios);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        }
    }

    @Test
    void firstFillImmediatelyCreatesFullTankWithoutNeighbor() throws Throwable { check("firstFillImmediatelyCreatesFullTankWithoutNeighbor"); }

    @Test
    void simulatedFillDoesNotSelectFluid() throws Throwable { check("simulatedFillDoesNotSelectFluid"); }

    @Test
    void repeatedFullDrainsDoNotExhaustOrChangeFluid() throws Throwable { check("repeatedFullDrainsDoNotExhaustOrChangeFluid"); }

    @Test
    void containerAssignmentImmediatelyFillsTankAndCopiesInput() throws Throwable { check("containerAssignmentImmediatelyFillsTankAndCopiesInput"); }

    @Test
    void loadingOldPartiallyFilledTankRestoresFullAmount() throws Throwable { check("loadingOldPartiallyFilledTankRestoresFullAmount"); }

    @Test
    void outputToAlwaysAcceptingNeighborIsBoundedPerTick() throws Throwable { check("outputToAlwaysAcceptingNeighborIsBoundedPerTick"); }

    public static class Scenarios {
        private static final Fluid FLUID = new Fluid("debug_tank_test_fluid");

        public void initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), java.util.Collections.emptyList());
            Bootstrap.func_151354_b();
            FluidRegistry.registerFluid(FLUID);
        }

        private IGregTechTileEntity base(IFluidHandler target) {
            return (IGregTechTileEntity) Proxy.newProxyInstance(
                    IGregTechTileEntity.class.getClassLoader(), new Class<?>[]{IGregTechTileEntity.class},
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "isServerSide": return true;
                            case "getFrontFacing": return (byte) 2;
                            case "getBackFacing": return (byte) 3;
                            case "getITankContainerAtSide": return target;
                            case "setMetaTileEntity":
                            case "markDirty": return null;
                            default: throw new AssertionError("Unexpected call: " + method.getName());
                        }
                    });
        }

        private GTMTE_DebugTank tank() {
            GTMTE_DebugTank tank = new GTMTE_DebugTank("test", 5, "", null);
            tank.setBaseMetaTileEntity(base(null));
            return tank;
        }

        public void firstFillImmediatelyCreatesFullTankWithoutNeighbor() {
            GTMTE_DebugTank tank = tank();
            FluidStack input = new FluidStack(FLUID, 1_000);
            assertEquals(1_000, tank.fill(input, true));
            assertEquals(2_000_000_000, tank.getFluidAmount());
            assertEquals(1_000, input.amount);
            tank.onPostTick(tank.getBaseMetaTileEntity(), 1);
            assertEquals(2_000_000_000, tank.getFluidAmount());
        }

        public void simulatedFillDoesNotSelectFluid() {
            GTMTE_DebugTank tank = tank();
            assertEquals(1_000, tank.fill(new FluidStack(FLUID, 1_000), false));
            assertNull(tank.getFluid());
        }

        public void repeatedFullDrainsDoNotExhaustOrChangeFluid() {
            GTMTE_DebugTank tank = tank();
            tank.fill(new FluidStack(FLUID, 1_000), true);
            for (int i = 0; i < 3; i++) {
                FluidStack drained = tank.drain(Integer.MAX_VALUE, true);
                assertEquals(2_000_000_000, drained.amount);
                assertSame(FLUID, drained.getFluid());
                drained.amount = 0;
                assertEquals(2_000_000_000, tank.getFluidAmount());
            }
            assertEquals(123, tank.drain(123, false).amount);
            assertNull(tank.drain(0, true));
            assertNull(tank.drain(-1, true));
        }

        public void containerAssignmentImmediatelyFillsTankAndCopiesInput() {
            GTMTE_DebugTank tank = tank();
            FluidStack input = new FluidStack(FLUID, 1_000);
            tank.setFillableStack(input);
            assertEquals(2_000_000_000, tank.getFluidAmount());
            assertEquals(1_000, input.amount);
        }

        public void loadingOldPartiallyFilledTankRestoresFullAmount() {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setTag("mFluid", new FluidStack(FLUID, 1_000).writeToNBT(new NBTTagCompound()));
            GTMTE_DebugTank tank = tank();
            tank.loadNBTData(nbt);
            assertEquals(2_000_000_000, tank.getFluidAmount());
            NBTTagCompound saved = new NBTTagCompound();
            tank.saveNBTData(saved);
            GTMTE_DebugTank restored = tank();
            restored.loadNBTData(saved);
            assertSame(FLUID, restored.getFluid().getFluid());
            assertEquals(2_000_000_000, restored.getFluidAmount());
        }

        public void outputToAlwaysAcceptingNeighborIsBoundedPerTick() {
            int[] committed = {0};
            IFluidHandler target = (IFluidHandler) Proxy.newProxyInstance(
                    IFluidHandler.class.getClassLoader(), new Class<?>[]{IFluidHandler.class},
                    (proxy, method, args) -> {
                        assertEquals("fill", method.getName());
                        if ((boolean) args[2]) {
                            assertEquals(1, ++committed[0], "Only one transfer per tick");
                        }
                        return ((FluidStack) args[1]).amount;
                    });
            GTMTE_DebugTank tank = tank();
            tank.setBaseMetaTileEntity(base(target));
            tank.fill(new FluidStack(FLUID, 1_000), true);
            tank.onPostTick(tank.getBaseMetaTileEntity(), 8);
            assertEquals(1, committed[0]);
            assertEquals(2_000_000_000, tank.getFluidAmount());
        }
    }
}
