//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WE_OreGenSphere implements IWorldGenerator {
	public List<Integer> fieldMinY = new ArrayList<>();
	public List<Integer> fieldMaxY = new ArrayList<>();
	public List<Integer> fieldMinRadius = new ArrayList<>();
	public List<Integer> fieldMaxRadius = new ArrayList<>();
	public List<Integer> oreInMaxFieldDensity = new ArrayList<>();
	public List<Integer> chunksForField = new ArrayList<>();


	public List<WorldGenMinable> oreGen = new ArrayList<>();


	public void add(Block oreBlock, byte oreBlockMeta, Block replacingBlock, int oreBlockCountInLode, int p_fieldMinY, int p_fieldMaxY, int p_fieldMinRadius, int p_fieldMaxRadius, int p_oreInMaxFieldDensity, int p_chunksForField) {
		this.oreGen.add(new WorldGenMinable(oreBlock, oreBlockMeta, oreBlockCountInLode, replacingBlock));

		this.fieldMinY.add(p_fieldMinY);
		this.fieldMaxY.add(p_fieldMaxY);
		this.fieldMinRadius.add(p_fieldMinRadius);
		this.fieldMaxRadius.add(p_fieldMaxRadius);
		this.oreInMaxFieldDensity.add(p_oreInMaxFieldDensity);
		this.chunksForField.add(p_chunksForField);
	}


	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < this.oreGen.size(); i++) {
			if (random.nextInt(this.chunksForField.get(i)) == 0) {
				float s = 360.0F / this.oreInMaxFieldDensity.get(i);
				int sy = this.fieldMinY.get(i) + random.nextInt(this.fieldMaxY.get(i) - this.fieldMinY.get(i));
				int sx = chunkX * 16 + random.nextInt(16);
				int sz = chunkZ * 16 + random.nextInt(16);
				int r = this.fieldMinRadius.get(i) + random.nextInt(this.fieldMaxRadius.get(i) - this.fieldMinRadius.get(i) + 1);
				float v;
				for (v = -90.0F; v <= 90.0F; v += s) {
					float h;
					for (h = 0.0F; h <= 359.0F; h += s) {
						int rc = random.nextInt(r + 1);

						float px = MathHelper.cos(v * 3.1415927F / 180.0F) * MathHelper.sin(h * 3.1415927F / 180.0F);
						float pz = MathHelper.cos(v * 3.1415927F / 180.0F) * MathHelper.cos(h * 3.1415927F / 180.0F);
						float py = MathHelper.sin(v * 3.1415927F / 180.0F);
						this.oreGen.get(i).generate(world, random, sx +
								MathHelper.floor_float(px * rc), sy + MathHelper.floor_float(py * rc), sz + MathHelper.floor_float(pz * rc));
					}
				}
			}
		}
	}
}