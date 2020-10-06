package com.impact.loader;

import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.client.gui.GUIHandler;
import com.impact.impact;
import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.common.item.FakeCircuits;
import com.impact.common.item.WoodBrickFormTool;
import com.impact.mods.GalactiCraft.GG.SpaceDimRegisterer;
import com.impact.mods.ASP.ASP;
import com.impact.recipes.AfterGregTechPostLoadRecipes;
import com.impact.util.OreDictRegister;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;

import static codechicken.nei.api.API.hideItem;
import static com.impact.core.impactLog.INFO;
import static com.impact.core.impactLog.WARNING;
import static com.impact.common.item.Core_List_Items.registerOreDictNames;

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

        FakeCircuits.getInstance().registerItem();
        INFO("[preInit] Fake Circuits - Loaded");

        registerOreDictNames();
        INFO("[preInit] Meta Items OreDict List - Loaded");

        ItemRegistery.registerBlocks();
        INFO("[preInit] Meta Blocks - Loaded");

        for (byte i = 0; i <= 7; i++)
            hideItem(new ItemStack(FakeCircuits.getInstance(), 1, i));
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

        Block_QuantumStuff.run();
        INFO("[load] Quantum Stuff registered");

        impact.proxy.registerRenderInfo();
    }

    public static void onPreLoad() {

    }

    public static void postLoad() {
        addAfterGregTechPostLoadRunner();
        INFO("[postLoad] After GregTech PostLoad Runner - Loaded");
    }

    public static void postInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, new GUIHandler());
        INFO("[postInit] GUI Handler - Loaded");
    }

    public static void addAfterGregTechPostLoadRunner() {
        GregTech_API.sAfterGTPostload.add(() -> new AfterGregTechPostLoadRecipes().run());
    }
}
