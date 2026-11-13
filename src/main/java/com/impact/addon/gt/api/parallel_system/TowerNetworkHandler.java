package com.impact.addon.gt.api.parallel_system;

import com.impact.addon.gt.api.satellite.ISatelliteNetwork;
import com.impact.addon.gt.api.satellite.SatelliteDirectory;
import com.impact.addon.gt.api.satellite.SatelliteReceiverHandler;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public final class TowerNetworkHandler implements INetworkNodeHandler {

    private final INetworkTower tower;
    private final BooleanSupplier operational;
    private final Supplier<? extends Collection<? extends ISatelliteNetwork>> receivers;
    private Set<INetworkMachine> connections = Collections.emptySet();
    private boolean connected;
    private NetworkStatus status = NetworkStatus.UNLOADED;
    private String satelliteId = "";

    public TowerNetworkHandler(
            INetworkTower tower,
            BooleanSupplier operational,
            Supplier<? extends Collection<? extends ISatelliteNetwork>> receivers
    ) {
        this.tower = tower;
        this.operational = operational;
        this.receivers = receivers;
    }

    public Set<INetworkMachine> getConnections() {
        return connections;
    }

    public void replaceConnections(Set<? extends INetworkMachine> machines) {
        connections = Collections.unmodifiableSet(new HashSet<>(machines));
    }

    @Override
    public NetworkStatus getStatus() {
        return status;
    }

    @Override
    public NetworkTickPhase getTickPhase() {
        return NetworkTickPhase.COVERAGE;
    }

    @Override
    public void onTick(long tick) {
        update();
    }

    public boolean isConnected() {
        return connected;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public boolean selectSatellite(String id, EntityPlayer player) {
        World world = NetworkNodeAccess.worldOf(tower);
        if (world == null || world.isRemote || player == null || player.worldObj != world
                || !NetworkNodeAccess.isLoaded(tower)
                || !SatelliteDirectory.get(world).isOwnedBy(id, player.getUniqueID())) {
            return false;
        }
        if (satelliteId.equals(id)) {
            return true;
        }
        satelliteId = id;
        if (tower instanceof IMetaTileEntity) {
            NetworkNodeAccess.markConfigurationDirty((IMetaTileEntity) tower);
        }
        update();
        return true;
    }

    public void save(NBTTagCompound tag) {
        tag.setString("impactSatelliteId", satelliteId);
    }

    public void load(NBTTagCompound tag) {
        satelliteId = tag.getString("impactSatelliteId");
        setConnected(false);
    }

    public void setConnected(boolean connected) {
        if (connected) {
            status = NetworkStatus.CONNECTED;
        } else if (status == NetworkStatus.CONNECTED) {
            status = NetworkStatus.TOWER_OFFLINE;
        }
        if (this.connected != connected) {
            this.connected = connected;
            SatelliteNetworkLogic.INSTANCE.updateStatusConnections(tower);
        }
    }

    public void update() {
        if (!NetworkNodeAccess.isLoaded(tower)) {
            status = NetworkStatus.UNLOADED;
            setConnected(false);
            return;
        }
        Collection<? extends ISatelliteNetwork> inputs = receivers.get();
        if (!satelliteId.isEmpty()) {
            NetworkStatus uplink = SatelliteDirectory.get(NetworkNodeAccess.worldOf(tower)).getStatus(satelliteId);
            boolean allReceiversLoaded = applyUplink(inputs, uplink);
            if (inputs.size() != 4) {
                status = NetworkStatus.RECEIVER_MISSING;
            } else if (!allReceiversLoaded) {
                status = NetworkStatus.UNLOADED;
            } else if (uplink != NetworkStatus.CONNECTED) {
                status = uplink;
            } else {
                status = operational.getAsBoolean() ? NetworkStatus.CONNECTED : NetworkStatus.MACHINE_OFFLINE;
            }
            setConnected(status == NetworkStatus.CONNECTED);
            return;
        }
        status = operational.getAsBoolean() ? NetworkStatus.CONNECTED : NetworkStatus.MACHINE_OFFLINE;
        if (inputs.size() != 4) {
            status = NetworkStatus.RECEIVER_MISSING;
        }
        NetworkStatus receiverFailure = null;
        for (ISatelliteNetwork receiver : inputs) {
            NetworkStatus reason;
            if (!NetworkNodeAccess.isLoaded(receiver)) {
                reason = NetworkStatus.UNLOADED;
            } else if (!receiver.getConnectionStatus()) {
                reason = receiver instanceof IHandledNetworkNode
                        ? NetworkNodeLifecycle.getStatus((IHandledNetworkNode) receiver) : NetworkStatus.SATELLITE_OFFLINE;
            } else {
                continue;
            }
            if (reason == NetworkStatus.LEGACY_SATELLITE_UNBOUND) {
                receiverFailure = reason;
                break;
            }
            if (receiverFailure == null) {
                receiverFailure = reason;
            }
        }
        if (receiverFailure != null) {
            status = receiverFailure;
        }
        setConnected(status == NetworkStatus.CONNECTED);
    }

    private boolean applyUplink(Collection<? extends ISatelliteNetwork> inputs, NetworkStatus uplink) {
        boolean allReceiversLoaded = true;
        for (ISatelliteNetwork receiver : inputs) {
            if (!NetworkNodeAccess.isLoaded(receiver) || NetworkNodeLifecycle.isFaulted(receiver)) {
                allReceiversLoaded = false;
                continue;
            }
            INetworkNodeHandler handler = receiver instanceof IHandledNetworkNode
                    ? ((IHandledNetworkNode) receiver).getNetworkHandler() : null;
            if (!(handler instanceof SatelliteReceiverHandler)) {
                allReceiversLoaded = false;
                continue;
            }
            try {
                ((SatelliteReceiverHandler) handler).applyTowerStatus(uplink);
            } catch (RuntimeException failure) {
                NetworkNodeLifecycle.reportFailure(receiver, failure);
                allReceiversLoaded = false;
            }
        }
        return allReceiversLoaded;
    }

    public void refreshCoverage() {
        SatelliteNetworkLogic.INSTANCE.refreshTower(tower);
    }

    @Override
    public void onLoad() {
        SatelliteNetworkLogic.INSTANCE.createTower(tower);
    }

    @Override
    public void onUnload() {
        SatelliteNetworkLogic.INSTANCE.removeTower(tower);
        if (!satelliteId.isEmpty()) {
            applyUplink(receivers.get(), NetworkStatus.UNLOADED);
        }
        status = NetworkStatus.UNLOADED;
    }
}
