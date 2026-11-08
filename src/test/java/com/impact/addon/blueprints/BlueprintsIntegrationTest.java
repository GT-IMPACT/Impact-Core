package com.impact.addon.blueprints;

import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.common.blocks.GT_Item_Machines;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.junit.jupiter.api.Test;
import space.impact.iblueprints.api.SceneAlternativeGroups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BlueprintsIntegrationTest {

    @Test
    void hatchAlternativesUseDedicatedGroups() throws Throwable {
        try (LaunchClassLoader loader = new LaunchClassLoader(
                ((URLClassLoader) getClass().getClassLoader()).getURLs())) {
            loader.addClassLoaderExclusion("org.junit.");
            try {
                loader.loadClass(getClass().getName() + "$Scenarios").getMethod("check").invoke(null);
            } catch (InvocationTargetException failure) {
                throw failure.getTargetException();
            }
        }
    }

    @Test
    void integrationClassLoadsWithoutBlueprints() throws Exception {
        try (URLClassLoader loader = new URLClassLoader(
                ((URLClassLoader) getClass().getClassLoader()).getURLs(), null) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (name.startsWith("space.impact.iblueprints.")) {
                    throw new ClassNotFoundException(name);
                }
                return super.loadClass(name, resolve);
            }
        }) {
            assertNotNull(Class.forName(BlueprintsIntegration.class.getName(), true, loader));
        }
    }

    public static class Scenarios {

        public static void check() throws Exception {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Field namedMods = Loader.class.getDeclaredField("namedMods");
            namedMods.setAccessible(true);
            namedMods.set(Loader.instance(), Collections.emptyMap());
            Bootstrap.func_151354_b();
            BlueprintsIntegration.register();
            Method group = Arrays.stream(SceneAlternativeGroups.class.getDeclaredMethods())
                    .filter(method -> method.getName().startsWith("group$"))
                    .findFirst().orElseThrow(() -> new AssertionError("Blueprints grouping method is unavailable"));
            Item machineItem = new GT_Item_Machines(Blocks.iron_block);
            checkEnergyGroups(group, machineItem);
            assertGroup(
                    group,
                    machineItem,
                    new GTMTE_ComputerRack("rack", new String[0], null),
                    "impact.blueprints.group.racks"
            );
            for (int tier = 5; tier <= 8; tier++) {
                int capacity = 1 << (2 * (tier - 4));
                assertGroup(
                        group,
                        machineItem,
                        new GTMTE_ParallelHatch_Output("transmitter", tier, new String[0], null, capacity),
                        "impact.blueprints.group.parallel_transmitters"
                );
                assertGroup(
                        group,
                        machineItem,
                        new GTMTE_ParallelHatch_Input("receiver", tier, new String[0], null, capacity),
                        "impact.blueprints.group.parallel_receivers"
                );
            }
            assertEquals(
                    "iblueprints.variants.group.other",
                    group.invoke(SceneAlternativeGroups.INSTANCE, new ItemStack(Items.stick))
            );
        }

        private static void checkEnergyGroups(Method group, Item machineItem) throws Exception {
            for (int tier : new int[] { 5, 8 }) {
                assertGroup(
                        group,
                        machineItem,
                        new GT_MetaTileEntity_Hatch_Energy("energy", tier, "", null),
                        "impact.blueprints.group.energy_input"
                );
                assertGroup(
                        group,
                        machineItem,
                        new GT_MetaTileEntity_Hatch_Dynamo("dynamo", tier, "", null),
                        "impact.blueprints.group.energy_output"
                );
                for (int amperage : new int[] { 2, 4, 16, 64 }) {
                    assertGroup(
                            group,
                            machineItem,
                            new GT_MetaTileEntity_Hatch_EnergyMulti("multi-energy", tier, amperage, "", null),
                            "impact.blueprints.group.multi_energy_input." + amperage
                    );
                    assertGroup(
                            group,
                            machineItem,
                            new GT_MetaTileEntity_Hatch_DynamoMulti("multi-dynamo", tier, amperage, "", null),
                            "impact.blueprints.group.multi_energy_output." + amperage
                    );
                }
                for (int amperage : new int[] { 256, 1024, 4096, 16384, 65536, 262144, 1048576 }) {
                    assertGroup(
                            group,
                            machineItem,
                            new GTMTE_LaserEnergy_In("laser-input", tier, amperage, "", null),
                            "impact.blueprints.group.laser_energy_input." + amperage
                    );
                    assertGroup(
                            group,
                            machineItem,
                            new GTMTE_LaserEnergy_Out("laser-output", tier, amperage, "", null),
                            "impact.blueprints.group.laser_energy_output." + amperage
                    );
                }
            }
        }

        private static void assertGroup(Method group, Item machineItem, IMetaTileEntity hatch, String expected)
                throws Exception {
            int id = 31950;
            IMetaTileEntity previous = GregTech_API.METATILEENTITIES[id];
            try {
                GregTech_API.METATILEENTITIES[id] = hatch;
                assertEquals(expected, group.invoke(SceneAlternativeGroups.INSTANCE, new ItemStack(machineItem, 1, id)));
            } finally {
                GregTech_API.METATILEENTITIES[id] = previous;
            }
        }
    }
}
