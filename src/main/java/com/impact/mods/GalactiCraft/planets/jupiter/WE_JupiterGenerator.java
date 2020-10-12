package com.impact.mods.GalactiCraft.planets.jupiter;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.vamig.worldengine.WE_PerlinNoise;
import ru.vamig.worldengine.additions.WE_CreateChunkGen;
import ru.vamig.worldengine.additions.WE_GeneratorData;

import java.util.Random;

import static com.impact.loader.ItemRegistery.*;
import static net.minecraft.init.Blocks.bedrock;


public class WE_JupiterGenerator extends WE_CreateChunkGen {

    public void gen(WE_GeneratorData data) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double n = WE_PerlinNoise.PerlinNoise2D(data.chunkProvider.worldObj.getSeed(), (data.chunk_X + x) / 16.0D, (data.chunk_Z + z) / 16.0D, 2.1D, 3);
                double m = WE_PerlinNoise.PerlinNoise2D(data.chunkProvider.worldObj.getSeed(), (data.chunk_X + x) / 16.0D, (data.chunk_Z + z) / 16.0D, 1.4D, 3);

                for (int y = 0; y < 256; y++) {
                    if (y == 0)
                        setBlock(data, bedrock, (byte) 0, x, y, z);
                    else if (y <= 70 + MathHelper.floor_double(n))
                        setBlock(data, HMetal, (byte) 0, x, y, z);
                    else if (y <= 101)
                        setBlock(data, HFluid, (byte) 0, x, y, z);
                    else if (y >= 246 && y <= 254 && y >= 248 + MathHelper.floor_double(m * 2.5D) && y <= 252 - MathHelper.floor_double(m * 2.0D))
                        setBlock(data, HCloud, (byte) 0, x, y, z);
                }
            }
        }
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
    }
}

