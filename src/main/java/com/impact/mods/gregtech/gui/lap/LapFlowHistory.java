package com.impact.mods.gregtech.gui.lap;

import com.impact.util.statistics.ILongData;
import com.impact.util.statistics.LongRunningAverage;

/** The LSC's GTNH-style per-tick input/output windows share one hour of samples. */
public final class LapFlowHistory {

    private final LongRunningAverage input = new LongRunningAverage(LapHistoryPeriod.HOUR.seconds * 20);
    private final LongRunningAverage output = new LongRunningAverage(LapHistoryPeriod.HOUR.seconds * 20);
    // Create and retain all views before the first sample, just as GTNH's LSC does.
    private final ILongData[] inputWindows = windows(input);
    private final ILongData[] outputWindows = windows(output);

    public void sample(long input, long output) {
        this.input.update(input);
        this.output.update(output);
    }

    public long averageInput(LapHistoryPeriod period) {
        return inputWindows[period.ordinal()].avgLong();
    }

    public long averageOutput(LapHistoryPeriod period) {
        return outputWindows[period.ordinal()].avgLong();
    }

    private static ILongData[] windows(LongRunningAverage values) {
        LapHistoryPeriod[] periods = LapHistoryPeriod.values();
        ILongData[] windows = new ILongData[periods.length];
        for (LapHistoryPeriod period : periods) {
            windows[period.ordinal()] = values.view(period.seconds * 20);
        }
        return windows;
    }
}
