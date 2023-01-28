package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.impact.common.oregeneration.OreVeinRandomizer;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.SaveManager;
import com.impact.impact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.impact.core.Impact_API.*;
import static com.impact.util.Utilits.getTimeString;

@SuppressWarnings("ALL")
public class JsonWorld {

    private final static String COMMUNICATION_TOWERS = "CommunicationTowers";
    private final static String SPACE_SATELLITES = "SpaceSatellites";
    private final static String AERO_STATES = "AeroStates";
    private final static String ORES = "Ores";

    public static void save() {
        long start = System.currentTimeMillis();
        saveCommunicationTowers();
        saveSpaceSatellite();
        saveAeroState();
        saveOreGenerator();
        System.out.println("Impact Save files finished: (" + (System.currentTimeMillis() - start) + "ms)");
    }
    
    public static void saveAsync() {
        System.out.println("[IMPACT] Started saving files!");
        CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long start = System.currentTimeMillis();
                saveCommunicationTowers();
                saveSpaceSatellite();
                saveAeroState();
                saveOreGenerator();
                return start;
            }
        }).thenAccept(start -> {
            System.out.println("[IMPACT] Save files finished: (" + getTimeString(System.currentTimeMillis() - start)+ ")");
        });
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
        if (sCommunicationTower.isEmpty()) return;
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
        if (sSpaceSatellite.isEmpty()) return;
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
        if (sAerostat.isEmpty()) return;
        File json = SaveManager.get().aerostateSystemDirectory;
        JsonUtils.jsonFromMapStringIntArray(sAerostat, json.getPath(), AERO_STATES);
    }
    //endregion
    
    private static void loadOreGenerator() {
        File json = SaveManager.get().oresDirectory;
        Gson gson = new Gson();
        JsonElement jsonElement = null;
        try {
            FileReader fr = new FileReader(json.getPath() + "\\" + ORES + ".json");
            BufferedReader br = new BufferedReader(fr);
            jsonElement = gson.fromJson(br, JsonElement.class);
            Type listType = new TypeToken<List<OresRegionGenerator>>() {}.getType();
            List<OresRegionGenerator> regionList = gson.fromJson(jsonElement, listType);
            for (OresRegionGenerator o : regionList) {
                regionsOres.put(Objects.hash(o.xRegion, o.zRegion, o.dim), o);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        OreVeinRandomizer.resizeVeins();
    }
    
    private static void saveOreGenerator() {
        if (regionsOres.isEmpty()) return;
        File json = SaveManager.get().oresDirectory;
        Gson objGson = new Gson();
        try (FileWriter writer = new FileWriter(json.getPath() + "\\" + ORES + ".json")) {
            List<OresRegionGenerator> list = new ArrayList<>(regionsOres.values());
            objGson.toJson(list, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //    private static void loadOreGenerator() {
    //        File json = SaveManager.get().oresDirectory;
    //        Gson gson = new Gson();
    //        JsonElement jsonElement = null;
    //        for (File fileEntry : Objects.requireNonNull(json.listFiles())) {
    //            try {
    //                FileReader fr = new FileReader(fileEntry);
    //                BufferedReader br = new BufferedReader(fr);
    //                jsonElement = gson.fromJson(br, JsonElement.class);
    //                Type listType = new TypeToken<List<OresRegionGenerator>>(){}.getType();
    //                List<OresRegionGenerator> regionList = gson.fromJson(jsonElement, listType);
    //                for (OresRegionGenerator o : regionList) {
    //                    regionsOres.put(Objects.hash(o.xRegion, o.zRegion, o.dim), o);
    //                }
    //                br.close();
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //            OreVeinRandomizer.resizeVeins();
    //        }
    //    }
    //
    //    private static void saveOreGenerator() {
    //        if (regionsOres.isEmpty()) return;
    //        File json = SaveManager.get().oresDirectory;
    //        Gson objGson = new Gson();
    //        regionsOres.forEach((dimID, region) -> {
    //            String pathFile = "\\dimID" + region.dim;
    //            try (FileWriter writer = new FileWriter(json.getPath() + "\\" + ORES + pathFile + ".json")) {
    //                List<OresRegionGenerator> list = new ArrayList<>(regionsOres.values());
    //                objGson.toJson(list, writer);
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }
    //        });
    //    }
}