package ru.vamig.worldengine.perlinnoise;


public class ValueNoise extends GenNoiseTemplate {
    public ValueNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
        super(gSeed, gPersistence, numOfOctaves, scl_x, scl_y, scl_z, sum, interpolation);
    }


    public ValueNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
        super(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, sum);
    }


    public double noiseOctave2d(double x, double z) {
        long squareStartX = (long) x, squareStartZ = (long) z, xs = 1L, zs = 1L;
        if (Math.abs(x) != x) xs = -1L;
        if (Math.abs(z) != z) zs = -1L;
        double pointInQuadX = Math.abs(x) - Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - Math.abs(squareStartZ);

        double topLeft = this.rand.smartGen2d(squareStartX, squareStartZ);
        double topRight = this.rand.smartGen2d(squareStartX + xs, squareStartZ);
        double bottomLeft = this.rand.smartGen2d(squareStartX, squareStartZ + zs);
        double bottomRight = this.rand.smartGen2d(squareStartX + xs, squareStartZ + zs);

        double line1 = lerp3d(topLeft, topRight, autoSmooth1d(pointInQuadX));
        double line2 = lerp3d(bottomLeft, bottomRight, autoSmooth1d(pointInQuadX));
        return lerp3d(line1, line2, autoSmooth1d(pointInQuadZ));
    }


    public float noiseOctave2f(float x, float z) {
        int squareStartX = (int) x, squareStartZ = (int) z, xs = 1, zs = 1;
        if (Math.abs(x) != x) xs = -1;
        if (Math.abs(z) != z) zs = -1;
        float pointInQuadX = Math.abs(x) - Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - Math.abs(squareStartZ);

        float topLeft = this.rand.smartGen2f(squareStartX, squareStartZ);
        float topRight = this.rand.smartGen2f(squareStartX + xs, squareStartZ);
        float bottomLeft = this.rand.smartGen2f(squareStartX, squareStartZ + zs);
        float bottomRight = this.rand.smartGen2f(squareStartX + xs, squareStartZ + zs);

        float line1 = lerp3f(topLeft, topRight, autoSmooth1f(pointInQuadX));
        float line2 = lerp3f(bottomLeft, bottomRight, autoSmooth1f(pointInQuadX));
        return lerp3f(line1, line2, autoSmooth1f(pointInQuadZ));
    }
}
