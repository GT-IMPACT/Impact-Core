package com.impact.workspace.draft.comms.common

import java.util.UUID

data class CommsVisibleComputerForLink(
    val id: UUID,
    val pos: CommsPos,
    val active: Boolean,
    val linked: Boolean,
    val routeAvailable: Boolean,
    val distanceSquared: Int,
)
