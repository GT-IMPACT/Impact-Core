package com.gwppcore;

import com.gwppcore.block.BlockList;
import com.gwppcore.command.*;
import com.gwppcore.config.CoreModConfig;
import com.gwppcore.creativetab.ModTabList;
import com.gwppcore.fluids.FluidList;
import com.gwppcore.gthandler.GT_CoreModSupport;
import com.gwppcore.gthandler.GT_CustomLoader;
import com.gwppcore.gtsu.TierHelper;
import com.gwppcore.gtsu.blocks.GTSUBlock;
import com.gwppcore.gtsu.blocks.itemblocks.ItemBlockGTSU;
import com.gwppcore.gtsu.gui.GuiHandler;
import com.gwppcore.gtsu.tileentity.TileEntityGTSU;
import com.gwppcore.guihandler.GUIHandler;
import com.gwppcore.item.ItemList;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.main.CommonProxy;
import com.gwppcore.modctt.CustomToolTipsHandler;
import com.gwppcore.network.CoreModDispatcher;
import com.gwppcore.oredict.OreDictHandler;
import com.gwppcore.util.SendUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import eu.usrv.yamcore.auxiliary.IngameErrorLog;
import eu.usrv.yamcore.auxiliary.LogHelper;
import eu.usrv.yamcore.blocks.ModBlockManager;
import eu.usrv.yamcore.creativetabs.CreativeTabsManager;
import eu.usrv.yamcore.fluids.ModFluidManager;
import eu.usrv.yamcore.items.ModItemManager;
import gregtech.GT_Mod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import com.gwppcore.loginhandler.LoginHandler;

import java.io.*;
import java.util.Random;

@Mod (
		modid = Refstrings.MODID,
        name = Refstrings.NAME,
        version = Refstrings.VERSION,
        dependencies = 
        	"required-after:Forge@[10.13.2.1291,);"
        +	"required-after:YAMCore@[0.5.77,);")

public class gwppcore {
	
	@SidedProxy(clientSide = Refstrings.CLIENTSIDE, serverSide = Refstrings.SERVERSIDE)
    public static CommonProxy proxy;
	
	@Mod.Instance(Refstrings.MODID)
    public static gwppcore instance;
	
	public static ModItemManager ItemManager;
	public static CreativeTabsManager TabManager;
	public static ModFluidManager FluidManager;
	public static ModBlockManager BlockManager;
	public static CustomToolTipsHandler Module_CustomToolTips;
	public static IngameErrorLog Module_AdminErrorLogs;
	public static GT_CustomLoader GTCustomLoader;
	public static CoreModConfig CoreConfig;
	public static CoreModDispatcher NW;
	public static Random Rnd;
	public static LogHelper Logger = new LogHelper(Refstrings.MODID);
    public static SendUtils SendUtils_instance = new SendUtils();
    public static final IGuiHandler GH = new GUIHandler();


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
        
