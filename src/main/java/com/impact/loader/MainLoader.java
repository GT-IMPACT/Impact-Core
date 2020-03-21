package com.impact.loader;

import com.impact.GTSU.TierHelper;
import com.impact.GTSU.blocks.GTSUBlock;
import com.impact.GTSU.blocks.itemblocks.ItemBlockGTSU;
import com.impact.GTSU.tileentity.TileEntityGTSU;
import com.impact.guihandler.GUIHandler;
import com.impact.impact;
import com.impact.item.ItemRegistery;
import com.impact.lib.Refstrings;
import com.impact.modChest.BASE.Item_BaseChest;
import com.impact.modChest.BASE.Renderer_BaseChest;
import com.impact.modChest.Steel_Chest.ItemRendererSteelChest;
import com.impact.modChest.Steel_Chest.SteelChest;
import com.impact.modChest.Steel_Chest.TESteelChest;
import com.impact.modChest.WroughtIron_Chest.WroughtIronChest;
import com.impact.modChest.WroughtIron_Chest.ItemRendererWroughtIronChest;
import com.impact.modChest.WroughtIron_Chest.TEWroughtIronChest;
import com.impact.modChest.chestAL.ChestAl;
import com.impact.modChest.chestAL.ItemRendererChestAl;
import com.impact.modChest.chestAL.TEChestAl;
import com.impact.modChest.chestCr.ChestCr;
import com.impact.modChest.chestCr.ItemRendererChestCr;
import com.impact.modChest.chestCr.TEChestCr;
import com.impact.modChest.chestHSLA.ChestHSLA;
import com.impact.modChest.chestHSLA.ItemRendererChestHSLA;
import com.impact.modChest.chestHSLA.TEChestHSLA;
import com.impact.modChest.chestIr.ChestIr;
import com.impact.modChest.chestIr.ItemRendererChestIr;
import com.impact.modChest.chestIr.TEChestIr;
import com.impact.modChest.chestNt.ChestNt;
import com.impact.modChest.chestNt.ItemRendererChestNt;
import com.impact.modChest.chestNt.TEChestNt;
import com.impact.modChest.chestOs.ChestOs;
import com.impact.modChest.chestOs.ItemRendererChestOs;
import com.impact.modChest.chestOs.TEChestOs;
import com.impact.modChest.chestTi.ChestTi;
import com.impact.modChest.chestTi.ItemRendererChestTi;
import com.impact.modChest.chestTi.TEChestTi;
import com.impact.modChest.chestW.ChestW;
import com.impact.modChest.chestW.ItemRendererChestW;
import com.impact.modChest.chestW.TEChestW;
import com.impact.oredict.OreDictHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.impact.impact.CoreConfig;

public class MainLoader {

    private MainLoader(){}

    public static void Init() {
        ProgressManager.ProgressBar progressBarLoad = ProgressManager.push(Refstrings.MODID +" Init", 1);

        progressBarLoad.step("Item Registery");
        new ItemRegistery().run();

        ProgressManager.pop(progressBarLoad);
    }

    public static void preInit() {
        ProgressManager.ProgressBar progressBarLoad = ProgressManager.push(Refstrings.MODID +" Pre Init", 3);

        progressBarLoad.step("Chests");
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

        progressBarLoad.step("GT Pump");
        ItemRegistery.GregtechPump();

        progressBarLoad.step("GT Circuit Programmer");
        ItemRegistery.CircuitProgrammer();

        ProgressManager.pop(progressBarLoad);
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient()
    {
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

    public static void load() {
        ProgressManager.ProgressBar progressBarLoad = ProgressManager.push(Refstrings.MODID +" Load", 2);

        progressBarLoad.step("Items Loading");
        new itemLoader().run();

        progressBarLoad.step("Oredict Loading");
        if(CoreConfig.OreDictItems_Enabled) OreDictHandler.run();

        ProgressManager.pop(progressBarLoad);
    }

    public static void postLoad() {
        ProgressManager.ProgressBar progressBarPostLoad = ProgressManager.push(Refstrings.MODID +" Post Load", 2);

        progressBarPostLoad.step("Register IC2 Storage Blocks");
        registerSingleIC2StorageBlocks();

        progressBarPostLoad.step("GT Loading");
        new GT_ModLoader().run();

        ProgressManager.pop(progressBarPostLoad);
    }
    public static void postInit() {
        ProgressManager.ProgressBar progressBarPostLoad = ProgressManager.push(Refstrings.MODID +" Post Init", 1);

        progressBarPostLoad.step("GUI");
        NetworkRegistry.INSTANCE.registerGuiHandler(impact.instance, new GUIHandler());


        ProgressManager.pop(progressBarPostLoad);
    }


    private static void registerSingleIC2StorageBlocks() {
        GameRegistry.registerTileEntity(TileEntityGTSU.class, "GTSU_TE");
        for (int i = 0; i < TierHelper.V.length; i++)
        {
            GameRegistry.registerBlock(new GTSUBlock(i), ItemBlockGTSU.class, String.format("GTSU_Tier_%d", i));
        }
    }

}
