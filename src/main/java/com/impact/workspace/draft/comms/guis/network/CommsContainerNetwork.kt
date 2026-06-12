package com.impact.workspace.draft.comms.guis.network

import com.impact.client.gui.GuiRegistry
import com.impact.client.gui.container.FullScreenContainer
import com.impact.network.ContainerClientStreamUpdater
import com.impact.network.ContainerServerStreamUpdater
import com.impact.network.Net
import com.impact.util.Utilits
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleTower
import com.impact.workspace.draft.comms.common.mappers.CommsVisibleTowerMapper
import com.impact.workspace.draft.comms.common.CommsWorldKey
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import space.impact.packet_network.streamnet.StreamIn

class CommsContainerNetwork(
    private val player: EntityPlayer,
) : FullScreenContainer(), ContainerClientStreamUpdater, ContainerServerStreamUpdater {

    internal val nodes = arrayListOf<CommsVisibleTower>()
    private var isFirstOpened = true

    override fun fromServer(stream: StreamIn) {
        nodes.clear()
        repeat(stream.int()) {
            nodes += CommsVisibleTowerMapper.read(stream)
        }
    }

    override fun fromClient(player: EntityPlayerMP, stream: StreamIn) {
        when (stream.int()) {
            CommsActionType.OpenGuiTower.id -> {
                val xyz = stream.xyz()
                Utilits.openGui(player, GuiRegistry.CommsTowerRadar.id, xyz.x, xyz.y, xyz.z)
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
        val newNodes = CommsServer.getWorld(key).visibleTowersFor(playerId = player.uniqueID)

        if (newNodes == nodes) return

        nodes.clear()
        nodes += newNodes

        Net.containerClientUpdatePipe.send(player) {
            int(nodes.size)
            nodes.forEach { tower ->
                CommsVisibleTowerMapper.write(tower = tower, stream = this)
            }
        }
    }
}
