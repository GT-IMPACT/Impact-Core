package com.impact.client.render.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Cubic crystal relief, symmetric under rotations and reflections of all three axes. */
public final class MatrixTransducerMesh {
    public static final double MIN = 0.2D;
    public static final double MAX = 0.8D;
    private static final int SIZE = 20;
    private static final double STEP = (MAX - MIN) / SIZE;
    private static final List<Face> FACES = createFaces();

    private MatrixTransducerMesh() {
    }

    public static List<Face> getFaces() {
        return FACES;
    }

    private static boolean filled(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) {
            return false;
        }
        int a = Math.abs(2 * x + 1 - SIZE);
        int b = Math.abs(2 * y + 1 - SIZE);
        int c = Math.abs(2 * z + 1 - SIZE);
        return (a <= 13 && b <= 13 && c <= 13)
                || terrace(a, b, c) || terrace(b, a, c) || terrace(c, a, b);
    }

    private static boolean terrace(int normal, int u, int v) {
        if (normal < 15) {
            return false;
        }
        int tier = (normal - 15) / 2;
        int width = 11 - tier * 4;
        boolean center = u <= width && v <= width;
        int cornerWidth = tier == 0 ? 3 : tier == 1 ? 1 : -1;
        boolean corners = Math.abs(u - 10) <= cornerWidth && Math.abs(v - 10) <= cornerWidth;
        return center || corners;
    }

    private static boolean cell(int axis, int normal, int u, int v) {
        return axis == 0 ? filled(normal, u, v) : axis == 1 ? filled(v, normal, u) : filled(u, v, normal);
    }

    private static List<Face> createFaces() {
        List<Face> faces = new ArrayList<>();
        for (int axis = 0; axis < 3; axis++) {
            for (int sign : new int[] { -1, 1 }) {
                for (int n = 0; n < SIZE; n++) {
                    boolean[][] mask = new boolean[SIZE][SIZE];
                    for (int u = 0; u < SIZE; u++) {
                        for (int v = 0; v < SIZE; v++) {
                            mask[u][v] = cell(axis, n, u, v) && !cell(axis, n + sign, u, v);
                        }
                    }
                    for (int u = 0; u < SIZE; u++) {
                        for (int v = 0; v < SIZE; v++) {
                            if (!mask[u][v]) {
                                continue;
                            }
                            int width = 1;
                            while (u + width < SIZE && mask[u + width][v]) {
                                width++;
                            }
                            int height = 1;
                            boolean extend = true;
                            while (v + height < SIZE && extend) {
                                for (int du = 0; du < width; du++) {
                                    extend &= mask[u + du][v + height];
                                }
                                if (extend) {
                                    height++;
                                }
                            }
                            for (int du = 0; du < width; du++) {
                                for (int dv = 0; dv < height; dv++) {
                                    mask[u + du][v + dv] = false;
                                }
                            }
                            faces.add(new Face(axis, sign, n + (sign > 0 ? 1 : 0), u, v, width, height));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(faces);
    }

    public static final class Face {
        public final int axis;
        public final int sign;
        private final double plane;
        private final double u;
        private final double v;
        private final double width;
        private final double height;

        private Face(int axis, int sign, int plane, int u, int v, int width, int height) {
            this.axis = axis;
            this.sign = sign;
            this.plane = MIN + plane * STEP;
            this.u = MIN + u * STEP;
            this.v = MIN + v * STEP;
            this.width = width * STEP;
            this.height = height * STEP;
        }

        public double coordinate(int corner, int dimension, double inset) {
            // Cyclic axis order keeps the winding outward on every side.
            int index = sign > 0 ? corner : (4 - corner) % 4;
            double localU = u + ((index == 1 || index == 2) ? width - inset : inset);
            double localV = v + (index >= 2 ? height - inset : inset);
            return dimension == axis ? plane : dimension == (axis + 1) % 3 ? localU : localV;
        }

        public double getInset() {
            return Math.min(0.0025D, Math.min(width, height) * 0.12D);
        }

        public int getColor() {
            double depth = Math.round(Math.abs(plane - 0.5D) * 10000) / 3000.0D;
            int red = (int) (164 - 65 * depth);
            int green = (int) (109 - 67 * depth);
            int blue = (int) (221 - 42 * depth);
            return red << 16 | green << 8 | blue;
        }
    }
}
