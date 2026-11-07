package com.impact.mods.gregtech.gui.lap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LapChargeHistoryTest {

    @Test
    void scalesOneHourIntoSixtyIntervalsForEverySelectedPeriod() {
        LapChargeHistory history = new LapChargeHistory();
        for (int second = 0; second <= 4000; second++) {
            history.sample(second * 20L, second, 10000);
        }
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            int[] samples = history.snapshot(period);
            assertEquals(61, samples.length);
            for (int index = 0; index <= 60; index++) {
                assertEquals(4000 - period.seconds + index * (period.seconds / 60), samples[index]);
            }
        }
    }

    @Test
    void longerWindowsKeepWarmupPointsAlignedWithTheLatestSample() {
        LapChargeHistory history = new LapChargeHistory();
        for (int second = 0; second <= 25; second++) {
            history.sample(second * 20L, second, 10000);
        }
        assertArrayEquals(new int[] { 5, 15, 25 }, history.snapshot(LapHistoryPeriod.TEN_MINUTES));
        assertArrayEquals(new int[] { 25 }, history.snapshot(LapHistoryPeriod.HOUR));
    }

    @Test
    void keepsOneMinuteOfSamplesInOrderAfterWraparound() {
        LapChargeHistory history = new LapChargeHistory();
        for (int tick = 0; tick <= 1_400; tick++) {
            history.sample(tick, tick / 20, 100);
        }

        int[] samples = history.snapshot();
        assertEquals(61, samples.length);
        assertEquals(1_000, samples[0]);
        assertEquals(4_000, samples[30]);
        assertEquals(7_000, samples[60]);
    }

    @Test
    void resetsAfterCapacityChangesAndClampsLongEnergyValues() {
        LapChargeHistory history = new LapChargeHistory();
        history.sample(20, Long.MAX_VALUE / 2, Long.MAX_VALUE);
        history.sample(40, Long.MAX_VALUE, Long.MAX_VALUE);
        assertArrayEquals(new int[]{5_000, 10_000}, history.snapshot());

        history.sample(41, 1, 1);
        assertArrayEquals(new int[0], history.snapshot());
        history.sample(60, 2, 1);
        history.sample(80, -1, 1);
        assertArrayEquals(new int[]{10_000, 0}, history.snapshot());
        history.sample(81, 2, 0);
        assertArrayEquals(new int[0], history.snapshot());
    }
}
