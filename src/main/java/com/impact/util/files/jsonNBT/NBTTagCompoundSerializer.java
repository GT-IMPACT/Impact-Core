/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */
package com.impact.util.files.jsonNBT;

import com.google.gson.*;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lukas on 25.05.14.
 */
public class NBTTagCompoundSerializer implements JsonSerializer<NBTTagCompound>, JsonDeserializer<NBTTagCompound> {
	@Override
	public JsonElement serialize(NBTTagCompound src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		for (Object key : src.func_150296_c()) {
			String keyString = (String) key;
			jsonObject.add(keyString, context.serialize(src.getTag(keyString)));
		}
		return jsonObject;
	}
	
	
	@Override
	public NBTTagCompound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonObject()) {
			JsonObject jsonObject = json.getAsJsonObject();
			NBTTagCompound compound = new NBTTagCompound();
			for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				try {
				String nameTag = entry.getKey();
				
				JsonElement jsonElement = entry.getValue();
				
				Type type = NbtToJson.getNBTTypeSmart(entry.getValue());
				
				NBTBase base = context.deserialize(jsonElement, type);
				
				compound.setTag(nameTag, base);
				
				} catch (Exception e){}
			}
			return compound;
		}
		return null;
	}
}
