package ru.vamig.worldengine.perlinnoise;


public class PerlinNoise extends GenNoiseTemplate {
    public PerlinNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_x, double scl_y, double scl_z, int sum, byte interpolation) {
        super(gSeed, gPersistence, numOfOctaves, scl_x, scl_y, scl_z, sum, interpolation);
    }


    public PerlinNoise(long gSeed, double gPersistence, int numOfOctaves, double scl_xz, double scl_y, int sum) {
        super(gSeed, gPersistence, numOfOctaves, scl_xz, scl_y, sum);
    }


    public double noiseOctave2d(double x, double z) {
        long squareStartX = (long) x, squareStartZ = (long) z, xs = 1L, zs = 1L;
        if (Math.abs(x) != x) xs = -1L;
        if (Math.abs(z) != z) zs = -1L;
        double pointInQuadX = Math.abs(x) - Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - Math.abs(squareStartZ);


        double[] topLeft = this.rand.smartVecGen2d(squareStartX, squareStartZ);
        double[] topRight = this.rand.smartVecGen2d(squareStartX + xs, squareStartZ);
        double[] bottomLeft = this.rand.smartVecGen2d(squareStartX, squareStartZ + zs);
        double[] bottomRight = this.rand.smartVecGen2d(squareStartX + xs, squareStartZ + zs);

        double[] distanceToTopLeft = {xs * pointInQuadX, zs * pointInQuadZ};
        double[] distanceToTopRight = {xs * pointInQuadX - xs, zs * pointInQuadZ};
        double[] distanceToBottomLeft = {xs * pointInQuadX, zs * pointInQuadZ - zs};
        double[] distanceToBottomRight = {xs * pointInQuadX - xs, zs * pointInQuadZ - zs};


        double dotTopLeft = dot2d(topLeft, distanceToTopLeft);
        double dotTopRight = dot2d(topRight, distanceToTopRight);
        double dotBottomLeft = dot2d(bottomLeft, distanceToBottomLeft);
        double dotBottomRight = dot2d(bottomRight, distanceToBottomRight);

        double line1 = lerp3d(dotTopLeft, dotTopRight, autoSmooth1d(pointInQuadX));
        double line2 = lerp3d(dotBottomLeft, dotBottomRight, autoSmooth1d(pointInQuadX));
        return lerp3d(line1, line2, autoSmooth1d(pointInQuadZ));
    }


    public float noiseOctave2f(float x, float z) {
        int squareStartX = (int) x, squareStartZ = (int) z, xs = 1, zs = 1;
        if (Math.abs(x) != x) xs = -1;
        if (Math.abs(z) != z) zs = -1;
        float pointInQuadX = Math.abs(x) - Math.abs(squareStartX), pointInQuadZ = Math.abs(z) - Math.abs(squareStartZ);


        float[] topLeft = this.rand.smartVecGen2f(squareStartX, squareStartZ);
        float[] topRight = this.rand.smartVecGen2f(squareStartX + xs, squareStartZ);
        float[] bottomLeft = this.rand.smartVecGen2f(squareStartX, squareStartZ + zs);
        float[] bottomRight = this.rand.smartVecGen2f(squareStartX + xs, squareStartZ + zs);

        float[] distanceToTopLeft = {xs * pointInQuadX, zs * pointInQuadZ};
        float[] distanceToTopRight = {xs * pointInQuadX - xs, zs * pointInQuadZ};
        float[] distanceToBottomLeft = {xs * pointInQuadX, zs * pointInQuadZ - zs};
        float[] distanceToBottomRight = {xs * pointInQuadX - xs, zs * pointInQuadZ - zs};


        float dotTopLeft = dot2f(topLeft, distanceToTopLeft);
        float dotTopRight = dot2f(topRight, distanceToTopRight);
        float dotBottomLeft = dot2f(bottomLeft, distanceToBottomLeft);
        float dotBottomRight = dot2f(bottomRight, distanceToBottomRight);

        float line1 = lerp3f(dotTopLeft, dotTopRight, autoSmooth1f(pointInQuadX));
        float line2 = lerp3f(dotBottomLeft, dotBottomRight, autoSmooth1f(pointInQuadX));
        return lerp3f(line1, line2, autoSmooth1f(pointInQuadZ));
    }
}
