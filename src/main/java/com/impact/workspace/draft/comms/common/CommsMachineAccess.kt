package com.impact.workspace.draft.comms.common

import java.util.UUID

interface CommsMachineAccess {
    val commsWorld: CommsWorldKey
    val commsPos: CommsPos
    val commsIsServerSide: Boolean
    val commsIsAlive: Boolean
    val unlocalizedName: String
    val isActive: Boolean
    val ownerId: UUID
}
