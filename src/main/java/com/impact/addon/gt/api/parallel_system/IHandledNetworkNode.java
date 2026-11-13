package com.impact.addon.gt.api.parallel_system;

import java.util.Collection;
import java.util.Collections;

/** Exposes an embedded handler to the shared chunk lifecycle listener. */
public interface IHandledNetworkNode {

    INetworkNodeHandler getNetworkHandler();

    default Collection<? extends INetworkNodeHandler> getNetworkHandlers() {
        return Collections.singletonList(getNetworkHandler());
    }
}
