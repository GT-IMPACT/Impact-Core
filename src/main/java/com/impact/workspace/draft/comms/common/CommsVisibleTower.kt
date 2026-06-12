package com.impact.workspace.draft.comms.common

import java.util.UUID

data class CommsVisibleTower(
    val id: UUID,
    val pos: CommsPos,
    val active: Boolean,
    val connectedMachines: Int,
)
