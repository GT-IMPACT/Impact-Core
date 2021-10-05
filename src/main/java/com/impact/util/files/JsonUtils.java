package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {

    public static void parseMapToJson(Map map, String pathFile, String nameFile) {
        try {
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map>(){}.getType();
            String gsonString = gson.toJson(map, gsonType);
            FileWriter file = new FileWriter(pathFile + "\\" + nameFile + ".json");
            file.write(gsonString);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map parseJsonToMapStringAndArrayInt(Map map, String pathFile, String nameFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathFile + "\\" + nameFile + ".json"));
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map>(){}.getType();
            map.clear();
            map.putAll(gson.fromJson(br, gsonType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
