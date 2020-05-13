package com.impact.loader;

import codechicken.nei.api.ItemInfo;
import com.impact.impact;
import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.item.WoodBrickFormTool;
import com.impact.mods.BartWorks.BacteriaRegistry;
import com.impact.mods.GTSU.TierHelper;
import com.impact.mods.GTSU.blocks.GTSUBlock;
import com.impact.mods.GTSU.blocks.itemblocks.ItemBlockGTSU;
import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import com.impact.mods.GalacticGreg.SpaceDimRegisterer;
import com.impact.mods.GregTech.GTregister.GT_Item_Block_And_Fluid;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import com.impact.mods.modChest.BASE.*;
import com.impact.mods.modSolar.ASP;
import com.impact.recipes.AfterGregTechPostLoadRecipes;
import com.impact.util.OreDictRegister;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.impact.item.Core_List_Items.registerOreDictNames;
import static com.impact.loader.GUIHandler.GUI_ID_Chest;
import static com.impact.loader.ItemRegistery.decorateBlock;

;

public class MainLoader {

    public static BaseChest Chest;
    private static BacteriaRegistry BacteriaRegistry;
    private static SpaceDimRegisterer SpaceDimReg;

    private MainLoader() {
    }

    public static void Init() {
        new ItemRegistery();
        ItemRegistery.run();
    }

    public static void preInit() {

        //Chest mod
        //chestRegister();
        //GTpump Register
        ItemRegistery.GregtechPump();
        //GT circuit_programmer
        //ItemRegistery.CircuitProgrammer();
        //Materials initial
        new GT_Materials();
        //solar
        ASP.preInit();

        //MetaItems
        Core_Items.getInstance().registerItem();
        Core_Items2.getInstance().registerItem();
        WoodBrickFormTool.getInstance().registerItem();
        registerOreDictNames();

        ItemInfo.hiddenItems.add(new ItemStack(decorateBlock[2], 1, 0));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 1));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 2));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 3));
    }

    public static void load() {

        // Register Dimensions in GalacticGregGT5
        if (Loader.isModLoaded("galacticgreg")) {
            SpaceDimReg = new SpaceDimRegisterer();
            if (SpaceDimReg.Init()) {
                SpaceDimReg.Register();
            }
        }

        OreDictRegister.register_all();
        //solar
        ASP.load();
    }

    public static void onPreLoad() {
        if (Loader.isModLoaded("bartworks")) {
            BacteriaRegistry = new BacteriaRegistry();
        }

        new GT_Item_Block_And_Fluid().run();
    }

    public static void postLoad() {

        //GTSU
        //registerSingleIC2StorageBlocks();

        //GT runnable
        new GT_ModLoader();
        GT_ModLoader.run();
        addAfterGregTechPostLoadRunner();
    }

    public static void postInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, new GUIHandler());
    }

    private static void registerSingleIC2StorageBlocks() {
        GameRegistry.registerTileEntity(TileEntityGTSU.class, "GTSU_TE");
        for (int i = 0; i < TierHelper.V.length; i++) {
            GameRegistry.registerBlock(new GTSUBlock(i), ItemBlockGTSU.class, String.format("GTSU_Tier_%d", i));
        }
    }

    private static void chestRegister() {
        Chest = new BaseChest();
        Chest.RegisterChestType(
                new TE_BaseChest(),0, GUI_ID_Chest, "Wrought Iron Chest", 64, 45, //TE
                175, 203, 500, 8, //GUI
                9, 5, 8, 122); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),1, GUI_ID_Chest, "Steel Chest", 64, 63, //TE
                175, 239, 500, 8, //GUI
                9, 7, 8, 158); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),2, GUI_ID_Chest, "Aluminium Chest", 64, 81, //TE
                175, 275, 500, 8, //GUI
                9, 9, 8, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),3, GUI_ID_Chest, "HSLA Chest", 64, 99, //TE
                211, 275, 500, 8, //GUI
                11, 9, 26, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),4, GUI_ID_Chest, "Titanium Chest", 64, 117, //TE
                247, 275, 500, 8, //GUI
                13, 9, 44, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),5, GUI_ID_Chest, "Tungsten Steel Chest", 64, 135, //TE
                283, 275, 500, 8, //GUI
                15, 9, 62, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),6, GUI_ID_Chest, "Chrome Chest", 64, 153, //TE
                320, 275, 500, 8, //GUI
                17, 9, 80, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),7, GUI_ID_Chest, "Iridium Chest", 64, 171, //TE
                356, 275, 500, 8, //GUI
                19, 9, 98, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),8, GUI_ID_Chest, "Osmium Chest", 64, 189, //TE
                392, 275, 500, 8, //GUI
                21, 9, 116, 194); //Container
        Chest.RegisterChestType(
                new TE_BaseChest(),9, GUI_ID_Chest, "Neutronium Chest", 64, 207, //TE
                428, 275, 500, 8, //GUI
                23, 9, 134, 194); //Container


        GameRegistry.registerBlock(new BaseChest(), Item_BaseChest.class, "Chests");
        GameRegistry.registerTileEntity(TE_BaseChest.class, "impact:Chests");

        ClientRegistry.bindTileEntitySpecialRenderer(TE_BaseChest.class, new Renderer_BaseChest());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(new BaseChest()), new ItemRenderBase());
    }

    public static void addAfterGregTechPostLoadRunner() {
        GregTech_API.sAfterGTPostload.add(() -> {
            new AfterGregTechPostLoadRecipes().run();
        });
    }

}
