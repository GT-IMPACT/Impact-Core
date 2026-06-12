package com.impact.workspace.draft.comms.common

import com.impact.workspace.draft.comms.node.CommsGroundNodeType
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.util.GT_LanguageManager
import net.minecraft.client.Minecraft
import space.impact.packet_network.streamnet.StreamIn
import space.impact.packet_network.streamnet.StreamOut
import java.util.UUID

data class CommsVisibleMachine(
    val id: UUID,
    val displayName: String,
    val type: CommsGroundNodeType,
    val pos: CommsPos,
    val distanceSquared: Int,
    val connected: Boolean,
    val dx: Int,
    val dz: Int,
    val dy: Int,
) {
    companion object {

        fun fromDO(stream: StreamIn): CommsVisibleMachine {
            val world = Minecraft.getMinecraft().theWorld

            val id = UUID(stream.long(), stream.long())
            val ordinal = stream.byte()
            val type = CommsGroundNodeType.entries[ordinal.toInt()]
            val pos = stream.xyz().let { CommsPos(it.x, it.y, it.z) }

            val gte = world.getTileEntity(pos.x, pos.y, pos.z) as? IGregTechTileEntity
            val name = gte?.metaTileEntity?.metaName
                ?.let { GT_LanguageManager.getTranslation("gt.blockmachines.$it.name") }
                .orEmpty()

            return CommsVisibleMachine(
                id = id,
                type = type,
                pos = pos,
                distanceSquared = 0,
                connected = stream.bool(),
                dx = 0,
                dz = 0,
                dy = 0,
                displayName = name,
            )
        }

        fun CommsVisibleMachine.toDO(stream: StreamOut) {
            stream.long(id.mostSignificantBits)
            stream.long(id.leastSignificantBits)
            stream.byte(type.ordinal)
            stream.xyz(pos.x, pos.y, pos.z)
            stream.bool(connected)
        }
    }
}
