package com.impact.core;

import com.impact.mods.gregtech.enums.DropsBlock;
import com.impact.util.PositionObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {

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
}
