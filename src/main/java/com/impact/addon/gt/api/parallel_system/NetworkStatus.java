package com.impact.addon.gt.api.parallel_system;

public enum NetworkStatus {

    CONNECTED("connected"),
    UNLOADED("unloaded"),
    NO_COVERAGE("no_coverage"),
    TOWER_OFFLINE("tower_offline"),
    MACHINE_OFFLINE("machine_offline"),
    RECEIVER_MISSING("receiver_missing"),
    SATELLITE_MISSING("satellite_missing"),
    SATELLITE_OFFLINE("satellite_offline"),
    FREQUENCY_MISMATCH("frequency_mismatch"),
    NO_TARGET("no_target"),
    DISABLED("disabled"),
    INSUFFICIENT_POINTS("insufficient_points"),
    FAULT("fault"),
    SATELLITE_UNBOUND("satellite_unbound"),
    LEGACY_SATELLITE_UNBOUND("legacy_satellite_unbound"),
    SATELLITE_UNLOADED("satellite_unloaded"),
    SATELLITE_TRANSMITTER_MISSING("satellite_transmitter_missing");

    private final String translationKey;

    NetworkStatus(String key) {
        translationKey = "impact.network.status." + key;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
