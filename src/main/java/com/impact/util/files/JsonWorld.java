package com.impact.util.files;

import com.impact.core.SaveManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.impact.core.Impact_API.*;
@SuppressWarnings("ALL")
public class JsonWorld {

    private final static String COMMUNICATION_TOWERS = "CommunicationTowers";
    private final static String SPACE_SATELLITES = "SpaceSatellites";
    private final static String AERO_STATES = "AeroStates";
    private final static String BIOMES_ORES = "BiomesOres";

    public static void save() {
        saveCommunicationTowers();
        saveSpaceSatellite();
        saveAeroState();
        saveBiomesOres();
    }

    public static void load() {
        loadCommunicationTowers();
        loadSpaceSatellite();
        loadAeroState();
        loadBiomesOres();
    }

    //region CommunicationTowers
    private static void loadCommunicationTowers() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.jsonToMapStringIntArray(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
    }

    private static void saveCommunicationTowers() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.jsonFromMapStringIntArray(sCommunicationTower, json.getPath(), COMMUNICATION_TOWERS);
    }
    //endregion

    //region SpaceSatellites
    private static void loadSpaceSatellite() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.jsonToMapStringIntArray(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
    }

    private static void saveSpaceSatellite() {
        File json = SaveManager.get().parallelSystemDirectory;
        JsonUtils.jsonFromMapStringIntArray(sSpaceSatellite, json.getPath(), SPACE_SATELLITES);
    }
    //endregion
    
    //region SpaceSatellites
    private static void loadAeroState() {
        File json = SaveManager.get().aerostateSystemDirectory;
        JsonUtils.jsonToMapStringIntArray(sAerostat, json.getPath(), AERO_STATES);
    }
    
    private static void saveAeroState() {
        File json = SaveManager.get().aerostateSystemDirectory;
        JsonUtils.jsonFromMapStringIntArray(sAerostat, json.getPath(), AERO_STATES);
    }
    //endregion
    
    //region BiomesOres
    private static void loadBiomesOres() {
        File json = SaveManager.get().biomesOresDirectory;
        Map<String, int[]> loadMap = new HashMap<>();
        JsonUtils.jsonToMapStringIntArray(loadMap, json.getPath(), BIOMES_ORES);
        loadMap.forEach((k, v) -> {
            int[] ints = new int[] {v[0], v[1], v[2], v[3]};
            int sub = k.indexOf('~');
            String name = k.substring(0, sub);
            sOreInChunk.put(ints, name);
        });
    }
    
    private static void saveBiomesOres() {
        File json = SaveManager.get().biomesOresDirectory;
        Map<String, int[]> saveMap = new HashMap<>();
        sOreInChunk.forEach((k, v) -> {
            int[] ints = new int[] {k[0], k[1], k[2], k[3]};
            saveMap.put(v + "~" + k, ints);
        });
        JsonUtils.jsonFromMapStringIntArray(saveMap, json.getPath(), BIOMES_ORES);
    }
    //endregion
}
