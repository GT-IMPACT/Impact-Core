package com.impact;

import static com.impact.core.Config.csv;
import static com.impact.core.Refstrings.MODID;
import static com.impact.core.impactLog.INFO;

import com.impact.client.gui.GUIHandler;
import com.impact.core.CommonProxy;
import com.impact.core.Config;
import com.impact.core.Refstrings;
import com.impact.events.PacketHandler;
import com.impact.events.TickHandler;
import com.impact.events.impactEvents;
import com.impact.loader.MainLoader;
import com.impact.mods.GregTech.enums.IRecipeAdder;
import com.impact.mods.GregTech.enums.RecipeAdder;
import com.impact.mods.GregTech.enums.Texture;
import com.impact.mods.NEI.OrePugin.helper.CSVMaker;
import com.impact.mods.NEI.OrePugin.helper.GT5OreLayerHelper;
import com.impact.mods.NEI.OrePugin.helper.GT5OreSmallHelper;
import com.impact.mods.RailCraft.carts.item.ChestCartModule;
import com.impact.mods.RailCraft.carts.item.events.Module;
import com.impact.network.ImpactNetwork;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.util.ArrayList;

@Mod(
    modid = MODID,
    name = Refstrings.NAME,
    version = Refstrings.VERSION,
    dependencies =
        "required-after:Forge@[10.13.2.1291,);")

public class impact {

  @SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
  public static CommonProxy proxy;

  @Mod.Instance(MODID)
  public static impact instance;
  public static String ModPackVersion = "1.0.1.0";
  public static Config mConfig;
  public static FMLEventChannel channel;
  public static IRecipeAdder I_RA;
  private static ArrayList<Module> MODULES_ENABLED = new ArrayList<Module>();

  public impact() {
    impact.I_RA = new RecipeAdder();
    Texture.Icons.VOID.name();
    new ImpactNetwork();
  }

  public static ArrayList<Module> getModules() {
    if (MODULES_ENABLED.isEmpty()) {
      MODULES_ENABLED.add(new ChestCartModule());
    }
    return MODULES_ENABLED;
  }

  @Mod.EventHandler
  public void onServerStarted(FMLServerStartedEvent aEvent) {
    proxy.onServerStarted();
  }

  @Mod.EventHandler
  public void onServerStopping(FMLServerStoppingEvent aEvent) {
    proxy.onServerStopping();
  }

  @Mod.EventHandler
  public void load(FMLInitializationEvent event) {
    MainLoader.load(event);
    new GUIHandler();
    INFO("MainLoader LOAD Loaded");
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    mConfig = new Config(new File("config/IMPACT/impact.cfg"));
    INFO("Config Loaded");

    MainLoader.preInit(event);
    INFO("MainLoader PREINIT Loaded ");
    //MainLoader.preInitClient();

    CommonProxy.register_event(new impactEvents());
    CommonProxy.register_event(new TickHandler());

    channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("Impact");
    channel.register(new PacketHandler());

    proxy.preload();
  }

  @Mod.EventHandler
  public void onLoadComplete(FMLLoadCompleteEvent event) {
    if (event.getSide() == Side.CLIENT) {
      new GT5OreLayerHelper();
      new GT5OreSmallHelper();
      if (csv) {
        new CSVMaker().run();
      }
    }
  }

  @Mod.EventHandler
  public void PostLoad(FMLPostInitializationEvent event) {
    MainLoader.postLoad(event);
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
