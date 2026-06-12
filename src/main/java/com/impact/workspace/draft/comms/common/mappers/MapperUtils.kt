package com.impact.workspace.draft.comms.common.mappers

import com.impact.workspace.draft.comms.common.CommsPos
import space.impact.packet_network.streamnet.Xyz

object MapperUtils {

    internal val Xyz.commsPos: CommsPos
        get() = CommsPos(x = x, y = y, z = z)
}
