package com.impact.util.fluid;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import appeng.api.AEApi;
import appeng.api.IAppEngApi;
import appeng.api.config.Actionable;
import appeng.api.config.FuzzyMode;
import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IUpgradeModule;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCellArrayUpdate;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.ICellWorkbenchItem;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEStackType;
import appeng.api.storage.data.IItemList;
import appeng.core.api.ApiStorage;
import appeng.tile.inventory.IAEStackInventory;
import com.google.common.base.Optional;
import com.google.common.collect.HashBiMap;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверяет настоящее хранилище и стеки AE2; сетевой транспорт и мир заменены стендом. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TankHatchTest {
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
        loader.registerTransformer("appeng.transformer.asm.ASMIntegration");
        loader.registerTransformer("appeng.transformer.asm.ApiRepairer");
        loader.registerTransformer(getClass().getName() + "$ApiFactoryTransformer");
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
    void exportsOncePerGridAndDoesNotOverpromiseSimulation() throws Throwable {
        check("exportsOncePerGridAndDoesNotOverpromiseSimulation");
    }

    @Test
    void notifiesExactDeltasAndBothLockTransitions() throws Throwable {
        check("notifiesExactDeltasAndBothLockTransitions");
    }

    @Test
    void unsupportedTaggedFluidsAreNotMergedWithPlainFluid() throws Throwable {
        check("unsupportedTaggedFluidsAreNotMergedWithPlainFluid");
    }

    @Test
    void nativeMeOperationsAreNotReportedTwice() throws Throwable {
        check("nativeMeOperationsAreNotReportedTwice");
    }

    @Test
    void independentNetworksReceiveEachOthersChanges() throws Throwable {
        check("independentNetworksReceiveEachOthersChanges");
    }

    @Test
    void invalidBindingCannotAccessPreviousStorage() throws Throwable {
        check("invalidBindingCannotAccessPreviousStorage");
    }

    @Test
    void detachRemovesWatchersAndTransfersExport() throws Throwable {
        check("detachRemovesWatchersAndTransfersExport");
    }

    @Test
    void controllerStorageChangesMarkItsChunkDirty() throws Throwable {
        check("controllerStorageChangesMarkItsChunkDirty");
    }

    @Test
    void priorityChangeRefreshesExportAndPersists() throws Throwable {
        check("priorityChangeRefreshesExportAndPersists");
    }

    @Test
    void invalidStructureAndControllerRemovalDetachHatches() throws Throwable {
        check("invalidStructureAndControllerRemovalDetachHatches");
    }

    @Test
    void proxyLoadsBeforeReadyAndRestartsAfterUnload() throws Throwable {
        check("proxyLoadsBeforeReadyAndRestartsAfterUnload");
    }

    @Test
    void configuredFluidCellAllowsMeInsertionInBothControllers() throws Throwable {
        check("configuredFluidCellAllowsMeInsertionInBothControllers");
    }

    @Test
    void fluidCellSupportsMultipleEntriesAndInverter() throws Throwable {
        check("fluidCellSupportsMultipleEntriesAndInverter");
    }

    @Test
    void configuredContainersAndDirectContainersStillFilter() throws Throwable {
        check("configuredContainersAndDirectContainersStillFilter");
    }

    public static class Scenarios {
        public void initialize() throws Exception {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), Collections.emptyList());
            Bootstrap.func_151354_b();
            Launch.classLoader = (LaunchClassLoader) getClass().getClassLoader();
            ModMetadata metadata = new ModMetadata();
            metadata.modId = "appliedenergistics2";
            DummyModContainer container = new DummyModContainer(metadata);
            Map<String, ModContainer> mods = new HashMap<>();
            mods.put(metadata.modId, container);
            setField(Loader.class, Loader.instance(), "namedMods", mods);
            LoadController controller = new LoadController(Loader.instance());
            setField(LoadController.class, controller, "activeContainer", container);
            setField(LoadController.class, controller, "modObjectList", HashBiMap.create());
            setField(Loader.class, Loader.instance(), "modController", controller);
            setField(FMLInjectionData.class, null, "minecraftHome", new File("."));
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
        }

        public void exportsOncePerGridAndDoesNotOverpromiseSimulation() throws Exception {
            Harness h = new Harness(false);
            Network network = new Network();
            Hatch first = h.hatch(network, 0);
            Hatch second = h.hatch(network, 1);
            h.storage.pushFluid(water(10_000), true);
            List<IAEFluidStack> advertised = new ArrayList<>();
            IItemList<IAEFluidStack> collector = collector(advertised);
            first.getAvailableItems(collector);
            second.getAvailableItems(collector);
            assertEquals(1, advertised.size());
            assertEquals(10_000, advertised.get(0).getStackSize());
            assertEquals(1, first.getCellArray(StorageChannel.FLUIDS).size());
            assertTrue(second.getCellArray(StorageChannel.FLUIDS).isEmpty());
            IAEFluidStack request = GTMTE_TankHatch.createAEFluidStack(water(20_000));
            assertEquals(10_000, first.extractItems(request, Actionable.SIMULATE, null).getStackSize());
            assertNull(second.extractItems(request, Actionable.SIMULATE, null));
            assertEquals(10_000, h.storage.getFluid(0).amount);
            first.extractItems(request, Actionable.MODULATE, null);
            assertTrue(h.storage.getFluids().isEmpty());
        }

        public void notifiesExactDeltasAndBothLockTransitions() throws Exception {
            Harness h = new Harness(false);
            Network network = new Network();
            Hatch hatch = h.hatch(network, 0);
            hatch.tick();
            network.clear();
            FluidStack tagged = water(2_000);
            h.storage.pushFluid(tagged, true);
            hatch.tick();
            assertEquals(0, network.cellUpdates);
            assertEquals(2_000, network.deltas.get(0).getStackSize());
            network.clear();
            FluidStack request = tagged.copy();
            request.amount = 700;
            h.storage.pullFluid(request, true);
            hatch.tick();
            assertEquals(-700, network.deltas.get(0).getStackSize());
            network.clear();
            h.storage.setLock(true);
            hatch.tick();
            assertEquals(-1_300, network.deltas.get(0).getStackSize());
            network.clear();
            h.storage.setLock(false);
            hatch.tick();
            assertEquals(1_300, network.deltas.get(0).getStackSize());
            network.clear();
            hatch.tick();
            assertTrue(network.deltas.isEmpty());
            assertEquals(0, network.cellUpdates);
        }

        public void unsupportedTaggedFluidsAreNotMergedWithPlainFluid() throws Exception {
            Harness h = new Harness(true);
            Network network = new Network();
            Hatch hatch = h.hatch(network, 0);
            hatch.tick();
            network.clear();
            FluidStack tagged = water(2_000);
            tagged.tag = new NBTTagCompound();
            tagged.tag.setString("variant", "test");
            IAEFluidStack taggedRequest = (IAEFluidStack) Proxy.newProxyInstance(IAEFluidStack.class.getClassLoader(),
                    new Class<?>[] { IAEFluidStack.class }, (proxy, method, args) -> {
                        if (method.getName().equals("getStackSize")) {
                            return (long) tagged.amount;
                        }
                        if (method.getName().equals("getFluidStack")) {
                            return tagged.copy();
                        }
                        throw new AssertionError("Unexpected tagged request call: " + method.getName());
                    });
            assertSame(taggedRequest, hatch.injectItems(taggedRequest, Actionable.MODULATE, null));
            assertNull(hatch.extractItems(taggedRequest, Actionable.MODULATE, null));
            h.storage.pushFluid(tagged, true);
            h.storage.pushFluid(water(1_000), true);
            hatch.tick();
            assertEquals(1, network.deltas.size());
            assertEquals(1_000, network.deltas.get(0).getStackSize());
            List<IAEFluidStack> advertised = new ArrayList<>();
            hatch.getAvailableItems(collector(advertised));
            assertEquals(1, advertised.size());
            assertEquals(1_000, advertised.get(0).getStackSize());
            IAEFluidStack extracted = hatch.extractItems(GTMTE_TankHatch.createAEFluidStack(water(3_000)),
                    Actionable.MODULATE, null);
            assertEquals(1_000, extracted.getStackSize());
            assertEquals(2_000, h.storage.getFluid(0).amount);
            assertEquals(tagged.tag, h.storage.getFluid(0).tag);
        }

        private static IItemList<IAEFluidStack> collector(List<IAEFluidStack> fluids) {
            return (IItemList<IAEFluidStack>) Proxy.newProxyInstance(IItemList.class.getClassLoader(),
                    new Class<?>[] { IItemList.class }, (proxy, method, args) -> {
                        if (method.getName().equals("add")) {
                            fluids.add(((IAEFluidStack) args[0]).copy());
                            return null;
                        }
                        throw new AssertionError("Unexpected list call: " + method.getName());
                    });
        }

        public void nativeMeOperationsAreNotReportedTwice() throws Exception {
            Harness h = new Harness(false);
            Network network = new Network();
            Hatch hatch = h.hatch(network, 0);
            h.storage.pushFluid(water(10_000), true);
            hatch.tick();
            network.clear();
            // Внешнее изменение до ME-операции в том же тике нельзя потерять при подтверждении дельты.
            h.storage.pushFluid(water(500), true);
            hatch.extractItems(GTMTE_TankHatch.createAEFluidStack(water(1_000)), Actionable.MODULATE, null);
            hatch.tick();
            assertEquals(1, network.deltas.size());
            assertEquals(500, network.deltas.get(0).getStackSize());
            network.clear();
            hatch.injectItems(GTMTE_TankHatch.createAEFluidStack(water(200)), Actionable.MODULATE, null);
            hatch.tick();
            assertTrue(network.deltas.isEmpty());
            h.storage.setVoidExcess(true);
            h.storage.setCapacity(9_700);
            hatch.injectItems(GTMTE_TankHatch.createAEFluidStack(water(500)), Actionable.MODULATE, null);
            hatch.tick();
            assertEquals(-500, network.deltas.get(0).getStackSize(), "Уничтоженный избыток не остаётся в ME-учёте");
        }

        public void independentNetworksReceiveEachOthersChanges() throws Exception {
            Harness h = new Harness(false);
            Network firstGrid = new Network();
            Network secondGrid = new Network();
            Hatch first = h.hatch(firstGrid, 0);
            Hatch second = h.hatch(secondGrid, 1);
            first.tick();
            second.tick();
            firstGrid.clear();
            secondGrid.clear();
            first.injectItems(GTMTE_TankHatch.createAEFluidStack(water(700)), Actionable.MODULATE, null);
            first.tick();
            second.tick();
            assertTrue(firstGrid.deltas.isEmpty());
            assertEquals(700, secondGrid.deltas.get(0).getStackSize());
        }

        public void invalidBindingCannotAccessPreviousStorage() throws Exception {
            Harness h = new Harness(false);
            Hatch hatch = h.hatch(new Network(), 0);
            h.storage.pushFluid(water(3_000), true);
            IAEFluidStack request = GTMTE_TankHatch.createAEFluidStack(water(500));
            h.world.isRemote = true;
            assertNull(hatch.extractItems(request, Actionable.MODULATE, null));
            assertSame(request, hatch.injectItems(request, Actionable.MODULATE, null));
            h.world.isRemote = false;
            h.world.controllerLoaded = false;
            assertNull(hatch.extractItems(request, Actionable.MODULATE, null));
            assertSame(request, hatch.injectItems(request, Actionable.MODULATE, null));
            assertEquals(3_000, h.storage.getFluid(0).amount);
            h.world.controllerLoaded = true;
            MultiFluidHandler previous = h.storage;
            h.owner.loadNBTData(h.saved(8_000));
            assertNull(hatch.extractItems(request, Actionable.MODULATE, null));
            assertEquals(3_000, previous.getFluid(0).amount);
            hatch.tick();
            assertTrue(previous.getFluidWatchers().isEmpty());
            h.storage = ((IMultiFluidTank) h.owner).getMultiFluidHandler();
            h.storage.setLock(false);
            hatch.setMultiFluidHandler(h.storage, h.controller);
            assertEquals(500, hatch.extractItems(request, Actionable.MODULATE, null).getStackSize());
            assertEquals(7_500, h.storage.getFluid(0).amount);
        }

        public void detachRemovesWatchersAndTransfersExport() throws Exception {
            Harness h = new Harness(false);
            Network network = new Network();
            Hatch first = h.hatch(network, 0);
            Hatch second = h.hatch(network, 1);
            first.tick();
            second.tick();
            network.clear();
            first.inValidate();
            assertEquals(Collections.singletonList(second), h.storage.getFluidWatchers());
            assertEquals(1, second.getCellArray(StorageChannel.FLUIDS).size());
            second.tick();
            assertEquals(1, network.cellUpdates);
            second.getProxy().onChunkUnload();
            assertTrue(h.storage.getFluidWatchers().isEmpty());
        }

        public void controllerStorageChangesMarkItsChunkDirty() throws Exception {
            for (boolean multi : new boolean[] { false, true }) {
                Harness h = new Harness(multi);
                Hatch hatch = h.hatch(new Network(), 0);
                h.world.chunk.isModified = false;
                h.storage.pushFluid(water(1_000), false);
                assertFalse(h.world.chunk.isModified);
                h.storage.pushFluid(water(1_000), true);
                assertTrue(h.world.chunk.isModified);
                h.world.chunk.isModified = false;
                hatch.saveChanges(hatch);
                assertTrue(h.world.chunk.isModified);
                h.world.chunk.isModified = false;
                h.storage.pullFluid(water(500), true);
                assertTrue(h.world.chunk.isModified);
                h.world.chunk.isModified = false;
                h.storage.setVoidExcess(true);
                assertTrue(h.world.chunk.isModified);
            }
        }

        public void priorityChangeRefreshesExportAndPersists() throws Exception {
            Harness h = new Harness(false);
            Network network = new Network();
            Hatch first = h.hatch(network, 0);
            Hatch second = h.hatch(network, 1);
            first.tick();
            second.tick();
            network.clear();
            h.world.chunk.isModified = false;
            second.setPriority(200);
            assertTrue(h.world.chunk.isModified);
            assertTrue(first.getCellArray(StorageChannel.FLUIDS).isEmpty());
            assertEquals(1, second.getCellArray(StorageChannel.FLUIDS).size());
            first.tick();
            second.tick();
            assertTrue(network.cellUpdates > 0);
            NBTTagCompound saved = new NBTTagCompound();
            second.saveNBTData(saved);
            Hatch restored = h.hatch(new Network(), 2);
            restored.loadNBTData(saved);
            assertEquals(200, restored.getPriority());
            first.tick();
            second.tick();
            network.clear();
            second.setPriority(200);
            first.tick();
            second.tick();
            assertEquals(0, network.cellUpdates);
        }

        public void invalidStructureAndControllerRemovalDetachHatches() throws Exception {
            for (boolean multi : new boolean[] { false, true }) {
                Harness h = new Harness(multi);
                Hatch hatch = h.hatch(new Network(), 0);
                Class<?> ownerType = multi ? GTMTE_MultiTank.class : GTMTE_SingleTank.class;
                Field field = ownerType.getDeclaredField("sMultiHatches");
                field.setAccessible(true);
                Set<GTMTE_TankHatch> hatches = (Set<GTMTE_TankHatch>) field.get(h.owner);
                hatches.add(hatch);
                boolean formed = multi ? ((GTMTE_MultiTank) h.owner).checkMachine(h.controller, null)
                        : ((GTMTE_SingleTank) h.owner).checkMachine(h.controller, null);
                assertFalse(formed);
                assertTrue(h.storage.getFluidWatchers().isEmpty());
                assertTrue(hatch.getCellArray(StorageChannel.FLUIDS).isEmpty());
                hatch.setMultiFluidHandler(h.storage, h.controller);
                hatches.add(hatch);
                h.owner.onRemoval();
                assertTrue(h.storage.getFluidWatchers().isEmpty());
            }
        }

        public void proxyLoadsBeforeReadyAndRestartsAfterUnload() throws Exception {
            Harness h = new Harness(false);
            Hatch hatch = h.hatch(new Network(), 0);
            int before = ApiFactory.createdNodes;
            NBTTagCompound data = new NBTTagCompound();
            NBTTagCompound proxy = new NBTTagCompound();
            proxy.setInteger("testOwner", 42);
            data.setTag("proxy", proxy);
            hatch.loadNBTData(data);
            assertNull(hatch.getProxy().getNode());
            assertEquals(before, ApiFactory.createdNodes);
            NBTTagCompound pending = new NBTTagCompound();
            hatch.saveNBTData(pending);
            assertEquals(42, pending.getCompoundTag("proxy").getInteger("testOwner"));
            hatch.onFirstTick(hatch.base);
            assertEquals(before + 1, ApiFactory.createdNodes);
            assertTrue(ApiFactory.flagsConfigured);
            NBTTagCompound saved = new NBTTagCompound();
            hatch.saveNBTData(saved);
            assertEquals(42, saved.getCompoundTag("proxy").getInteger("testOwner"));
            hatch.getProxy().onChunkUnload();
            hatch.onFirstTick(hatch.base);
            assertEquals(before + 2, ApiFactory.createdNodes);
            NBTTagCompound reloaded = new NBTTagCompound();
            hatch.saveNBTData(reloaded);
            assertEquals(42, reloaded.getCompoundTag("proxy").getInteger("testOwner"));
        }

        public void configuredFluidCellAllowsMeInsertionInBothControllers() throws Exception {
            for (boolean multi : new boolean[] { false, true }) {
                Harness h = new Harness(multi);
                Hatch hatch = h.hatch(new Network(), 0);
                ConfiguredCell cell = new ConfiguredCell();
                IAEFluidStack sample = GTMTE_TankHatch.createAEFluidStack(water(1_000));
                cell.config.putAEStackInSlot(0, sample);
                ItemStack filter = new ItemStack(cell);
                assertNull(cell.getConfigInventory(filter).getStackInSlot(0), "Старый API не видит жидкостную запись");
                if (multi) {
                    ((GTMTE_MultiTank) h.owner).checkRecipe(filter);
                } else {
                    ((GTMTE_SingleTank) h.owner).checkRecipe(filter);
                }
                IAEFluidStack input = GTMTE_TankHatch.createAEFluidStack(water(2_000));
                IAEFluidStack refused = GTMTE_TankHatch.createAEFluidStack(FluidRegistry.LAVA, 2_000);
                assertNull(hatch.injectItems(input, Actionable.SIMULATE, null));
                assertEquals(0, h.storage.getFluid(0).amount);
                assertNull(hatch.injectItems(input, Actionable.MODULATE, null));
                assertSame(refused, hatch.injectItems(refused, Actionable.MODULATE, null));
                assertEquals(2_000, h.storage.getFluid(0).amount);
                assertEquals(1_000, sample.getStackSize(), "Образец фильтра не расходуется");
                MultiFluidHandler restored = new MultiFluidHandler(multi ? 25 : 1);
                restored.loadNBTData(h.storage.saveNBTData(null));
                restored.setLock(false);
                assertEquals(100, restored.pushFluid(water(100), true));
                assertEquals(0, restored.pushFluid(new FluidStack(FluidRegistry.LAVA, 100), true));
            }
        }

        public void fluidCellSupportsMultipleEntriesAndInverter() throws Exception {
            Harness h = new Harness(true);
            Hatch hatch = h.hatch(new Network(), 0);
            ConfiguredCell cell = new ConfiguredCell();
            cell.config.putAEStackInSlot(0, GTMTE_TankHatch.createAEFluidStack(water(1_000)));
            cell.config.putAEStackInSlot(1, GTMTE_TankHatch.createAEFluidStack(FluidRegistry.LAVA, 1_000));
            FluidRegistry.registerFluid(new Fluid("tank_filter_unlisted"));
            IAEFluidStack unlisted = GTMTE_TankHatch.createAEFluidStack(FluidRegistry.getFluid("tank_filter_unlisted"),
                    500);
            ItemStack filter = new ItemStack(cell);
            ((GTMTE_MultiTank) h.owner).checkRecipe(filter);
            assertNull(hatch.injectItems(GTMTE_TankHatch.createAEFluidStack(water(500)), Actionable.MODULATE, null));
            assertNull(hatch.injectItems(GTMTE_TankHatch.createAEFluidStack(FluidRegistry.LAVA, 500),
                    Actionable.MODULATE, null));
            assertSame(unlisted, hatch.injectItems(unlisted, Actionable.MODULATE, null));
            cell.upgrades.setInventorySlotContents(0, new ItemStack(new InverterCard()));
            ((GTMTE_MultiTank) h.owner).checkRecipe(filter);
            assertFalse(hatch.canAccept(GTMTE_TankHatch.createAEFluidStack(water(1))));
            assertFalse(hatch.canAccept(GTMTE_TankHatch.createAEFluidStack(FluidRegistry.LAVA, 1)));
            assertNull(hatch.injectItems(unlisted, Actionable.MODULATE, null));
            h.storage.setFilterFluids(null);
            assertTrue(hatch.canAccept(GTMTE_TankHatch.createAEFluidStack(water(1))));
        }

        public void configuredContainersAndDirectContainersStillFilter() {
            ItemStack bucket = new ItemStack(Items.water_bucket);
            GT_Utility.addFluidContainerData(
                    new FluidContainerRegistry.FluidContainerData(water(1_000), bucket, new ItemStack(Items.bucket)));
            ConfiguredCell cell = new ConfiguredCell();
            cell.config.putAEStackInSlot(0, AEApi.instance().storage().createItemStack(bucket));
            for (ItemStack filter : new ItemStack[] { bucket, new ItemStack(cell) }) {
                MultiFluidHandler tank = new MultiFluidHandler(64_000, 25);
                tank.setLock(false);
                tank.setFilterFluids(filter);
                assertEquals(1_000, tank.pushFluid(water(1_000), true));
                assertEquals(0, tank.pushFluid(new FluidStack(FluidRegistry.LAVA, 1_000), true));
                assertSame(Items.water_bucket, bucket.getItem());
            }
        }

        private static FluidStack water(int amount) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }

        private static void setField(Class<?> type, Object owner, String name, Object value) throws Exception {
            Field field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(owner, value);
        }
    }

    public static class ConfiguredCell extends Item implements ICellWorkbenchItem {
        final IAEStackInventory config = new IAEStackInventory(null, 63);
        final IInventory upgrades = new InventoryBasic("test.cell.upgrades", false, 2);

        @Override
        public boolean isEditable(ItemStack stack) {
            return true;
        }

        @Override
        public IInventory getUpgradesInventory(ItemStack stack) {
            return upgrades;
        }

        @Override
        public IAEStackInventory getConfigAEInventory(ItemStack stack) {
            return config;
        }

        @Override
        public IAEStackType<?> getStackType() {
            return null;
        }

        @Override
        public FuzzyMode getFuzzyMode(ItemStack stack) {
            return FuzzyMode.IGNORE_ALL;
        }

        @Override
        public void setFuzzyMode(ItemStack stack, FuzzyMode mode) {
        }
    }

    public static class InverterCard extends Item implements IUpgradeModule {
        @Override
        public Upgrades getType(ItemStack stack) {
            return Upgrades.INVERTER;
        }
    }

    public static class Harness {
        final TestWorld world = new TestWorld();
        final MetaTileEntity owner;
        final BaseMetaTileEntity controller;
        MultiFluidHandler storage;

        Harness(boolean multi) {
            owner = multi ? new GTMTE_MultiTank("test.multi") {
                @Override
                public void run() {
                }
            } : new GTMTE_SingleTank("test.single") {
                @Override
                public void run() {
                }
            };
            controller = tile(owner, 32);
            owner.loadNBTData(saved(0));
            storage = ((IMultiFluidTank) owner).getMultiFluidHandler();
            storage.setLock(false);
            controller.setActive(true);
        }

        NBTTagCompound saved(int amount) {
            MultiFluidHandler tank = new MultiFluidHandler(64_000, 25);
            tank.setLock(false);
            if (amount > 0) {
                tank.pushFluid(new FluidStack(FluidRegistry.WATER, amount), true);
            }
            NBTTagCompound saved = new NBTTagCompound();
            saved.setTag("fluids", tank.saveNBTData(null));
            return saved;
        }

        BaseMetaTileEntity tile(MetaTileEntity machine, int x) {
            BaseMetaTileEntity tile = new BaseMetaTileEntity();
            tile.setWorldObj(world);
            tile.xCoord = x;
            tile.yCoord = 64;
            machine.setBaseMetaTileEntity(tile);
            world.tiles.put(x, tile);
            return tile;
        }

        Hatch hatch(Network network, int x) {
            Hatch hatch = new Hatch(network);
            hatch.base = tile(hatch, x);
            hatch.setMultiFluidHandler(storage, controller);
            return hatch;
        }
    }

    public static class Hatch extends GTMTE_TankHatch {
        final Network network;
        BaseMetaTileEntity base;
        long ticks;

        Hatch(Network network) {
            super("test.me.hatch", 5, new String[0], new ITexture[0][][]);
            this.network = network;
        }

        @Override
        public IGridNode getGridNode(ForgeDirection side) {
            return network.node;
        }

        @Override
        public ItemStack getStackForm(long amount) {
            return null;
        }

        void tick() {
            onPostTick(base, ++ticks);
        }
    }

    public static class Network {
        int cellUpdates;
        final List<IAEFluidStack> deltas = new ArrayList<>();
        final IStorageGrid storage = (IStorageGrid) Proxy.newProxyInstance(IStorageGrid.class.getClassLoader(),
                new Class<?>[] { IStorageGrid.class }, (proxy, method, args) -> {
                    if (method.getName().equals("postAlterationOfStoredItems")) {
                        for (Object entry : (Iterable<?>) args[1]) {
                            deltas.add(((IAEFluidStack) entry).copy());
                        }
                    }
                    return null;
                });
        final IGrid grid = (IGrid) Proxy.newProxyInstance(IGrid.class.getClassLoader(), new Class<?>[] { IGrid.class },
                (proxy, method, args) -> {
                    if (method.getName().equals("getCache")) {
                        return storage;
                    }
                    if (method.getName().equals("postEvent")) {
                        if (args[0] instanceof MENetworkCellArrayUpdate) {
                            cellUpdates++;
                        }
                        return args[0];
                    }
                    return null;
                });
        final IGridNode node = (IGridNode) Proxy.newProxyInstance(IGridNode.class.getClassLoader(),
                new Class<?>[] { IGridNode.class }, (proxy, method, args) -> {
                    if (method.getName().equals("getGrid")) {
                        return grid;
                    }
                    if (method.getName().equals("isActive")) {
                        return true;
                    }
                    return null;
                });

        void clear() {
            cellUpdates = 0;
            deltas.clear();
        }
    }

    public static class TestWorld extends World {
        final Map<Integer, TileEntity> tiles = new HashMap<>();
        final Chunk chunk = new Chunk(this, 2, 0);
        boolean controllerLoaded = true;

        TestWorld() {
            super(new SaveHandlerMP(), "tank-test", new WorldProviderSurface(),
                    new WorldSettings(0, WorldSettings.GameType.CREATIVE, false, false, WorldType.DEFAULT),
                    new Profiler());
        }

        @Override
        public boolean blockExists(int x, int y, int z) {
            return x < 32 || controllerLoaded;
        }

        @Override
        public TileEntity getTileEntity(int x, int y, int z) {
            assertTrue(blockExists(x, y, z), "Доступ не должен загружать чанк контроллера");
            return tiles.get(x);
        }

        @Override
        public Chunk getChunkFromBlockCoords(int x, int z) {
            return chunk;
        }

        @Override
        public Block getBlock(int x, int y, int z) {
            return Blocks.air;
        }

        @Override
        public int getBlockMetadata(int x, int y, int z) {
            return 0;
        }

        @Override
        public void markTileEntityChunkModified(int x, int y, int z, TileEntity tile) {
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

    /** Исключает регистрацию блоков всего AE2; ApiStorage и AEFluidStack остаются настоящими. */
    public static class ApiFactoryTransformer implements IClassTransformer {
        @Override
        public byte[] transform(String name, String transformedName, byte[] bytes) {
            if (!"appeng.api.AEApi".equals(transformedName)) {
                return bytes;
            }
            ClassNode type = new ClassNode();
            new ClassReader(bytes).accept(type, 0);
            for (Object candidate : type.methods) {
                MethodNode method = (MethodNode) candidate;
                if (!method.name.equals("<clinit>") && !method.name.equals("instance")) {
                    continue;
                }
                method.instructions.clear();
                method.tryCatchBlocks.clear();
                if (method.localVariables != null) {
                    method.localVariables.clear();
                }
                if (method.name.equals("instance")) {
                    method.instructions.add(
                            new MethodInsnNode(Opcodes.INVOKESTATIC, "com/impact/util/fluid/TankHatchTest$ApiFactory",
                                    "api", "()Lappeng/api/IAppEngApi;", false));
                    method.instructions.add(new InsnNode(Opcodes.ARETURN));
                } else {
                    method.instructions.add(new InsnNode(Opcodes.RETURN));
                }
            }
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            type.accept(writer);
            return writer.toByteArray();
        }
    }

    public static class ApiFactory {
        static int createdNodes;
        static boolean flagsConfigured;

        public static IAppEngApi api() {
            return (IAppEngApi) empty(IAppEngApi.class);
        }

        private static Object empty(Class<?> type) {
            return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, (proxy, method, args) -> {
                if (method.getName().equals("storage")) {
                    return new ApiStorage();
                }
                if (method.getName().equals("createGridNode")) {
                    createdNodes++;
                    flagsConfigured = ((IGridBlock) args[0]).getFlags().contains(GridFlags.REQUIRE_CHANNEL);
                    NBTTagCompound data = new NBTTagCompound();
                    return Proxy.newProxyInstance(IGridNode.class.getClassLoader(), new Class<?>[] { IGridNode.class },
                            (node, call, params) -> {
                                if (call.getName().equals("loadFromNBT")) {
                                    data.setTag("saved",
                                            ((NBTTagCompound) params[1]).getCompoundTag((String) params[0]).copy());
                                }
                                if (call.getName().equals("saveToNBT")) {
                                    ((NBTTagCompound) params[1]).setTag((String) params[0],
                                            data.getCompoundTag("saved").copy());
                                }
                                return null;
                            });
                }
                if (method.getReturnType() == Optional.class) {
                    return Optional.absent();
                }
                if (method.getReturnType().isInterface()) {
                    return empty(method.getReturnType());
                }
                throw new AssertionError("Unexpected API call: " + method.getName());
            });
        }
    }
}
