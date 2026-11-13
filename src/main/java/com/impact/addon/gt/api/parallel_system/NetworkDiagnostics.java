package com.impact.addon.gt.api.parallel_system;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public final class NetworkDiagnostics {

    public static final String STATUS_TAG = "impactNetworkStatus";
    public static final String BIND_SATELLITE_KEY = "impact.network.bind_satellite";

    private NetworkDiagnostics() {
    }

    public static NetworkStatus status(IHandledNetworkNode node) {
        NetworkStatus status = NetworkNodeLifecycle.getStatus(node);
        if (status != NetworkStatus.FAULT && node instanceof IParallelComputer) {
            status = ((IParallelComputer) node).getParallelSupplyStatus();
        }
        return status;
    }

    public static void write(NBTTagCompound tag, IHandledNetworkNode node) {
        tag.setString(STATUS_TAG, status(node).getTranslationKey());
    }

    public static void append(NBTTagCompound tag, List<String> tooltip) {
        String key = tag.getString(STATUS_TAG);
        if (hasFailure(tag)) {
            tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal(key));
            if (needsSatelliteBinding(key)) {
                tooltip.add(StatCollector.translateToLocal(BIND_SATELLITE_KEY));
            }
        }
    }

    public static boolean hasFailure(NBTTagCompound tag) {
        String key = tag.getString(STATUS_TAG);
        return !key.isEmpty() && !key.equals(NetworkStatus.CONNECTED.getTranslationKey());
    }

    public static boolean needsSatelliteBinding(String key) {
        return key.equals(NetworkStatus.SATELLITE_UNBOUND.getTranslationKey())
                || key.equals(NetworkStatus.LEGACY_SATELLITE_UNBOUND.getTranslationKey());
    }

    public static void appendConnection(NBTTagCompound tag, List<String> tooltip, boolean connected) {
        if (hasFailure(tag)) {
            append(tag, tooltip);
        } else if (connected) {
            tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("impact.waila.connect.yes"));
        } else {
            tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("impact.waila.connect.no"));
        }
    }
}
