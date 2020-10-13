//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WE_LakeGen implements IWorldGenerator {
    public Block lakeBlock = Blocks.water;
    public Block lakeBlock_f = Blocks.ice;
    public byte lakeBlockMeta = 0;
    public byte lakeBlockMeta_f = 0;
    public int chunksForLake = 12;
    public int minY = 0;
    public int maxY = 255;
    public int fY = 111;
    public int random_fY = 2;

    public boolean fGen = true, u = true;

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextInt(this.chunksForLake) == 0) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int y = this.minY + random.nextInt(this.maxY - this.minY + 1);

            lake(world, random, x, y, z);
        }
    }

    public void lake(World world, Random random, int x, int y, int z) {
        x -= 8;
        z -= 8;
        while (y > 5 && world.isAirBlock(x, y, z)) {
            y--;
        }
        if (y <= 4) {
            return;
        }
        y -= 4;

        boolean[] aboolean = new boolean[2048];
        for (int w = 0; w < random.nextInt(4) + 4; w++) {

            double d0 = random.nextDouble() * 6.0D + 3.0D;
            double d1 = random.nextDouble() * 4.0D + 2.0D;
            double d2 = random.nextDouble() * 6.0D + 3.0D;
            double d3 = random.nextDouble() * (14.0D - d0) + 1.0D + d0 / 2.0D;
            double d4 = random.nextDouble() * (4.0D - d1) + 2.0D + d1 / 2.0D;
            double d5 = random.nextDouble() * (14.0D - d2) + 1.0D + d2 / 2.0D;

            for (int i = 1; i < 15; i++) {
                for (int bz = 1; bz < 15; bz++) {
                    for (int by = 1; by < 7; by++) {

                        double d6 = (i - d3) * 2.0D / d0;
                        double d7 = (by - d4) * 2.0D / d1;
                        double d8 = (bz - d5) * 2.0D / d2;
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                        if (d9 < 1.0D)
                            aboolean[(i * 16 + bz) * 8 + by] = true;
                    }
                }
            }
        }
        for (int bx = 0; bx < 16; bx++) {
            for (int bz = 0; bz < 16; bz++) {
                for (int by = 0; by < 8; by++) {
                    if (!aboolean[(bx * 16 + bz) * 8 + by] && ((bx < 15 && aboolean[((bx + 1) * 16 + bz) * 8 + by]) || (bx > 0 && aboolean[((bx - 1) * 16 + bz) * 8 + by]) || (bz < 15 && aboolean[(bx * 16 + bz + 1) * 8 + by]) || (bz > 0 && aboolean[(bx * 16 + bz - 1) * 8 + by]) || (by < 7 && aboolean[(bx * 16 + bz) * 8 + by + 1]) || (by > 0 && aboolean[(bx * 16 + bz) * 8 + by - 1]))) {


                        Material material = world.getBlock(x + bx, y + by, z + bz).getMaterial();
                        if (by >= 4 && material.isLiquid())
                            return;
                        if (by < 4 && !material.isSolid() && (world
                                .getBlock(x + bx, y + by, z + bz) != this.lakeBlock || world.getBlockMetadata(x + bx, y + by, z + bz) != this.lakeBlockMeta)) {
                            return;
                        }
                    }
                    if (aboolean[(bx * 16 + bz) * 8 + by])
                        world.setBlock(x + bx, y + by, z + bz, (by >= 4) ? Blocks.air : this.lakeBlock, this.lakeBlockMeta, this.u ? 3 : 2);
                }
                if (y + 4 >= this.fY + random.nextInt(this.random_fY + 1))
                    world.setBlock(x + bx, y + 4, z + bz, this.lakeBlock_f, this.lakeBlockMeta_f, this.u ? 3 : 2);
            }
        }
    }
}
