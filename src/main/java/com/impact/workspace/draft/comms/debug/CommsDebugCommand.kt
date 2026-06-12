package com.impact.workspace.draft.comms.debug

import com.impact.client.gui.GuiRegistry
import com.impact.util.Utilits
import com.impact.workspace.draft.comms.CommsServer
import com.impact.workspace.draft.comms.parts.types.VirtualSatelliteCommsMachinePart
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class CommsDebugCommand : CommandBase() {

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun getCommandName(): String {
        return "comms"
    }

    override fun getCommandUsage(ics: ICommandSender?): String {
        return "comms"
    }

    override fun canCommandSenderUseCommand(ics: ICommandSender?): Boolean {
        return true
    }

    override fun processCommand(ics: ICommandSender, args: Array<String>) {

        val player = getCommandSenderAsPlayer(ics)

        when (args[0]) {
            "sputnik" -> {
                val satellite = VirtualSatelliteCommsMachinePart()
                CommsServer.satelliteLoad(
                    satellite = satellite,
                )
            }
            "gui" -> {
                Utilits.openGui(player, GuiRegistry.CommsNetwork.id, 0, 0, 0)
            }
            else -> Unit
        }
    }
}
