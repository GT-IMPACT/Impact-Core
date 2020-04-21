package com.impact;

import com.impact.System.Refstrings;
import com.impact.loader.MainLoader;
import com.impact.System.CommonProxy;
import com.impact.util.SendUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import com.impact.System.LoginHandler;

@Mod (
		modid = Refstrings.MODID,
        name = Refstrings.NAME,
        version = Refstrings.VERSION,
        dependencies = 
        	"required-after:Forge@[10.13.2.1291,);"
        +	"required-after:YAMCore@[0.5.77,);")

public class impact {
	
	@SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
    public static CommonProxy proxy;
	
	@Mod.Instance(Refstrings.MODID)
    public static impact instance;
    public static SendUtils SendUtils_instance = new SendUtils();
    public static String ModPackVersion = "1.0 RELEASE";

	@Mod.EventHandler
    public void PreLoad(FMLPreInitializationEvent PreEvent) {
        FMLCommonHandler.instance().bus().register(new LoginHandler());
    }
	
	@Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MainLoader.load();
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MainLoader.preInit(event);
        //MainLoader.preInitClient();
    }

	@Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent PostEvent) {
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
