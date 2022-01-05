package com.impact.util.files.jsonNBT;

import com.google.gson.*;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.items.ItemDebug;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Type;


public class JsonItemStack implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	
	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		GameRegistry.UniqueIdentifier item = GameRegistry.findUniqueIdentifierFor(src.getItem());
		if (src.getTagCompound() != null) {
			if (src.getTagCompound().hasKey("oredict")) {
				String ore = OreDictionary.getOreName(OreDictionary.getOreID(src));
				if (!ore.equals("Unknown")) {
					object.addProperty("oredict", ore);
					object.addProperty("amount", src.stackSize);
					return object;
				}
			}
		}
		object.addProperty("mod", item.modId);
		object.addProperty("name", item.name);
		object.addProperty("damage", src.getItemDamage());
		if (src.getTagCompound() != null) {
			object.add("nbt", context.serialize(src.getTagCompound(), NBTTagCompound.class));
		}
		object.addProperty("amount", src.stackSize);
		return object;
	}
	
	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		ItemStack is;
		int amount = object.get("amount").getAsInt();
		if (object.has("oredict")) {
			String ore = object.get("oredict").getAsString();
			if (ore.startsWith("craftingTool")) {
				is = new ItemStack(ItemDebug.getInstance(), amount);
				OreDictionary.registerOre(ore, is);
				return is;
			}
			OrePrefixes prefixes = OrePrefixes.getOrePrefix(ore);
			Materials material = OrePrefixes.getMaterial(ore);
			if (prefixes != null && material != Materials._NULL) {
				is = GT_OreDictUnificator.get(prefixes, material, amount);
			} else {
				is = GregTech_API.getStackofAmountFromOreDict(ore, amount);
			}
		} else {
			NBTTagCompound nbtTagCompound = null;
			try {
				nbtTagCompound = object.has("nbt") ? context.deserialize(object.get("nbt"), NBTTagCompound.class) : null;
			} catch (Exception ignored) {}
			String mod = object.get("mod").getAsString();
			String name = object.get("name").getAsString();
			int damage = object.get("damage").getAsInt();
			is = new ItemStack(GameRegistry.findItem(mod, name), amount, damage);
			is.setTagCompound(nbtTagCompound);
		}
		return is;
	}
}