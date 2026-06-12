package com.impact.workspace.draft.comms.parts.types

import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.common.CommsActiveProvider
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import com.impact.workspace.draft.comms.common.CommsNetworkState
import com.impact.workspace.draft.comms.common.CommsVisibleMachine
import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.node.CommsGroundNodeType
import com.impact.workspace.draft.comms.node.types.CommsTowerNode
import com.impact.workspace.draft.comms.parts.GroundCommsMachinePart
import java.util.UUID

class TowerCommsMachinePart(
    access: CommsMachineAccess,
    private val activeProvider: CommsActiveProvider,
    private val radiusProvider: Int,
) : GroundCommsMachinePart(access), CommsTowerNode {

    override val commsType: CommsGroundNodeType
        get() = CommsGroundNodeType.TOWER

    override val commsRadius: Int
        get() = radiusProvider

    override val unlocalizedName: String
        get() = access.unlocalizedName

    override val commsActive: Boolean
        get() = activeProvider.isCommsActive()

    override fun isCommsAlive(): Boolean {
        return commsLoaded && !commsRemoved
    }

    override var commsNetworkState = CommsNetworkState.DISCONNECTED

    override fun canReachCommsNode(node: CommsGroundNode): Boolean {
        if (!isCommsOnline()) return false
        if (!node.isCommsOnline()) return false
        if (commsWorld != node.commsWorld) return false

        return commsPos.distanceSquaredTo(node.commsPos) <= commsRadius * commsRadius
    }

    fun getVisibleMachines(playerId: UUID): List<CommsVisibleMachine> {
        return CommsServer.getMachinesInTowerRadius(
            world = commsWorld,
            playerId = playerId,
            towerId = commsId,
        )
    }
}
