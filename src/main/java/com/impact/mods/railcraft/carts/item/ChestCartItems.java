package com.impact.mods.railcraft.carts.item;

import com.impact.mods.railcraft.carts.item.items.ItemIronChestCart;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.impact.core.Refstrings.MODID;

public class ChestCartItems {
	
	public static Item ChestCart;
	public static Block ironChest;
	
	public static void init() {
		ChestCart = new ItemIronChestCart();
	}
	
	public static void registerItems() {
		GameRegistry.registerItem(ChestCart, MODID + "_" + ChestCart.getUnlocalizedName().substring(5));
	}
	
	public static void registerRecipes() {
		ironChest = Block.getBlockFromName("chestup:Blockchestup");
		for (int i = 0; i <= 2; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(ChestCart, 1, i), new ItemStack(ironChest, 1, i), Items.minecart);
		}
	}
}