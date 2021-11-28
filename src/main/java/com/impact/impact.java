package com.impact;

import com.impact.client.gui.GUIHandler;
import com.impact.command.Command_FixBQ;
import com.impact.core.CommonProxy;
import com.impact.core.Config;
import com.impact.core.Refstrings;
import com.impact.core.SaveManager;
import com.impact.events.EventDropBlock;
import com.impact.events.TickHandler;
import com.impact.events.impactEvents;
import com.impact.loader.MainLoader;
import com.impact.mods.gregtech.enums.IRecipeAdder;
import com.impact.mods.gregtech.enums.RecipeAdder;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.railcraft.carts.item.ChestCartModule;
import com.impact.mods.railcraft.carts.item.events.Module;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.FMLEventChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static com.impact.core.Refstrings.MODID;
import static com.impact.core.impactLog.INFO;

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
    public static String ModPackVersion = "1.0.1.7-dev";
    public static Config mConfig;
    public static IRecipeAdder I_RA;
    private static final ArrayList<Module> MODULES_ENABLED = new ArrayList<>();

    public impact() {
        impact.I_RA = new RecipeAdder();
        Texture.Icons.VOID.name();
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
    public void onServerStarting(FMLServerStartingEvent aEvent) {
        aEvent.registerServerCommand(new Command_FixBQ());
    }

    @SuppressWarnings("")
    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent aEvent) {
        proxy.onServerStopping();
        Objects.requireNonNull(SaveManager.get()).onServerStopping();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        MainLoader.Init(event);
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
        CommonProxy.register_event(new EventDropBlock());

        proxy.preInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MainLoader.postInit(event);
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverAboutToStart(final FMLServerAboutToStartEvent event) {
        SaveManager.onServerAboutToStart();
    }

    @Mod.EventHandler
    private void serverStopped(final FMLServerStoppedEvent event) {
        Objects.requireNonNull(SaveManager.get()).onServerStopped();
    }
}