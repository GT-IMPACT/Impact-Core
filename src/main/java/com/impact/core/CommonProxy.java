package com.impact.core;

import com.impact.client.render.BlockHint;
import com.impact.command.Command_FixBQ;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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

  public void init() {
  }

  public void postInit() {
  }
  
  public void hint_particle(World w, int x, int y, int z, Block block, int meta) {
  }
  
  public void hint_particle(World w, int x, int y, int z, Block block, int meta, int age) {
  }
  
  public void hint_particle(World w, int x, int y, int z, double xx, double yy, double zz, Block block, int meta, int age) {
  }

  public void onServerStarted() {
    Impact_API.sSpaceSatellite.clear();
    Impact_API.sElevatorSpace.clear();
    Impact_API.sAerostat.clear();
  }

  public void onServerStopping() {
    Impact_API.sSpaceSatellite.clear();
    Impact_API.sElevatorSpace.clear();
    Impact_API.sAerostat.clear();
  }
}