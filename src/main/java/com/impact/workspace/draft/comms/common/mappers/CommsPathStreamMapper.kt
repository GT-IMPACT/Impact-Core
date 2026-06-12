package com.impact.workspace.draft.comms.common.mappers

import com.impact.network.Net.readUuid
import com.impact.network.Net.writeUuid
import com.impact.workspace.draft.comms.common.CommsConnectionPath
import com.impact.workspace.draft.comms.common.CommsPathPoint
import com.impact.workspace.draft.comms.common.CommsPathPointType
import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsWorldKey
import space.impact.packet_network.streamnet.StreamIn
import space.impact.packet_network.streamnet.StreamOut

object CommsPathStreamMapper {

    fun writePath(
        out: StreamOut,
        path: CommsConnectionPath?
    ) {
        out.bool(path != null)

        if (path == null) {
            return
        }

        writePoint(out, path.computer)
        writePoint(out, path.executor)
        writePoint(out, path.computerTower)
        writePoint(out, path.executorTower)
        writePoint(out, path.satellite)
    }

    fun readPath(input: StreamIn): CommsConnectionPath? {
        val exists = input.bool()

        if (!exists) {
            return null
        }

        return CommsConnectionPath(
            computer = readPoint(input),
            executor = readPoint(input),
            computerTower = readPoint(input),
            executorTower = readPoint(input),
            satellite = readPoint(input)
        )
    }

    fun writePoint(
        out: StreamOut,
        point: CommsPathPoint
    ) {
        out.writeUuid(point.id)

        out.int(point.type.ordinal)

        out.bool(point.world != null)
        if (point.world != null) {
            writeWorld(out, point.world)
        }

        out.bool(point.pos != null)
        if (point.pos != null) {
            writePos(out, point.pos)
        }
    }

    fun readPoint(input: StreamIn): CommsPathPoint {
        val id = input.readUuid()

        val typeOrdinal = input.int()
        val type = CommsPathPointType.entries[typeOrdinal]

        val world = if (input.bool()) {
            readWorld(input)
        } else {
            null
        }

        val pos = if (input.bool()) {
            readPos(input)
        } else {
            null
        }

        return CommsPathPoint(
            id = id,
            type = type,
            world = world,
            pos = pos
        )
    }

    fun writeWorld(
        out: StreamOut,
        world: CommsWorldKey
    ) {
        out.int(world.dimensionId)
    }

    fun readWorld(input: StreamIn): CommsWorldKey {
        return CommsWorldKey(
            dimensionId = input.int()
        )
    }

    fun writePos(
        out: StreamOut,
        pos: CommsPos
    ) {
        out.xyz(
            pos.x,
            pos.y,
            pos.z
        )
    }

    fun readPos(input: StreamIn): CommsPos {
        val xyz = input.xyz()

        return CommsPos(
            x = xyz.x,
            y = xyz.y,
            z = xyz.z
        )
    }
}
