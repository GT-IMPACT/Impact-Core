package com.impact.mods.GalactiCraft.GG;

import bloodasp.galacticgreg.api.AsteroidBlockComb;
import bloodasp.galacticgreg.api.Enums;
import bloodasp.galacticgreg.api.GTOreTypes;
import bloodasp.galacticgreg.api.ModContainer;
import bloodasp.galacticgreg.api.ModDBMDef;
import bloodasp.galacticgreg.api.ModDimensionDef;
import bloodasp.galacticgreg.api.SpecialBlockComb;
import com.impact.register.IGM_Blocks;
import com.impact.systems.SolarSystems.moon.deimos.dimension.ChunkProviderDeimos;
import com.impact.systems.SolarSystems.moon.phobos.dimension.ChunkProviderPhobos;
import com.impact.systems.SolarSystems.planet.haumea.dimension.ChunkProviderHaumea;
import com.impact.systems.SolarSystems.planet.makemake.dimension.ChunkProviderMakemake;
import com.impact.systems.SolarSystems.planet.oberon.dimension.ChunkProviderOberon;
import com.impact.systems.SolarSystems.planet.proteus.dimension.ChunkProviderProteus;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.world.worldengine.WE_ChunkProvider;
import galaxyspace.systems.BarnardsSystem.core.registers.blocks.BRBlocks;
import galaxyspace.systems.BarnardsSystem.planets.barnardaC.dimension.ChunkProviderBarnardaC;
import galaxyspace.systems.SolarSystem.moons.callisto.dimension.ChunkProviderCallisto;
import galaxyspace.systems.SolarSystem.moons.enceladus.dimension.ChunkProviderEnceladus;
import galaxyspace.systems.SolarSystem.moons.europa.dimension.ChunkProviderEuropa;
import galaxyspace.systems.SolarSystem.moons.ganymede.dimension.ChunkProviderGanymede;
import galaxyspace.systems.SolarSystem.moons.io.dimension.ChunkProviderIo;
import galaxyspace.systems.SolarSystem.moons.miranda.dimension.ChunkProviderMiranda;
import galaxyspace.systems.SolarSystem.moons.titan.dimension.ChunkProviderTitan;
import galaxyspace.systems.SolarSystem.moons.triton.dimension.ChunkProviderTriton;
import galaxyspace.systems.SolarSystem.planets.ceres.dimension.ChunkProviderCeres;
import galaxyspace.systems.SolarSystem.planets.kuiperbelt.dimension.ChunkProviderKuiper;
import galaxyspace.systems.SolarSystem.planets.mercury.dimension.ChunkProviderMercury;
import galaxyspace.systems.SolarSystem.planets.pluto.dimension.ChunkProviderPluto;
import galaxyspace.systems.SolarSystem.planets.venus.dimension.ChunkProviderVenus;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.ChunkProviderEnd;

/**
 * In this class, you'll find everything you need in order to tell GGreg what to do and where.
 * Everything is done in here. If you're trying to use anything else, you're probably doing
 * something wrong (Or I forgot to add it. In that case, find me on github and create an issue
 * please)
 */
public class SpaceDimRegisterer {

  private static Method registerModContainer;

