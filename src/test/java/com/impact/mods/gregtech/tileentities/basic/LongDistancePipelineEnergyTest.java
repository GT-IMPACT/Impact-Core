package com.impact.mods.gregtech.tileentities.basic;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.impact.mods.gregtech.blocks.other.GT_Block_LongDistancePipe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicBatteryBuffer;
import gregtech.api.util.GT_Config;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.item.ElectricItemManager;
import ic2.core.item.GatewayElectricItemManager;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.config.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static gregtech.api.enums.GT_Values.V;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LongDistancePipelineEnergyTest {
    private LaunchClassLoader loader;
    private LaunchClassLoader previousLoader;
    private Object scenarios;

    @BeforeAll
    void initialize() throws Exception {
        previousLoader = Launch.classLoader;
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        Class<?> type = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios = type.getConstructor().newInstance();
        type.getMethod("initialize").invoke(scenarios);
    }

    @AfterAll
    void closeLoader() throws Exception {
        Launch.classLoader = previousLoader;
        if (loader != null) {
            loader.close();
        }
    }

    private void check(String method) throws Throwable {
        String previousName = Thread.currentThread().getName();
        try {
            Thread.currentThread().setName("Server thread");
            scenarios.getClass().getMethod(method).invoke(scenarios);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        } finally {
            Thread.currentThread().setName(previousName);
        }
    }

    @Test
    void restoredTargetIsSafeBeforePeriodicCheck() throws Throwable {
        check("restoredTargetIsSafeBeforePeriodicCheck");
    }

    @Test
    void failedTargetCheckBlocksEveryFollowingTick() throws Throwable {
        check("failedTargetCheckBlocksEveryFollowingTick");
    }

    @Test
    void disabledEndpointsAndClientCannotTransfer() throws Throwable {
        check("disabledEndpointsAndClientCannotTransfer");
    }

    @Test
    void transfersOnlyOwnBuffersAndReceiverDoesNotSend() throws Throwable {
        check("transfersOnlyOwnBuffersAndReceiverDoesNotSend");
    }

    @Test
    void partialPacketsRespectReserveCapacityAndAmperage() throws Throwable {
        check("partialPacketsRespectReserveCapacityAndAmperage");
    }

    @Test
    void unloadedOrReplacedReceiverCannotAccessOldBuffer() throws Throwable {
        check("unloadedOrReplacedReceiverCannotAccessOldBuffer");
    }

    @Test
    void differentTiersAndCompetingSendersCannotShareReceiver() throws Throwable {
        check("differentTiersAndCompetingSendersCannotShareReceiver");
    }

    @Test
    void blockUpdatesRotationAndRemovalDisconnectBothEnds() throws Throwable {
        check("blockUpdatesRotationAndRemovalDisconnectBothEnds");
    }

    @Test
    void localGtNetworkEnforcesSidesAndVoltage() throws Throwable {
        check("localGtNetworkEnforcesSidesAndVoltage");
    }

    @Test
    void scanFindsRouteWithoutPreexistingDistance() throws Throwable {
        check("scanFindsRouteWithoutPreexistingDistance");
    }

    @Test
    void branchesCannotBypassMinimumRouteLength() throws Throwable {
        check("branchesCannotBypassMinimumRouteLength");
    }

    @Test
    void scansDoNotLoadChunksAndRetryAfterTheyLoad() throws Throwable {
        check("scansDoNotLoadChunksAndRetryAfterTheyLoad");
    }

    @Test
    void nativeGtTickCanOutputSixteenAmperesAtStandardCost() throws Throwable {
        check("nativeGtTickCanOutputSixteenAmperesAtStandardCost");
    }

    @Test
    void ordinaryBatteryBufferKeepsNativeOutputCost() throws Throwable {
        check("ordinaryBatteryBufferKeepsNativeOutputCost");
    }

    @Test
    void nativeLvHandlesColdStartAndSavedRemainders() throws Throwable {
        check("nativeLvHandlesColdStartAndSavedRemainders");
    }

    @Test
    void nativeMvHandlesColdStartAndSavedRemainders() throws Throwable {
        check("nativeMvHandlesColdStartAndSavedRemainders");
    }

    @Test
    void nativeHvHandlesColdStartAndSavedRemainders() throws Throwable {
        check("nativeHvHandlesColdStartAndSavedRemainders");
    }

    @Test
    void nativeEvHandlesColdStartAndSavedRemainders() throws Throwable {
        check("nativeEvHandlesColdStartAndSavedRemainders");
    }

    @Test
    void nativeIvHandlesColdStartAndSavedRemainders() throws Throwable {
        check("nativeIvHandlesColdStartAndSavedRemainders");
    }

    @Test
    void nativeLvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Throwable {
        check("nativeLvBatteryChainMatchesDirectConnectionInEveryTickOrder");
    }

    @Test
    void nativeMvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Throwable {
        check("nativeMvBatteryChainMatchesDirectConnectionInEveryTickOrder");
    }

    @Test
    void nativeHvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Throwable {
        check("nativeHvBatteryChainMatchesDirectConnectionInEveryTickOrder");
    }

    @Test
    void nativeEvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Throwable {
        check("nativeEvBatteryChainMatchesDirectConnectionInEveryTickOrder");
    }

    @Test
    void nativeIvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Throwable {
        check("nativeIvBatteryChainMatchesDirectConnectionInEveryTickOrder");
    }

    public static class Scenarios {
        private GT_Block_LongDistancePipe pipe;

        public void initialize() throws Exception {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            Launch.classLoader = (LaunchClassLoader) getClass().getClassLoader();
            LoadController controller = new LoadController(Loader.instance());
            setField(LoadController.class, controller, "activeContainer", new DummyModContainer(new ModMetadata()));
            setField(Loader.class, Loader.instance(), "modController", controller);
            setField(FMLRelaunchLog.class, null, "side", Side.SERVER);
            setField(FMLCommonHandler.class, FMLCommonHandler.instance(), "sidedDelegate",
                    Proxy.newProxyInstance(IFMLSidedHandler.class.getClassLoader(),
                            new Class<?>[] { IFMLSidedHandler.class }, (proxy, method, args) -> {
                                return method.getName().equals("getSide") ? Side.SERVER : null;
                            }));
            GregTech_API.sMachineFile = new GT_Config(new Configuration() {
                @Override
                public void load() {
                }

                @Override
                public void save() {
                }
            });
            ElectricItem.rawManager = new ElectricItemManager();
            ElectricItem.manager = new GatewayElectricItemManager();
            Info.itemEnergy = stack -> 0D;
            pipe = new GT_Block_LongDistancePipe();
            Files.write(Paths.get("build/energy-pipeline-tier-matrix.csv"),
                    Collections.singleton("tier,tickOrder,inputEU,deliveredEU,storedInPipelineEU,lineOutputCostEU,"
                            + "lineLossEU,sourceOutputCostEU,directInputEU"),
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        private static void setField(Class<?> type, Object owner, String name, Object value) throws Exception {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(owner, value);
        }

        public void restoredTargetIsSafeBeforePeriodicCheck() {
            Harness h = new Harness();
            NBTTagCompound saved = new NBTTagCompound();
            h.sender.saveNBTData(saved);
            h.sender.loadNBTData(saved);
            assertNull(h.sender.mTarget);
            assertEquals(32L, h.sender.mDistance);
            h.sender.setEUVar(608L);
            h.sender.onPostTick(h.source, 1L);
            assertEquals(512L, h.sender.getEUVar());
            assertEquals(96L, h.receiver.getEUVar());
            assertSame(h.sender, h.receiver.mSender);
        }

        public void failedTargetCheckBlocksEveryFollowingTick() {
            Harness h = new Harness();
            h.sender.setEUVar(1_024L);
            h.world.tiles.remove(h.receiver.getCoords());
            for (long tick = 1L; tick <= 41L; tick++) {
                h.sender.onPostTick(h.source, tick);
            }
            assertEquals(1_024L, h.sender.getEUVar());
            assertEquals(0L, h.receiver.getEUVar());
            assertNull(h.receiver.mSender);
        }

        public void disabledEndpointsAndClientCannotTransfer() {
            Harness h = new Harness();
            h.sender.setEUVar(1_024L);
            h.source.allowed = false;
            for (long tick = 1L; tick < 20L; tick++) {
                h.sender.onPostTick(h.source, tick);
            }
            assertFalse(h.sender.isInputFacing((byte) 4));
            assertFalse(h.sender.isEnetInput());
            h.source.allowed = true;
            h.destination.allowed = false;
            h.sender.onPostTick(h.source, 20L);
            h.sender.onPostTick(h.source, 21L);
            assertFalse(h.receiver.isOutputFacing((byte) 5));
            assertFalse(h.receiver.isEnetOutput());
            h.destination.allowed = true;
            h.world.isRemote = true;
            h.sender.onPostTick(h.source, 22L);
            h.world.isRemote = false;
            assertEquals(1_024L, h.sender.getEUVar());
            assertEquals(0L, h.receiver.getEUVar());
            h.sender.onPostTick(h.source, 23L);
            assertEquals(512L, h.sender.getEUVar());
            assertEquals(512L, h.receiver.getEUVar());
        }

        public void transfersOnlyOwnBuffersAndReceiverDoesNotSend() {
            Harness h = new Harness();
            GTMTE_LongDistancePipelineEnergy neighbor = h.machine(1, -1, 0);
            neighbor.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 1L);
            assertEquals(1_024L, neighbor.getEUVar());
            assertEquals(0L, h.receiver.getEUVar());
            h.sender.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 2L);
            assertEquals(512L, h.receiver.getEUVar());
            h.receiver.onPostTick(h.destination, 1L);
            h.receiver.onPostTick(h.destination, 20L);
            assertEquals(512L, h.receiver.getEUVar());
            assertEquals(512L, h.sender.getEUVar());
            assertFalse(h.receiver.isSender());
            assertTrue(h.world.chunk.isModified);
        }

        public void partialPacketsRespectReserveCapacityAndAmperage() {
            Harness h = new Harness();
            h.sender.setEUVar(607L);
            h.sender.onPostTick(h.source, 1L);
            assertEquals(543L, h.sender.getEUVar());
            assertEquals(64L, h.receiver.getEUVar());
            assertEquals(607L, h.sender.getEUVar() + h.receiver.getEUVar());

            h.sender.setEUVar(1_024L);
            h.receiver.setEUVar(h.receiver.maxEUStore() - 63L);
            h.sender.onPostTick(h.source, 2L);
            assertEquals(992L, h.sender.getEUVar());
            assertEquals(h.receiver.maxEUStore() - 31L, h.receiver.getEUVar());
            h.sender.onPostTick(h.source, 3L);
            assertEquals(992L, h.sender.getEUVar());

            h.sender.setEUVar(2_000L);
            h.receiver.setEUVar(0L);
            h.sender.onPostTick(h.source, 4L);
            assertEquals(1_488L, h.sender.getEUVar());
            assertEquals(512L, h.receiver.getEUVar());
        }

        public void unloadedOrReplacedReceiverCannotAccessOldBuffer() {
            Harness h = new Harness();
            h.sender.setEUVar(1_024L);
            h.world.unloadedChunks.add(2);
            h.sender.onPostTick(h.source, 1L);
            h.sender.onPostTick(h.source, 2L);
            assertEquals(0L, h.receiver.getEUVar());
            h.world.unloadedChunks.clear();
            h.sender.onPostTick(h.source, 21L);
            assertEquals(512L, h.receiver.getEUVar());

            h.sender.setEUVar(1_024L);
            GTMTE_LongDistancePipelineEnergy replacement = h.machine(1, 33, 0);
            h.sender.onPostTick(h.source, 22L);
            assertEquals(0L, replacement.getEUVar());
            assertEquals(512L, h.receiver.getEUVar());
            h.sender.onPostTick(h.source, 41L);
            assertEquals(512L, replacement.getEUVar());
            assertNull(h.receiver.mSender);
            assertSame(h.sender, replacement.mSender);
        }

        public void differentTiersAndCompetingSendersCannotShareReceiver() {
            Harness h = new Harness();
            assertFalse(h.sender.isSameClass(h.machine(2, 40, 0)));
            GTMTE_LongDistancePipelineEnergy competitor = h.machine(1, 0, 1);
            h.remember(competitor, h.receiver);
            competitor.setEUVar(1_024L);
            competitor.onPostTick(competitor.getBaseMetaTileEntity(), 1L);
            competitor.onPostTick(competitor.getBaseMetaTileEntity(), 2L);
            assertEquals(0L, h.receiver.getEUVar());
            assertSame(h.sender, h.receiver.mSender);

            GTMTE_LongDistancePipelineEnergy wrongTier = h.machine(2, 33, 0);
            h.sender.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 1L);
            h.sender.onPostTick(h.source, 2L);
            assertEquals(0L, wrongTier.getEUVar());
            assertEquals(1_024L, h.sender.getEUVar());
        }

        public void blockUpdatesRotationAndRemovalDisconnectBothEnds() {
            for (int event = 0; event < 4; event++) {
                Harness h = new Harness();
                h.sender.setEUVar(1_024L);
                if (event == 0) {
                    h.sender.onMachineBlockUpdate();
                } else if (event == 1) {
                    h.receiver.onFacingChange();
                } else if (event == 2) {
                    h.receiver.onRemoval();
                    h.world.tiles.remove(h.receiver.getCoords());
                } else {
                    h.sender.inValidate();
                    h.world.tiles.remove(h.sender.getCoords());
                }
                assertNull(h.receiver.mSender);
                assertNull(h.sender.mTarget);
                for (long tick = 2L; tick <= 41L; tick++) {
                    h.sender.onPostTick(h.source, tick);
                }
                assertEquals(1_024L, h.sender.getEUVar());
                assertEquals(0L, h.receiver.getEUVar());
            }
        }

        public void localGtNetworkEnforcesSidesAndVoltage() {
            Harness h = new Harness();
            assertEquals(0L, h.source.injectEnergyUnits((byte) 5, 32L, 1L));
            assertEquals(0L, h.destination.injectEnergyUnits((byte) 4, 32L, 1L));
            assertEquals(1L, h.source.injectEnergyUnits((byte) 4, 32L, 1L));
            assertEquals(32L, h.sender.getEUVar());
            assertFalse(h.sender.isEnetOutput());
            assertTrue(h.receiver.isEnetOutput());

            GTMTE_LongDistancePipelineEnergy consumer = h.machine(1, 34, 0);
            h.receiver.setEUVar(1_024L);
            assertEquals(2L, IEnergyConnected.Util.emitEnergyToNetwork(32L, 2L, h.destination));
            assertEquals(64L, consumer.getEUVar());

            Harness overvoltage = new Harness();
            assertEquals(0L, overvoltage.source.injectEnergyUnits((byte) 4, 128L, 1L));
            assertEquals(1, overvoltage.source.explosions);
            assertEquals(0L, overvoltage.sender.getEUVar());
        }

        public void scanFindsRouteWithoutPreexistingDistance() {
            Harness h = route(32);
            h.sender.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 1L);
            assertEquals(32L, h.sender.getDistance());
            assertSame(h.receiver, h.sender.mTarget);
            assertEquals(512L, h.receiver.getEUVar());
            assertFalse(h.receiver.isSender());
        }

        public void branchesCannotBypassMinimumRouteLength() {
            Harness h = route(31);
            for (int z = 1; z < 45; z++) {
                h.world.pipes.add(new ChunkCoordinates(1, 64, z));
            }
            h.sender.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 1L);
            h.sender.onPostTick(h.source, 2L);
            assertEquals(31L, h.sender.getDistance());
            assertEquals(0L, h.receiver.getEUVar());
            assertEquals(1_024L, h.sender.getEUVar());
        }

        public void scansDoNotLoadChunksAndRetryAfterTheyLoad() {
            Harness h = route(32);
            h.world.unloadedChunks.add(1);
            h.sender.setEUVar(1_024L);
            h.sender.onPostTick(h.source, 1L);
            int reads = h.world.blockReads;
            for (long tick = 2L; tick <= 20L; tick++) {
                h.sender.onPostTick(h.source, tick);
            }
            assertEquals(reads, h.world.blockReads);
            assertEquals(0L, h.receiver.getEUVar());
            h.world.unloadedChunks.clear();
            h.sender.onPostTick(h.source, 21L);
            assertEquals(512L, h.receiver.getEUVar());
        }

        public void nativeGtTickCanOutputSixteenAmperesAtStandardCost() throws Exception {
            for (int tier = 1; tier <= 5; tier++) {
                long voltage = V[tier];
                long outputPacketCost = voltage + (1L << tier);
                for (long remainder : new long[] { 0L, 1L, outputPacketCost - 1L }) {
                    Harness h = new Harness(33, tier);
                    GTMTE_LongDistancePipelineEnergy consumer = h.machine(tier, 34, 0);
                    long reserve = h.receiver.getMinimumStoredEU();
                    h.receiver.setEUVar(reserve + 16L * outputPacketCost + remainder);
                    prepareNativeTick(h.destination);
                    h.destination.updateEntity();
                    assertEquals(16L * voltage, consumer.getEUVar(), "Full rated burst output, tier " + tier);
                    assertEquals(reserve + remainder, h.receiver.getEUVar(), "GT debits its native packet overhead");
                }
            }
        }

        public void ordinaryBatteryBufferKeepsNativeOutputCost() throws Exception {
            Harness h = new Harness();
            GT_MetaTileEntity_BasicBatteryBuffer battery = new GT_MetaTileEntity_BasicBatteryBuffer("test.battery", 3,
                    "", null, 16);
            TestTile source = new TestTile();
            source.setWorldObj(h.world);
            source.xCoord = 40;
            source.yCoord = 64;
            battery.setBaseMetaTileEntity(source);
            h.world.tiles.put(new ChunkCoordinates(40, 64, 0), source);
            GTMTE_LongDistancePipelineEnergy consumer = h.machine(3, 41, 0);
            source.front = 5;
            battery.mBatteryCount = 16;
            long reserve = battery.getMinimumStoredEU();
            battery.setEUVar(reserve + 16L * 520L);
            prepareNativeTick(source);
            source.updateEntity();
            assertEquals(16L * 512L, consumer.getEUVar());
            assertEquals(reserve, battery.getEUVar());
        }

        public void nativeLvHandlesColdStartAndSavedRemainders() throws Exception {
            verifyColdStartAndSavedRemainders(1);
        }

        public void nativeMvHandlesColdStartAndSavedRemainders() throws Exception {
            verifyColdStartAndSavedRemainders(2);
        }

        public void nativeHvHandlesColdStartAndSavedRemainders() throws Exception {
            verifyColdStartAndSavedRemainders(3);
        }

        public void nativeEvHandlesColdStartAndSavedRemainders() throws Exception {
            verifyColdStartAndSavedRemainders(4);
        }

        public void nativeIvHandlesColdStartAndSavedRemainders() throws Exception {
            verifyColdStartAndSavedRemainders(5);
        }

        public void nativeLvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Exception {
            verifyNativeBatteryChain(1);
        }

        public void nativeMvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Exception {
            verifyNativeBatteryChain(2);
        }

        public void nativeHvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Exception {
            verifyNativeBatteryChain(3);
        }

        public void nativeEvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Exception {
            verifyNativeBatteryChain(4);
        }

        public void nativeIvBatteryChainMatchesDirectConnectionInEveryTickOrder() throws Exception {
            verifyNativeBatteryChain(5);
        }

        private void verifyNativeBatteryChain(int tier) throws Exception {
            for (int first = 0; first < 4; first++) {
                for (int second = 0; second < 4; second++) {
                    if (second == first) {
                        continue;
                    }
                    for (int third = 0; third < 4; third++) {
                        if (third == first || third == second) {
                            continue;
                        }
                        int fourth = 6 - first - second - third;
                        int[] order = { first, second, third, fourth };
                        NativeChain chain = new NativeChain(tier, true, pipe);
                        NativeChain direct = new NativeChain(tier, false, pipe);
                        String orderName = "" + first + second + third + fourth;
                        for (long tick = 1; tick <= 1_000L; tick++) {
                            for (int node : order) {
                                if (chain.nodes[node] != null) {
                                    chain.nodes[node].updateEntity();
                                }
                                if (direct.nodes[node] != null) {
                                    direct.nodes[node].updateEntity();
                                }
                            }
                            String context = "tier=" + tier + ", order=" + orderName + ", tick=" + tick;
                            long stored = chain.sender.getEUVar() + chain.receiver.getEUVar();
                            long outputPacketCost = V[tier] + (1L << tier);
                            assertEquals(chain.nodes[3].totalInjected / V[tier] * outputPacketCost,
                                    chain.nodes[2].totalOutputCost, "Receiver's native output cost: " + context);
                            assertEquals(chain.nodes[1].totalInjected, chain.nodes[2].totalOutputCost + stored,
                                    "Line energy conservation: " + context);
                            assertEquals(direct.nodes[3].totalInjected, chain.nodes[1].totalInjected,
                                    "Pipeline must accept the same source packets as the direct connection: "
                                            + context);
                            assertTrue(chain.nodes[2].lastOutputCost <= 16L * outputPacketCost, context);
                            assertEquals(chain.nodes[1].totalInjected / V[tier] * (V[tier] + (1L << tier)),
                                    chain.nodes[0].totalOutputCost, "Source battery's native output cost: " + context);
                        }
                        assertTrue(chain.nodes[3].totalInjected > 100L * 16L * V[tier], "The chain must actually run");
                        Files.write(Paths.get("build/energy-pipeline-tier-matrix.csv"),
                                Collections.singleton(tier + "," + orderName + "," + chain.nodes[1].totalInjected + ","
                                        + chain.nodes[3].totalInjected + ","
                                        + (chain.sender.getEUVar() + chain.receiver.getEUVar()) + ","
                                        + chain.nodes[2].totalOutputCost + ","
                                        + (chain.nodes[2].totalOutputCost - chain.nodes[3].totalInjected) + ","
                                        + chain.nodes[0].totalOutputCost + "," + direct.nodes[3].totalInjected),
                                StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    }
                }
            }
        }

        private void verifyColdStartAndSavedRemainders(int tier) throws Exception {
            long voltage = V[tier];
            long outputPacketCost = voltage + (1L << tier);
            for (long initial : new long[] { 0L, 1L, voltage - 1L, 511L, 512L, voltage + 1L }) {
                for (long amperage : new long[] { 1L, 8L, 16L }) {
                    Harness h = new Harness(33, tier);
                    GTMTE_LongDistancePipelineEnergy consumer = h.machine(tier, 34, 0);
                    TestTile consumerTile = (TestTile) consumer.getBaseMetaTileEntity();
                    h.sender.setEUVar(initial);
                    h.receiver.setEUVar(initial);
                    prepareNativeTick(h.destination);
                    long injected = 0L;
                    long delivered = 0L;
                    long deliveredBeforeMeasurement = 0L;
                    for (long tick = 1L; tick <= 300L; tick++) {
                        setField(BaseMetaTileEntity.class, h.source, "mAcceptedAmperes", 0L);
                        setField(BaseMetaTileEntity.class, consumerTile, "mAcceptedAmperes", 0L);
                        consumer.setEUVar(0L);
                        long accepted = h.source.injectEnergyUnits((byte) 4, voltage, amperage);
                        injected += accepted * voltage;
                        h.sender.onPostTick(h.source, tick);
                        h.destination.updateEntity();
                        delivered += consumer.getEUVar();
                        long outputCost = delivered / voltage * outputPacketCost;
                        assertEquals(2L * initial + injected, outputCost + h.sender.getEUVar() + h.receiver.getEUVar(),
                                "Energy conservation");
                        assertTrue(consumer.getEUVar() <= 16L * voltage, "The native output is limited to 16 A");
                        if (tick == 100L) {
                            deliveredBeforeMeasurement = delivered;
                        }
                        if (tick > 100L) {
                            String context = "tier=" + tier + ", initial=" + initial + ", amperage=" + amperage
                                    + ", tick=" + tick;
                            assertEquals(amperage, accepted, context);
                        }
                    }
                    double expectedAverage = amperage * voltage * (double) voltage / outputPacketCost;
                    double measuredAverage = (delivered - deliveredBeforeMeasurement) / 200D;
                    assertEquals(expectedAverage, measuredAverage, voltage / 200D + 1E-8,
                            "Steady flow must include exactly the GT output loss, tier=" + tier + ", initial=" + initial
                                    + ", amperage=" + amperage);
                }
            }
        }

        private static void prepareNativeTick(BaseMetaTileEntity tile) throws Exception {
            setField(BaseMetaTileEntity.class, tile, "mTickTimer", 100L);
            setField(BaseMetaTileEntity.class, tile, "mFacing", tile.getFrontFacing());
            setField(BaseMetaTileEntity.class, tile, "oFacing", tile.getFrontFacing());
            setField(BaseMetaTileEntity.class, tile, "mNeedsBlockUpdate", false);
            setField(BaseMetaTileEntity.class, tile, "mSendClientData", false);
            setField(BaseMetaTileEntity.class, tile, "oTextureData", (byte) (tile.getFrontFacing() | 64));
            setField(BaseMetaTileEntity.class, tile, "oLightValue", (byte) 0);
            setField(BaseMetaTileEntity.class, tile, "oX", tile.xCoord);
            setField(BaseMetaTileEntity.class, tile, "oY", tile.yCoord);
            setField(BaseMetaTileEntity.class, tile, "oZ", tile.zCoord);
            setField(BaseMetaTileEntity.class, tile, "cableUpdateDelay", -1);
        }

        private Harness route(int length) {
            Harness h = new Harness(length + 1);
            h.sender.onMachineBlockUpdate();
            h.world.pipe = pipe;
            for (int x = 1; x <= length; x++) {
                h.world.pipes.add(new ChunkCoordinates(x, 64, 0));
            }
            return h;
        }
    }

    public static class Harness {
        final TestWorld world = new TestWorld();
        final GTMTE_LongDistancePipelineEnergy sender;
        final GTMTE_LongDistancePipelineEnergy receiver;
        final TestTile source;
        final TestTile destination;

        Harness() {
            this(33);
        }

        Harness(int targetX) {
            this(targetX, 1);
        }

        Harness(int targetX, int tier) {
            sender = machine(tier, 0, 0);
            receiver = machine(tier, targetX, 0);
            source = (TestTile) sender.getBaseMetaTileEntity();
            destination = (TestTile) receiver.getBaseMetaTileEntity();
            remember(sender, receiver);
            receiver.mSender = sender;
            receiver.mDistance = 32L;
        }

        void remember(GTMTE_LongDistancePipelineEnergy from, GTMTE_LongDistancePipelineEnergy to) {
            from.mTarget = to;
            from.mTargetPos = to.getCoords();
            from.mDistance = 32L;
        }

        GTMTE_LongDistancePipelineEnergy machine(int tier, int x, int z) {
            GTMTE_LongDistancePipelineEnergy machine = new GTMTE_LongDistancePipelineEnergy("test.pipeline", tier, "",
                    null);
            TestTile tile = new TestTile();
            tile.setWorldObj(world);
            tile.xCoord = x;
            tile.yCoord = 64;
            tile.zCoord = z;
            machine.setBaseMetaTileEntity(tile);
            world.tiles.put(machine.getCoords(), tile);
            return machine;
        }
    }

    public static class TestTile extends BaseMetaTileEntity {
        boolean allowed = true;
        int explosions;
        byte front = 4;

        TestTile() {
            // BaseMetaTileEntity normally resets its per-tick input budget in updateEntity().
            try {
                Field accepted = BaseMetaTileEntity.class.getDeclaredField("mAcceptedAmperes");
                accepted.setAccessible(true);
                accepted.setLong(this, 0L);
            } catch (ReflectiveOperationException failure) {
                throw new AssertionError(failure);
            }
        }

        @Override
        public boolean isAllowedToWork() {
            return allowed;
        }

        @Override
        public byte getFrontFacing() {
            return front;
        }

        @Override
        public byte getBackFacing() {
            return (byte) (front ^ 1);
        }

        @Override
        public boolean inputEnergyFrom(byte side, boolean waitForActive) {
            return super.inputEnergyFrom(side, false);
        }

        @Override
        public boolean outputsEnergyTo(byte side, boolean waitForActive) {
            return super.outputsEnergyTo(side, false);
        }

        @Override
        public void doExplosion(long amount) {
            explosions++;
        }
    }

    /** Real GT tick, sided input masks and input budgets; overrides only record energy crossing the API. */
    public static class NativeTile extends BaseMetaTileEntity {
        long totalInjected;
        long totalOutputCost;
        long lastOutputCost;

        @Override
        public long injectEnergyUnits(byte side, long voltage, long amperage) {
            long accepted = super.injectEnergyUnits(side, voltage, amperage);
            totalInjected += accepted * voltage;
            return accepted;
        }

        @Override
        public void updateEntity() {
            super.updateEntity();
            lastOutputCost = mAverageEUOutput[mAverageEUOutputIndex];
            totalOutputCost += lastOutputCost;
        }
    }

    /** Batteries use the real IC2 managers and NBT charge; no per-tick refilling or forced amp counters. */
    public static class BatteryItem extends Item implements IElectricItem {
        private final int tier;

        BatteryItem(int tier) {
            this.tier = tier;
            setMaxStackSize(1);
        }

        @Override
        public boolean canProvideEnergy(ItemStack stack) {
            return true;
        }

        @Override
        public Item getChargedItem(ItemStack stack) {
            return this;
        }

        @Override
        public Item getEmptyItem(ItemStack stack) {
            return this;
        }

        @Override
        public double getMaxCharge(ItemStack stack) {
            return V[tier] * 1_000_000D;
        }

        @Override
        public int getTier(ItemStack stack) {
            return tier;
        }

        @Override
        public double getTransferLimit(ItemStack stack) {
            return V[tier];
        }
    }

    public static class NativeChain {
        final TestWorld world = new TestWorld();
        final NativeTile[] nodes = new NativeTile[4];
        GTMTE_LongDistancePipelineEnergy sender;
        GTMTE_LongDistancePipelineEnergy receiver;

        NativeChain(int tier, boolean pipeline, GT_Block_LongDistancePipe pipe) throws Exception {
            nodes[0] = battery(tier, -1, true);
            nodes[3] = battery(tier, pipeline ? 34 : 0, false);
            if (pipeline) {
                sender = new GTMTE_LongDistancePipelineEnergy("test.native.sender", tier, "", null);
                receiver = new GTMTE_LongDistancePipelineEnergy("test.native.receiver", tier, "", null);
                nodes[1] = tile(sender, 0, (byte) 4);
                nodes[2] = tile(receiver, 33, (byte) 4);
                world.pipe = pipe;
                world.pipeMetadata = tier + 1;
                for (int x = 1; x <= 32; x++) {
                    world.pipes.add(new ChunkCoordinates(x, 64, 0));
                }
            }
        }

        private NativeTile battery(int tier, int x, boolean charged) throws Exception {
            GT_MetaTileEntity_BasicBatteryBuffer battery = new GT_MetaTileEntity_BasicBatteryBuffer(
                    "test.native.battery", tier, "", null, 16);
            NativeTile tile = tile(battery, x, (byte) 5);
            BatteryItem item = new BatteryItem(tier);
            for (int slot = 0; slot < battery.mInventory.length; slot++) {
                ItemStack stack = new ItemStack(item);
                if (charged) {
                    ElectricItem.manager.charge(stack, item.getMaxCharge(stack), tier, true, false);
                }
                battery.mInventory[slot] = stack;
            }
            return tile;
        }

        private NativeTile tile(MetaTileEntity meta, int x, byte facing) throws Exception {
            NativeTile tile = new NativeTile();
            tile.setWorldObj(world);
            tile.xCoord = x;
            tile.yCoord = 64;
            meta.setBaseMetaTileEntity(tile);
            Scenarios.setField(BaseMetaTileEntity.class, tile, "mFacing", facing);
            Scenarios.setField(BaseMetaTileEntity.class, tile, "oFacing", facing);
            Scenarios.prepareNativeTick(tile);
            world.tiles.put(new ChunkCoordinates(x, 64, 0), tile);
            return tile;
        }
    }

    public static class TestWorld extends World {
        final Map<ChunkCoordinates, TileEntity> tiles = new HashMap<>();
        final Set<ChunkCoordinates> pipes = new HashSet<>();
        final Set<Integer> unloadedChunks = new HashSet<>();
        final Chunk chunk = new Chunk(this, 0, 0);
        GT_Block_LongDistancePipe pipe;
        int pipeMetadata = 2;
        int blockReads;

        TestWorld() {
            super(new SaveHandlerMP(), "energy-pipeline-test", new WorldProviderSurface(),
                    new WorldSettings(0, WorldSettings.GameType.CREATIVE, false, false, WorldType.DEFAULT),
                    new Profiler());
        }

        @Override
        public boolean blockExists(int x, int y, int z) {
            return y >= 0 && y < 256 && !unloadedChunks.contains(x >> 4);
        }

        @Override
        public TileEntity getTileEntity(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Tile lookup must not load chunks");
            return tiles.get(new ChunkCoordinates(x, y, z));
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Pipe scanning must not load chunks");
            blockReads++;
            return pipes.contains(new ChunkCoordinates(x, y, z)) ? pipe : Blocks.air;
        }

        @Override
        public int getBlockMetadata(int x, int y, int z) {
            assertTrue(blockExists(x, y, z));
            return pipes.contains(new ChunkCoordinates(x, y, z)) ? pipeMetadata : 0;
        }

        @Override
        public Chunk getChunkFromBlockCoords(int x, int z) {
            assertTrue(blockExists(x, 64, z));
            return chunk;
        }

        @Override
        public int getPrecipitationHeight(int x, int z) {
            return 256;
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
        protected IChunkProvider createChunkProvider() {
            throw new UnsupportedOperationException();
        }
    }

}
