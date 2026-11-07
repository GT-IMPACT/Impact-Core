package com.impact.mods.gregtech;

import com.cleanroommc.modularui.api.RecipeViewerSettings;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularContainer;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.MouseData;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.ModularSyncManager;
import com.cleanroommc.modularui.value.sync.PanelSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.value.sync.ValueSyncHandler;
import com.cleanroommc.modularui.widget.WidgetTree;
import com.cleanroommc.modularui.widget.sizer.Area;
import com.cleanroommc.modularui.widget.sizer.ResizeNode;
import com.cleanroommc.modularui.widget.sizer.StaticResizer;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.google.common.io.ByteStreams;
import com.impact.mods.gregtech.gui.aerostat.ContainerSelectAerostat;
import com.impact.mods.gregtech.gui.lap.LapChargeHistory;
import com.impact.mods.gregtech.gui.lap.LapFlowHistory;
import com.impact.mods.gregtech.gui.processing.ImpactProcessingGui;
import com.impact.mods.gregtech.tileentities.covers.DetectorCover;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvEUDetector;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvEUDetectorRange;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvFluidDetectorBound;
import com.impact.mods.gregtech.tileentities.covers.GTC_AdvFluidDetectorRange;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorI;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorIII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_AdvancedMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_BasicMiner;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_CommunicationTower_Receiver;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_TowerCommunication;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_CokeOven;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_TheMill;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_AdvancedPyrolyse;
import com.impact.mods.gregtech.tileentities.multi.processing.parallel.GTMTE_Mixer;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_BasicWaterPump;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_MoonMiner;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import gregtech.api.GregTech_API;
import gregtech.api.covers.CoverFactory;
import gregtech.api.covers.CoverRegistry;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ITexture;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.modularui2.CoverGuiData;
import gregtech.api.modularui2.CoverUIFactory;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.modularui2.MetaTileEntityGuiHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiControls;
import gregtech.common.modularui2.widget.CoverTabButton;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.jupiter.api.Test;

