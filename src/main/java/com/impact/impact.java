package com.impact;

import com.impact.System.Config;
import com.impact.System.Refstrings;
import com.impact.api.enums.Textures;
import com.impact.loader.MainLoader;
import com.impact.System.CommonProxy;
import com.impact.loader.ModLoader;
import com.impact.mods.GregTech.GTregister.GT_ItemRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_BasicRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_MultiRegister;
import com.impact.mods.GregTech.GTregister.GT_WorldGenRegister;
import com.impact.mods.GregTech.casings.GT_Loader_Casings;
import com.impact.recipes.HandRecipe;
import com.impact.recipes.OpenComputersRecipe;
import com.impact.recipes.debug.DEBUG_Recipe;
import com.impact.recipes.machines.*;
import com.impact.util.SendUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import com.impact.System.LoginHandler;

import java.io.File;

import static com.impact.System.impactLog.INFO;

@Mod (
		modid = Refstrings.MODID,
        name = Refstrings.NAME,
        version = Refstrings.VERSION,
        dependencies = 
        	"required-after:Forge@[10.13.2.1291,);")

public class impact {
	
	@SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
    public static CommonProxy proxy;
	
	@Mod.Instance(Refstrings.MODID)
    public static impact instance;
    public static SendUtils SendUtils_instance = new SendUtils();
    public static String ModPackVersion = "1.0 RELEASE";
    public static Config mConfig;


    public impact(){
        Textures.Icons.VOID.name();
    }

    @Mod.EventHandler
    public void PreLoad(FMLPreInitializationEvent PreEvent) {
        FMLCommonHandler.instance().bus().register(new LoginHandler());
        INFO("LoginHandler Loaded");
    }
	
	@Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MainLoader.load();
        INFO("MainLoader LOAD Loaded");
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mConfig = new Config(new File("config/IMPACT/impact.cfg"));
        INFO("Config Loaded");

        MainLoader.preInit();
        INFO("MainLoader PREINIT Loaded ");
        //MainLoader.preInitClient();
    }

	@Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent PostEvent) {
        new GT_ItemRegister().run();
        new GT_Loader_Casings().run();
        new GT_Machines_MultiRegister().run();
        new GT_Machines_BasicRegister().run();
        new GT_WorldGenRegister().run();
        new ModLoader().run();
        new HandRecipe().run();
        new DEBUG_Recipe().run();
        new CentrifugeRecipe().run();
        new PulveriserRecipe().run();
        new LaserEngraverRecipe();
        new FormingPressRecipe().run();
        new ChemicalBathRecipe().run();
        new AssemblerRecipe().run();
        new FreezSolidifierRecipe().run();
        new Printer3DRecipe().run();
        new BlastSmelterRecipe().run();
        new ComponentAssemblerRecipe().run();
        new CompessorRecipe().run();
        new AlloySmelterRecipe().run();
        new MixerRecipe().run();
        new EBFRecipe().run();
        new FluidCannerRecipe().run();
        new CircuitAssemblerRecipe().run();
        new FarmRecipe().run();
        new CuttingRecipe().run();
        new VacuumFreezerRecipe().run();
        new AssemblyLineRecipe().run();
        new OpenComputersRecipe().run();
        new ForgeHammerRecipe().run();
        new FluidExtractorRecipe().run();
        new ExtruderRecipe().run();
        new ImplosionCompressorRecipe().run();
        new FluidSolidifierRecipe().run();
        new AutoclaveRecipe().run();
        new BreweryRecipe().run();
        new ExtractorRecipe().run();
        new ChemicalReactorRecipe().run();
        new SifterRecipe().run();
        new LatheRecipe().run();
        new WiremillRecipe().run();
        new ElectrolyzerRecipe().run();
        new WireassemblerRecipe().run();
        new FusionRecipe().run();
        new ArcFurnaceRecipe().run();
        MainLoader.postLoad();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
        MainLoader.postInit();
    }

    @Mod.EventHandler
    public void Init(FMLPostInitializationEvent init) {
        MainLoader.Init();
    }

    @Mod.EventHandler
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        MainLoader.onPreLoad();
    }
}
