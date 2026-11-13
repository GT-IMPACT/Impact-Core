package com.impact.addon.gt.api.satellite;

import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.util.PositionObject;
import java.util.function.Consumer;
import net.minecraft.nbt.NBTTagCompound;

public final class SatelliteReceiverHandler implements INetworkNodeHandler {

    private final ISatelliteNetwork receiver;
    private final Consumer<Boolean> statusChanged;
    private IPosition target;
    private ISatellite satellite;
    private boolean connected;
    private boolean statusInitialized;
    private boolean missingLegacyTarget;
    private boolean towerManaged;
    private NetworkStatus status = NetworkStatus.SATELLITE_UNBOUND;
    private int frequency;
    private long satelliteRevision = Long.MIN_VALUE;

    public SatelliteReceiverHandler(ISatelliteNetwork receiver, Consumer<Boolean> statusChanged) {
        this.receiver = receiver;
        this.statusChanged = statusChanged;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isTowerManaged() {
        return towerManaged;
    }

    public void applyTowerStatus(NetworkStatus uplink) {
        if (!towerManaged) {
            towerManaged = true;
            disconnect();
        }
        setConnected(uplink == NetworkStatus.CONNECTED);
        status = uplink;
    }

    public void releaseTowerStatus() {
        if (towerManaged) {
            towerManaged = false;
            update();
        }
    }

    public void setConnected(boolean connected) {
        if (connected) {
            status = NetworkStatus.CONNECTED;
        } else if (status == NetworkStatus.CONNECTED) {
            status = NetworkStatus.SATELLITE_OFFLINE;
        }
        if (!statusInitialized || this.connected != connected) {
            this.connected = connected;
            statusInitialized = true;
            statusChanged.accept(connected);
        }
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        if (this.frequency == frequency) {
            return;
        }
        disconnect();
        this.frequency = frequency;
        update();
    }

    public void setTarget(IPosition target) {
        disconnect();
        this.target = new PositionObject(target);
        missingLegacyTarget = false;
        update();
    }

    public void update() {
        if (towerManaged) {
            return;
        }
        satelliteRevision = SatelliteNetworkManager.INSTANCE.getRevision();
        ISatellite available = target == null ? null : SatelliteNetworkManager.INSTANCE.getSatelliteInWorld(target);
        if (satellite != available || satellite != null && satellite.getFrequency() != frequency) {
            disconnect();
        }
        if (satellite == null && available != null && available.getFrequency() == frequency) {
            satellite = available;
            if (!satellite.onFirstConnect(frequency, receiver)) {
                satellite = null;
            }
        }
        if (target == null) {
            status = unboundStatus();
        } else if (available == null) {
            status = NetworkStatus.SATELLITE_MISSING;
        } else if (available.getFrequency() != frequency) {
            status = NetworkStatus.FREQUENCY_MISMATCH;
        } else {
            status = available.getConnectionStatus() ? NetworkStatus.CONNECTED : NetworkStatus.SATELLITE_OFFLINE;
        }
        setConnected(satellite != null && satellite.getConnectionStatus());
    }

    public void disconnect() {
        ISatellite previous = satellite;
        satellite = null;
        if (previous != null) {
            previous.disconnect(receiver);
        }
        setConnected(false);
    }

    public void save(NBTTagCompound tag) {
        tag.setInteger("mFrequency", frequency);
        if (missingLegacyTarget) {
            tag.setBoolean("mIsReceive", true);
        } else {
            tag.removeTag("mIsReceive");
        }
        if (target != null) {
            tag.setTag("satellite", target.saveToNBT());
        } else {
            tag.removeTag("satellite");
        }
    }

    public void load(NBTTagCompound tag) {
        disconnect();
        frequency = tag.getInteger("mFrequency");
        target = tag.hasKey("satellite") ? PositionObject.loadFromNBT(tag.getCompoundTag("satellite")) : null;
        // The old flag identifies an incomplete saved binding. It cannot establish a connection.
        missingLegacyTarget = target == null && tag.getBoolean("mIsReceive");
        status = target == null ? unboundStatus() : NetworkStatus.SATELLITE_MISSING;
    }

    private NetworkStatus unboundStatus() {
        return missingLegacyTarget ? NetworkStatus.LEGACY_SATELLITE_UNBOUND : NetworkStatus.SATELLITE_UNBOUND;
    }

    @Override
    public void onTick(long tick) {
        if (!towerManaged && (tick % 20 == 0 || satelliteRevision != SatelliteNetworkManager.INSTANCE.getRevision())) {
            update();
        }
    }

    @Override
    public NetworkStatus getStatus() {
        return status;
    }

    @Override
    public void onLoad() {
        update();
    }

    @Override
    public void onUnload() {
        disconnect();
        status = NetworkStatus.UNLOADED;
    }
}
