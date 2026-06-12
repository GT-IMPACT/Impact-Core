package com.impact.network

import net.minecraft.entity.player.EntityPlayerMP
import space.impact.packet_network.streamnet.StreamIn

interface ContainerServerStreamUpdater {

    fun fromClient(player: EntityPlayerMP, stream: StreamIn)
}
