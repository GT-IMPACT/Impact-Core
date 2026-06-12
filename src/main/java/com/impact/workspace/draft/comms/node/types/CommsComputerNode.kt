package com.impact.workspace.draft.comms.node.types

import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.node.CommsGroundNodeType

interface CommsComputerNode : CommsGroundNode {
    override val commsType: CommsGroundNodeType
        get() = CommsGroundNodeType.COMPUTER
}