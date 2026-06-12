package com.impact.workspace.draft.comms.guis.computerlinks

import com.impact.client.gui.container.FullScreenContainer
import com.impact.client.render.special.RenderSpecialType
import com.impact.network.ContainerClientStreamUpdater
import com.impact.network.ContainerServerStreamUpdater
import com.impact.network.Net
import com.impact.network.Net.readUuid
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.adapters.addLinkComputerToExecutor
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleComputerForLink
import com.impact.workspace.draft.comms.common.CommsWorldKey
import com.impact.workspace.draft.comms.common.mappers.CommsPathStreamMapper
import com.impact.workspace.draft.comms.common.mappers.CommsVisibleComputerMapper
import com.impact.workspace.draft.comms.parts.types.ExecutorCommsMachinePart
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import space.impact.packet_network.streamnet.StreamIn

class CommsContainerComputerLink(
    private val player: EntityPlayer,
    private val executor: ExecutorCommsMachinePart,
) : FullScreenContainer(), ContainerClientStreamUpdater, ContainerServerStreamUpdater {

    internal val nodes = arrayListOf<CommsVisibleComputerForLink>()
    private var isFirstOpened = true

    override fun fromServer(stream: StreamIn) {
        nodes.clear()
        repeat(stream.int()) {
            nodes += CommsVisibleComputerMapper.read(stream)
        }
    }

    override fun fromClient(player: EntityPlayerMP, stream: StreamIn) {
        when (stream.int()) {
            CommsActionType.ShowConnectionPath.id -> {
                val connection = CommsServer
                    .getWorld(CommsWorldKey(player.worldObj.provider.dimensionId))
                    .findConnectionPathFromExecutor(executor.commsId)

                Net.renderSpecialClientUpdatePipe.send(player) {
                    int(RenderSpecialType.PathHighlight.ordinal)
                    CommsPathStreamMapper.writePath(this, connection)
                }
            }

            CommsActionType.LinkComputerExecutor.id -> {
                val computerId = stream.readUuid()
                val pos = executor.commsPos
                player.worldObj.addLinkComputerToExecutor(pos = pos, computerId = computerId)
            }

            CommsActionType.UnlinkComputerExecutor.id -> {
                val pos = executor.commsPos
                player.worldObj.addLinkComputerToExecutor(pos = pos, computerId = null)
            }
        }
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()

        val player = player as? EntityPlayerMP ?: return
        if (player.worldObj.totalWorldTime % 80 != 0L && !isFirstOpened) return
        isFirstOpened = false

        val key = CommsWorldKey(player.worldObj.provider.dimensionId)
        val newNodes = CommsServer.getWorld(key).visibleComputersForExecutor(
            playerId = player.uniqueID,
            executorId = executor.commsId,
        )

        if (newNodes == nodes) return

        nodes.clear()
        nodes += newNodes

        Net.containerClientUpdatePipe.send(player) {
            int(nodes.size)
            nodes.forEach { node ->
                CommsVisibleComputerMapper.write(computer = node, stream = this)
            }
        }
    }
}
