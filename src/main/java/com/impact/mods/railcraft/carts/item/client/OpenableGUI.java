package com.impact.mods.railcraft.carts.item.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface OpenableGUI {
	
	Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
	
	Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
}