package com.impact.addon.gt.api.satellite;

import com.impact.addon.gt.api.parallel_system.IHandledNetworkNode;
import com.impact.addon.gt.api.parallel_system.INetworkNodeHandler;
import com.impact.addon.gt.api.parallel_system.NetworkNodeAccess;
import com.impact.addon.gt.api.parallel_system.NetworkTickPhase;
import com.impact.addon.gt.api.parallel_system.NetworkStatus;
import com.impact.addon.gt.api.parallel_system.NetworkNodeLifecycle;
import com.impact.addon.gt.api.position.IPosition;
import com.impact.util.PositionObject;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class SatelliteControllerHandler implements INetworkNodeHandler {

    private final IMetaTileEntity controller;
    private final BooleanSupplier formed;
    private final BooleanSupplier operational;
    private final Supplier<? extends Collection<? extends ISatellite>> transmitters;
    private SatelliteDirectory directory;
    private IPosition registeredPosition;
    private UUID registeredOwner;

    public SatelliteControllerHandler(
            IMetaTileEntity controller,
            BooleanSupplier formed,
            BooleanSupplier operational,
            Supplier<? extends Collection<? extends ISatellite>> transmitters
    ) {
        this.controller = controller;
        this.formed = formed;
        this.operational = operational;
        this.transmitters = transmitters;
    }

    @Override
    public NetworkTickPhase getTickPhase() {
        return NetworkTickPhase.SOURCES;
    }

    @Override
    public void onTick(long tick) {
        if (directory == null && formed.getAsBoolean()) {
            register();
        } else if (directory != null) {
            UUID owner = controller.getBaseMetaTileEntity().getOwnerUuid();
            if (!Objects.equals(registeredOwner, owner)) {
                registeredOwner = owner;
                directory.register(registeredPosition, this, owner);
            }
        }
        update();
    }

    @Override
    public NetworkStatus getStatus() {
        if (!NetworkNodeAccess.isLoaded(controller)) {
            return NetworkStatus.SATELLITE_UNLOADED;
        }
        if (transmitters.get().isEmpty()) {
            return NetworkStatus.SATELLITE_TRANSMITTER_MISSING;
        }
        if (operational.getAsBoolean()) {
            for (ISatellite transmitter : transmitters.get()) {
                if (transmitter.getConnectionStatus() && NetworkNodeAccess.isLoaded(transmitter)) {
                    return NetworkStatus.CONNECTED;
                }
            }
        }
        return NetworkStatus.SATELLITE_OFFLINE;
    }

    public void register() {
        if (directory != null || transmitters.get().isEmpty() || !NetworkNodeAccess.isLoaded(controller)) {
            return;
        }
        IGregTechTileEntity tile = controller.getBaseMetaTileEntity();
        registeredPosition = new PositionObject(tile);
        registeredOwner = tile.getOwnerUuid();
        directory = SatelliteDirectory.get(tile.getWorld());
        directory.register(registeredPosition, this, registeredOwner);
    }

    public void remove() {
        IGregTechTileEntity tile = controller.getBaseMetaTileEntity();
        if (tile != null && tile.isServerSide()) {
            SatelliteDirectory.get(tile.getWorld()).remove(new PositionObject(tile), this);
        }
        directory = null;
        registeredPosition = null;
    }

    public void update() {
        boolean active = operational.getAsBoolean();
        for (ISatellite transmitter : transmitters.get()) {
            NetworkNodeLifecycle.runFor(transmitter,
                    () -> transmitter.updateConnectionStatus(active && NetworkNodeAccess.isLoaded(transmitter)));
        }
    }

    public void deactivate() {
        for (ISatellite transmitter : transmitters.get()) {
            transmitter.updateConnectionStatus(false);
        }
    }

    @Override
    public void onLoad() {
        for (ISatellite transmitter : transmitters.get()) {
            if (transmitter instanceof IHandledNetworkNode && NetworkNodeAccess.isLoaded(transmitter)) {
                NetworkNodeLifecycle.load((IHandledNetworkNode) transmitter);
            }
        }
    }

    @Override
    public void onUnload() {
        if (directory != null) {
            directory.unload(registeredPosition, this);
            directory = null;
            registeredPosition = null;
        }
        for (ISatellite transmitter : transmitters.get()) {
            if (transmitter instanceof IHandledNetworkNode) {
                NetworkNodeLifecycle.unload((IHandledNetworkNode) transmitter);
            } else {
                transmitter.updateConnectionStatus(false);
            }
        }
    }
}
