package com.impact.workspace.draft.comms

import com.impact.workspace.draft.comms.common.CommsWorldKey
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent

object CommsForgeEvents {

    @JvmStatic
    fun register() {
        FMLCommonHandler.instance().bus().register(CommsForgeEvents)
        MinecraftForge.EVENT_BUS.register(CommsForgeEvents)
    }

    @JvmStatic
    fun serverAboutToStart() {
        CommsServer.serverAboutToStart()
    }

    @JvmStatic
    fun onServerStarting() {
        CommsServer.serverStarting()
    }

    @JvmStatic
    fun onServerStopping() {
        CommsServer.serverStopping()
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        val world = event.world
        if (world.isRemote) return

        CommsServer.worldLoad(
            CommsWorldKey(world.provider.dimensionId)
        )
    }

    @SubscribeEvent
    fun onWorldUnload(event: WorldEvent.Unload) {
        val world = event.world
        if (world.isRemote) return

        CommsServer.worldUnload(
            CommsWorldKey(world.provider.dimensionId)
        )
    }

    @SubscribeEvent
    fun onServerTick(event: TickEvent.ServerTickEvent) {
        if (event.phase != TickEvent.Phase.END) return

        CommsServer.tick()
    }
}
