//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.vamig.worldengine.standardcustomgen.help.*;

import java.util.*;

public class WE_WorldTreeGen implements IWorldGenerator {
	public List<WE_TreeGen> ug = new ArrayList<>();
	public List<WE_BigTreeGen> bg = new ArrayList<>();

	public List<Integer> cwt = new ArrayList<>(), tfc = new ArrayList<>(), tfb = new ArrayList<>();


	public void add(Block bWood, int mWood, Block bLeaves, int mLeaves, Block bSapling, Block bVine, Block bCocoa, int v1, int v2, int v3, int minHeight, boolean vines, byte ocp1, byte ocp2, byte ocp3, byte ocp4, byte ocp5, byte ocp6, int ts, int hl, int ldl, double ha, double bs, double sw, double ld) {
		this.ug.add(new WE_TreeGen(false));
		this.bg.add(new WE_BigTreeGen(false));

		this.cwt.add(v1);
		this.tfc.add(v2);
		this.tfb.add(v3);

		this.ug.get(this.ug.size() - 1).bWood = bWood;
		this.ug.get(this.ug.size() - 1).metaWood = mWood;
		this.ug.get(this.ug.size() - 1).bLeaves = bLeaves;
		this.ug.get(this.ug.size() - 1).metaLeaves = mLeaves;
		this.ug.get(this.ug.size() - 1).bSapling = bSapling;
		this.ug.get(this.ug.size() - 1).bVine = bVine;
		this.ug.get(this.ug.size() - 1).bCocoa = bCocoa;

		this.bg.get(this.bg.size() - 1).bWood = bWood;
		this.bg.get(this.bg.size() - 1).bLeaves = bLeaves;
		this.bg.get(this.bg.size() - 1).metaLeaves = mLeaves;
		this.bg.get(this.bg.size() - 1).bSapling = bSapling;

		this.ug.get(this.ug.size() - 1).minTreeHeight = minHeight;
		this.ug.get(this.ug.size() - 1).vinesGrow = vines;

		this.bg.get(this.bg.size() - 1).otherCoordPairs = new byte[]{ocp1, ocp2, ocp3, ocp4, ocp5, ocp6};
		this.bg.get(this.bg.size() - 1).trunkSize = ts;
		this.bg.get(this.bg.size() - 1).heightLimitLimit = hl;
		this.bg.get(this.bg.size() - 1).leafDistanceLimit = ldl;
		this.bg.get(this.bg.size() - 1).heightAttenuation = ha;
		this.bg.get(this.bg.size() - 1).branchSlope = bs;
		this.bg.get(this.bg.size() - 1).scaleWidth = sw;
		this.bg.get(this.bg.size() - 1).leafDensity = ld;
	}

	public void add(Block bWood, int mWood, Block bLeaves, int mLeaves, Block bSapling, Block bVine, Block bCocoa, int v1, int v2, int minHeight, boolean vines) {
		this.ug.add(new WE_TreeGen(false));
		this.bg.add(null);

		this.cwt.add(v1);
		this.tfc.add(v2);
		this.tfb.add(0);

		this.ug.get(this.ug.size() - 1).bWood = bWood;
		this.ug.get(this.ug.size() - 1).metaWood = mWood;
		this.ug.get(this.ug.size() - 1).bLeaves = bLeaves;
		this.ug.get(this.ug.size() - 1).metaLeaves = mLeaves;
		this.ug.get(this.ug.size() - 1).bSapling = bSapling;
		this.ug.get(this.ug.size() - 1).bVine = bVine;
		this.ug.get(this.ug.size() - 1).bCocoa = bCocoa;

		this.ug.get(this.ug.size() - 1).minTreeHeight = minHeight;
		this.ug.get(this.ug.size() - 1).vinesGrow = vines;
	}


	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < this.ug.size(); i++) {
			if (random.nextInt(this.cwt.get(i)) == 0)
				for (int i2 = 0; i2 < this.tfc.get(i); i2++) {
					int x = chunkX * 16 + random.nextInt(16);
					int z = chunkZ * 16 + random.nextInt(16);
					int y = world.getHeightValue(x, z);

					if (this.tfb.get(i) <= 0) {
						this.ug.get(i).generate(world, random, x, y, z);
					} else if (random.nextInt(this.tfb.get(i)) == 0) {
						this.bg.get(i).generate(world, random, x, y, z);
					} else {
						this.ug.get(i).generate(world, random, x, y, z);
					}
				}
		}
	}
}