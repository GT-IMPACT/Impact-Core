package com.impact.mods.gregtech.tileentities.multi.generators.green;

import com.impact.common.item.ITieredDamagedItems;
import com.impact.common.te.TE_WindMill;
import com.impact.loader.ItemRegistery;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.util.GT_Config;
import ic2.core.IC2;
import ic2.core.Platform;
import ic2.core.WorldData;
import ic2.core.init.MainConfig;
import ic2.core.util.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.config.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;

import java.awt.Color;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WindGeneratorTest {
    private LaunchClassLoader loader;
    private LaunchClassLoader previousLaunchLoader;
    private Object scenarios;

    @BeforeAll
    void initialize() throws Exception {
        previousLaunchLoader = Launch.classLoader;
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        Class<?> type = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios = type.getConstructor().newInstance();
        type.getMethod("initialize").invoke(scenarios);
    }

    @AfterAll
    void closeLoader() throws Exception {
        Launch.classLoader = previousLaunchLoader;
        if (loader != null) loader.close();
    }

    private void check(String method) throws Throwable {
        String threadName = Thread.currentThread().getName();
        try {
            Thread.currentThread().setName("Server thread");
            scenarios.getClass().getMethod(method).invoke(scenarios);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        } finally {
            Thread.currentThread().setName(threadName);
        }
    }

    @Test
    void turningInactiveMachineMovesOnlyItsRotor() throws Throwable { check("turningInactiveMachineMovesOnlyItsRotor"); }
    @Test
    void inactiveTickRepairsRotorPositionAndStopsAnimation() throws Throwable { check("inactiveTickRepairsRotorPositionAndStopsAnimation"); }
    @Test
    void clientCannotCreateOrRemoveRotorBlocks() throws Throwable { check("clientCannotCreateOrRemoveRotorBlocks"); }
    @Test
    void occupiedPositionAndReplacementBlocksArePreserved() throws Throwable { check("occupiedPositionAndReplacementBlocksArePreserved"); }
    @Test
    void anotherControllerCannotClaimOrDeleteRotor() throws Throwable { check("anotherControllerCannotClaimOrDeleteRotor"); }
    @Test
    void reloadRetainsOwnershipAndPreviousPosition() throws Throwable { check("reloadRetainsOwnershipAndPreviousPosition"); }
    @Test
    void initialSyncIncludesAllVisualFieldsAndUnchangedStateSendsNothing() throws Throwable { check("initialSyncIncludesAllVisualFieldsAndUnchangedStateSendsNothing"); }
    @Test
    void legacyNbtRemainsReadable() throws Throwable { check("legacyNbtRemainsReadable"); }
    @Test
    void missingBusAndMalformedRotorDoNotCrash() throws Throwable { check("missingBusAndMalformedRotorDoNotCrash"); }
    @Test
    void lastDurabilityPointBreaksWithoutGeneratingPower() throws Throwable { check("lastDurabilityPointBreaksWithoutGeneratingPower"); }
    @Test
    void turnWaitsForOldRotorChunkWithoutLoadingIt() throws Throwable { check("turnWaitsForOldRotorChunkWithoutLoadingIt"); }
    @Test
    void orphanedRotorCleansUpAfterItsChunkLoads() throws Throwable { check("orphanedRotorCleansUpAfterItsChunkLoads"); }

    public static class Scenarios {
        public void initialize() throws Exception {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            Launch.classLoader = (LaunchClassLoader) getClass().getClassLoader();
            LoadController controller = new LoadController(Loader.instance());
            Field activeContainer = LoadController.class.getDeclaredField("activeContainer");
            activeContainer.setAccessible(true);
            activeContainer.set(controller, new DummyModContainer(new ModMetadata()));
            Field modController = Loader.class.getDeclaredField("modController");
            modController.setAccessible(true);
            modController.set(Loader.instance(), controller);
            Field logSide = FMLRelaunchLog.class.getDeclaredField("side");
            logSide.setAccessible(true);
            logSide.set(null, Side.SERVER);
            Field sidedDelegate = FMLCommonHandler.class.getDeclaredField("sidedDelegate");
            sidedDelegate.setAccessible(true);
            sidedDelegate.set(FMLCommonHandler.instance(), Proxy.newProxyInstance(IFMLSidedHandler.class.getClassLoader(),
                    new Class<?>[]{IFMLSidedHandler.class}, (proxy, method, args) -> {
                        if (method.getName().equals("getSide")) return Side.SERVER;
                        throw new AssertionError("Unexpected sided handler call: " + method.getName());
                    }));
            GregTech_API.sMachineFile = new GT_Config(new Configuration() {
                @Override public void load() {}
                @Override public void save() {}
            });
            Config config = new Config("wind-generator-test");
            try (InputStream defaults = IC2.class.getResourceAsStream("/assets/ic2/config/general.ini")) {
                config.load(defaults);
            }
            Field configField = MainConfig.class.getDeclaredField("config");
            configField.setAccessible(true);
            configField.set(null, config);
            IC2.platform = new Platform() {
                @Override public boolean isSimulating() { return false; }
            };
            GameRegistry.registerTileEntity(TE_WindMill.class, "TileWindMill");
            ItemRegistery.Wind_rotor = new Block(Material.iron) {};
        }

        public void turningInactiveMachineMovesOnlyItsRotor() {
            Harness h = new Harness(false, 8, 8);
            h.machine.onFirstTick(h.base);
            for (byte side = 3; side <= 5; side++) {
                TE_WindMill old = h.machine.wind;
                h.facing = side;
                h.machine.onFacingChange();
                assertSame(Blocks.air, h.world.getBlock(old.xCoord, old.yCoord, old.zCoord));
                assertEquals(side, h.machine.wind.getFacing());
                assertEquals(1, h.world.tiles.size());
                assertEquals(0, h.machine.wind.getSpeed());
            }
            h.machine.inValidate();
            assertTrue(h.world.tiles.isEmpty());
        }

        public void inactiveTickRepairsRotorPositionAndStopsAnimation() {
            Harness h = new Harness(false, 8, 8);
            h.machine.setWind();
            h.machine.wind.setSpeed(20);
            h.machine.onPostTick(h.base, 1);
            assertEquals(0, h.machine.wind.getSpeed());
            TE_WindMill old = h.machine.wind;
            h.facing = 5;
            h.machine.onPostTick(h.base, 40);
            assertSame(Blocks.air, h.world.getBlock(old.xCoord, old.yCoord, old.zCoord));
            assertEquals(5, h.machine.wind.getFacing());
        }

        public void clientCannotCreateOrRemoveRotorBlocks() {
            Harness h = new Harness(true, 8, 8);
            h.machine.setWind();
            h.facing = 5;
            h.machine.onFacingChange();
            h.machine.inValidate();
            assertEquals(0, h.world.blockWrites);
            assertNull(h.machine.wind);
            TE_WindMill rotor = new TE_WindMill();
            rotor.setWorldObj(h.world);
            rotor.setRGB(123);
            rotor.setSpeed(20);
            rotor.setRotorHidden(false);
            assertFalse(rotor.attachController(8, 64, 8));
            assertEquals(0, rotor.getRGB());
            assertEquals(0, rotor.getSpeed());
            assertTrue(rotor.hasRotorHidden());
        }

        public void occupiedPositionAndReplacementBlocksArePreserved() {
            Harness h = new Harness(false, 8, 8);
            h.machine.setWind();
            TE_WindMill old = h.machine.wind;
            h.world.setBlock(old.xCoord, old.yCoord, old.zCoord, Blocks.stone, 0, 2);
            h.world.setBlock(9, 74, 8, Blocks.stone, 0, 2);
            h.facing = 5;
            h.machine.onFacingChange();
            assertSame(Blocks.stone, h.world.getBlock(old.xCoord, old.yCoord, old.zCoord));
            assertSame(Blocks.stone, h.world.getBlock(9, 74, 8));
            assertNull(h.machine.wind);
        }

        public void anotherControllerCannotClaimOrDeleteRotor() {
            Harness first = new Harness(false, 8, 8);
            first.machine.setWind();
            TE_WindMill rotor = first.machine.wind;
            Harness second = new Harness(first.world, 7, 7);
            second.facing = 5;
            second.machine.setWind();
            assertNull(second.machine.wind);
            second.machine.inValidate();
            assertSame(rotor, first.world.getTileEntity(rotor.xCoord, rotor.yCoord, rotor.zCoord));
        }

        public void reloadRetainsOwnershipAndPreviousPosition() {
            Harness original = new Harness(false, 8, 8);
            original.machine.setWind();
            TE_WindMill rotor = original.machine.wind;
            rotor.setRGB(0x123456);
            rotor.setSpeed(25);
            rotor.setRotorHidden(false);
            NBTTagCompound rotorNbt = new NBTTagCompound();
            rotor.writeToNBT(rotorNbt);
            TE_WindMill restored = (TE_WindMill) TileEntity.createAndLoadEntity(rotorNbt);
            assertNotNull(restored);
            assertEquals(rotor.getFacing(), restored.getFacing());
            assertEquals(25, restored.getSpeed());
            assertEquals(0x123456, restored.getRGB());
            assertFalse(restored.hasRotorHidden());
            assertTrue(restored.isControlledBy(8, 64, 8));
            restored.setWorldObj(original.world);
            original.world.tiles.put(TestWorld.key(restored.xCoord, restored.yCoord, restored.zCoord), restored);
            NBTTagCompound machineNbt = new NBTTagCompound();
            original.machine.saveNBTData(machineNbt);
            Harness loaded = new Harness(original.world, 8, 8);
            loaded.machine.loadNBTData(machineNbt);
            loaded.facing = 5;
            loaded.machine.setWind();
            assertSame(Blocks.air, loaded.world.getBlock(rotor.xCoord, rotor.yCoord, rotor.zCoord));
            assertEquals(5, loaded.machine.wind.getFacing());
            assertEquals(1, loaded.world.tiles.size());
        }

        public void initialSyncIncludesAllVisualFieldsAndUnchangedStateSendsNothing() throws Exception {
            Harness h = new Harness(false, 8, 8);
            h.machine.setWind();
            TE_WindMill rotor = h.machine.wind;
            rotor.setSpeed(20);
            rotor.setRGB(0x123456);
            rotor.setRotorHidden(false);
            WorldData data = WorldData.get(h.world);
            data.networkedFieldsToUpdate.clear();
            IC2.network.get().sendInitialData(rotor, null);
            Set<String> fields = new HashSet<>();
            for (Object update : data.networkedFieldsToUpdate) {
                Field field = update.getClass().getDeclaredField("field");
                field.setAccessible(true);
                fields.add((String) field.get(update));
            }
            assertTrue(fields.contains("facing"));
            assertTrue(fields.contains("speed"));
            assertTrue(fields.contains("rgb"));
            assertTrue(fields.contains("isRotorHidden"));
            data.networkedFieldsToUpdate.clear();
            rotor.setSpeed(20);
            rotor.setRGB(0x123456);
            rotor.setRotorHidden(false);
            assertTrue(data.networkedFieldsToUpdate.isEmpty());
            rotor.setSpeed(0);
            assertEquals(1, data.networkedFieldsToUpdate.size());
        }

        public void legacyNbtRemainsReadable() {
            NBTTagCompound old = new NBTTagCompound();
            old.setShort("facing", (short) 4);
            old.setInteger("rgb", 0x654321);
            old.setBoolean("isRotorHidden", false);
            TE_WindMill rotor = new TE_WindMill();
            rotor.readFromNBT(old);
            assertEquals(4, rotor.getFacing());
            assertEquals(0x654321, rotor.getRGB());
            assertFalse(rotor.hasRotorHidden());
            assertEquals(0, rotor.getSpeed());
            assertTrue(rotor.attachController(8, 64, 8));
        }

        public void missingBusAndMalformedRotorDoNotCrash() {
            Harness h = new Harness(false, 8, 8);
            assertFalse(h.machine.checkRecipe(null));
            h.addRotor(1);
            h.bus.mInventory[0].setTagCompound(null);
            assertFalse(h.machine.checkRecipe(null));
            h.machine.setWind();
            h.machine.mMaxProgresstime = 8;
            h.machine.onPostTick(h.base, 40);
            assertTrue(h.machine.wind.hasRotorHidden());
            assertEquals(0, h.machine.wind.getSpeed());
            assertEquals(0, h.machine.mOutputSalary);
        }

        public void lastDurabilityPointBreaksWithoutGeneratingPower() {
            Harness h = new Harness(false, 8, 8);
            h.addRotor(1);
            h.machine.setWind();
            h.machine.mMaxProgresstime = 8;
            IC2.random.setSeed(0);
            h.machine.onPostTick(h.base, 40);
            assertNull(h.bus.mInventory[0]);
            assertEquals(0, h.machine.mOutputSalary);
            assertEquals(0, h.machine.mCapacity);
            assertTrue(h.machine.wind.hasRotorHidden());
            assertEquals(0, h.machine.wind.getSpeed());
        }

        public void turnWaitsForOldRotorChunkWithoutLoadingIt() {
            Harness h = new Harness(false, 15, 8);
            h.facing = 5;
            h.machine.setWind();
            TE_WindMill old = h.machine.wind;
            old.setSpeed(20);
            h.world.unloadedChunks.add("1:0");
            h.facing = 2;
            int writes = h.world.blockWrites;
            h.machine.onFacingChange();
            assertEquals(writes, h.world.blockWrites);
            h.world.unloadedChunks.clear();
            h.machine.setWind();
            assertSame(Blocks.air, h.world.getBlock(old.xCoord, old.yCoord, old.zCoord));
            assertEquals(2, h.machine.wind.getFacing());
            assertEquals(1, h.world.tiles.size());
        }

        public void orphanedRotorCleansUpAfterItsChunkLoads() throws Exception {
            Harness h = new Harness(false, 15, 8);
            h.facing = 5;
            h.machine.setWind();
            TE_WindMill orphan = h.machine.wind;
            h.world.unloadedChunks.add("1:0");
            h.machine.inValidate();
            assertSame(ItemRegistery.Wind_rotor, h.world.getBlock(orphan.xCoord, orphan.yCoord, orphan.zCoord));
            h.world.unloadedChunks.clear();
            java.lang.reflect.Method tick = TE_WindMill.class.getDeclaredMethod("updateEntityServer");
            tick.setAccessible(true);
            tick.invoke(orphan);
            assertSame(Blocks.air, h.world.getBlock(orphan.xCoord, orphan.yCoord, orphan.zCoord));
            assertTrue(h.world.tiles.isEmpty());
        }
    }

    public static class Harness {
        final TestWorld world;
        final TestMachine machine = new TestMachine();
        final IGregTechTileEntity base;
        GT_MetaTileEntity_Hatch_InputBus bus;
        byte facing = 2;
        boolean active;

        Harness(boolean client, int x, int z) { this(new TestWorld(client), x, z); }

        Harness(TestWorld world, int x, int z) {
            this.world = world;
            base = (IGregTechTileEntity) Proxy.newProxyInstance(IGregTechTileEntity.class.getClassLoader(),
                    new Class<?>[]{IGregTechTileEntity.class}, (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "isServerSide": return !world.isRemote;
                            case "isClientSide": return world.isRemote;
                            case "getWorld": return world;
                            case "getXCoord": return x;
                            case "getYCoord": return (short) 64;
                            case "getZCoord": return z;
                            case "getFrontFacing": return facing;
                            case "getBlockOffset": return world.getBlock(x + (int) args[0], 64 + (int) args[1], z + (int) args[2]);
                            case "getTileEntityOffset": return world.getTileEntity(x + (int) args[0], 64 + (int) args[1], z + (int) args[2]);
                            case "getMetaTileEntity": return machine;
                            case "isActive": return active;
                            case "setActive": active = (boolean) args[0]; return null;
                            case "getErrorDisplayID": return 0;
                            case "markDirty":
                            case "setMetaTileEntity":
                            case "setErrorDisplayID":
                            case "disableWorking": return null;
                            default: throw new AssertionError("Unexpected base call: " + method.getName());
                        }
                    });
            machine.setBaseMetaTileEntity(base);
        }

        void addRotor(int durability) {
            bus = new GT_MetaTileEntity_Hatch_InputBus("test_wind_bus", 1, new String[0], null);
            bus.setBaseMetaTileEntity(base);
            ItemStack stack = new ItemStack(new TestRotorItem());
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("rotorDamage", durability);
            stack.setTagCompound(tag);
            bus.mInventory[0] = stack;
            machine.mInputBusses.add(bus);
        }
    }

    public static class TestMachine extends GTMTE_Wind_Generator {
        TestMachine() {
            super("test_wind_generator");
            mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
            mStartUpCheck = 100;
        }

        @Override
        public void setExtendedFacing(ExtendedFacing facing) {
            // Alignment transport is independent of the rotor lifecycle under test.
        }
    }

    public static class TestRotorItem extends Item implements ITieredDamagedItems {
        @Override public int getCoefficient(ItemStack stack) { return 0; }
        @Override public boolean isBroken(ItemStack stack) { return stack.getTagCompound().getInteger("rotorDamage") <= 0; }
        @Override public Color getColor(ItemStack stack) { return new Color(0x123456); }
        @Override public boolean isNew(ItemStack stack) { return false; }
    }

    public static class TestWorld extends World {
        final Map<String, Block> blocks = new HashMap<>();
        final Map<String, TE_WindMill> tiles = new HashMap<>();
        final Set<String> unloadedChunks = new HashSet<>();
        int blockWrites;

        TestWorld(boolean client) {
            super(new SaveHandlerMP(), "wind-generator-test", new WorldProviderSurface(),
                    new WorldSettings(0, WorldSettings.GameType.CREATIVE, false, false, WorldType.DEFAULT), new Profiler());
            isRemote = client;
        }

        static String key(int x, int y, int z) { return x + ":" + y + ":" + z; }

        @Override public boolean blockExists(int x, int y, int z) {
            return y >= 0 && y < 256 && !unloadedChunks.contains((x >> 4) + ":" + (z >> 4));
        }
        @Override public Block getBlock(int x, int y, int z) { return blocks.getOrDefault(key(x, y, z), Blocks.air); }
        @Override public TileEntity getTileEntity(int x, int y, int z) { return tiles.get(key(x, y, z)); }
        @Override public int getBlockMetadata(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Must not access an unloaded rotor chunk");
            return 0;
        }
        @Override public void markTileEntityChunkModified(int x, int y, int z, TileEntity tile) {}
        @Override public void func_147453_f(int x, int y, int z, Block block) {}
        @Override public Entity getEntityByID(int id) { return null; }
        @Override protected int func_152379_p() { return 0; }
        @Override protected IChunkProvider createChunkProvider() { throw new UnsupportedOperationException(); }

        @Override
        public boolean setBlock(int x, int y, int z, Block block, int metadata, int flags) {
            assertFalse(isRemote, "Client must not mutate rotor blocks");
            blockWrites++;
            String key = key(x, y, z);
            blocks.put(key, block);
            tiles.remove(key);
            if (block == ItemRegistery.Wind_rotor) {
                TE_WindMill rotor = new TE_WindMill();
                rotor.setWorldObj(this);
                rotor.xCoord = x;
                rotor.yCoord = y;
                rotor.zCoord = z;
                tiles.put(key, rotor);
            }
            return true;
        }
    }
}
