package com.impact.addon.gt.api.parallel_system;

public interface INetworkNodeHandler {

    void onLoad();

    void onUnload();

    default void onTick(long tick) {
    }

    default NetworkTickPhase getTickPhase() {
        return NetworkTickPhase.CONNECTIONS;
    }

    default NetworkStatus getStatus() {
        return NetworkStatus.UNLOADED;
    }
}
