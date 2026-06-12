package com.impact.workspace.draft.comms.parts

import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.comms.node.CommsGroundNode
import java.util.UUID

abstract class GroundCommsMachinePart(
    access: CommsMachineAccess,
) : CommsMachinePart(access), CommsGroundNode {

    final override val commsWorld: CommsWorldKey
        get() = super.commsWorld

    final override val commsPos: CommsPos
        get() = super.commsPos

    override val commsActive: Boolean
        get() = true

    override val commsOwnerId: UUID
        get() = access.ownerId

    final override var commsLoaded: Boolean = false
    final override var commsRemoved: Boolean = false

    final override fun handleLoad() {
        commsLoaded = false
        commsRemoved = false
        CommsServer.nodeLoad(this)
    }

    final override fun handleUnload() {
        CommsServer.nodeUnload(this)
    }

    final override fun handleRemove() {
        CommsServer.nodeRemove(this)
    }

    final override fun handleRead() {
        commsLoaded = false
        commsRemoved = false
    }
}
