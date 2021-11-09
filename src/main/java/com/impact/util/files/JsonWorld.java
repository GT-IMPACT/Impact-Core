package com.impact.util.files;

import com.impact.core.SaveManager;

import java.io.File;

import static com.impact.core.Impact_API.*;

public class JsonWorld {

    private final static String COMMUNICATION_TOWERS = "CommunicationTowers";
    private final static String SPACE_SATELLITES = "SpaceSatellites";
    private final static String AERO_STATES = "AeroStates";

    public static void save() {
        saveCommunicationTowers();
        saveSpaceSatellite();
        saveAeroState();
    }

    public static void load() {
        loadCommunicationTowers();
        loadSpaceSatellite();
        loadAeroState();
    }

    //region CommunicationTowers
    private static void loadCommunicationTowers() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseJsonToMap(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
    }

    private static void saveCommunicationTowers() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseMapToJson(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
    }
    //endregion

    //region SpaceSatellites
    private static void loadSpaceSatellite() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseJsonToMap(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
    }

    private static void saveSpaceSatellite() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.parseMapToJson(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
    }
    //endregion
    
    //region SpaceSatellites
    private static void loadAeroState() {
        File json = SaveManager.get().aerostateSystemDirectory;
        JsonUtils.parseJsonToMap(sAerostat, json.getPath(), AERO_STATES);
    }
    
    private static void saveAeroState() {
        File json = SaveManager.get().aerostateSystemDirectory;
        JsonUtils.parseMapToJson(sAerostat, json.getPath(), AERO_STATES);
    }
    //endregion
}
