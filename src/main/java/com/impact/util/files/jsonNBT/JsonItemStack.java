package com.impact.util.files.jsonNBT;

import com.google.gson.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;

public class JsonItemStack  implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	
	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		GameRegistry.UniqueIdentifier item = GameRegistry.findUniqueIdentifierFor(src.getItem());
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
		int amount = object.get("amount").getAsInt();
		NBTTagCompound nbtTagCompound = null;
		try {
			nbtTagCompound = object.has("nbt") ? context.deserialize(object.get("nbt"), NBTTagCompound.class) : null;
		} catch (Exception e) {

		}
		ItemStack is;
		String mod = object.get("mod").getAsString();
		String name = object.get("name").getAsString();
		int damage = object.get("damage").getAsInt();
		is = new ItemStack(GameRegistry.findItem(mod, name), amount, damage);
		is.setTagCompound(nbtTagCompound);
		return is;
	}
}