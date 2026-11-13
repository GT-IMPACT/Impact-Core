package com.impact.addon.gt.api.parallel_system;

public interface IParallelTransmitter {

    int getParallel();

    boolean hasReceiver();

    NetworkStatus getReceiverStatus();

    boolean isAvailable();

    boolean isEnabled();

    long getAllocationRevision();

    void allocate(int points, NetworkStatus status);

    NetworkStatus getNetworkStatus();
}