  /**
   * Use loose binding of the register-method. Should be enough to provide support for GGreg without
   * the requirement to have it in a modpack at all
   */
  public static void registerModContainer(ModContainer pModContainer) {
    try {
      registerModContainer.invoke(null, pModContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Just a helper to convert a single element to a list
   */
  private List<ModDBMDef> singleToList(ModDBMDef... pDef) {
    List<ModDBMDef> tLst = new ArrayList<>();
      for (int i = 0; i < pDef.length; i++) {
          tLst.add(pDef[i]);
      }
    return tLst;
  }

  /**
   * Try to get the instance of GalacticGregs registry in order to register stuff
   */
  public boolean Init() {
    try {
      Class<?> gGregRegistry = Class.forName("bloodasp.galacticgreg.registry.GalacticGregRegistry");
      registerModContainer = gGregRegistry.getMethod("registerModContainer", ModContainer.class);

      return true;
    } catch (Exception e) {
      // GalacticGreg is not installed or something is wrong
      return false;
    }
  }

  public void Register() {
    registerModContainer(Setup_Vanilla());
    registerModContainer(Setup_GalactiCraftCore());
    registerModContainer(Setup_GalactiCraftPlanets());
    registerModContainer(Setup_GalaxySpace());
    registerModContainer(Setup_ImpactGalacticModule());
  }

  /**
   * Vanilla MC (End Asteroids)
   */
  private ModContainer Setup_Vanilla() {
    // --- Mod Vanilla (Heh, "mod")
    ModContainer modMCVanilla = new ModContainer("Vanilla");

    // If you happen to have an asteroid dim, just skip the blocklist, and setDimensionType() to DimensionType.Asteroid
    // also don't forget to add at least one asteroid type, or nothing will generate!
    ModDimensionDef dimEndAsteroids = new ModDimensionDef("EndAsteroids", ChunkProviderEnd.class,
        Enums.DimensionType.Asteroid);

    dimEndAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.Netherrack));
    dimEndAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.RedGranite));
    dimEndAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.BlackGranite));
    dimEndAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.EndStone));

    // These Blocks will randomly be generated
    dimEndAsteroids.addSpecialAsteroidBlock(new SpecialBlockComb(Blocks.glowstone));
    dimEndAsteroids.addSpecialAsteroidBlock(
        new SpecialBlockComb(Blocks.lava, Enums.AllowedBlockPosition.AsteroidCore));

    modMCVanilla.addDimensionDef(dimEndAsteroids);

    return modMCVanilla;
  }

  /**
   * Mod GalactiCraft
   */
  private ModContainer Setup_GalactiCraftCore() {
    ModContainer modGCraftCore = new ModContainer("GalacticraftCore");
    ModDBMDef DBMMoon = new ModDBMDef("tile.moonBlock", 4);

    ModDimensionDef tMoonDim = new ModDimensionDef("Moon",
        "micdoodle8.mods.galacticraft.core.world.gen.ChunkProviderMoon", Enums.DimensionType.Planet,
        singleToList(DBMMoon));
    modGCraftCore.addDimensionDef(tMoonDim);

    return modGCraftCore;
  }


  /**
   * As GalactiCraftPlanets is an optional mod, don't hardlink it here
   */
  private ModContainer Setup_GalactiCraftPlanets() {
    ModContainer modGCraftPlanets = new ModContainer("GalacticraftMars");
    ModDBMDef DBMMars = new ModDBMDef("tile.mars", 9);
    ModDimensionDef dimMars = new ModDimensionDef("Mars",
        "micdoodle8.mods.galacticraft.planets.mars.world.gen.ChunkProviderMars",
        Enums.DimensionType.Planet, singleToList(DBMMars));

    // Overwrite ore blocks on mars with red granite ones. This will default to regular stone if not set
    dimMars.setStoneType(GTOreTypes.RedGranite);
    modGCraftPlanets.addDimensionDef(dimMars);

    ModDimensionDef dimAsteroids = new ModDimensionDef("Asteroids",
        "micdoodle8.mods.galacticraft.planets.asteroids.world.gen.ChunkProviderAsteroids",
        Enums.DimensionType.Asteroid);
    dimAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.BlackGranite));
    dimAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.RedGranite));
    dimAsteroids.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.Netherrack));
    modGCraftPlanets.addDimensionDef(dimAsteroids);

    return modGCraftPlanets;
  }

  /**
   * Mod GalaxySpace by BlesseNtumble
   */
  private ModContainer Setup_GalaxySpace() {
    // First, we create a mod-container that will be populated with dimensions later.
    // The Name must match your modID, as it is checked if this mod is loaded, in order
    // to enable/disable the parsing/registering of dimensions
    ModContainer modCGalaxySpace = new ModContainer("GalaxySpace");

    // Now lets first define a block here for our dimension. You can add the modID, but you don't have to.
    // It will automatically add the mods name that is defined in the modcontainer.
    final ModDBMDef DBMCeres = new ModDBMDef(GSBlocks.CeresBlocks, 1);
    final ModDBMDef DBMIO = new ModDBMDef(GSBlocks.IoBlocks, 2);
    final ModDBMDef DBMGanymede = new ModDBMDef(GSBlocks.GanymedeBlocks, 1);
    final ModDBMDef DBMCallisto = new ModDBMDef(GSBlocks.CallistoBlocks, 1);
    final ModDBMDef DBMVenus = new ModDBMDef(GSBlocks.VenusBlocks, 1);
    final ModDBMDef DBMMercury = new ModDBMDef(GSBlocks.MercuryBlocks, 2);
    final ModDBMDef DBMEnceladus = new ModDBMDef(GSBlocks.EnceladusBlocks, 1);

    final ModDBMDef DBMPluto = new ModDBMDef(GSBlocks.PlutoBlocks, 5);

    List<ModDBMDef> DBMbarnardaC = new ArrayList<>();
    DBMbarnardaC.add(new ModDBMDef(BRBlocks.BarnardaCBlocks, 1));
    DBMbarnardaC.add(new ModDBMDef(Blocks.stone));

    List<ModDBMDef> DBMTitan = new ArrayList<>();
    DBMTitan.add(new ModDBMDef(GSBlocks.TitanBlocks, 2));

    List<ModDBMDef> DBMTriton = new ArrayList<>();
    DBMTriton.add(new ModDBMDef(GSBlocks.TritonBlocks, 3));
    DBMTriton.add(new ModDBMDef("TritonSubGrunt"));

    final ModDBMDef DBMMiranda = new ModDBMDef(GSBlocks.MirandaBlocks, 2);

    List<ModDBMDef> DBMEuropa = new ArrayList<>();
    DBMEuropa.add(new ModDBMDef(GSBlocks.EuropaBlocks,
        1));            //Europa Ice Layer ~55-65 without top layer
    DBMEuropa.add(new ModDBMDef(Blocks.water));
    DBMEuropa.add(new ModDBMDef(Blocks.flowing_water));
    DBMEuropa.add(new ModDBMDef(Blocks.ice));                //Generates directly over bedrock
    DBMEuropa.add(new ModDBMDef(Blocks.packed_ice));        //Generates directly over bedrock
    DBMEuropa.add(new ModDBMDef(GSBlocks.EuropaUnderwaterGeyser)); //Generates directly over bedrock

    // Now define the available dimensions, and their chunkprovider.
    // Same as above, to not have any dependency in your code, you can just give it a string.
    // But it's better to use the actual ChunkProvider class. The Name is used for the GalacticGreg config file.
    // The resulting config setting will be: <ModID>_<Name you give here as arg0>_false = false
    // make sure to never change this name once you've generated your config files, as it will overwrite everything!

    // 05.12.2020 GalaxySpace Impact Edition
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Pluto", ChunkProviderPluto.class, Enums.DimensionType.Planet,
            singleToList(DBMPluto)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Triton", ChunkProviderTriton.class, Enums.DimensionType.Planet,
            DBMTriton));
