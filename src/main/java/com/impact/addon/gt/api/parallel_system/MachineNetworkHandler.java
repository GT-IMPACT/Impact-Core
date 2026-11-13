package com.impact.addon.gt.api.parallel_system;

public final class MachineNetworkHandler implements INetworkNodeHandler {

    private final INetworkMachine machine;
    private boolean connected;

    public MachineNetworkHandler(INetworkMachine machine) {
        this.machine = machine;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public NetworkStatus getStatus() {
        return connected ? NetworkStatus.CONNECTED : SatelliteNetworkLogic.INSTANCE.getMachineStatus(machine);
    }

    @Override
    public void onLoad() {
        SatelliteNetworkLogic.INSTANCE.registerMachine(machine);
    }

    @Override
    public void onUnload() {
        SatelliteNetworkLogic.INSTANCE.removeMachine(machine);
    }
}
