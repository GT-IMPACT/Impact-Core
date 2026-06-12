package com.impact.workspace.draft.comms.guis.computerlinks

import com.impact.client.gui.container.FullScreenGuiContainer
import com.impact.network.Net
import com.impact.network.Net.writeUuid
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleComputerForLink
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import org.lwjgl.input.Mouse
import java.util.UUID

@SideOnly(Side.CLIENT)
class CommsGuiComputerLink(
    private val container: CommsContainerComputerLink,
) : FullScreenGuiContainer(container) {

    private data class Rect(
        val x: Int,
        val y: Int,
        val w: Int,
        val h: Int
    ) {
        fun contains(mouseX: Int, mouseY: Int): Boolean {
            return mouseX >= x &&
                    mouseX <= x + w &&
                    mouseY >= y &&
                    mouseY <= y + h
        }
    }

    private var selectedId: UUID? = null
    private var scrollOffset = 0

    private val cardHeight = 42
    private val buttonWidth = 76
    private val buttonHeight = 18
    private val buttonGap = 6

    override fun drawGuiContainerBackgroundLayer(
        partialTicks: Float,
        mouseX: Int,
        mouseY: Int
    ) {
        drawDefaultBackground()
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)

        drawPanels()
        drawComputerList()
        drawSelectedComputer(mouseX, mouseY)
    }

    private fun drawPanels() {
        drawRect(0, 0, width, height, 0xCC111111.toInt())

        drawRect(8, 28, leftPanelRight(), height - 8, 0x66000000)
        drawRect(leftPanelRight() + 4, 28, width - 8, height - 8, 0x66000000)

        fontRendererObj.drawString(
            "Executor Link Manager",
            10,
            10,
            0xFFFFFF
        )

        val linked = container.nodes.firstOrNull { it.linked }

        fontRendererObj.drawString(
            "Computers: ${container.nodes.size}",
            width - 110,
            10,
            0xAAAAAA
        )

        if (linked != null) {
            fontRendererObj.drawString(
                "Linked: ${linked.id.toString().substring(0, 8)}",
                10,
                18,
                0x55FF55
            )
        } else {
            fontRendererObj.drawString(
                "Linked: none",
                10,
                18,
                0xFF5555
            )
        }
    }

    private fun sortedComputers(): List<CommsVisibleComputerForLink> {
        return container.nodes
            .sortedWith(
                compareByDescending<CommsVisibleComputerForLink> { it.linked }
                    .thenByDescending { it.routeAvailable }
                    .thenByDescending { it.active }
                    .thenBy { it.distanceSquared }
            )
    }

    private fun drawComputerList() {
        val computers = sortedComputers()

        clampScroll(computers.size)

        val visibleCards = (height - 42) / cardHeight
        val end = minOf(computers.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val computer = computers[index]
            val y = 32 + (index - scrollOffset) * cardHeight

            drawComputerCard(
                computer = computer,
                y = y,
                selected = selectedId == computer.id
            )
        }
    }

    private fun drawComputerCard(
        computer: CommsVisibleComputerForLink,
        y: Int,
        selected: Boolean
    ) {
        val bg = when {
            selected -> 0xAA336699.toInt()
            computer.linked -> 0x66336633
            else -> 0x66333333
        }

        drawRect(
            10,
            y,
            leftPanelRight() - 4,
            y + cardHeight - 2,
            bg
        )

        val statusColor = when {
            computer.linked -> 0x55FF55
            computer.routeAvailable -> 0x55AAFF
            computer.active -> 0xFFFF55
            else -> 0xFF5555
        }

        fontRendererObj.drawString(
            "COMPUTER",
            14,
            y + 4,
            0xFFFFFF
        )

        fontRendererObj.drawString(
            computer.id.toString().substring(0, 8),
            14,
            y + 16,
            0xBBBBBB
        )

        val status = when {
            computer.linked -> "Linked"
            computer.routeAvailable -> "Route OK"
            computer.active -> "No Route"
            else -> "Offline"
        }

        fontRendererObj.drawString(
            status,
            leftPanelRight() - 72,
            y + 6,
            statusColor
        )

        fontRendererObj.drawString(
            "%.1f m".format(kotlin.math.sqrt(computer.distanceSquared.toDouble())),
            leftPanelRight() - 72,
            y + 22,
            0xAAAAAA
        )
    }

    private fun drawSelectedComputer(mouseX: Int, mouseY: Int) {
        val computer = getSelectedComputer()

        if (computer == null) {
            fontRendererObj.drawString(
                "Select computer",
                leftPanelRight() + 20,
                48,
                0x888888
            )
            return
        }

        val x = leftPanelRight() + 16
        var y = 44

        fontRendererObj.drawString("Selected Computer", x, y, 0xFFFFFF)

        drawActionButtons(mouseX, mouseY)

        y += 36

        drawField(x, y, "ID", computer.id.toString())
        y += 14

        drawField(x, y, "ACTIVE", computer.active.toString())
        y += 14

        drawField(x, y, "LINKED", computer.linked.toString())
        y += 14

        drawField(x, y, "ROUTE", computer.routeAvailable.toString())
        y += 24

        drawField(x, y, "X", computer.pos.x.toString())
        y += 14

        drawField(x, y, "Y", computer.pos.y.toString())
        y += 14

        drawField(x, y, "Z", computer.pos.z.toString())
    }

    private fun drawActionButtons(mouseX: Int, mouseY: Int) {
        val link = linkButtonRect()
        val unlink = unlinkButtonRect()
        val path = showPathButtonRect()

        drawButtonRect(
            link.x,
            link.y,
            link.w,
            link.h,
            "Link",
            link.contains(mouseX, mouseY),
        )

        drawButtonRect(
            unlink.x,
            unlink.y,
            unlink.w,
            unlink.h,
            "Unlink",
            unlink.contains(mouseX, mouseY),
        )

        drawButtonRect(
            path.x,
            path.y,
            path.w,
            path.h,
            "Show Path",
            path.contains(mouseX, mouseY),
        )
    }

    private fun linkButtonRect(): Rect {
        return Rect(
            x = leftPanelRight() + 16,
            y = 62,
            w = buttonWidth,
            h = buttonHeight
        )
    }

    private fun unlinkButtonRect(): Rect {
        val link = linkButtonRect()

        return Rect(
            x = link.x + buttonWidth + buttonGap,
            y = link.y,
            w = buttonWidth,
            h = buttonHeight
        )
    }

    private fun showPathButtonRect(): Rect {
        val unlink = unlinkButtonRect()

        return Rect(
            x = unlink.x + buttonWidth + buttonGap,
            y = unlink.y,
            w = buttonWidth + 18,
            h = buttonHeight
        )
    }

    private fun drawButtonRect(
        x: Int,
        y: Int,
        w: Int,
        h: Int,
        text: String,
        hovered: Boolean,
    ) {
        val bg = when {
            hovered -> 0xAA555555.toInt()
            else -> 0x88444444.toInt()
        }

        drawRect(x, y, x + w, y + h, bg)

        val border =
            if (hovered) 0xFFFFFFFF.toInt()
            else 0xAA777777.toInt()

        drawRect(x, y, x + w, y + 1, border)
        drawRect(x, y, x + 1, y + h, border)
        drawRect(x, y + h - 1, x + w, y + h, 0xAA222222.toInt())
        drawRect(x + w - 1, y, x + w, y + h, 0xAA222222.toInt())

        val textX = x + (w - fontRendererObj.getStringWidth(text)) / 2
        val textY = y + (h - 8) / 2

        fontRendererObj.drawString(text, textX, textY, 0xFFFFFF)
    }

    private fun drawField(
        x: Int,
        y: Int,
        name: String,
        value: String
    ) {
        fontRendererObj.drawString("$name:", x, y, 0xAAAAAA)
        fontRendererObj.drawString(value, x + 110, y, 0xFFFFFF)
    }

    override fun mouseClicked(
        mouseX: Int,
        mouseY: Int,
        button: Int
    ) {
        if (button == 0) {
            val selected = getSelectedComputer()

            if (selected != null) {
                val link = linkButtonRect()
                if (link.contains(mouseX, mouseY)) {
                    onLinkClicked(selected)
                    return
                }

                val unlink = unlinkButtonRect()
                if (unlink.contains(mouseX, mouseY)) {
                    onUnlinkClicked(selected)
                    return
                }

                val path = showPathButtonRect()
                if (path.contains(mouseX, mouseY)) {
                    onShowPathClicked(selected)
                    return
                }
            }

            selectComputerAt(mouseX, mouseY)
        }

        super.mouseClicked(mouseX, mouseY, button)
    }

    private fun selectComputerAt(mouseX: Int, mouseY: Int) {
        val computers = sortedComputers()

        val visibleCards = (height - 42) / cardHeight
        val end = minOf(computers.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val y = 32 + (index - scrollOffset) * cardHeight

            if (
                mouseX >= 10 &&
                mouseX <= leftPanelRight() - 4 &&
                mouseY >= y &&
                mouseY <= y + cardHeight
            ) {
                selectedId = computers[index].id
                return
            }
        }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()

        val wheel = Mouse.getEventDWheel()

        if (wheel > 0) scrollOffset--
        if (wheel < 0) scrollOffset++

        clampScroll(sortedComputers().size)
    }

    private fun clampScroll(count: Int = container.nodes.size) {
        val visibleCards = (height - 42) / cardHeight

        val maxOffset = maxOf(
            0,
            count - visibleCards
        )

        scrollOffset = scrollOffset.coerceIn(0, maxOffset)
    }

    private fun getSelectedComputer(): CommsVisibleComputerForLink? {
        val id = selectedId ?: return null
        return container.nodes.firstOrNull { it.id == id }
    }

    private fun leftPanelRight(): Int {
        return width / 3
    }

    private fun onLinkClicked(computer: CommsVisibleComputerForLink) {
        Net.containerServerUpdatePipe.send {
            int(CommsActionType.LinkComputerExecutor.id)
            writeUuid(computer.id)
        }
    }

    private fun onUnlinkClicked(computer: CommsVisibleComputerForLink) {
        Net.containerServerUpdatePipe.send {
            int(CommsActionType.UnlinkComputerExecutor.id)
        }
    }

    private fun onShowPathClicked(computer: CommsVisibleComputerForLink) {
        Net.containerServerUpdatePipe.send {
            int(CommsActionType.ShowConnectionPath.id)
        }
    }
}