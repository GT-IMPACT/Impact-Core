package com.impact.mods.gregtech;

import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactBlocks;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.util.GT_Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import org.junit.jupiter.api.Test;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.alignment.enumerable.ExtendedFacing;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImpactStructureMigrationTest {
    @Test
    void casingDescriptorsAreLazyAndShapesPreserveCheckedPositions() throws Throwable {
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
    void registeredControllersUseNativeBaseAndProvideSurvivalWithoutClientClasses() throws Exception {
        try (URLClassLoader server = new URLClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs(),
                null) {
            @Override
            protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                if (name.startsWith("net.minecraft.client.") || name.startsWith("org.lwjgl.")) {
                    throw new ClassNotFoundException("Client dependency on server: " + name);
                }
                return super.loadClass(name, resolve);
            }
        };
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        getClass().getResourceAsStream("/structures/controllers.tsv"), StandardCharsets.UTF_8))) {
            Class<?> base = server.loadClass(GT_MetaTileEntity_EnhancedMultiBlockBase.class.getName());
            Class<?> survival = server.loadClass(ISurvivalConstructable.class.getName());
            String row;
            int controllers = 0;
            while ((row = reader.readLine()) != null) {
                String name = row.split("\t")[1];
                Class<?> controller = Class.forName(name, false, server);
                assertTrue(base.isAssignableFrom(controller), name);
                assertTrue(survival.isAssignableFrom(controller), name);
                controllers++;
            }
            assertEquals(68, controllers);
        }
    }

    public static class Scenarios {
        public static void check() throws Exception {
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

            for (ImpactCasings casing : ImpactCasings.values()) {
                assertNull(casing.getBlock(), casing.name());
                assertTrue(casing.getMetadata() >= 0 && casing.getMetadata() < 16);
            }
            ImpactBlocks.sCaseCore1 = Blocks.wool;
            ImpactBlocks.sCaseCore2 = Blocks.stained_hardened_clay;
            ImpactBlocks.sCaseCore3 = Blocks.stained_glass;
            ImpactBlocks.sCasePage8_3 = Blocks.stained_glass_pane;
            assertSame(Blocks.stained_hardened_clay, ImpactCasings.NUKE_TURBINE_CASING.getBlock());
            assertEquals(400, ImpactCasings.NUKE_TURBINE_CASING.getTextureId());
            assertEquals(1088 + 7, ImpactCasings.COMPUTER_CASING.getTextureId());

            GregTech_API.sBlockCasings1 = Blocks.wool;
            GregTech_API.sBlockCasings2 = Blocks.wool;
            GregTech_API.sBlockCasings3 = Blocks.wool;
            GregTech_API.sBlockCasings4 = Blocks.wool;
            GregTech_API.sBlockCasings5 = Blocks.wool;
            GregTech_API.sBlockCasings6 = Blocks.wool;
            GregTech_API.sBlockCasings7 = Blocks.wool;
            GregTech_API.sBlockCasings8 = Blocks.wool;
            GregTech_API.sBlockMachines = Blocks.iron_block;
            ItemRegistery.IGlassBlock = Blocks.glass;
            ItemRegistery.InsideBlock = Blocks.wool;
            ItemRegistery.SawMillBlock = Blocks.wool;
            ItemRegistery.MPSystem = Blocks.wool;
            ItemRegistery.MPTransducer = Blocks.iron_block;
            ItemRegistery.CollisionBlock = Blocks.bedrock;

            // Former validation coordinates, with the missing pyrolyse legs and supply bottom center restored.
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Scenarios.class.getResourceAsStream("/structures/checked-positions.tsv"),
                    StandardCharsets.UTF_8))) {
                String row;
                while ((row = reader.readLine()) != null) {
                    String[] fields = row.split("\t");
                    GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class
                            .forName(fields[0]).getConstructor(String.class).newInstance("structure-test");
                    int[] origin = point(fields[1]);
                    for (ForgeDirection direction : new ForgeDirection[] {
                            ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
                        Set<String> expected = new HashSet<>();
                        for (String coordinate : fields[2].split(";")) {
                            int[] p = point(coordinate);
                            int x = p[0], y = p[1], z = p[2];
                            switch (direction) {
                            case NORTH:
                                expected.add(key(-x, y, -z));
                                break;
                            case SOUTH:
                                expected.add(key(x, y, z));
                                break;
                            case WEST:
                                expected.add(key(-z, y, x));
                                break;
                            case EAST:
                                expected.add(key(z, y, -x));
                                break;
                            default:
                                throw new AssertionError(direction);
                            }
                        }
                        Set<String> actual = positions(machine, origin, direction);
                        assertEquals(expected, actual, fields[0] + " facing " + direction);
                    }
                }
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Scenarios.class.getResourceAsStream("/structures/controllers.tsv"), StandardCharsets.UTF_8))) {
                String row;
                while ((row = reader.readLine()) != null) {
                    Class<?> controller = Class.forName(row.split("\t")[1], false, Scenarios.class.getClassLoader());
                    assertTrue(controller.getMethod("survivalConstruct", ItemStack.class, int.class,
                            ISurvivalBuildEnvironment.class).getDeclaringClass() != ISurvivalConstructable.class,
                            controller.getName());
                }
            }
            ImpactConstructionChecks.check();
        }

        private static Set<String> positions(GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine, int[] origin,
                ForgeDirection direction) {
            Set<String> positions = new HashSet<>();
            int[] point = { -origin[0], -origin[1], -origin[2] };
            int[] world = new int[3];
            for (IStructureElement<?> element : machine.getStructureDefinition().getStructureFor("main")) {
                if (element.isNavigating()) {
                    point[0] = (element.resetA() ? -origin[0] : point[0]) + element.getStepA();
                    point[1] = (element.resetB() ? -origin[1] : point[1]) + element.getStepB();
                    point[2] = (element.resetC() ? -origin[2] : point[2]) + element.getStepC();
                } else {
                    ExtendedFacing.of(direction).getWorldOffset(point, world);
                    assertTrue(positions.add(key(world[0], world[1], world[2])), "Duplicate checked position");
                    point[0]++;
                }
            }
            return positions;
        }

        private static int[] point(String value) {
            String[] parts = value.split(",");
            return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]) };
        }

        private static String key(int x, int y, int z) {
            return x + "," + y + "," + z;
        }
    }
}
