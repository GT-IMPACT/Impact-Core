package com.impact.mods.gregtech.tileentities.basic;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.ArrayList;
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
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GT_Config;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LongDistancePipelineTransportTest {
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
    void fluidFindsStraightRoutesInAllSixDirections() throws Throwable {
        check("fluidFindsStraightRoutesInAllSixDirections");
    }

    @Test
    void itemFindsStraightRoutesInAllSixDirections() throws Throwable {
        check("itemFindsStraightRoutesInAllSixDirections");
    }

    @Test
    void fluidFindsBentRoute() throws Throwable {
        check("fluidFindsBentRoute");
    }

    @Test
    void itemFindsBentRoute() throws Throwable {
        check("itemFindsBentRoute");
    }

    @Test
    void fluidRejectsWrongMetadataAndOtherEndpointTypes() throws Throwable {
        check("fluidRejectsWrongMetadataAndOtherEndpointTypes");
    }

    @Test
    void itemRejectsWrongMetadataAndOtherEndpointTypes() throws Throwable {
        check("itemRejectsWrongMetadataAndOtherEndpointTypes");
    }

    @Test
    void fluidRequiresReceiverFrontConnectedToPipe() throws Throwable {
        check("fluidRequiresReceiverFrontConnectedToPipe");
    }

    @Test
    void itemRequiresReceiverFrontConnectedToPipe() throws Throwable {
        check("itemRequiresReceiverFrontConnectedToPipe");
    }

    @Test
    void fluidSelectsNearestReceiverWithBranchesAndLoops() throws Throwable {
        check("fluidSelectsNearestReceiverWithBranchesAndLoops");
    }

    @Test
    void itemSelectsNearestReceiverWithBranchesAndLoops() throws Throwable {
        check("itemSelectsNearestReceiverWithBranchesAndLoops");
    }

    @Test
    void fluidScanNeverLoadsUnloadedChunks() throws Throwable {
        check("fluidScanNeverLoadsUnloadedChunks");
    }

    @Test
    void itemScanNeverLoadsUnloadedChunks() throws Throwable {
        check("itemScanNeverLoadsUnloadedChunks");
    }

    @Test
    void fluidCachedAndRestoredLinkWorksWithUnloadedMiddle() throws Throwable {
        check("fluidCachedAndRestoredLinkWorksWithUnloadedMiddle");
    }

    @Test
    void itemCachedAndRestoredLinkWorksWithUnloadedMiddle() throws Throwable {
        check("itemCachedAndRestoredLinkWorksWithUnloadedMiddle");
    }

    @Test
    void fluidReceiverUnloadBlocksTransferAndReloadReconnects() throws Throwable {
        check("fluidReceiverUnloadBlocksTransferAndReloadReconnects");
    }

    @Test
    void itemReceiverUnloadBlocksTransferAndReloadReconnects() throws Throwable {
        check("itemReceiverUnloadBlocksTransferAndReloadReconnects");
    }

    @Test
    void fluidOutputStorageChunkIsNotForcedLoaded() throws Throwable {
        check("fluidOutputStorageChunkIsNotForcedLoaded");
    }

    @Test
    void itemOutputStorageChunkIsNotForcedLoaded() throws Throwable {
        check("itemOutputStorageChunkIsNotForcedLoaded");
    }

    @Test
    void fluidReceiverRemovalAndReplacementAreHandled() throws Throwable {
        check("fluidReceiverRemovalAndReplacementAreHandled");
    }

    @Test
    void itemReceiverRemovalAndReplacementAreHandled() throws Throwable {
        check("itemReceiverRemovalAndReplacementAreHandled");
    }

    @Test
    void fluidReceiverHasOnlyOneLiveSender() throws Throwable {
        check("fluidReceiverHasOnlyOneLiveSender");
    }

    @Test
    void itemReceiverHasOnlyOneLiveSender() throws Throwable {
        check("itemReceiverHasOnlyOneLiveSender");
    }

    @Test
    void fluidDisabledSourceAndClientCallsCannotTransfer() throws Throwable {
        check("fluidDisabledSourceAndClientCallsCannotTransfer");
    }

    @Test
    void itemDisabledSourceAndClientCallsCannotTransfer() throws Throwable {
        check("itemDisabledSourceAndClientCallsCannotTransfer");
    }

    @Test
    void fluidNbtRoundTripPreservesLongDistance() throws Throwable {
        check("fluidNbtRoundTripPreservesLongDistance");
    }

    @Test
    void itemNbtRoundTripPreservesLongDistance() throws Throwable {
        check("itemNbtRoundTripPreservesLongDistance");
    }

    @Test
    void fluidBrokenRouteStopsAfterMachineUpdate() throws Throwable {
        check("fluidBrokenRouteStopsAfterMachineUpdate");
    }

    @Test
    void itemBrokenRouteStopsAfterMachineUpdate() throws Throwable {
        check("itemBrokenRouteStopsAfterMachineUpdate");
    }

    @Test
    void fluidDebugWithoutTargetIsSafe() throws Throwable {
        check("fluidDebugWithoutTargetIsSafe");
    }

    @Test
    void itemDebugWithoutTargetIsSafe() throws Throwable {
        check("itemDebugWithoutTargetIsSafe");
    }

    @Test
    void fluidSimulationCapacityTagsAndDrainContract() throws Throwable {
        check("fluidSimulationCapacityTagsAndDrainContract");
    }

    @Test
    void fluidTankInfoUsesRemoteTankContactSide() throws Throwable {
        check("fluidTankInfoUsesRemoteTankContactSide");
    }

    @Test
    void itemHopperInsertsAndMergesWithoutLosingItems() throws Throwable {
        check("itemHopperInsertsAndMergesWithoutLosingItems");
    }

    @Test
    void itemInventoryProxyPreservesStacksAndMetadata() throws Throwable {
        check("itemInventoryProxyPreservesStacksAndMetadata");
    }

    @Test
    void itemSidedInventoryUsesRemoteInventoryContactSide() throws Throwable {
        check("itemSidedInventoryUsesRemoteInventoryContactSide");
    }

    @Test
    void missingOutputStorageFailsSafely() throws Throwable {
        check("missingOutputStorageFailsSafely");
    }

    @Test
    void fluidSupportsStorageFrontAndBackPortsInAnyOrientation() throws Throwable {
        check("fluidSupportsStorageFrontAndBackPortsInAnyOrientation");
    }

    @Test
    void itemSupportsStorageFrontAndBackPortsInAnyOrientation() throws Throwable {
        check("itemSupportsStorageFrontAndBackPortsInAnyOrientation");
    }

    public static class Scenarios {
        private static GT_Block_LongDistancePipe pipe;

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
            pipe = new GT_Block_LongDistancePipe();
        }

        private static void setField(Class<?> type, Object owner, String name, Object value) throws Exception {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(owner, value);
        }

        public void fluidFindsStraightRoutesInAllSixDirections() throws Exception {
            verifyStraightRoutes(Kind.FLUID);
        }

        public void itemFindsStraightRoutesInAllSixDirections() throws Exception {
            verifyStraightRoutes(Kind.ITEM);
        }

        public void fluidFindsBentRoute() throws Exception {
            verifyBentRoute(Kind.FLUID);
        }

        public void itemFindsBentRoute() throws Exception {
            verifyBentRoute(Kind.ITEM);
        }

        public void fluidRejectsWrongMetadataAndOtherEndpointTypes() throws Exception {
            verifyWrongTypes(Kind.FLUID);
        }

        public void itemRejectsWrongMetadataAndOtherEndpointTypes() throws Exception {
            verifyWrongTypes(Kind.ITEM);
        }

        public void fluidRequiresReceiverFrontConnectedToPipe() throws Exception {
            verifyReceiverOrientation(Kind.FLUID);
        }

        public void itemRequiresReceiverFrontConnectedToPipe() throws Exception {
            verifyReceiverOrientation(Kind.ITEM);
        }

        public void fluidSelectsNearestReceiverWithBranchesAndLoops() throws Exception {
            verifyNearestReceiver(Kind.FLUID);
        }

        public void itemSelectsNearestReceiverWithBranchesAndLoops() throws Exception {
            verifyNearestReceiver(Kind.ITEM);
        }

        public void fluidScanNeverLoadsUnloadedChunks() throws Exception {
            verifyUnloadedScan(Kind.FLUID);
        }

        public void itemScanNeverLoadsUnloadedChunks() throws Exception {
            verifyUnloadedScan(Kind.ITEM);
        }

        public void fluidCachedAndRestoredLinkWorksWithUnloadedMiddle() throws Exception {
            verifyCachedAndRestoredLink(Kind.FLUID);
        }

        public void itemCachedAndRestoredLinkWorksWithUnloadedMiddle() throws Exception {
            verifyCachedAndRestoredLink(Kind.ITEM);
        }

        public void fluidReceiverUnloadBlocksTransferAndReloadReconnects() throws Exception {
            verifyReceiverUnload(Kind.FLUID);
        }

        public void itemReceiverUnloadBlocksTransferAndReloadReconnects() throws Exception {
            verifyReceiverUnload(Kind.ITEM);
        }

        public void fluidOutputStorageChunkIsNotForcedLoaded() throws Exception {
            verifyOutputChunkUnload(Kind.FLUID);
        }

        public void itemOutputStorageChunkIsNotForcedLoaded() throws Exception {
            verifyOutputChunkUnload(Kind.ITEM);
        }

        public void fluidReceiverRemovalAndReplacementAreHandled() throws Exception {
            verifyReceiverReplacement(Kind.FLUID);
        }

        public void itemReceiverRemovalAndReplacementAreHandled() throws Exception {
            verifyReceiverReplacement(Kind.ITEM);
        }

        public void fluidReceiverHasOnlyOneLiveSender() throws Exception {
            verifySingleSender(Kind.FLUID);
        }

        public void itemReceiverHasOnlyOneLiveSender() throws Exception {
            verifySingleSender(Kind.ITEM);
        }

        public void fluidDisabledSourceAndClientCallsCannotTransfer() throws Exception {
            verifyDisabledAndClient(Kind.FLUID);
        }

        public void itemDisabledSourceAndClientCallsCannotTransfer() throws Exception {
            verifyDisabledAndClient(Kind.ITEM);
        }

        public void fluidNbtRoundTripPreservesLongDistance() throws Exception {
            verifyNbt(Kind.FLUID);
        }

        public void itemNbtRoundTripPreservesLongDistance() throws Exception {
            verifyNbt(Kind.ITEM);
        }

        public void fluidBrokenRouteStopsAfterMachineUpdate() throws Exception {
            verifyBrokenRoute(Kind.FLUID);
        }

        public void itemBrokenRouteStopsAfterMachineUpdate() throws Exception {
            verifyBrokenRoute(Kind.ITEM);
        }

        public void fluidDebugWithoutTargetIsSafe() throws Exception {
            verifyDebug(Kind.FLUID);
        }

        public void itemDebugWithoutTargetIsSafe() throws Exception {
            verifyDebug(Kind.ITEM);
        }

        private void verifyStraightRoutes(Kind kind) throws Exception {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                for (int length : new int[] { 1, 2, 32, 48 }) {
                    Harness h = new Harness(kind, length, direction);
                    assertTrue(h.sender.checkTarget(), kind + " / " + direction + " / " + length);
                    assertSame(h.receiver, h.sender.mTarget);
                    assertSame(h.sender, h.receiver.mSender);
                    assertEquals(length, h.sender.getDistance());
                    assertEquals(length, h.receiver.getDistance());
                    assertTrue(h.transferOne());
                    assertEquals(1, h.storedUnits());
                }
            }
        }

        private void verifyBentRoute(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            h.bendRoute();
            assertTrue(h.sender.checkTarget());
            assertSame(h.receiver, h.sender.mTarget);
            assertEquals(4L, h.sender.getDistance());
            assertTrue(h.transferOne());
        }

        private void verifyWrongTypes(Kind kind) throws Exception {
            Harness wrongMetadata = new Harness(kind);
            ChunkCoordinates middle = offset(wrongMetadata.start, ForgeDirection.EAST, 16);
            wrongMetadata.world.pipes.put(middle, kind == Kind.FLUID ? 1 : 0);
            assertFalse(wrongMetadata.sender.checkTarget());
            assertFalse(wrongMetadata.transferOne());
            assertEquals(0, wrongMetadata.storedUnits());

            Harness wrongEndpoint = new Harness(kind);
            wrongEndpoint.replaceReceiver(kind == Kind.FLUID ? Kind.ITEM : Kind.FLUID);
            assertFalse(wrongEndpoint.sender.checkTarget());
            assertFalse(wrongEndpoint.transferOne());

            Harness absentWire = new Harness(kind, 0, ForgeDirection.EAST);
            assertFalse(absentWire.sender.checkTarget());
            assertFalse(absentWire.transferOne());
        }

        private void verifyReceiverOrientation(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            setField(BaseMetaTileEntity.class, h.receiverTile, "mFacing", (byte) ForgeDirection.NORTH.ordinal());
            assertFalse(h.sender.checkTarget());
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
        }

        private void verifyNearestReceiver(Kind kind) throws Exception {
            Harness h = new Harness(kind, 8, ForgeDirection.EAST);
            for (int z = 9; z <= 24; z++) {
                h.world.pipes.put(new ChunkCoordinates(9, 64, z), kind.metadata);
            }
            h.endpoint(kind, new ChunkCoordinates(9, 64, 25), ForgeDirection.NORTH);
            for (int z = 9; z <= 10; z++) {
                h.world.pipes.put(new ChunkCoordinates(10, 64, z), kind.metadata);
                h.world.pipes.put(new ChunkCoordinates(12, 64, z), kind.metadata);
            }
            h.world.pipes.put(new ChunkCoordinates(11, 64, 10), kind.metadata);
            h.endpoint(kind == Kind.FLUID ? Kind.ITEM : Kind.FLUID, new ChunkCoordinates(11, 64, 7),
                    ForgeDirection.SOUTH);
            assertTrue(h.sender.checkTarget());
            assertSame(h.receiver, h.sender.mTarget);
            assertEquals(8L, h.sender.getDistance(), "Branches and loops must not inflate route length");
            assertTrue(h.transferOne());
        }

        private void verifyUnloadedScan(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            h.world.unload(new ChunkCoordinates(24, 64, 8));
            assertFalse(h.sender.checkTarget());
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.world.unloadedChunks.clear();
            h.sender.onMachineBlockUpdate();
            assertTrue(h.sender.checkTarget());
            assertTrue(h.transferOne());

            Harness unloadedFirstWire = new Harness(kind, 8, ForgeDirection.EAST, new ChunkCoordinates(15, 64, 8));
            unloadedFirstWire.world.unload(new ChunkCoordinates(16, 64, 8));
            assertFalse(unloadedFirstWire.sender.checkTarget());
            assertFalse(unloadedFirstWire.transferOne());
        }

        private void verifyCachedAndRestoredLink(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            h.world.unload(new ChunkCoordinates(24, 64, 8));
            assertTrue(h.transferOne(), "The cached link does not need the middle chunks");
            h.reloadSender();
            assertNull(h.sender.mTarget);
            assertTrue(h.sender.checkTarget(), "Saved coordinates restore a loaded receiver without rescanning");
            assertTrue(h.transferOne());
            assertEquals(2, h.storedUnits());
        }

        private void verifyReceiverUnload(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            h.receiverTile.onChunkUnload();
            h.world.unload(h.receiver.getCoords());
            assertFalse(h.sender.checkTarget());
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.world.unloadedChunks.clear();
            h.receiverTile.validate();
            h.receiverTile.isDead = false;
            assertTrue(h.sender.checkTarget());
            assertTrue(h.transferOne());
        }

        private void verifyOutputChunkUnload(Kind kind) throws Exception {
            Harness h = new Harness(kind, 6, ForgeDirection.EAST);
            assertEquals(15, h.receiver.getCoords().posX);
            assertTrue(h.sender.checkTarget());
            h.world.unload(h.storagePosition);
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.world.unloadedChunks.clear();
            assertTrue(h.transferOne());
        }

        private void verifyReceiverReplacement(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            GTMTE_LongDistancePipelineBase previous = h.receiver;
            ChunkCoordinates position = h.receiver.getCoords();
            h.receiverTile.invalidate();
            h.world.tiles.remove(position);
            assertFalse(h.sender.checkTarget());
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.replaceReceiver(kind);
            assertTrue(h.sender.checkTarget());
            assertSame(h.receiver, h.sender.mTarget);
            assertTrue(h.transferOne());
            assertNull(previous.getBaseMetaTileEntity());
        }

        private void verifySingleSender(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            GTMTE_LongDistancePipelineBase second = h.endpoint(kind, new ChunkCoordinates(8, 64, 10),
                    ForgeDirection.WEST);
            h.world.pipes.put(new ChunkCoordinates(9, 64, 10), kind.metadata);
            h.world.pipes.put(new ChunkCoordinates(9, 64, 9), kind.metadata);
            assertFalse(second.checkTarget());
            assertSame(h.sender, h.receiver.mSender);
            h.senderTile.invalidate();
            h.world.tiles.remove(h.start);
            assertTrue(second.checkTarget());
            assertSame(second, h.receiver.mSender);
        }

        private void verifyDisabledAndClient(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            h.senderTile.disableWorking();
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.senderTile.enableWorking();
            h.world.isRemote = true;
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.world.isRemote = false;
            assertTrue(h.transferOne());
        }

        private void verifyNbt(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            h.sender.mDistance = (long) Integer.MAX_VALUE + 500L;
            long distance = h.sender.mDistance;
            ChunkCoordinates receiverPosition = h.receiver.getCoords();
            h.reloadSender();
            assertEquals(distance, h.sender.getDistance());
            assertEquals(receiverPosition, h.sender.mTargetPos);
            assertNull(h.sender.mTarget);
            assertTrue(h.sender.checkTarget());
            assertEquals(distance, h.receiver.getDistance());
            assertTrue(h.transferOne());
        }

        private void verifyBrokenRoute(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            assertTrue(h.sender.checkTarget());
            ChunkCoordinates middle = offset(h.start, ForgeDirection.EAST, 16);
            h.world.pipes.remove(middle);
            h.sender.onMachineBlockUpdate();
            h.receiver.onMachineBlockUpdate();
            assertFalse(h.transferOne());
            assertEquals(0, h.storedUnits());
            h.world.pipes.put(middle, kind.metadata);
            h.sender.onMachineBlockUpdate();
            h.receiver.onMachineBlockUpdate();
            assertTrue(h.transferOne());
        }

        private void verifyDebug(Kind kind) throws Exception {
            Harness h = new Harness(kind);
            h.senderTile.disableWorking();
            ArrayList<String> info = h.sender.getSpecialDebugInfo(h.senderTile, null, 0, new ArrayList<>());
            assertFalse(info.isEmpty());
            assertNull(h.sender.mTargetPos);
        }

        public void fluidSimulationCapacityTagsAndDrainContract() throws Exception {
            Harness h = new Harness(Kind.FLUID);
            FluidStack resource = new FluidStack(FluidRegistry.WATER, 20_000);
            resource.tag = new NBTTagCompound();
            resource.tag.setString("batch", "transport-test");
            assertTrue(h.senderTile.canFill(ForgeDirection.WEST, FluidRegistry.WATER));
            assertEquals(16_000, h.senderTile.fill(ForgeDirection.WEST, resource, false));
            assertEquals(0, h.tank.getFluidAmount());
            assertEquals(0, h.tank.dirtyCalls);
            FluidStack partial = resource.copy();
            partial.amount = 1_000;
            assertEquals(1_000, h.senderTile.fill(ForgeDirection.WEST, partial, true));
            FluidStack otherTag = partial.copy();
            otherTag.tag.setString("batch", "other-batch");
            assertEquals(0, h.senderTile.fill(ForgeDirection.WEST, otherTag, false));
            assertEquals(0, h.senderTile.fill(ForgeDirection.WEST, otherTag, true));
            assertEquals(0, h.senderTile.fill(ForgeDirection.WEST, new FluidStack(FluidRegistry.LAVA, 1_000), true));
            assertEquals(1_000, h.tank.getFluidAmount());
            assertEquals(15_000, h.senderTile.fill(ForgeDirection.WEST, resource, true));
            assertEquals(20_000, resource.amount);
            assertEquals(resource.tag, h.tank.tank.getFluid().tag);
            assertEquals(16_000, h.tank.getFluidAmount());
            assertEquals(0, h.senderTile.fill(ForgeDirection.WEST, resource, true));
            assertEquals(0, h.senderTile.fill(ForgeDirection.WEST, new FluidStack(FluidRegistry.LAVA, 1_000), true));
            assertNull(h.senderTile.drain(ForgeDirection.WEST, 1_000, true));
            assertNull(h.senderTile.drain(ForgeDirection.WEST, resource, true));
            assertFalse(h.senderTile.canDrain(ForgeDirection.WEST, FluidRegistry.WATER));
            assertEquals(16_000, h.tank.getFluidAmount());
            assertEquals(2, h.tank.dirtyCalls);
        }

        public void fluidTankInfoUsesRemoteTankContactSide() throws Exception {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                Harness straight = new Harness(Kind.FLUID, 8, direction);
                ForgeDirection contactSide = direction.getOpposite();
                assertEquals(1_000,
                        straight.senderTile.fill(contactSide, new FluidStack(FluidRegistry.WATER, 1_000), true));
                for (ForgeDirection querySide : ForgeDirection.VALID_DIRECTIONS) {
                    FluidTankInfo[] tanks = straight.senderTile.getTankInfo(querySide);
                    assertEquals(contactSide, straight.tank.lastInfoSide);
                    assertEquals(1, tanks.length);
                    assertEquals(1_000, tanks[0].fluid.amount);
                    assertEquals(16_000, tanks[0].capacity);
                }
            }
            Harness h = new Harness(Kind.FLUID);
            h.bendRoute();
            assertEquals(1_000,
                    h.senderTile.fill(ForgeDirection.WEST, new FluidStack(FluidRegistry.WATER, 1_000), true));
            FluidTankInfo[] info = h.senderTile.getTankInfo(ForgeDirection.WEST);
            assertEquals(ForgeDirection.NORTH, h.tank.lastInfoSide,
                    "Tank queries must use the remote contact face, just like fill()");
            assertEquals(1, info.length);
            assertEquals(1_000, info[0].fluid.amount);
            assertEquals(16_000, info[0].capacity);
        }

        public void itemHopperInsertsAndMergesWithoutLosingItems() throws Exception {
            Harness h = new Harness(Kind.ITEM);
            ItemStack first = taggedItems(70, "first");
            ItemStack remainder = TileEntityHopper.func_145889_a(h.senderTile, first, ForgeDirection.WEST.ordinal());
            assertNull(remainder);
            assertEquals(70, h.storedUnits());
            assertEquals(32, h.inventory.getStackInSlot(0).stackSize);
            assertEquals(32, h.inventory.getStackInSlot(1).stackSize);
            assertEquals(6, h.inventory.getStackInSlot(2).stackSize);
            ItemStack second = taggedItems(40, "second");
            remainder = TileEntityHopper.func_145889_a(h.senderTile, second, ForgeDirection.WEST.ordinal());
            assertNotNull(remainder);
            assertEquals(40, remainder.stackSize, "Different NBT must not merge into existing stacks");
            assertEquals(70, h.storedUnits());
            assertEquals("first", h.inventory.getStackInSlot(0).getTagCompound().getString("batch"));
            assertTrue(h.inventory.dirtyCalls > 0);
            assertFalse(h.senderTile.canExtractItem(0, h.inventory.getStackInSlot(0), ForgeDirection.WEST.ordinal()));
        }

        public void itemInventoryProxyPreservesStacksAndMetadata() throws Exception {
            Harness h = new Harness(Kind.ITEM);
            GTMTE_LongDistancePipelineItem sender = (GTMTE_LongDistancePipelineItem) h.sender;
            assertEquals(3, sender.getSizeInventory());
            assertEquals(32, sender.getInventoryStackLimit());
            assertEquals("Remote warehouse", sender.getInventoryName());
            assertTrue(sender.hasCustomInventoryName());
            assertArrayEquals(new int[] { 0, 1, 2 }, sender.getAccessibleSlotsFromSide(4));
            ItemStack stack = taggedItems(20, "proxy");
            sender.setInventorySlotContents(1, stack);
            assertSame(stack, h.inventory.getStackInSlot(1));
            assertSame(stack, sender.getStackInSlot(1));
            ItemStack removed = sender.decrStackSize(1, 7);
            assertEquals(7, removed.stackSize);
            assertEquals(stack.getTagCompound(), removed.getTagCompound());
            assertEquals(13, h.inventory.getStackInSlot(1).stackSize);
            assertEquals(13, sender.getStackInSlotOnClosing(1).stackSize);
            assertNull(h.inventory.getStackInSlot(1));
            assertTrue(sender.isItemValidForSlot(0, new ItemStack(Items.iron_ingot)));
            assertFalse(sender.isItemValidForSlot(0, new ItemStack(Items.coal)));
        }

        public void itemSidedInventoryUsesRemoteInventoryContactSide() throws Exception {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
                Harness h = new Harness(Kind.ITEM, 8, direction);
                verifySidedInsertion(h, direction.getOpposite());
            }
            Harness bent = new Harness(Kind.ITEM);
            bent.bendRoute();
            verifySidedInsertion(bent, ForgeDirection.NORTH);
        }

        private void verifySidedInsertion(Harness h, ForgeDirection contactSide) {
            SidedInventoryTile inventory = new SidedInventoryTile(contactSide.ordinal());
            h.placeStorage(inventory);
            ItemStack remainder = TileEntityHopper.func_145889_a(h.senderTile, taggedItems(8, "sided"),
                    h.senderTile.getFrontFacing());
            assertNull(remainder, "Insertion must use the remote inventory contact side: " + contactSide);
            assertEquals(contactSide.ordinal(), inventory.lastSlotsSide);
            assertEquals(contactSide.ordinal(), inventory.lastInsertSide);
            assertEquals(8, inventory.getStackInSlot(1).stackSize);
            assertNull(inventory.getStackInSlot(0));
            assertNull(inventory.getStackInSlot(2));
            assertFalse(h.senderTile.canInsertItem(0, taggedItems(1, "denied"), h.senderTile.getFrontFacing()));
            assertFalse(h.senderTile.canInsertItem(1, new ItemStack(Items.coal), h.senderTile.getFrontFacing()));
            assertFalse(h.senderTile.canExtractItem(1, inventory.getStackInSlot(1), h.senderTile.getFrontFacing()));
        }

        public void missingOutputStorageFailsSafely() throws Exception {
            for (Kind kind : Kind.values()) {
                Harness h = new Harness(kind);
                h.placeStorage(new TileEntity());
                assertTrue(h.sender.checkTarget());
                assertFalse(h.transferOne());
                if (kind == Kind.ITEM) {
                    GTMTE_LongDistancePipelineItem item = (GTMTE_LongDistancePipelineItem) h.sender;
                    assertEquals(0, item.getSizeInventory());
                    assertNull(item.getStackInSlot(0));
                    assertArrayEquals(new int[0], item.getAccessibleSlotsFromSide(4));
                    assertFalse(item.canInsertItem(0, taggedItems(1, "missing"), 4));
                } else {
                    assertNotNull(h.senderTile.getTankInfo(ForgeDirection.WEST));
                }
            }
        }

        public void fluidSupportsStorageFrontAndBackPortsInAnyOrientation() throws Exception {
            for (ForgeDirection lineDirection : ForgeDirection.VALID_DIRECTIONS) {
                ForgeDirection touchingFace = lineDirection.getOpposite();
                for (ForgeDirection storageFacing : ForgeDirection.VALID_DIRECTIONS) {
                    for (boolean frontPort : new boolean[] { true, false }) {
                        Harness h = new Harness(Kind.FLUID, 8, lineDirection);
                        ForgeDirection inputSide = frontPort ? storageFacing : storageFacing.getOpposite();
                        TrackingTank storage = new TrackingTank(inputSide);
                        h.placeStorage(storage);
                        boolean connected = inputSide == touchingFace;
                        String context = "line=" + lineDirection + ", storageFacing=" + storageFacing + ", frontPort="
                                + frontPort;
                        ForgeDirection callerSide = ForgeDirection.getOrientation(h.senderTile.getFrontFacing());
                        assertEquals(connected, h.senderTile.canFill(callerSide, FluidRegistry.WATER), context);
                        assertEquals(connected ? 8 : 0,
                                h.senderTile.fill(callerSide, new FluidStack(FluidRegistry.WATER, 8), true), context);
                        assertEquals(connected ? 8 : 0, storage.getFluidAmount(), context);
                        FluidTankInfo[] info = h.senderTile.getTankInfo(callerSide);
                        assertEquals(touchingFace, storage.lastInfoSide, context);
                        assertEquals(connected ? 1 : 0, info.length, context);
                    }
                }
            }
        }

        public void itemSupportsStorageFrontAndBackPortsInAnyOrientation() throws Exception {
            for (ForgeDirection lineDirection : ForgeDirection.VALID_DIRECTIONS) {
                ForgeDirection touchingFace = lineDirection.getOpposite();
                for (ForgeDirection storageFacing : ForgeDirection.VALID_DIRECTIONS) {
                    for (boolean frontPort : new boolean[] { true, false }) {
                        Harness h = new Harness(Kind.ITEM, 8, lineDirection);
                        ForgeDirection inputSide = frontPort ? storageFacing : storageFacing.getOpposite();
                        SidedInventoryTile storage = new SidedInventoryTile(inputSide.ordinal());
                        h.placeStorage(storage);
                        boolean connected = inputSide == touchingFace;
                        String context = "line=" + lineDirection + ", storageFacing=" + storageFacing + ", frontPort="
                                + frontPort;
                        ItemStack remainder = TileEntityHopper.func_145889_a(h.senderTile,
                                taggedItems(8, "storage-port"), h.senderTile.getFrontFacing());
                        assertEquals(touchingFace.ordinal(), storage.lastSlotsSide, context);
                        assertEquals(connected ? 8 : 0,
                                storage.getStackInSlot(1) == null ? 0 : storage.getStackInSlot(1).stackSize, context);
                        assertEquals(connected ? 0 : 8, remainder == null ? 0 : remainder.stackSize, context);
                        if (connected) {
                            assertEquals(touchingFace.ordinal(), storage.lastInsertSide, context);
                        }
                    }
                }
            }
        }
    }

    private static ItemStack taggedItems(int amount, String batch) {
        ItemStack stack = new ItemStack(Items.iron_ingot, amount, 3);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("batch", batch);
        stack.setTagCompound(tag);
        return stack;
    }

    private static ChunkCoordinates offset(ChunkCoordinates start, ForgeDirection direction, int distance) {
        return new ChunkCoordinates(start.posX + direction.offsetX * distance,
                start.posY + direction.offsetY * distance, start.posZ + direction.offsetZ * distance);
    }

    public enum Kind {
        FLUID(0), ITEM(1);

        final int metadata;

        Kind(int metadata) {
            this.metadata = metadata;
        }

        GTMTE_LongDistancePipelineBase create() {
            if (this == FLUID) {
                return new GTMTE_LongDistancePipelineFluid("test.fluid.pipeline", 1, "", null);
            }
            return new GTMTE_LongDistancePipelineItem("test.item.pipeline", 1, "", null);
        }
    }

    public static class Harness {
        final Kind kind;
        final TestWorld world = new TestWorld();
        final ChunkCoordinates start;
        GTMTE_LongDistancePipelineBase sender;
        BaseMetaTileEntity senderTile;
        GTMTE_LongDistancePipelineBase receiver;
        BaseMetaTileEntity receiverTile;
        ChunkCoordinates storagePosition;
        TrackingTank tank;
        InventoryTile inventory;

        Harness(Kind kind) throws Exception {
            this(kind, 32, ForgeDirection.EAST);
        }

        Harness(Kind kind, int length, ForgeDirection direction) throws Exception {
            this(kind, length, direction, new ChunkCoordinates(8, 64, 8));
        }

        Harness(Kind kind, int length, ForgeDirection direction, ChunkCoordinates start) throws Exception {
            this.kind = kind;
            this.start = start;
            world.pipe = Scenarios.pipe;
            sender = endpoint(kind, start, direction.getOpposite());
            senderTile = (BaseMetaTileEntity) sender.getBaseMetaTileEntity();
            receiver = endpoint(kind, offset(start, direction, length + 1), direction.getOpposite());
            receiverTile = (BaseMetaTileEntity) receiver.getBaseMetaTileEntity();
            for (int distance = 1; distance <= length; distance++) {
                world.pipes.put(offset(start, direction, distance), kind.metadata);
            }
            storagePosition = offset(receiver.getCoords(), direction, 1);
            placeDefaultStorage(direction.getOpposite());
        }

        GTMTE_LongDistancePipelineBase endpoint(Kind endpointKind, ChunkCoordinates position, ForgeDirection front)
                throws Exception {
            GTMTE_LongDistancePipelineBase endpoint = endpointKind.create();
            BaseMetaTileEntity tile = new BaseMetaTileEntity();
            tile.setWorldObj(world);
            tile.xCoord = position.posX;
            tile.yCoord = position.posY;
            tile.zCoord = position.posZ;
            endpoint.setBaseMetaTileEntity(tile);
            Scenarios.setField(BaseMetaTileEntity.class, tile, "mFacing", (byte) front.ordinal());
            Scenarios.setField(BaseMetaTileEntity.class, tile, "mTickTimer", 10L);
            world.tiles.put(position, tile);
            return endpoint;
        }

        void placeDefaultStorage(ForgeDirection contactSide) {
            if (kind == Kind.FLUID) {
                tank = new TrackingTank(contactSide);
                placeStorage(tank);
            } else {
                inventory = new InventoryTile();
                placeStorage(inventory);
            }
        }

        void placeStorage(TileEntity storage) {
            storage.setWorldObj(world);
            storage.xCoord = storagePosition.posX;
            storage.yCoord = storagePosition.posY;
            storage.zCoord = storagePosition.posZ;
            world.tiles.put(storagePosition, storage);
        }

        void bendRoute() throws Exception {
            ChunkCoordinates previousPosition = receiver.getCoords();
            receiverTile.invalidate();
            world.tiles.remove(previousPosition);
            world.tiles.remove(storagePosition);
            world.pipes.clear();
            world.pipes.put(new ChunkCoordinates(9, 64, 8), kind.metadata);
            world.pipes.put(new ChunkCoordinates(10, 64, 8), kind.metadata);
            world.pipes.put(new ChunkCoordinates(10, 64, 9), kind.metadata);
            world.pipes.put(new ChunkCoordinates(10, 64, 10), kind.metadata);
            receiver = endpoint(kind, new ChunkCoordinates(10, 64, 11), ForgeDirection.NORTH);
            receiverTile = (BaseMetaTileEntity) receiver.getBaseMetaTileEntity();
            storagePosition = new ChunkCoordinates(10, 64, 12);
            placeDefaultStorage(ForgeDirection.NORTH);
        }

        void replaceReceiver(Kind endpointKind) throws Exception {
            ChunkCoordinates position = new ChunkCoordinates(receiverTile.xCoord, receiverTile.yCoord,
                    receiverTile.zCoord);
            ForgeDirection facing = ForgeDirection.getOrientation(receiverTile.getFrontFacing());
            receiver = endpoint(endpointKind, position, facing);
            receiverTile = (BaseMetaTileEntity) receiver.getBaseMetaTileEntity();
        }

        void reloadSender() throws Exception {
            NBTTagCompound saved = new NBTTagCompound();
            sender.saveNBTData(saved);
            ForgeDirection facing = ForgeDirection.getOrientation(senderTile.getFrontFacing());
            senderTile.invalidate();
            sender = endpoint(kind, start, facing);
            senderTile = (BaseMetaTileEntity) sender.getBaseMetaTileEntity();
            sender.loadNBTData(saved);
        }

        boolean transferOne() {
            if (kind == Kind.FLUID) {
                return senderTile.fill(ForgeDirection.getOrientation(senderTile.getFrontFacing()),
                        new FluidStack(FluidRegistry.WATER, 1), true) == 1;
            }
            ItemStack stack = taggedItems(1, "route");
            return TileEntityHopper.func_145889_a(senderTile, stack, senderTile.getFrontFacing()) == null;
        }

        int storedUnits() {
            if (kind == Kind.FLUID) {
                return tank.getFluidAmount();
            }
            int total = 0;
            for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
                ItemStack stack = inventory.getStackInSlot(slot);
                if (stack != null) {
                    total += stack.stackSize;
                }
            }
            return total;
        }
    }

    public static class TrackingTank extends TileEntity implements IFluidHandler {
        final FluidTank tank = new FluidTank(16_000);
        final ForgeDirection contactSide;
        ForgeDirection lastInfoSide;
        int dirtyCalls;

        TrackingTank(ForgeDirection contactSide) {
            this.contactSide = contactSide;
        }

        int getFluidAmount() {
            return tank.getFluidAmount();
        }

        @Override
        public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
            if (from != contactSide) {
                return 0;
            }
            int accepted = tank.fill(resource, doFill);
            if (doFill && accepted > 0) {
                markDirty();
            }
            return accepted;
        }

        @Override
        public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
            return tank.drain(resource == null ? 0 : resource.amount, doDrain);
        }

        @Override
        public FluidStack drain(ForgeDirection from, int amount, boolean doDrain) {
            return tank.drain(amount, doDrain);
        }

        @Override
        public boolean canFill(ForgeDirection from, Fluid fluid) {
            return from == contactSide;
        }

        @Override
        public boolean canDrain(ForgeDirection from, Fluid fluid) {
            return true;
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection from) {
            lastInfoSide = from;
            if (from != contactSide) {
                return new FluidTankInfo[0];
            }
            return new FluidTankInfo[] { tank.getInfo() };
        }

        @Override
        public void markDirty() {
            dirtyCalls++;
            super.markDirty();
        }
    }

    public static class InventoryTile extends TileEntity implements IInventory {
        private final InventoryBasic inventory = new InventoryBasic("Remote warehouse", true, 3);
        int dirtyCalls;

        @Override
        public int getSizeInventory() {
            return inventory.getSizeInventory();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public ItemStack decrStackSize(int slot, int amount) {
            ItemStack removed = inventory.decrStackSize(slot, amount);
            if (removed != null) {
                markDirty();
            }
            return removed;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int slot) {
            ItemStack removed = inventory.getStackInSlotOnClosing(slot);
            if (removed != null) {
                markDirty();
            }
            return removed;
        }

        @Override
        public void setInventorySlotContents(int slot, ItemStack stack) {
            inventory.setInventorySlotContents(slot, stack);
            markDirty();
        }

        @Override
        public String getInventoryName() {
            return inventory.getInventoryName();
        }

        @Override
        public boolean hasCustomInventoryName() {
            return inventory.hasCustomInventoryName();
        }

        @Override
        public int getInventoryStackLimit() {
            return 32;
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player) {
            return true;
        }

        @Override
        public void openInventory() {
        }

        @Override
        public void closeInventory() {
        }

        @Override
        public boolean isItemValidForSlot(int slot, ItemStack stack) {
            return stack != null && stack.getItem() == Items.iron_ingot;
        }

        @Override
        public void markDirty() {
            dirtyCalls++;
            super.markDirty();
        }
    }

    public static class SidedInventoryTile extends InventoryTile implements ISidedInventory {
        final int contactSide;
        int lastSlotsSide = -1;
        int lastInsertSide = -1;

        SidedInventoryTile(int contactSide) {
            this.contactSide = contactSide;
        }

        @Override
        public int[] getAccessibleSlotsFromSide(int side) {
            lastSlotsSide = side;
            return side == contactSide ? new int[] { 1 } : new int[0];
        }

        @Override
        public boolean canInsertItem(int slot, ItemStack stack, int side) {
            lastInsertSide = side;
            return side == contactSide && slot == 1 && isItemValidForSlot(slot, stack);
        }

        @Override
        public boolean canExtractItem(int slot, ItemStack stack, int side) {
            return true;
        }
    }

    public static class TestWorld extends World {
        final Map<ChunkCoordinates, TileEntity> tiles = new HashMap<>();
        final Map<ChunkCoordinates, Integer> pipes = new HashMap<>();
        final Set<Long> unloadedChunks = new HashSet<>();
        GT_Block_LongDistancePipe pipe;

        TestWorld() {
            super(new SaveHandlerMP(), "transport-pipeline-test", new WorldProviderSurface(),
                    new WorldSettings(0, WorldSettings.GameType.CREATIVE, false, false, WorldType.DEFAULT),
                    new Profiler());
        }

        private static long chunkKey(int x, int z) {
            return ((long) (x >> 4) << 32) ^ ((z >> 4) & 0xFFFF_FFFFL);
        }

        void unload(ChunkCoordinates position) {
            unloadedChunks.add(chunkKey(position.posX, position.posZ));
        }

        @Override
        public boolean blockExists(int x, int y, int z) {
            return y >= 0 && y < 256 && !unloadedChunks.contains(chunkKey(x, z));
        }

        @Override
        public TileEntity getTileEntity(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Tile lookup must not force a chunk load");
            return tiles.get(new ChunkCoordinates(x, y, z));
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Pipe scan must not force a chunk load");
            return pipes.containsKey(new ChunkCoordinates(x, y, z)) ? pipe : Blocks.air;
        }

        @Override
        public int getBlockMetadata(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Metadata lookup must not force a chunk load");
            return pipes.getOrDefault(new ChunkCoordinates(x, y, z), 0);
        }

        @Override
        public void markTileEntityChunkModified(int x, int y, int z, TileEntity tile) {
        }

        @Override
        public Chunk getChunkFromBlockCoords(int x, int z) {
            assertTrue(blockExists(x, 64, z));
            return new Chunk(this, x >> 4, z >> 4);
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
