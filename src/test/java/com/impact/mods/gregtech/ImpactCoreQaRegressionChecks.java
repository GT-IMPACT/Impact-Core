package com.impact.mods.gregtech;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Supplier;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.enums.*;
import gregtech.api.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.structure.*;
import space.impact.structures.alignment.constructable.*;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactBlocks;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Isolated player-path regressions with distinct metadata-preserving blocks and
 * real hatch classes.
 */
public class ImpactCoreQaRegressionChecks {
    static int blockId = 3100;
    static final List<Block> blocks = new ArrayList<>();
    static final Map<Integer, Supplier<IMetaTileEntity>> factories = new LinkedHashMap<>();
    static final Set<Integer> lowIds = new HashSet<>();
    static Block machineBlock;
    static Item machineItem;
    static int nextId = 30000;
    static PrintWriter report;
    static final Set<String> mutationDone = new HashSet<>();

    static class MetaBlock extends Block {
        MetaBlock() {
            super(Material.iron);
        }

        @Override
        public int damageDropped(int meta) {
            return meta;
        }

        @Override
        public int getDamageValue(World w, int x, int y, int z) {
            return w.getBlockMetadata(x, y, z);
        }
    }

    static Block registerBlock(String name) {
        Block b = new MetaBlock();
        int id = blockId++;
        Block.blockRegistry.addObject(id, "fullqa:" + name, b);
        Item.itemRegistry.addObject(id, "fullqa:" + name, new ItemBlock(b) {
            @Override
            public int getMetadata(int m) {
                return m;
            }
        }.setHasSubtypes(true));
        blocks.add(b);
        return b;
    }

    static class FullWorld extends ImpactConstructionChecks.TestWorld {
        void placeMachine(int x, int y, int z, int id) {
            IMetaTileEntity m = factories.get(id).get();
            ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(this,
                    m instanceof MetaTileEntity ? (MetaTileEntity) m
                            : new GT_MetaTileEntity_Hatch_Energy("frame-tile", 1, "", new ITexture[0][][]),
                    ForgeDirection.NORTH) {
                @Override
                public int getMetaTileID() {
                    return id;
                }

                @Override
                public IMetaTileEntity getMetaTileEntity() {
                    return m;
                }
            };
            putTile(x, y, z, tile);
            blocks.put(key(x, y, z), machineBlock);
            metadata.put(key(x, y, z), (int) m.getTileEntityBaseType());
        }
    }

    static MetaTileEntity instantiate(Class<?> type, int tier) {
        for (Constructor<?> c : type.getConstructors()) {
            Class<?>[] p = c.getParameterTypes();
            if (p.length == 0 || p[0] != String.class)
                continue;
            Object[] a = new Object[p.length];
            boolean supported = true;
            for (int i = 0; i < p.length; i++) {
                if (p[i] == String.class)
                    a[i] = "fullqa";
                else if (p[i] == String[].class)
                    a[i] = new String[0];
                else if (p[i] == int.class)
                    a[i] = i == 1 ? tier : 16;
                else if (p[i] == long.class)
                    a[i] = 16L;
                else if (p[i] == byte.class)
                    a[i] = (byte) tier;
                else if (p[i] == boolean.class)
                    a[i] = false;
                else if (p[i] == ITexture[][][].class)
                    a[i] = new ITexture[0][][];
                else
                    supported = false;
            }
            if (supported)
                try {
                    return (MetaTileEntity) c.newInstance(a);
                } catch (Throwable e) {
                }
        }
        throw new IllegalStateException(
                "No working constructor " + type.getName() + " " + Arrays.toString(type.getConstructors()));
    }

    static void addFactory(int id, Supplier<IMetaTileEntity> factory, boolean low) {
        factories.put(id, factory);
        GregTech_API.METATILEENTITIES[id] = factory.get();
        if (low)
            lowIds.add(id);
    }

