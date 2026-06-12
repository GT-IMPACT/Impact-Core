package com.impact.client.render.special

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderWorldLastEvent
import org.lwjgl.opengl.GL11
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@SideOnly(Side.CLIENT)
object SphereHighlightRenderer {

    private data class HighlightSphere(
        val x: Double,
        val y: Double,
        val z: Double,
        val radius: Double,
        val startTime: Long,
        val durationMs: Long
    )

    private val spheres = CopyOnWriteArrayList<HighlightSphere>()

    /**
     * Вызвать с клиента:
     * SphereHighlightRenderer.highlightSphere(x, y, z, radius)
     */
    fun highlightSphere(
        x: Double,
        y: Double,
        z: Double,
        radius: Double,
        durationMs: Long = 3000L
    ) {
        spheres += HighlightSphere(
            x = x,
            y = y,
            z = z,
            radius = radius,
            startTime = System.currentTimeMillis(),
            durationMs = durationMs
        )
    }

    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        if (spheres.isEmpty()) return

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
//        GL11.glDisable(GL11.GL_DEPTH_TEST)

        GL11.glEnable(GL11.GL_BLEND)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        GL11.glLineWidth(2.5f)

        GL11.glTranslated(-camX, -camY, -camZ)

        for (sphere in spheres) {
            val elapsed = now - sphere.startTime

            if (elapsed > sphere.durationMs) {
                spheres.remove(sphere)
                continue
            }

            val blink = ((sin(elapsed / 120.0) + 1.0) / 2.0).toFloat()
            val alpha = 0.15f + blink * 0.85f

            GL11.glColor4f(1.0f, 0.0f, 0.0f, alpha)

            drawSphere(
                centerX = sphere.x,
                centerY = sphere.y,
                centerZ = sphere.z,
                radius = sphere.radius,
                segments = 64,
                rings = 16,
                alpha = alpha
            )
        }

        GL11.glDepthMask(true)
        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    private fun drawSphere(
        centerX: Double,
        centerY: Double,
        centerZ: Double,
        radius: Double,
        segments: Int,
        rings: Int,
        alpha: Float
    ) {

        // Красная сетка поверх стенок
        GL11.glColor4f(1.0f, 0.0f, 0.0f, alpha)

        for (i in 1 until rings) {
            val theta = PI * i / rings
            val y = centerY + cos(theta) * radius
            val ringRadius = sin(theta) * radius

            GL11.glBegin(GL11.GL_LINE_LOOP)

            for (j in 0 until segments) {
                val angle = 2.0 * PI * j / segments
                GL11.glVertex3d(
                    centerX + cos(angle) * ringRadius,
                    y,
                    centerZ + sin(angle) * ringRadius
                )
            }

            GL11.glEnd()
        }

        for (i in 0 until rings) {
            val phi = 2.0 * PI * i / rings

            GL11.glBegin(GL11.GL_LINE_LOOP)

            for (j in 0 until segments) {
                val theta = 2.0 * PI * j / segments

                GL11.glVertex3d(
                    centerX + cos(phi) * sin(theta) * radius,
                    centerY + cos(theta) * radius,
                    centerZ + sin(phi) * sin(theta) * radius
                )
            }

            GL11.glEnd()
        }
    }
}
