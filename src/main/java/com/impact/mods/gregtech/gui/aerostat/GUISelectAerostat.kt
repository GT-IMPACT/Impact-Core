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

class GUISelectAerostat(
    inventory: InventoryPlayer,
    tile: IGregTechTileEntity,
    private val title: String,
) : GT_GUIContainerMT_Machine(
    ContainerSelectAerostat(
        inventory = inventory,
        tile = tile,
    ),
    GT_Values.RES_PATH_GUI + "AerostatSelect.png",
), IStreamPacketReceiver {

    private var stationName: String = ""
    private var ownerName: String = ""
    private val names: MutableList<String> = ArrayList()

    override fun drawScreen(
        mouseX: Int,
        mouseY: Int,
        partialTicks: Float,
    ) {
        super.drawScreen(
            mouseX,
            mouseY,
            partialTicks,
        )
        val container = mContainer as ContainerSelectAerostat
        val fuel = NumberFormat.getPercentInstance().format(fuelFraction(container))
        getTooltip(
            mouseX,
            mouseY,
            9,
            98,
            60,
            14,
            arrayOf(
                "Fuel Amount: ",
                fuel,
            ),
        )
    }

    override fun drawGuiContainerForegroundLayer(
        mouseX: Int,
        mouseY: Int,
    ) {
        val container = mContainer as ContainerSelectAerostat
        drawText(
            "$title ${EnumChatFormatting.GREEN}$stationName",
            8,
        )
        drawText(
            "Owner: ${EnumChatFormatting.YELLOW}$ownerName",
            18,
        )
        if (stationName.isEmpty()) {
            drawText(
                "${EnumChatFormatting.RED}Station without name!",
                70,
            )
            drawText(
                "${EnumChatFormatting.RED}Station no valid!",
                90,
            )
            return
        }
        if (names.isEmpty()) {
            drawText(
                "${EnumChatFormatting.RED}No Stations",
                70,
            )
            drawText(
                "${EnumChatFormatting.RED}in the range of visibility ",
                80,
            )
            return
        }
        val neededFuel = container.distance * 25.0 / GTMTE_Aerostat.MAX_BUFFER
        val formattedFuel = NumberFormat.getPercentInstance().format(neededFuel)
        drawText(
            "Station Select:",
            28,
        )
        drawText(
            "Need Fuel to move: $formattedFuel",
            38,
        )
        for (offset in 4 downTo -4) {
            val name = names.getOrNull(container.selectedIndex + offset) ?: continue
            val y = 90 - offset * 10
            if (offset == 0) {
                val gold = EnumChatFormatting.GOLD
                val bold = EnumChatFormatting.BOLD
                drawText(
                    "$gold$bold> $gold$name$gold ${container.distance}m",
                    95,
                )
            } else {
                drawText(
                    "${EnumChatFormatting.DARK_GRAY}$name",
                    if (offset > 0) y else y + 10,
                )
            }
        }
    }

    override fun drawGuiContainerBackgroundLayer(
        partialTicks: Float,
        mouseX: Int,
        mouseY: Int,
    ) {
        super.drawGuiContainerBackgroundLayer(
            partialTicks,
            mouseX,
            mouseY,
        )
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(
            x,
            y,
            0,
            0,
            xSize,
            ySize,
        )
        val container = mContainer as ContainerSelectAerostat
        val fill = (fuelFraction(container) * 60).toInt()
        drawTexturedModalRect(
            x + 9,
            y + 158 - fill,
            242,
            60 - fill,
            14,
            fill,
        )
    }

    override fun receive(data: ByteArrayDataInput) {
        val size = data.readInt()
        ownerName = data.readUTF()
        stationName = data.readUTF()
        names.clear()
        repeat(size - 2) {
            names.add(data.readUTF())
        }
    }

    private fun drawText(
        text: String,
        y: Int,
    ) {
        fontRendererObj.drawString(
            text,
            33,
            y,
            16448255,
        )
    }

    private fun fuelFraction(container: ContainerSelectAerostat): Double {
        return (container.fuel.toDouble() / GTMTE_Aerostat.MAX_BUFFER).coerceIn(
            0.0,
            1.0,
        )
    }
}
