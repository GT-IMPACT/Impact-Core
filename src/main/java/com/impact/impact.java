package com.impact;

import com.impact.System.*;
import com.impact.api.enums.Texture;
import com.impact.block.blocks.Block_IGlass;
import com.impact.loader.MainLoader;
import com.impact.loader.ModLoader;
import com.impact.mods.GregTech.GTregister.GT_ItemRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_BasicRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_MultiRegister;
import com.impact.mods.GregTech.GTregister.GT_WorldGenRegister;
import com.impact.mods.GregTech.casings.GT_Loader_Casings;
import com.impact.recipes.HandRecipe;
import com.impact.recipes.OpenComputersRecipe;
import com.impact.recipes.machines.*;
import com.impact.util.SendUtils;
import com.impact.util.oreplugin.CSVMaker;
import com.impact.util.oreplugin.GT5OreLayerHelper;
import com.impact.util.oreplugin.GT5OreSmallHelper;
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
import galaxyspace.core.register.GSItems;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.SpaceStationType;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.util.HashMap;

import static com.impact.System.Config.csv;
import static com.impact.System.Refstrings.MODID;
import static com.impact.System.impactLog.INFO;
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
    public static String ModPackVersion = "1.0 Release [DEV]";
    public static Config mConfig;
    public static FMLEventChannel channel;


    public impact() {
        Texture.Icons.VOID.name();
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

        MinecraftForge.EVENT_BUS.register(new impactEvents());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("Impact");
        channel.register(new PacketHandler());
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
    public void PostLoad(FMLPostInitializationEvent PostEvent) {
        new GT_ItemRegister().run();
        new GT_Loader_Casings().run();
        new GT_Machines_MultiRegister().run();
        new GT_Machines_BasicRegister().run();
        new GT_WorldGenRegister().run();
        new ModLoader().run();
        new HandRecipe().run();
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
        new UnboxingRecipe().run();
        final HashMap<Object, Integer> inputMap = new HashMap<Object, Integer>();
        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel,40), 40);
        inputMap.put(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel,20), 20);
        inputMap.put(new ItemStack(IGlassBlock, 20), 20);
        inputMap.put(new ItemStack(GCItems.rocketEngine, 4), 4);
        GalacticraftRegistry.registerSpaceStation(new SpaceStationType(ConfigManagerCore.idDimensionOverworldOrbit, 0, new SpaceStationRecipe(inputMap)));
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
