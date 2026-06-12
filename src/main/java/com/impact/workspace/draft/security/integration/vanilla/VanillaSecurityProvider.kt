package com.impact.workspace.draft.security.integration.vanilla

import com.impact.workspace.draft.security.common.SecurityContext
import com.impact.workspace.draft.security.common.SecurityProvider

class VanillaSecurityProvider : SecurityProvider {

    override fun can(ctx: SecurityContext): Boolean {
        return true
    }
}
