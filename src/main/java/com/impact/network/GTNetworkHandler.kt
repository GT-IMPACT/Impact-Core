package com.impact.network

import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import space.impact.packet_network.network.NetworkHandler.sendToAllAround
import space.impact.packet_network.network.packets.ImpactPacket

object GTNetworkHandler {
    @JvmStatic
    fun IGregTechTileEntity.sendToAllAround(packet: ImpactPacket, range: Int) {
        val te = getTileEntity(xCoord, yCoord.toInt(), zCoord)
        te?.sendToAllAround(packet, range)
    }
}
