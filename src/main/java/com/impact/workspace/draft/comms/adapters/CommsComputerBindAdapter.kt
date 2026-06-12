package com.impact.workspace.draft.comms.adapters

import com.impact.workspace.draft.comms.common.CommsComputerBindingProvider
import com.impact.workspace.draft.comms.common.CommsPos
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.world.World
import java.util.UUID

internal fun World.addLinkComputerToExecutor(pos: CommsPos, computerId: UUID?) {
    val te = getTileEntity(pos.x, pos.y, pos.z) as? IGregTechTileEntity ?: return
    val executor = te.metaTileEntity as? CommsComputerBindingProvider ?: return
    executor.addLinkComputer(computerId)
}
