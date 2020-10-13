//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen.help;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class WE_BigTreeGen extends WorldGenerator {
	public Block bWood = Blocks.log;
	public Block bLeaves = Blocks.leaves;
	public Block bSapling = Blocks.sapling;

	public byte[] otherCoordPairs = new byte[]{2, 0, 0, 1, 2, 1};
	public int trunkSize = 1;
	public int heightLimitLimit = 12;
	public int leafDistanceLimit = 4;
	public int metaLeaves = 0;
	public double heightAttenuation = 0.618D;
	public double branchSlope = 0.381D;
	public double scaleWidth = 1.0D;
	public double leafDensity = 1.0D;

	public List<Block> ab = new ArrayList<>();
	public List<Material> am = new ArrayList<>();

	public WE_BigTreeGen(boolean doBlockNotify) {
		super(doBlockNotify);

		this.ab.add(Blocks.grass);
		this.ab.add(Blocks.dirt);
		this.ab.add(Blocks.log);
		this.ab.add(Blocks.log2);
		this.ab.add(Blocks.sapling);
		this.ab.add(Blocks.vine);

		this.am.add(Material.air);
		this.am.add(Material.leaves);
	}

	public boolean cb(Block b) {
		int i;
		for (i = 0; i < this.ab.size(); i++) {
			if (b == this.ab.get(i))
				return true;
		}
		for (i = 0; i < this.am.size(); i++) {
			if (b.getMaterial() == this.am.get(i))
				return true;
		}
		return false;
	}

	public boolean isReplaceable(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		return (b.isAir(world, x, y, z) || b.isLeaves(world, x, y, z) || b.isWood(world, x, y, z) || cb(b));
	}

	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
		boolean br;
		int heightLimit = 5 + p_76484_2_.nextInt(this.heightLimitLimit);
		int[] basePos = {p_76484_3_, p_76484_4_, p_76484_5_};


		int[] _aint = {basePos[0], basePos[1], basePos[2]};
		int[] _aint1 = {basePos[0], basePos[1] + heightLimit - 1, basePos[2]};
		Block block = p_76484_1_.getBlock(basePos[0], basePos[1] - 1, basePos[2]);


		if (!block.canSustainPlant(p_76484_1_, basePos[0], basePos[1] - 1, basePos[2], ForgeDirection.UP, (IPlantable) this.bSapling)) {
			br = false;
		} else {
			int m = checkBlockLine(p_76484_1_, _aint, _aint1);
			if (m == -1) {
				br = true;
			} else if (m < 6) {
				br = false;
			} else {
				heightLimit = m;
				br = true;
			}
		}

		if (!br) {
			return false;
		}
		int height = (int) (heightLimit * this.heightAttenuation);
		if (height >= heightLimit) {
			height = heightLimit - 1;
		}
		int i = (int) (1.382D + Math.pow(this.leafDensity * heightLimit / 13.0D, 2.0D));
		if (i < 1) {
			i = 1;
		}
		int[][] aint = new int[i * heightLimit][4];
		int j = basePos[1] + heightLimit - this.leafDistanceLimit, k = 1, l = basePos[1] + height, i1 = j - basePos[1];
		aint[0][0] = basePos[0];
		aint[0][1] = j;
		aint[0][2] = basePos[2];
		aint[0][3] = l;
		j--;

		while (i1 >= 0) {
			float f2;
			int j1 = 0;


			if (i1 < heightLimit * 0.3D) {
				f2 = -1.618F;
			} else {
				float f = heightLimit / 2.0F, f1 = heightLimit / 2.0F - i1;
				if (f1 == 0.0F) {
					f2 = f;
				} else if (Math.abs(f1) >= f) {
					f2 = 0.0F;
				} else {
					f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
				}
				f2 *= 0.5F;
			}

			if (f2 < 0.0F) {
				j--;
				i1--;
				continue;
			}
			for (double d0 = 0.5D; j1 < i; j1++) {
				double d1 = this.scaleWidth * f2 * (p_76484_2_.nextFloat() + 0.328D), d2 = p_76484_2_.nextFloat() * 2.0D * Math.PI;
				int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + basePos[0] + d0);
				int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + basePos[2] + d0);
				int[] aint1 = {k1, j, l1}, aint2 = {k1, j + this.leafDistanceLimit, l1};

				if (checkBlockLine(p_76484_1_, aint1, aint2) == -1) {
					int[] aint3 = {basePos[0], basePos[1], basePos[2]};
					double d3 = Math.sqrt(Math.pow(Math.abs(basePos[0] - aint1[0]), 2.0D) +
							Math.pow(Math.abs(basePos[2] - aint1[2]), 2.0D));
					double d4 = d3 * this.branchSlope;

					if (aint1[1] - d4 > l) {
						aint3[1] = l;
					} else {
						aint3[1] = (int) (aint1[1] - d4);
					}
					if (checkBlockLine(p_76484_1_, aint3, aint1) == -1) {
						aint[k][0] = k1;
						aint[k][1] = j;
						aint[k][2] = l1;
						aint[k][3] = aint3[1];
						k++;
					}
				}
			}

			j--;
			i1--;
		}


		int[][] leafNodes = new int[k][4];
		System.arraycopy(aint, 0, leafNodes, 0, k);

		int i_1 = 0;
		for (int j_1 = leafNodes.length; i_1 < j_1; i_1++) {
			int l_2 = leafNodes[i_1][1];
			for (int i1_2 = leafNodes[i_1][1] + this.leafDistanceLimit; l_2 < i1_2; l_2++) {
				float p1 = (l_2 - leafNodes[i_1][1]), p2 = (p1 >= 0.0F && p1 < this.leafDistanceLimit) ? ((p1 != 0.0F && p1 != (this.leafDistanceLimit - 1)) ? 3.0F : 2.0F) : -1.0F;
				byte b1_3 = this.otherCoordPairs[1], b2_3 = this.otherCoordPairs[4];
				int[] aint_3 = {leafNodes[i_1][0], l_2, leafNodes[i_1][2]}, aint1_3 = {0, 0, 0};
				int l_3 = (int) (p2 + 0.618D), i1_3 = -l_3, j1_3;

				for (aint1_3[1] = aint_3[1]; i1_3 <= l_3; i1_3++) {
					aint1_3[b1_3] = aint_3[b1_3] + i1_3;
					j1_3 = -l_3;
					while (j1_3 <= l_3) {
						double d0_3 = Math.pow(Math.abs(i1_3) + 0.5D, 2.0D) + Math.pow(Math.abs(j1_3) + 0.5D, 2.0D);
						if (d0_3 > (p2 * p2)) {
							j1_3++;
							continue;
						}
						aint1_3[b2_3] = aint_3[b2_3] + j1_3;
						Block block1 = p_76484_1_.getBlock(aint1_3[0], aint1_3[1], aint1_3[2]);
						if (!block1.isAir(p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2]) &&
								!block1.isLeaves(p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2])) {
							j1_3++;
							continue;
						}
						setBlockAndNotifyAdequately(p_76484_1_, aint1_3[0], aint1_3[1], aint1_3[2], this.bLeaves, this.metaLeaves);
						j1_3++;
					}
				}
			}
		}


		int[] aint_4 = {basePos[0], basePos[1], basePos[2]};
		int[] aint1_4 = {basePos[0], basePos[1] + height, basePos[2]};
		func_150530_a(p_76484_1_, aint_4, aint1_4, this.bWood);

		if (this.trunkSize == 2) {
			aint_4[0] = aint_4[0] + 1;
			aint1_4[0] = aint1_4[0] + 1;
			func_150530_a(p_76484_1_, aint_4, aint1_4, this.bWood);

			aint_4[2] = aint_4[2] + 1;
			aint1_4[2] = aint1_4[2] + 1;
			func_150530_a(p_76484_1_, aint_4, aint1_4, this.bWood);

			aint_4[0] = aint_4[0] - 1;
			aint1_4[0] = aint1_4[0] - 1;
			func_150530_a(p_76484_1_, aint_4, aint1_4, this.bWood);
		}

		int i_5 = 0;
		for (int[] aint_5 = {basePos[0], basePos[1], basePos[2]}; i_5 < leafNodes.length; i_5++) {
			int[] aint1_5 = leafNodes[i_5], aint2_5 = {aint1_5[0], aint1_5[1], aint1_5[2]};
			aint_5[1] = aint1_5[3];

			int k_5 = aint_5[1] - basePos[1];
			if (k_5 >= heightLimit * 0.2D) {
				func_150530_a(p_76484_1_, aint_5, aint2_5, this.bWood);
			}
		}
		return true;
	}


	public void func_150530_a(World worldObj, int[] p_150530_1_, int[] p_150530_2_, Block p_150530_3_) {
		int[] aint2 = {0, 0, 0};
		byte b0 = 0;
		byte b1;
		for (b1 = 0; b0 < 3; b0 = (byte) (b0 + 1)) {
			aint2[b0] = p_150530_2_[b0] - p_150530_1_[b0];
			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
				b1 = b0;
			}
		}
		if (aint2[b1] != 0) {
			byte b4, b2 = this.otherCoordPairs[b1], b3 = this.otherCoordPairs[b1 + 3];
			if (aint2[b1] > 0) {
				b4 = 1;
			} else {
				b4 = -1;
			}
			double d0 = (double) aint2[b2] / aint2[b1], d1 = (double) aint2[b3] / aint2[b1];
			int[] aint3 = {0, 0, 0};
			int i = 0;

			for (int j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = MathHelper.floor_double((p_150530_1_[b1] + i) + 0.5D);
				aint3[b2] = MathHelper.floor_double(p_150530_1_[b2] + i * d0 + 0.5D);
				aint3[b3] = MathHelper.floor_double(p_150530_1_[b3] + i * d1 + 0.5D);

				byte b5 = 0;
				int k = Math.abs(aint3[0] - p_150530_1_[0]), l = Math.abs(aint3[2] - p_150530_1_[2]), i1 = Math.max(k, l);

				if (i1 > 0)
					if (k == i1) {
						b5 = 4;
					} else if (l == i1) {
						b5 = 8;
					}
				setBlockAndNotifyAdequately(worldObj, aint3[0], aint3[1], aint3[2], p_150530_3_, b5);
			}
		}
	}

	public int checkBlockLine(World worldObj, int[] p_76496_1_, int[] p_76496_2_) {
		byte b4;
		int[] aint2 = {0, 0, 0};
		byte b0 = 0;
		byte b1;
		for (b1 = 0; b0 < 3; b0 = (byte) (b0 + 1)) {
			aint2[b0] = p_76496_2_[b0] - p_76496_1_[b0];
			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
				b1 = b0;
			}
		}
		if (aint2[b1] == 0) {
			return -1;
		}
		byte b2 = this.otherCoordPairs[b1], b3 = this.otherCoordPairs[b1 + 3];
		if (aint2[b1] > 0) {
			b4 = 1;
		} else {
			b4 = -1;
		}
		double d0 = (double) aint2[b2] / aint2[b1], d1 = (double) aint2[b3] / aint2[b1];
		int[] aint3 = {0, 0, 0};
		int i = 0;
		int j;
		for (j = aint2[b1] + b4; i != j; i += b4) {
			aint3[b1] = p_76496_1_[b1] + i;
			aint3[b2] = MathHelper.floor_double(p_76496_1_[b2] + i * d0);
			aint3[b3] = MathHelper.floor_double(p_76496_1_[b3] + i * d1);

			Block block = worldObj.getBlock(aint3[0], aint3[1], aint3[2]);

			if (!isReplaceable(worldObj, aint3[0], aint3[1], aint3[2])) {
				break;
			}
		}
		return (i == j) ? -1 : Math.abs(i);
	}
}