package com.impact.addon.gt.api.parallel_system

import com.impact.addon.gt.api.position.IPosition

public interface IParallelIn {

    public fun onConnect(pout: IParallelOut)
    public fun updateConnectionStatus(isConnected: Boolean)
    public fun getConnectionStatus(): Boolean
    public fun isValid(): Boolean
    public fun onDisconnect()
    public fun updateParallelAmount(parallels: Int)
    public fun updateNetworkStatus(status: NetworkStatus)
    public fun getAllocatedParallel(): Int
    public fun getPosition(): IPosition
    public fun getParallel(): Int
    public fun getMachineName(): String
    public fun getMachineAddress(): String
}
