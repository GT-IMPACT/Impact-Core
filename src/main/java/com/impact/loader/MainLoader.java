package com.impact.loader;

import codechicken.nei.api.ItemInfo;
import com.impact.impact;
import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.item.WoodBrickFormTool;
import com.impact.mods.BartWorks.BacteriaRegistry;
import com.impact.mods.GalacticGreg.SpaceDimRegisterer;
import com.impact.mods.GregTech.GTregister.GT_ItemRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_BasicRegister;
import com.impact.mods.GregTech.GTregister.GT_Machines_MultiRegister;
import com.impact.mods.GregTech.GTregister.GT_WorldGenRegister;
import com.impact.mods.GregTech.casings.GT_Loader_Casings;
import com.impact.mods.modSolar.ASP;
import com.impact.recipes.AfterGregTechPostLoadRecipes;
import com.impact.recipes.HandRecipe;
import com.impact.recipes.OpenComputersRecipe;
import com.impact.recipes.debug.DEBUG_Recipe;
import com.impact.recipes.machines.*;
import com.impact.util.OreDictRegister;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import static com.impact.System.impactLog.INFO;
import static com.impact.System.impactLog.WARNING;
import static com.impact.item.Core_List_Items.registerOreDictNames;
import static com.impact.loader.ItemRegistery.decorateBlock;

public class MainLoader {

    private MainLoader() {
    }

    public static void Init() {
        new ItemRegistery();
        ItemRegistery.run();
        INFO("[Init] Item Registery - Loaded");
    }

    public static void preInit() {

        ItemRegistery.GregtechPump();
        INFO("[preInit] Gregtech Pump - Loaded");

        ASP.preInit();
        INFO("[preInit] Solar Panel - Loaded");

        Core_Items.getInstance().registerItem();
        INFO("[preInit] Meta Items 1 - Loaded");

        Core_Items2.getInstance().registerItem();
        INFO("[preInit] Meta Items 2 - Loaded");

        WoodBrickFormTool.getInstance().registerItem();
        INFO("[preInit] Wood Brick Form Tool - Loaded");

        registerOreDictNames();
        INFO("[preInit] Meta Items OreDict List - Loaded");

        ItemInfo.hiddenItems.add(new ItemStack(decorateBlock[2], 1, 0));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 1));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 2));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 3));
        INFO("[preInit] Hide NEI Items - Loaded");
    }

    public static void load() {

        // Register Dimensions in GalacticGregGT5
        if (Loader.isModLoaded("galacticgreg")) {
            SpaceDimRegisterer spaceDimReg = new SpaceDimRegisterer();
            if (spaceDimReg.Init()) {
                spaceDimReg.Register();
                INFO("[load] Space Dimension Register - Loaded");
            }
            WARNING("[load] Space Dimension Register - Not Loaded");
        }

        OreDictRegister.register_all();
        INFO("[load] OreDict Register List - Loaded");

        ASP.load();
        INFO("[load] Solar Panel 2 - Loaded");
    }

    public static void onPreLoad() {
        if (Loader.isModLoaded("bartworks")) {
            new BacteriaRegistry();
            INFO("[onPreLoad] Bacteria Register - Loaded");
        }
    }

    public static void postLoad() {
        GregTechRecipesPostLoad();
        INFO("[postLoad] Before GregTech PostLoad Runner - Loaded");

        addAfterGregTechPostLoadRunner();
        INFO("[postLoad] After GregTech PostLoad Runner - Loaded");
    }

    public static void postInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, new GUIHandler());
        INFO("[postInit] GUI Handler - Loaded");
    }

//    private static void registerSingleIC2StorageBlocks() {
//        GameRegistry.registerTileEntity(TileEntityGTSU.class, "GTSU_TE");
//        for (int i = 0; i < TierHelper.V.length; i++) {
//            GameRegistry.registerBlock(new GTSUBlock(i), ItemBlockGTSU.class, String.format("GTSU_Tier_%d", i));
//        }
//    }

    public static void addAfterGregTechPostLoadRunner() {
        GregTech_API.sAfterGTPostload.add(() -> new AfterGregTechPostLoadRecipes().run());
    }

    public static void GregTechRecipesPostLoad() {
        GregTech_API.sBeforeGTPostload.add(() -> {
            new GT_ItemRegister().run();
            new GT_Loader_Casings().run();
            new GT_Machines_MultiRegister().run();
            new GT_Machines_BasicRegister().run();
            new HandRecipe().run();
            new GT_WorldGenRegister().run();
            new ModLoader().run();
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
        });
    }
}
