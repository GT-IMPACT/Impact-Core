package com.impact.addon.gt.api.parallel_system

public interface INetworkTower : INetworkMachine {

    public val connections: Set<INetworkMachine>

    public fun replaceConnections(connections: Set<INetworkMachine>)

    public fun onUpdateConnections()

    public fun updateStatusConnections() {
        SatelliteNetworkLogic.updateStatusConnections(this)
    }

    public fun updateLocalNetwork()

    public fun createTower() {
        SatelliteNetworkLogic.createTower(this)
    }

    public fun removeTower() {
        SatelliteNetworkLogic.removeTower(this)
    }

    public override fun createConnect() {
    }

    public override fun removeConnect() {
    }
}
