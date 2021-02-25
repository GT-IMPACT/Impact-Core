package com.impact.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class Impact_API {

  /**
   * The OreGeneration Map
   */
  public static final Map<BiomeGenBase, ItemStack[]> sBiomeOres = new HashMap<>();

  /**
   * The Space Satellite Frequencies
   */
  public static final Map<String, int[]> sSpaceSatellite = new ConcurrentHashMap<>();

  /**
   * The MultiBlocks connect with Communication Tower Frequencies
   */
  public static final Map<String, int[]> sCommunicationTower = new ConcurrentHashMap<>();

  /**
   * The Space Elevator Frequencies
   */
  public static final Map<String, int[]> sElevatorSpace = new ConcurrentHashMap<>();
}