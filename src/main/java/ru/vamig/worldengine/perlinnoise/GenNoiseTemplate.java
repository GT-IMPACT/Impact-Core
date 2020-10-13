package ru.vamig.worldengine.perlinnoise;


public abstract class GenNoiseTemplate {
    public long seed;
    public double persistence;
    public double scaleX;
    public double scaleY;
    public double scaleZ;
    public int octaves;
    public int height;
    public byte iType;
    public WhiteNoise rand;

    public GenNoiseTemplate(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
        this.seed = gSeed;
        this.persistence = gPersistence;
        this.octaves = numOfOctaves;
        this.scaleX = scl_x;
        this.scaleY = scl_y;
        this.scaleZ = scl_z;
        this.height = sum;
        this.iType = interpolation;
        this.rand = new WhiteNoise(gSeed);
    }


    public GenNoiseTemplate(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
        this(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, scl_xz, sum, (byte) 2);
    }


    public double lerp3d(double a, double b, double n) {
        return a + (b - a) * n;
    }


    public float lerp3f(float a, float b, float n) {
        return a + (b - a) * n;
    }


    public double smoothstep1d(double n) {
        return n * n * (3.0D - 2.0D * n);
    }


    public float smoothstep1f(float n) {
        return n * n * (3.0F - 2.0F * n);
    }


    public double smootherstep1d(double n) {
        return n * n * n * (n * (n * 6.0D - 15.0D) + 10.0D);
    }


    public float smootherstep1f(float n) {
        return n * n * n * (n * (n * 6.0F - 15.0F) + 10.0F);
    }


    public double autoSmooth1d(double n) {
        switch (this.iType) {
            case 2:
                return smootherstep1d(n);
            case 1:
                return smoothstep1d(n);
        }
        return n;
    }


    public float autoSmooth1f(float n) {
        switch (this.iType) {
            case 2:
                return smootherstep1f(n);
            case 1:
                return smoothstep1f(n);
        }
        return n;
    }


    public double dot2d(double[] vec1, double[] vec2) {
        return vec1[0] * vec2[0] + vec1[1] * vec2[1];
    }


    public float dot2f(float[] vec1, float[] vec2) {
        return vec1[0] * vec2[0] + vec1[1] * vec2[1];
    }


    public abstract double noiseOctave2d(double paramDouble1, double paramDouble2);


    public abstract float noiseOctave2f(float paramFloat1, float paramFloat2);


    public double genNoise2d(double x, double z) {
        double result = 0.0D, amplitudeMultiplier = 1.0D, nowX = x, nowZ = z;
        for (int i = 0; i < this.octaves; i++) {
            result += noiseOctave2d(nowX / this.scaleX, nowZ / this.scaleZ) * amplitudeMultiplier;
            amplitudeMultiplier *= this.persistence;
            nowX *= 2.0D;
            nowZ *= 2.0D;
        }
        return this.height + result * this.scaleY;
    }


    public float genNoise2f(float x, float z) {
        float result = 0.0F, amplitudeMultiplier = 1.0F, nowX = x, nowZ = z;
        for (int i = 0; i < this.octaves; i++) {
            result += noiseOctave2f(nowX / (float) this.scaleX, nowZ / (float) this.scaleZ) * amplitudeMultiplier;
            amplitudeMultiplier *= (float) this.persistence;
            nowX *= 2.0F;
            nowZ *= 2.0F;
        }
        return this.height + result * (float) this.scaleY;
    }
}
