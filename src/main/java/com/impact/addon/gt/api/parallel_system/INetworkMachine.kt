package com.impact.addon.gt.api.parallel_system

public interface INetworkMachine {

    public var isSatelliteConnected: Boolean

    public fun hasShowConnect(): Boolean

    public fun createConnect() {
        SatelliteNetworkLogic.registerMachine(this)
    }

    public fun removeConnect() {
        SatelliteNetworkLogic.removeMachine(this)
    }
}
