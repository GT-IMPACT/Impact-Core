package com.impact.addon.gt.api.satellite;

import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Owns live subscribers; frequency is configuration, connectivity is runtime state. */
public final class SatelliteNetworkHandler implements INetworkNodeHandler {

    private final ISatellite satellite;
    private final Set<ISatelliteNetwork> receivers = new HashSet<>();
    private boolean connected;
    private int frequency;

    public SatelliteNetworkHandler(ISatellite satellite) {
        this.satellite = satellite;
    }

    @Override
    public NetworkStatus getStatus() {
        return connected ? NetworkStatus.CONNECTED : NetworkStatus.SATELLITE_OFFLINE;
    }

    public boolean isConnected() {
        return connected;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        if (this.frequency != frequency) {
            SatelliteNetworkManager.INSTANCE.markChanged();
        }
        this.frequency = frequency;
        for (ISatelliteNetwork receiver : new ArrayList<>(receivers)) {
            if (receiver.getFrequency() != frequency) {
                receiver.onDisconnect();
                receivers.remove(receiver);
            }
        }
    }

    public boolean connect(int frequency, ISatelliteNetwork receiver) {
        if (frequency != this.frequency) {
            return false;
        }
        receivers.add(receiver);
        receiver.onChangeConnection(connected);
        return true;
    }

    public void disconnect(ISatelliteNetwork receiver) {
        receivers.remove(receiver);
    }

    public Set<ISatelliteNetwork> getConnections() {
        return Collections.unmodifiableSet(receivers);
    }

    public void setConnected(boolean connected) {
        if (this.connected != connected) {
            this.connected = connected;
            notifyConnections();
        }
    }

    public void notifyConnections() {
        for (ISatelliteNetwork receiver : new ArrayList<>(receivers)) {
            NetworkNodeLifecycle.runFor(receiver, () -> receiver.onChangeConnection(connected));
        }
    }

    @Override
    public void onLoad() {
        SatelliteNetworkManager.INSTANCE.addSatelliteToWorld(satellite);
    }

    @Override
    public void onUnload() {
        SatelliteNetworkManager.INSTANCE.removeSatelliteFromWorld(satellite);
        satellite.updateConnectionStatus(false);
        for (ISatelliteNetwork receiver : new ArrayList<>(receivers)) {
            receiver.onDisconnect();
        }
        receivers.clear();
    }
}
