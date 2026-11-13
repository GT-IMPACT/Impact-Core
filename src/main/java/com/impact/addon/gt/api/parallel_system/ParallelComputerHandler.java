package com.impact.addon.gt.api.parallel_system;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ParallelComputerHandler implements INetworkNodeHandler {

    private final IParallelComputer computer;
    private final List<IParallelTransmitter> orderedTransmitters = new ArrayList<>();
    private final List<IParallelRack> racks = new ArrayList<>();
    private final List<Integer> demands = new ArrayList<>();
    private final List<Long> revisions = new ArrayList<>();
    private final List<Boolean> eligible = new ArrayList<>();
    private int capacity;
    private int free;
    private long structureRevision = Long.MIN_VALUE;
    private boolean allocationDirty = true;
    private NetworkStatus status = NetworkStatus.UNLOADED;

    public ParallelComputerHandler(IParallelComputer computer) {
        this.computer = computer;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFreeCapacity() {
        return free;
    }

    public void setCapacity(int capacity) {
        this.capacity = Math.max(0, capacity);
        free = this.capacity;
        allocationDirty = true;
    }

    public void setFreeCapacity(int free) {
        this.free = Math.max(0, Math.min(capacity, free));
    }

    public void invalidateStructure() {
        deactivate();
        racks.clear();
        racks.addAll(computer.getParallelRacks());
        orderedTransmitters.clear();
        orderedTransmitters.addAll(computer.getParallelTransmitters());
        orderedTransmitters.sort(Comparator.comparingInt(computer::getTransmitterOrder));
        demands.clear();
        revisions.clear();
        eligible.clear();
        for (int index = 0; index < orderedTransmitters.size(); index++) {
            demands.add(-1);
            revisions.add(-1L);
            eligible.add(false);
        }
        allocationDirty = true;
        structureRevision = computer.getStructureRevision();
    }

    private void ensureStructure() {
        if (structureRevision != computer.getStructureRevision()
                || racks.size() != computer.getParallelRacks().size()
                || orderedTransmitters.size() != computer.getParallelTransmitters().size()
        ) {
            invalidateStructure();
        }
    }

    public void updateCapacity() {
        ensureStructure();
        int installed = 0;
        for (IParallelRack rack : racks) {
            if (rack.isAvailable()) {
                installed += Math.max(0, rack.getCapacityPoints());
            }
        }
        if (capacity != installed) {
            setCapacity(installed);
        }
    }

    public void update() {
        updateCapacity();
        NetworkStatus supply = computer.getParallelSupplyStatus();
        boolean changed = allocationDirty || status != supply;
        for (int index = 0; index < orderedTransmitters.size(); index++) {
            IParallelTransmitter transmitter = orderedTransmitters.get(index);
            int demand = transmitter.getParallel();
            boolean available = transmitter.isAvailable() && transmitter.hasReceiver() && transmitter.isEnabled();
            if (demands.get(index) != demand || eligible.get(index) != available
                    || revisions.get(index) != transmitter.getAllocationRevision()) {
                demands.set(index, demand);
                revisions.set(index, transmitter.getAllocationRevision());
                eligible.set(index, available);
                changed = true;
            }
        }
        if (!changed) {
            return;
        }
        status = supply;
        allocationDirty = false;
        free = capacity;
        for (IParallelRack rack : racks) {
            rack.setNetworkActive(supply == NetworkStatus.CONNECTED && rack.isAvailable());
        }
        for (int index = 0; index < orderedTransmitters.size(); index++) {
            IParallelTransmitter transmitter = orderedTransmitters.get(index);
            NetworkStatus reason = supply;
            if (!transmitter.isAvailable()) {
                reason = NetworkStatus.UNLOADED;
            } else if (!transmitter.isEnabled()) {
                reason = NetworkStatus.DISABLED;
            } else if (!transmitter.hasReceiver()) {
                reason = transmitter.getReceiverStatus();
            } else if (supply == NetworkStatus.CONNECTED
                    && (demands.get(index) <= 0 || demands.get(index) > free)) {
                reason = NetworkStatus.INSUFFICIENT_POINTS;
            }
            int points = reason == NetworkStatus.CONNECTED ? demands.get(index) : 0;
            free -= points;
            transmitter.allocate(points, reason);
        }
    }

    public void deactivate() {
        for (IParallelTransmitter transmitter : computer.getParallelTransmitters()) {
            transmitter.allocate(0, NetworkStatus.MACHINE_OFFLINE);
        }
        for (IParallelRack rack : computer.getParallelRacks()) {
            rack.setNetworkActive(false);
        }
        free = capacity;
        allocationDirty = true;
    }

    @Override
    public NetworkStatus getStatus() {
        return status;
    }

    @Override
    public NetworkTickPhase getTickPhase() {
        return NetworkTickPhase.ALLOCATION;
    }

    @Override
    public void onTick(long tick) {
        update();
    }

    @Override
    public void onLoad() {
        allocationDirty = true;
    }

    @Override
    public void onUnload() {
        deactivate();
        status = NetworkStatus.UNLOADED;
    }
}
