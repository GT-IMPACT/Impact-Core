package com.impact.workspace.draft.comms.adapters

import com.impact.workspace.draft.comms.common.CommsPos
import com.impact.workspace.draft.comms.common.CommsMachineAccess
import com.impact.workspace.draft.comms.common.CommsWorldKey
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import java.util.UUID

class GregtechMetaTileEntityCommsAccess(
    private val tile: IGregTechTileEntity
) : CommsMachineAccess {

    companion object {
        val IGregTechTileEntity.access
            get() = GregtechMetaTileEntityCommsAccess(this)
    }

    override val commsWorld: CommsWorldKey
        get() = tile.commsWorldKey()

    override val commsPos: CommsPos
        get() = tile.commsPos()

    override val commsIsServerSide: Boolean
        get() = tile.isServerSide

    override val commsIsAlive: Boolean
        get() = !tile.isDead

    override val unlocalizedName: String
        get() = tile.metaTileEntity.metaName

    override val isActive: Boolean
        get() = tile.isActive

    override val ownerId: UUID
        get() = tile.ownerUuid
}
