package com.impact.network

import com.impact.network.NetworkPackets.LaserPushPacket
import com.impact.network.NetworkPackets.MetaBlockGlassPacket
import com.impact.network.NetworkPackets.PlacedItemsPacket
import com.impact.network.NetworkPackets.StreamPacket
import space.impact.packet_network.network.registerPacket

object RegisterPackets {
    @JvmStatic
    fun register() {
        registerPacket(PlacedItemsPacket)
        registerPacket(MetaBlockGlassPacket)
        registerPacket(StreamPacket)
        registerPacket(LaserPushPacket)
//        registerPacket(RecipeToolPacket)
    }
}
