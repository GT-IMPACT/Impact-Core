package com.gwppcore;

import com.gwppcore.command.*;
import com.gwppcore.config.CoreModConfig;
import com.gwppcore.creativetab.ModTabList;
import com.gwppcore.libs.Refstrings;
import com.gwppcore.gthandler.GT_CustomLoader;
import com.gwppcore.item.ItemList;
import com.gwppcore.main.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;

import eu.usrv.yamcore.auxiliary.LogHelper;
import eu.usrv.yamcore.creativetabs.CreativeTabsManager;
import eu.usrv.yamcore.items.ModItemManager;
import eu.usrv.yamcore.auxiliary.IngameErrorLog;

import net.minecraftforge.common.config.Configuration;

import java.io.*;
import java.util.Random;

    @Mod(modid = Refstrings.MODID, version = Refstrings.VERSION)

    public class gwppcore {

        @SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
        public static CommonProxy proxy;

        @Mod.Instance(Refstrings.MODID)
        public static gwppcore instance;

        public static ModItemManager ItemManager;
        public static CreativeTabsManager TabManager;
        public static CoreModConfig CoreConfig;

        public static GT_CustomLoader GTCustomLoader;
        public static IngameErrorLog Module_AdminErrorLogs;
        public static Random Rnd;
        public static LogHelper Logger = new LogHelper(Refstrings.MODID);
        public static void AddLoginError(String pMessage)
        {
            if (Module_AdminErrorLogs != null) {
                Module_AdminErrorLogs.AddErrorLogOnAdminJoin(pMessage);
            }
        }

        @Mod.EventHandler
        public void PreLoad(FMLPreInitializationEvent PreEvent)
        {
            Logger.setDebugOutput(true);

            Rnd = new Random(System.currentTimeMillis());

            // ------------------------------------------------------------
            // Init coremod config file. Create it if it's not there
            CoreConfig = new CoreModConfig(PreEvent.getModConfigurationDirectory(), Refstrings.COLLECTIONID, Refstrings.MODID);
            if (!CoreConfig.LoadConfig()) {
                Logger.error(String.format("%s could not load its config file. Things are going to be weird!", Refstrings.MODID));
            }
            // ------------------------------------------------------------

            if (CoreConfig.ModAdminErrorLogs_Enabled)
            {
                Logger.debug("Module_AdminErrorLogs is enabled");
                Module_AdminErrorLogs = new IngameErrorLog();
            }

            Logger.debug("PRELOAD Init TexturePage");
            File tFile = new File(new File(PreEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg");
            Configuration tMainConfig = new Configuration(tFile);
            tMainConfig.load();


        /*GregTech_API.sUseMachineMetal = tMainConfig.get("machines", "use_machine_metal_tint", true).getBoolean(true);
        if (GregTech_API.sUseMachineMetal)
            {
                // use default in GregTech Dyes enum.
            }
        else
            {
                // Override MACHINE_METAL dye color with white
                MACHINE_METAL.mRGBa[0]= 255;
                MACHINE_METAL.mRGBa[1]= 255;
                MACHINE_METAL.mRGBa[2]= 255;
            }*/

            proxy.addTexturePage();

            // ------------------------------------------------------------
            Logger.debug("PRELOAD Init itemmanager");
            ItemManager = new ModItemManager(Refstrings.MODID);

            // ------------------------------------------------------------
            Logger.debug("PRELOAD Init Tabmanager");
            TabManager = new CreativeTabsManager();
            ModTabList.InitModTabs(TabManager, ItemManager);

            // ------------------------------------------------------------
            Logger.debug("PRELOAD Create Items");
            if (!ItemList.AddToItemManager(ItemManager))
            {
                Logger.warn("Some items failed to register. Check the logfile for details");
                AddLoginError("[GWPPCoreMod-Items] Some items failed to register. Check the logfile for details");
            }

            // register final list with valid items to forge
            Logger.debug("LOAD Register Items");
            ItemManager.RegisterItems(TabManager);
        }

        private void RegisterModuleEvents()
        {
            if (CoreConfig.ModAdminErrorLogs_Enabled) {
                FMLCommonHandler.instance().bus().register(Module_AdminErrorLogs);
            }
        }

        @Mod.EventHandler
        public void PostLoad(FMLPostInitializationEvent PostEvent)
        {
            GTCustomLoader = new GT_CustomLoader();
            GTCustomLoader.run();
        }

        @Mod.EventHandler
        public void serverLoad(FMLServerStartingEvent pEvent)
        {
            if (CoreConfig.ModItemInHandInfo_Enabled) {
                pEvent.registerServerCommand(new ItemInHandInfoCommand());
            }
        }
    }