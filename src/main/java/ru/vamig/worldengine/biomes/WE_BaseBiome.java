package ru.vamig.worldengine.biomes;

import cpw.mods.fml.common.IWorldGenerator;
import ru.vamig.worldengine.WE_Biome;
import ru.vamig.worldengine.standardcustomgen.WE_BiomeLayer;


import java.util.Arrays;


public class WE_BaseBiome extends WE_Biome {
    public WE_BaseBiome(double min, double max, double persistance, int octaves, int height, int intquility, WE_BiomeLayer layers, IWorldGenerator... gens) {
        super(0, false);

        this.biomeMinValueOnMap = min;
        this.biomeMaxValueOnMap = max;
        this.biomePersistence = persistance;
        this.biomeNumberOfOctaves = octaves;
        this.biomeScaleX = 280.0D;
        this.biomeScaleY = 1.5D;
        this.biomeSurfaceHeight = height;
        this.biomeInterpolateQuality = intquility;


        this.decorateChunkGen_List.clear();
        this.createChunkGen_InXZ_List.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();

        this.createChunkGen_InXZ_List.add(layers);

        this.decorateChunkGen_List.addAll(Arrays.asList(gens));
    }

    public WE_BaseBiome addChunkGenXZ() {
        return this;
    }
}
