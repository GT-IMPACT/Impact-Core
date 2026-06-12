package com.impact.workspace.draft.comms.adapters

import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsWorldKey
import gregtech.api.interfaces.tileentity.IGregTechTileEntity

fun IGregTechTileEntity.commsWorldKey(): CommsWorldKey {
    return CommsWorldKey(world.provider.dimensionId)
}

fun IGregTechTileEntity.commsPos(): CommsPos {
    return CommsPos(xCoord, yCoord.toInt(), zCoord)
}
