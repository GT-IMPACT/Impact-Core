package com.impact.util.files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {

    public static <K,V> void parseMapToJson(Map<K,V> map, String pathFile, String nameFile) {
        try {
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map<K,V> >(){}.getType();
            String gsonString = gson.toJson(map, gsonType);
            FileWriter file = new FileWriter(pathFile + "\\" + nameFile + ".json");
            file.write(gsonString);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <K,V> void parseJsonToMap(Map<K,V> map, String pathFile, String nameFile) {
        try {
            FileReader fr = new FileReader(pathFile + "\\" + nameFile + ".json");
            BufferedReader br = new BufferedReader(fr);
            Gson gson = new Gson();
            Type gsonType = new TypeToken<Map<K,V>>(){}.getType();
            map.clear();
            map.putAll(gson.fromJson(br, gsonType));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
