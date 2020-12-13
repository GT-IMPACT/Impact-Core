package com.impact;

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
import com.impact.util.SendUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.impact.core.Config.csv;
import static com.impact.core.Refstrings.MODID;
import static com.impact.core.impactLog.INFO;
import static com.impact.loader.ItemRegistery.IGlassBlock;

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
    public static SendUtils SendUtils_instance = new SendUtils();
    public static String ModPackVersion = "1.0.0.0";
    public static Config mConfig;
    public static FMLEventChannel channel;
    private static ArrayList<Module> MODULES_ENABLED = new ArrayList<Module>();
    public static IRecipeAdder I_RA;

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

        MinecraftForge.EVENT_BUS.register(new impactEvents());
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("Impact");
        channel.register(new PacketHandler());

        TickHandler tickHandler = new TickHandler();
        FMLCommonHandler.instance().bus().register(tickHandler);
        MinecraftForge.EVENT_BUS.register(tickHandler);

        proxy.preload();
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        if (event.getSide() == Side.CLIENT) {
            new GT5OreLayerHelper();
            new GT5OreSmallHelper();
            if (csv) new CSVMaker().run();
        }
    }

    @Mod.EventHandler
    public void PostLoad(FMLPostInitializationEvent event) {
        MainLoader.postLoad(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
        MainLoader.postInit();
//        final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
//        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel,40), 40);
//        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel,20), 20);
//        inputMap.put(new ItemStack(IGlassBlock, 20), 20);
//        inputMap.put(new ItemStack(GCItems.rocketEngine, 4), 4);
//        GalacticraftRegistry.registerSpaceStation(new SpaceStationType(ConfigManagerCore.idDimensionOverworldOrbit, 0, new SpaceStationRecipe(inputMap)));
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
