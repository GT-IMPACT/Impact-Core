package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.*;

public class JsonUtils {

    public static void parseMapToJson(Map<String, int[]> map, String pathFile, String nameFile) {
        try {
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map<String, int[]>>(){}.getType();
            String gsonString = gson.toJson(map, gsonType);
            FileWriter file = new FileWriter(pathFile + "\\" + nameFile + ".json");
            file.write(gsonString);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseJsonToMap(Map<String, int[]> map, String pathFile, String nameFile) {
        try {
            FileReader fr = new FileReader(pathFile + "\\" + nameFile + ".json");
            BufferedReader br = new BufferedReader(fr);
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map<String, int[]>>(){}.getType();
            map.putAll(gson.fromJson(br, gsonType));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}