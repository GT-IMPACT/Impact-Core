package com.impact.workspace.draft.comms.common

import java.util.UUID

data class CommsLink(
    val from: UUID,
    val to: UUID,
    val type: CommsLinkType
) {
    fun has(id: UUID): Boolean {
        return from == id || to == id
    }
}