package com.impact.network

import com.impact.client.render.special.RenderSpecialType
import com.impact.client.render.special.SpecialRendersRegistry
import com.impact.core.impactLog.INFO
import net.minecraft.client.Minecraft
import space.impact.packet_network.streamnet.StreamIn
import space.impact.packet_network.streamnet.StreamNet
import space.impact.packet_network.streamnet.StreamOut
import java.util.UUID

object Net {

    val net = StreamNet("ImpactCoreNetwork")

    fun init() {
        INFO("Initializing StreamNet ImpactCoreNetworkChannel")
    }

    fun StreamIn.readUuid(): UUID {
        return UUID(this.long(), this.long())
    }

    fun StreamOut.writeUuid(id: UUID) {
        this.long(id.mostSignificantBits)
        this.long(id.leastSignificantBits)
    }

    internal val containerClientUpdatePipe = net.client(1) { stream ->
        val player = Minecraft.getMinecraft().thePlayer ?: return@client
        val container = player.openContainer ?: return@client
        if (container is ContainerClientStreamUpdater) {
            container.fromServer(stream)
        }
    }

    internal val containerServerUpdatePipe = net.server(2) { player, stream ->
        val container = player.openContainer ?: return@server
        if (container is ContainerServerStreamUpdater) {
            container.fromClient(player, stream)
        }
    }

    internal val renderSpecialClientUpdatePipe = net.client(3) { stream ->
        SpecialRendersRegistry.doRender(
            type = RenderSpecialType.valueOf(stream.int()),
            stream = stream,
        )
    }
}
