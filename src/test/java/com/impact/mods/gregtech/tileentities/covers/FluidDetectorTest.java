package com.impact.mods.gregtech.tileentities.covers;

import com.impact.util.math.PackUnpackValues;
import cpw.mods.fml.common.Loader;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.init.Bootstrap;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FluidDetectorTest {
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
    void missingTanksDoNotCrashOrEraseSettings() throws Throwable { check("missingTanksDoNotCrashOrEraseSettings"); }

    @Test
    void blockWithoutFluidHandlerIsSafe() throws Throwable { check("blockWithoutFluidHandlerIsSafe"); }

    @Test
    void boundUsesAllTanksAndKeepsInvertedMode() throws Throwable { check("boundUsesAllTanksAndKeepsInvertedMode"); }

    @Test
    void rangeUsesCombinedCapacityAndInclusiveBounds() throws Throwable { check("rangeUsesCombinedCapacityAndInclusiveBounds"); }

    @Test
    void largeTankTotalsDoNotOverflow() throws Throwable { check("largeTankTotalsDoNotOverflow"); }

    @Test
    void clampedBoundUpdatesSignalImmediately() throws Throwable { check("clampedBoundUpdatesSignalImmediately"); }

    public static class Scenarios {
        private static final Fluid FLUID = new Fluid("fluid_detector_test_fluid");
        private byte signal;

        public void initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            FluidRegistry.registerFluid(FLUID);
        }

        private ICoverable tile(FluidTankInfo[] tanks, boolean fluidHandler) {
            return (ICoverable) Proxy.newProxyInstance(ICoverable.class.getClassLoader(),
                    fluidHandler ? new Class<?>[]{ICoverable.class, IFluidHandler.class} : new Class<?>[]{ICoverable.class},
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "getTankInfo":
                                assertSame(ForgeDirection.UNKNOWN, args[0]);
                                return tanks;
                            case "setOutputRedstoneSignal":
                                assertEquals((byte) 2, args[0]);
                                signal = (byte) args[1];
                                return null;
                            default: throw new AssertionError("Unexpected call: " + method.getName());
                        }
                    });
        }

        private FluidTankInfo tank(int capacity, int amount) {
            return new FluidTankInfo(amount == 0 ? null : new FluidStack(FLUID, amount), capacity);
        }

        private void tick(GT_CoverBehavior cover, ICoverable tile, int setting, byte expected) {
            signal = -1;
            assertEquals(setting, cover.doCoverThings((byte) 2, (byte) 0, 0, setting, tile, 20));
            assertEquals(expected, signal);
        }

        public void missingTanksDoNotCrashOrEraseSettings() {
            for (FluidTankInfo[] tanks : new FluidTankInfo[][]{
                    null, new FluidTankInfo[0], new FluidTankInfo[]{null}, new FluidTankInfo[]{tank(0, 0)}}) {
                ICoverable tile = tile(tanks, true);
                tick(new GTC_AdvFluidDetectorBound(), tile, 500, (byte) 0);
                tick(new GTC_AdvFluidDetectorBound(), tile, 0, (byte) 0);
                tick(new GTC_AdvFluidDetectorRange(), tile, PackUnpackValues.packFloats(0, 100), (byte) 0);
                assertEquals(1L, FluidDetectorTankData.read(tile).guiCapacity());
            }
        }

        public void blockWithoutFluidHandlerIsSafe() {
            ICoverable tile = tile(null, false);
            tick(new GTC_AdvFluidDetectorBound(), tile, 500, (byte) 0);
            tick(new GTC_AdvFluidDetectorRange(), tile, 0, (byte) 0);
            assertEquals(1L, FluidDetectorTankData.read(tile).guiCapacity());
        }

        public void boundUsesAllTanksAndKeepsInvertedMode() {
            ICoverable tile = tile(new FluidTankInfo[]{null, tank(1000, 400), tank(2000, 800)}, true);
            tick(new GTC_AdvFluidDetectorBound(), tile, 1000, (byte) 15);
            tick(new GTC_AdvFluidDetectorBound(), tile, 1201, (byte) 0);
            tick(new GTC_AdvFluidDetectorBound(), tile, -1200, (byte) 15);
            tick(new GTC_AdvFluidDetectorBound(), tile, -1199, (byte) 0);
            assertEquals(3000L, FluidDetectorTankData.read(tile).guiCapacity());
            tick(new GTC_AdvFluidDetectorBound(), tile(new FluidTankInfo[]{tank(1000, 0)}, true), 0, (byte) 15);
        }

        public void rangeUsesCombinedCapacityAndInclusiveBounds() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(1000, 0), tank(1000, 1000)}, true);
            tick(new GTC_AdvFluidDetectorRange(), tile, PackUnpackValues.packFloats(50, 50), (byte) 15);
            tick(new GTC_AdvFluidDetectorRange(), tile, PackUnpackValues.packFloats(51, 100), (byte) 0);
            tick(new GTC_AdvFluidDetectorRange(), tile, PackUnpackValues.packFloats(0, 49), (byte) 0);
            tick(new GTC_AdvFluidDetectorRange(), tile(new FluidTankInfo[]{tank(1000, 0)}, true), 0, (byte) 15);
        }

        public void largeTankTotalsDoNotOverflow() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(2_000_000_000, 1_500_000_000), tank(2_000_000_000, 1_500_000_000)}, true);
            assertEquals(4_000_000_000L, FluidDetectorTankData.read(tile).guiCapacity());
            tick(new GTC_AdvFluidDetectorBound(), tile, 2_000_000_000, (byte) 15);
            tick(new GTC_AdvFluidDetectorBound(), tile, Integer.MIN_VALUE, (byte) 0);
            tick(new GTC_AdvFluidDetectorBound(), tile(new FluidTankInfo[]{tank(1000, 500)}, true), Integer.MIN_VALUE, (byte) 15);
            tick(new GTC_AdvFluidDetectorRange(), tile, PackUnpackValues.packFloats(75, 75), (byte) 15);
        }

        public void clampedBoundUpdatesSignalImmediately() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(1000, 1000)}, true);
            signal = 0;
            assertEquals(1000, new GTC_AdvFluidDetectorBound().doCoverThings((byte) 2, (byte) 0, 0, 2000, tile, 20));
            assertEquals((byte) 15, signal);
        }
    }
}