     // ------------------------------------------------------------
        Logger.debug("PRELOAD Init TexturePage");
        File tFile = new File(new File(PreEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg");
        Configuration tMainConfig = new Configuration(tFile);
        tMainConfig.load();
        
        proxy.addTexturePage();
        // ------------------------------------------------------------
        
        // ------------------------------------------------------------
        Logger.debug("PRELOAD Init NetworkChannel");
        NW = new CoreModDispatcher();
        NW.registerPackets();
        // ------------------------------------------------------------
        
        // ------------------------------------------------------------
        Logger.debug("PRELOAD Init itemmanager");
        ItemManager = new ModItemManager(Refstrings.MODID);
        BlockManager = new ModBlockManager(Refstrings.MODID);
        
     // ------------------------------------------------------------
        Logger.debug("PRELOAD Init Tabmanager");
        TabManager = new CreativeTabsManager();
        ModTabList.InitModTabs(TabManager, ItemManager);
        
      //Materials init
        if (!GT_Mod.gregtechproxy.mEnableAllMaterials) {
            new GT_CoreModSupport();
        }
        
     // ------------------------------------------------------------
        Logger.debug("PRELOAD Create Items");
        if (!ItemList.AddToItemManager(ItemManager))
        {
            Logger.warn("Some items failed to register. Check the logfile for details");
            AddLoginError("[CoreMod-Items] Some items failed to register. Check the logfile for details");
        }
     // ------------------------------------------------------------

     // ------------------------------------------------------------
        Logger.info("PRELOAD Create Blocks");
        if (!BlockList.AddToItemManager(BlockManager))
        {
            Logger.warn("Some blocks failed to register. Check the logfile for details");
            AddLoginError("[CoreMod-Blocks] Some blocks failed to register. Check the logfile for details");
        }
     // ------------------------------------------------------------

        
     // ------------------------------------------------------------
        // Init Modules
        Logger.debug("PRELOAD Init Modules");

        if (CoreConfig.ModCustomToolTips_Enabled)
        {
            //Logger.debug("Module_HazardousItems is enabled");
            Module_CustomToolTips = new CustomToolTipsHandler();
            // Module_CustomToolTips.LoadConfig();
        }
        
     // ------------------------------------------------------------
        Logger.debug("PRELOAD Create Fluids");
        FluidManager = new ModFluidManager(Refstrings.MODID);
        if (!FluidList.AddToItemManager(FluidManager))
        {
            Logger.warn("Some fluids failed to register. Check the logfile for details");
            AddLoginError("[CoreMod-Fluids] Some fluids failed to register. Check the logfile for details");
        }
        
     // register final list with valid items to forge
        Logger.debug("LOAD Register Items");
        ItemManager.RegisterItems(TabManager);
        
        Logger.debug("LOAD Register Blocks");
        BlockManager.RegisterItems(TabManager);
        
        Logger.debug("LOAD Register Fluids");
        FluidManager.RegisterItems(TabManager);

        if (CoreConfig.ModLoginMessage_Enabled)
        {
            FMLCommonHandler.instance().bus().register(new LoginHandler());
        }
        Logger.warn( "==================================================" );
        Logger.warn( "Welcome to GregWorld:PlusPlus " + CoreModConfig.ModPackVersion );
        Logger.warn( "Please bring comments to " + "https://discord.gg/bMf2qvd" );
        Logger.warn( "==================================================" );


    }
	
	@Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
		// register events in modules
        RegisterModuleEvents();
        
		// Register additional OreDictionary Names
        if(CoreConfig.OreDictItems_Enabled)
        OreDictHandler.register_all();
    }

	private void RegisterModuleEvents()
    {
		if (CoreConfig.ModAdminErrorLogs_Enabled) {
            FMLCommonHandler.instance().bus().register(Module_AdminErrorLogs);
        }
		
		if (CoreConfig.ModCustomToolTips_Enabled)
        {
            MinecraftForge.EVENT_BUS.register(Module_CustomToolTips);
            FMLCommonHandler.instance().bus().register(Module_CustomToolTips);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        registerSingleIC2StorageBlocks();

    }

    private void registerSingleIC2StorageBlocks()
    {
        GameRegistry.registerTileEntity(TileEntityGTSU.class, "GTSU_TE");
        for (int i = 0; i < TierHelper.V.length; i++)
        {
            GameRegistry.registerBlock(new GTSUBlock(i), ItemBlockGTSU.class, String.format("GTSU_Tier_%d", i));
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(gwppcore.instance, new GuiHandler());

    }





	@Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent PostEvent)
    {
		
		if (CoreConfig.ModCustomToolTips_Enabled) {
            Module_CustomToolTips.LoadConfig();
        }
		
		GTCustomLoader = new GT_CustomLoader();
        GTCustomLoader.run();



    }

	@Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent pEvent)
    {
		if (CoreConfig.ModCustomToolTips_Enabled) {
            pEvent.registerServerCommand(new CustomToolTipsCommand());
        }
		
		if (CoreConfig.ModItemInHandInfo_Enabled) {
            pEvent.registerServerCommand(new ItemInHandInfoCommand());
        }
		
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
        NetworkRegistry.INSTANCE.registerGuiHandler(gwppcore.instance, gwppcore.GH);
    }
}
