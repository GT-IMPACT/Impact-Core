package com.impact.workspace.draft.comms.guis.tower

import com.impact.client.gui.GuiRegistry
import com.impact.client.gui.container.FullScreenContainer
import com.impact.network.ContainerClientStreamUpdater
import com.impact.network.ContainerServerStreamUpdater
import com.impact.network.Net
import com.impact.util.Utilits
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleMachine
import com.impact.workspace.draft.comms.common.CommsVisibleMachine.Companion.toDO
import com.impact.workspace.draft.comms.parts.types.TowerCommsMachinePart
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import space.impact.packet_network.streamnet.StreamIn

class CommsContainerTower(
    private val player: EntityPlayer,
    internal val tower: TowerCommsMachinePart,
) : FullScreenContainer(), ContainerClientStreamUpdater, ContainerServerStreamUpdater {

    internal val nodes = arrayListOf<CommsVisibleMachine>()
    private var isFirstOpened = true

    override fun fromServer(stream: StreamIn) {
        nodes.clear()
        repeat(stream.int()) {
            nodes += CommsVisibleMachine.fromDO(stream)
        }
    }

    override fun fromClient(player: EntityPlayerMP, stream: StreamIn) {
        when (stream.int()) {
            CommsActionType.OpenGuiComputerLinks.id -> {
                val xyz = stream.xyz()
                Utilits.openGui(player, GuiRegistry.CommsComputerLink.id, xyz.x, xyz.y, xyz.z)
            }

            CommsActionType.OpenGuiComputerSetting.id -> {
                val xyz = stream.xyz()
                // TODO add open gui settings by computer type
            }
        }
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return tower.isCommsAlive()
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()

        val player = player as? EntityPlayerMP ?: return
        if (player.worldObj.totalWorldTime % 80 != 0L && !isFirstOpened) return
        isFirstOpened = false

        val newNodes = tower.getVisibleMachines(playerId = player.uniqueID)
        if (newNodes == nodes) return

        nodes.clear()
        nodes += newNodes

        Net.containerClientUpdatePipe.send(player) {
            int(nodes.size)
            nodes.forEach { it.toDO(this) }
        }
    }
}
