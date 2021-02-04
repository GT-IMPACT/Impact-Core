package com.impact.mods.RailCraft.carts.item.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface OpenableGUI {

  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
}
