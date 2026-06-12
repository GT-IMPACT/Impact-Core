package com.impact.workspace.draft.security.common

import net.minecraft.world.World
import java.util.UUID

data class SecurityContext(
    val actorOwnerId: UUID,
    val targetOwnerId: UUID,
    val action: SecurityAction,
    val world: World? = null,
    val x: Int = 0,
    val y: Int = 0,
    val z: Int = 0,
)
