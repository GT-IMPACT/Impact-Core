package com.impact.workspace.draft.comms.common.mappers

import com.impact.network.Net.readUuid
import com.impact.network.Net.writeUuid
import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsVisibleComputerForLink
import com.impact.workspace.draft.comms.common.mappers.MapperUtils.commsPos
import space.impact.packet_network.streamnet.StreamIn
import space.impact.packet_network.streamnet.StreamOut

object CommsVisibleComputerMapper {

    fun write(computer: CommsVisibleComputerForLink, stream: StreamOut) {
        stream.writeUuid(computer.id)
        stream.xyz(computer.pos.x, computer.pos.y, computer.pos.z)
        stream.bool(computer.active)
        stream.bool(computer.linked)
        stream.bool(computer.routeAvailable)
    }

    fun read(stream: StreamIn): CommsVisibleComputerForLink {
        return CommsVisibleComputerForLink(
            id = stream.readUuid(),
            pos = stream.xyz().commsPos,
            active = stream.bool(),
            linked = stream.bool(),
            routeAvailable = stream.bool(),
            distanceSquared = 0,
        )
    }
}
