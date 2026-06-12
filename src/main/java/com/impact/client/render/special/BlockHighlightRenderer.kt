package com.impact.client.render.special

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.sin

@SideOnly(Side.CLIENT)
object BlockHighlightRenderer {

    private data class Highlight(
        val x: Int,
        val y: Int,
        val z: Int,
        val startTime: Long,
        val durationMs: Long
    )

    private val highlights = CopyOnWriteArrayList<Highlight>()

    /**
     * Вызвать с клиента:
     * BlockHighlightRenderer.highlightBlock(x, y, z)
     */
    fun highlightBlock(x: Int, y: Int, z: Int, durationMs: Long = 3000L) {
        highlights += Highlight(
            x = x,
            y = y,
            z = z,
            startTime = System.currentTimeMillis(),
            durationMs = durationMs
        )
    }

    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (highlights.isEmpty()) return

        val mc = Minecraft.getMinecraft()
        val player = mc.renderViewEntity ?: return

        val partial = event.partialTicks.toDouble()

        val camX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partial
        val camY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partial
        val camZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partial

        val now = System.currentTimeMillis()

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glPushMatrix()

        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glDisable(GL11.GL_DEPTH_TEST)

        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GL11.glLineWidth(3.0f)

        GL11.glTranslated(-camX, -camY, -camZ)

        for (highlight in highlights) {
            val elapsed = now - highlight.startTime

            if (elapsed > highlight.durationMs) {
                highlights.remove(highlight)
                continue
            }

            val progress = elapsed.toFloat() / highlight.durationMs.toFloat()

            // Моргание: alpha от 0.15 до 1.0
            val blink = ((sin(elapsed / 120.0) + 1.0) / 2.0).toFloat()
            val alpha = 0.15f + blink * 0.85f

            val box = AxisAlignedBB.getBoundingBox(
                highlight.x.toDouble(),
                highlight.y.toDouble(),
                highlight.z.toDouble(),
                highlight.x + 1.0,
                highlight.y + 1.0,
                highlight.z + 1.0
            ).expand(0.003, 0.003, 0.003)

            GL11.glColor4f(1.0f, 0.0f, 0.0f, alpha)

            drawOutlinedBox(box)
        }

        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    private fun drawOutlinedBox(box: AxisAlignedBB) {
        val t = Tessellator.instance

        GL11.glBegin(GL11.GL_LINES)

        vertex(box.minX, box.minY, box.minZ)
        vertex(box.maxX, box.minY, box.minZ)

        vertex(box.maxX, box.minY, box.minZ)
        vertex(box.maxX, box.minY, box.maxZ)

        vertex(box.maxX, box.minY, box.maxZ)
        vertex(box.minX, box.minY, box.maxZ)

        vertex(box.minX, box.minY, box.maxZ)
        vertex(box.minX, box.minY, box.minZ)

        vertex(box.minX, box.maxY, box.minZ)
        vertex(box.maxX, box.maxY, box.minZ)

        vertex(box.maxX, box.maxY, box.minZ)
        vertex(box.maxX, box.maxY, box.maxZ)

        vertex(box.maxX, box.maxY, box.maxZ)
        vertex(box.minX, box.maxY, box.maxZ)

        vertex(box.minX, box.maxY, box.maxZ)
        vertex(box.minX, box.maxY, box.minZ)

        vertex(box.minX, box.minY, box.minZ)
        vertex(box.minX, box.maxY, box.minZ)

        vertex(box.maxX, box.minY, box.minZ)
        vertex(box.maxX, box.maxY, box.minZ)

        vertex(box.maxX, box.minY, box.maxZ)
        vertex(box.maxX, box.maxY, box.maxZ)

        vertex(box.minX, box.minY, box.maxZ)
        vertex(box.minX, box.maxY, box.maxZ)

        GL11.glEnd()
    }

    private fun vertex(x: Double, y: Double, z: Double) {
        GL11.glVertex3d(x, y, z)
    }
}