    static void prepare(List<String> names) throws Exception {
        for (Class<?> owner : new Class<?>[] { GregTech_API.class, ImpactBlocks.class, ItemRegistery.class }) {
            for (Field f : owner.getFields())
                if (Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())
                        && f.getType() == Block.class
                        && (owner != GregTech_API.class || f.getName().startsWith("sBlockCasings")))
                    f.set(null, registerBlock(owner.getSimpleName() + "_" + f.getName()));
        }
        machineBlock = new MetaBlock() {
            @Override
            public int getDamageValue(World w, int x, int y, int z) {
                TileEntity t = w.getTileEntity(x, y, z);
                return t instanceof gregtech.api.interfaces.tileentity.IGregTechTileEntity
                        ? ((gregtech.api.interfaces.tileentity.IGregTechTileEntity) t).getMetaTileID()
                        : 0;
            }
        };
        int id = blockId++;
        Block.blockRegistry.addObject(id, "fullqa:machines", machineBlock);
        machineItem = new gregtech.common.blocks.GT_Item_Machines(machineBlock) {
            @Override
            public boolean onItemUse(ItemStack s, EntityPlayer p, World w, int x, int y, int z, int side, float a,
                    float b, float c) {
                ((FullWorld) w).placeMachine(x, y, z, s.getItemDamage());
                s.stackSize--;
                return true;
            }

            @Override
            public boolean placeBlockAt(ItemStack s, EntityPlayer p, World w, int x, int y, int z, int side, float a,
                    float b, float c, int meta) {
                ((FullWorld) w).placeMachine(x, y, z, s.getItemDamage());
                return true;
            }
        };
        Item.itemRegistry.addObject(id, "fullqa:machines", machineItem);
        GregTech_API.sBlockMachines = machineBlock;
        List<String> candidates = new ArrayList<>(
                Arrays.asList("gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Muffler",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output",
                        "gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus",
                        "com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver",
                        "com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack",
                        "com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_EnrichmentUnit",
                        "com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In",
                        "com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out",
                        "com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch",
                        "com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input",
                        "com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output",
                        "com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch",
                        "com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Transmitter",
                        "com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch",
                        "com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output",
                        "com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_InputBus",
                        "com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_OutputBus",
                        "com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_Hatch_MESystemMPChamber"));
        // Put ordinary item and fluid ports first; selection order is deliberately
        // realistic and reproducible.
        candidates.sort(Comparator.comparingInt(s -> s.startsWith("gregtech.api") ? 0 : 1));
        for (String n : candidates) {
            Class<?> c = Class.forName(n);
            if (Modifier.isAbstract(c.getModifiers()) || !(GT_MetaTileEntity_Hatch.class.isAssignableFrom(c)
                    || GT_MetaTileEntity_BasicHull.class.isAssignableFrom(c)))
                continue;
            try {
                for (int tier : new int[] { 9, 1 }) {
                    final int t = tier;
                    instantiate(c, t);
                    addFactory(nextId++, () -> instantiate(c, t), t == 1);
                }
                report.println("FACTORY " + n);
            } catch (Throwable e) {
                throw new AssertionError("Cannot create hatch fixture: " + n, e);
            }
        }
        for (Materials mat : new Materials[] { Materials.Iron, Materials.Wood, Materials.Steel, Materials.HSLA,
                Materials.Titanium, Materials.TungstenSteel, Materials.StainlessSteel }) {
            int fid = nextId++;
            addFactory(fid, () -> new GT_MetaPipeEntity_Frame("full-frame", mat), false);
            GT_OreDictUnificator.set(OrePrefixes.frameGt, mat, new ItemStack(machineItem, 1, fid));
        }
        Block tether = com.impact.common.block.blocks.Block_NqTether.INSTANCE;
        int tid = blockId++;
        Block.blockRegistry.addObject(tid, "fullqa:tether", tether);
        Item.itemRegistry.addObject(tid, "fullqa:tether", new ItemBlock(tether));
        for (String name : names)
            for (Class<?> c = Class.forName(name); c != null; c = c.getSuperclass())
                for (Field f : c.getDeclaredFields())
                    if (Modifier.isStatic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers())
                            && IStructureDefinition.class.isAssignableFrom(f.getType())) {
                        f.setAccessible(true);
                        f.set(null, null);
                    }
    }

    static GT_MetaTileEntity_EnhancedMultiBlockBase machine(String name) throws Exception {
        Class<?> c = Class.forName(name);
        return (GT_MetaTileEntity_EnhancedMultiBlockBase) (name.contains("GTMTE_SOFC_")
                ? c.getConstructor(String.class, int.class).newInstance(name,
                        name.substring(name.lastIndexOf('_') + 1).length() - 1)
                : c.getConstructor(String.class).newInstance(name));
    }

    static ImpactConstructionChecks.Source inventory(boolean low, boolean empty) {
        List<ItemStack> inv = new ArrayList<>();
        if (!empty) {
            for (Map.Entry<Integer, Supplier<IMetaTileEntity>> e : factories.entrySet())
                if (!low || lowIds.contains(e.getKey()) || e.getValue().get() instanceof GT_MetaPipeEntity_Frame)
                    inv.add(new ItemStack(machineItem, 2000, e.getKey()));
            for (Object entry : Block.blockRegistry) {
                Block b = (Block) entry;
                if (b != machineBlock && Item.getItemFromBlock(b) != null)
                    for (int meta = 0; meta < 16; meta++)
                        inv.add(new ItemStack(b, 10000, meta));
            }
            for (Block b : new Block[] { Blocks.glass, Blocks.stone, Blocks.iron_bars, Blocks.air })
                if (Item.getItemFromBlock(b) != null)
                    for (int meta = 0; meta < 16; meta++)
                        inv.add(new ItemStack(b, 10000, meta));
        }
        return new ImpactConstructionChecks.Source(inv.toArray(new ItemStack[0]));
    }

    static boolean recheck(GT_MetaTileEntity_EnhancedMultiBlockBase m, ImpactConstructionChecks.TestTile tile)
            throws Exception {
        Method setup = GT_MetaTileEntity_MultiBlockBase.class.getDeclaredMethod("setupMachine",
                gregtech.api.interfaces.tileentity.IGregTechTileEntity.class, ItemStack.class);
        setup.setAccessible(true);
        setup.invoke(m, tile, null);
        return m.mMachine;
    }

    static void run(String name, String mode) throws Exception {
        report.println("START " + name + " " + mode);
        GT_MetaTileEntity_EnhancedMultiBlockBase m = machine(name);
        FullWorld w = new FullWorld();
        if (name.endsWith("GTMTE_MPContainment")) {
            w.provider.dimensionId = com.impact.addon.gc.DimensionsIDs.ORBIT_EARTH;
        }
        ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(w, m,
                name.endsWith("GTMTE_Mining_Coal") || name.endsWith("GTMTE_BasicMiner")
                        || name.endsWith("GTMTE_AdvancedMiner") ? ForgeDirection.UP : ForgeDirection.NORTH);
        w.putTile(0, 64, 0, tile);
        ItemStack trigger = new ItemStack(Items.stick);
        for (Object o : m.getStructureDefinition().getChannelDefinitions()) {
            IChannelDefinition c = (IChannelDefinition) o;
            ChannelDataAccessor.setChannelData(trigger, c.getChannel(), c.getMinimumValue());
        }
        ImpactConstructionChecks.Source source = inventory(mode.equals("LOW_TIER"), mode.equals("EMPTY"));
        if (mode.equals("ITEM_FIRST"))
            source.stacks.sort(Comparator.comparingInt(s -> s.getItem() == machineItem
                    && GregTech_API.METATILEENTITIES[s.getItemDamage()].getClass().getSimpleName().endsWith("Bus") ? 0
                            : 1));
        ImpactConstructionChecks.TestPlayer player = new ImpactConstructionChecks.TestPlayer(w);
        ISurvivalBuildEnvironment env = ISurvivalBuildEnvironment.create(source, player);
        int last = 0, steps = 0, unchanged = 0;
        for (; steps < 500; steps++) {
            int before = w.changes;
            last = ((ISurvivalConstructable) m).survivalConstruct(trigger, 64, env);
            if (w.changes == before)
                unchanged++;
            else
                unchanged = 0;
            if (last < 0 || unchanged >= 2)
                break;
        }
        boolean formed = recheck(m, tile);
        assertEquals(!mode.equals("EMPTY"), formed, name + " " + mode + " did not complete a usable structure");
        if (formed)
            checkSelectedHatches(m, w, tile);
        Map<String, Integer> hatches = new TreeMap<>();
        for (ImpactConstructionChecks.TestTile t : w.tiles.values())
            if (t != tile)
                hatches.merge(t.getMetaTileEntity().getClass().getSimpleName(), 1, Integer::sum);
        report.println("BUILD " + m.getClass().getSimpleName() + " " + mode + " formed=" + formed + " last=" + last
                + " steps=" + steps + " blocks=" + w.blocks.size() + " consumed=" + source.consumed + " hatches="
                + hatches);
        if (mode.equals("EMPTY")) {
            assertEquals(0, source.consumed);
            assertFalse(formed);
            return;
        }
        if (formed && mutationDone.add(name)) {
            int rejected = 0, accepted = 0;
            String missing = null;
            for (String key : new ArrayList<>(w.blocks.keySet())) {
                if (w.tiles.containsKey(key))
                    continue;
                String[] p = key.split(":");
                int x = Integer.parseInt(p[0]), y = Integer.parseInt(p[1]), z = Integer.parseInt(p[2]);
                Block old = w.getBlock(x, y, z);
                int meta = w.getBlockMetadata(x, y, z);
                w.setBlock(x, y, z, Blocks.bedrock, 0, 0);
                boolean valid = recheck(m, tile);
                Block after = w.getBlock(x, y, z);
                w.setBlock(x, y, z, old, meta, 0);
                if (valid) {
                    accepted++;
                    report.println("ACCEPTED_FOREIGN " + m.getClass().getSimpleName() + " pos=" + key + " old="
                            + Block.blockRegistry.getNameForObject(old) + ":" + meta
                            + " replacement=bedrock afterCheck=" + Block.blockRegistry.getNameForObject(after));
                } else {
                    rejected++;
                    missing = key;
                }
            }
            assertEquals(0, accepted, name + " accepted a foreign block");
            report.println("WRONG_MATERIAL " + m.getClass().getSimpleName() + " rejected=" + rejected + " accepted="
                    + accepted);
            int wrongRejected = 0, wrongAccepted = 0;
            for (String key : new ArrayList<>(w.tiles.keySet())) {
                ImpactConstructionChecks.TestTile old = w.tiles.get(key);
                if (old == tile)
                    continue;
                String[] p = key.split(":");
                int x = Integer.parseInt(p[0]), y = Integer.parseInt(p[1]), z = Integer.parseInt(p[2]);
                IMetaTileEntity wrong = new GT_MetaPipeEntity_Frame("wrong-component", Materials.Platinum);
                ImpactConstructionChecks.TestTile replacement = new ImpactConstructionChecks.TestTile(w,
                        new GT_MetaTileEntity_Hatch_Energy("placeholder", 1, "", new ITexture[0][][]),
                        ForgeDirection.NORTH) {
                    @Override
                    public int getMetaTileID() {
                        return 31800;
                    }

                    @Override
                    public IMetaTileEntity getMetaTileEntity() {
                        return wrong;
                    }
                };
                replacement.xCoord = x;
                replacement.yCoord = y;
                replacement.zCoord = z;
                w.tiles.put(key, replacement);
                boolean valid = recheck(m, tile);
                w.tiles.put(key, old);
                if (valid) {
                    wrongAccepted++;
                    report.println("ACCEPTED_COMPONENT " + m.getClass().getSimpleName() + " pos=" + key + " old="
                            + old.getMetaTileEntity().getClass().getSimpleName());
                } else {
                    wrongRejected++;
                    missing = key;
                }
            }
            assertEquals(0, wrongAccepted, name + " accepted an incompatible component");
            report.println("WRONG_COMPONENT " + m.getClass().getSimpleName() + " rejected=" + wrongRejected
                    + " accepted=" + wrongAccepted);
            if (missing != null) {
                String[] p = missing.split(":");
                w.setBlock(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), Blocks.air, 0, 0);
                ImpactConstructionChecks.Source empty = inventory(false, true);
                ((ISurvivalConstructable) m).survivalConstruct(trigger, 64,
                        ISurvivalBuildEnvironment.create(empty, player));
                boolean valid = recheck(m, tile);
                report.println("SHORTAGE " + m.getClass().getSimpleName() + " formed=" + valid + " consumed="
                        + empty.consumed);
                assertFalse(valid);
                assertEquals(0, empty.consumed);
            }

        }
        report.flush();
    }

    static void checkSelectedHatches(GT_MetaTileEntity_EnhancedMultiBlockBase machine, FullWorld world,
            ImpactConstructionChecks.TestTile controller) throws Exception {
        String name = machine.getClass().getSimpleName();
        if (name.equals("GTMTE_MPContainment")) {
            int orbit = world.provider.dimensionId;
            world.provider.dimensionId = 0;
            assertFalse(recheck(machine, controller), "Containment requires low gravity");
            world.provider.dimensionId = orbit;
            assertTrue(recheck(machine, controller), "Containment must form again on Earth's orbit");
        }
        if (name.equals("GTMTE_Wind_Generator")) {
            assertEquals(1, machine.mInputBusses.size());
            assertEquals(1, machine.mDynamoHatches.size());
            assertTrue(machine.mMaintenanceHatches.isEmpty());
            assertFalse(machine.enabledMaintenance);
            assertEquals(machine.getIdealStatus(), machine.getRepairStatus());
        }
        if (name.equals("GTMTE_AdvancedCrackingUnit"))
            assertEquals(1, machine.mMufflerHatches.size());
        if (name.equals("GTMTE_BasicWaterPump")) {
            assertTrue(machine.mOutputBusses.isEmpty());
            assertEquals(1, machine.mOutputHatches.size());
            assertTrue(((GT_MetaTileEntity_Hatch_Output) machine.mOutputHatches.get(0)).mTier <= 1);
        }
        if (name.equals("GTMTEMESystemProvider") || name.equals("GTMTE_MPContainment")) {
            assertEquals(1, machine.mInputBusses.size());
            assertEquals(1, machine.mOutputBusses.size());
            assertTrue(machine.mInputHatches.isEmpty());
            assertTrue(machine.mOutputHatches.isEmpty());
        }
        if (name.equals("GTMTE_BasicWaterPump") || name.equals("GTMTE_DrillerWater")) {
            for (String position : new ArrayList<>(world.tiles.keySet())) {
                ImpactConstructionChecks.TestTile previous = world.tiles.get(position);
                if (previous == controller)
                    continue;
                IMetaTileEntity component = previous.getMetaTileEntity();
                MetaTileEntity replacement;
                int id;
                if (component instanceof GT_MetaPipeEntity_Frame) {
                    replacement = new GT_MetaTileEntity_Hatch_Energy("not-a-frame", 1, "", new ITexture[0][][]);
                    id = name.equals("GTMTE_BasicWaterPump") ? 4 : 7;
                } else if (name.equals("GTMTE_DrillerWater") && component instanceof GT_MetaTileEntity_Hatch_Energy) {
                    int tier = ((GT_MetaTileEntity_Hatch_Energy) component).mTier;
                    replacement = new GT_MetaTileEntity_Hatch_Energy("mismatched-energy", tier == 1 ? 2 : 1, "",
                            new ITexture[0][][]);
                    id = 31800;
                } else if (name.equals("GTMTE_DrillerWater") && component instanceof GT_MetaTileEntity_Hatch_Output) {
                    int tier = ((GT_MetaTileEntity_Hatch_Output) component).mTier;
                    replacement = new GT_MetaTileEntity_Hatch_Output("mismatched-output", tier == 1 ? 2 : 1, "",
                            new ITexture[0][][]);
                    id = 31800;
                } else
                    continue;
                putCandidate(world, previous.xCoord, previous.yCoord, previous.zCoord, replacement, id);
                assertFalse(recheck(machine, controller),
                        name + " accepted " + replacement.getClass().getSimpleName() + " at " + position);
                world.tiles.put(position, previous);
                assertTrue(recheck(machine, controller), name + " did not recover after restoring the component");
            }
        }
    }

    static void putCandidate(FullWorld world, int x, int y, int z, MetaTileEntity component, int id) {
        ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(world, component,
                ForgeDirection.NORTH) {
            @Override
            public int getMetaTileID() {
                return id;
            }
        };
        world.putTile(x, y, z, tile);
        world.blocks.put(ImpactConstructionChecks.TestWorld.key(x, y, z), machineBlock);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static void checkRole(String suffix, char symbol, MetaTileEntity hatch, boolean accepted) throws Exception {
        GT_MetaTileEntity_EnhancedMultiBlockBase machine = machine(
                "com.impact.mods.gregtech.tileentities.multi." + suffix);
        FullWorld world = new FullWorld();
        world.putTile(0, 64, 0, new ImpactConstructionChecks.TestTile(world, machine, ForgeDirection.NORTH));
        putCandidate(world, 20, 64, 20, hatch, 31800);
        IStructureElement element = (IStructureElement) ((StructureDefinition) machine.getStructureDefinition())
                .getElements().get(symbol);
        assertEquals(accepted, element.couldBeValid(machine, world, 20, 64, 20, new ItemStack(Items.stick)),
                suffix + " hint " + symbol);
        assertEquals(accepted, element.check(machine, world, 20, 64, 20), suffix + " check " + symbol);
    }

    static void checkDedicatedRoles() throws Exception {
        checkRole("generators.nq.GTMTE_LiquidNqGenerator", 'E',
                new GT_MetaTileEntity_Hatch_Output("fluid-output", 1, "", new ITexture[0][][]), false);
        checkRole("generators.nq.GTMTE_LiquidNqGenerator", 'E',
                new GT_MetaTileEntity_Hatch_InputBus("item-input", 1, "", new ITexture[0][][]), false);
        checkRole("generators.nq.GTMTE_LiquidNqGenerator", 'E',
                new GT_MetaTileEntity_Hatch_Muffler("muffler", 1, "", new ITexture[0][][]), false);
        checkRole("units.GTMTE_BasicWaterPump", 'H',
                new GT_MetaTileEntity_Hatch_OutputBus("bus", 1, "", new ITexture[0][][]), false);
        checkRole("units.GTMTE_BasicWaterPump", 'H',
                new GT_MetaTileEntity_Hatch_Output("mv", 2, "", new ITexture[0][][]), false);
        checkRole("units.GTMTE_BasicWaterPump", 'H',
                new GT_MetaTileEntity_Hatch_Output("lv", 1, "", new ITexture[0][][]), true);
        checkRole("units.GTMTE_DrillerWater", 'C',
                new GT_MetaTileEntity_Hatch_Energy("ulv", 0, "", new ITexture[0][][]), false);
        checkRole("units.GTMTE_DrillerWater", 'C',
                new GT_MetaTileEntity_Hatch_Output("ulv", 0, "", new ITexture[0][][]), false);
        checkRole("generators.green.GTMTE_Wind_Generator", 'C',
                new GT_MetaTileEntity_Hatch_Energy("energy", 1, "", new ITexture[0][][]), false);
        checkRole("generators.green.GTMTE_Wind_Generator", 'C',
                instantiate(GT_MetaTileEntity_Hatch_DynamoMulti.class, 1), false);
        checkRole("processing.parallel.GTMTE_AdvancedCrackingUnit", 'A',
                new GT_MetaTileEntity_Hatch_Muffler("muffler", 1, "", new ITexture[0][][]), false);
        checkRole("processing.parallel.GTMTE_AdvancedCrackingUnit", 'D',
                new GT_MetaTileEntity_Hatch_Muffler("muffler", 1, "", new ITexture[0][][]), true);
        checkRole("matrixsystem.GTMTE_MPContainment", 'O',
                new GT_MetaTileEntity_Hatch_Output("fluid", 1, "", new ITexture[0][][]), false);
        checkRole("matrixsystem.GTMTE_MPContainment", 'O',
                new GT_MetaTileEntity_Hatch_OutputBus("bus", 1, "", new ITexture[0][][]), true);
        checkRole("matrixsystem.GTMTEMESystemProvider", 'A',
                new GT_MetaTileEntity_Hatch_Input("fluid", 1, "", new ITexture[0][][]), false);
        checkRole("matrixsystem.GTMTEMESystemProvider", 'A',
                new GT_MetaTileEntity_Hatch_Output("fluid", 1, "", new ITexture[0][][]), false);
        checkRole("ores.GTMTEOilDrilling", 'B',
                new GT_MetaTileEntity_Hatch_Maintenance("maintenance", 1, "", new ITexture[0][][], false), false);
        checkRole("ores.GTMTEOilDrilling", 'E', new GT_MetaTileEntity_Hatch_Input("input", 1, "", new ITexture[0][][]),
                false);
        checkRole("ores.GTMTEOilDrilling", 'C',
                new GT_MetaTileEntity_Hatch_Output("output", 1, "", new ITexture[0][][]), false);
        checkRole("ores.GTMTEOilDrilling", 'D',
                new GT_MetaTileEntity_Hatch_Output("output", 1, "", new ITexture[0][][]), false);
    }

    static void checkTooltips() throws Exception {
        String[][] machines = { { "generators.green.GTMTE_Wind_Generator", "impact.multis.generator.wind_generator" },
                { "processing.parallel.GTMTE_AdvancedCrackingUnit", "impact.multis.cracking_unit" },
                { "matrixsystem.GTMTE_MPContainment", "impact.multis.matrixcontainment" },
                { "generators.nq.GTMTE_LiquidNqGenerator", "impact.multimachine.liquidnaqgen" },
                { "units.GTMTE_DrillerWater", "impact.multimachine.waterdrill" } };
        for (String language : new String[] { "ru_RU", "en_US" }) {
            Properties translations = new Properties();
            ImpactTooltipChecks.loadLanguage(language);
            try (Reader reader = new InputStreamReader(ImpactCoreQaRegressionChecks.class.getResourceAsStream(
                    "/assets/impact/lang/" + language + ".lang"), StandardCharsets.UTF_8)) {
                translations.load(reader);
            }
            GT_MetaTileEntity_TooltipMultiBlockBase.invalidateTooltipCache();
            for (String[] entry : machines) {
                GT_MetaTileEntity_EnhancedMultiBlockBase controller = (GT_MetaTileEntity_EnhancedMultiBlockBase) Class
                        .forName("com.impact.mods.gregtech.tileentities.multi." + entry[0]).getConstructor(String.class)
                        .newInstance(entry[1]);
                String text = net.minecraft.util.EnumChatFormatting
                        .getTextWithoutFormattingCodes(String.join("\n", controller.getPrimaryDescription()) + "\n"
                                + String.join("\n", controller.getSecondaryDescription()));
                for (String key : translations.stringPropertyNames()) {
                    if (key.startsWith("TileEntity_DESCRIPTION_" + entry[1] + "_")) {
                        assertTrue(text.contains(translations.getProperty(key)),
                                language + " missing or misnumbered " + key);
                    }
                }
            }
        }
    }

    static class TestSpaceProvider extends net.minecraft.world.WorldProviderSurface {
    }

    private static void checkGravityRequirements() {
        assertFalse(com.impact.core.Config.isDebugDev, "Structure tests must not bypass dimension restrictions");
        FullWorld world = new FullWorld();
        assertNull(net.minecraftforge.common.DimensionManager.getWorld(world.provider.dimensionId));
        ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(world,
                new com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment("gravity-test"),
                ForgeDirection.NORTH);
        assertFalse(com.impact.util.Utilits.isValidDim(world.provider.dimensionId, "Space"));
        assertFalse(com.impact.util.Utilits.isLowGravity(tile), "The overworld has normal gravity");
        world.provider.dimensionId = com.impact.addon.gc.DimensionsIDs.ORBIT_EARTH;
        assertTrue(com.impact.util.Utilits.isLowGravity(tile), "Earth's orbit has low gravity");
        ImpactConstructionChecks.TestWorld spaceWorld = new ImpactConstructionChecks.TestWorld(new TestSpaceProvider());
        ImpactConstructionChecks.TestTile spaceTile = new ImpactConstructionChecks.TestTile(spaceWorld,
                new com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment("space-gravity-test"),
                ForgeDirection.NORTH);
        assertTrue(com.impact.util.Utilits.isLowGravity(spaceTile), "Use the world's provider even outside DimensionManager");
    }

    public static void check() throws Exception {
        ImpactStructureMigrationTest.Scenarios.check();
        checkGravityRequirements();
        checkTooltips();
        List<String> names = Arrays.asList(
                "com.impact.mods.gregtech.tileentities.multi.generators.green.GTMTE_Wind_Generator",
                "com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedCrackingUnit",
                "com.impact.mods.gregtech.tileentities.multi.units.GTMTE_BasicWaterPump",
                "com.impact.mods.gregtech.tileentities.multi.units.GTMTE_DrillerWater",
                "com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTEMESystemProvider",
                "com.impact.mods.gregtech.tileentities.multi.matrixsystem.GTMTE_MPContainment",
                "com.impact.mods.gregtech.tileentities.multi.generators.nq.GTMTE_LiquidNqGenerator",
                "com.impact.mods.gregtech.tileentities.multi.ores.GTMTEOilDrilling");
        StringWriter log = new StringWriter();
        report = new PrintWriter(log);
        try {
            prepare(names);
            for (String name : names)
                for (String mode : new String[] { "FULL", "EMPTY", "LOW_TIER", "ITEM_FIRST" })
                    run(name, mode);
            checkDedicatedRoles();
        } catch (Throwable error) {
            error.printStackTrace(report);
            report.flush();
            throw new AssertionError(log.toString(), error);
        }
    }
}
