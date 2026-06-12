package com.impact.workspace.draft.comms.node.types

import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.node.CommsGroundNodeType

interface CommsTowerNode : CommsGroundNode {
    val commsRadius: Int

    override val commsType: CommsGroundNodeType
        get() = CommsGroundNodeType.TOWER

    fun canReachCommsNode(node: CommsGroundNode): Boolean {
        if (!isCommsOnline()) return false
        if (!node.isCommsOnline()) return false
        if (commsWorld != node.commsWorld) return false

        return commsPos.distanceSquaredTo(node.commsPos) <= commsRadius * commsRadius
    }
}
