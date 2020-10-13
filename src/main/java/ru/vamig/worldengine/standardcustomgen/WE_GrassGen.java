//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.*;

public class WE_GrassGen implements IWorldGenerator {
	public List<Block> flowerList = new ArrayList<>();
	public List<Byte> flowerMetaList = new ArrayList<>();
	public List<Integer> blocksForFlower = new ArrayList<>();

	public List<Boolean> waterGen = new ArrayList<>();
	public List<Block> surface = new ArrayList<>();
	public List<Byte> surfaceMeta = new ArrayList<>();

	public void add(Block f, byte fm, int bff, boolean wg, Block s, byte sm) {
		this.flowerList.add(f);
		this.flowerMetaList.add(fm);
		this.blocksForFlower.add(bff);

		this.waterGen.add(wg);
		this.surface.add(s);
		this.surfaceMeta.add(sm);
	}


	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < this.flowerList.size(); i++) {
			if (random.nextInt(this.blocksForFlower.get(i)) <= 255)
				for (int i2 = 0; i2 < MathHelper.ceiling_float_int(256.0F / this.blocksForFlower.get(i)); i2++) {
					int x = chunkX * 16 + random.nextInt(16);
					int z = chunkZ * 16 + random.nextInt(16);
					int y = world.getHeightValue(x, z);

					if (this.waterGen.get(i)) {
						if (world.getBlock(x, y - 1, z).getMaterial().isLiquid()) {
							y--;

							while (world.getBlock(x, y - 1, z).getMaterial().isLiquid()) {
								y--;
							}
							if (world.getBlock(x, y - 1, z) == this.surface.get(i) && world.getBlockMetadata(x, y - 1, z) == this.surfaceMeta.get(i)) {
								world.setBlock(x, y, z, this.flowerList.get(i), this.flowerMetaList.get(i), 2);
							}
						}

					} else if (world.getBlock(x, y - 1, z) == this.surface.get(i) && world.getBlockMetadata(x, y - 1, z) == this.surfaceMeta.get(i)) {
						world.setBlock(x, y, z, this.flowerList.get(i), this.flowerMetaList.get(i), 2);
					}
				}
		}
	}
}