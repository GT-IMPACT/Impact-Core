package com.impact.workspace.draft.comms.satellite

import com.impact.workspace.draft.comms.node.CommsNetworkStateHolder
import com.impact.workspace.draft.comms.node.CommsOwnedNode
import com.impact.workspace.draft.comms.node.types.CommsTowerNode
import java.util.UUID

interface CommsSatelliteEndpoint : CommsNetworkStateHolder, CommsOwnedNode {
    val commsId: UUID

    var commsOnline: Boolean
    var commsRemoved: Boolean

    val commsActive: Boolean

    fun isCommsAlive(): Boolean {
        return commsOnline && !commsRemoved
    }

    fun isCommsOnline(): Boolean {
        return isCommsAlive() && commsActive
    }

    fun canAcceptCommsTower(tower: CommsTowerNode): Boolean
}
