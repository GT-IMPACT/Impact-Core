package com.impact.workspace.draft.comms.common

import java.util.UUID

data class CommsPathPoint(
    val id: UUID,
    val type: CommsPathPointType,
    val world: CommsWorldKey?,
    val pos: CommsPos?
)
