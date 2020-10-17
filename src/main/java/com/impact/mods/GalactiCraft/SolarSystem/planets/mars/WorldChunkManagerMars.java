package com.impact.mods.GalactiCraft.SolarSystem.planets.mars;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldChunkManagerSpace;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldChunkManagerMars extends WorldChunkManagerSpace {

    @Override
    public BiomeGenBase getBiome() {
        return BiomeGenBaseMars.mars;
    }
}
