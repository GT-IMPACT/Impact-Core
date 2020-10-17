//package com.impact.mods.GalactiCraft;
//
//import com.impact.core.Refstrings;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.ceres.TeleportTypeCeres;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.ceres.WorldProviderCeres;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.venus.TeleportTypeVenus;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.venus.WorldProviderVenus;
//import com.impact.mods.GalactiCraft.api.BodiesHelper;
//import com.impact.mods.GalactiCraft.api.IBodiesHandler;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.jupiter.WorldProviderJupiter;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.mars.WorldProviderMars;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.mercury.TeleportTypeMercury;
//import com.impact.mods.GalactiCraft.SolarSystem.planets.mercury.WorldProviderMercury;
//import cpw.mods.fml.common.event.FMLInitializationEvent;
//import cpw.mods.fml.common.event.FMLPostInitializationEvent;
//import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
//import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
//import micdoodle8.mods.galacticraft.api.galaxies.Moon;
//import micdoodle8.mods.galacticraft.api.galaxies.Planet;
//import micdoodle8.mods.galacticraft.api.vector.Vector3;
//import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
//import micdoodle8.mods.galacticraft.core.GalacticraftCore;
//import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
//import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
//import net.minecraft.util.ResourceLocation;
//
//
//public class SolarSystemRegister implements IBodiesHandler {
//
//    public static Planet pMercury, pVenus, pEarth, pMars, pCeres, pAsteroids, pJupiter, pSaturn, pUranus, pNeptune, pPluto, pKuiperBelt, pHaumea, pMakemake, pEris;
//    public static Moon EarthMoon,
//            MarsPhobos, MarsDeimos,
//            JupiterIo, JupiterEuropa, JupiterGanymede, JupiterCallisto,
//            SaturnMimas, SaturnEnceladus, SaturnTethys, SaturnDione, SaturnRheya, SaturnTitan, SaturnIapetus,
//            UranusMiranda, UranusAriel, UranusUmbriel, UranusTitania, UranusOberon,
//            NeptuneProteus, NeptuneTriton,
//            PlutoCharon;
//
//    public static void registryCelestial() {
//        BodiesHelper.BodiesData data = new BodiesHelper.BodiesData(BodiesHelper.yellow + " " + BodiesHelper.dwarf, 28.088F, 0, 999.0F, 0.0F, 0L, false, false);
//        BodiesHelper.registerBodyWithClass(GalacticraftCore.solarSystemSol.getMainStar(), data);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(3.8F), 0, 6.0F, 0.0F, 176000L, false, true);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pMercury, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(8.88F), 92, 12.0F, 1.2F, 182000L, false, false);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pVenus, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(10.0F), 1, 1.0F, 1.0F, 24000L, true, false);
//        BodiesHelper.registerBody(GalacticraftCore.planetOverworld, data, false);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(4.0F), 0, -5.0F, 1.2F, 9553L, true, false);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pMars, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.062F, 0, 0.0F, 0.0F, 192000L, false, true);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(GalacticraftCore.moonMoon, data, false);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(2.37F), 0, -1.5F, 0.0F, 10000L, false, true);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pCeres, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.0F, 0, -2.0F, 0.0F, 1L, false, false);
//        BodiesHelper.registerBody(AsteroidsModule.planetAsteroids, data, false);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(8.375F), 100, -5.0F, 10.0F, 9000L, false, false);
//        BodiesHelper.registerBody(pJupiter, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(7.37F), 100, -5.0F, 10.0F, 11000L, false, false);
//        BodiesHelper.registerBody(pSaturn, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(8.61F), 100, -5.0F, 10.0F, 16000L, false, false);
//        BodiesHelper.registerBody(pUranus, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(8.547F), 100, -8.0F, 10.0F, 18000L, false, false);
//        BodiesHelper.registerBody(pNeptune, data, true);
//
//        data = new BodiesHelper.BodiesData(null, BodiesHelper.calculateGravity(2.62F), 0, -5.0F, 0.0F, 98000L, false, true);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pPluto, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.0F, 0, -6.0F, 0.0F, 0L, false, true);
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitHelmet, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitPlate, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitLeg, 1, 32767));
//        //data.addItemStack(new ItemStack(GSItems.SpacesuitBoots, 1, 32767));
//        BodiesHelper.registerBody(pKuiperBelt, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.068F, 0, -1.0F, 0.0F, 12000L, false, false);
//        BodiesHelper.registerBody(MarsPhobos, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.064F, 0, -1.0F, 0.0F, 24000L, false, false);
//        BodiesHelper.registerBody(MarsDeimos, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.052F, 0, 2.0F, 0.0F, 42000L, false, true);
//        BodiesHelper.registerBody(JupiterIo, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.062F, 0, -3.0F, 0.0F, 58000L, false, true);
//        BodiesHelper.registerBody(JupiterEuropa, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.057F, 0, -2.0F, 0.0F, 102000L, false, false);
//        BodiesHelper.registerBody(JupiterGanymede, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.054F, 0, -3.0F, 0.0F, 154000L, false, true);
//        BodiesHelper.registerBody(JupiterCallisto, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.058F, 0, -2.5F, 0.0F, 32000L, false, true);
//        BodiesHelper.registerBody(SaturnEnceladus, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.056F, 5, -3.5F, 0.0F, 105500L, false, false);
//        BodiesHelper.registerBody(SaturnTitan, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.057F, 0, -4.0F, 0.0F, 33500L, false, true);
//        BodiesHelper.registerBody(UranusMiranda, data, true);
//
//        data = new BodiesHelper.BodiesData(null, 0.052F, 0, -4.0F, 0.0F, 28500L, false, false);
//        BodiesHelper.registerBody(NeptuneTriton, data, true);
//
//        BodiesHelper.BodiesData unreachableData = new BodiesHelper.BodiesData(null, 0.0F, 0, 0.0F, 0.0F, 0L, false, false);
//        BodiesHelper.registerBody(UranusOberon, unreachableData, true);
//        BodiesHelper.registerBody(NeptuneProteus, unreachableData, true);
//        BodiesHelper.registerBody(SaturnMimas, unreachableData, true);
//        BodiesHelper.registerBody(SaturnTethys, unreachableData, true);
//        BodiesHelper.registerBody(SaturnDione, unreachableData, true);
//        BodiesHelper.registerBody(SaturnRheya, unreachableData, true);
//        BodiesHelper.registerBody(SaturnIapetus, unreachableData, true);
//        BodiesHelper.registerBody(UranusAriel, unreachableData, true);
//        BodiesHelper.registerBody(UranusUmbriel, unreachableData, true);
//        BodiesHelper.registerBody(UranusTitania, unreachableData, true);
//        BodiesHelper.registerBody(PlutoCharon, unreachableData, true);
//    }
//
//    private static void registryTeleport() {
//        GalacticraftRegistry.registerTeleportType(WorldProviderJupiter.class, new WorldProviderJupiter());
//        GalacticraftRegistry.registerTeleportType(WorldProviderMars.class, new WorldProviderMars());
//        GalacticraftRegistry.registerRocketGui(WorldProviderMars.class, new ResourceLocation(MarsModule.ASSET_PREFIX, "textures/gui/marsRocketGui.png"));
//        GalacticraftRegistry.registerTeleportType(WorldProviderMercury.class, new TeleportTypeMercury());
//        GalacticraftRegistry.registerTeleportType(WorldProviderVenus.class, new TeleportTypeVenus());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new WorldProviderPluto());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderKuiper.class, new WorldProviderKuiper());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderIo.class, new WorldProviderIo());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderEuropa.class, new WorldProviderEuropa());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderGanymede.class, new WorldProviderGanymede());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderCallisto.class, new WorldProviderCallisto());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderEnceladus.class, new WorldProviderEnceladus());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderTitan_WE.class, new WorldProviderTitan_WE());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderMiranda.class, new WorldProviderMiranda());
//        //GalacticraftRegistry.registerTeleportType(WorldProviderTriton.class, new WorldProviderTriton());
//    }
//
//    public static void registerPlanet(){
//        GalacticraftCore.solarSystemSol.setMapPosition(new Vector3(0.0D, 0.0D, 0.0D));
//
//        pMercury = BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "mercury", Refstrings.MODID, WorldProviderMercury.class, GC_Config.dimID_Mercury, 4, 1.45F, 0.5F, 0.5F, 0.24096386F);
//        pVenus = (Planet) BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "venus", Refstrings.MODID, WorldProviderVenus.class, GC_Config.dimID_Venus, 4, 2.0F, 1.0F, 0.75F, 0.6152793F).atmosphereComponent(IAtmosphericGas.CO2).atmosphereComponent(IAtmosphericGas.NITROGEN);
//        pCeres = (Planet) BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "ceres", Refstrings.MODID, null /*WorldProviderCeres.class*/, GC_Config.dimID_Ceres, 3, 2.0F, 0.5F, 1.7F, 15.0F).setRingColorRGB(0.0F, 0.0F, 0.0F);
//
//        pMars = (Planet) BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "mars", Refstrings.MODID, WorldProviderMars.class, GC_Config.dimID_Mars, 2, 0.1667F, 0.5319F, 1.25F, 1.8811610076670317634173055859803F).setRingColorRGB(0.0F, 0.4F, 0.9F);
//
//        pJupiter = BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "jupiter", Refstrings.MODID, WorldProviderJupiter.class, GC_Config.dimID_Jupiter, 4, 3.1415927F, 1.0F, 2.0F, 11.861994F);
//        pSaturn = BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "saturn", Refstrings.MODID, null, -1, -1, 1.5707964F, 1.0F, 2.25F, 29.463308F);
//        pUranus = BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "uranus", Refstrings.MODID, null, -1, -1, 3.1415927F, 1.0F, 2.5F, 84.06353F);
//        pNeptune = BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "neptune", Refstrings.MODID, null, -1, -1, 1.0F, 1.0F, 2.75F, 164.84119F);
//
//        pPluto = (Planet) BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "pluto", Refstrings.MODID, null, -1, 8, 0.1F, 0.5F, 3.0F, 5.0F).atmosphereComponent(IAtmosphericGas.NITROGEN);
//        pKuiperBelt = (Planet) BodiesHelper.registerPlanet(GalacticraftCore.solarSystemSol, "kuiperbelt", Refstrings.MODID, null, -1, 8, 1.5F, 0.5F, 3.25F, 300.0F).setRingColorRGB(1.1F, 0.0F, 0.0F);
//
//
//        MarsPhobos = BodiesHelper.registerMoon(pMars, "phobos", Refstrings.MODID, null, -1, 2, 1.0F, 0.0017F, 8.0F, 100.0F);
//        MarsDeimos = BodiesHelper.registerMoon(pMars, "deimos", Refstrings.MODID, null, -1, -1, 1.0F, 0.0017F, 16.0F, 300.0F);
//
//        JupiterIo = BodiesHelper.registerMoon(pJupiter, "io", Refstrings.MODID, null, -1, 4, 1.0F, 0.0017F, 10.0F, 50.0F);
//        JupiterEuropa = BodiesHelper.registerMoon(pJupiter, "europa", Refstrings.MODID, null, -1, 4, 1.0F, 0.0017F, 15.0F, 100.0F);
//        JupiterGanymede = BodiesHelper.registerMoon(pJupiter, "ganymede", Refstrings.MODID, null, -1, 4, 1.0F, 0.0017F, 20.0F, 150.0F);
//        JupiterCallisto = BodiesHelper.registerMoon(pJupiter, "callisto", Refstrings.MODID, null, -1, 4, 1.0F, 0.0017F, 30.0F, 200.0F);
//
//        SaturnMimas = BodiesHelper.registerMoon(pSaturn, "mimas", Refstrings.MODID, null, -1, -1, 1.5707964F, 0.0017F, 10.0F, 20.0F);
//        SaturnEnceladus = BodiesHelper.registerMoon(pSaturn, "enceladus", Refstrings.MODID, null, -1, 5, 1.0471976F, 0.0017F, 15.0F, 50.0F);
//        SaturnTethys = BodiesHelper.registerMoon(pSaturn, "tethys", Refstrings.MODID, null, -1, -1, 3.1415927F, 0.0017F, 20.0F, 120.0F);
//        SaturnDione = BodiesHelper.registerMoon(pSaturn, "dione", Refstrings.MODID, null, -1, -1, 0.7853982F, 0.0017F, 25.0F, 180.0F);
//        SaturnRheya = BodiesHelper.registerMoon(pSaturn, "rheya", Refstrings.MODID, null, -1, -1, 1.0471976F, 0.0017F, 30.0F, 220.0F);
//        SaturnTitan = (Moon) BodiesHelper.registerMoon(pSaturn, "titan", Refstrings.MODID, null, -1, 5, 0.62831855F, 0.0017F, 35.0F, 280.0F).atmosphereComponent(IAtmosphericGas.NITROGEN).atmosphereComponent(IAtmosphericGas.METHANE).atmosphereComponent(IAtmosphericGas.HYDROGEN);
//        SaturnIapetus = BodiesHelper.registerMoon(pSaturn, "iapetus", Refstrings.MODID, null, -1, -1, 3.1415927F, 0.0017F, 40.0F, 350.0F);
//
//        UranusMiranda = BodiesHelper.registerMoon(pUranus, "miranda", Refstrings.MODID, null, -1, 6, 3.1415927F, 0.0017F, 10.0F, 20.0F);
//        UranusAriel = BodiesHelper.registerMoon(pUranus, "ariel", Refstrings.MODID, null, -1, -1, 1.5707964F, 0.0017F, 15.0F, 50.0F);
//        UranusUmbriel = BodiesHelper.registerMoon(pUranus, "umbriel", Refstrings.MODID, null, -1, -1, 1.0471976F, 0.0017F, 20.0F, 120.0F);
//        UranusTitania = BodiesHelper.registerMoon(pUranus, "titania", Refstrings.MODID, null, -1, -1, 0.7853982F, 0.0017F, 25.0F, 180.0F);
//        UranusOberon = BodiesHelper.registerMoon(pUranus, "oberon", Refstrings.MODID, null, -1, -1, 0.62831855F, 0.0017F, 30.0F, 200.0F);
//
//        NeptuneProteus = BodiesHelper.registerMoon(pNeptune, "proteus", Refstrings.MODID, null, -1, -1, 3.1415927F, 0.0017F, 10.0F, 50.0F);
//        NeptuneTriton = (Moon) BodiesHelper.registerMoon(pNeptune, "triton", Refstrings.MODID, null, -1, 6, 1.5707964F, 0.0017F, 25.0F, -200.0F).atmosphereComponent(IAtmosphericGas.NITROGEN);
//
//        PlutoCharon = BodiesHelper.registerMoon(pPluto, "charon", Refstrings.MODID, null, -1, -1, 1.5707964F, 0.0017F, 15.0F, 50.0F);
//
//
//        GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
//        GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);
//        AsteroidsModule.planetAsteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
//
//        registryCelestial();
//        registryTeleport();
//    }
//
//
//    @Override
//    public void init(FMLInitializationEvent event) {
//
//    }
//
//    @Override
//    public void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent) {
//        registerPlanet();

//      final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
//      inputMap.put(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel,40), 40);
//      inputMap.put(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel,20), 20);
//      inputMap.put(new ItemStack(IGlassBlock, 20), 20);
//      inputMap.put(new ItemStack(GCItems.rocketEngine, 4), 4);
//      GalacticraftRegistry.registerSpaceStation(new SpaceStationType(ConfigManagerCore.idDimensionOverworldOrbit, 0, new SpaceStationRecipe(inputMap)));
//      if (GSConfigDimensions.enableVenusSS)
//      GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.dimensionIDVenusOrbit, GSConfigDimensions.dimensionIDVenus, new SpaceStationRecipe(inputMap)));
//      if (GSConfigDimensions.enableMarsSS)
//      GalacticraftRegistry.registerSpaceStation(new SpaceStationType(GSConfigDimensions.dimensionIDMarsOrbit, ConfigManagerMars.dimensionIDMars, new SpaceStationRecipe(inputMap)));

//    }
//
//
//    @Override
//    public void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent) {
//
//    }
//}
