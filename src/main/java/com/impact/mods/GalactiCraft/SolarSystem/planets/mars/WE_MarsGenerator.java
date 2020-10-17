package com.impact.mods.GalactiCraft.SolarSystem.planets.mars;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.vamig.worldengine.WE_PerlinNoise;
import ru.vamig.worldengine.additions.WE_CreateChunkGen;
import ru.vamig.worldengine.additions.WE_GeneratorData;

import java.util.Random;

import static com.impact.loader.ItemRegistery.MarsStone;
import static net.minecraft.init.Blocks.bedrock;
import static net.minecraft.init.Blocks.ice;


public class WE_MarsGenerator extends WE_CreateChunkGen {

    public void gen(WE_GeneratorData data) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                double n = WE_PerlinNoise.PerlinNoise2D(data.chunkProvider.worldObj.getSeed(), (data.chunk_X + x) / 150.0D, (data.chunk_Z + z) / 150.0D, 2.1D, 3);
                for (int y = 0; y < 256; y++) {
                    if (y == 0)
                        setBlock(data, bedrock, (byte) 0, x, y, z);
                    else if (y <= 120 + MathHelper.floor_double(n * 1.8D))
                        setBlock(data, MarsStone, (byte) 0, x, y, z);
                    else if (y <= 112)
                        setBlock(data, ice, (byte) 0, x, y, z);
                }
            }
        }
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
    }

}

