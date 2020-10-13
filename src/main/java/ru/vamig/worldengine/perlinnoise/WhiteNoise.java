package ru.vamig.worldengine.perlinnoise;

import net.minecraft.util.MathHelper;


public class WhiteNoise {
    public long seed;

    public WhiteNoise(long genSeed) {
        this.seed = (long) Math.pow(genSeed, 11.0D) * 17L + 514L;
    }


    public double gen2d(long x, long z) {
        long n = this.seed + x * 4L + z * 341L;
        n = n << 13L ^ n;
        return 1.0D - (n * (n * n * 15731L + 789221L) + 1376312589L & 0x7FFFFFFFL) / 1.073741824E9D;
    }


    public float gen2f(int x, int z) {
        int n = (int) this.seed + x * 4 + z * 341;
        n = n << 13 ^ n;
        return 1.0F - (n * (n * n * 15731 + 789221) + 1376312589 & Integer.MAX_VALUE) / 1.07374182E9F;
    }


    public double[] vecGen2d(long x, long z) {
        double angle = gen2d(x, z) * Math.PI;
        return new double[]{MathHelper.cos((float) angle), MathHelper.sin((float) angle)};
    }


    public float[] vecGen2f(int x, int z) {
        float angle = gen2f(x, z) * 3.1415927F;
        return new float[]{MathHelper.cos(angle), MathHelper.sin(angle)};
    }


    public double smartGen2d(long x, long z) {
        return gen2d(x, z);
    }


    public float smartGen2f(int x, int z) {
        return gen2f(x, z);
    }


    public double[] smartVecGen2d(long x, long z) {
        return vecGen2d(x, z);
    }


    public float[] smartVecGen2f(int x, int z) {
        return vecGen2f(x, z);
    }
}
