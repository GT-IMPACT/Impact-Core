package com.impact.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {

    /**
     * The Space Satellite Frequencies
     */
    public static final Map<Integer, int[]> sSpaceSatellite = new ConcurrentHashMap<>();

    /**
     * The MultiBlocks connect with Communication Tower Frequencies
     */
    public static final Map<Integer, int[]> sCommunicationTower = new ConcurrentHashMap<>();


}
