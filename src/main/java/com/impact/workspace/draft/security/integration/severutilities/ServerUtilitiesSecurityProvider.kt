package com.impact.workspace.draft.security.integration.severutilities

import com.impact.workspace.draft.security.common.SecurityAction
import com.impact.workspace.draft.security.common.SecurityContext
import com.impact.workspace.draft.security.common.SecurityProvider
import serverutils.lib.data.Universe

class ServerUtilitiesSecurityProvider : SecurityProvider {

    override fun can(ctx: SecurityContext): Boolean {
        val universe = Universe.get() ?: return false

        val actor = universe.getPlayer(ctx.actorOwnerId) ?: return false
        val target = universe.getPlayer(ctx.targetOwnerId) ?: return false

        val actorTeam = actor.team ?: return false
        val targetTeam = target.team ?: return false

        return actorTeam.equalsTeam(targetTeam)
    }
}
