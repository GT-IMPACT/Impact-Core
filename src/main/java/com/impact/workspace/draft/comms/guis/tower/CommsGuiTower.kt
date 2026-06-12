package com.impact.workspace.draft.comms.guis.tower

import com.impact.client.gui.container.FullScreenGuiContainer
import com.impact.client.render.special.BlockHighlightRenderer
import com.impact.client.render.special.SphereHighlightRenderer
import com.impact.network.Net
import com.impact.workspace.draft.comms.common.CommsActionType
import com.impact.workspace.draft.comms.common.CommsVisibleMachine
import com.impact.workspace.draft.comms.node.CommsGroundNodeType
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import org.lwjgl.input.Mouse
import java.util.UUID

@SideOnly(Side.CLIENT)
class CommsGuiTower(
    private val container: CommsContainerTower,
    private val towerRadius: Int,
) : FullScreenGuiContainer(container) {

    private enum class MachineFilter {
        ALL,
        CONNECTED,
        DISCONNECTED,
    }

    private var selectedId: UUID? = null
    private var scrollOffset = 0
    private var filter = MachineFilter.ALL

    private val cardHeight = 36
    private val filterButtonWidth = 92
    private val filterButtonHeight = 16

    private val topButtonHeight = 16
    private val showRadiusButtonWidth = 100

    private val detailButtonWidth = 100
    private val detailButtonHeight = 18

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

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        drawPanels()
        drawFilterButton()
        drawShowRadiusButton(mouseX, mouseY)
        drawMachineList()
        drawSelectedMachine(mouseX, mouseY)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        drawDefaultBackground()
    }

    private fun visibleNodes(): List<CommsVisibleMachine> {
        return container.nodes
            .asSequence()
            .filter { node ->
                when (filter) {
                    MachineFilter.ALL -> true
                    MachineFilter.CONNECTED -> node.connected
                    MachineFilter.DISCONNECTED -> !node.connected
                }
            }
            .sortedWith(
                compareByDescending<CommsVisibleMachine> { it.connected }
                    .thenBy { it.distanceSquared }
            )
            .toList()
    }

    private fun drawPanels() {
        drawRect(0, 0, width, height, 0xCC111111.toInt())

        drawRect(8, 42, leftPanelRight(), height - 8, 0x66000000)
        drawRect(leftPanelRight() + 4, 42, width - 8, height - 8, 0x66000000)

        fontRendererObj.drawString("Communication Tower", 10, 10, 0xFFFFFF)
        fontRendererObj.drawString("Radius: $towerRadius", width - 90, 10, 0xAAAAAA)

        val visible = visibleNodes()
        val connected = container.nodes.count { it.connected }

        fontRendererObj.drawString("Nodes: ${visible.size}/${container.nodes.size}   Connected: $connected", 10, 27, 0xAAAAAA)
    }

    private fun drawFilterButton() {
        val x = leftPanelRight() - filterButtonWidth - 6
        val y = 23

        drawRect(x, y, x + filterButtonWidth, y + filterButtonHeight, 0x88444444.toInt())

        fontRendererObj.drawString("Filter: ${filter.name}", x + 5, y + 4, 0xFFFFFF)
    }

    private fun drawMachineList() {
        val nodes = visibleNodes()

        clampScroll(nodes.size)

        val visibleCards = (height - 54) / cardHeight
        val end = minOf(nodes.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val node = nodes[index]
            val cardY = 46 + (index - scrollOffset) * cardHeight

            drawCard(
                node = node,
                y = cardY,
                selected = selectedId == node.id
            )
        }
    }

    private fun drawCard(
        node: CommsVisibleMachine,
        y: Int,
        selected: Boolean
    ) {
        val bg = if (selected) 0xAA336699.toInt() else 0x66333333

        drawRect(10, y, leftPanelRight() - 4, y + cardHeight - 2, bg)

        val statusColor = if (node.connected) 0x55FF55 else 0xFF5555

        fontRendererObj.drawString(node.displayName, 14, y + 4, 0xFFFFFF)

        fontRendererObj.drawString(node.id.toString().substring(0, 8), 14, y + 16, 0xBBBBBB)

        fontRendererObj.drawString(if (node.connected) "Connected" else "Offline", leftPanelRight() - 70, y + 10, statusColor)
    }

    private fun drawSelectedMachine(mouseX: Int, mouseY: Int) {
        val node = selectedId
            ?.let { id -> container.nodes.firstOrNull { it.id == id } }

        if (node == null) {
            fontRendererObj.drawString("Select machine", leftPanelRight() + 20, 58, 0x888888)
            return
        }

        drawShowInWorldButton(mouseX = mouseX, mouseY = mouseY)
        drawComputerActionButton(mouseX = mouseX, mouseY = mouseY)

        val x = leftPanelRight() + 16
        var y = 78

        drawField(x, y, "NAME", node.displayName)
        y += 14

        drawField(x, y, "ID", node.id.toString())
        y += 14

        drawField(x, y, "TYPE", node.type.name)
        y += 14

        drawField(x, y, "CONNECTED", node.connected.toString())
        y += 24

        drawField(x, y, "X", node.pos.x.toString())
        y += 14

        drawField(x, y, "Y", node.pos.y.toString())
        y += 14

        drawField(x, y, "Z", node.pos.z.toString())
    }

    private fun drawField(x: Int, y: Int, name: String, value: String) {
        fontRendererObj.drawString("$name:", x, y, 0xAAAAAA)
        fontRendererObj.drawString(value, x + 100, y, 0xFFFFFF)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (button == 0) {
            if (isInsideShowRadiusButton(mouseX, mouseY)) {
                onShowSphereRadius()
                return
            }

            if (isInsideComputerLinkButton(mouseX, mouseY)) {
                val node = getSelectedNode()
                if (node != null) {
                    onComputerLinkClicked(node)
                }
                return
            }

            if (isInsideShowInWorldButton(mouseX, mouseY)) {
                val node = getSelectedNode()
                if (node != null) {
                    onShowInWorldClicked(node)
                }
                return
            }

            if (isInsideFilterButton(mouseX, mouseY)) {
                nextFilter()
                scrollOffset = 0

                val visible = visibleNodes()
                if (selectedId != null && visible.none { it.id == selectedId }) {
                    selectedId = null
                }

                return
            }

            selectCardAt(mouseX, mouseY)
        }
    }

    private fun getSelectedNode(): CommsVisibleMachine? {
        val id = selectedId ?: return null
        return container.nodes.firstOrNull { it.id == id }
    }

    override fun handleMouseInput() {
        super.handleMouseInput()

        val wheel = Mouse.getEventDWheel()

        if (wheel > 0) scrollOffset--
        if (wheel < 0) scrollOffset++

        clampScroll(visibleNodes().size)
    }

    private fun nextFilter() {
        filter = when (filter) {
            MachineFilter.ALL -> MachineFilter.CONNECTED
            MachineFilter.CONNECTED -> MachineFilter.DISCONNECTED
            MachineFilter.DISCONNECTED -> MachineFilter.ALL
        }
    }

    private fun isInsideFilterButton(mouseX: Int, mouseY: Int): Boolean {
        val x = leftPanelRight() - filterButtonWidth - 6
        val y = 23

        return mouseX >= x && mouseX <= x + filterButtonWidth && mouseY >= y && mouseY <= y + filterButtonHeight
    }

    private fun clampScroll(nodeCount: Int) {
        val visibleCards = (height - 54) / cardHeight

        val maxOffset = maxOf(
            0,
            nodeCount - visibleCards
        )

        scrollOffset = scrollOffset.coerceIn(0, maxOffset)
    }

    private fun leftPanelRight(): Int {
        return width / 3
    }

    private fun drawShowRadiusButton(mouseX: Int, mouseY: Int) {
        val x = width - showRadiusButtonWidth - 10
        val y = 23

        drawButtonRect(
            x = x,
            y = y,
            w = showRadiusButtonWidth,
            h = topButtonHeight,
            text = "Show work radius",
            hovered = isInsideShowRadiusButton(mouseX, mouseY),
        )
    }

    private fun isInsideShowRadiusButton(mouseX: Int, mouseY: Int): Boolean {
        val x = width - showRadiusButtonWidth - 10
        val y = 23

        return isInside(mouseX, mouseY, x, y, showRadiusButtonWidth, topButtonHeight)
    }

    private fun drawShowInWorldButton(mouseX: Int, mouseY: Int) {
        val rect = showInWorldButtonRect() ?: return

        drawButtonRect(
            x = rect.x,
            y = rect.y,
            w = rect.w,
            h = rect.h,
            text = "Show in World",
            hovered = rect.contains(mouseX, mouseY),
        )
    }

    private fun isInsideShowInWorldButton(mouseX: Int, mouseY: Int): Boolean {
        if (selectedId == null) return false
        return showInWorldButtonRect()?.contains(mouseX, mouseY) == true
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

        val border = if (hovered) 0xFFFFFFFF.toInt() else 0xAA777777.toInt()

        drawRect(x, y, x + w, y + 1, border)
        drawRect(x, y, x + 1, y + h, border)
        drawRect(x, y + h - 1, x + w, y + h, 0xAA222222.toInt())
        drawRect(x + w - 1, y, x + w, y + h, 0xAA222222.toInt())

        val textX = x + (w - fontRendererObj.getStringWidth(text)) / 2
        val textY = y + (h - 8) / 2

        fontRendererObj.drawString(text, textX, textY, 0xFFFFFF)
    }

    private fun isInside(mouseX: Int, mouseY: Int, x: Int, y: Int, w: Int, h: Int): Boolean {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h
    }

    private fun showInWorldButtonRect(): Rect? {
        if (getSelectedNode() == null) return null

        return Rect(
            x = width - detailButtonWidth - 16,
            y = 50,
            w = detailButtonWidth,
            h = detailButtonHeight
        )
    }

    private fun selectCardAt(mouseX: Int, mouseY: Int) {
        val nodes = visibleNodes()

        val visibleCards = (height - 54) / cardHeight
        val end = minOf(nodes.size, scrollOffset + visibleCards)

        for (index in scrollOffset until end) {
            val y = 46 + (index - scrollOffset) * cardHeight

            if (
                mouseX >= 10 &&
                mouseX <= leftPanelRight() - 4 &&
                mouseY >= y &&
                mouseY <= y + cardHeight
            ) {
                selectedId = nodes[index].id
                return
            }
        }
    }

    private fun drawComputerActionButton(mouseX: Int, mouseY: Int) {
        val node = getSelectedNode() ?: return
        val rect = computerLinkButtonRect() ?: return

        val text = when (node.type) {
            CommsGroundNodeType.EXECUTOR -> "Computer Link"
            CommsGroundNodeType.COMPUTER -> "Computer Settings"
            else -> return
        }

        drawButtonRect(
            x = rect.x,
            y = rect.y,
            w = rect.w,
            h = rect.h,
            text = text,
            hovered = rect.contains(mouseX, mouseY),
        )
    }

    private fun isInsideComputerLinkButton(mouseX: Int, mouseY: Int): Boolean {
        if (selectedId == null) return false
        return computerLinkButtonRect()?.contains(mouseX, mouseY) == true
    }

    private fun computerLinkButtonRect(): Rect? {
        if (getSelectedNode() == null) return null

        val showRect = showInWorldButtonRect() ?: return null

        return Rect(
            x = showRect.x - detailButtonWidth - 6,
            y = showRect.y,
            w = detailButtonWidth,
            h = detailButtonHeight
        )
    }

    private fun onShowSphereRadius() {
        val pos = container.tower.commsPos
        SphereHighlightRenderer.highlightSphere(
            x = pos.x.toDouble(),
            y = pos.y.toDouble(),
            z = pos.z.toDouble(),
            radius = towerRadius.toDouble(),
            durationMs = 20 * 1000,
        )
    }

    private fun onShowInWorldClicked(node: CommsVisibleMachine) {
        BlockHighlightRenderer.highlightBlock(
            x = node.pos.x,
            y = node.pos.y,
            z = node.pos.z,
            durationMs = 20 * 1000,
        )
    }

    private fun onComputerLinkClicked(node: CommsVisibleMachine) {
        when (node.type) {
            CommsGroundNodeType.COMPUTER -> {
                Net.containerServerUpdatePipe.send {
                    int(CommsActionType.OpenGuiComputerSetting.id)
                    xyz(node.pos.x, node.pos.y, node.pos.z)
                }
            }

            CommsGroundNodeType.EXECUTOR -> {
                Net.containerServerUpdatePipe.send {
                    int(CommsActionType.OpenGuiComputerLinks.id)
                    xyz(node.pos.x, node.pos.y, node.pos.z)
                }
            }

            else -> Unit
        }

    }
}
