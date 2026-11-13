package com.impact.addon.gt.api.parallel_system;

public interface IParallelRack {

    int getCapacityPoints();

    boolean isAvailable();

    void setNetworkActive(boolean active);
}
