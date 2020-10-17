//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine;

import com.impact.mods.GalactiCraft.GC_Config;
import galaxyspace.core.world.GSWorldProviderSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public abstract class WE_WorldProvider extends GSWorldProviderSpace {
    float rainfall = 0.1F;


    public void registerWorldChunkManager() {
        System.out.println("WorldEngine: -Registering WorldEngine...");
        this.worldChunkMgr = new WorldChunkManagerHell(new WE_Biome(GC_Config.IDWorldEngineBiome, true), this.rainfall);
        System.out.println("WorldEngine: -Registration completed successfully!");
    }


    public IChunkProvider createChunkGenerator() {
        System.out.println("WorldEngine: -Starting WorldEngine...");
        WE_ChunkProvider m = new WE_ChunkProvider(this);
        System.out.println("WorldEngine: -WorldEngine started successfully!");

        return m;
    }

    public abstract void genSettings(WE_ChunkProvider paramWE_ChunkProvider);

    public abstract BiomeDecoratorSpace getDecorator();
}