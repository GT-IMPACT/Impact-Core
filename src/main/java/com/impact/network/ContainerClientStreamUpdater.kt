package com.impact.network

import space.impact.packet_network.streamnet.StreamIn

interface ContainerClientStreamUpdater {

    fun fromServer(stream: StreamIn)
}
