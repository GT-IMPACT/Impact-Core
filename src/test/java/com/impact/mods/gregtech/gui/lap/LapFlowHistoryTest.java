package com.impact.mods.gregtech.gui.lap;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LapFlowHistoryTest {

    @Test
    void averagesOnlyAvailableTicksWhileTheWindowsAreFilling() {
        LapFlowHistory history = new LapFlowHistory();
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            assertEquals(0, history.averageInput(period));
            assertEquals(0, history.averageOutput(period));
        }
        history.sample(100, 0);
        history.sample(0, 20);
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            assertEquals(50, history.averageInput(period));
            assertEquals(10, history.averageOutput(period));
        }
    }

    @Test
    void zeroFlowAgesOutTrafficAtEachPeriodWithoutOverflow() {
        LapFlowHistory history = new LapFlowHistory();
        for (int tick = 0; tick < 72000; tick++) {
            history.sample(Long.MAX_VALUE, Long.MAX_VALUE / 2);
        }
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            assertEquals(Long.MAX_VALUE, history.averageInput(period));
        }
        for (int tick = 0; tick < 1200; tick++) {
            history.sample(0, 0);
        }
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            int ticks = period.seconds * 20;
            BigInteger expectedInput = BigInteger.valueOf(Long.MAX_VALUE)
                    .multiply(BigInteger.valueOf(ticks - 1200)).divide(BigInteger.valueOf(ticks));
            BigInteger expectedOutput = BigInteger.valueOf(Long.MAX_VALUE / 2)
                    .multiply(BigInteger.valueOf(ticks - 1200)).divide(BigInteger.valueOf(ticks));
            assertEquals(expectedInput.longValueExact(), history.averageInput(period));
            assertEquals(expectedOutput.longValueExact(), history.averageOutput(period));
        }
        for (int tick = 1200; tick < 72000; tick++) {
            history.sample(0, 0);
        }
        for (LapHistoryPeriod period : LapHistoryPeriod.values()) {
            assertEquals(0, history.averageInput(period));
            assertEquals(0, history.averageOutput(period));
        }
    }
}
