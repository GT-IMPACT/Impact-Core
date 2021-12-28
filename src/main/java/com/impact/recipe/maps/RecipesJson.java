package com.impact.recipe.maps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.impact.impact;
import com.impact.recipe.json.RecipeJson;
import com.impact.recipe.json.RecipeJsonCrafing;
import com.impact.util.files.JsonUtils;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.*;

public class RecipesJson {
	public static Map<String, List<RecipeJson>> recipeTest = new HashMap<>();
	public static List<RecipeJsonCrafing> recipeTestCrafting = new ArrayList<>();
	
	private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
	
	public static void preSave(String nameRecipeMap, RecipeJson recipe) {
		if (recipeTest.containsKey(nameRecipeMap)) {
			recipeTest.get(nameRecipeMap).add(recipe);
		} else {
			List<RecipeJson> list = new ArrayList<>();
			list.add(recipe);
			recipeTest.put(nameRecipeMap, list);
		}
	}
	
	public static void preSaveCrafting(RecipeJsonCrafing recipe) {
		if (!recipeTestCrafting.contains(recipe)) {
			recipeTestCrafting.add(recipe);
		}
	}
	
	public static void saveCrafting() {
		if (recipeTestCrafting.isEmpty()) return;
		File saveDir = new File("config/IMPACT/recipes");
			Gson gson = JsonUtils.recipeDefaultGson;
			try (FileWriter writer = new FileWriter(saveDir.getPath() + "\\crafting.json")) {
				Type gsonType = new TypeToken<List<RecipeJsonCrafing>>() {}.getType();
				String gsonString = gson.toJson(recipeTestCrafting, gsonType);
				writer.write(gsonString);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void loadCrafting() {
		try {
			recipeTestCrafting.clear();
			Gson gson = JsonUtils.recipeDefaultGson;
			File saveDir = new File("config/IMPACT/recipes");
			if (!saveDir.isDirectory()) return;
			try {
				FileReader fr = new FileReader(saveDir.getPath() + "\\crafting.json");
				BufferedReader br = new BufferedReader(fr);
				Type gsonType = new TypeToken<List<RecipeJsonCrafing>>(){}.getType();
				recipeTestCrafting.addAll(gson.fromJson(br, gsonType));
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ignored) {}
		
		for (RecipeJsonCrafing recipe : recipeTestCrafting) {
			final char[] slot = new char[9];
			List<Character> chars = new ArrayList<>();
			List<ItemStack> stacks = new ArrayList<>();
			for (int i = 0; i < recipe.inputItem.length; i++) {
				slot[i] = recipe.inputItem[i] != null ? Integer.toString(i).toCharArray()[0] : ' ';
				if (recipe.inputItem[i] != null) {
					chars.add(slot[i]);
					stacks.add(recipe.inputItem[i]);
				}
			}
			
			String first = slot[0] + "" + slot[1] + "" + (recipe.inputItem[2] == null ? "" : slot[2]);
			String second = slot[3] + "" + slot[4] + "" + (recipe.inputItem[5] == null ? "" : slot[5]);
			String third = (recipe.inputItem[6] == null ? "" : slot[6]) + "" +
					(recipe.inputItem[7] == null ? "" : slot[7]) + "" +
					(recipe.inputItem[8] == null ? "" : slot[8]);
			
			int size = 2 + (third.isEmpty() ? 0 : 1);
			
			Object[] shapeRecipe = new Object[size + chars.size() + stacks.size()];
			
			shapeRecipe[0] = first;
			shapeRecipe[1] = second;
			if (size > 2) {
				shapeRecipe[2] = third;
			}
			
			int indexSlot = 0;
			for (int i = size; i < shapeRecipe.length; i += 2) {
				shapeRecipe[i + 1] = stacks.get(indexSlot);
				shapeRecipe[i] = chars.get(indexSlot++);
			}
			GT_ModHandler.addCraftingRecipe(recipe.outputItem, tBitMask, shapeRecipe);
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
		recipeTest.clear();
	}
	
	public static void load() {
		try {
			Gson gson = JsonUtils.recipeDefaultGson;
			File saveDir = new File("config/IMPACT/recipes");
			if (!saveDir.isDirectory()) return;
			for (File fileEntry : Objects.requireNonNull(saveDir.listFiles())) {
				try {
					if (!fileEntry.equals(new File(saveDir.getPath() + "\\crafting.json"))) {
						FileReader fr = new FileReader(fileEntry);
						BufferedReader br = new BufferedReader(fr);
						Type gsonType = new TypeToken<List<RecipeJson>>() {}.getType();
						recipeTest.put(fileEntry.getName().replaceAll("\\.json", "")
								.replaceAll("_", "\\."), gson.fromJson(br, gsonType));
						br.close();
					}
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