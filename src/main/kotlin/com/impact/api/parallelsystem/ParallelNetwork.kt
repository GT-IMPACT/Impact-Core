package com.impact.api.parallelsystem

import com.impact.api.position.IPosition

interface IParallelOut {
    fun onConnect(pin: IParallelIn)
    fun updateData(name: String, address: String)
    fun updateStatus(isConnected: Boolean)
    fun onPostDisconnect()
    fun getParallel(): Int
    fun changeCurrentParallel(parallels: Int)
}

interface IParallelIn {
    fun onConnect(pout: IParallelOut)
    fun updateConnectionStatus(isConnected: Boolean)
    fun getConnectionStatus(): Boolean
    fun isValid(): Boolean
    fun onDisconnect()
    fun updateParallelAmount(parallels: Int)
    fun getPosition(): IPosition
    fun getParallel(): Int
    fun getMachineName(): String
    fun getMachineAddress(): String
}