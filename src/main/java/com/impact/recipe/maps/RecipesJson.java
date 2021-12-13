package com.impact.recipe.maps;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.recipe.json.RecipeJson;
import com.impact.util.files.JsonUtils;
import gregtech.api.util.GT_Recipe;
import net.minecraft.nbt.JsonToNBT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.*;

import static com.impact.core.Impact_API.regionsOres;

public class RecipesJson {
	public static Map<String, List<RecipeJson>> recipeTest = new HashMap<>();
	
	public static void preSave(String nameRecipeMap, RecipeJson recipe) {
		if (recipeTest.containsKey(nameRecipeMap)) {
			recipeTest.get(nameRecipeMap).add(recipe);
		} else {
			List<RecipeJson> list = new ArrayList<>();
			list.add(recipe);
			recipeTest.put(nameRecipeMap, list);
		}
	}
	
	public static void save() {
		File saveDir = new File("config/IMPACT/recipes");
		for (String name : recipeTest.keySet()) {
			Gson gson = JsonUtils.recipeDefaultGson;
			try (FileWriter writer = new FileWriter(saveDir.getPath() + "\\" + name.replaceAll("\\.", "_") + ".json")) {
				Type gsonType = new TypeToken<List<RecipeJson>>() {}.getType();
				String gsonString = gson.toJson(recipeTest.get(name), gsonType);
				writer.write(gsonString);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void load() {
		try {
			Gson gson = JsonUtils.recipeDefaultGson;
			File saveDir = new File("config/IMPACT/recipes");
			if (!saveDir.isDirectory()) return;
			for (File fileEntry : Objects.requireNonNull(saveDir.listFiles())) {
				try {
					FileReader fr = new FileReader(fileEntry);
					BufferedReader br = new BufferedReader(fr);
					Type gsonType = new TypeToken<List<RecipeJson>>(){}.getType();
					recipeTest.put(fileEntry.getName().replaceAll("\\.json", "")
							.replaceAll("_", "\\."), gson.fromJson(br, gsonType));
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ignored) {}
		
		for (String name : recipeTest.keySet()) {
			for (GT_Recipe.GT_Recipe_Map sMapping : GT_Recipe.GT_Recipe_Map.sMappings) {
				if (sMapping.mUnlocalizedName.equals(name)) {
					for (RecipeJson recipeJson : recipeTest.get(name)) {
						sMapping.addRecipe(recipeJson.optimize, recipeJson.inputItem, recipeJson.outputItem,
								null, recipeJson.chance, recipeJson.inputFluid, recipeJson.outputFluid,
								recipeJson.time, recipeJson.voltage, recipeJson.special);
					}
				}
			}
		}
		int x = 0;
	}
}