package com.impact.workspace.draft.comms.common.mappers

import com.impact.network.Net.readUuid
import com.impact.network.Net.writeUuid
import com.impact.workspace.draft.comms.common.CommsVisibleTower
import com.impact.workspace.draft.comms.common.mappers.MapperUtils.commsPos
import space.impact.packet_network.streamnet.StreamIn
import space.impact.packet_network.streamnet.StreamOut

object CommsVisibleTowerMapper {

    fun write(tower: CommsVisibleTower, stream: StreamOut) {
        stream.writeUuid(tower.id)
        stream.xyz(tower.pos.x, tower.pos.y, tower.pos.z)
        stream.bool(tower.active)
        stream.int(tower.connectedMachines)
    }

    fun read(stream: StreamIn): CommsVisibleTower {
        return CommsVisibleTower(
            id = stream.readUuid(),
            pos = stream.xyz().commsPos,
            active = stream.bool(),
            connectedMachines = stream.int(),
        )
    }
}
