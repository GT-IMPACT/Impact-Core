package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.impact.core.SaveManager;
import com.impact.mods.gregtech.enums.VeinChunk;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
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
        Gson gson = new Gson();
        JsonElement jsonElement = null;
        try {
            jsonElement = gson.fromJson(new FileReader(json.getPath() + "\\" + BIOMES_ORES + ".json"), JsonElement.class);
            Type listType = new TypeToken<Map<String, VeinChunk>>() {}.getType();
            Map<String, VeinChunk> loadMap = gson.fromJson(jsonElement, listType);
            loadMap.forEach((k, v) -> {
                int sub = k.indexOf('~');
                String name = k.substring(0, sub);
                sOreInChunk.put(v, name);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void saveBiomesOres() {
        File json = SaveManager.get().biomesOresDirectory;
        Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(json.getPath() + "\\" + BIOMES_ORES + ".json")) {
            Map<String, VeinChunk> saveMap = new HashMap<>();
            sOreInChunk.forEach((k, v) -> saveMap.put(v + "~" + k.hashCode(), k));
            objGson.toJson(saveMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
