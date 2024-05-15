package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.impact.core.SaveManager;

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

    }
    
    private static void saveAeroState() {

    }
    //endregion

}