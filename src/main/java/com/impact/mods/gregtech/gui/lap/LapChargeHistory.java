package com.impact.mods.gregtech.gui.lap;

/** One hour sampled each second; the selected chart always contains at most 61 points. */
public final class LapChargeHistory {

    public static final int SECONDS = 60;
    static final int POINTS = SECONDS + 1;
    static final int FULL_CHARGE = 10_000;

    private final int[] samples = new int[LapHistoryPeriod.HOUR.seconds + 1];
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
        nextSample = (nextSample + 1) % samples.length;
        size = Math.min(size + 1, samples.length);
    }

    public int[] snapshot() {
        return snapshot(LapHistoryPeriod.MINUTE);
    }

    public int[] snapshot(LapHistoryPeriod period) {
        if (size == 0) {
            return new int[0];
        }
        int step = period.seconds / SECONDS;
        int count = Math.min(POINTS, (size - 1) / step + 1);
        int[] ordered = new int[count];
        int oldest = (nextSample - 1 - (count - 1) * step + samples.length) % samples.length;
        for (int index = 0; index < count; index++) {
            ordered[index] = samples[(oldest + index * step) % samples.length];
        }
        return ordered;
    }

    static double fraction(long stored, long capacity) {
        return capacity > 0 ? (double) Math.max(0, Math.min(stored, capacity)) / capacity : 0;
    }
}
