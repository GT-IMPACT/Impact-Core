package com.impact.workspace.draft.comms.node

import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsWorldKey
import java.util.UUID

interface CommsGroundNode : CommsNetworkStateHolder, CommsOwnedNode {
    val commsId: UUID
    val commsWorld: CommsWorldKey
    val commsPos: CommsPos
    val commsType: CommsGroundNodeType
    val unlocalizedName: String

    var commsLoaded: Boolean
    var commsRemoved: Boolean

    val commsActive: Boolean

    fun isCommsAlive(): Boolean {
        return commsLoaded && !commsRemoved
    }

    fun isCommsOnline(): Boolean {
        return isCommsAlive() && commsActive
    }
}
