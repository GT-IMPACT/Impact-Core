package com.impact.mods.gregtech.tileentities.basic

import net.minecraft.entity.Entity
import net.minecraft.profiler.Profiler
import net.minecraft.world.World
import net.minecraft.world.WorldProviderSurface
import net.minecraft.world.WorldSettings
import net.minecraft.world.WorldType
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.storage.SaveHandlerMP
import org.junit.jupiter.api.Assertions.assertEquals

internal class DebugTankTestWorld : World(
    SaveHandlerMP(),
    "debug-tank-test",
    WorldProviderSurface(),
    WorldSettings(
        0L,
        WorldSettings.GameType.CREATIVE,
        false,
        false,
        WorldType.DEFAULT,
    ),
    Profiler(),
) {

    val tankChunk: Chunk = Chunk(
        this,
        0,
        0,
    )

    override fun getChunkFromBlockCoords(
        x: Int,
        z: Int,
    ): Chunk {
        assertEquals(
            0,
            x,
        )
        assertEquals(
            0,
            z,
        )
        return tankChunk
    }

    override fun createChunkProvider(): IChunkProvider {
        throw UnsupportedOperationException("The test world only exposes the tank's chunk")
    }

    override fun getEntityByID(id: Int): Entity? {
        return null
    }

    override fun func_152379_p(): Int {
        return 0
    }
}
