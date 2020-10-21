package com.impact.loader;

import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.common.block.netherportal.BlockHandler;
import com.impact.common.block.netherportal.BlockNullPortal;
import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.common.item.FakeCircuits;
import com.impact.common.item.WoodBrickFormTool;
import com.impact.impact;
import com.impact.mods.ASP.ASP;
import com.impact.mods.GalactiCraft.GG.SpaceDimRegisterer;
import com.impact.mods.GregTech.Basic_Register;
import com.impact.mods.GregTech.GT_ItemRegister;
import com.impact.mods.GregTech.GT_WorldGenRegister;
import com.impact.mods.GregTech.Multi_Register;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.RailCraft.carts.item.events.Module;
import com.impact.recipes.AfterGregTechPostLoadRecipes;
import com.impact.recipes.HandRecipe;
import com.impact.recipes.machines.*;
import com.impact.util.OreDictRegister;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregtech.api.GregTech_API;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import static codechicken.nei.api.API.hideItem;
import static com.impact.common.item.Core_List_Items.registerOreDictNames;
import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;
import static com.impact.impact.getModules;

public class MainLoader {

    private MainLoader() {
    }

    public static void Init() {
        new ItemRegistery();
        ItemRegistery.run();
        INFO("[Init] Item Registery - Loaded");
    }

    public static void preInit(FMLPreInitializationEvent event) {

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

        FakeCircuits.getInstance().registerItem();
        INFO("[preInit] Fake Circuits - Loaded");

        registerOreDictNames();
        INFO("[preInit] Meta Items OreDict List - Loaded");

        ItemRegistery.registerBlocks();
        INFO("[preInit] Meta Blocks - Loaded");

        for (byte i = 0; i <= 7; i++)
            hideItem(new ItemStack(FakeCircuits.getInstance(), 1, i));
        INFO("[preInit] Hide NEI Items - Loaded");

        BlockHandler.replaceBlock(Blocks.portal, BlockNullPortal.class, ItemBlock.class);
        INFO("[preInit] Disabled Nether Portal - Loaded");

        for(Module module : getModules()) {
            if(!module.areRequirementsMet() && module.getIsActive()) {
                module.setIsActive(false);
            }
        }

        for(Module module : getModules()) {
            if(module.getIsActive())
                module.init(event);
        }
    }

    public static void load(FMLInitializationEvent event) {

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

        Block_QuantumStuff.run();
        INFO("[load] Quantum Stuff registered");

        for (Module module : getModules()) {
            if (module.getIsActive()) module.load(event);
        }

        impact.proxy.registerRenderInfo();
    }

    public static void onPreLoad() {

    }

    public static void postLoad(FMLPostInitializationEvent event) {
        new GT_ItemRegister().run();
        new Casing_Helper().run();
        new Multi_Register().run();
        new Basic_Register().run();
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
        new CannerRecipe().run();

        addAfterGregTechPostLoadRunner();
        INFO("[postLoad] After GregTech PostLoad Runner - Loaded");

        for(Module module : getModules()) {
            if(module.getIsActive()) module.postInit(event);
        }
    }

    public static void postInit() {
    }

    public static void addAfterGregTechPostLoadRunner() {
        GregTech_API.sAfterGTPostload.add(() -> new AfterGregTechPostLoadRecipes().run());
    }
}
