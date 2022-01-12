package com.impact.core;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.mods.gregtech.enums.DropsBlock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {
	
	/**
	 * Ore Regions
	 */
	public static final Map<Integer, OresRegionGenerator> regionsOres = new HashMap<>();
	public static final Map<Integer, OreVein> registerVeins = new HashMap<>();
	
	/**
	 * Drops from harvest blocks
	 */
	public static final List<DropsBlock> dropsFromBlock = new ArrayList<>();
	
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
	
	/**
	 * The Space Elevator Frequencies
	 */
	public static final Map<String, int[]> sAerostat = new LinkedHashMap<>();
}