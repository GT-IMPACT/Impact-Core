package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.addon.gt.api.position.IPosition;
import com.impact.util.PositionObject;
import cpw.mods.fml.common.Loader;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.init.Bootstrap;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URLClassLoader;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParallelHatchConnectionTest {
    private LaunchClassLoader loader;
    private Object scenarios;

    @BeforeAll
    void initialize() throws Exception {
        loader = new LaunchClassLoader(((URLClassLoader) getClass().getClassLoader()).getURLs());
        loader.addClassLoaderExclusion("org.junit.");
        Class<?> scenarioClass = loader.loadClass(getClass().getName() + "$Scenarios");
        scenarios = scenarioClass.getConstructor().newInstance();
        scenarioClass.getMethod("initialize").invoke(scenarios);
    }

    @AfterAll
    void closeLoader() throws Exception {
        if (loader != null) loader.close();
    }

    private void check(String method) throws Throwable {
        try {
            scenarios.getClass().getMethod(method).invoke(scenarios);
        } catch (InvocationTargetException failure) {
            throw failure.getTargetException();
        }
    }

    @Test
    void replacingReceiverDisconnectsFirstMachine() throws Throwable { check("replacingReceiverDisconnectsFirstMachine"); }

    @Test
    void switchingTransmitterReleasesOldTransmitter() throws Throwable { check("switchingTransmitterReleasesOldTransmitter"); }

    @Test
    void disconnectedReceiverCannotDisableNewReceiver() throws Throwable { check("disconnectedReceiverCannotDisableNewReceiver"); }

    @Test
    void transmitterInvalidationClearsBothRuntimeLinks() throws Throwable { check("transmitterInvalidationClearsBothRuntimeLinks"); }

    @Test
    void savedReceiverStatusDoesNotCreatePhantomConnection() throws Throwable { check("savedReceiverStatusDoesNotCreatePhantomConnection"); }

    @Test
    void savedTransmitterRestoresReciprocalConnection() throws Throwable { check("savedTransmitterRestoresReciprocalConnection"); }

    public static class Scenarios {
        public void initialize() {
            Loader.injectData("7", "10", "99", "99", "1.7.10", "9.05", new File("."), java.util.Collections.emptyList());
            Bootstrap.func_151354_b();
        }

        private GTMTE_ParallelHatch_Output output() {
            return new GTMTE_ParallelHatch_Output("test_output", 5, new String[0], null, 4);
        }

        private GTMTE_ParallelHatch_Input input(int x) {
            GTMTE_ParallelHatch_Input input = new GTMTE_ParallelHatch_Input("test_input", 5, new String[0], null, 4) {
                @Override
                public IPosition getPosition() { return new PositionObject(x, 64, 1); }
            };
            input.setMachineName("machine_" + x);
            input.setMachineAddress("address_" + x);
            return input;
        }

        public void replacingReceiverDisconnectsFirstMachine() {
            GTMTE_ParallelHatch_Output output = output();
            GTMTE_ParallelHatch_Input first = input(1);
            GTMTE_ParallelHatch_Input second = input(2);
            output.onConnect(first);
            output.updateStatus(true);
            output.changeCurrentParallel(4);
            assertTrue(first.getConnectionStatus());
            output.onConnect(second);
            assertFalse(first.getConnectionStatus());
            assertEquals(0, first.getAllocatedParallel());
            assertNull(first.getTransmitter());
            assertSame(second, output.getReceiver());
            assertSame(output, second.getTransmitter());
            assertTrue(second.getConnectionStatus());
            assertEquals(second.getMachineAddress(), output.getMachineAddress());
            output.updateStatus(false);
            assertFalse(first.getConnectionStatus());
            assertFalse(second.getConnectionStatus());
        }

        public void switchingTransmitterReleasesOldTransmitter() {
            GTMTE_ParallelHatch_Output first = output();
            GTMTE_ParallelHatch_Output second = output();
            GTMTE_ParallelHatch_Input input = input(1);
            first.onConnect(input);
            first.updateStatus(true);
            second.onConnect(input);
            second.updateStatus(true);
            assertNull(first.getReceiver());
            assertNull(first.getReceiverPosition());
            assertSame(second, input.getTransmitter());
            first.updateStatus(false);
            assertTrue(input.getConnectionStatus());
            input.onDisconnect();
            assertNull(second.getReceiver());
            assertNotNull(second.getReceiverPosition());
            assertFalse(input.getConnectionStatus());
        }

        public void disconnectedReceiverCannotDisableNewReceiver() {
            GTMTE_ParallelHatch_Output output = output();
            GTMTE_ParallelHatch_Input first = input(1);
            GTMTE_ParallelHatch_Input second = input(2);
            output.onConnect(first);
            output.onConnect(second);
            output.updateStatus(true);
            // Also tolerate a stale back-reference from an old connection.
            first.onConnect(output);
            first.onDisconnect();
            assertSame(second, output.getReceiver());
            assertTrue(second.getConnectionStatus());
            assertTrue(output.getConnectionStatus());
        }

        public void transmitterInvalidationClearsBothRuntimeLinks() {
            GTMTE_ParallelHatch_Output output = output();
            GTMTE_ParallelHatch_Input input = input(1);
            output.onConnect(input);
            output.updateStatus(true);
            output.onPostDisconnect();
            assertNull(output.getReceiver());
            assertNull(input.getTransmitter());
            assertFalse(input.getConnectionStatus());
            assertFalse(output.getConnectionStatus());
            // Keep the position for restoring the link after chunk reload.
            assertNotNull(output.getReceiverPosition());
        }

        public void savedReceiverStatusDoesNotCreatePhantomConnection() {
            GTMTE_ParallelHatch_Input input = input(1);
            NBTTagCompound saved = new NBTTagCompound();
            saved.setBoolean("isConnected", true);
            saved.setInteger("mCurrentParallelIn", 4);
            input.loadNBTData(saved);
            assertFalse(input.getConnectionStatus());
            assertEquals(0, input.getAllocatedParallel());
            assertNull(input.getTransmitter());
        }

        public void savedTransmitterRestoresReciprocalConnection() {
            GTMTE_ParallelHatch_Output output = output();
            GTMTE_ParallelHatch_Input input = input(1);
            NBTTagCompound saved = new NBTTagCompound();
            saved.setTag("parallelIn", input.getPosition().saveToNBT());
            output.loadNBTData(saved);
            IGregTechTileEntity target = (IGregTechTileEntity) Proxy.newProxyInstance(
                    IGregTechTileEntity.class.getClassLoader(), new Class<?>[]{IGregTechTileEntity.class},
                    (proxy, method, args) -> {
                        if (method.getName().equals("getMetaTileEntity")) return input;
                        throw new AssertionError("Unexpected target call: " + method.getName());
                    });
            net.minecraft.world.World world = new gregtech.common.GT_DummyWorld() {
                @Override
                public boolean blockExists(int x, int y, int z) {
                    return true;
                }
            };
            IGregTechTileEntity base = (IGregTechTileEntity) Proxy.newProxyInstance(
                    IGregTechTileEntity.class.getClassLoader(), new Class<?>[]{IGregTechTileEntity.class},
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "isServerSide": return true;
                            case "getWorld":
                                return world;
                            case "getIGregTechTileEntity": return target;
                            case "setMetaTileEntity":
                            case "markDirty":
                            case "setActive": return null;
                            default: throw new AssertionError("Unexpected base call: " + method.getName());
                        }
                    });
            output.setBaseMetaTileEntity(base);
            output.getNetworkHandler().onLoad();
            assertSame(input, output.getReceiver());
            assertSame(output, input.getTransmitter());
            output.updateStatus(true);
            assertTrue(input.getConnectionStatus());
            output.loadNBTData(new NBTTagCompound());
            assertNull(output.getReceiverPosition());
            assertNull(input.getTransmitter());
            assertFalse(input.getConnectionStatus());
        }
    }
}
