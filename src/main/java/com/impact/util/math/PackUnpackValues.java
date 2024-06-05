package com.impact.util.math;

public class PackUnpackValues {

    public static int packValues(int value1, int value2) {
        if ((value1 & 0xFFFF0000) != 0 || (value2 & 0xFFFF0000) != 0) {
            return 0;
        }
        return (value1 << 16) | (value2 & 0xFFFF);
    }

    public static int[] unpackValues(int packedValue) {
        int value1 = (packedValue >> 16) & 0xFFFF;
        int value2 = packedValue & 0xFFFF;
        return new int[]{value1, value2};
    }

    public static int packFloats(float value1, float value2) {
        return (Math.min((int) value1, 100) << 7) | Math.min((int) value2, 100);
    }

    public static float unpackFloat1(int packedValue) {
        return (float) (packedValue >> 7);
    }

    public static float unpackFloat2(int packedValue) {
        return (float) (packedValue & 0x7F);
    }
}
