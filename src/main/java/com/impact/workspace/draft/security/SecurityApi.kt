package com.impact.workspace.draft.security

import com.impact.workspace.draft.security.common.SecurityAction
import com.impact.workspace.draft.security.common.SecurityContext
import com.impact.workspace.draft.security.common.SecurityProvider
import com.impact.workspace.draft.security.integration.severutilities.ServerUtilitiesSecurityProvider
import com.impact.workspace.draft.security.integration.vanilla.VanillaSecurityProvider
import cpw.mods.fml.common.Loader
import java.util.UUID

object SecurityApi {

    private val securityProvider: SecurityProvider by lazy {
        if (Loader.isModLoaded("serverutilities")) {
            ServerUtilitiesSecurityProvider()
        } else {
            VanillaSecurityProvider()
        }
    }

    fun can(ctx: SecurityContext): Boolean {
        if (ctx.actorOwnerId == ctx.targetOwnerId) return true
        return securityProvider.can(ctx)
    }

    fun can(
        player1: UUID,
        player2: UUID,
        action: SecurityAction = SecurityAction.ALL,
    ): Boolean {
        val ctx = SecurityContext(
            actorOwnerId = player1,
            targetOwnerId = player2,
            action = action,
        )
        return can(ctx)
    }

    fun can(
        player1: String,
        player2: String,
        action: SecurityAction = SecurityAction.ALL,
    ): Boolean {
        val player1Uuid = runCatching { UUID.fromString(player1) }.getOrNull() ?: return false
        val player2Uuid = runCatching { UUID.fromString(player2) }.getOrNull() ?: return false
        return can(
            player1 = player1Uuid,
            player2 = player2Uuid,
            action = action,
        )
    }
}
