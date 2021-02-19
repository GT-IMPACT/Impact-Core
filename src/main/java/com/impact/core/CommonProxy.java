package com.impact.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

  public static void register_event(Object obj) {
    FMLCommonHandler.instance().bus().register(obj);
    MinecraftForge.EVENT_BUS.register(obj);
  }

  public void addTexturePage() {
  }

  public void registerRenderInfo() {
  }

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    return null;
  }

  public World getClientWorld() {
    return null;
  }

  public void registerRenderers() {
  }

  public void preInit() {
  }

  public void load() {
  }

  public void preload() {
  }

  public void onServerStarted() {
    Impact_API.sSpaceSatellite.clear();
  }

  public void onServerStopping() {
    Impact_API.sSpaceSatellite.clear();
  }
}