//    modCGalaxySpace.addDimensionDef(
//        new ModDimensionDef("Titan", ChunkProviderTitan.class, Enums.DimensionType.Planet,
//            DBMTitan));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Titan", WE_ChunkProvider.class, Enums.DimensionType.Planet, DBMTitan));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Callisto", ChunkProviderCallisto.class, Enums.DimensionType.Planet,
            singleToList(DBMCallisto)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Ganymede", ChunkProviderGanymede.class, Enums.DimensionType.Planet,
            singleToList(DBMGanymede)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Ceres", ChunkProviderCeres.class, Enums.DimensionType.Planet,
            singleToList(DBMCeres)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Enceladus", ChunkProviderEnceladus.class, Enums.DimensionType.Planet,
            singleToList(DBMEnceladus)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Io", ChunkProviderIo.class, Enums.DimensionType.Planet,
            singleToList(DBMIO)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Europa", ChunkProviderEuropa.class, Enums.DimensionType.Planet,
            DBMEuropa));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Venus", ChunkProviderVenus.class, Enums.DimensionType.Planet,
            singleToList(DBMVenus)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Mercury", ChunkProviderMercury.class, Enums.DimensionType.Planet,
            singleToList(DBMMercury)));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("BarnardC", ChunkProviderBarnardaC.class, Enums.DimensionType.Planet,
            DBMbarnardaC));
    modCGalaxySpace.addDimensionDef(
        new ModDimensionDef("Miranda", ChunkProviderMiranda.class, Enums.DimensionType.Planet,
            singleToList(DBMMiranda)));

    ModDimensionDef dimKupierBelt = new ModDimensionDef("Kuiperbelt", ChunkProviderKuiper.class,
        Enums.DimensionType.Asteroid);

    dimKupierBelt.setDimensionType(Enums.DimensionType.Asteroid);
    dimKupierBelt.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.RedGranite));
    dimKupierBelt.addAsteroidMaterial(new AsteroidBlockComb(GTOreTypes.BlackGranite));
    modCGalaxySpace.addDimensionDef(dimKupierBelt);
    return modCGalaxySpace;
  }

  private ModContainer Setup_ImpactGalacticModule() {
    ModContainer ImpactGalacticModule = new ModContainer("Impact|GalacticModule");
    final ModDBMDef DBMPhobos = new ModDBMDef(IGM_Blocks.PhobosBlocks, 1);
    final ModDBMDef DBMDeimos = new ModDBMDef(IGM_Blocks.DeimosBlocks, 1);
    final ModDBMDef DBMMakemake = new ModDBMDef(IGM_Blocks.MakeMakeBlocks, 1);
    final ModDBMDef DBMHaumea = new ModDBMDef(IGM_Blocks.HaumeaBlocks);
    final ModDBMDef DBMOberon = new ModDBMDef(IGM_Blocks.OberonBlocks, 2);
    final ModDBMDef DBMProteus = new ModDBMDef(IGM_Blocks.ProteusBlocks, 2);

    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Deimos", ChunkProviderDeimos.class, Enums.DimensionType.Planet,
            singleToList(DBMDeimos)));
    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Phobos", ChunkProviderPhobos.class, Enums.DimensionType.Planet,
            singleToList(DBMPhobos)));
    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Makemake", ChunkProviderMakemake.class, Enums.DimensionType.Planet,
            singleToList(DBMMakemake)));
    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Haumea", ChunkProviderHaumea.class, Enums.DimensionType.Planet,
            singleToList(DBMHaumea)));
    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Oberon", ChunkProviderOberon.class, Enums.DimensionType.Planet,
            singleToList(DBMOberon)));
    ImpactGalacticModule.addDimensionDef(
        new ModDimensionDef("Proteus", ChunkProviderProteus.class, Enums.DimensionType.Planet,
            singleToList(DBMProteus)));

    return ImpactGalacticModule;
  }

}
