//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine;

import galaxyspace.core.world.GSWorldProviderSpace;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class WE_WorldProvider extends GSWorldProviderSpace {

    public final int we_id = 49;
    public final float rainfall = 0.0F;

    @Override
    public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new WE_Biome(this.we_id, true), this.rainfall);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        WE_ChunkProvider m = new WE_ChunkProvider(this);
        return m;
    }

    public abstract void genSettings(WE_ChunkProvider cp);
}