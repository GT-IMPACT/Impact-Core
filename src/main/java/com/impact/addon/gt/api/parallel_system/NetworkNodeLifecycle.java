package com.impact.addon.gt.api.parallel_system;

import com.impact.addon.gt.api.satellite.SatelliteNetworkManager;
import com.impact.core.impactLog;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Serializes handler updates and contains failures to one node. */
public final class NetworkNodeLifecycle {

    private static final int RETRY_DELAY = 20;
    private static final Map<IHandledNetworkNode, Entry> nodes = new IdentityHashMap<>();
    private static long tick;

    private NetworkNodeLifecycle() {
    }

    public static void startServer() {
        clear();
        SatelliteNetworkManager.INSTANCE.reload();
        SatelliteNetworkLogic.INSTANCE.onStartServer();
    }

    public static void loadWorld(World world) {
        if (world == null || world.isRemote) {
            return;
        }
        for (Object value : new ArrayList<>(world.loadedTileEntityList)) {
            TileEntity tile = (TileEntity) value;
            Object node = tile instanceof IGregTechTileEntity
                    ? ((IGregTechTileEntity) tile).getMetaTileEntity() : tile;
            if (node instanceof IHandledNetworkNode) {
                load((IHandledNetworkNode) node);
            }
        }
    }

    public static void load(IHandledNetworkNode node) {
        if (nodes.containsKey(node) || !NetworkNodeAccess.isLoaded(node)) {
            return;
        }
        Entry entry = new Entry(node);
        nodes.put(node, entry);
        load(entry);
    }

    private static void load(Entry entry) {
        entry.failed = false;
        try {
            for (INetworkNodeHandler handler : entry.handlers) {
                handler.onLoad();
            }
        } catch (RuntimeException failure) {
            fail(entry, failure);
        }
    }

    public static void unload(IHandledNetworkNode node) {
        Entry entry = nodes.remove(node);
        if (entry != null) {
            unload(entry);
        } else {
            // Removal may precede the first tick of a newly placed tile.
            unload(new Entry(node));
        }
    }

    private static void unload(Entry entry) {
        for (INetworkNodeHandler handler : entry.handlers) {
            try {
                handler.onUnload();
            } catch (RuntimeException failure) {
                impactLog.getLogger().error("Failed to unload network handler " + handler.getClass().getName(), failure);
            }
        }
    }

    public static void unloadWorld(World world) {
        for (Entry entry : new ArrayList<>(nodes.values())) {
            if (NetworkNodeAccess.worldOf(entry.node) == world) {
                unload(entry.node);
            }
        }
    }

    public static void clear() {
        for (Entry entry : new ArrayList<>(nodes.values())) {
            unload(entry.node);
        }
        nodes.clear();
        tick = 0;
    }

    public static void onServerTick() {
        tick++;
        for (Entry entry : new ArrayList<>(nodes.values())) {
            if (!NetworkNodeAccess.isLoaded(entry.node)) {
                unload(entry.node);
            } else if (entry.failed && tick >= entry.retryAt) {
                load(entry);
            }
        }
        ArrayList<Entry> active = new ArrayList<>(nodes.values());
        for (NetworkTickPhase phase : NetworkTickPhase.values()) {
            if (phase == NetworkTickPhase.ALLOCATION) {
                SatelliteNetworkLogic.INSTANCE.onServerTick();
            }
            for (Entry entry : active) {
                if (entry.failed || nodes.get(entry.node) != entry) {
                    continue;
                }
                try {
                    for (INetworkNodeHandler handler : entry.handlers) {
                        if (handler.getTickPhase() == phase) {
                            handler.onTick(tick);
                        }
                    }
                } catch (RuntimeException failure) {
                    fail(entry, failure);
                }
            }
        }
    }

    public static void reportFailure(Object node, RuntimeException failure) {
        if (node instanceof IHandledNetworkNode) {
            IHandledNetworkNode handled = (IHandledNetworkNode) node;
            Entry entry = nodes.get(handled);
            if (entry == null) {
                entry = new Entry(handled);
                nodes.put(handled, entry);
            }
            fail(entry, failure);
        } else {
            impactLog.getLogger().error("Failed to update network node " + node.getClass().getName(), failure);
        }
    }

    public static void runFor(Object node, Runnable action) {
        if (isFaulted(node)) {
            return;
        }
        try {
            action.run();
        } catch (RuntimeException failure) {
            reportFailure(node, failure);
        }
    }

    public static boolean isFaulted(Object node) {
        Entry entry = nodes.get(node);
        return entry != null && entry.failed;
    }

    private static void fail(Entry entry, RuntimeException failure) {
        entry.failed = true;
        entry.retryAt = tick + RETRY_DELAY;
        unload(entry);
        impactLog.getLogger().error("Network node suspended for " + RETRY_DELAY + " ticks: "
                + entry.node.getClass().getName(), failure);
    }

    public static NetworkStatus getStatus(IHandledNetworkNode node) {
        Entry entry = nodes.get(node);
        return entry != null && entry.failed ? NetworkStatus.FAULT : node.getNetworkHandler().getStatus();
    }

    private static final class Entry {

        private final IHandledNetworkNode node;
        private final ArrayList<INetworkNodeHandler> handlers;
        private boolean failed;
        private long retryAt;

        private Entry(IHandledNetworkNode node) {
            this.node = node;
            handlers = new ArrayList<>(node.getNetworkHandlers());
        }
    }
}
