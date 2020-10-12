package com.impact.mods.GalactiCraft;

import com.impact.mods.GalactiCraft.planets.jupiter.WorldProviderJupiter;
import galaxyspace.SolarSystem.SolarSystemPlanets;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;

public class GC_Register {

    public static void init() {
        SolarSystemPlanets.planetJupiter.setTierRequired(4).setDimensionInfo(100, WorldProviderJupiter.class).atmosphereComponent(IAtmosphericGas.HYDROGEN).atmosphereComponent(IAtmosphericGas.HELIUM);
        GC_Register.registerPlanet();
    }

    public static void registerPlanet() {
        GalaxyRegistry.registerPlanet(SolarSystemPlanets.planetJupiter);
        GalacticraftRegistry.registerTeleportType(WorldProviderJupiter.class, new WorldProviderJupiter());
    }
}
