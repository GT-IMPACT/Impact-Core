package com.impact.mods.gregtech;

import com.cleanroommc.modularui.api.RecipeViewerSettings;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularContainer;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.MouseData;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.widgets.SliderWidget;
import com.cleanroommc.modularui.value.sync.InteractionSyncHandler;
import com.cleanroommc.modularui.value.sync.ModularSyncManager;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.value.sync.PanelSyncHandler;
import com.cleanroommc.modularui.value.sync.StringSyncValue;
import com.cleanroommc.modularui.value.sync.ValueSyncHandler;
import com.impact.mods.gregtech.gui.processing.ImpactProcessingGui;
import com.impact.mods.gregtech.gui.aerostat.ContainerSelectAerostat;
import com.cleanroommc.modularui.widget.WidgetTree;
import com.cleanroommc.modularui.widget.sizer.Area;
import com.cleanroommc.modularui.widget.sizer.ResizeNode;
import com.cleanroommc.modularui.widget.sizer.StaticResizer;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import gregtech.common.gui.modularui.multiblock.ProcessingGuiControls;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorI;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorIII;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.ores.GTMTE_Mining_Coal;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_CokeOven;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_TheMill;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_MultiTank;
import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_SingleTank;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_BasicWaterPump;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Filler;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_SpaceElevator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.FMLInjectionData;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import cpw.mods.fml.server.FMLServerHandler;
import gregtech.api.interfaces.ITexture;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ProcessingMultiBlockBase;
import gregtech.api.modularui2.GTGuis;
import gregtech.api.modularui2.MetaTileEntityGuiHandler;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.ForgeDirection;
import org.junit.jupiter.api.Test;

import static gregtech.api.gui.GT_Container_ProcessingMachine.POWER_SLOT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImpactProcessingGuiTest {

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
            assertEquals(68, count);
        }

        private static void inventory() throws Exception {
            MetaTileEntity[] machines = {
                    new GTMTE_TheMill("mill"), new GTMTE_Mining_Coal("coal"), new GTMTE_Filler("filler")
            };
            int[][] indices = { { 0, 1, 2, 3, 4 }, { 0, 1 }, { 0 } };
            for (int index = 0; index < machines.length; index++) {
                MetaTileEntity machine = machines[index];
                Fixture nativeGui = index == 2 ? new Fixture(machine) : null;
                AttachedMachine fixture = nativeGui != null ? nativeGui : new AttachedMachine(machine);
                Container container = nativeGui != null
                        ? nativeGui.container
                        : (Container) machine.getServerGUI(0, fixture.player.inventory, fixture.tile);
                assertNotNull(container);
                fixture.player.openContainer = container;
                assertTrue(container.canInteractWith(fixture.player));
                assertEquals(36 + indices[index].length, container.inventorySlots.size());
                for (int inventoryIndex : indices[index]) {
                    Slot slot = nativeGui != null
                            ? nativeGui.machineSlot(inventoryIndex)
                            : (Slot) container.inventorySlots.get(inventoryIndex);
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
            GTMTE_Filler machine = new GTMTE_Filler("filler");
            Fixture fixture = new Fixture(machine);
            fixture.press("preserve_tiles", false);
            assertTrue(machine.saveTiles);
            fixture.player.openContainer = fixture.player.inventoryContainer;
            fixture.press("preserve_tiles", false);
            assertTrue(machine.saveTiles);
            fixture.player.openContainer = fixture.container;
            fixture.player.setPosition(100, 64, 100);
            fixture.press("preserve_tiles", false);
            assertTrue(machine.saveTiles);
            fixture.player.setPosition(0.5, 64.5, 0.5);
            fixture.press("preserve_tiles", false);
            assertFalse(machine.saveTiles);
            machine.setBaseMetaTileEntity(null);
            fixture.press("preserve_tiles", false);
            assertFalse(machine.saveTiles);
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
                if (widget.getName().startsWith("processing_control_")) {
                    int index = Integer.parseInt(widget.getName().substring("processing_control_".length()));
                    if (!ProcessingGuiControls.isControlVisible(
                            (GT_MetaTileEntity_ProcessingMultiBlockBase<?>) machine, index)) {
                        continue;
                    }
                }
                Area area = widget.getArea();
                assertTrue(area.x >= panel.getArea().x && area.y >= panel.getArea().y, machine.mName);
                assertTrue(area.x + area.width <= panel.getArea().x + panel.getArea().width, machine.mName);
                assertTrue(area.y + area.height <= panel.getArea().y + panel.getArea().height, machine.mName);
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

    private static final class NamedMachine extends GTMTE_Filler {

        private String stationName = "original";
        private int applied;

        private NamedMachine() {
            super("named");
        }

        @Override
        public ModularPanel buildUI(PosGuiData data, PanelSyncManager sync, UISettings settings) {
            ModularPanel panel = super.buildUI(data, sync, settings);
            new ImpactProcessingGui(this, panel, sync).addNameEditor(() -> stationName, value -> {
                stationName = value;
                applied++;
            });
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
