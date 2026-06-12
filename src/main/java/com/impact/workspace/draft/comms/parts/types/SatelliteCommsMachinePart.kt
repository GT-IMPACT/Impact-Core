package com.impact.workspace.draft.comms.parts.types

import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.node.types.CommsTowerNode
import com.impact.workspace.draft.comms.parts.CommsMachinePart
import com.impact.workspace.draft.comms.satellite.CommsSatelliteEndpoint
import java.util.UUID

class SatelliteCommsMachinePart(
    access: CommsMachineAccess,
    private val activeProvider: CommsActiveProvider,
) : CommsMachinePart(access), CommsSatelliteEndpoint {

    override var commsOnline: Boolean = false
    override var commsRemoved: Boolean = false

    override val commsActive: Boolean
        get() = activeProvider.isCommsActive()

    override val commsOwnerId: UUID
        get() = access.ownerId

    override var commsNetworkState = CommsNetworkState.DISCONNECTED

    override fun canAcceptCommsTower(tower: CommsTowerNode): Boolean {
        return isCommsOnline() && tower.isCommsOnline()
    }

    override fun handleLoad() {
        commsOnline = false
        commsRemoved = false
        CommsServer.satelliteLoad(this)
    }

    override fun handleUnload() {
        CommsServer.satelliteUnload(commsId)
    }

    override fun handleRemove() {
        CommsServer.satelliteRemove(commsId)
    }

    override fun handleRead() {
        commsOnline = false
        commsRemoved = false
    }
}
