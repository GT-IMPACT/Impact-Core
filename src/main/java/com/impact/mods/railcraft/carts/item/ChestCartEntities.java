package com.impact.mods.railcraft.carts.item;

import com.impact.impact;
import com.impact.mods.railcraft.carts.item.entities.EntityAlChestCart;
import com.impact.mods.railcraft.carts.item.entities.EntitySteelChestCart;
import com.impact.mods.railcraft.carts.item.entities.EntityWIronChestCart;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ChestCartEntities {
	
	public static void init() {
		EntityRegistry.registerModEntity(EntitySteelChestCart.class, "EntityIronChestCart", 1, impact.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityAlChestCart.class, "EntityGoldChestCart", 2, impact.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityWIronChestCart.class, "EntityCopperChestCart", 3, impact.instance, 80, 3, true);
	}
}