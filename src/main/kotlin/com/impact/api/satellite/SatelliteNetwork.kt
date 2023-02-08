package com.impact.api.satellite

import com.impact.api.position.IPosition

interface IConnection {
    fun getFrequency(): Int
    fun updateFrequency(frequency: Int)
    fun updateConnectionStatus(isConnected: Boolean)
    fun getConnectionStatus(): Boolean
    fun isConnected(connection: IConnection?): Boolean = connection?.getFrequency() == getFrequency()
}

interface ISatelliteNetwork : IConnection {
    fun onFirstConnect(pos: IPosition)
    fun onChangeConnection(isConnected: Boolean)
    fun onDisconnect()
}

interface ISatellite : IConnection {
    fun onFirstConnect(frequency: Int, connection: ISatelliteNetwork): Boolean
    fun notifyConnections()
    fun getPosition(): IPosition
    fun getConnections(): Set<ISatelliteNetwork>
    fun disconnect(connection: ISatelliteNetwork)
}

interface IDistributor {
    fun notifyConnections()
    fun getConnections(): Set<IReceiver>
    fun disconnect(connection: IReceiver)
}

interface IReceiver {
    fun updateConnectionStatus(isConnected: Boolean)
    fun getConnectionStatus(): Boolean
    fun isValid(): Boolean
    fun onDisconnect()
    fun createConnect(distributor: IDistributor)
}