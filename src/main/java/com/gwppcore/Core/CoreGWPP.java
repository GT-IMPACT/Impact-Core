package com.gwppcore.Core;


import com.gwppcore.Libs.Refstrings;
import com.gwppcore.GTHandler.GT_CustomLoader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

import eu.usrv.yamcore.YAMCore;
import eu.usrv.yamcore.auxiliary.LogHelper;
import eu.usrv.yamcore.auxiliary.IngameErrorLog;
import gregtech.api.GregTech_API;
import gregtech.GT_Mod;


    @Mod(modid = Refstrings.MODID, version = Refstrings.VERSION)

    public class CoreGWPP {


        @Mod.Instance(Refstrings.MODID)
        public static CoreGWPP instance;


        public static GT_CustomLoader GTCustomLoader;
        public static IngameErrorLog Module_AdminErrorLogs;
        public static LogHelper Logger = new LogHelper(Refstrings.MODID);
        public static void AddLoginError(String pMessage)
        {
            if (Module_AdminErrorLogs != null) {
                Module_AdminErrorLogs.AddErrorLogOnAdminJoin(pMessage);
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

        }
    }