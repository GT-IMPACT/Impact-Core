@file:Suppress("unused")

package com.impact.client.render.special

import com.impact.workspace.draft.comms.common.mappers.CommsPathStreamMapper
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.common.MinecraftForge
import space.impact.packet_network.streamnet.StreamIn

object SpecialRendersRegistry {

    @JvmStatic
    fun registerRenders() {
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onRenderWorldLast(event: RenderWorldLastEvent) {
        BlockHighlightRenderer.onRenderWorldLast(event)
        SphereHighlightRenderer.onRenderWorldLast(event)
        PathHighlightRenderer.onRenderWorldLast(event)
    }

    fun doRender(type: RenderSpecialType, stream: StreamIn) {
        when (type) {
            RenderSpecialType.Unknown -> Unit

            RenderSpecialType.BlockHighlight -> Unit  // TODO

            RenderSpecialType.SphereHighlight -> Unit // TODO

            RenderSpecialType.PathHighlight -> {
                CommsPathStreamMapper.readPath(stream)?.also { path ->
                    PathHighlightRenderer.showPath(path = path, durationMs = 20 * 1000L)
                }
            }
        }
    }

}
