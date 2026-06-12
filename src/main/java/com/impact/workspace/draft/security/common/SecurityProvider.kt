package com.impact.workspace.draft.security.common

interface SecurityProvider {
    fun can(ctx: SecurityContext): Boolean
}
