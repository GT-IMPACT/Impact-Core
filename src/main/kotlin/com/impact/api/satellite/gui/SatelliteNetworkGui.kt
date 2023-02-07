package com.impact.api.satellite.gui

import gregtech.api.enums.GT_Values.RES_PATH_GUI
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.EnumChatFormatting.*

class SatelliteNetworkGui(
    inv: InventoryPlayer, te: IGregTechTileEntity, val name: String
) : GT_GUIContainerMetaTile_Machine(SatelliteNetworkContainer(inv, te), "${RES_PATH_GUI}SpaceSatelliteHatches.png") {

    override fun drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
        fontRendererObj.drawString(name, 33, 8, 16448255)
        if (mContainer != null) {
            val aContainer = mContainer as SatelliteNetworkContainer
            fontRendererObj.drawString("Frequency: ${aContainer.currentFrequency}", 33, 20, 16448255)
            fontRendererObj.drawString("Satellite connection: ${if (aContainer.isConnected) "${GREEN}on" else "${RED}off"}", 33, 64, 16448255)
        }
    }

    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3)
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
    }
}