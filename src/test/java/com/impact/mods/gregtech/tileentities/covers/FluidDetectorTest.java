package com.impact.mods.gregtech.tileentities.covers;

import com.impact.util.math.PackUnpackValues;
import cpw.mods.fml.common.Loader;
import gregtech.api.covers.CoverContext;
import gregtech.api.covers.CoverFactory;
import gregtech.api.interfaces.tileentity.ICoverable;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.Collections;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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
        if (loader != null) {
            loader.close();
        }
    }

    private void check(String method) throws Throwable {
        try {
            scenarios.getClass().getMethod(method).invoke(scenarios);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        }
    }

    @Test
    void missingTanksDoNotCrashOrEraseSettings() throws Throwable {
        check("missingTanksDoNotCrashOrEraseSettings");
    }

    @Test
    void blockWithoutFluidHandlerIsSafe() throws Throwable {
        check("blockWithoutFluidHandlerIsSafe");
    }

    @Test
    void boundUsesAllTanksAndKeepsInvertedMode() throws Throwable {
        check("boundUsesAllTanksAndKeepsInvertedMode");
    }

    @Test
    void rangeUsesCombinedCapacityAndInclusiveBounds() throws Throwable {
        check("rangeUsesCombinedCapacityAndInclusiveBounds");
    }

    @Test
    void largeTankTotalsDoNotOverflow() throws Throwable {
        check("largeTankTotalsDoNotOverflow");
    }

    @Test
    void clampedBoundUpdatesSignalImmediately() throws Throwable {
        check("clampedBoundUpdatesSignalImmediately");
    }

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

        private void tick(CoverFactory factory, ICoverable tile, int setting, byte expected) {
            signal = -1;
            DetectorCover cover = (DetectorCover) factory.buildCover(new CoverContext(1, ForgeDirection.NORTH, tile));
            cover.setLegacyData(setting);
            cover.doCoverThings((byte) 0, 20);
            assertEquals(setting, cover.getLegacyData());
            assertEquals(expected, signal);
        }

        public void missingTanksDoNotCrashOrEraseSettings() {
            for (FluidTankInfo[] tanks : new FluidTankInfo[][]{
                    null, new FluidTankInfo[0], new FluidTankInfo[]{null}, new FluidTankInfo[]{tank(0, 0)}}) {
                ICoverable tile = tile(tanks, true);
                tick(GTC_AdvFluidDetectorBound::new, tile, 500, (byte) 0);
                tick(GTC_AdvFluidDetectorBound::new, tile, 0, (byte) 0);
                tick(GTC_AdvFluidDetectorRange::new, tile, PackUnpackValues.packFloats(0, 100), (byte) 0);
                assertEquals(1L, FluidDetectorTankData.read(tile).guiCapacity());
            }
        }

        public void blockWithoutFluidHandlerIsSafe() {
            ICoverable tile = tile(null, false);
            tick(GTC_AdvFluidDetectorBound::new, tile, 500, (byte) 0);
            tick(GTC_AdvFluidDetectorRange::new, tile, 0, (byte) 0);
            assertEquals(1L, FluidDetectorTankData.read(tile).guiCapacity());
        }

        public void boundUsesAllTanksAndKeepsInvertedMode() {
            ICoverable tile = tile(new FluidTankInfo[]{null, tank(1000, 400), tank(2000, 800)}, true);
            tick(GTC_AdvFluidDetectorBound::new, tile, 1000, (byte) 15);
            tick(GTC_AdvFluidDetectorBound::new, tile, 1201, (byte) 0);
            tick(GTC_AdvFluidDetectorBound::new, tile, -1200, (byte) 15);
            tick(GTC_AdvFluidDetectorBound::new, tile, -1199, (byte) 0);
            assertEquals(3000L, FluidDetectorTankData.read(tile).guiCapacity());
            tick(GTC_AdvFluidDetectorBound::new, tile(new FluidTankInfo[]{tank(1000, 0)}, true), 0, (byte) 15);
        }

        public void rangeUsesCombinedCapacityAndInclusiveBounds() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(1000, 0), tank(1000, 1000)}, true);
            tick(GTC_AdvFluidDetectorRange::new, tile, PackUnpackValues.packFloats(50, 50), (byte) 15);
            tick(GTC_AdvFluidDetectorRange::new, tile, PackUnpackValues.packFloats(51, 100), (byte) 0);
            tick(GTC_AdvFluidDetectorRange::new, tile, PackUnpackValues.packFloats(0, 49), (byte) 0);
            tick(GTC_AdvFluidDetectorRange::new, tile(new FluidTankInfo[]{tank(1000, 0)}, true), 0, (byte) 15);
        }

        public void largeTankTotalsDoNotOverflow() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(2_000_000_000, 1_500_000_000), tank(2_000_000_000, 1_500_000_000)}, true);
            assertEquals(4_000_000_000L, FluidDetectorTankData.read(tile).guiCapacity());
            tick(GTC_AdvFluidDetectorBound::new, tile, 2_000_000_000, (byte) 15);
            tick(GTC_AdvFluidDetectorBound::new, tile, Integer.MIN_VALUE, (byte) 0);
            tick(GTC_AdvFluidDetectorBound::new, tile(new FluidTankInfo[]{tank(1000, 500)}, true), Integer.MIN_VALUE, (byte) 15);
            tick(GTC_AdvFluidDetectorRange::new, tile, PackUnpackValues.packFloats(75, 75), (byte) 15);
        }

        public void clampedBoundUpdatesSignalImmediately() {
            ICoverable tile = tile(new FluidTankInfo[]{tank(1000, 1000)}, true);
            signal = 0;
            GTC_AdvFluidDetectorBound cover = new GTC_AdvFluidDetectorBound(
                new CoverContext(1, ForgeDirection.NORTH, tile));
            cover.setLegacyData(2000);
            cover.doCoverThings((byte) 0, 20);
            assertEquals(1000, cover.getLegacyData());
            assertEquals((byte) 15, signal);
        }
    }
}
