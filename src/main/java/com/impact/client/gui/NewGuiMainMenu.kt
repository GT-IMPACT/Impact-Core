package com.impact.client.gui

import com.impact.client.gui.button.ImpactGuiButton
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiMultiplayer
import net.minecraft.client.gui.GuiOptions
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiSelectWorld
import org.lwjgl.opengl.GL11
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.Random

@SideOnly(Side.CLIENT)
class NewGuiMainMenu : GuiScreen() {

    companion object {
        private val DASH_MESSAGES = arrayOf(
            "STELLAR MAP LINK: STABLE",
            "AE2 QUANTUM BRIDGE: SYNCHRONIZED",
            "GREGTECH POWER GRID: NOMINAL",
            "ORBITAL FACTORY BUS: ONLINE",
            "ME STORAGE INDEX: READY",
            "ASTRO-MINING TELEMETRY: ACTIVE",
            "DEEP SPACE ROUTING: CALCULATED",
            "MATTER FABRICATION ARRAY: STANDBY"
        )
    }

    val lastReleaseVersion by lazy {
        val url = URL("https://api.github.com/repos/GT-IMPACT/IMPACT-1/releases/latest")
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("User-Agent", "IMPACT-Launcher")

            val response = connection.inputStream.bufferedReader().use { it.readText() }

            Regex("\"tag_name\"\\s*:\\s*\"([^\"]+)\"")
                .find(response)
                ?.groupValues
                ?.get(1)
        } finally {
            connection.disconnect()
        }
    }

    val currentVersionModpack by lazy {
        try {
            File(
                Minecraft.getMinecraft().mcDataDir,
                "IMPACT/version.txt"
            ).readText()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun urlOpen(url: String) {
        try {
            val oclass = Class.forName("java.awt.Desktop")
            val `object` = oclass.getMethod("getDesktop", *arrayOfNulls<Class<*>>(0)).invoke(null)
            oclass.getMethod("browse", *arrayOf<Class<*>>(URI::class.java)).invoke(`object`, URI(url))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private data class Star(val x: Int, val y: Int, val layer: Int, val phase: Int)
    private data class Node(val radius: Int, val size: Int, val speedDiv: Int, val color: Int)

    private val random = Random(844L)
    private val stars = ArrayList<Star>()
    private val nodes = arrayOf(
        Node(34, 3, 42, 0xFF66DDFF.toInt()),
        Node(58, 4, 63, 0xFFAA66FF.toInt()),
        Node(86, 3, 88, 0xFFFFAA33.toInt()),
        Node(118, 5, 128, 0xFF55FF99.toInt()),
        Node(150, 3, 171, 0xFFFFFFFF.toInt())
    )

    private var ticks: Int = 0
    private var messageIndex: Int = 0

    override fun initGui() {
        buttonList.clear()
        buildStars()

        val buttonW = 172
        val buttonH = 20
        val x = 18
        var y = 54 + 20 + 20 + 12

        buttonList.add(ImpactGuiButton(0, x, y, buttonW, buttonH, "SINGLEPLAYER"))
        y += 25
        buttonList.add(ImpactGuiButton(1, x, y, buttonW, buttonH, "MULTIPLAYER"))
        y += 25
        buttonList.add(ImpactGuiButton(2, x, y, buttonW, buttonH, "OPTIONS"))
        y += 25
        buttonList.add(ImpactGuiButton(3, x, y, buttonW, buttonH, "QUIT GAME"))

        if (width > 500) {
            buttonList.add(GuiTelemetryButton(4, width - 80, 80, 30, 12, "LINK"))
            buttonList.add(GuiTelemetryButton(5, width - 80, 96, 30, 12, "LINK"))
            buttonList.add(GuiTelemetryButton(6, width - 80, 112, 30, 12, "LINK"))
            buttonList.add(GuiTelemetryButton(7, width - 80, 184, 30, 12, "LINK"))
            buttonList.add(GuiTelemetryButton(8, width - 80, 200, 30, 12, "LINK"))
            buttonList.add(GuiTelemetryButton(9, width - 80, 216, 30, 12, "LINK"))
        }

        messageIndex = random.nextInt(DASH_MESSAGES.size)
    }

    private fun buildStars() {
        stars.clear()
        val count = 150
        var i = 0
        while (i < count) {
            stars.add(Star(random.nextInt(Math.max(width, 1)), random.nextInt(Math.max(height, 1)), 1 + random.nextInt(3), random.nextInt(80)))
            i++
        }
    }

    override fun actionPerformed(button: GuiButton) {
        if (!button.enabled) return

        when (button.id) {
            0 -> mc.displayGuiScreen(GuiSelectWorld(this))
            1 -> mc.displayGuiScreen(GuiMultiplayer(this))
            2 -> mc.displayGuiScreen(GuiOptions(this, mc.gameSettings))
            3 -> mc.shutdown()
            4 -> urlOpen("https://gt-impact.github.io/#/download")
            5 -> urlOpen("https://discord.gg/bMf2qvd")
            6 -> urlOpen("https://github.com/GT-IMPACT")
            7 -> urlOpen("https://github.com/GT-IMPACT/IMPACT-1/issues/new?template=report-a-bug.md")
            8 -> urlOpen("https://github.com/GT-IMPACT/IMPACT-1/issues/new?template=suggestion-for-future-modpack.md")
            9 -> urlOpen("https://github.com/GT-IMPACT/IMPACT-1/issues/new?template=modpack-questions.md")
        }
    }

    override fun updateScreen() {
        ticks++
        if (ticks % 110 == 0) {
            messageIndex = random.nextInt(DASH_MESSAGES.size)
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawSpaceBackground()
        drawStarfield()
        drawStellarSystem(partialTicks)
        drawLeftNavigationPanel()
        drawTelemetryBlocks()
        drawLogo()

        super.drawScreen(mouseX, mouseY, partialTicks)

        drawFooter()
    }

    private fun drawSpaceBackground() {
        drawGradientRect(0, 0, width, height, 0xFF030711.toInt(), 0xFF090D1E.toInt())

        // Subtle top glow.
        drawRect(0, 0, width, 1, 0x6644AAFF)
        drawRect(0, 1, width, 2, 0x333377AA)
    }

    private fun drawStarfield() {
        var i = 0
        while (i < stars.size) {
            val s = stars[i]
            val drift = (ticks / (8 * s.layer)) % width
            var x = s.x - drift
            if (x < 0) x += width

            val blink = (ticks + s.phase) % 80
            val alpha = if (blink < 40) 0xFF else 0xAA
            val color = when (s.layer) {
                1 -> (alpha shl 24) or 0x777777
                2 -> (alpha shl 24) or 0xAACCFF
                else -> (alpha shl 24) or 0xFFFFFF
            }
            val size = if (s.layer == 3 && blink < 20) 2 else 1
            drawRect(x, s.y, x + size, s.y + size, color)
            i++
        }
    }

    private fun drawStellarSystem(partialTicks: Float) {
        val cx = width / 2 + 35
        val cy = height / 2 + 8

        // Local time scale for this stellar system only.
        // Bigger value = slower planet movement.
        val planetSlowdown = 2.0f
        val slowTime = (ticks.toFloat() + partialTicks) / planetSlowdown

        // Central star / reactor-like stellar core.
        val pulse = (((ticks / 2) % 40) - 20)
        val core = 8 + Math.abs(pulse) / 5
        drawCircleBlock(cx, cy, core + 5, 0x3322AAFF)
        drawCircleBlock(cx, cy, core + 2, 0x6655CCFF)
        drawCircleBlock(cx, cy, core, 0xFFCCEEFF.toInt())

        // Orbits.
        var rIndex = 0
        while (rIndex < nodes.size) {
            val node = nodes[rIndex]
            drawOrbit(cx, cy, node.radius, 0x3355AAFF)
            rIndex++
        }

        // Rotating nodes / planets.
        var i = 0
        while (i < nodes.size) {
            val node = nodes[i]

            val angle = ((slowTime / node.speedDiv.toDouble()) * Math.PI * 2.0) + i * 0.85
            val x = cx.toDouble() + Math.cos(angle) * node.radius.toDouble()
            val y = cy.toDouble() + Math.sin(angle) * node.radius.toDouble() * 0.58

            drawCircleBlockSmooth(x, y, node.size + 2, 0x4422AAFF)
            drawCircleBlockSmooth(x, y, node.size, node.color)

            if (i % 2 == 0) {
                drawLine(cx.toDouble(), cy.toDouble(), x, y, 0x3355CCFF, 1.0f)
            }

            i++
        }

        // Crosshair overlay.
        drawLine((cx - 170).toDouble(), cy.toDouble(), (cx - 130).toDouble(), cy.toDouble(), 0x4455CCFF, 1.0f)
        drawLine((cx + 130).toDouble(), cy.toDouble(), (cx + 170).toDouble(), cy.toDouble(), 0x4455CCFF, 1.0f)
        drawLine(cx.toDouble(), (cy - 170).toDouble(), cx.toDouble(), (cy - 130).toDouble(), 0x4455CCFF, 1.0f)
        drawLine(cx.toDouble(), (cy + 130).toDouble(), cx.toDouble(), (cy + 170).toDouble(), 0x4455CCFF, 1.0f)
    }

    private fun drawCircleBlockSmooth(cx: Double, cy: Double, radius: Int, color: Int) {
        drawCircleBlock(Math.round(cx).toInt(), Math.round(cy).toInt(), radius, color)
    }

    private fun drawLeftNavigationPanel() {
        val x = 10
        val y = 18 + 25
        val w = 188
        val h = 198

        drawHudPanel(x, y, w, h, 0x55101A2A, 0xAA55CCFF.toInt())
        fontRendererObj.drawString("MENU", x + w / 2 - 12, y + 18, 0x88DDFF)
        drawRect(x + 10, y + 40, x + w - 10, y + 41, 0x8855CCFF.toInt())
    }

    private fun drawTelemetryBlocks() {
        val rightW = 210
        val rightX = width - rightW - 14
        val y1 = 34 + 10
        val y2 = 148
        val yy = 6

        if (width > 500) {
            drawHudPanel(rightX, y1, rightW, 92, 0x55101816, 0xAA55FFAA.toInt())
            fontRendererObj.drawString("SOCIAL MEDIA", rightX + 12, y1 + yy + 10, 0x55FFAA)
            telemetryText(rightX, y1 + yy + 30, "WEBSITE", "->")
            telemetryText(rightX, y1 + yy + 46, "DISCORD", "->")
            telemetryText(rightX, y1 + yy + 62, "GITHUB", "->")

            drawHudPanel(rightX, y2, rightW, 92, 0x551A1410, 0xAAFFAA33.toInt())
            fontRendererObj.drawString("SUBMIT A REQUEST", rightX + 12, y2 + yy + 10, 0xFFBB55)
            telemetryText(rightX, y2 + yy + 30, "REPORT A BUG", "->")
            telemetryText(rightX, y2 + yy + 46, "SUGGESTION", "->")
            telemetryText(rightX, y2 + yy + 62, "FEEDBACK", "->")
        }
    }

    private fun telemetryText(x: Int, y: Int, left: String, right: String) {
        fontRendererObj.drawString(left, x + 12, y, 0x999999)
        fontRendererObj.drawString(right, x + 120, y, 0xE6F6FF)
    }

    private fun drawLogo() {
        val title = "IMPACT: GREGTECH EDITION"
        val subtitle = "FACTORY * SPACE * RESEARCH * TECHNOLOGY"

        drawCenteredString(fontRendererObj, title, width / 2, 11, 0x88DDFF)
        drawCenteredString(fontRendererObj, subtitle, width / 2, 24, 0xCCAAFF)
    }

    private fun drawFooter() {
        val versionModpack = when {
            currentVersionModpack == lastReleaseVersion ->
                if (lastReleaseVersion.isNullOrEmpty()) "" else "* MODPACK v$lastReleaseVersion"

            currentVersionModpack != lastReleaseVersion ->
                if (currentVersionModpack.isEmpty()) "" else "* MODPACK v$currentVersionModpack"

            else -> ""
        }

        val text = "MINECRAFT v1.7.10 $versionModpack"
        fontRendererObj.drawString(text, 8, height - 12, 0xFFBB55)
    }

    private fun drawCircleBlock(cx: Int, cy: Int, radius: Int, color: Int) {
        var y = -radius
        while (y <= radius) {
            var x = -radius
            while (x <= radius) {
                if (x * x + y * y <= radius * radius) {
                    drawRect(cx + x, cy + y, cx + x + 1, cy + y + 1, color)
                }
                x++
            }
            y++
        }
    }

    private fun drawOrbit(cx: Int, cy: Int, radius: Int, color: Int) {
        var lastX = cx + radius
        var lastY = cy
        var a = 8
        while (a <= 360) {
            val rad = Math.toRadians(a.toDouble())
            val x = cx + Math.cos(rad) * radius
            val y = cy + Math.sin(rad) * radius * 0.58
            drawLine(lastX.toDouble(), lastY.toDouble(), x, y, color, 1.0f)
            lastX = x.toInt()
            lastY = y.toInt()
            a += 8
        }
    }

    private fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, color: Int, width: Float) {
        val a = (color shr 24 and 255).toFloat() / 255.0f
        val r = (color shr 16 and 255).toFloat() / 255.0f
        val g = (color shr 8 and 255).toFloat() / 255.0f
        val b = (color and 255).toFloat() / 255.0f

        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glLineWidth(width)
        GL11.glColor4f(r, g, b, a)
        GL11.glBegin(GL11.GL_LINES)
        GL11.glVertex2d(x1, y1)
        GL11.glVertex2d(x2, y2)
        GL11.glEnd()
        GL11.glEnable(GL11.GL_TEXTURE_2D)
    }

    private fun drawHudPanel(x: Int, y: Int, w: Int, h: Int, fillColor: Int, borderColor: Int) {
        drawRect(x, y, x + w, y + h, fillColor)

        drawRect(x, y, x + w, y + 1, borderColor)
        drawRect(x, y + h - 1, x + w, y + h, borderColor)
        drawRect(x, y, x + 1, y + h, borderColor)
        drawRect(x + w - 1, y, x + w, y + h, borderColor)

        val c = 11
        drawRect(x, y, x + c, y + 2, borderColor)
        drawRect(x, y, x + 2, y + c, borderColor)
        drawRect(x + w - c, y, x + w, y + 2, borderColor)
        drawRect(x + w - 2, y, x + w, y + c, borderColor)
        drawRect(x, y + h - 2, x + c, y + h, borderColor)
        drawRect(x, y + h - c, x + 2, y + h, borderColor)
        drawRect(x + w - c, y + h - 2, x + w, y + h, borderColor)
        drawRect(x + w - 2, y + h - c, x + w, y + h, borderColor)
    }

    override fun doesGuiPauseGame(): Boolean {
        return false
    }

    class GuiTelemetryButton(
        id: Int,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        text: String
    ) : GuiButton(id, x, y, width, height, text) {

        override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int) {
            if (!visible) return

            field_146123_n =
                mouseX >= xPosition &&
                        mouseY >= yPosition &&
                        mouseX < xPosition + width &&
                        mouseY < yPosition + height

            val color = if (field_146123_n) 0x66DDFF else 0xE6F6FF
            mc.fontRenderer.drawString(displayString, xPosition, yPosition, color)
        }
    }
}
