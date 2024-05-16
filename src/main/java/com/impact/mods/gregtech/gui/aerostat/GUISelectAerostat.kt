package com.impact.mods.gregtech.gui.aerostat

import com.google.common.io.ByteArrayDataInput
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat
import gregtech.api.enums.GT_Values
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.util.EnumChatFormatting
import space.impact.packet_network.network.packets.IStreamPacketReceiver
import java.text.NumberFormat
import kotlin.math.min

class GUISelectAerostat(aInventoryPlayer: InventoryPlayer?, aTileEntity: IGregTechTileEntity?, var mName: String) :
    GT_GUIContainerMT_Machine(ContainerSelectAerostat(aInventoryPlayer, aTileEntity), GT_Values.RES_PATH_GUI + "AerostatSelect.png"), IStreamPacketReceiver {

    private var mStationName: String = ""
    private var playerName: String = ""
    private var names: MutableList<String> = ArrayList()

    private fun getNameLocation(id: Int, color: EnumChatFormatting, general: Boolean): String {
        val container = mContainer as ContainerSelectAerostat
        for ((idd, name) in names.withIndex()) {
            if (idd == id) {
                if (color == EnumChatFormatting.RESET) return name
                return color.toString() + "" + (if (general) EnumChatFormatting.BOLD.toString() + "> " + color else "") + name + color + (if (general) " " + container.distance + "m" else "")
            }
        }
        return ""
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, par3: Float) {
        super.drawScreen(mouseX, mouseY, par3)
        if (mContainer != null) {
            val container = mContainer as ContainerSelectAerostat
            if (mContainer.mTileEntity != null) {
                val buffer = maxOf(0.0, min(container.curBuffer.toDouble() / GTMTE_Aerostat.MAX_BUFFER.toDouble(), 100.0))
                getTooltip(
                    mouseX, mouseY, 9, 158 - 60, 60, 14, arrayOf(
                        "Fuel Amount: ", NumberFormat.getNumberInstance().format(buffer) + "%"
                    )
                )
            }
        }
    }

    override fun drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
        //this.fontRendererObj.drawString(mName, 33, 8, 16448255);
        val container = mContainer as ContainerSelectAerostat

        fontRendererObj.drawString(mName + " " + EnumChatFormatting.GREEN + mStationName, 33, 8, 16448255)
        fontRendererObj.drawString("Owner: " + EnumChatFormatting.YELLOW + playerName, 33, 18, 16448255)

        if (mStationName.isNotEmpty()) {
            if (names.isNotEmpty()) {
                fontRendererObj.drawString("Station Select:", 33, 28, 16448255)
                fontRendererObj.drawString("Need Fuel to move: " + NumberFormat.getNumberInstance().format((container.distance * 25).toDouble() / GTMTE_Aerostat.MAX_BUFFER.toDouble() * 100.0) + "%", 33, 38, 16448255)

                fontRendererObj.drawString(getNameLocation(container.idLocation + 4, EnumChatFormatting.DARK_GRAY, false), 33, 60 - 10, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation + 3, EnumChatFormatting.DARK_GRAY, false), 33, 70 - 10, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation + 2, EnumChatFormatting.DARK_GRAY, false), 33, 80 - 10, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation + 1, EnumChatFormatting.DARK_GRAY, false), 33, 90 - 10, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation + 0, EnumChatFormatting.GOLD, true), 33, 100 - 5, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation - 1, EnumChatFormatting.DARK_GRAY, false), 33, 110, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation - 2, EnumChatFormatting.DARK_GRAY, false), 33, 120, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation - 3, EnumChatFormatting.DARK_GRAY, false), 33, 130, 16448255)
                fontRendererObj.drawString(getNameLocation(container.idLocation - 4, EnumChatFormatting.DARK_GRAY, false), 33, 140, 16448255)
            } else {
                fontRendererObj.drawString(EnumChatFormatting.RED.toString() + "No Stations", 33, 70, 16448255)
                fontRendererObj.drawString(EnumChatFormatting.RED.toString() + "in the range of visibility ", 33, 80, 16448255)
            }
        } else {
            fontRendererObj.drawString(EnumChatFormatting.RED.toString() + "Station without name!", 33, 70, 16448255)
            fontRendererObj.drawString(EnumChatFormatting.RED.toString() + "Station no valid!", 33, 90, 16448255)
        }
    }

    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3)
        val x = (this.width - this.xSize) / 2
        val y = (this.height - this.ySize) / 2
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize)
        val container = mContainer as ContainerSelectAerostat
        val tScale = maxOf(0.0, min(container.curBuffer.toDouble() / GTMTE_Aerostat.MAX_BUFFER.toDouble(), 100.0))
        drawTexturedModalRect(x + 9, y + 158 - min(60.0, tScale * 60.0).toInt(), 242, 60 - min(60.0, tScale * 60.0).toInt(), 14, 60)
    }

    override fun receive(data: ByteArrayDataInput) {
        val size = data.readInt()
        this.playerName = data.readUTF()
        this.mStationName = data.readUTF()
        for (i in 0 until size - 2) {
            names.add(data.readUTF())
        }
    }
}
