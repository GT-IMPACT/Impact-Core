package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.impact.common.oregeneration.OresRegion;
import com.impact.core.Impact_API;
import com.impact.core.SaveManager;
import com.impact.mods.gregtech.enums.VeinChunk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
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
        saveOreGenerator();
    }

    public static void load() {
        loadCommunicationTowers();
        loadSpaceSatellite();
        loadAeroState();
        loadOreGenerator();
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
    
    private static void loadOreGenerator() {
        File json = SaveManager.get().biomesOresDirectory;
        Gson gson = new Gson();
        JsonElement jsonElement = null;
        try {
            
            FileReader fr = new FileReader(json.getPath() + "\\" + BIOMES_ORES + ".json");
            BufferedReader br = new BufferedReader(fr);
            jsonElement = gson.fromJson(br, JsonElement.class);
            Type listType = new TypeToken<List<OresRegion>>() {}.getType();
            List<OresRegion> regionList = gson.fromJson(jsonElement, listType);
            regionsOres.addAll(regionList);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void saveOreGenerator() {
        File json = SaveManager.get().biomesOresDirectory;
        Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(json.getPath() + "\\" + BIOMES_ORES + ".json")) {
            objGson.toJson(regionsOres, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}