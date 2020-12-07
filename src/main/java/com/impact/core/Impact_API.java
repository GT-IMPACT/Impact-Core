package com.impact.core;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {

    /**
     * The OreGeneration Map
     */
    public static final Map<ItemStack[], BiomeGenBase> sBiomeOres = new HashMap<>();

    /**
     * The Space Satellite Frequencies
     */
    public static final Map<Integer, int[]> sSpaceSatellite = new ConcurrentHashMap<>();

    /**
     * The MultiBlocks connect with Communication Tower Frequencies
     */

    public static final Map<Integer, int[]> sCommunicationTower = new ConcurrentHashMap<>();

}