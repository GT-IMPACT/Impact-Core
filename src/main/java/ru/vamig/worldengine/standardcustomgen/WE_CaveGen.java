//- By Vamig Aliev.
//- https://vk.com/win_vista.

package ru.vamig.worldengine.standardcustomgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import ru.vamig.worldengine.additions.WE_CreateChunkGen;
import ru.vamig.worldengine.additions.WE_GeneratorData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WE_CaveGen extends WE_CreateChunkGen {
    public List<Block> replaceBlocksList = new ArrayList<>();
    public List<Byte> replaceBlocksMetaList = new ArrayList<>();

    public Block caveBlock = null;
    public byte caveBlockMeta = 0;

    public Block lavaBlock = Blocks.lava;
    public byte lavaBlockMeta = 0;
    public int lavaMaxY = 12;

    public int range = 8;
    Random rand = new Random();

    public WE_CaveGen() {
        this.replaceBlocksList.add(Blocks.stone);
        this.replaceBlocksMetaList.add((byte) 0);
    }


    public void gen(WE_GeneratorData data) {
        this.rand.setSeed(data.chunkProvider.worldObj.getSeed());
        long rx = this.rand.nextLong(), rz = this.rand.nextLong();
        long cx;
        for (cx = data.chunk_X / 16L - this.range; cx <= data.chunk_X / 16L + this.range; cx++) {
            long cz;
            for (cz = data.chunk_Z / 16L - this.range; cz <= data.chunk_Z / 16L + this.range; cz++) {
                long nv1 = cx * rx, nv2 = cz * rz;
                this.rand.setSeed(nv1 ^ nv2 ^ data.chunkProvider.worldObj.getSeed());

                int e = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
                if (this.rand.nextInt(7) != 0) {
                    e = 0;
                }
                for (int i = 0; i < e; i++) {

                    double gx = cx * 16.0D + this.rand.nextInt(16);
                    double gy = this.rand.nextInt(this.rand.nextInt(120) + 8);
                    double gz = cz * 16.0D + this.rand.nextInt(16);

                    int k = 1;
                    if (this.rand.nextInt(4) == 0) {
                        caveGen_func(data, this.rand.nextLong(), gx, gy, gz, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
                        k += this.rand.nextInt(4);
                    }

                    for (int l1 = 0; l1 < k; l1++) {

                        float x = this.rand.nextFloat() * 2.0F * 3.1415927F;
                        float x1 = (this.rand.nextFloat() - 0.5F) * 0.25F;
                        float x2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
                        if (this.rand.nextInt(10) == 0) {
                            x2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                        }
                        caveGen_func(data, this.rand.nextLong(), gx, gy, gz, x2, x, x1, 0, 0, 1.0D);
                    }
                }
            }
        }
    }

    public void caveGen_func(WE_GeneratorData data, long rs, double gx, double gy, double gz, float fn1, float fn2, float fn3, int in1, int in2, double dn) {
        double cx = data.chunk_X + 8.0D, cz = data.chunk_Z + 8.0D;
        float f3 = 0.0F, f4 = 0.0F;
        Random r = new Random(rs);

        if (in2 <= 0) {
            int p = this.range * 16 - 16;
            in2 = p - r.nextInt(p / 4);
        }

        boolean flag2 = false;
        if (in1 == -1) {
            in1 = in2 / 2;
            flag2 = true;
        }

        int k1 = r.nextInt(in2 / 2) + in2 / 4;
        for (boolean flag = (r.nextInt(6) == 0); in1 < in2; in1++) {
            double d6 = 1.5D + MathHelper.sin(in1 * 3.1415927F / in2) * fn1, d7 = d6 * dn;
            float f5 = MathHelper.cos(fn3), f6 = MathHelper.sin(fn3);
            gx += MathHelper.cos(fn2) * f5;
            gy += f6;
            gz += MathHelper.sin(fn2) * f5;

            if (flag) {
                fn3 *= 0.92F;
            } else {
                fn3 *= 0.7F;
            }
            fn3 += f4 * 0.1F;
            fn2 += f3 * 0.1F;

            f4 *= 0.9F;
            f3 *= 0.75F;
            f4 += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 2.0F;
            f3 += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 4.0F;

            if (!flag2 && in1 == k1 && fn1 > 1.0F && in2 > 0) {
                caveGen_func(data, r.nextLong(), gx, gy, gz, r.nextFloat() * 0.5F + 0.5F, fn2 - 1.5707964F, fn3 / 3.0F, in1, in2, 1.0D);
                caveGen_func(data, r.nextLong(), gx, gy, gz, r.nextFloat() * 0.5F + 0.5F, fn2 + 1.5707964F, fn3 / 3.0F, in1, in2, 1.0D);

                return;
            }
            if (flag2 || r.nextInt(4) != 0) {
                double d8 = gx - cx, d9 = gz - cz, d10 = in2 - in1, d11 = fn1 + 18.0D;
                if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
                    return;
                }
                if (gx >= cx - 16.0D - d6 * 2.0D && gz >= cz - 16.0D - d6 * 2.0D && gx <= cx + 16.0D + d6 * 2.0D && gz <= cz + 16.0D + d6 * 2.0D) {
                    int i4 = MathHelper.floor_double(gx - d6) - (int) data.chunk_X - 1, l1 = MathHelper.floor_double(gx + d6) - (int) data.chunk_X + 1;
                    int j4 = MathHelper.floor_double(gy - d7) - 1, i2 = MathHelper.floor_double(gy + d7) + 1;
                    int k4 = MathHelper.floor_double(gz - d6) - (int) data.chunk_Z - 1, j2 = MathHelper.floor_double(gz + d6) - (int) data.chunk_Z + 1;

                    if (i4 < 0)
                        i4 = 0;
                    if (l1 > 16)
                        l1 = 16;
                    if (j4 < 1)
                        j4 = 1;
                    if (i2 > 248)
                        i2 = 248;
                    if (k4 < 0)
                        k4 = 0;
                    if (j2 > 16) {
                        j2 = 16;
                    }
                    boolean flag3 = false;

                    int k2;
                    for (k2 = i4; !flag3 && k2 < l1; k2++) {
                        for (int l2 = k4; !flag3 && l2 < j2; l2++) {
                            for (int i3 = i2 + 1; !flag3 && i3 >= j4 - 1; i3--) {
                                int j3 = (k2 * 16 + l2) * 256 + i3;
                                if (i3 < 256) {
                                    if (isOceanBlock(data, j3, k2, i3, l2))
                                        flag3 = true;
                                    if (i3 != j4 - 1 && k2 != i4 && k2 != l1 - 1 && l2 != k4 && l2 != j2 - 1)
                                        i3 = j4;
                                }
                            }
                        }
                    }
                    if (!flag3) {
                        for (k2 = i4; k2 < l1; k2++) {
                            double d13 = (k2 + data.chunk_X + 0.5D - gx) / d6;
                            for (int j3 = k4; j3 < j2; j3++) {
                                double d14 = (j3 + data.chunk_Z + 0.5D - gz) / d6;
                                int k3 = (k2 * 16 + j3) * 256 + i2;
                                if (d13 * d13 + d14 * d14 < 1.0D) {
                                    for (int l3 = i2 - 1; l3 >= j4; l3--) {
                                        double d12 = (l3 + 0.5D - gy) / d7;
                                        if (d12 > -0.7D && d13 * d13 + d12 * d12 + d14 * d14 < 1.0D)
                                            digBlock(data, k3, k2, l3, j3);
                                        k3--;
                                    }
                                }
                            }
                        }
                        if (flag2)
                            break;
                    }
                }
            }
        }
    }

    public void digBlock(WE_GeneratorData data, int index, int x, int y, int z) {
        if (data.chunkBlocks[index] instanceof net.minecraft.block.BlockFalling && data.chunkBlocks[index + 1] instanceof net.minecraft.block.BlockFalling) {
            return;
        }
        for (int i = 0; i < this.replaceBlocksList.size(); i++) {
            if (data.chunkBlocks[index] == this.replaceBlocksList.get(i) && data.chunkBlocksMeta[index] == this.replaceBlocksMetaList.get(i)) {
                if (y <= this.lavaMaxY) {
                    data.chunkBlocks[index] = this.lavaBlock;
                    data.chunkBlocksMeta[index] = this.lavaBlockMeta;
                    break;
                }
                data.chunkBlocks[index] = this.caveBlock;
                data.chunkBlocksMeta[index] = this.caveBlockMeta;
                break;
            }
        }
    }

    public boolean isOceanBlock(WE_GeneratorData data, int index, int x, int y, int z) {
        if (data.chunkBlocks[index] != null) {
            return data.chunkBlocks[index].getMaterial().isLiquid();
        }
        return false;
    }

    public void addReplacingBlock(Block block, byte meta) {
        this.replaceBlocksList.add(block);
        this.replaceBlocksMetaList.add(meta);
    }
}