package com.impact.mods.gregtech.gui.lap;

public enum LapHistoryPeriod {

    MINUTE(60, "minute"),
    TEN_MINUTES(600, "ten_minutes"),
    HOUR(3600, "hour");

    public final int seconds;
    public final String key;

    LapHistoryPeriod(int seconds, String key) {
        this.seconds = seconds;
        this.key = key;
    }
}