import static gregtech.api.gui.GT_Container_ProcessingMachine.BATCH_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.LOCK_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.POWER_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.RECIPE_MAP_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.SEPARATION_SLOT;
import static gregtech.api.gui.GT_Container_ProcessingMachine.VOIDING_SLOT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImpactProcessingGuiTest {

    @Test
    void minersExposeOnlyPowerAndOutputProtectionAndRejectUnsupportedModes() throws Throwable {
        run("miners");
    }

    @Test
    void nativeImpactCoversKeepSettingsAndBuildStandaloneAndMachinePopupControls() throws Throwable {
        run("covers");
    }

    @Test
    void realControllerSlotsKeepIndicesContentsAndOutputRestrictions() throws Throwable {
        run("inventory");
    }

    @Test
    void millShiftClickCannotInsertIntoOutputSlots() throws Throwable {
        run("millShiftClick");
    }

    @Test
    void millClearsFullyConsumedInputFromItsGuiSlot() throws Throwable {
        run("millConsumedInput");
    }

    @Test
    void storageTankGaugesKeepTheFilterSlotAndSyncReservedFluids() throws Throwable {
        run("tanks");
    }

    @Test
    void parallelComputerSyncsAllRacksWithinTheExistingScreen() throws Throwable {
        run("parallelComputer");
    }

    @Test
    void communicationTowerPagesAllConnectionsWithinTheExistingScreen() throws Throwable {
        run("communicationTower");
    }

    @Test
    void lapPeriodsSyncMatchingAveragesAndHistoryWithoutResizingThePanel() throws Throwable {
        run("lapHistory");
    }

    @Test
    void reactorPowerRodsAndTemperatureUseServerPacketsAndSurviveReload() throws Throwable {
        run("reactor");
    }

    @Test
    void rodSlidersValidatePacketsForEveryReactorTierAndIndividualHatches() throws Throwable {
        run("rods");
    }

    @Test
    void specialActionsRejectClosedDistantAndDetachedWindows() throws Throwable {
        run("access");
    }

    @Test
    void travelControlsKeepStationSelectionAndShiftNavigation() throws Throwable {
        run("travel");
    }

    @Test
    void allRegisteredControllersBuildNativePanelsWithoutDuplicateSlots() throws Throwable {
        run("registry");
    }

    @Test
    void stationNameEditorValidatesAndAppliesOnlyConfirmedServerInput() throws Throwable {
        run("name");
    }

    private void run(String scenario) throws Throwable {
        LaunchClassLoader previousLaunch = Launch.classLoader;
        Map<String, Object> previousBlackboard = Launch.blackboard;
        ClassLoader previousContext = Thread.currentThread().getContextClassLoader();
        String previousThread = Thread.currentThread().getName();
        try (LaunchClassLoader loader = new LaunchClassLoader(
                ((URLClassLoader) getClass().getClassLoader()).getURLs())) {
            loader.addClassLoaderExclusion("org.junit.");
            Launch.classLoader = loader;
            Launch.blackboard = new HashMap<>();
            Thread.currentThread().setContextClassLoader(loader);
            Thread.currentThread().setName("Server thread");
            try {
                loader.loadClass(getClass().getName() + "$Scenarios")
                        .getMethod("check", String.class).invoke(null, scenario);
            } catch (InvocationTargetException failure) {
                throw failure.getTargetException();
            }
        } finally {
            Launch.classLoader = previousLaunch;
            Launch.blackboard = previousBlackboard;
            Thread.currentThread().setContextClassLoader(previousContext);
            Thread.currentThread().setName(previousThread);
        }
    }

    public static final class Scenarios {

        public static void check(String scenario) throws Exception {
            Launch.blackboard.put("fml.deobfuscatedEnvironment", true);
            Launch.classLoader.registerTransformer("cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer");
            Field side = FMLRelaunchLog.class.getDeclaredField("side");
            side.setAccessible(true);
            side.set(null, Side.SERVER);
            Field home = FMLInjectionData.class.getDeclaredField("minecraftHome");
            home.setAccessible(true);
            home.set(null, new File("."));
            ImpactProcessingMigrationTest.Scenarios.initialize();
            FMLServerHandler.instance();
            GTGuis.init();
            switch (scenario) {
                case "miners":
                    miners();
                    break;
                case "covers":
                    covers();
                    break;
                case "lapHistory":
                    lapHistory();
                    break;
                case "communicationTower":
                    communicationTower();
                    break;
                case "parallelComputer":
                    parallelComputer();
                    break;
                case "name":
                    nameEditor();
                    break;
                case "registry":
                    registry();
                    break;
                case "inventory":
                    inventory();
                    break;
                case "millShiftClick":
                    millShiftClick();
                    break;
                case "millConsumedInput":
                    millConsumedInput();
                    break;
                case "tanks":
                    tanks();
                    break;
                case "reactor":
                    reactor();
                    break;
                case "rods":
                    rods();
                    break;
                case "access":
                    access();
                    break;
                case "travel":
                    travel();
                    break;
                default:
                    throw new AssertionError(scenario);
            }
        }

        private static void miners() throws Exception {
            GT_MetaTileEntity_ProcessingMultiBlockBase<?>[] miners = {
                new GTMTE_Mining_Coal("coal"),
                new GTMTE_BasicMiner("basic"),
                new GTMTE_AdvancedMiner("advanced"),
                new GTMTE_MoonMiner("moon")
            };
            for (GT_MetaTileEntity_ProcessingMultiBlockBase<?> miner : miners) {
                boolean primitive = miner instanceof GTMTE_Mining_Coal;
                int protection = primitive ? 0 : ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS;
                assertEquals(protection, miner.getSupportedProcessingModes(), miner.mName);
                Fixture nativeGui = miner.useMui2() ? new Fixture(miner) : null;
                AttachedMachine fixture = nativeGui != null ? nativeGui : new AttachedMachine(miner);
                if (nativeGui != null) {
                    nativeGui.checkLayout();
                }
                for (int index = 0; index <= RECIPE_MAP_SLOT; index++) {
                    assertEquals(index == POWER_SLOT || !primitive && index == VOIDING_SLOT,
                        ProcessingGuiControls.isControlVisible(miner, index), miner.mName + ": " + index);
                }
                miner.setProcessingModes(ProcessingLogic.ALL_MODES);
                assertEquals(protection, miner.getProcessingModes());
                for (int hidden : new int[]{BATCH_SLOT, LOCK_SLOT, SEPARATION_SLOT}) {
                    clickMinerControl(fixture, hidden);
                    assertEquals(protection, miner.getProcessingModes());
                }
                int[] expectedProtection = primitive ? new int[]{0} : new int[]{
                    ProcessingLogic.PROTECT_FLUIDS, ProcessingLogic.PROTECT_ITEMS, 0, protection
                };
                for (int expected : expectedProtection) {
                    clickMinerControl(fixture, VOIDING_SLOT);
                    assertEquals(expected, miner.getProcessingModes());
                }
                fixture.tile.disableWorking();
                clickMinerControl(fixture, POWER_SLOT);
                assertTrue(fixture.tile.isAllowedToWork());
                clickMinerControl(fixture, POWER_SLOT);
                assertFalse(fixture.tile.isAllowedToWork());

                NBTTagCompound saved = new NBTTagCompound();
                miner.saveNBTData(saved);
                saved.getCompoundTag("processing").setInteger("modes", ProcessingLogic.ALL_MODES);
                miner.loadNBTData(saved);
                assertEquals(protection, miner.getProcessingModes());
            }
        }

        private static void clickMinerControl(AttachedMachine fixture, int index) throws Exception {
            if (fixture instanceof Fixture) {
                ((Fixture) fixture).press("processingControl_" + index, false);
            } else {
                ProcessingGuiControls.click((GT_MetaTileEntity_ProcessingMultiBlockBase<?>) fixture.machine, index, 0);
            }
        }

        private static void covers() throws Exception {
            CoverFactory[] factories = {
                GTC_AdvFluidDetectorBound::new,
                GTC_AdvEUDetector::new,
                GTC_AdvFluidDetectorRange::new,
                GTC_AdvEUDetectorRange::new
            };
            Item[] items = {Items.apple, Items.paper, Items.stick, Items.coal};
            for (int index = 0; index < factories.length; index++) {
                GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("cover_host");
                machine.capacity = 4_000_000_000L;
                AttachedMachine fixture = new AttachedMachine(machine);
                ItemStack stack = new ItemStack(items[index]);
                CoverRegistry.registerCover(stack, null, factories[index]);
                int id = GT_Utility.stackToInt(stack);
                fixture.tile.setCoverIDAtSide((byte) ForgeDirection.NORTH.ordinal(), id);
                DetectorCover cover = (DetectorCover) fixture.tile.getCoverAtSide(ForgeDirection.NORTH);
                assertTrue(CoverRegistry.isNativeCover(stack));
                assertTrue(cover.useMui2());
                assertTrue(cover.hasCoverGUI());
                for (int setting : new int[]{0, -50, 75, 12900, Integer.MIN_VALUE}) {
                    NBTTagCompound legacy = new NBTTagCompound();
                    legacy.setTag("d", new NBTTagInt(setting));
                    cover.readFromNbt(legacy);
                    assertEquals(setting, cover.getLegacyData());
                    DetectorCover copy = (DetectorCover) CoverRegistry.buildCover(
                        id,
                        ForgeDirection.NORTH,
                        fixture.tile
                    );
                    copy.readFromNbt(cover.writeToNBT(new NBTTagCompound()));
                    assertEquals(setting, copy.getLegacyData());
                    ByteBuf packet = Unpooled.buffer();
                    try {
                        cover.writeToByteBuf(packet);
                        byte[] bytes = new byte[packet.readableBytes()];
                        packet.readBytes(bytes);
                        copy.readFromPacket(ByteStreams.newDataInput(bytes));
                        assertEquals(setting, copy.getLegacyData());
                    } finally {
                        packet.release();
                    }
                }
                cover.setLegacyData(0);
                for (boolean popup : new boolean[]{false, true}) {
                    ModularSyncManager manager = new ModularSyncManager(false);
                    PanelSyncManager handlers = new PanelSyncManager(manager, true);
                    UISettings settings = new UISettings(RecipeViewerSettings.DUMMY);
                    CoverGuiData data = new CoverGuiData(fixture.player, id, 0, 64, 0, ForgeDirection.NORTH, popup);
                    ModularPanel panel = popup
                        ? cover.buildPopUpUI(data, "cover_test", handlers, settings, new ButtonWidget<>())
                        : CoverUIFactory.INSTANCE.createPanel(data, handlers, settings);
                    WidgetTree.collectSyncValues(handlers, panel);
                    ModularContainer container = new ModularContainer();
                    container.construct(fixture.player, manager, settings, panel.getName(), data);
                    assertTrue(container.isInitialized());
                    if (!popup) {
                        assertEquals(36, container.inventorySlots.size());
                    }
                    assertNotNull(handlers.findSyncHandler("capacity", LongSyncValue.class));
                    IntSyncValue value = handlers.findSyncHandler(
                        cover.isRange() ? "start_percent" : "setting", IntSyncValue.class);
                    PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
                    try {
                        packet.writeVarIntToBuffer(55);
                        value.readOnServer(ValueSyncHandler.SYNC_VALUE, packet);
                        assertEquals(cover.isRange() ? 55 << 7 : cover.isFluid() ? 0 : 55, cover.getLegacyData());
                        fixture.tile.detachCover(ForgeDirection.NORTH);
                        packet.clear();
                        packet.writeVarIntToBuffer(80);
                        value.readOnServer(ValueSyncHandler.SYNC_VALUE, packet);
                        assertEquals(cover.isRange() ? 55 << 7 : cover.isFluid() ? 0 : 55, cover.getLegacyData());
                        fixture.tile.attachCover(cover);
                    } finally {
                        packet.release();
                    }
                }
            }
        }

        private static void nameEditor() throws Exception {
            NamedMachine machine = new NamedMachine();
            Fixture fixture = new Fixture(machine);
            PanelSyncHandler handler = (PanelSyncHandler) fixture.handlers.findPanelHandlerNullable("impact_name_editor");
            handler.openPanel();
            Field managerField = PanelSyncHandler.class.getDeclaredField("syncManager");
            managerField.setAccessible(true);
            PanelSyncManager editor = (PanelSyncManager) managerField.get(handler);
            StringSyncValue text = editor.findSyncHandler("stationName", StringSyncValue.class);
            InteractionSyncHandler accept = editor.findSyncHandler("acceptName", InteractionSyncHandler.class);
            sendText(text, "Valid station");
            assertEquals("original", machine.stationName);
            press(accept, false);
            assertEquals("Valid station", machine.stationName);
            assertEquals(1, machine.applied);
            sendText(text, " ");
            press(accept, false);
            assertEquals(1, machine.applied);
            sendText(text, "12345678901234567");
            press(accept, false);
            assertEquals(1, machine.applied);
            fixture.player.openContainer = fixture.player.inventoryContainer;
            sendText(text, "changed");
            press(accept, false);
            assertEquals(1, machine.applied);
        }

        private static void sendText(StringSyncValue target, String value) throws Exception {
            StringSyncValue source = new StringSyncValue(() -> value);
            source.updateCacheFromSource(false);
            PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
            try {
                source.write(packet);
                target.readOnServer(ValueSyncHandler.SYNC_VALUE, packet);
            } finally {
                packet.release();
            }
        }

        private static void registry() throws Exception {
            int count = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Scenarios.class.getResourceAsStream("/structures/controllers.tsv"), StandardCharsets.UTF_8))) {
                String row;
                while ((row = reader.readLine()) != null) {
                    String name = row.split("\t")[1];
                    Class<?> type = Class.forName(name);
                    MetaTileEntity machine;
                    if (name.contains("GTMTE_SOFC_")) {
                        int tier = name.substring(name.lastIndexOf('_') + 1).length() - 1;
                        machine = (MetaTileEntity) type.getConstructor(String.class, int.class).newInstance(name, tier);
                    } else {
                        machine = (MetaTileEntity) type.getConstructor(String.class).newInstance(name);
                    }
                    if (machine instanceof GTMTE_TheMill || machine instanceof GTMTE_Mining_Coal
                            || machine instanceof GTMTE_Aerostat) {
                        assertFalse(machine.useMui2(), name);
                        count++;
                        continue;
                    }
                    if (machine instanceof GTMTE_CokeOven || machine instanceof GTMTE_BasicWaterPump) {
                        AttachedMachine fixture = new AttachedMachine(machine);
                        assertFalse(machine.useMui2(), name);
                        assertFalse(machine.onRightclick(fixture.tile, fixture.player), name);
                        assertNull(machine.getServerGUI(0, fixture.player.inventory, fixture.tile), name);
                        assertNull(machine.getClientGUI(0, fixture.player.inventory, fixture.tile), name);
                        count++;
                        continue;
                    }
                    Fixture fixture = new Fixture(machine);
                    List<Integer> indices = new ArrayList<>();
                    for (Object entry : fixture.container.inventorySlots) {
                        ModularSlot slot = (ModularSlot) entry;
                        if (slot.getItemHandler() == machine.getInventoryHandler()) {
                            assertFalse(indices.contains(slot.getSlotIndex()), name);
                            indices.add(slot.getSlotIndex());
                            assertTrue(slot.getSlotIndex() < machine.mInventory.length, name);
                        }
                    }
                    assertNotNull(fixture.find("processing_display"), name);
                    assertNotNull(fixture.find("processing_control_" + POWER_SLOT), name);
                    fixture.checkLayout();
                    count++;
                }
            }
            assertEquals(67, count);
        }

        private static void inventory() throws Exception {
            MetaTileEntity[] machines = {
                    new GTMTE_TheMill("mill"), new GTMTE_Mining_Coal("coal")
            };
            int[][] indices = { { 0, 1, 2, 3, 4 }, { 0, 1 } };
            for (int index = 0; index < machines.length; index++) {
                MetaTileEntity machine = machines[index];
                AttachedMachine fixture = new AttachedMachine(machine);
                Container container = (Container) machine.getServerGUI(0, fixture.player.inventory, fixture.tile);
                assertNotNull(container);
                fixture.player.openContainer = container;
                assertTrue(container.canInteractWith(fixture.player));
                assertEquals(36 + indices[index].length, container.inventorySlots.size());
                for (int inventoryIndex : indices[index]) {
                    Slot slot = (Slot) container.inventorySlots.get(inventoryIndex);
                    boolean input = inventoryIndex == 0;
                    assertEquals(input, slot.isItemValid(new ItemStack(Items.coal)));
                    machine.mInventory[inventoryIndex] = new ItemStack(Items.coal, inventoryIndex + 2);
                    assertEquals(inventoryIndex + 2, slot.getStack().stackSize);
                    assertTrue(slot.canTakeStack(fixture.player));
                    container.transferStackInSlot(fixture.player, slot.slotNumber);
                    assertTrue(machine.mInventory[inventoryIndex] == null);
                }
                NBTTagCompound saved = new NBTTagCompound();
                machine.mInventory[0] = new ItemStack(Items.coal, 17);
                fixture.tile.writeToNBT(saved);
                int id = 31900 + index;
                GregTech_API.METATILEENTITIES[id] = machine;
                saved.setInteger("mID", id);
                AttachedMachine restored = new AttachedMachine((MetaTileEntity) machine.newMetaEntity(null));
                restored.tile.readFromNBT(saved);
                MetaTileEntity loaded = (MetaTileEntity) restored.tile.getMetaTileEntity();
                assertEquals(17, loaded.mInventory[0].stackSize);
            }
        }

        private static void millShiftClick() throws Exception {
            GTMTE_TheMill mill = new GTMTE_TheMill("mill-shift-click");
            AttachedMachine fixture = new AttachedMachine(mill);
            Container container = (Container) mill.getServerGUI(0, fixture.player.inventory, fixture.tile);
            fixture.player.openContainer = container;
            int firstPlayerSlot = 5;
            fixture.tile.setInventorySlotContents(0, new ItemStack(Items.coal, 64));
            fixture.player.inventory.setInventorySlotContents(9, new ItemStack(Items.sugar, 7));

            container.slotClick(firstPlayerSlot, 0, 1, fixture.player);

            for (int slot = 1; slot < 5; slot++) {
                assertNull(fixture.tile.getStackInSlot(slot), "Shift-click must leave output slot " + slot + " empty");
            }
            assertEquals(7, fixture.player.inventory.getStackInSlot(9).stackSize);
            fixture.tile.setInventorySlotContents(0, null);

            container.slotClick(firstPlayerSlot, 0, 1, fixture.player);

            assertNull(fixture.player.inventory.getStackInSlot(9));
            assertEquals(7, fixture.tile.getStackInSlot(0).stackSize);
        }

        private static void millConsumedInput() throws Exception {
            GTMTE_TheMill mill = new GTMTE_TheMill("mill-consumed-input");
            AttachedMachine fixture = new AttachedMachine(mill);
            Container container = (Container) mill.getServerGUI(0, fixture.player.inventory, fixture.tile);
            fixture.player.openContainer = container;
            assertNotNull(mill.getRecipeMap().addRecipe(
                    false,
                    new ItemStack[]{new ItemStack(Items.wheat, 2)},
                    new ItemStack[]{new ItemStack(Items.bread)},
                    null,
                    new int[]{10000},
                    null,
                    null,
                    40,
                    0,
                    0
            ));
            fixture.tile.setInventorySlotContents(0, new ItemStack(Items.wheat, 2));

            assertTrue(mill.checkRecipe(null), String.valueOf(mill.getProcessingResult()));
            assertNull(container.getSlot(0).getStack(), "A consumed input must not leave a zero-size stack");
            assertEquals(1, mill.mOutputItems[0].stackSize);
        }

        private static void tanks() throws Exception {
            MetaTileEntity[] tanks = {new GTMTE_SingleTank("single"), new GTMTE_MultiTank("multi")};
            for (MetaTileEntity tank : tanks) {
                boolean multi = tank instanceof GTMTE_MultiTank;
                int slots = multi ? 25 : 1;
                Fixture fixture = new Fixture(tank);
                NBTTagCompound saved = new NBTTagCompound();
                saved.setInteger("capacityPerFluid", 1_000_000);
                saved.setTag("0", new FluidStack(FluidRegistry.WATER, 700_000).writeToNBT(new NBTTagCompound()));
                if (multi) {
                    saved.setTag("1", new FluidStack(FluidRegistry.LAVA, 0).writeToNBT(new NBTTagCompound()));
                }
                tank.loadNBTData(saved);

                fixture.checkLayout();
                assertEquals(37, fixture.container.inventorySlots.size());
                for (int index = 0; index < slots; index++) {
                    assertNotNull(fixture.find("tank_fluid_" + index));
                }
                assertNull(fixture.find("tank_fluid_" + slots));
                ModularSlot filter = fixture.machineSlot(1);
                ItemStack waterBucket = new ItemStack(Items.water_bucket);
                assertTrue(filter.isItemValid(waterBucket));
                filter.putStack(waterBucket);
                assertEquals(waterBucket.getItem(), tank.mInventory[1].getItem());

                GenericSyncValue<?, ?> sync = fixture.handlers.findSyncHandler("tankStorage", GenericSyncValue.class);
                assertNotNull(sync);
                assertFalse(sync.isAllowC2S());
                sync.updateCacheFromSource(false);
                NBTTagCompound payload = (NBTTagCompound) sync.getValue();
                assertEquals(1_000_000, payload.getInteger("capacity"));
                NBTTagList fluids = payload.getTagList("fluids", 10);
                assertEquals(multi ? 2 : 1, fluids.tagCount());
                FluidStack water = FluidStack.loadFluidStackFromNBT(fluids.getCompoundTagAt(0));
                assertEquals(FluidRegistry.WATER, water.getFluid());
                assertEquals(700_000, water.amount);
                if (multi) {
                    FluidStack reserved = FluidStack.loadFluidStackFromNBT(fluids.getCompoundTagAt(1));
                    assertEquals(FluidRegistry.LAVA, reserved.getFluid());
                    assertEquals(0, reserved.amount);
                }
            }
        }

        private static double copySliderToClient(DoubleSyncValue server) throws Exception {
            server.updateCacheFromSource(false);
            DoubleSyncValue client = new DoubleSyncValue(() -> 0);
            PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
            try {
                server.write(packet);
                client.read(packet);
                return client.getDoubleValue();
            } finally {
                packet.release();
            }
        }

        private static void lapHistory() throws Exception {
            GTMTE_LapPowerStation machine = new GTMTE_LapPowerStation("lap");
            machine.mMachine = true;
            machine.capacity = 10000;
            machine.stored = 5000;
            machine.intputLastTick = 999;
            machine.outputLastTick = 123;
            Field flowsField = GTMTE_LapPowerStation.class.getDeclaredField("flowHistory");
            flowsField.setAccessible(true);
            LapFlowHistory flows = (LapFlowHistory) flowsField.get(machine);
            for (int tick = 0; tick < 72000; tick++) {
                flows.sample(tick >= 70800 ? 300 : tick >= 60000 ? 200 : 100, 10);
            }
            Field chargeField = GTMTE_LapPowerStation.class.getDeclaredField("chargeHistory");
            chargeField.setAccessible(true);
            LapChargeHistory charge = (LapChargeHistory) chargeField.get(machine);
            for (int second = 0; second <= 3600; second++) {
                charge.sample(second * 20L, second, machine.capacity);
            }
            Fixture fixture = new Fixture(machine);
            GenericSyncValue<?, ?> history = fixture.handlers.findSyncHandler("lapHistory", GenericSyncValue.class);
            GenericSyncValue<?, ?> readings = fixture.handlers.findSyncHandler("lapReadings", GenericSyncValue.class);
            assertFalse(history.isAllowC2S());
            assertFalse(readings.isAllowC2S());
            history.updateCacheFromSource(false);
            NBTTagCompound snapshot = (NBTTagCompound) history.getValue();
            assertEquals("minute", snapshot.getString("period"));
            assertEquals(300, snapshot.getLong("averageInput"));
            assertEquals(10, snapshot.getLong("averageOutput"));
            assertEquals(3540, snapshot.getIntArray("charge")[0]);
            fixture.press("lap_period_ten_minutes", false);
            history.updateCacheFromSource(false);
            snapshot = (NBTTagCompound) history.getValue();
            assertEquals("ten_minutes", snapshot.getString("period"));
            assertEquals(210, snapshot.getLong("averageInput"));
            assertEquals(3000, snapshot.getIntArray("charge")[0]);
            fixture.press("lap_period_hour", false);
            history.updateCacheFromSource(false);
            snapshot = (NBTTagCompound) history.getValue();
            assertEquals("hour", snapshot.getString("period"));
            assertEquals(118, snapshot.getLong("averageInput"));
            assertEquals(61, snapshot.getIntArray("charge").length);
            assertEquals(0, snapshot.getIntArray("charge")[0]);
            assertEquals(3600, snapshot.getIntArray("charge")[60]);
            fixture.player.setPosition(100, 64, 100);
            fixture.press("lap_period_minute", false);
            history.updateCacheFromSource(false);
            assertEquals("hour", ((NBTTagCompound) history.getValue()).getString("period"));
            fixture.player.setPosition(0.5, 64.5, 0.5);
            fixture.player.openContainer = fixture.player.inventoryContainer;
            fixture.press("lap_period_minute", false);
            history.updateCacheFromSource(false);
            assertEquals("hour", ((NBTTagCompound) history.getValue()).getString("period"));
            fixture.player.openContainer = fixture.container;
            readings.updateCacheFromSource(false);
            NBTTagCompound telemetry = (NBTTagCompound) readings.getValue();
            assertEquals(999, telemetry.getLong("input"));
            assertEquals(123, telemetry.getLong("output"));
            initializeResizers(fixture.panel, new Viewport());
            WidgetTree.resizeInternal(fixture.panel.resizer(), false);
            assertEquals(268, fixture.panel.getArea().width);
            assertEquals(238, fixture.panel.getArea().height);
            assertEquals(36, fixture.container.inventorySlots.size());
            for (String period : new String[] { "minute", "ten_minutes", "hour" }) {
                Area area = fixture.find("impact_lap_period_" + period).getArea();
                int x = area.x - fixture.panel.getArea().x;
                int y = area.y - fixture.panel.getArea().y;
                assertTrue(x >= 104 && x + area.width <= 198);
                assertEquals(108, y);
                assertEquals(11, area.height);
            }
            assertNotNull(fixture.find("lap_instant_input"));
            assertNotNull(fixture.find("lap_instant_output"));
        }

        private static void communicationTower() throws Exception {
            GTMTE_TowerCommunication tower = new GTMTE_TowerCommunication("tower");
            Fixture fixture = new Fixture(tower);
            tower.mMachine = true;
            tower.setSatelliteConnected(true);
            for (int index = 69; index >= 0; index--) {
                GTMTE_Mixer machine = new GTMTE_Mixer("machine-" + index);
                ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                        fixture.world, machine, ForgeDirection.NORTH);
                tile.xCoord = index;
                machine.setSatelliteConnected(index % 2 == 0);
                tower.getConnections().add(machine);
            }
            GTMTE_AdvancedPyrolyse unsupported = new GTMTE_AdvancedPyrolyse("unsupported");
            new ImpactConstructionChecks.TestTile(fixture.world, unsupported, ForgeDirection.NORTH);
            tower.getConnections().add(unsupported);
            tower.getConnections().add(new GTMTE_Mixer("detached"));
            for (int index = 3; index >= 0; index--) {
                GTMTE_CommunicationTower_Receiver receiver = new GTMTE_CommunicationTower_Receiver(
                        "receiver-" + index, new String[0], new ITexture[0][][]);
                ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                        fixture.world, receiver, ForgeDirection.NORTH);
                tile.xCoord = index;
                receiver.isConnected = index != 2;
                tile.setActive(true);
                tower.sCommunReceiver.add(receiver);
            }
            GenericSyncValue<?, ?> state =
                    fixture.handlers.findSyncHandler("communicationTowerState", GenericSyncValue.class);
            assertNotNull(state);
            assertFalse(state.isAllowC2S());
            state.updateCacheFromSource(false);
            NBTTagCompound snapshot = (NBTTagCompound) state.getValue();
            assertEquals(70, snapshot.getInteger("total"));
            assertEquals(35, snapshot.getInteger("connected"));
            assertEquals(3, snapshot.getInteger("pages"));
            assertTrue(snapshot.getBoolean("uplink"));
            assertArrayEquals(new int[] { 1, 1, 0, 1 }, snapshot.getIntArray("receivers"));
            assertEquals(32, snapshot.getTagList("nodes", 10).tagCount());
            assertEquals(0, snapshot.getTagList("nodes", 10).getCompoundTagAt(0).getInteger("x"));
            fixture.press("tower_next", false);
            state.updateCacheFromSource(false);
            snapshot = (NBTTagCompound) state.getValue();
            assertEquals(1, snapshot.getInteger("page"));
            assertEquals(32, snapshot.getTagList("nodes", 10).getCompoundTagAt(0).getInteger("x"));
            fixture.press("tower_next", false);
            state.updateCacheFromSource(false);
            snapshot = (NBTTagCompound) state.getValue();
            assertEquals(2, snapshot.getInteger("page"));
            assertEquals(6, snapshot.getTagList("nodes", 10).tagCount());
            fixture.press("tower_next", false);
            state.updateCacheFromSource(false);
            assertEquals(2, ((NBTTagCompound) state.getValue()).getInteger("page"));
            fixture.player.setPosition(100, 64, 100);
            fixture.press("tower_previous", false);
            state.updateCacheFromSource(false);
            assertEquals(2, ((NBTTagCompound) state.getValue()).getInteger("page"));
            fixture.player.setPosition(0.5, 64.5, 0.5);
            fixture.player.openContainer = fixture.player.inventoryContainer;
            fixture.press("tower_previous", false);
            state.updateCacheFromSource(false);
            assertEquals(2, ((NBTTagCompound) state.getValue()).getInteger("page"));
            fixture.player.openContainer = fixture.container;
            fixture.press("tower_previous", false);
            state.updateCacheFromSource(false);
            assertEquals(1, ((NBTTagCompound) state.getValue()).getInteger("page"));
            initializeResizers(fixture.panel, new Viewport());
            WidgetTree.resizeInternal(fixture.panel.resizer(), false);
            assertEquals(268, fixture.panel.getArea().width);
            assertEquals(238, fixture.panel.getArea().height);
            assertEquals(36, fixture.container.inventorySlots.size());
            for (String name : new String[] { "impact_tower_previous", "impact_tower_next" }) {
                Area area = fixture.find(name).getArea();
                int x = area.x - fixture.panel.getArea().x;
                int y = area.y - fixture.panel.getArea().y;
                assertTrue(x >= 198 && x + area.width <= 258);
                assertTrue(y >= 133 && y + area.height <= 144);
            }
            for (int index = 0; index < 32; index++) {
                IWidget connection = fixture.find("tower_connection_" + index);
                assertNotNull(connection);
                Area area = connection.getArea();
                int x = area.x - fixture.panel.getArea().x;
                int y = area.y - fixture.panel.getArea().y;
                assertEquals(10 + index % 8 * 31, x);
                assertEquals(80 + index / 8 * 12, y);
                assertEquals(28, area.width);
                assertEquals(11, area.height);
                assertTrue(x + area.width <= 262);
                assertTrue(y + area.height <= 148);
            }
            tower.getConnections().clear();
            state.updateCacheFromSource(false);
            snapshot = (NBTTagCompound) state.getValue();
            assertEquals(0, snapshot.getInteger("total"));
            assertEquals(0, snapshot.getInteger("page"));
            assertEquals(1, snapshot.getInteger("pages"));
            assertEquals(0, snapshot.getTagList("nodes", 10).tagCount());
        }

        private static void parallelComputer() throws Exception {
            GTMTE_ParallelComputer machine = new GTMTE_ParallelComputer("parallel-computer");
            Fixture fixture = new Fixture(machine);
            machine.mMachine = true;
            int[] capacities = new int[32];
            int[] states = new int[32];
            int total = 0;
            for (int index = 31; index >= 0; index--) {
                GTMTE_ComputerRack rack = new GTMTE_ComputerRack("rack-" + index, new String[0], new ITexture[0][][]);
                ImpactConstructionChecks.TestTile tile = new ImpactConstructionChecks.TestTile(
                        fixture.world, rack, ForgeDirection.NORTH);
                tile.xCoord = index;
                rack.mCapacityP = index % 3 == 0 ? 0 : index % 3 == 1 ? 64 : 256;
                tile.setActive(index % 3 == 2);
                capacities[index] = rack.mCapacityP;
                states[index] = index % 3;
                total += rack.mCapacityP;
                machine.sComputerRack.add(rack);
            }
            machine.sComputerRack.add(new GTMTE_ComputerRack("detached", new String[0], new ITexture[0][][]));
            machine.setMaxCapacityPP(total);
            machine.setCurrentCapacityPP(512);
            GenericSyncValue<?, ?> state =
                    fixture.handlers.findSyncHandler("parallelComputerState", GenericSyncValue.class);
            assertNotNull(state);
            assertFalse(state.isAllowC2S());
            state.updateCacheFromSource(false);
            NBTTagCompound snapshot = (NBTTagCompound) state.getValue();
            assertEquals(total, snapshot.getInteger("capacity"));
            assertEquals(512, snapshot.getInteger("free"));
            assertEquals(0, snapshot.getLong("consumption"));
            assertTrue(snapshot.getBoolean("formed"));
            assertArrayEquals(capacities, snapshot.getIntArray("rackCapacity"));
            assertArrayEquals(states, snapshot.getIntArray("rackState"));
            initializeResizers(fixture.panel, new Viewport());
            WidgetTree.resizeInternal(fixture.panel.resizer(), false);
            assertEquals(268, fixture.panel.getArea().width);
            assertEquals(238, fixture.panel.getArea().height);
            assertEquals(36, fixture.container.inventorySlots.size());
            for (int index = 0; index < 32; index++) {
                IWidget rack = fixture.find("parallel_rack_" + index);
                assertNotNull(rack);
                Area area = rack.getArea();
                int x = area.x - fixture.panel.getArea().x;
                int y = area.y - fixture.panel.getArea().y;
                assertEquals(10 + index % 8 * 31, x);
                assertEquals(80 + index / 8 * 12, y);
                assertEquals(28, area.width);
                assertEquals(11, area.height);
                assertTrue(x >= 6 && x + area.width <= 262);
                assertTrue(y >= 4 && y + area.height <= 148);
            }
            machine.setCurrentCapacityPP(128);
            state.updateCacheFromSource(false);
            assertEquals(128, ((NBTTagCompound) state.getValue()).getInteger("free"));
            fixture.tile.setActive(true);
            machine.lEUt = -18432;
            machine.mEfficiency = 5000;
            state.updateCacheFromSource(false);
            assertEquals(36864, ((NBTTagCompound) state.getValue()).getLong("consumption"));
            machine.mEfficiency = 10000;
            state.updateCacheFromSource(false);
            assertEquals(18432, ((NBTTagCompound) state.getValue()).getLong("consumption"));
            fixture.tile.setActive(false);
            state.updateCacheFromSource(false);
            assertEquals(0, ((NBTTagCompound) state.getValue()).getLong("consumption"));
            machine.clearHatches();
            state.updateCacheFromSource(false);
            assertEquals(0, ((NBTTagCompound) state.getValue()).getIntArray("rackCapacity").length);
        }

        private static void reactor() throws Exception {
            GTMTE_NuclearReactorI machine = new GTMTE_NuclearReactorI("reactor");
            Fixture fixture = new Fixture(machine);
            GTMTE_Reactor_Rod_Hatch rod = new GTMTE_Reactor_Rod_Hatch("rod", "", new ITexture[0][][]);
            machine.mRodHatches.add(rod);
            fixture.tile.disableWorking();
            fixture.press("processingControl_" + POWER_SLOT, false);
            assertTrue(fixture.tile.isAllowedToWork());
            assertTrue(machine.mFirstStart);
            DoubleSyncValue holding = fixture.handlers.findSyncHandler("hold_temperature", DoubleSyncValue.class);
            DoubleSyncValue fastDecay = fixture.handlers.findSyncHandler("fast_decay", DoubleSyncValue.class);
            assertNotNull(holding);
            assertNotNull(fastDecay);
            assertTrue(holding.isAllowC2S());
            assertTrue(fastDecay.isAllowC2S());
            assertEquals(0, copySliderToClient(holding));
            fixture.slide("reactor_hold_temperature", 1);
            assertTrue(machine.stopTemp);
            assertEquals(1, copySliderToClient(holding));
            fixture.slide("reactor_hold_temperature", 0);
            assertFalse(machine.stopTemp);
            assertEquals(0, copySliderToClient(holding));
            fixture.slide("reactor_hold_temperature", 1);
            machine.setFastDecayMode(true);
            assertEquals(1, copySliderToClient(fastDecay));
            assertEquals(0, copySliderToClient(holding));
            fixture.slide("reactor_hold_temperature", 0);
            assertTrue(machine.stopTemp);
            Materials.Water.mFluid = FluidRegistry.WATER;
            FluidRegistry.registerFluid(new Fluid("ic2coolant"));
            GT_MetaTileEntity_Hatch_Input input = new GT_MetaTileEntity_Hatch_Input("coolant", 8, "", new ITexture[0][][]);
            new ImpactConstructionChecks.TestTile(fixture.world, input, ForgeDirection.NORTH);
            machine.mInputHatches.add(input);
            input.mFluid = new FluidStack(FluidRegistry.WATER, 10);
            machine.mCurrentTemp = 11;
            fixture.slide("reactor_fast_decay", 0);
            assertTrue(machine.isFastDecay);
            assertEquals(10, input.mFluid.amount);
            machine.mCurrentTemp = 10;
            fixture.slide("reactor_fast_decay", 0);
            assertFalse(machine.isFastDecay);
            assertEquals(9, input.mFluid.amount);
            fixture.slide("reactor_fast_decay", 1);
            assertFalse(machine.isFastDecay);
            assertEquals(9, input.mFluid.amount);
            input.mFluid = FluidRegistry.getFluidStack("ic2coolant", 10);
            fixture.slide("reactor_fast_decay", 1);
            assertTrue(machine.isFastDecay);
            assertEquals(9, input.mFluid.amount);
            fixture.slide("reactor_fast_decay", 1);
            assertEquals(9, input.mFluid.amount);
            for (double invalid : new double[]{-1, 2, 0.5, Double.NaN}) {
                fixture.slide("reactor_hold_temperature", invalid);
                fixture.slide("reactor_fast_decay", invalid);
                assertTrue(machine.stopTemp);
                assertTrue(machine.isFastDecay);
            }
            NBTTagCompound saved = new NBTTagCompound();
            machine.saveNBTData(saved);
            GTMTE_NuclearReactorI restored = new GTMTE_NuclearReactorI("restored");
            new Fixture(restored);
            restored.loadNBTData(saved);
            assertTrue(restored.stopTemp);
            assertTrue(restored.mFirstStart);
            GenericSyncValue<?, ?> state = fixture.handlers.findSyncHandler("processingState", GenericSyncValue.class);
            state.updateCacheFromSource(false);
            assertEquals(7, ((NBTTagCompound) state.getValue()).getTagList("machineInfo", 8).tagCount());
            fixture.press("processingControl_" + POWER_SLOT, false);
            assertFalse(machine.mFirstStart);
        }

        private static void rods() throws Exception {
            GTMTE_NuclearReactorI machine = new GTMTE_NuclearReactorI("reactor");
            Fixture fixture = new Fixture(machine);
            GTMTE_Reactor_Rod_Hatch rod = new GTMTE_Reactor_Rod_Hatch("rod", "", new ITexture[0][][]);
            machine.mRodHatches.add(rod);
            GTMTE_Reactor_Rod_Hatch second = new GTMTE_Reactor_Rod_Hatch("second", "", new ITexture[0][][]);
            machine.mRodHatches.add(second);
            rod.setRodPosition(2);
            second.setRodPosition(8);
            assertNull(fixture.find("impact_rods_up"));
            assertNull(fixture.find("impact_rods_down"));
            fixture.slide(5);
            assertEquals(5, rod.mDownRod);
            assertEquals(5, second.mDownRod);
            fixture.slide(10);
            assertEquals(10, rod.mDownRod);
            assertEquals(10, second.mDownRod);
            fixture.slide(0);
            assertEquals(0, rod.mDownRod);
            assertEquals(0, second.mDownRod);
            for (double invalid : new double[]{-1, 11, 0.5, Double.NaN, Double.POSITIVE_INFINITY}) {
                fixture.slide(invalid);
                assertEquals(0, rod.mDownRod);
                assertEquals(0, second.mDownRod);
            }
            fixture.player.openContainer = null;
            fixture.slide(6);
            assertEquals(0, rod.mDownRod);
            fixture.player.openContainer = fixture.container;
            fixture.player.setPosition(100, 64, 100);
            fixture.slide(6);
            assertEquals(0, rod.mDownRod);
            fixture.player.setPosition(0.5, 64.5, 0.5);
            fixture.slide(7);
            assertEquals(7, rod.mDownRod);
            assertEquals(7, second.mDownRod);
            GenericSyncValue<?, ?> reactorState = fixture.handlers.findSyncHandler("reactorState", GenericSyncValue.class);
            assertFalse(reactorState.isAllowC2S());
            reactorState.updateCacheFromSource(false);
            assertArrayEquals(new int[]{7, 7}, ((NBTTagCompound) reactorState.getValue()).getIntArray("rods"));
            NBTTagCompound savedRod = new NBTTagCompound();
            rod.saveNBTData(savedRod);
            GTMTE_Reactor_Rod_Hatch restoredRod = new GTMTE_Reactor_Rod_Hatch("restoredRod", "", new ITexture[0][][]);
            Fixture hatch = new Fixture(restoredRod);
            restoredRod.loadNBTData(savedRod);
            assertEquals(7, restoredRod.mDownRod);
            hatch.checkLayout();
            assertEquals(37, hatch.container.inventorySlots.size());
            assertEquals(1, hatch.machineSlot(0).getSlotStackLimit());
            ItemStack fuel = new ItemStack(Items.stick);
            assertTrue(hatch.machineSlot(0).isItemValid(fuel));
            hatch.machineSlot(0).putStack(fuel);
            assertEquals(fuel, restoredRod.mInventory[0]);
            assertTrue(hatch.machineSlot(0).canTakeStack(hatch.player));
            hatch.slide(3);
            assertEquals(3, restoredRod.mDownRod);
            assertEquals(7, rod.mDownRod);
            for (double invalid : new double[]{-1, 11, 0.5, Double.NaN, Double.NEGATIVE_INFINITY}) {
                hatch.slide(invalid);
                assertEquals(3, restoredRod.mDownRod);
            }
            hatch.player.openContainer = null;
            hatch.slide(9);
            assertEquals(3, restoredRod.mDownRod);
            GTMTE_NuclearReactorBase<?>[] reactors = {
                    new GTMTE_NuclearReactorI("tier1"),
                    new GTMTE_NuclearReactorII("tier2"),
                    new GTMTE_NuclearReactorIII("tier3")
            };
            int[] rodCounts = {1, 9, 25};
            for (int tier = 0; tier < reactors.length; tier++) {
                GTMTE_NuclearReactorBase<?> reactor = reactors[tier];
                for (int index = 0; index < rodCounts[tier]; index++) {
                    GTMTE_Reactor_Rod_Hatch attached = new GTMTE_Reactor_Rod_Hatch("rod", "", new ITexture[0][][]);
                    attached.setRodPosition(index % 11);
                    reactor.mRodHatches.add(attached);
                }
                Fixture controller = new Fixture(reactor);
                controller.checkLayout();
                for (int position = 0; position <= 10; position++) {
                    controller.slide(position);
                    for (int actual : reactor.getRodPosition()) {
                        assertEquals(position, actual);
                    }
                }
                GenericSyncValue<?, ?> state = controller.handlers.findSyncHandler("reactorState", GenericSyncValue.class);
                state.updateCacheFromSource(false);
                assertEquals(rodCounts[tier], ((NBTTagCompound) state.getValue()).getIntArray("rods").length);
            }
        }

        private static void access() throws Exception {
            NamedMachine machine = new NamedMachine();
            Fixture fixture = new Fixture(machine);
            fixture.press("test_action", false);
            assertTrue(machine.actionEnabled);
            fixture.player.openContainer = fixture.player.inventoryContainer;
            fixture.press("test_action", false);
            assertTrue(machine.actionEnabled);
            fixture.player.openContainer = fixture.container;
            fixture.player.setPosition(100, 64, 100);
            fixture.press("test_action", false);
            assertTrue(machine.actionEnabled);
            fixture.player.setPosition(0.5, 64.5, 0.5);
            fixture.press("test_action", false);
            assertFalse(machine.actionEnabled);
            machine.setBaseMetaTileEntity(null);
            fixture.press("test_action", false);
            assertFalse(machine.actionEnabled);
        }

        private static void travel() throws Exception {
            GTMTE_Aerostat machine = new GTMTE_Aerostat("aerostat");
            machine.setName("origin");
            machine.mMachine = true;
            for (int index = 0; index < 3; index++) {
                GTMTE_Aerostat station = new GTMTE_Aerostat("destination");
                new AttachedMachine(station);
                station.setName("station-" + index);
                machine.currentLocationPlatforms.add(station);
            }
            AttachedMachine fixture = new AttachedMachine(machine);
            ContainerSelectAerostat container = new ContainerSelectAerostat(fixture.player.inventory, fixture.tile);
            fixture.player.openContainer = container;
            assertTrue(container.canInteractWith(fixture.player));
            assertEquals(3, container.inventorySlots.size());
            container.slotClick(0, 0, 0, fixture.player);
            assertEquals(1, machine.indexSelect);
            container.slotClick(0, 0, 1, fixture.player);
            assertEquals(2, machine.indexSelect);
            container.slotClick(1, 0, 1, fixture.player);
            assertEquals(0, machine.indexSelect);
            fixture.player.setPosition(100, 64, 100);
            container.slotClick(0, 0, 1, fixture.player);
            assertEquals(0, machine.indexSelect);
            fixture.player.setPosition(0.5, 64.5, 0.5);
            machine.currentLocationPlatforms.clear();
            container.slotClick(0, 0, 0, fixture.player);
            assertEquals(0, machine.indexSelect);
            Fixture elevator = new Fixture(new GTMTE_SpaceElevator("elevator"));
            assertNotNull(elevator.find("impact_travel"));
        }
    }

    private static class AttachedMachine {

        protected final MetaTileEntity machine;
        protected final ImpactConstructionChecks.TestWorld world = new ImpactConstructionChecks.TestWorld();
        protected final ImpactConstructionChecks.TestTile tile;
        protected final ImpactConstructionChecks.TestPlayer player;

        private AttachedMachine(MetaTileEntity machine) throws Exception {
            this.machine = machine;
            tile = new ImpactConstructionChecks.TestTile(world, machine, ForgeDirection.NORTH);
            world.putTile(0, 64, 0, tile);
            Field timer = gregtech.api.metatileentity.BaseMetaTileEntity.class.getDeclaredField("mTickTimer");
            timer.setAccessible(true);
            timer.setLong(tile, 51);
            player = new ImpactConstructionChecks.TestPlayer(world);
            player.setPosition(0.5, 64.5, 0.5);
        }
    }

    private static final class Fixture extends AttachedMachine {

        private final PanelSyncManager handlers;
        private final ModularPanel panel;
        private final ModularContainer container;
        private final List<IWidget> widgets = new ArrayList<>();

        private Fixture(MetaTileEntity machine) throws Exception {
            super(machine);
            ModularSyncManager manager = new ModularSyncManager(false);
            handlers = new PanelSyncManager(manager, true);
            UISettings settings = new UISettings(RecipeViewerSettings.DUMMY);
            PosGuiData data = new PosGuiData(player, 0, 64, 0);
            assertTrue(machine.useMui2());
            panel = MetaTileEntityGuiHandler.INSTANCE.createPanel(data, handlers, settings);
            WidgetTree.collectSyncValues(handlers, panel);
            container = new ModularContainer();
            container.construct(player, manager, settings, panel.getName(), data);
            player.openContainer = container;
            assertTrue(container.canInteractWith(player));
            collect(panel);
        }

        private void checkLayout() {
            initializeResizers(panel, new Viewport());
            WidgetTree.resizeInternal(panel.resizer(), false);
            List<IWidget> controls = new ArrayList<>();
            for (IWidget widget : widgets) {
                String name = widget.getName();
                boolean tankGauge = name != null && name.startsWith("tank_fluid_");
                if (!(widget instanceof ItemSlot) && !(widget instanceof ButtonWidget)
                        && !(widget instanceof SliderWidget) && !tankGauge) {
                    continue;
                }
                if (name != null && name.startsWith("processing_control_")) {
                    int index = Integer.parseInt(widget.getName().substring("processing_control_".length()));
                    if (!ProcessingGuiControls.isControlVisible(
                            (GT_MetaTileEntity_ProcessingMultiBlockBase<?>) machine, index)) {
                        continue;
                    }
                }
                Area area = widget.getArea();
                String bounds = machine.mName + ": " + widget.getClass().getSimpleName() + " " + name
                        + " " + area + " / " + panel.getArea();
                if (widget instanceof CoverTabButton) {
                    assertEquals(20, area.width, bounds);
                    assertEquals(20, area.height, bounds);
                    assertEquals(panel.getArea().x - 17, area.x, bounds);
                    assertTrue(area.y >= panel.getArea().y, bounds);
                    assertTrue(area.y + area.height <= panel.getArea().y + panel.getArea().height, bounds);
                    continue;
                }
                assertTrue(area.x >= panel.getArea().x && area.y >= panel.getArea().y, bounds);
                assertTrue(area.x + area.width <= panel.getArea().x + panel.getArea().width, bounds);
                assertTrue(area.y + area.height <= panel.getArea().y + panel.getArea().height, bounds);
                for (IWidget previous : controls) {
                    Area other = previous.getArea();
                    boolean overlaps = area.x < other.x + other.width && area.x + area.width > other.x
                            && area.y < other.y + other.height && area.y + area.height > other.y;
                    assertFalse(overlaps, machine.mName + ": " + widget.getName() + " / " + previous.getName());
                }
                controls.add(widget);
            }
        }

        private void collect(IWidget widget) {
            widgets.add(widget);
            for (IWidget child : widget.getChildren()) {
                collect(child);
            }
        }

        private IWidget find(String name) {
            for (IWidget widget : widgets) {
                if (widget.isName(name)) {
                    return widget;
                }
            }
            return null;
        }

        private ModularSlot machineSlot(int index) {
            for (Object entry : container.inventorySlots) {
                ModularSlot slot = (ModularSlot) entry;
                if (slot.getItemHandler() == machine.getInventoryHandler() && slot.getSlotIndex() == index) {
                    return slot;
                }
            }
            throw new AssertionError("Missing controller inventory slot " + index);
        }

        private void slide(double position) throws Exception {
            slide("rod_position_slider", position);
        }

        private void slide(String name, double position) throws Exception {
            SliderWidget slider = (SliderWidget) find(name);
            assertNotNull(slider);
            DoubleSyncValue handler = (DoubleSyncValue) slider.getDoubleValue();
            assertTrue(handler.isAllowC2S());
            PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
            try {
                packet.writeDouble(position);
                container.getSyncManager().receiveWidgetUpdate(
                        handler.getSyncManager().getPanelName(), handler.getKey(), false, 0, packet);
            } finally {
                packet.release();
            }
        }

        private void press(String name, boolean shift) throws Exception {
            String widgetName = name.startsWith("processingControl_")
                    ? name.replace("processingControl_", "processing_control_") : "impact_" + name;
            ButtonWidget<?> button = (ButtonWidget<?>) find(widgetName);
            assertNotNull(button, widgetName);
            InteractionSyncHandler handler = button.getSyncHandler();
            assertNotNull(handler, name);
            assertTrue(handler.isAllowC2S());
            PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
            try {
                new MouseData(Side.CLIENT, 0, shift, false, false).writeToPacket(packet);
                container.getSyncManager().receiveWidgetUpdate(
                        handler.getSyncManager().getPanelName(), handler.getKey(), false, 1, packet);
            } finally {
                packet.release();
            }
        }
    }

    private static void press(InteractionSyncHandler handler, boolean shift) throws Exception {
        PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
        try {
            new MouseData(Side.CLIENT, 0, shift, false, false).writeToPacket(packet);
            handler.readOnServer(1, packet);
        } finally {
            packet.release();
        }
    }

    private static final class NamedMachine extends GTMTE_Mixer {

        private String stationName = "original";
        private int applied;
        private boolean actionEnabled;

        private NamedMachine() {
            super("named");
        }

        @Override
        public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
            ModularPanel panel = super.buildUI(data, sync, settings);
            ImpactProcessingGui gui = new ImpactProcessingGui(this, panel, sync);
            gui.addNameEditor(() -> stationName, value -> {
                stationName = value;
                applied++;
            });
            gui.addAction(
                "test_action",
                0,
                1,
                GTGuiTextures.OVERLAY_BUTTON_CHECKMARK,
                "impact.processing.apply",
                (player, shift) -> actionEnabled = !actionEnabled
            );
            return panel;
        }
    }


    private static void initializeResizers(IWidget widget, ResizeNode parent) {
        ResizeNode resizer = widget.resizer();
        resizer.initialize(parent, parent);
        for (IWidget child : widget.getChildren()) {
            initializeResizers(child, resizer);
        }
    }

    private static final class Viewport extends StaticResizer {

        private final Area area = new Area(0, 0, 268, 238);

        @Override
        public Area getArea() {
            return area;
        }

        @Override
        public String getDebugDisplayName() {
            return "IMPACT processing GUI";
        }

        @Override
        public String toString() {
            return getDebugDisplayName();
        }
    }

}
