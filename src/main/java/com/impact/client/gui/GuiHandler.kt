package com.impact.client.gui

import com.impact.workspace.draft.comms.guis.computerlinks.CommsContainerComputerLink
import com.impact.workspace.draft.comms.guis.computerlinks.CommsGuiComputerLink
import com.impact.workspace.draft.comms.guis.network.CommsContainerNetwork
import com.impact.workspace.draft.comms.guis.network.CommsGuiNetwork
import com.impact.workspace.draft.comms.guis.tower.CommsContainerTower
import com.impact.workspace.draft.comms.guis.tower.CommsGuiTower
import com.impact.workspace.draft.comms.integration.gt.tower.TowerCommunicationMachine
import com.impact.workspace.draft.comms.parts.CommsMachinePartProvider
import com.impact.workspace.draft.comms.parts.types.ExecutorCommsMachinePart
import cpw.mods.fml.common.network.IGuiHandler
import gregtech.api.interfaces.metatileentity.IMetaTileEntity
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class GuiHandler : IGuiHandler {

    override fun getServerGuiElement(
        id: Int,
        player: EntityPlayer, world: World,
        x: Int, y: Int, z: Int
    ): Any? {
        return when (id) {
            GuiRegistry.CommsTowerRadar.id -> {
                val mte = getMte(world, x, y, z) ?: return null
                val tower = mte as? TowerCommunicationMachine ?: return null
                CommsContainerTower(player = player, tower = tower.part)
            }

            GuiRegistry.CommsNetwork.id -> {
                CommsContainerNetwork(player)
            }

            GuiRegistry.CommsComputerLink.id -> {
                val mte = getMte(world, x, y, z) ?: return null
                val provider = mte as? CommsMachinePartProvider ?: return null
                val executor = provider.part as? ExecutorCommsMachinePart ?: return null
                CommsContainerComputerLink(player = player, executor = executor)
            }

            else -> null
        }
    }

    override fun getClientGuiElement(
        id: Int,
        player: EntityPlayer, world: World,
        x: Int, y: Int, z: Int
    ): Any? {
        return when (id) {
            GuiRegistry.CommsTowerRadar.id -> {
                val mte = getMte(world, x, y, z) ?: return null
                val tower = mte as? TowerCommunicationMachine ?: return null
                CommsGuiTower(
                    container = CommsContainerTower(player = player, tower = tower.part),
                    towerRadius = tower.part.commsRadius,
                )
            }

            GuiRegistry.CommsNetwork.id -> {
                CommsGuiNetwork(
                    container = CommsContainerNetwork(player),
                )
            }

            GuiRegistry.CommsComputerLink.id -> {
                val mte = getMte(world, x, y, z) ?: return null
                val provider = mte as? CommsMachinePartProvider ?: return null
                val executor = provider.part as? ExecutorCommsMachinePart ?: return null
                CommsGuiComputerLink(
                    container = CommsContainerComputerLink(player = player, executor = executor),
                )
            }

            else -> null
        }
    }

    private fun getMte(world: World, x: Int, y: Int, z: Int): IMetaTileEntity? {
        val te = world.getTileEntity(x, y, z) ?: return null
        val igt = te as? IGregTechTileEntity ?: return null
        return igt.metaTileEntity
    }
}
