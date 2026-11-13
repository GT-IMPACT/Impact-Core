package com.impact.addon.gt.api.parallel_system

public interface IParallelOut {

    public fun onConnect(pin: IParallelIn)
    public fun updateData(
        name: String,
        address: String,
    )
    public fun updateStatus(isConnected: Boolean)
    public fun onPostDisconnect()
    public fun getParallel(): Int
    public fun changeCurrentParallel(parallels: Int)
    public fun disconnect(
        receiver: IParallelIn,
        forgetPosition: Boolean,
    )
}
