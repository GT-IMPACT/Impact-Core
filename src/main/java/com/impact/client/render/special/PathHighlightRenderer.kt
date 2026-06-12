package com.impact.client.render.special

import com.impact.workspace.draft.comms.common.CommsConnectionPath
import com.impact.workspace.draft.comms.common.CommsPos
import net.minecraft.client.Minecraft
import net.minecraft.util.AxisAlignedBB
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.sin

object PathHighlightRenderer {
    private data class RenderPoint(
        val x: Int,
        val y: Int,
        val z: Int
    )

    private data class RenderPath(
        val points: List<RenderPoint>,
        val startTime: Long,
        val durationMs: Long
    )

    private val paths = CopyOnWriteArrayList<RenderPath>()

    fun showPath(
        points: List<CommsPos>,
        durationMs: Long = 5000L
    ) {
        if (points.size < 2) return

        paths += RenderPath(
            points = points.map {
                RenderPoint(it.x, it.y, it.z)
            },
            startTime = System.currentTimeMillis(),
            durationMs = durationMs
        )
    }

    fun showPath(
        path: CommsConnectionPath,
        durationMs: Long = 5000L
    ) {
        val points = path.points()
            .mapNotNull { it.pos }

        showPath(points, durationMs)
    }

    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (paths.isEmpty()) return

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
        GL11.glBlendFunc(
            GL11.GL_SRC_ALPHA,
            GL11.GL_ONE_MINUS_SRC_ALPHA
        )

        GL11.glTranslated(-camX, -camY, -camZ)

        val iterator = paths.iterator()

        while (iterator.hasNext()) {
            val path = iterator.next()
            val elapsed = now - path.startTime

            if (elapsed > path.durationMs) {
                paths.remove(path)
                continue
            }

            val blink = ((sin(elapsed / 140.0) + 1.0) / 2.0).toFloat()
            val alpha = 0.35f + blink * 0.45f

            drawPath(path.points, alpha)
            drawPoints(path.points, alpha)
        }

        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    private fun drawPath(
        points: List<RenderPoint>,
        alpha: Float
    ) {
        GL11.glLineWidth(3.0f)
        GL11.glColor4f(0.2f, 0.8f, 1.0f, alpha)

        GL11.glBegin(GL11.GL_LINES)

        for (i in 0 until points.lastIndex) {
            val a = points[i]
            val b = points[i + 1]

            vertexCenter(a)
            vertexCenter(b)
        }

        GL11.glEnd()
    }

    private fun drawPoints(
        points: List<RenderPoint>,
        alpha: Float
    ) {
        GL11.glLineWidth(2.0f)

        for (point in points) {
            GL11.glColor4f(1.0f, 1.0f, 0.2f, alpha)

            val box = AxisAlignedBB.getBoundingBox(
                point.x.toDouble(),
                point.y.toDouble(),
                point.z.toDouble(),
                point.x + 1.0,
                point.y + 1.0,
                point.z + 1.0
            ).expand(0.004, 0.004, 0.004)

            drawOutlinedBox(box)
        }
    }

    private fun vertexCenter(point: RenderPoint) {
        GL11.glVertex3d(
            point.x + 0.5,
            point.y + 0.5,
            point.z + 0.5
        )
    }

    private fun drawOutlinedBox(box: AxisAlignedBB) {
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

