package com.impact.workspace.draft.comms.guis.network

import com.impact.client.gui.container.FullScreenGuiContainer
import com.impact.client.render.special.BlockHighlightRenderer
import com.impact.network.Net
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleTower
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import org.lwjgl.input.Mouse
import java.util.UUID

@SideOnly(Side.CLIENT)
class CommsGuiNetwork(
    private val container: CommsContainerNetwork,
) : FullScreenGuiContainer(container) {

    private var selectedId: UUID? = null
    private var scrollOffset = 0

    private val cardHeight = 38

    private val detailButtonWidth = 76
    private val detailButtonHeight = 18
    private val detailButtonGap = 6

    override fun initGui() {
        super.initGui()
        xSize = width
        ySize = height
    }

    override fun drawGuiContainerBackgroundLayer(
        partialTicks: Float,
        mouseX: Int,
        mouseY: Int
    ) {
        drawDefaultBackground()
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        drawPanels()
        drawTowerList()
        drawSelectedTower(mouseX, mouseY)
    }

    private fun drawPanels() {
        drawRect(0, 0, width, height, 0xCC111111.toInt())

        drawRect(8, 28, leftPanelRight(), height - 8, 0x66000000)
        drawRect(leftPanelRight() + 4, 28, width - 8, height - 8, 0x66000000)

        fontRendererObj.drawString(
            "Communication Network",
            10,
            10,
            0xFFFFFF
        )

        fontRendererObj.drawString(
            "NODES: ${container.nodes.size}",
            width - 90,
            10,
            0xAAAAAA
        )
    }

    private fun drawTowerList() {
        clampScroll()

        val visibleCards = (height - 42) / cardHeight
        val end = minOf(container.nodes.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val tower = container.nodes[index]
            val y = 32 + (index - scrollOffset) * cardHeight

            drawTowerCard(
                tower = tower,
                y = y,
                selected = selectedId == tower.id
            )
        }
    }

    private fun drawTowerCard(
        tower: CommsVisibleTower,
        y: Int,
        selected: Boolean
    ) {
        val bg =
            if (selected) 0xAA336699.toInt()
            else 0x66333333

        drawRect(
            10,
            y,
            leftPanelRight() - 4,
            y + cardHeight - 2,
            bg
        )

        val statusColor =
            if (tower.active) 0x55FF55 else 0xFF5555

        fontRendererObj.drawString(
            "Communication Node",
            14,
            y + 4,
            0xFFFFFF
        )

        fontRendererObj.drawString(
            tower.id.toString().substring(0, 8),
            14,
            y + 16,
            0xBBBBBB
        )

        val text = if (tower.active) "CONNECTED" else "DISCONNECTED"
        fontRendererObj.drawString(
            text,
            leftPanelRight() - fontRendererObj.getStringWidth(text) - 8,
            y + 6,
            statusColor
        )
    }

    private fun drawSelectedTower(mouseX: Int, mouseY: Int) {
        val tower = getSelectedTower()

        if (tower == null) {
            fontRendererObj.drawString("Select Node", leftPanelRight() + 20, 46, 0x888888)
            return
        }

        val x = leftPanelRight() + 16
        var y = 40

        fontRendererObj.drawString("Node Information", x, y, 0xFFFFFF)


        drawTowerDetailButtons(mouseX, mouseY)

        y += 60

        drawField(x, y, "ID", tower.id.toString())
        y += 14

        drawField(x, y, "ACTIVE", if (tower.active) "ON" else "OFF")
        y += 14

        drawField(x, y, "SUB NODES", tower.connectedMachines.toString())
        y += 24

        drawField(x, y, "X", tower.pos.x.toString())
        y += 14

        drawField(x, y, "Y", tower.pos.y.toString())
        y += 14

        drawField(x, y, "Z", tower.pos.z.toString())
    }

    private fun drawField(x: Int, y: Int, name: String, value: String) {
        fontRendererObj.drawString("$name:", x, y, 0xAAAAAA)
        fontRendererObj.drawString(value, x + 110, y, 0xFFFFFF)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (button == 0) {
            val open = openTowerButtonRect()
            if (open != null && open.contains(mouseX, mouseY)) {
                getSelectedTower()?.let { onOpenTowerClicked(it) }
                return
            }

            val show = showTowerButtonRect()
            if (show != null && show.contains(mouseX, mouseY)) {
                getSelectedTower()?.let { onShowTowerInWorldClicked(it) }
                return
            }

            selectTowerAt(mouseX, mouseY)
        }

        super.mouseClicked(mouseX, mouseY, button)
    }

    private fun selectTowerAt(
        mouseX: Int,
        mouseY: Int
    ) {
        val visibleCards = (height - 42) / cardHeight
        val end = minOf(container.nodes.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val y = 32 + (index - scrollOffset) * cardHeight

            if (
                mouseX >= 10 &&
                mouseX <= leftPanelRight() - 4 &&
                mouseY >= y &&
                mouseY <= y + cardHeight
            ) {
                selectedId = container.nodes[index].id
                return
            }
        }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()

        val wheel = Mouse.getEventDWheel()

        if (wheel > 0) scrollOffset--
        if (wheel < 0) scrollOffset++

        clampScroll()
    }

    private fun clampScroll() {
        val visibleCards = (height - 42) / cardHeight

        val maxOffset = maxOf(
            0,
            container.nodes.size - visibleCards
        )

        scrollOffset = scrollOffset.coerceIn(0, maxOffset)
    }

    private fun getSelectedTower(): CommsVisibleTower? {
        val id = selectedId ?: return null
        return container.nodes.firstOrNull { it.id == id }
    }

    private fun leftPanelRight(): Int {
        return width / 3
    }

    private fun openTowerButtonRect(): Rect? {
        if (getSelectedTower() == null) return null

        return Rect(
            x = leftPanelRight() + 16,
            y = 62,
            w = detailButtonWidth,
            h = detailButtonHeight
        )
    }

    private fun showTowerButtonRect(): Rect? {
        val open = openTowerButtonRect() ?: return null

        return Rect(
            x = open.x + detailButtonWidth + detailButtonGap,
            y = open.y,
            w = detailButtonWidth + 24,
            h = detailButtonHeight
        )
    }

    private fun drawTowerDetailButtons(mouseX: Int, mouseY: Int) {
        val open = openTowerButtonRect()
        if (open != null) {
            drawButtonRect(
                x = open.x,
                y = open.y,
                w = open.w,
                h = open.h,
                text = "Open",
                hovered = open.contains(mouseX, mouseY),
            )
        }

        val show = showTowerButtonRect()
        if (show != null) {
            drawButtonRect(
                x = show.x,
                y = show.y,
                w = show.w,
                h = show.h,
                text = "Show in World",
                hovered = show.contains(mouseX, mouseY),
            )
        }
    }

    private fun drawButtonRect(
        x: Int,
        y: Int,
        w: Int,
        h: Int,
        text: String,
        hovered: Boolean,
        pressed: Boolean = false,
    ) {
        val bg = when {
            pressed -> 0xAA222222.toInt()
            hovered -> 0xAA555555.toInt()
            else -> 0x88444444.toInt()
        }

        drawRect(x, y, x + w, y + h, bg)

        val border = if (hovered) 0xFFFFFFFF.toInt() else 0xAA777777.toInt()

        drawRect(x, y, x + w, y + 1, border)
        drawRect(x, y, x + 1, y + h, border)
        drawRect(x, y + h - 1, x + w, y + h, 0xAA222222.toInt())
        drawRect(x + w - 1, y, x + w, y + h, 0xAA222222.toInt())

        val textX = x + (w - fontRendererObj.getStringWidth(text)) / 2
        val textY = y + (h - 8) / 2 + if (pressed) 1 else 0

        fontRendererObj.drawString(text, textX, textY, 0xFFFFFF)
    }

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

    private fun onOpenTowerClicked(tower: CommsVisibleTower) {
        Net.containerServerUpdatePipe.send {
            int(CommsActionType.OpenGuiTower.id)
            xyz(tower.pos.x, tower.pos.y, tower.pos.z)
        }
    }

    private fun onShowTowerInWorldClicked(tower: CommsVisibleTower) {
        BlockHighlightRenderer.highlightBlock(
            x = tower.pos.x,
            y = tower.pos.y,
            z = tower.pos.z,
            durationMs = 20 * 1000,
        )
        keyTyped(Char.MAX_VALUE, 1)
    }
}
