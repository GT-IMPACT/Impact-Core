package com.impact.mods.gregtech;

import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.te.TE_NqTether;
import com.impact.common.te.TE_SpaceElevatorTether;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator;
import com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_ParametricDiffuser;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_CokeOven;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_Pyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_TheMill;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedPyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Assembler;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_FlotationUnit;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import com.impact.util.fluid.IMultiFluidTank;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HeatingCoil;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;
import gregtech.api.util.GTStructureUtility;
import ic2.core.Ic2Items;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.CommonProxy;
import space.impact.structures.Main;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.alignment.enumerable.ExtendedFacing;
import space.impact.structures.hint.HintColor;
import space.impact.structures.hint.HintMarkerKind;
import space.impact.structures.structure.IItemSource;
import space.impact.structures.structure.ISurvivalBuildEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImpactConstructionChecks {
    public static void check() throws Exception {
        Field side = FMLRelaunchLog.class.getDeclaredField("side");
        side.setAccessible(true);
        side.set(null, Side.SERVER);
        Launch.classLoader = (LaunchClassLoader) ImpactConstructionChecks.class.getClassLoader();
        Launch.blackboard = new HashMap<>();
        Launch.blackboard.put("fml.deobfuscatedEnvironment", false);
        Field proxy = Main.class.getDeclaredField("proxy");
        proxy.setAccessible(true);
        HintRecorder hints = new HintRecorder();
        proxy.set(null, hints);
        checkFlotationHints(hints);
        checkPyrolyseLegs(hints);
        checkAssemblerHatches(hints);
        ItemRegistery.FluidTankBlock = Blocks.stained_glass;
        ItemRegistery.lscLapotronicEnergyUnit = Blocks.stained_glass;
        checkDiffuser();
        checkCokeOven();
        checkTank();
        checkTankDirections(hints);
        checkNaquadahBeams();
        checkSpaceElevatorBeam();
        checkParallelComputerLength(hints);
        checkCapacitors();
        checkSurvival();
        assertEquals(5, new GTMTE_TheMill("mill-test").mInventory.length);
        assertEquals(2, new GTMTE_CokeOven("coke-test").mInventory.length);
        checkAllControllerHints(hints);
    }

    @SuppressWarnings("unchecked")
    private static void checkAllControllerHints(HintRecorder hints) throws Exception {
        for (Class<?> registry : new Class<?>[] { ItemRegistery.class, GregTech_API.class }) {
            for (Field field : registry.getFields()) {
                if (field.getType() == Block.class && Modifier.isStatic(field.getModifiers())
                        && !Modifier.isFinal(field.getModifiers()) && field.get(null) == null) {
                    field.set(null, Blocks.stone);
                }
            }
        }
        Ic2Items.reinforcedGlass = new ItemStack(Blocks.glass);
        Field coolantBlocks = BlocksItems.class.getDeclaredField("fluidBlocks");
        coolantBlocks.setAccessible(true);
        ((Map<InternalName, Block>) coolantBlocks.get(null)).put(InternalName.fluidCoolant, Blocks.water);
        Field hintBlock = Main.class.getDeclaredField("blockHint");
        hintBlock.setAccessible(true);
        Block coloredHint = new Block(Material.rock) {
        };
        hintBlock.set(null, coloredHint);
        List<String> controllers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                ImpactConstructionChecks.class.getResourceAsStream("/structures/controllers.tsv"),
                StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                controllers.add(line.split("\t")[1]);
            }
        }
        // The oil rig is currently unregistered, but its hologram must follow the same
        // contract.
        controllers.add("com.impact.mods.gregtech.tileentities.multi.ores.GTMTEOilDrilling");
        List<AssertionError> failures = new ArrayList<>();
        for (String name : controllers) {
            try {
                Class<?> type = Class.forName(name);
                GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine;
                if (name.contains("GTMTE_SOFC_")) {
                    int tier = name.substring(name.lastIndexOf('_') + 1).length() - 1;
                    machine = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) type.getConstructor(String.class, int.class)
                            .newInstance(name, tier);
                } else {
                    machine = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) type.getConstructor(String.class)
                            .newInstance(name);
                }
                int orientations = 0;
                for (ExtendedFacing facing : ExtendedFacing.VALUES) {
                    if (!machine.isNewExtendedFacingValid(facing)) {
                        continue;
                    }
                    orientations++;
                    ForgeDirection direction = facing.getDirection();
                    TestWorld world = new TestWorld();
                    world.putTile(0, 64, 0, new TestTile(world, machine, direction));
                    world.isRemote = true;
                    machine.setExtendedFacing(facing);
                    assertEquals(facing, machine.getExtendedFacing());
                    int changes = world.changes;
                    for (int size : new int[] { 1, 64 }) {
                        hints.clear();
                        ItemStack trigger = new ItemStack(Items.stick, size);
                        machine.construct(trigger, true);
                        assertFalse(hints.positions.isEmpty(), name);
                        assertFalse(hints.positions.contains(TestWorld.key(0, 64, 0)),
                                "The hologram covers its controller: " + name);
                        Set<HintColor> usedColors = new HashSet<>(hints.frames.values());
                        hints.blocks.forEach((position, block) -> {
                            if (block == coloredHint) {
                                assertFalse(hints.frames.containsKey(position), "Dedicated hatch must stay a marker");
                                usedColors.add(HintColor.fromMetadata(hints.metadata.get(position)));
                            }
                        });
                        if (machine instanceof GTMTE_TheMill) {
                            assertEquals(Collections.singleton(HintColor.GREEN), usedColors);
                            assertTrue(hints.frames.isEmpty(), "The rotor is a dedicated component, not a hatch");
                            String rotor = TestWorld.key(direction.offsetX, 64 + direction.offsetY, direction.offsetZ);
                            assertSame(coloredHint, hints.blocks.get(rotor), "The rotor needs a marker, not a block icon");
                            assertEquals(HintColor.GREEN, HintColor.fromMetadata(hints.metadata.get(rotor)));
                        } else {
                            assertFalse(usedColors.isEmpty(), "Missing hatch hints: " + name);
                        }
                        String legend = String.join("\n", machine.getStructureDescription(trigger));
                        for (HintColor color : usedColors) {
                            assertTrue(legend.contains(GTStructureUtility.formatHintText(color, "■")),
                                    "Missing legend for " + color + ": " + name);
                        }
                        Map<String, Block> beforeBlocks = new HashMap<>(hints.blocks);
                        Map<String, Integer> beforeMetadata = new HashMap<>(hints.metadata);
                        Map<String, HintColor> beforeFrames = new HashMap<>(hints.frames);
                        hints.clear();
                        machine.construct(trigger, true);
                        assertEquals(beforeBlocks, hints.blocks, name);
                        assertEquals(beforeMetadata, hints.metadata, name);
                        assertEquals(beforeFrames, hints.frames, name);
                        assertEquals(changes, world.changes, "Hologram modified world: " + name);
                    }
                }
                assertTrue(orientations > 0, "No allowed orientations: " + name);
            } catch (Throwable failure) {
                failures.add(new AssertionError("Hologram audit: " + name, failure));
            }
        }
        if (!failures.isEmpty()) {
            AssertionError failure = new AssertionError("Failed holograms: " + failures.size());
            failures.forEach(failure::addSuppressed);
            throw failure;
        }
        assertEquals(69, controllers.size());
    }

    private static void checkFlotationHints(HintRecorder hints) {
        for (ForgeDirection direction : new ForgeDirection[] {
                ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
            TestWorld world = new TestWorld();
            GTMTE_FlotationUnit machine = new GTMTE_FlotationUnit("flotation-hints");
            world.putTile(0, 64, 0, new TestTile(world, machine, direction));
            machine.mMaintenanceHatches.add(new GT_MetaTileEntity_Hatch_Maintenance(
                    "maintenance", 1, "", new ITexture[0][][], false));
            world.isRemote = true;
            int changes = world.changes;
            for (int repeat = 0; repeat < 2; repeat++) {
                hints.clear();
                machine.construct(new ItemStack(Items.stick), true);
                assertEquals(33, hints.blocks.size(), direction.name());
                assertEquals(17, hints.frames.size(), "All casing/hatch positions need a frame");
                assertFalse(hints.blocks.containsKey(TestWorld.key(0, 64, 0)), "Do not cover the controller");
                int pipes = 0, coils = 0;
                for (String position : hints.blocks.keySet()) {
                    if (hints.frames.containsKey(position)) {
                        assertSame(Casings.STABLE_TITANIUM.getBlock(), hints.blocks.get(position));
                        assertEquals(Integer.valueOf(Casings.STABLE_TITANIUM.getMetadata()),
                                hints.metadata.get(position));
                        assertEquals(HintColor.RED, hints.frames.get(position));
                    } else if (hints.metadata.get(position) == Casings.TITANIUM_PIPE.getMetadata()) {
                        assertSame(Casings.TITANIUM_PIPE.getBlock(), hints.blocks.get(position));
                        pipes++;
                    } else {
                        assertSame(HeatingCoil.NICHROME.getBlock(), hints.blocks.get(position));
                        assertEquals(Integer.valueOf(HeatingCoil.NICHROME.getMetadata()), hints.metadata.get(position));
                        coils++;
                    }
                }
                assertEquals(8, pipes);
                assertEquals(8, coils);
                assertEquals(changes, world.changes, "Hologram must not modify blocks");
                assertEquals(1, machine.mMaintenanceHatches.size(), "Hologram must not rebuild hatch lists");
            }
        }
    }

    private static void checkPyrolyseLegs(HintRecorder hints) {
        for (boolean advanced : new boolean[] { false, true }) {
            for (ForgeDirection direction : new ForgeDirection[] {
                    ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
                TestWorld world = new TestWorld();
                GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = advanced
                        ? new GTMTE_AdvancedPyrolyse("advanced-pyrolyse-legs")
                        : new GTMTE_Pyrolyse("pyrolyse-legs");
                world.putTile(0, 64, 0, new TestTile(world, machine, direction));
                world.isRemote = true;
                Set<String> expected = new HashSet<>();
                for (int x : new int[] { 0, 2, 4, 8 }) {
                    for (int depth : new int[] { 0, 2 }) {
                        int[] offset = new int[3];
                        ExtendedFacing.of(direction).getWorldOffset(new int[] { x, 1, depth }, offset);
                        expected.add(TestWorld.key(offset[0], 64 + offset[1], offset[2]));
                    }
                }
                int changes = world.changes;
                for (int repeat = 0; repeat < 2; repeat++) {
                    hints.clear();
                    machine.construct(new ItemStack(Items.stick), true);
                    assertEquals(expected, hints.tintedPositions, machine.getClass().getSimpleName() + " " + direction);
                    assertEquals(changes, world.changes, "Showing frame legs must not change the world");
                }
            }
        }
    }

    private static void checkAssemblerHatches(HintRecorder hints) {
        for (ForgeDirection direction : new ForgeDirection[] {
                ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
            TestWorld world = new TestWorld();
            Assembler machine = new Assembler();
            world.putTile(0, 64, 0, new TestTile(world, machine, direction));
            world.isRemote = true;
            hints.clear();
            machine.construct(new ItemStack(Items.stick), true);
            List<String> casings = new ArrayList<>(hints.frames.keySet());
            assertEquals(61, casings.size(), "Every assembler casing must show the combined hint");
            assertTrue(hints.frames.values().stream().allMatch(color -> color == HintColor.GREEN));
            world.isRemote = false;
            machine.construct(new ItemStack(Items.stick), false);
            for (String position : casings) {
                String maintenance = casings.get(position.equals(casings.get(0)) ? 1 : 0);
                putHatch(world, maintenance, new GT_MetaTileEntity_Hatch_Maintenance(
                        "maintenance", 1, "", new ITexture[0][][], false));
                for (MetaTileEntity hatch : new MetaTileEntity[] {
                        new GTMTE_ParallelHatch_Input("parallel", 5, new String[0], new ITexture[0][][], 256),
                        new GT_MetaTileEntity_Hatch_Energy("energy", 4, "", new ITexture[0][][]) }) {
                    putHatch(world, position, hatch);
                    assertTrue(machine.recheck(), direction + " hatch at " + position);
                    assertTrue(machine.recheck(), "Repeated checks must not accumulate hatches");
                }
                for (String restore : new String[] { position, maintenance }) {
                    String[] coordinates = restore.split(":");
                    world.setBlock(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]),
                            Integer.parseInt(coordinates[2]), ImpactCasings.ASSEMBLER_CASING.getBlock(),
                            ImpactCasings.ASSEMBLER_CASING.getMetadata(), 0);
                }
            }
        }
    }

    private static class Assembler extends GTMTE_Assembler {
        Assembler() {
            super("assembler-test");
        }

        boolean recheck() {
            setupMachine(getBaseMetaTileEntity(), null);
            return mMachine;
        }
    }

    private static class HintRecorder extends CommonProxy {
        final Set<String> positions = new HashSet<>();
        final Set<String> tintedPositions = new HashSet<>();
        final Map<String, Block> blocks = new HashMap<>();
        final Map<String, Integer> metadata = new HashMap<>();
        final Map<String, HintColor> frames = new HashMap<>();

        void clear() {
            positions.clear();
            tintedPositions.clear();
            blocks.clear();
            metadata.clear();
            frames.clear();
        }

        @Override
        public void hintParticle(World world, int x, int y, int z, Block block, int meta) {
            String position = TestWorld.key(x, y, z);
            positions.add(position);
            blocks.put(position, block);
            metadata.put(position, meta);
        }

        @Override
        public void hintMarker(World world, int x, int y, int z, HintMarkerKind kind) {
            positions.add(TestWorld.key(x, y, z));
        }

        @Override
        public void hintParticleTinted(World world, int x, int y, int z, IIcon[] icons, short[] color,
                ItemStack displayStack) {
            positions.add(TestWorld.key(x, y, z));
            tintedPositions.add(TestWorld.key(x, y, z));
        }

        @Override
        public void hintFrame(World world, int x, int y, int z, HintColor color) {
            String position = TestWorld.key(x, y, z);
            assertTrue(blocks.containsKey(position), "The frame must decorate an existing block hint");
            frames.put(position, color);
        }
    }

    private static void checkDiffuser() {
        TestWorld world = new TestWorld();
        world.provider.dimensionId = -2057; // Earth's orbit: the diffuser's actual gravity restriction.
        Diffuser machine = new Diffuser();
        controller(world, machine);
        assertFalse(machine.recheck(), "An empty world must not form a diffuser with fewer than eight hatches");
        machine.construct(new ItemStack(Items.stick), false);
        assertFalse(machine.recheck(), "Maintenance and an input bus are mandatory");
        List<String> casings = new ArrayList<>();
        world.blocks.forEach((position, block) -> {
            if (block == ImpactCasings.LAB_SAFELG_CASING.getBlock()
                    && world.metadata.get(position) == ImpactCasings.LAB_SAFELG_CASING.getMetadata()) {
                casings.add(position);
            }
        });
        putHatch(world, casings.get(0), new GT_MetaTileEntity_Hatch_Maintenance(
                "maintenance", 1, "", new ITexture[0][][], false));
        putHatch(world, casings.get(1), new GT_MetaTileEntity_Hatch_InputBus(
                "input", 1, "", new ITexture[0][][]));
        world.blocks.put(casings.get(10), Blocks.bedrock);
        assertFalse(machine.recheck(), "Damaged shell with valid hatch counts");
        world.blocks.put(casings.get(10), ImpactCasings.LAB_SAFELG_CASING.getBlock());
        for (int energy = 0; energy < 8; energy++) {
            assertTrue(machine.recheck(), "Allowed energy hatch count: " + energy);
            assertTrue(machine.recheck());
            assertEquals(1, machine.mMaintenanceHatches.size());
            assertEquals(1, machine.mInputBusses.size());
            putHatch(world, casings.get(energy + 2), new GT_MetaTileEntity_Hatch_Energy(
                    "energy", 4, "", new ITexture[0][][]));
        }
        assertFalse(machine.recheck(), "Eight energy hatches exceed the original limit");
        world.blocks.put(casings.get(10), Blocks.bedrock);
        assertFalse(machine.recheck(), "Damaged shell");
    }

    private static void putHatch(TestWorld world, String position, MetaTileEntity hatch) {
        String[] coordinates = position.split(":");
        world.putTile(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]),
                Integer.parseInt(coordinates[2]), new TestTile(world, hatch, ForgeDirection.NORTH));
    }

    private static class Diffuser extends GTMTE_ParametricDiffuser {
        Diffuser() {
            super("diffuser-test");
        }

        boolean recheck() {
            setupMachine(getBaseMetaTileEntity(), null);
            return mMachine;
        }
    }

    private static void checkCokeOven() {
        TestWorld world = new TestWorld();
        GTMTE_CokeOven machine = new GTMTE_CokeOven("coke-test");
        TestTile tile = controller(world, machine);
        machine.construct(new ItemStack(Items.stick), false);
        assertTrue(machine.checkMachine(tile, null));
        world.setBlock(0, 64, 1, Blocks.bedrock, 0, 0);
        assertFalse(machine.checkMachine(tile, null), "Coke oven requires empty center");
    }

    private static void checkTank() {
        TestWorld world = new TestWorld();
        GTMTE_SingleTank tank = new GTMTE_SingleTank("tank-test");
        TestTile tile = controller(world, tank);
        ItemStack trigger = new ItemStack(Items.stick);
        tank.construct(trigger, false);
        assertTrue(tank.checkMachine(tile, null));
        assertEquals(80_000_000, tank.getMultiFluidHandler().getCapacity());
        assertTrue(tank.checkMachine(tile, null));
        assertEquals(80_000_000, tank.getMultiFluidHandler().getCapacity());
        int changes = world.changes;
        world.isRemote = true;
        tank.construct(trigger, true);
        world.isRemote = false;
        assertEquals(changes, world.changes);
        assertEquals(80_000_000, tank.getMultiFluidHandler().getCapacity());
        String cell = world.blocks.entrySet().stream()
                .filter(entry -> entry.getValue() == ItemRegistery.FluidTankBlock).findFirst().get().getKey();
        world.metadata.put(cell, 1);
        assertTrue(tank.checkMachine(tile, null));
        assertEquals(96_000_000, tank.getMultiFluidHandler().getCapacity());
        world.blocks.put(cell, Blocks.bedrock);
        assertFalse(tank.checkMachine(tile, null));
    }

    private static void checkTankDirections(HintRecorder hints) {
        for (boolean multi : new boolean[] { false, true }) {
            Integer expectedCapacity = null;
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                for (boolean survival : new boolean[] { false, true }) {
                    TestWorld world = new TestWorld();
                    GT_MetaTileEntity_EnhancedMultiBlockBase<?> tank = multi
                            ? new GTMTE_MultiTank("multi-tank-directions")
                            : new GTMTE_SingleTank("single-tank-directions");
                    assertTrue(tank.isFacingValid((byte) direction.ordinal()), direction.name());
                    TestTile tile = new TestTile(world, tank, direction);
                    world.putTile(0, 64, 0, tile);
                    assertEquals(direction, tank.getExtendedFacing().getDirection());
                    ItemStack trigger = new ItemStack(Items.stick);
                    world.isRemote = true;
                    hints.clear();
                    tank.construct(trigger, true);
                    Set<String> expectedBlocks = new HashSet<>(hints.positions);
                    assertEquals(multi ? 224 : 62, expectedBlocks.size());
                    assertEquals(multi ? 101 : 28, hints.frames.size());
                    assertFalse(hints.frames.containsValue(HintColor.BLUE), "Tanks have no energy hatch positions");
                    assertEquals(1, world.blocks.size(), "Holograms must not place blocks");
                    world.isRemote = false;
                    if (survival) {
                        Source source = new Source(
                                new ItemStack(Casings.CHEMICALLY_INERT.getBlock(), 300,
                                        Casings.CHEMICALLY_INERT.getMetadata()),
                                new ItemStack(ItemRegistery.IGlassBlock, 300),
                                new ItemStack(ItemRegistery.FluidTankBlock, 300));
                        ISurvivalBuildEnvironment environment = ISurvivalBuildEnvironment.create(
                                source, new TestPlayer(world));
                        for (int step = 0; step < 100 && !tank.checkMachine(tile, null); step++) {
                            int consumed = source.consumed;
                            int built = ((ISurvivalConstructable) tank).survivalConstruct(trigger, 7, environment);
                            assertTrue(built >= 0 && built <= 7, "Tank construction budget");
                            assertEquals(built, source.consumed - consumed);
                        }
                    } else {
                        tank.construct(trigger, false);
                    }
                    assertTrue(tank.checkMachine(tile, null), tank.getClass().getSimpleName() + " " + direction);
                    Set<String> actualBlocks = new HashSet<>(world.blocks.keySet());
                    actualBlocks.remove(TestWorld.key(0, 64, 0));
                    assertEquals(expectedBlocks, actualBlocks, "Build and hologram must agree");
                    int capacity = ((IMultiFluidTank) tank).getMultiFluidHandler().getCapacity();
                    if (expectedCapacity == null) {
                        expectedCapacity = capacity;
                    }
                    assertEquals(expectedCapacity.intValue(), capacity, "Capacity must not depend on direction");
                    assertTrue(tank.checkMachine(tile, null));
                    assertEquals(capacity, ((IMultiFluidTank) tank).getMultiFluidHandler().getCapacity());
                    String cell = world.blocks.entrySet().stream()
                            .filter(entry -> entry.getValue() == ItemRegistery.FluidTankBlock)
                            .findFirst().get().getKey();
                    world.blocks.put(cell, Blocks.bedrock);
                    assertFalse(tank.checkMachine(tile, null), "Damaged tank must fail in every direction");
                }
            }
        }
    }

    private static void checkNaquadahBeams() {
        Block_QuantumStuff.INSTANCE = new Block_QuantumStuff();
        TestWorld world = new TestWorld();
        TE_NqTether tether = new TE_NqTether();
        tether.setWorldObj(world);
        tether.yCoord = 64;
        assertFalse(tether.shouldRenderBeam(), "A tether without a core must not render a stray beam");
        for (int metadata : new int[] { Block_QuantumStuff.ACTIVE_META, Block_QuantumStuff.INACTIVE_META }) {
            world.setBlock(0, 66, 0, Block_QuantumStuff.INSTANCE, metadata, 0);
            assertTrue(tether.shouldRenderBeam(), "Vertical generators retain active and inactive beams");
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                int x = direction.offsetX * 3;
                int y = 66 + direction.offsetY * 3;
                int z = direction.offsetZ * 3;
                world.putTile(x, y, z, new TestTile(world, new GTMTE_LiquidNqGenerator("liquid-beam-test"),
                        direction));
                assertFalse(tether.shouldRenderBeam(), "Liquid generator must not render a beam: " + direction);
                world.setBlock(x, y, z, Blocks.air, 0, 0);
            }
            world.setBlock(0, 66, 0, Blocks.air, 0, 0);
            world.setBlock(2, 64, 0, Block_QuantumStuff.INSTANCE, metadata, 0);
            assertFalse(tether.shouldRenderBeam(), "A horizontal core must not produce a vertical beam");
            world.setBlock(2, 64, 0, Blocks.air, 0, 0);
        }
        assertTrue(ImpactConstructionChecks.class.getResource("/assets/impact/textures/blocks/FullWhite.png") != null,
                "The white block sprite used by the beam must be packaged");
    }

    private static void checkSpaceElevatorBeam() {
        TE_SpaceElevatorTether tether = new TE_SpaceElevatorTether();
        assertEquals(0, tether.getBeamHeight(), "An inventory tether has no beam");
        TestWorld world = new TestWorld();
        tether.setWorldObj(world);
        tether.yCoord = 63;
        assertEquals(0, tether.getBeamHeight(), "No controller");
        TestTile unrelated = controller(world, new GTMTE_SingleTank("not-an-elevator"));
        unrelated.setActive(true);
        assertEquals(0, tether.getBeamHeight(), "An unrelated active machine must not create a beam");
        TestTile tile = controller(world, new GTMTE_SpaceElevator("elevator-beam-test"));
        for (int dimension : new int[] { 0, 1, -1, -2057 }) {
            world.provider.dimensionId = dimension;
            tile.setActive(false);
            assertEquals(0, tether.getBeamHeight(), "An inactive elevator must not enter the renderer");
            tile.setActive(true);
            assertEquals(dimension == 0 ? 256 : -256, tether.getBeamHeight());
            tile.setActive(false);
            assertEquals(0, tether.getBeamHeight());
        }
        world.setBlock(0, 64, 0, Blocks.air, 0, 0);
        assertEquals(0, tether.getBeamHeight(), "Removing the controller removes the beam");
        assertTrue(ImpactConstructionChecks.class.getResource(
                "/assets/impact/textures/blocks/space_elevator_beam.png") != null,
                "The beam sprite must be available to the block atlas");
    }

    private static void checkParallelComputerLength(HintRecorder hints) {
        for (ForgeDirection direction : new ForgeDirection[] {
                ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST }) {
            int[] maintenanceOffset = new int[3];
            ExtendedFacing.of(direction).getWorldOffset(new int[] { 1, 0, 0 }, maintenanceOffset);
            String maintenance = TestWorld.key(maintenanceOffset[0], 64 + maintenanceOffset[1], maintenanceOffset[2]);
            for (int size : new int[] { 1, 2, 15, 64 }) {
                int length = size == 1 ? 4 : size == 2 ? 5 : 18;
                for (boolean survival : new boolean[] { false, true }) {
                    TestWorld world = new TestWorld();
                    ParallelComputer machine = new ParallelComputer();
                    world.putTile(0, 64, 0, new TestTile(world, machine, direction));
                    ItemStack trigger = new ItemStack(Items.stick, size);
                    world.isRemote = true;
                    hints.clear();
                    machine.construct(trigger, true);
                    Set<String> expected = new HashSet<>(hints.positions);
                    assertEquals(length * 8 - 1, expected.size(), "Computer length for stack " + size);
                    world.isRemote = false;
                    if (survival) {
                        Source source = new Source(
                                new ItemStack(ImpactCasings.COMPUTER_CASING.getBlock(), 300,
                                        ImpactCasings.COMPUTER_CASING.getMetadata()),
                                new ItemStack(ItemRegistery.InsideBlock, 300, 2));
                        ISurvivalBuildEnvironment environment = ISurvivalBuildEnvironment.create(
                                source, new TestPlayer(world));
                        for (int step = 0; step < 100; step++) {
                            int consumed = source.consumed;
                            int built = machine.survivalConstruct(trigger, 7, environment);
                            assertTrue(built >= 0 && built <= 7);
                            assertEquals(built, source.consumed - consumed);
                            if (built == 0) {
                                break;
                            }
                        }
                    } else {
                        machine.construct(trigger, false);
                    }
                    Set<String> actual = new HashSet<>(world.blocks.keySet());
                    actual.remove(TestWorld.key(0, 64, 0));
                    assertEquals(expected, actual, "Computer build and hologram must match");
                    putHatch(world, maintenance, new GT_MetaTileEntity_Hatch_Maintenance(
                            "maintenance", 1, "", new ITexture[0][][], false));
                    assertTrue(machine.recheck(), "Computer length " + length + " facing " + direction);
                    assertTrue(machine.recheck(), "Repeated computer check");
                }
            }
            for (int layers : new int[] { 0, 1, 17 }) {
                TestWorld world = new TestWorld();
                ParallelComputer machine = new ParallelComputer();
                world.putTile(0, 64, 0, new TestTile(world, machine, direction));
                machine.buildLayers(layers);
                putHatch(world, maintenance, new GT_MetaTileEntity_Hatch_Maintenance(
                        "maintenance", 1, "", new ITexture[0][][], false));
                assertFalse(machine.recheck(), "Invalid computer length " + (layers + 2));
            }
        }
    }

    private static class ParallelComputer extends GTMTE_ParallelComputer {
        ParallelComputer() {
            super("parallel-computer-length-test");
        }

        boolean recheck() {
            setupMachine(getBaseMetaTileEntity(), null);
            return mMachine;
        }

        void buildLayers(int layers) {
            ItemStack trigger = new ItemStack(Items.stick);
            buildPiece("front", trigger, false, 0, 2, 0);
            for (int depth = 1; depth <= layers; depth++) {
                buildPiece("layer", trigger, false, 0, 2, -depth);
            }
            buildPiece("end", trigger, false, 0, 2, -layers - 1);
        }
    }

    private static void checkCapacitors() {
        for (int layers : new int[] { 1, 15, 64 }) {
            TestWorld world = new TestWorld();
            GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("capacitor-test");
            TestTile tile = controller(world, machine);
            ItemStack trigger = new ItemStack(Items.stick, layers);
            machine.construct(trigger, false);
            assertTrue(machine.checkMachine(tile, null), "layers=" + layers);
            long expected = 9L * Math.min(15, layers) * GTMTE_LapPowerStation.L_UEV;
            assertEquals(expected, machine.capacity);
            assertTrue(machine.checkMachine(tile, null));
            assertEquals(expected, machine.capacity);
            int changes = world.changes;
            world.isRemote = true;
            machine.construct(trigger, true);
            world.isRemote = false;
            assertEquals(changes, world.changes);
            assertEquals(expected, machine.capacity);
            String cell = world.blocks.entrySet().stream()
                    .filter(entry -> entry.getValue() == ItemRegistery.lscLapotronicEnergyUnit)
                    .findFirst().get().getKey();
            world.metadata.put(cell, 1);
            assertTrue(machine.checkMachine(tile, null));
            assertEquals(expected - GTMTE_LapPowerStation.L_UEV + GTMTE_LapPowerStation.L_IV, machine.capacity);
            world.blocks.put(cell, Blocks.bedrock);
            assertFalse(machine.checkMachine(tile, null));
        }
    }

    private static void checkSurvival() {
        TestWorld world = new TestWorld();
        GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("survival-test");
        TestTile tile = controller(world, machine);
        TestPlayer player = new TestPlayer(world);
        Source source = new Source(
                new ItemStack(ImpactCasings.LSCC_CASING.getBlock(), 200, ImpactCasings.LSCC_CASING.getMetadata()),
                new ItemStack(ItemRegistery.IGlassBlock, 200),
                new ItemStack(ItemRegistery.lscLapotronicEnergyUnit, 200, 8));
        ISurvivalBuildEnvironment environment = ISurvivalBuildEnvironment.create(source, player);
        ItemStack trigger = new ItemStack(Items.stick, 2);
        assertEquals(0, machine.survivalConstruct(trigger, 0, environment));
        assertEquals(0, source.consumed);
        world.setBlock(2, 65, 0, Blocks.bedrock, 0, 0);
        machine.survivalConstruct(trigger, 3, environment);
        assertSame(Blocks.bedrock, world.getBlock(2, 65, 0));
        world.setBlock(2, 65, 0, Blocks.air, 0, 0);
        for (int step = 0; step < 100 && !machine.checkMachine(tile, null); step++) {
            int previous = source.consumed;
            int built = machine.survivalConstruct(trigger, 3, environment);
            assertTrue(built >= 0 && built <= 3, "Shared piece budget");
            assertEquals(built, source.consumed - previous, "One consumed item per placed block");
        }
        assertTrue(machine.checkMachine(tile, null));
        assertEquals(18L * GTMTE_LapPowerStation.L_UEV, machine.capacity);
        int previous = source.consumed;
        assertEquals(-1, machine.survivalConstruct(trigger, 3, environment));
        assertEquals(previous, source.consumed);
    }

    private static TestTile controller(TestWorld world, MetaTileEntity machine) {
        TestTile tile = new TestTile(world, machine, ForgeDirection.NORTH);
        world.putTile(0, 64, 0, tile);
        return tile;
    }

    public static class TestTile extends BaseMetaTileEntity {
        private byte facing;

        TestTile(World world, MetaTileEntity machine, ForgeDirection direction) {
            worldObj = world;
            facing = (byte) direction.ordinal();
            machine.setBaseMetaTileEntity(this);
        }

        @Override
        public byte getFrontFacing() {
            return facing;
        }

        @Override
        public byte getBackFacing() {
            return (byte) ForgeDirection.getOrientation(facing).getOpposite().ordinal();
        }

        @Override
        public void setFrontFacing(byte side) {
            if (isValidFacing(side)) {
                facing = side;
                ((MetaTileEntity) getMetaTileEntity()).onFacingChange();
            }
        }

        @Override
        public void issueTextureUpdate() {
        }

        @Override
        public void markDirty() {
        }
    }

    public static class TestWorld extends World {
        final Set<String> unloadedPositions = new HashSet<>();
        final Map<String, Block> blocks = new HashMap<>();
        final Map<String, Integer> metadata = new HashMap<>();
        final Map<String, TestTile> tiles = new HashMap<>();
        int changes;

        TestWorld() {
            super(new SaveHandlerMP(), "structures", new WorldSettings(0, WorldSettings.GameType.SURVIVAL,
                    false, false, WorldType.DEFAULT), new WorldProviderSurface(), new Profiler());
        }

        static String key(int x, int y, int z) {
            return x + ":" + y + ":" + z;
        }

        @Override
        public Chunk getChunkFromChunkCoords(int x, int z) {
            return new Chunk(this, x, z);
        }

        @Override
        protected IChunkProvider createChunkProvider() {
            return null;
        }

        @Override
        protected int func_152379_p() {
            return 0;
        }

        @Override
        public Entity getEntityByID(int id) {
            return null;
        }

        @Override
        public boolean blockExists(int x, int y, int z) {
            return !unloadedPositions.contains(key(x, y, z));
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            return blocks.getOrDefault(key(x, y, z), Blocks.air);
        }

        @Override
        public int getBlockMetadata(int x, int y, int z) {
            return metadata.getOrDefault(key(x, y, z), 0);
        }

        @Override
        public TileEntity getTileEntity(int x, int y, int z) {
            return tiles.get(key(x, y, z));
        }

        @Override
        public boolean setBlock(int x, int y, int z, Block block, int meta, int flags) {
            changes++;
            String key = key(x, y, z);
            tiles.remove(key);
            if (block == Blocks.air) {
                blocks.remove(key);
                metadata.remove(key);
            } else {
                blocks.put(key, block);
                metadata.put(key, meta);
            }
            return true;
        }

        void putTile(int x, int y, int z, TestTile tile) {
            setBlock(x, y, z, Blocks.iron_block, 0, 0);
            tile.xCoord = x;
            tile.yCoord = y;
            tile.zCoord = z;
            tiles.put(key(x, y, z), tile);
        }

        @Override
        public boolean canPlaceEntityOnSide(Block block, int x, int y, int z, boolean replace,
                int side, Entity entity, ItemStack stack) {
            return isAirBlock(x, y, z) || getBlock(x, y, z).isReplaceable(this, x, y, z);
        }

        @Override
        public void playSoundEffect(double x, double y, double z, String sound, float volume, float pitch) {
        }
    }

    static class TestPlayer extends EntityPlayer {
        final List<IChatComponent> messages = new ArrayList<>();

        TestPlayer(World world) {
            super(world, new GameProfile(UUID.randomUUID(), "Builder"));
        }

        @Override
        public void addChatMessage(IChatComponent message) {
            messages.add(message);
        }

        @Override
        public void addChatComponentMessage(IChatComponent message) {
            messages.add(message);
        }

        @Override
        public boolean canCommandSenderUseCommand(int level, String command) {
            return true;
        }

        @Override
        public ChunkCoordinates getPlayerCoordinates() {
            return new ChunkCoordinates(0, 64, 0);
        }

        @Override
        public void addStat(StatBase stat, int amount) {
        }

        @Override
        public boolean canPlayerEdit(int x, int y, int z, int side, ItemStack stack) {
            return true;
        }
    }

    static class Source implements IItemSource {
        final List<ItemStack> stacks;
        int consumed;
        int queries;

        Source(ItemStack... stacks) {
            this.stacks = Arrays.asList(stacks);
        }

        @Override
        public Map<ItemStack, Integer> take(Predicate<ItemStack> predicate, boolean simulate, int count) {
            queries++;
            Map<ItemStack, Integer> result = new LinkedHashMap<>();
            for (ItemStack stack : stacks) {
                if (stack.stackSize <= 0 || !predicate.test(stack))
                    continue;
                int taken = Math.min(count, stack.stackSize);
                ItemStack copy = stack.copy();
                copy.stackSize = taken;
                result.put(copy, taken);
                if (!simulate) {
                    stack.stackSize -= taken;
                    consumed += taken;
                }
                count -= taken;
                if (count == 0)
                    break;
            }
            return result;
        }
    }

}
