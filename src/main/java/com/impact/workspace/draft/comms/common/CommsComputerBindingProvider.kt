package com.impact.workspace.draft.comms.common

import java.util.UUID

interface CommsComputerBindingProvider {

    fun getLinkComputer(): UUID?

    fun addLinkComputer(id: UUID?)
}
