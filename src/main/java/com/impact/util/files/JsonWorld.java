package com.impact.util.files;

import com.impact.core.SaveManager;

import java.io.File;

import static com.impact.core.Impact_API.sCommunicationTower;
import static com.impact.core.Impact_API.sSpaceSatellite;

public class JsonWorld {

    private final static String COMMUNICATION_TOWERS = "CommunicationTowers";
    private final static String SPACE_SATELLITES = "SpaceSatellites";

    public static void save() {
        saveCommunicationTowers();
        saveSpaceSatellite();
    }

    public static void load() {
        loadCommunicationTowers();
        loadSpaceSatellite();
    }

    //region CommunicationTowers
    private static void loadCommunicationTowers() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseJsonToMapStringAndArrayInt(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
    }

    private static void saveCommunicationTowers() {
        if (!sCommunicationTower.isEmpty()) {
            File json = SaveManager.get().parallelSystemDirectory;
            JsonUtils.parseMapToJson(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
        }
    }
    //endregion

    //region SpaceSatellites
    private static void loadSpaceSatellite() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseJsonToMapStringAndArrayInt(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
    }

    private static void saveSpaceSatellite() {
        if (!sSpaceSatellite.isEmpty()) {
            File json = SaveManager.get().parallelSystemDirectory;
            JsonUtils.parseMapToJson(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
        }
    }
    //endregion
}
