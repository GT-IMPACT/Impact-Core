package com.impact.loader;

import codechicken.nei.api.ItemInfo;
import com.impact.item.*;
import com.impact.mods.BartWorks.BacteriaRegistry;
import com.impact.mods.GTSU.TierHelper;
import com.impact.mods.GTSU.blocks.GTSUBlock;
import com.impact.mods.GTSU.blocks.itemblocks.ItemBlockGTSU;
import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import com.impact.impact;
import com.impact.mods.GalacticGreg.SpaceDimRegisterer;
import com.impact.mods.GregTech.GTregister.GT_Item_Block_And_Fluid;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import com.impact.mods.modChest.BASE.Item_BaseChest;
import com.impact.mods.modChest.BASE.Renderer_BaseChest;
import com.impact.mods.modChest.Steel_Chest.*;
import com.impact.mods.modChest.WroughtIron_Chest.*;;
import com.impact.mods.modChest.chestAL.*;
import com.impact.mods.modChest.chestCr.*;
import com.impact.mods.modChest.chestHSLA.*;
import com.impact.mods.modChest.chestIr.*;
import com.impact.mods.modChest.chestNt.*;
import com.impact.mods.modChest.chestOs.*;
import com.impact.mods.modChest.chestTi.*;
import com.impact.mods.modChest.chestW.*;
import com.impact.mods.modSolar.ASP;
import com.impact.util.OreDictRegister;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.impact.item.Core_List_Items.registerOreDictNames;
import static com.impact.loader.ItemRegistery.decorateBlock;

public class MainLoader {

    private static BacteriaRegistry BacteriaRegistry;
    private static SpaceDimRegisterer SpaceDimReg;

    private MainLoader(){}

    public static void Init() {
        new ItemRegistery();
        ItemRegistery.run();
    }

    public static void preInit(FMLPreInitializationEvent event) {
        //chest mod
        //chestRegister();
        //GT pump
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
            if (SpaceDimReg.Init())  {
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
    }

    public static void postInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, new GUIHandler());
    }

    private static void registerSingleIC2StorageBlocks() {
        GameRegistry.registerTileEntity(TileEntityGTSU.class, "GTSU_TE");
        for (int i = 0; i < TierHelper.V.length; i++)
        {
            GameRegistry.registerBlock(new GTSUBlock(i), ItemBlockGTSU.class, String.format("GTSU_Tier_%d", i));
        }
    }

    private static void chestRegister() {
        GameRegistry.registerBlock(WroughtIronChest.instance, Item_BaseChest.class, "WroughtIronChest");
        GameRegistry.registerTileEntity(TEWroughtIronChest.class, "impact:WroughtIronChest");
        GameRegistry.registerBlock(SteelChest.instance, Item_BaseChest.class, "SteelChest");
        GameRegistry.registerTileEntity(TESteelChest.class, "impact:SteelChest");
        GameRegistry.registerBlock(ChestAl.instance, Item_BaseChest.class, "AluminiumChest");
        GameRegistry.registerTileEntity(TEChestAl.class, "impact:AluminiumChest");
        GameRegistry.registerBlock(ChestHSLA.instance, Item_BaseChest.class, "HSLAChest");
        GameRegistry.registerTileEntity(TEChestHSLA.class, "impact:HSLAChest");
        GameRegistry.registerBlock(ChestTi.instance, Item_BaseChest.class, "TitaniumChest");
        GameRegistry.registerTileEntity(TEChestTi.class, "impact:TitaniumChest");
        GameRegistry.registerBlock(ChestW.instance, Item_BaseChest.class, "TungstenSteelChest");
        GameRegistry.registerTileEntity(TEChestW.class, "impact:TungstenSteelChest");
        GameRegistry.registerBlock(ChestCr.instance, Item_BaseChest.class, "ChromeChest");
        GameRegistry.registerTileEntity(TEChestCr.class, "impact:ChromeChest");
        GameRegistry.registerBlock(ChestIr.instance, Item_BaseChest.class, "IridiumChest");
        GameRegistry.registerTileEntity(TEChestIr.class, "impact:IridiumChest");
        GameRegistry.registerBlock(ChestOs.instance, Item_BaseChest.class, "OsmiumChest");
        GameRegistry.registerTileEntity(TEChestOs.class, "impact:OsmiumChest");
        GameRegistry.registerBlock(ChestNt.instance, Item_BaseChest.class, "NeutroniumChest");
        GameRegistry.registerTileEntity(TEChestNt.class, "impact:NeutroniumChest");

        ClientRegistry.bindTileEntitySpecialRenderer(TEWroughtIronChest.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(WroughtIronChest.instance), new ItemRendererWroughtIronChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TESteelChest.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(SteelChest.instance), new ItemRendererSteelChest());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestAl.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestAl.instance), new ItemRendererChestAl());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestHSLA.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestHSLA.instance), new ItemRendererChestHSLA());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestTi.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestTi.instance), new ItemRendererChestTi());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestW.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestW.instance), new ItemRendererChestW());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestCr.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestCr.instance), new ItemRendererChestCr());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestIr.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestIr.instance), new ItemRendererChestIr());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestOs.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestOs.instance), new ItemRendererChestOs());
        ClientRegistry.bindTileEntitySpecialRenderer(TEChestNt.class, Renderer_BaseChest.instance);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ChestNt.instance), new ItemRendererChestNt());
    }

}
