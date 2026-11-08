package com.impact.mods.gregtech.tileentities.covers;

import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.util.math.PackUnpackValues;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.covers.CoverContext;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Config;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.Collections;
import net.minecraft.init.Bootstrap;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnergyDetectorTest {

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
    void fullRangeDetectsSupercapacitorChargeFromReport() throws Throwable {
        check("fullRangeDetectsSupercapacitorChargeFromReport");
    }

    @Test
    void largeRangeKeepsBothBoundsInclusive() throws Throwable {
        check("largeRangeKeepsBothBoundsInclusive");
    }

    @Test
    void smallRangeKeepsExistingBehavior() throws Throwable {
        check("smallRangeKeepsExistingBehavior");
    }

    public static class Scenarios {

        private byte signal;

        public void initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            GregTech_API.sMachineFile = new GT_Config(new Configuration() {
                @Override
                public void load() {
                }

                @Override
                public void save() {
                }
            });
        }

        private void tick(
            long capacity,
            long stored,
            int startPercent,
            int endPercent,
            byte expected
        ) {
            GTMTE_LapPowerStation station = new GTMTE_LapPowerStation("energy_detector_test");
            station.capacity = capacity;
            station.stored = stored;
            IGregTechTileEntity tile = (IGregTechTileEntity) Proxy.newProxyInstance(
                IGregTechTileEntity.class.getClassLoader(),
                new Class<?>[]{IGregTechTileEntity.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getMetaTileEntity":
                            return station;
                        case "setOutputRedstoneSignal":
                            assertEquals((byte) ForgeDirection.NORTH.ordinal(), args[0]);
                            signal = (byte) args[1];
                            return null;
                        default:
                            throw new AssertionError("Unexpected call: " + method.getName());
                    }
                }
            );
            GTC_AdvEUDetectorRange cover = new GTC_AdvEUDetectorRange(
                new CoverContext(1, ForgeDirection.NORTH, tile));
            int setting = PackUnpackValues.packFloats(startPercent, endPercent);
            cover.setLegacyData(setting);
            assertEquals(capacity, cover.getCapacity());
            signal = -1;
            cover.doCoverThings((byte) 0, 20);
            assertEquals(expected, signal);
            assertEquals(setting, cover.getLegacyData());
        }

        public void fullRangeDetectsSupercapacitorChargeFromReport() {
            tick(108_000_000_000_000L, 100_880_914_235_392L, 0, 100, (byte) 15);
            tick(108_000_000_000_000L, 0, 0, 100, (byte) 15);
            tick(108_000_000_000_000L, 108_000_000_000_000L, 0, 100, (byte) 15);
        }

        public void largeRangeKeepsBothBoundsInclusive() {
            long capacity = 108_000_000_000_000L;
            tick(capacity, 27_000_000_000_000L - 1, 25, 75, (byte) 0);
            tick(capacity, 27_000_000_000_000L, 25, 75, (byte) 15);
            tick(capacity, 81_000_000_000_000L, 25, 75, (byte) 15);
            tick(capacity, 81_000_000_000_000L + 1, 25, 75, (byte) 0);
            tick(capacity, 54_000_000_000_000L, 50, 50, (byte) 15);
        }

        public void smallRangeKeepsExistingBehavior() {
            tick(1000, 249, 25, 75, (byte) 0);
            tick(1000, 250, 25, 75, (byte) 15);
            tick(1000, 750, 25, 75, (byte) 15);
            tick(1000, 751, 25, 75, (byte) 0);
        }
    }
}
