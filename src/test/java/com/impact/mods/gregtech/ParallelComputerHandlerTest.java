package com.impact.mods.gregtech;

import com.impact.addon.gt.api.parallel_system.IParallelComputer;
import com.impact.addon.gt.api.parallel_system.IParallelRack;
import com.impact.addon.gt.api.parallel_system.IParallelTransmitter;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.parallel_system.ParallelComputerHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParallelComputerHandlerTest {

    @Test
    void allocationWorksWithoutMinecraftControllerClasses() {
        Computer computer = new Computer();
        Rack rack = computer.rack(16);
        Transmitter first = computer.transmitter(1, 16);
        Transmitter later = computer.transmitter(2, 16);
        ParallelComputerHandler handler = new ParallelComputerHandler(computer);
        handler.update();
        assertEquals(16, handler.getCapacity());
        assertEquals(0, handler.getFreeCapacity());
        assertEquals(16, first.allocated);
        assertEquals(NetworkStatus.INSUFFICIENT_POINTS, later.status);
        computer.status = NetworkStatus.TOWER_OFFLINE;
        handler.update();
        assertEquals(0, first.allocated);
        assertEquals(16, handler.getFreeCapacity());
        assertEquals(NetworkStatus.TOWER_OFFLINE, first.status);
        assertFalse(rack.active);
    }

    @Test
    void unchangedTicksDoNotSortOrRepublishAllocations() {
        Computer computer = new Computer();
        computer.rack(64);
        Transmitter first = computer.transmitter(1, 16);
        computer.transmitter(2, 16);
        ParallelComputerHandler handler = new ParallelComputerHandler(computer);
        handler.update();
        int sorts = computer.orderReads;
        int allocations = first.updates;
        for (int tick = 0; tick < 1000; tick++) {
            handler.update();
        }
        assertEquals(sorts, computer.orderReads);
        assertEquals(allocations, first.updates);
        first.revision++;
        handler.update();
        assertEquals(allocations + 1, first.updates);
        assertEquals(sorts, computer.orderReads);
    }

    @Test
    void sameSizeStructureReplacementInvalidatesCachedOrder() {
        Computer computer = new Computer();
        computer.rack(4);
        computer.transmitter(3, 4);
        ParallelComputerHandler handler = new ParallelComputerHandler(computer);
        handler.update();
        computer.transmitters.clear();
        Transmitter replacement = computer.transmitter(1, 4);
        handler.update();
        assertEquals(4, replacement.allocated);
    }

    @Test
    void unloadedRackAndDisabledOrEmptyTransmittersReleaseBudget() {
        Computer computer = new Computer();
        Rack rack = computer.rack(4);
        Transmitter empty = computer.transmitter(0, 4);
        Transmitter disabled = computer.transmitter(1, 4);
        Transmitter served = computer.transmitter(2, 4);
        empty.linked = false;
        disabled.enabled = false;
        ParallelComputerHandler handler = new ParallelComputerHandler(computer);
        handler.update();
        assertEquals(NetworkStatus.NO_TARGET, empty.status);
        assertEquals(NetworkStatus.DISABLED, disabled.status);
        assertEquals(4, served.allocated);
        rack.available = false;
        handler.update();
        assertEquals(0, handler.getCapacity());
        assertEquals(0, served.allocated);
        assertEquals(NetworkStatus.INSUFFICIENT_POINTS, served.status);
    }

    @Test
    void oversizedDemandDoesNotMakeCapacityNegativeOrStarveSmallerDemand() {
        Computer computer = new Computer();
        computer.rack(16);
        Transmitter large = computer.transmitter(0, 64);
        Transmitter small = computer.transmitter(1, 16);
        ParallelComputerHandler handler = new ParallelComputerHandler(computer);
        handler.update();
        assertEquals(NetworkStatus.INSUFFICIENT_POINTS, large.status);
        assertEquals(16, small.allocated);
        assertEquals(0, handler.getFreeCapacity());
    }

    @Test
    void allDiagnosticReasonsHaveEnglishAndRussianTranslations() throws Exception {
        for (String language : new String[] { "en_US", "ru_RU" }) {
            Properties translations = new Properties();
            try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(
                    "/assets/impact/lang/" + language + ".lang"), StandardCharsets.UTF_8)) {
                translations.load(reader);
            }
            for (NetworkStatus status : NetworkStatus.values()) {
                assertNotNull(translations.getProperty(status.getTranslationKey()), language + ": " + status);
            }
        }
    }

    private static final class Computer implements IParallelComputer {

        private final List<Rack> racks = new ArrayList<>();
        private final List<Transmitter> transmitters = new ArrayList<>();
        private NetworkStatus status = NetworkStatus.CONNECTED;
        private long revision;
        private int orderReads;

        private Rack rack(int points) {
            Rack rack = new Rack(points);
            racks.add(rack);
            revision++;
            return rack;
        }

        private Transmitter transmitter(int order, int points) {
            Transmitter transmitter = new Transmitter(order, points);
            transmitters.add(transmitter);
            revision++;
            return transmitter;
        }

        @Override
        public Collection<Rack> getParallelRacks() {
            return racks;
        }

        @Override
        public Collection<Transmitter> getParallelTransmitters() {
            return transmitters;
        }

        @Override
        public int getTransmitterOrder(IParallelTransmitter transmitter) {
            orderReads++;
            return ((Transmitter) transmitter).order;
        }

        @Override
        public long getStructureRevision() {
            return revision;
        }

        @Override
        public NetworkStatus getParallelSupplyStatus() {
            return status;
        }
    }

    private static final class Rack implements IParallelRack {

        private final int capacity;
        private boolean available = true;
        private boolean active;

        private Rack(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public int getCapacityPoints() {
            return capacity;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        @Override
        public void setNetworkActive(boolean active) {
            this.active = active;
        }
    }

    private static final class Transmitter implements IParallelTransmitter {

        private final int order;
        private final int points;
        private boolean linked = true;
        private boolean enabled = true;
        private long revision;
        private int allocated;
        private int updates;
        private NetworkStatus status;

        private Transmitter(int order, int points) {
            this.order = order;
            this.points = points;
        }

        @Override
        public int getParallel() {
            return points;
        }

        @Override
        public boolean hasReceiver() {
            return linked;
        }

        @Override
        public NetworkStatus getReceiverStatus() {
            return linked ? NetworkStatus.CONNECTED : NetworkStatus.NO_TARGET;
        }

        @Override
        public boolean isAvailable() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public long getAllocationRevision() {
            return revision;
        }

        @Override
        public void allocate(int points, NetworkStatus status) {
            allocated = points;
            this.status = status;
            updates++;
        }

        @Override
        public NetworkStatus getNetworkStatus() {
            return status;
        }
    }
}
