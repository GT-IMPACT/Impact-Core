package com.impact.workspace.draft.comms.parts.types

import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.node.types.CommsTowerNode
import com.impact.workspace.draft.comms.satellite.CommsSatelliteEndpoint
import java.util.UUID

class VirtualSatelliteCommsMachinePart : CommsSatelliteEndpoint {

    override val commsId: UUID = UUID.randomUUID()

    override var commsOnline: Boolean = true
    override var commsRemoved: Boolean = false

    override val commsActive: Boolean = true

    override val commsOwnerId: UUID
        get() = UUID.randomUUID() // TODO add real owner id

    override var commsNetworkState = CommsNetworkState.CONNECTED

    override fun canAcceptCommsTower(tower: CommsTowerNode): Boolean {
        return isCommsOnline() && tower.isCommsOnline()
    }
}
