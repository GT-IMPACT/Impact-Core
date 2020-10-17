package com.impact.mods.GalactiCraft.SolarSystem.planets.mars;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeGenBaseMars extends BiomeGenBase {
    public static final BiomeGenBase mars = (new BiomeGenBaseMars(51)).setBiomeName("Mars");


    private BiomeGenBaseMars(int var1) {
        super(var1);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.setColor(16711680);
        this.rainfall = 0.0F;
        this.setHeight(new Height(2.5F, 0.4F));
        this.temperature = 500.0F;
        if (!ConfigManagerCore.disableBiomeTypeRegistrations) {
            BiomeDictionary.registerBiomeType(this,BiomeDictionary.Type.COLD, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.SANDY);
        }
    }

    public BiomeGenBaseMars setColor(int var1) {
        return (BiomeGenBaseMars) super.setColor(var1);
    }
}