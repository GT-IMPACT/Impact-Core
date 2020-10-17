package com.impact.mods.GalactiCraft.SolarSystem.planets.jupiter;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseJupiter extends BiomeGenBase {
    public static final BiomeGenBase jupiter = (new BiomeGenBaseJupiter(50)).setBiomeName("Jupiter");


    private BiomeGenBaseJupiter(int var1) {
        super(var1);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.rainfall = 0.0F;
        this.temperature = 500.0F;
        if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
            BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SPOOKY);
        }
    }

    public BiomeGenBaseJupiter setColor(int var1) {
        return (BiomeGenBaseJupiter) super.setColor(var1);
    }

    public float getSpawningChance() {
        return 0.01F;
    }
}