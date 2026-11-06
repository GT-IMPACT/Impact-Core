package com.impact.mods.gregtech.gui.lap;

/** One sample per second, including both ends of the one-minute chart. */
public final class LapChargeHistory {

    public static final int SECONDS = 60;
    static final int POINTS = SECONDS + 1;
    static final int FULL_CHARGE = 10_000;

    private final int[] samples = new int[POINTS];
    private int nextSample;
    private int size;
    private long capacity;

    public void sample(long tick, long stored, long currentCapacity) {
        if (capacity != currentCapacity) {
            capacity = currentCapacity;
            nextSample = 0;
            size = 0;
        }
        if (capacity <= 0 || tick % 20 != 0) {
            return;
        }

        samples[nextSample] = (int) Math.round(fraction(stored, capacity) * FULL_CHARGE);
        nextSample = (nextSample + 1) % POINTS;
        size = Math.min(size + 1, POINTS);
    }

    public int[] snapshot() {
        int[] ordered = new int[size];
        int oldest = (nextSample + POINTS - size) % POINTS;
        int firstPart = Math.min(size, POINTS - oldest);
        System.arraycopy(samples, oldest, ordered, 0, firstPart);
        System.arraycopy(samples, 0, ordered, firstPart, size - firstPart);
        return ordered;
    }

    static double fraction(long stored, long capacity) {
        return capacity > 0 ? (double) Math.max(0, Math.min(stored, capacity)) / capacity : 0;
    }
}
