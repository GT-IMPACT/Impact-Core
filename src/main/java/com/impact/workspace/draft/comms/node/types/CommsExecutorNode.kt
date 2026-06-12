package com.impact.workspace.draft.comms.node.types

import com.impact.workspace.draft.comms.node.CommsGroundNode
import com.impact.workspace.draft.comms.node.CommsGroundNodeType
import java.util.UUID

interface CommsExecutorNode : CommsGroundNode {
    override val commsType: CommsGroundNodeType
        get() = CommsGroundNodeType.EXECUTOR

    val boundComputerId: UUID?
}
