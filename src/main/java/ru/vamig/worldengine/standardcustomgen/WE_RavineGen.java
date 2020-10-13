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

public class WE_RavineGen extends WE_CreateChunkGen {
    public List<Block> replaceBlocksList = new ArrayList<>();
    public List<Byte> replaceBlocksMetaList = new ArrayList<>();

    public Block caveBlock = null;
    public byte caveBlockMeta = 0;

    public Block lavaBlock = Blocks.lava;
    public byte lavaBlockMeta = 0;
    public int lavaMaxY = 12;

    public int range = 8;
    public float[] field_75046_d = new float[1024];
    Random rand = new Random();

    public WE_RavineGen() {
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

                if (this.rand.nextInt(50) == 0) {

                    double gx = cx * 16.0D + this.rand.nextInt(16);
                    double gy = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20.0D;
                    double gz = cz * 16.0D + this.rand.nextInt(16);
                    byte b0 = 1;

                    for (int i1 = 0; i1 < b0; i1++) {

                        float x = this.rand.nextFloat() * 2.0F * 3.1415927F;
                        float x1 = (this.rand.nextFloat() - 0.5F) * 0.25F;
                        float x2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;

                        caveGen_func(data, this.rand.nextLong(), gx, gy, gz, x2, x, x1, 0, 0, 3.0D);
                    }
                }
            }
        }
    }

    public void caveGen_func(WE_GeneratorData data, long rs, double gx, double gy, double gz, float fn1, float fn2, float fn3, int in1, int in2, double dn) {
        double d4 = data.chunk_X + 8.0D, d5 = data.chunk_Z + 8.0D;
        float f3 = 0.0F, f4 = 0.0F;
        Random r = new Random(rs);

        if (in2 <= 0) {
            int p = this.range * 16 - 16;
            in2 = p - r.nextInt(p / 4);
        }

        boolean flag1 = false;
        if (in1 == -1) {
            in1 = in2 / 2;
            flag1 = true;
        }

        float f5 = 1.0F;
        for (int k1 = 0; k1 < 256; k1++) {
            if (k1 == 0 || r.nextInt(3) == 0)
                f5 = 1.0F + r.nextFloat() * r.nextFloat() * 1.0F;
            this.field_75046_d[k1] = f5 * f5;
        }

        for (; in1 < in2; in1++) {
            double d12 = 1.5D + MathHelper.sin(in1 * 3.1415927F / in2) * fn1, d6 = d12 * dn;
            d12 *= r.nextFloat() * 0.25D + 0.75D;
            d6 *= r.nextFloat() * 0.25D + 0.75D;

            float f6 = MathHelper.cos(fn3), f7 = MathHelper.sin(fn3);
            gx += MathHelper.cos(fn2) * f6;
            gy += f7;
            gz += MathHelper.sin(fn2) * f6;

            fn3 *= 0.7F;
            fn3 += f4 * 0.05F;
            fn2 += f3 * 0.05F;

            f4 *= 0.8F;
            f3 *= 0.5F;
            f4 += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 2.0F;
            f3 += (r.nextFloat() - r.nextFloat()) * r.nextFloat() * 4.0F;

            if (flag1 || r.nextInt(4) != 0) {
                double d7 = gx - d4, d8 = gz - d5, d9 = in2 - in1, d10 = fn1 + 18.0D;
                if (d7 * d7 + d8 * d8 - d9 * d9 > d10 * d10) {
                    return;
                }
                if (gx >= d4 - 16.0D - d12 * 2.0D && gz >= d5 - 16.0D - d12 * 2.0D && gx <= d4 + 16.0D + d12 * 2.0D && gz <= d5 + 16.0D + d12 * 2.0D) {
                    int i4 = MathHelper.floor_double(gx - d12) - (int) data.chunk_X - 1, l1 = MathHelper.floor_double(gx + d12) - (int) data.chunk_X + 1;
                    int j4 = MathHelper.floor_double(gy - d6) - 1, i2 = MathHelper.floor_double(gy + d6) + 1;
                    int k4 = MathHelper.floor_double(gz - d12) - (int) data.chunk_Z - 1, j2 = MathHelper.floor_double(gz + d12) - (int) data.chunk_Z + 1;

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
                    boolean flag2 = false;

                    int k2;
                    for (k2 = i4; !flag2 && k2 < l1; k2++) {
                        for (int l2 = k4; !flag2 && l2 < j2; l2++) {
                            for (int i3 = i2 + 1; !flag2 && i3 >= j4 - 1; i3--) {
                                int j3 = (k2 * 16 + l2) * 256 + i3;
                                if (i3 < 256) {
                                    if (isOceanBlock(data, j3, k2, i3, l2))
                                        flag2 = true;
                                    if (i3 != j4 - 1 && k2 != i4 && k2 != l1 - 1 && l2 != k4 && l2 != j2 - 1)
                                        i3 = j4;
                                }
                            }
                        }
                    }
                    if (!flag2) {
                        for (k2 = i4; k2 < l1; k2++) {
                            double d13 = (k2 + data.chunk_X + 0.5D - gx) / d12;
                            for (int j3 = k4; j3 < j2; j3++) {
                                double d14 = (j3 + data.chunk_Z + 0.5D - gz) / d12;
                                int k3 = (k2 * 16 + j3) * 256 + i2;
                                if (d13 * d13 + d14 * d14 < 1.0D) {
                                    for (int l3 = i2 - 1; l3 >= j4; l3--) {
                                        double d11 = (l3 + 0.5D - gy) / d6;
                                        if ((d13 * d13 + d14 * d14) * this.field_75046_d[l3] + d11 * d11 / 6.0D < 1.0D)
                                            digBlock(data, k3, k2, l3, j3);
                                        k3--;
                                    }
                                }
                            }
                        }
                        if (flag1)
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