package com.impact.addon.waila;

import com.impact.common.te.TE_DryingRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.util.fluid.MultiFluidHandler;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Config;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.init.Bootstrap;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.common.config.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WailaIntegrationTest {
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
    void rackSynchronizesProgressWithoutSavingInventory() throws Throwable {
        check("rackSynchronizesProgressWithoutSavingInventory");
    }

    @Test
    void hatchKeepsInheritedAndImpactData() throws Throwable {
        check("hatchKeepsInheritedAndImpactData");
    }

    @Test
    void tankSynchronizesFluidsAndSkipsUnknownFluid() throws Throwable {
        check("tankSynchronizesFluidsAndSkipsUnknownFluid");
    }

    public static class Scenarios {
        public void initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            StringTranslate.inject(getClass().getResourceAsStream("/assets/impact/lang/en_US.lang"));
            GregTech_API.sMachineFile = new GT_Config(new Configuration() {
                @Override
                public void load() {}

                @Override
                public void save() {}
            });
        }

        private IWailaDataAccessor accessor(NBTTagCompound tag) {
            return (IWailaDataAccessor) Proxy.newProxyInstance(
                    IWailaDataAccessor.class.getClassLoader(), new Class<?>[]{IWailaDataAccessor.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("getNBTData")) return tag;
                        throw new AssertionError("Unexpected accessor call: " + method.getName());
                    });
        }

        private IWailaConfigHandler config() {
            return (IWailaConfigHandler) Proxy.newProxyInstance(
                    IWailaConfigHandler.class.getClassLoader(), new Class<?>[]{IWailaConfigHandler.class},
                    (proxy, method, args) -> false);
        }

        public void rackSynchronizesProgressWithoutSavingInventory() {
            TE_DryingRack rack = new TE_DryingRack() {
                @Override
                public void writeToNBT(NBTTagCompound tag) {
                    fail("Waila must not serialize the whole tile entity");
                }
            };
            rack.currentTime = 40;
            rack.maxTime = 200;
            ImpactPlugin provider = new ImpactPlugin();
            NBTTagCompound tag = new NBTTagCompound();
            assertSame(tag, provider.getNBTData(null, rack, tag, null, 0, 0, 0));
            List<String> tooltip = new ArrayList<>();
            provider.getWailaBody(null, tooltip, accessor(tag), config());
            assertEquals(Collections.singletonList("2 / 10 s"), tooltip);
            try {
                StringTranslate.inject(getClass().getResourceAsStream("/assets/impact/lang/ru_RU.lang"));
                tooltip.clear();
                provider.getWailaBody(null, tooltip, accessor(tag), config());
                assertEquals(Collections.singletonList("2 / 10 с"), tooltip);
            } finally {
                StringTranslate.inject(getClass().getResourceAsStream("/assets/impact/lang/en_US.lang"));
            }
            assertFalse(tag.hasKey("Items"));
        }

        public void hatchKeepsInheritedAndImpactData() {
            GTMTE_ParallelHatch_Input hatch = new GTMTE_ParallelHatch_Input("test", 5, new String[0], null, 4);
            hatch.idHatch = 7;
            hatch.machineName = "Test machine";
            hatch.address = "12:64:34";
            hatch.isConnected = true;
            NBTTagCompound tag = new NBTTagCompound();
            hatch.getWailaNBTData(null, null, tag, null, 0, 0, 0);
            assertEquals(7, tag.getInteger("hatchId"));
            assertTrue(tag.getBoolean("isParallelIN"));
            List<String> tooltip = new ArrayList<>();
            hatch.getWailaBody(null, tooltip, accessor(tag), config());
            assertTrue(tooltip.stream().anyMatch(line -> line.contains("ID: 7")));
            assertTrue(tooltip.contains("Test machine"));
            assertTrue(tooltip.stream().anyMatch(line -> line.contains("12:64:34")));
        }

        public void tankSynchronizesFluidsAndSkipsUnknownFluid() throws Exception {
            GTMTE_SingleTank tank = new GTMTE_SingleTank("test") {
                @Override
                public void run() {
                    // Structure registration requires a running mod environment.
                }
            };
            MultiFluidHandler fluids = new MultiFluidHandler(10000, 1);
            fluids.fluids.add(new FluidStack(FluidRegistry.WATER, 1234));
            Field storage = GTMTE_SingleTank.class.getDeclaredField("mfh");
            storage.setAccessible(true);
            storage.set(tank, fluids);
            NBTTagCompound tag = new NBTTagCompound();
            tank.getWailaNBTData(null, null, tag, null, 0, 0, 0);
            assertEquals(10000, tag.getInteger("capacityPerFluid"));
            assertEquals(1234, tag.getCompoundTag("fluids").getCompoundTag("0").getInteger("Amount"));
            assertFalse(tag.getCompoundTag("fluids").hasKey("filter"));
            List<String> tooltip = new ArrayList<>();
            tank.getWailaBody(null, tooltip, accessor(tag), config());
            assertEquals(1, tooltip.size());
            tag.getCompoundTag("fluids").getCompoundTag("0").setString("FluidName", "missing_fluid");
            tooltip.clear();
            tank.getWailaBody(null, tooltip, accessor(tag), config());
            assertTrue(tooltip.isEmpty());
        }
    }
}
