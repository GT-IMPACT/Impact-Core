package com.impact.util.statistics;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LongRunningAverageTest {

    @Test
    void matchesExactSumsAndAveragesAcrossOverflowAndWraparound() {
        LongRunningAverage average = new LongRunningAverage(17);
        ILongData shortWindow = average.view(5);
        ArrayDeque<Long> expected = new ArrayDeque<>();
        for (int index = 0; index < 400; index++) {
            long value = index % 7 == 0 ? 0 : Long.MAX_VALUE - index;
            average.update(value);
            expected.addLast(value);
            if (expected.size() > 17) {
                expected.removeFirst();
            }
            BigInteger sum = BigInteger.ZERO;
            BigInteger shortSum = BigInteger.ZERO;
            int position = 0;
            int shortSize = Math.min(5, expected.size());
            for (long sample : expected) {
                sum = sum.add(BigInteger.valueOf(sample));
                if (position++ >= expected.size() - shortSize) {
                    shortSum = shortSum.add(BigInteger.valueOf(sample));
                }
            }
            assertEquals(expected.size(), average.size());
            assertEquals(sum, average.sum());
            assertEquals(sum.divide(BigInteger.valueOf(expected.size())).longValueExact(), average.avgLong());
            assertEquals(shortSize, shortWindow.size());
            assertEquals(shortSum, shortWindow.sum());
            assertEquals(shortSum.divide(BigInteger.valueOf(shortSize)).longValueExact(), shortWindow.avgLong());
        }
    }

    @Test
    void preservesGtnhEmptyWindowsAndArgumentChecks() {
        LongRunningAverage average = new LongRunningAverage(10);
        assertEquals(0, average.avgLong());
        assertEquals(0d, average.avg());
        assertEquals(0, average.view(2).avgLong());
        assertSame(average, average.view(10));
        assertThrows(IllegalArgumentException.class, () -> new LongRunningAverage(1));
        assertThrows(IllegalArgumentException.class, () -> average.view(1));
        assertThrows(IllegalArgumentException.class, () -> average.view(11));
        assertThrows(IllegalArgumentException.class, () -> average.update(-1));
    }
}
