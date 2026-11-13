package com.impact.mods.gregtech;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.impact.addon.gt.api.parallel_system.SatelliteNetworkLogic;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.parallel_system.NetworkDiagnostics;
import com.impact.addon.gt.api.satellite.SatelliteDirectory;
import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.satellite.SatelliteNetworkManager;
import com.impact.events.NetworkNodeEvents;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_SpaceSatellite_Transmitter;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Mixer;
import com.impact.network.special.TowerConnectionsPacket;
import com.impact.util.PositionObject;
import com.impact.core.CommonProxy;
import com.impact.core.SaveManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.MetaTileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.ChunkEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NetworkSystemTest {

    private LaunchClassLoader loader;
    private Class<?> scenarios;

    @BeforeAll
    void initialize() throws Exception {
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        scenarios = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios.getMethod("initialize").invoke(null);
    }

    @AfterEach
    void reset() throws Exception {
        scenarios.getMethod("reset").invoke(null);
    }

    @AfterAll
    void closeLoader() throws Exception {
        if (loader != null) {
            loader.close();
        }
    }

    private void run(String method) throws Throwable {
        try {
            scenarios.getMethod(method).invoke(null);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        }
    }

    @Test
    void overlappingTowersDoNotOverwriteEachOther() throws Throwable {
        run("overlappingTowers");
    }

    @Test
    void changesOnlyRefreshNearbyTowersWithoutLoadingChunks() throws Throwable {
        run("localUpdates");
    }

    @Test
    void invalidationsDuringUpdateAndFailuresRemainQueued() throws Throwable {
        run("queuedUpdates");
    }

    @Test
    void dimensionsAndReplacedMachineInstancesStayIsolated() throws Throwable {
        run("dimensionsAndReplacement");
    }

    @Test
    void satelliteRegistrationIsNotAnActiveConnection() throws Throwable {
        run("satelliteStatus");
    }

    @Test
    void unavailableSatelliteAndFrequencyChangesCanReconnect() throws Throwable {
        run("satelliteRetry");
    }

    @Test
    void disconnectedSatelliteTargetSurvivesSavingAndReplacement() throws Throwable {
        run("satelliteSaveAndReplacement");
    }

    @Test
    void chunkEventsDisconnectAndRestoreAllNetworkLayers() throws Throwable {
        run("chunkLifecycle");
    }

    @Test
    void allocationIsStableAndEmptyTransmittersDoNotReservePoints() throws Throwable {
        run("allocation");
    }

    @Test
    void unloadingSatelliteControllerDisablesTransmittersInOtherChunks() throws Throwable {
        run("satelliteControllerUnload");
    }

    @Test
    void parallelLinksRetryAndRejectAnotherDimension() throws Throwable {
        run("parallelRetryAndDimension");
    }

    @Test
    void towerPowerAndReceiverLossPropagateImmediately() throws Throwable {
        run("towerAvailability");
    }

    @Test
    void failingHandlerDoesNotStopHealthyNodesAndRetriesAfterDelay() throws Throwable {
        run("handlerFailureIsolation");
    }

    @Test
    void aFailingSubscriberCannotDisableOtherSatelliteReceivers() throws Throwable {
        run("subscriberFailureIsolation");
    }

    @Test
    void centralTickPropagatesTheWholeSatelliteToParallelChain() throws Throwable {
        run("centralTickChain");
    }

    @Test
    void towerSelectsAndSwitchesSatellitesAcrossDimensions() throws Throwable {
        run("towerSatelliteSelection");
    }

    @Test
    void serverStartedDoesNotEraseConnectionsRegisteredByChunkLoading() throws Throwable {
        run("legacyStartupRegistrations");
    }

    @Test
    void alreadyLoadedWorldRestoresLegacyBindingsWithoutAnotherFirstTick() throws Throwable {
        run("legacyLoadedWorldBootstrap");
    }

    @Test
    void towerOverlayPacketPreservesBatchedLinksAndCanClearThem() throws Throwable {
        run("towerOverlayPacket");
    }

    public static final class Scenarios {

        public static void initialize() throws Exception {
            ImpactProcessingMigrationTest.Scenarios.initialize();
        }

        public static void reset() {
            NetworkNodeLifecycle.clear();
            SatelliteNetworkManager.INSTANCE.reload();
            SatelliteNetworkLogic.INSTANCE.onStopServer();
        }

        public static void towerOverlayPacket() throws Exception {
            int[] links = {-300, 64, 500, 1, 18, 121, -330, 0, 0, 0, 0, 0};
            TowerConnectionsPacket snapshot = TowerConnectionsPacket.snapshot(links, 2);
            links[0] = 0;

            ByteBuf buffer = Unpooled.buffer();
            try (ByteBufOutputStream output = new ByteBufOutputStream(buffer)) {
                snapshot.encode(output);
                TowerConnectionsPacket.empty().encode(output);

                byte[] payload = new byte[buffer.readableBytes()];
                buffer.readBytes(payload);
                ByteArrayDataInput input = ByteStreams.newDataInput(payload);
                TowerConnectionsPacket decoded = TowerConnectionsPacket.TYPE.decode(input);
                assertTrue(decoded.hasSameLinks(snapshot));
                assertEquals(2, decoded.getLinkCount());
                assertEquals(-300, decoded.getLinkX(0));
                assertEquals(121, decoded.getLinkY(1));
                assertEquals(-330, decoded.getLinkZ(1));
                assertTrue(decoded.isLinkActive(0));
                assertFalse(decoded.isLinkActive(1));
                assertEquals(0, TowerConnectionsPacket.TYPE.decode(input).getLinkCount());
            } finally {
                buffer.release();
            }
        }

        private static ImpactConstructionChecks.TestWorld world() {
            return new ImpactConstructionChecks.TestWorld() {
                @Override
                public Chunk getChunkFromChunkCoords(int x, int z) {
                    throw new AssertionError("Network must not request chunks");
                }
            };
        }

        private static ImpactConstructionChecks.TestTile place(
                ImpactConstructionChecks.TestWorld world,
                MetaTileEntity node,
                int x,
                int y,
                int z
        ) {
            ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                    world, node, ForgeDirection.NORTH);
            world.putTile(x, y, z, tile);
            return tile;
        }

        public static void overlappingTowers() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_TowerCommunication first = new GTMTE_TowerCommunication("first");
            GTMTE_TowerCommunication second = new GTMTE_TowerCommunication("second");
            GTMTE_Mixer machine = new GTMTE_Mixer("machine");
            NetworkNodeEvents.load(place(world, first, 0, 64, 0));
            NetworkNodeEvents.load(place(world, second, 128, 64, 0));
            NetworkNodeEvents.load(place(world, machine, 64, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertFalse(first.getConnections().contains(second));
            assertThrows(UnsupportedOperationException.class, first.getConnections()::clear);
            first.setSatelliteConnected(true);
            second.updateStatusConnections();
            assertTrue(machine.isSatelliteConnected());
            second.setSatelliteConnected(true);
            NetworkNodeEvents.unload((net.minecraft.tileentity.TileEntity) first.getBaseMetaTileEntity());
            assertTrue(machine.isSatelliteConnected());
            NetworkNodeEvents.unload((net.minecraft.tileentity.TileEntity) second.getBaseMetaTileEntity());
            assertFalse(machine.isSatelliteConnected());
        }

        public static void localUpdates() {
            ImpactConstructionChecks.TestWorld world = world();
            CountingTower nearby = new CountingTower("nearby");
            CountingTower distant = new CountingTower("distant");
            NetworkNodeEvents.load(place(world, nearby, -16, 64, 0));
            NetworkNodeEvents.load(place(world, distant, 4096, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertEquals(1, nearby.updates);
            assertEquals(1, distant.updates);
            GTMTE_Mixer machine = new GTMTE_Mixer("machine");
            NetworkNodeEvents.load(place(world, machine, -144, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertEquals(2, nearby.updates);
            assertEquals(1, distant.updates);
            assertTrue(nearby.getConnections().contains(machine));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertEquals(2, nearby.updates);
        }

        public static void queuedUpdates() {
            ImpactConstructionChecks.TestWorld world = world();
            CountingTower tower = new CountingTower("queued");
            NetworkNodeEvents.load(place(world, tower, 0, 64, 0));
            tower.invalidateDuringUpdate = true;
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertEquals(2, tower.updates);
            tower.failOnce = true;
            SatelliteNetworkLogic.INSTANCE.reloadNetwork();
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertEquals(NetworkStatus.FAULT, NetworkNodeLifecycle.getStatus(tower));
            for (int tick = 0; tick < 20; tick++) {
                NetworkNodeLifecycle.onServerTick();
            }
            assertEquals(4, tower.updates);
        }

        public static void dimensionsAndReplacement() {
            ImpactConstructionChecks.TestWorld firstWorld = world();
            WorldProviderSurface provider = new WorldProviderSurface();
            provider.dimensionId = 7;
            ImpactConstructionChecks.TestWorld secondWorld = new ImpactConstructionChecks.TestWorld(provider);
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            GTMTE_Mixer first = new GTMTE_Mixer("first");
            GTMTE_Mixer otherDimension = new GTMTE_Mixer("other_dimension");
            NetworkNodeEvents.load(place(firstWorld, tower, 0, 64, 0));
            NetworkNodeEvents.load(place(firstWorld, first, 16, 64, 0));
            NetworkNodeEvents.load(place(secondWorld, otherDimension, 16, 64, 0));
            tower.setSatelliteConnected(true);
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertTrue(first.isSatelliteConnected());
            assertFalse(otherDimension.isSatelliteConnected());
            GTMTE_Mixer replacement = new GTMTE_Mixer("replacement");
            NetworkNodeEvents.load(place(firstWorld, replacement, 16, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            assertFalse(first.isSatelliteConnected());
            assertTrue(replacement.isSatelliteConnected());
            first.removeConnect();
            assertTrue(replacement.isSatelliteConnected());
        }

        private static GTMTE_SpaceSatellite_Transmitter satellite(ImpactConstructionChecks.TestWorld world) {
            GTMTE_SpaceSatellite_Transmitter satellite = new GTMTE_SpaceSatellite_Transmitter(
                    "satellite", new String[0], new ITexture[0][][]);
            place(world, satellite, 0, 80, 0);
            satellite.updateFrequency(42);
            satellite.getNetworkHandler().onLoad();
            return satellite;
        }

        private static GTMTE_CommunicationTower_Receiver receiver(ImpactConstructionChecks.TestWorld world) {
            GTMTE_CommunicationTower_Receiver receiver = new GTMTE_CommunicationTower_Receiver(
                    "receiver", new String[0], new ITexture[0][][]);
            place(world, receiver, 16, 64, 0);
            receiver.updateFrequency(42);
            return receiver;
        }

        public static void satelliteStatus() {
            ImpactConstructionChecks.TestWorld world = world();
            WorldProviderSurface orbit = new WorldProviderSurface();
            orbit.dimensionId = 7;
            GTMTE_SpaceSatellite_Transmitter satellite = satellite(new ImpactConstructionChecks.TestWorld(orbit));
            GTMTE_CommunicationTower_Receiver receiver = receiver(world);
            receiver.onFirstConnect(satellite.getPosition());
            assertFalse(receiver.getConnectionStatus());
            assertEquals(1, satellite.getConnections().size());
            satellite.updateConnectionStatus(true);
            assertTrue(receiver.getConnectionStatus());
            assertTrue(satellite.onFirstConnect(42, receiver));
            assertTrue(receiver.getConnectionStatus());
            satellite.updateConnectionStatus(false);
            assertFalse(receiver.getConnectionStatus());
        }

        public static void satelliteRetry() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_CommunicationTower_Receiver receiver = receiver(world);
            receiver.onFirstConnect(new PositionObject(0, 80, 0));
            assertFalse(receiver.getConnectionStatus());
            GTMTE_SpaceSatellite_Transmitter satellite = satellite(world);
            satellite.updateConnectionStatus(true);
            receiver.getNetworkHandler().update();
            assertTrue(receiver.getConnectionStatus());
            satellite.updateFrequency(43);
            assertFalse(receiver.getConnectionStatus());
            assertTrue(satellite.getConnections().isEmpty());
            receiver.getNetworkHandler().update();
            NBTTagCompound diagnosis = new NBTTagCompound();
            receiver.getWailaNBTData(null, null, diagnosis, world, 16, 64, 0);
            assertEquals(NetworkStatus.FREQUENCY_MISMATCH.getTranslationKey(),
                    diagnosis.getString(NetworkDiagnostics.STATUS_TAG));
            receiver.updateFrequency(43);
            assertTrue(receiver.getConnectionStatus());
        }

        public static void satelliteSaveAndReplacement() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_SpaceSatellite_Transmitter original = satellite(world);
            GTMTE_CommunicationTower_Receiver receiver = receiver(world);
            receiver.onFirstConnect(original.getPosition());
            original.updateConnectionStatus(true);
            original.getNetworkHandler().onUnload();
            assertFalse(receiver.getConnectionStatus());
            NBTTagCompound tag = new NBTTagCompound();
            receiver.saveNBTData(tag);
            assertTrue(tag.hasKey("satellite"));
            tag.setBoolean("mIsReceive", true);
            receiver.loadNBTData(tag);
            assertFalse(receiver.getConnectionStatus());
            GTMTE_SpaceSatellite_Transmitter replacement = satellite(world);
            replacement.updateConnectionStatus(true);
            receiver.getNetworkHandler().update();
            assertTrue(receiver.getConnectionStatus());
            original.getNetworkHandler().onUnload();
            assertSame(replacement, SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(replacement.getPosition()));
            assertTrue(receiver.getConnectionStatus());
        }

        public static void chunkLifecycle() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            GTMTE_Mixer machine = new GTMTE_Mixer("machine");
            ImpactConstructionChecks.TestTile tile = place(world, tower, 0, 64, 0);
            NetworkNodeEvents.load(tile);
            NetworkNodeEvents.load(place(world, machine, 16, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            tower.setSatelliteConnected(true);
            Chunk chunk = new Chunk(world, 0, 0);
            chunk.chunkTileEntityMap.put(new net.minecraft.world.ChunkPosition(0, 64, 0), tile);
            NetworkNodeEvents events = new NetworkNodeEvents();
            events.onChunkUnload(new ChunkEvent.Unload(chunk));
            assertFalse(machine.isSatelliteConnected());
            assertTrue(tower.getConnections().isEmpty());
            events.onChunkLoad(new ChunkEvent.Load(chunk));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            tower.setSatelliteConnected(true);
            assertTrue(machine.isSatelliteConnected());
        }

        private static GTMTE_ParallelHatch_Output output(ImpactConstructionChecks.TestWorld world, int depth) {
            GTMTE_ParallelHatch_Output output = new GTMTE_ParallelHatch_Output(
                    "output_" + depth, 5, new String[0], new ITexture[0][][], 4);
            place(world, output, 0, 64, depth);
            return output;
        }

        private static GTMTE_ParallelHatch_Input input(ImpactConstructionChecks.TestWorld world, int x) {
            GTMTE_ParallelHatch_Input input = new GTMTE_ParallelHatch_Input(
                    "input_" + x, 5, new String[0], new ITexture[0][][], 4);
            place(world, input, x, 64, 0);
            input.setMachineName("machine");
            input.setMachineAddress("address_" + x);
            return input;
        }

        public static void allocation() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_ParallelComputer computer = new GTMTE_ParallelComputer("computer");
            ImpactConstructionChecks.TestTile controller = place(world, computer, 0, 64, 0);
            controller.setActive(true);
            computer.mMachine = true;
            computer.setSatelliteConnected(true);
            GTMTE_ComputerRack rack = new GTMTE_ComputerRack("rack", new String[0], new ITexture[0][][]);
            place(world, rack, -1, 64, 1);
            NBTTagCompound rackTag = new NBTTagCompound();
            rackTag.setInteger("mCapacityP", 4);
            rack.loadNBTData(rackTag);
            computer.sComputerRack.add(rack);
            GTMTE_ParallelHatch_Output empty = output(world, 1);
            GTMTE_ParallelHatch_Output first = output(world, 2);
            GTMTE_ParallelHatch_Output second = output(world, 3);
            GTMTE_ParallelHatch_Input firstInput = input(world, 32);
            GTMTE_ParallelHatch_Input secondInput = input(world, 48);
            first.onConnect(firstInput);
            second.onConnect(secondInput);
            computer.sParallHatchesOut.add(second);
            computer.sParallHatchesOut.add(empty);
            computer.sParallHatchesOut.add(first);
            computer.getParallelHandler().updateCapacity();
            computer.getParallelHandler().update();
            assertTrue(firstInput.getConnectionStatus());
            assertFalse(secondInput.getConnectionStatus());
            assertFalse(empty.getConnectionStatus());
            assertEquals(0, computer.getCurrentCapacityPP());
            assertEquals(0, computer.getCurrentParallel(), "Capacity is not a committed recipe parallel count");
            computer.sParallHatchesOut.clear();
            computer.sParallHatchesOut.add(first);
            computer.sParallHatchesOut.add(second);
            computer.sParallHatchesOut.add(empty);
            computer.getParallelHandler().update();
            assertTrue(firstInput.getConnectionStatus());
            first.getBaseMetaTileEntity().disableWorking();
            computer.getParallelHandler().update();
            assertFalse(firstInput.getConnectionStatus());
            assertTrue(secondInput.getConnectionStatus());
            NetworkNodeEvents.unload(controller);
            assertFalse(secondInput.getConnectionStatus());
            assertEquals(4, computer.getCurrentCapacityPP());
            world.unloadedPositions.add(ImpactConstructionChecks.TestWorld.key(-1, 64, 1));
            computer.getParallelHandler().updateCapacity();
            assertEquals(0, computer.getMaxCapacityPP());
        }

        public static void satelliteControllerUnload() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_SpaceSatellite controller = new GTMTE_SpaceSatellite("controller");
            ImpactConstructionChecks.TestTile tile = place(world, controller, 32, 80, 0);
            GTMTE_SpaceSatellite_Transmitter satellite = satellite(world);
            controller.sCommunTransmitter.add(satellite);
            GTMTE_CommunicationTower_Receiver receiver = receiver(world);
            receiver.onFirstConnect(satellite.getPosition());
            satellite.updateConnectionStatus(true);
            NetworkNodeEvents.unload(tile);
            assertFalse(receiver.getConnectionStatus());
            assertNull(SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(satellite.getPosition()));
            NetworkNodeEvents.load(tile);
            assertSame(satellite, SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(satellite.getPosition()));
        }

        public static void parallelRetryAndDimension() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_ParallelHatch_Output output = output(world, 1);
            GTMTE_ParallelHatch_Input input = input(world, 32);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("parallelIn", input.getPosition().saveToNBT());
            tag.setBoolean("isConnected", true);
            output.loadNBTData(tag);
            assertFalse(output.getConnectionStatus());
            world.unloadedPositions.add(ImpactConstructionChecks.TestWorld.key(32, 64, 0));
            output.onFirstTick(output.getBaseMetaTileEntity());
            assertNull(output.getReceiver());
            world.unloadedPositions.clear();
            output.getNetworkHandler().onTick(20);
            assertSame(input, output.getReceiver());
            NetworkNodeEvents.unload((net.minecraft.tileentity.TileEntity) input.getBaseMetaTileEntity());
            assertNull(output.getReceiver());
            assertNotNull(output.getReceiverPosition());
            output.getNetworkHandler().onTick(40);
            assertSame(input, output.getReceiver());
            output.onPostDisconnect();
            tag.setTag("parallelIn", new PositionObject(32, 64, 0, 7).saveToNBT());
            output.loadNBTData(tag);
            output.getNetworkHandler().onTick(60);
            assertNull(output.getReceiver());
        }

        public static void towerAvailability() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            ImpactConstructionChecks.TestTile controller = place(world, tower, 0, 64, 0);
            GTMTE_Mixer machine = new GTMTE_Mixer("machine");
            NetworkNodeEvents.load(controller);
            NetworkNodeEvents.load(place(world, machine, 32, 64, 0));
            SatelliteNetworkLogic.INSTANCE.onServerTick();
            tower.mMachine = true;
            controller.setActive(true);
            GTMTE_CommunicationTower_Receiver last = null;
            for (int index = 0; index < 4; index++) {
                last = new GTMTE_CommunicationTower_Receiver("receiver_" + index,
                        new String[0], new ITexture[0][][]);
                place(world, last, index + 1, 70, 0);
                last.updateConnectionStatus(true);
                tower.sCommunReceiver.add(last);
                last.connectToTower(tower);
            }
            assertTrue(machine.isSatelliteConnected());
            controller.setActive(false);
            tower.getNetworkHandler().update();
            assertFalse(machine.isSatelliteConnected());
            controller.setActive(true);
            tower.getNetworkHandler().update();
            assertTrue(machine.isSatelliteConnected());
            NetworkNodeEvents.unload((net.minecraft.tileentity.TileEntity) last.getBaseMetaTileEntity());
            assertFalse(machine.isSatelliteConnected());
        }

        public static void handlerFailureIsolation() {
            TestNode failing = new TestNode(true);
            TestNode healthy = new TestNode(false);
            NetworkNodeLifecycle.load(failing);
            NetworkNodeLifecycle.load(healthy);
            NetworkNodeLifecycle.onServerTick();
            assertEquals(NetworkStatus.FAULT, NetworkNodeLifecycle.getStatus(failing));
            assertEquals(1, healthy.updates);
            for (int tick = 0; tick < 19; tick++) {
                NetworkNodeLifecycle.onServerTick();
            }
            assertEquals(1, failing.loads);
            assertEquals(1, failing.updates);
            assertEquals(20, healthy.updates);
            NetworkNodeLifecycle.onServerTick();
            assertEquals(2, failing.loads);
            assertEquals(2, failing.updates);
            assertEquals(NetworkStatus.CONNECTED, NetworkNodeLifecycle.getStatus(failing));
        }

        public static void subscriberFailureIsolation() {
            ImpactConstructionChecks.TestWorld world = world();
            GTMTE_SpaceSatellite_Transmitter satellite = satellite(world);
            GTMTE_CommunicationTower_Receiver healthy = receiver(world);
            GTMTE_CommunicationTower_Receiver failing = new GTMTE_CommunicationTower_Receiver(
                    "failing", new String[0], new ITexture[0][][]) {
                private boolean failOnce = true;

                @Override
                public void onChangeConnection(boolean connected) {
                    if (connected && failOnce) {
                        failOnce = false;
                        throw new IllegalStateException("Expected receiver callback failure");
                    }
                    super.onChangeConnection(connected);
                }
            };
            NetworkNodeEvents.load(place(world, failing, 32, 64, 0));
            failing.updateFrequency(42);
            failing.onFirstConnect(satellite.getPosition());
            healthy.onFirstConnect(satellite.getPosition());
            satellite.updateConnectionStatus(true);
            assertTrue(healthy.getConnectionStatus());
            assertTrue(satellite.getConnectionStatus());
            assertEquals(NetworkStatus.FAULT, NetworkNodeLifecycle.getStatus(failing));
            for (int tick = 0; tick < 20; tick++) {
                NetworkNodeLifecycle.onServerTick();
            }
            assertTrue(failing.getConnectionStatus());
            assertTrue(healthy.getConnectionStatus());
        }

        public static void centralTickChain() {
            ImpactConstructionChecks.TestWorld world = world();
            WorldProviderSurface orbit = new WorldProviderSurface();
            orbit.dimensionId = 7;
            ImpactConstructionChecks.TestWorld orbitalWorld = new ImpactConstructionChecks.TestWorld(orbit);
            GTMTE_SpaceSatellite source = new GTMTE_SpaceSatellite("source");
            ImpactConstructionChecks.TestTile sourceTile = place(orbitalWorld, source, 0, 80, 0);
            source.mMachine = true;
            sourceTile.setActive(true);
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            ImpactConstructionChecks.TestTile towerTile = place(world, tower, 0, 64, 0);
            tower.mMachine = true;
            towerTile.setActive(true);
            for (int index = 0; index < 4; index++) {
                GTMTE_SpaceSatellite_Transmitter transmitter = new GTMTE_SpaceSatellite_Transmitter(
                        "source_" + index, new String[0], new ITexture[0][][]);
                NetworkNodeEvents.load(place(orbitalWorld, transmitter, index + 1, 80, 0));
                transmitter.updateFrequency(42);
                source.sCommunTransmitter.add(transmitter);
                GTMTE_CommunicationTower_Receiver receiver = new GTMTE_CommunicationTower_Receiver(
                        "receiver_" + index, new String[0], new ITexture[0][][]);
                NetworkNodeEvents.load(place(world, receiver, index + 1, 80, 0));
                receiver.updateFrequency(42);
                receiver.onFirstConnect(transmitter.getPosition());
                tower.sCommunReceiver.add(receiver);
                receiver.connectToTower(tower);
            }
            NetworkNodeEvents.load(sourceTile);
            NetworkNodeEvents.load(towerTile);
            GTMTE_ParallelComputer computer = new GTMTE_ParallelComputer("computer");
            ImpactConstructionChecks.TestTile computerTile = place(world, computer, 32, 64, 0);
            computer.mMachine = true;
            computerTile.setActive(true);
            GTMTE_ComputerRack rack = new GTMTE_ComputerRack("rack", new String[0], new ITexture[0][][]);
            place(world, rack, 31, 64, 1);
            NBTTagCompound capacity = new NBTTagCompound();
            capacity.setInteger("mCapacityP", 4);
            rack.loadNBTData(capacity);
            computer.sComputerRack.add(rack);
            GTMTE_ParallelHatch_Output output = output(world, 2);
            GTMTE_ParallelHatch_Input input = input(world, 64);
            output.onConnect(input);
            computer.sParallHatchesOut.add(output);
            NetworkNodeEvents.load((net.minecraft.tileentity.TileEntity) output.getBaseMetaTileEntity());
            NetworkNodeEvents.load(computerTile);
            NetworkNodeLifecycle.onServerTick();
            assertTrue(tower.isSatelliteConnected());
            assertTrue(computer.isSatelliteConnected());
            assertEquals(4, input.getAllocatedParallel());
            assertEquals(0, computer.getCurrentCapacityPP());
            NetworkNodeEvents.unload(sourceTile);
            assertFalse(tower.isSatelliteConnected());
            assertEquals(0, input.getAllocatedParallel());
            NetworkNodeEvents.load(sourceTile);
            NetworkNodeLifecycle.onServerTick();
            assertEquals(4, input.getAllocatedParallel());

            GTMTE_CommunicationTower_Receiver receiver = tower.sCommunReceiver.iterator().next();
            NBTTagCompound binding = new NBTTagCompound();
            receiver.saveNBTData(binding);
            NBTTagCompound legacy = new NBTTagCompound();
            legacy.setInteger("mFrequency", receiver.getFrequency());
            legacy.setBoolean("mIsReceive", true);
            receiver.loadNBTData(legacy);
            for (int tick = 0; tick < 40; tick++) {
                NetworkNodeLifecycle.onServerTick();
            }
            NetworkStatus missingAddress = NetworkStatus.LEGACY_SATELLITE_UNBOUND;
            assertFalse(receiver.getConnectionStatus());
            assertEquals(missingAddress, NetworkDiagnostics.status(receiver));
            assertEquals(missingAddress, NetworkDiagnostics.status(tower));
            assertEquals(missingAddress, NetworkDiagnostics.status(computer));
            assertEquals(missingAddress, NetworkDiagnostics.status(output));
            assertEquals(missingAddress, NetworkDiagnostics.status(input));
            assertEquals(0, input.getAllocatedParallel());

            NBTTagCompound tooltipData = new NBTTagCompound();
            NetworkDiagnostics.write(tooltipData, input);
            List<String> tooltip = new ArrayList<>();
            NetworkDiagnostics.appendConnection(tooltipData, tooltip, false);
            assertEquals(2, tooltip.size());
            assertTrue(tooltip.get(0).endsWith(StatCollector.translateToLocal(missingAddress.getTranslationKey())));
            assertEquals(StatCollector.translateToLocal(NetworkDiagnostics.BIND_SATELLITE_KEY), tooltip.get(1));

            NBTTagCompound saved = new NBTTagCompound();
            receiver.saveNBTData(saved);
            assertTrue(saved.getBoolean("mIsReceive"));
            assertFalse(saved.hasKey("satellite"));
            receiver.loadNBTData(saved);
            assertEquals(missingAddress, NetworkDiagnostics.status(receiver));

            receiver.onFirstConnect(PositionObject.loadFromNBT(binding.getCompoundTag("satellite")));
            NetworkNodeLifecycle.onServerTick();
            receiver.saveNBTData(saved);
            assertFalse(saved.hasKey("mIsReceive"));
            assertTrue(saved.hasKey("satellite"));
            assertEquals(4, input.getAllocatedParallel());
        }

        private static final class TestNode implements IHandledNetworkNode, INetworkNodeHandler {

            private boolean failOnce;
            private int loads;
            private int updates;

            private TestNode(boolean failOnce) {
                this.failOnce = failOnce;
            }

            @Override
            public INetworkNodeHandler getNetworkHandler() {
                return this;
            }

            @Override
            public void onLoad() {
                loads++;
            }

            @Override
            public void onUnload() {
            }

            @Override
            public void onTick(long tick) {
                updates++;
                if (failOnce) {
                    failOnce = false;
                    throw new IllegalStateException("Expected handler failure");
                }
            }

            @Override
            public NetworkStatus getStatus() {
                return NetworkStatus.CONNECTED;
            }
        }

        public static void towerSatelliteSelection() {
            ImpactConstructionChecks.TestWorld world = world();
            ImpactConstructionChecks.TestPlayer owner = new ImpactConstructionChecks.TestPlayer(world);
            ImpactConstructionChecks.TestPlayer other = new ImpactConstructionChecks.TestPlayer(world);
            WorldProviderSurface orbit = new WorldProviderSurface();
            orbit.dimensionId = 7;
            ImpactConstructionChecks.TestWorld orbitalWorld = new ImpactConstructionChecks.TestWorld(orbit);
            orbitalWorld.mapStorage = world.mapStorage;
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            ImpactConstructionChecks.TestTile towerTile = place(world, tower, 0, 64, 0);
            tower.mMachine = true;
            towerTile.setActive(true);
            for (int index = 0; index < 4; index++) {
                GTMTE_CommunicationTower_Receiver receiver = new GTMTE_CommunicationTower_Receiver(
                        "receiver_" + index, new String[0], new ITexture[0][][]);
                NetworkNodeEvents.load(place(world, receiver, index + 1, 77, 0));
                tower.sCommunReceiver.add(receiver);
                receiver.connectToTower(tower);
            }
            NetworkNodeEvents.load(towerTile);
            GTMTE_SpaceSatellite first = formedSatellite(orbitalWorld, 0);
            GTMTE_SpaceSatellite second = formedSatellite(orbitalWorld, 32);
            GTMTE_SpaceSatellite foreign = formedSatellite(orbitalWorld, 64);
            first.getBaseMetaTileEntity().setOwnerUuid(owner.getUniqueID());
            second.getBaseMetaTileEntity().setOwnerUuid(owner.getUniqueID());
            foreign.getBaseMetaTileEntity().setOwnerUuid(other.getUniqueID());
            NetworkNodeLifecycle.onServerTick();

            String firstId = "7:0:80:0";
            String secondId = "7:32:80:0";
            String foreignId = "7:64:80:0";
            SatelliteDirectory directory = SatelliteDirectory.get(world);
            assertEquals(2, directory.getPositions(owner.getUniqueID()).size());
            assertEquals(1, directory.getPositions(other.getUniqueID()).size());
            assertFalse(directory.getPositions(owner.getUniqueID()).containsKey(foreignId));
            assertEquals(NetworkStatus.CONNECTED, directory.getStatus(firstId));
            assertFalse(tower.isSatelliteConnected());
            NetworkNodeLifecycle.unloadWorld(orbitalWorld);
            assertEquals(NetworkStatus.SATELLITE_UNLOADED, directory.getStatus(firstId));
            assertFalse(tower.getNetworkHandler().selectSatellite(foreignId, owner));
            assertFalse(tower.getNetworkHandler().selectSatellite(firstId, other));
            assertTrue(tower.getNetworkHandler().selectSatellite(firstId, owner));
            assertEquals(NetworkStatus.SATELLITE_UNLOADED, NetworkDiagnostics.status(tower));
            assertFalse(tower.isSatelliteConnected());

            NBTTagCompound catalogTag = new NBTTagCompound();
            directory.writeToNBT(catalogTag);
            SatelliteDirectory restored = new SatelliteDirectory("restored");
            restored.readFromNBT(catalogTag);
            assertEquals(directory.getPositions(owner.getUniqueID()).keySet(),
                    restored.getPositions(owner.getUniqueID()).keySet());
            assertTrue(restored.isOwnedBy(foreignId, other.getUniqueID()));
            assertFalse(restored.isOwnedBy(foreignId, owner.getUniqueID()));
            assertEquals(NetworkStatus.SATELLITE_UNLOADED, restored.getStatus(firstId));

            NetworkNodeLifecycle.load(first);
            NetworkNodeLifecycle.onServerTick();
            assertTrue(tower.isSatelliteConnected());
            for (GTMTE_CommunicationTower_Receiver receiver : tower.sCommunReceiver) {
                assertTrue(receiver.getConnectionStatus());
                assertEquals(0, receiver.getFrequency());
            }
            assertTrue(first.sCommunTransmitter.iterator().next().getConnections().isEmpty());

            assertFalse(tower.getNetworkHandler().selectSatellite("unknown", owner));
            assertEquals(firstId, tower.getNetworkHandler().getSatelliteId());
            assertTrue(tower.getNetworkHandler().selectSatellite(secondId, owner));
            assertFalse(tower.isSatelliteConnected());
            NBTTagCompound towerTag = new NBTTagCompound();
            tower.saveNBTData(towerTag);
            tower.loadNBTData(towerTag);
            assertEquals(secondId, tower.getNetworkHandler().getSatelliteId());
            NetworkNodeLifecycle.load(second);
            NetworkNodeLifecycle.onServerTick();
            assertTrue(tower.isSatelliteConnected());

            second.onRemoval();
            NetworkNodeLifecycle.unload(second);
            NetworkNodeLifecycle.onServerTick();
            assertFalse(directory.contains(secondId));
            assertEquals(NetworkStatus.SATELLITE_MISSING, NetworkDiagnostics.status(tower));
            assertFalse(tower.isSatelliteConnected());
        }

        private static GTMTE_SpaceSatellite formedSatellite(ImpactConstructionChecks.TestWorld world, int x) {
            GTMTE_SpaceSatellite source = new GTMTE_SpaceSatellite("source");
            ImpactConstructionChecks.TestTile sourceTile = place(world, source, x, 80, 0);
            GTMTE_SpaceSatellite_Transmitter transmitter = new GTMTE_SpaceSatellite_Transmitter(
                    "transmitter", new String[0], new ITexture[0][][]);
            NetworkNodeEvents.load(place(world, transmitter, x + 1, 80, 0));
            transmitter.updateFrequency(42);
            source.sCommunTransmitter.add(transmitter);
            source.mMachine = true;
            sourceTile.setActive(true);
            NetworkNodeEvents.load(sourceTile);
            return source;
        }

        public static void legacyStartupRegistrations() throws Exception {
            new CommonProxy().onServerAboutToStart();
            LegacyNetwork network = new LegacyNetwork();
            for (Object tile : network.world.loadedTileEntityList) {
                NetworkNodeEvents.load((net.minecraft.tileentity.TileEntity) tile);
            }
            network.satellite.updateConnectionStatus(true);
            network.output.updateStatus(true);
            assertTrue(network.receiver.getConnectionStatus());
            assertEquals(4, network.input.getAllocatedParallel());

            Path directory = Files.createTempDirectory(new java.io.File("build").toPath(), "network-startup-");
            Constructor<SaveManager> constructor = SaveManager.class.getDeclaredConstructor(java.io.File.class);
            constructor.setAccessible(true);
            SaveManager manager = constructor.newInstance(directory.toFile());
            Files.createDirectories(manager.parallelSystemDirectory.toPath());
            Field instance = SaveManager.class.getDeclaredField("instance");
            instance.setAccessible(true);
            Object previous = instance.get(null);
            instance.set(null, manager);
            try {
                // Forge loads spawn chunks before posting FMLServerStartedEvent.
                new CommonProxy().onServerStarted();
                assertSame(network.satellite,
                        SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(network.satellite.getPosition()));
                assertTrue(network.receiver.getConnectionStatus());
                assertSame(network.input, network.output.getReceiver());
                assertEquals(4, network.input.getAllocatedParallel());
                network.output.onFirstTick(network.output.getBaseMetaTileEntity());
                network.input.onFirstTick(network.input.getBaseMetaTileEntity());
                assertEquals(network.input.getMachineAddress(), network.output.getMachineAddress());
                assertEquals(4, network.input.getAllocatedParallel());
            } finally {
                instance.set(null, previous);
            }
        }

        public static void legacyLoadedWorldBootstrap() {
            LegacyNetwork network = new LegacyNetwork();
            // These tiles are loaded already: no ChunkEvent.Load or onFirstTick follows the bootstrap.
            NetworkNodeLifecycle.loadWorld(network.world);
            network.satellite.updateConnectionStatus(true);
            NetworkNodeLifecycle.onServerTick();
            network.output.updateStatus(true);
            assertTrue(network.receiver.getConnectionStatus());
            assertSame(network.input, network.output.getReceiver());
            assertEquals(4, network.input.getAllocatedParallel());
            NBTTagCompound receiverSaved = new NBTTagCompound();
            network.receiver.saveNBTData(receiverSaved);
            assertEquals(2, receiverSaved.getInteger("mFrequency"));
            assertTrue(network.satellite.getPosition().isEquals(
                    PositionObject.loadFromNBT(receiverSaved.getCompoundTag("satellite"))));
            NBTTagCompound transmitterSaved = new NBTTagCompound();
            network.output.saveNBTData(transmitterSaved);
            assertTrue(network.input.getPosition().isEquals(
                    PositionObject.loadFromNBT(transmitterSaved.getCompoundTag("parallelIn"))));
        }

        private static final class LegacyNetwork {

            private final ImpactConstructionChecks.TestWorld world = world();
            private final GTMTE_SpaceSatellite_Transmitter satellite = new GTMTE_SpaceSatellite_Transmitter(
                    "legacy-satellite", new String[0], new ITexture[0][][]);
            private final GTMTE_CommunicationTower_Receiver receiver = new GTMTE_CommunicationTower_Receiver(
                    "legacy-receiver", new String[0], new ITexture[0][][]);
            private final GTMTE_ParallelHatch_Output output = new GTMTE_ParallelHatch_Output(
                    "legacy-output", 5, new String[0], new ITexture[0][][], 4);
            private final GTMTE_ParallelHatch_Input input = new GTMTE_ParallelHatch_Input(
                    "legacy-input", 5, new String[0], new ITexture[0][][], 4);

            private LegacyNetwork() {
                // Legacy NBT keys and addresses, including the old saved connection flags.
                world.loadedTileEntityList.add(place(world, satellite, 346, 5, 412));
                world.loadedTileEntityList.add(place(world, receiver, 355, 18, 416));
                world.loadedTileEntityList.add(place(world, input, 353, 7, 399));
                world.loadedTileEntityList.add(place(world, output, 364, 6, 414));
                NBTTagCompound satelliteSaved = new NBTTagCompound();
                satelliteSaved.setInteger("mFrequency", 2);
                satelliteSaved.setBoolean("mIsTransmit", true);
                satellite.loadNBTData(satelliteSaved);
                NBTTagCompound receiverSaved = new NBTTagCompound();
                receiverSaved.setInteger("mFrequency", 2);
                receiverSaved.setBoolean("mIsReceive", true);
                receiverSaved.setTag("satellite", new PositionObject(346, 5, 412, 0).saveToNBT());
                receiver.loadNBTData(receiverSaved);
                NBTTagCompound inputSaved = new NBTTagCompound();
                inputSaved.setBoolean("isConnected", true);
                inputSaved.setInteger("mCurrentParallelIn", 4);
                inputSaved.setString("machineName", "Legacy machine");
                inputSaved.setString("address", "legacy-address");
                input.loadNBTData(inputSaved);
                NBTTagCompound outputSaved = new NBTTagCompound();
                outputSaved.setBoolean("isConnected", true);
                outputSaved.setTag("parallelIn", new PositionObject(353, 7, 399, 0).saveToNBT());
                output.loadNBTData(outputSaved);
            }
        }

        private static final class CountingTower extends GTMTE_TowerCommunication {

            private int updates;
            private boolean invalidateDuringUpdate;
            private boolean failOnce;

            private CountingTower(String name) {
                super(name);
            }

            @Override
            public void onUpdateConnections() {
                updates++;
                if (failOnce) {
                    failOnce = false;
                    throw new IllegalStateException("Expected test failure");
                }
                super.onUpdateConnections();
                if (invalidateDuringUpdate) {
                    invalidateDuringUpdate = false;
                    SatelliteNetworkLogic.INSTANCE.reloadNetwork();
                }
            }
        }
    }
}
