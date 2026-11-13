package com.impact.addon.gt.api.parallel_system;

import java.util.Collection;

public interface IParallelComputer {

    Collection<? extends IParallelRack> getParallelRacks();

    Collection<? extends IParallelTransmitter> getParallelTransmitters();

    int getTransmitterOrder(IParallelTransmitter transmitter);

    long getStructureRevision();

    NetworkStatus getParallelSupplyStatus();
}
