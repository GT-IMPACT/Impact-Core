package com.impact.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {
	
	/**
	 * int[]  - dimension, xChunk, zChunk, tier
	 * String - BiomesOreGenerator.name
	 */
	public static final Map<int[], String> sOreInChunk = new ConcurrentHashMap<>();
	
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