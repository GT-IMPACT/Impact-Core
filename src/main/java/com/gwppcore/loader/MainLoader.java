package com.gwppcore.loader;

import com.gwppcore.GTSU.TierHelper;
import com.gwppcore.GTSU.blocks.GTSUBlock;
import com.gwppcore.GTSU.blocks.itemblocks.ItemBlockGTSU;
import com.gwppcore.GTSU.tileentity.TileEntityGTSU;
import com.gwppcore.guihandler.GUIHandler;
import com.gwppcore.gwppcore;
import com.gwppcore.item.ItemRegistery;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.modChest.BASE.Item_BaseChest;
import com.gwppcore.modChest.BASE.Renderer_BaseChest;
import com.gwppcore.modChest.Steel_Chest.ItemRendererSteelChest;
import com.gwppcore.modChest.Steel_Chest.SteelChest;
import com.gwppcore.modChest.Steel_Chest.TESteelChest;
import com.gwppcore.modChest.WroughtIron_Chest.WroughtIronChest;
import com.gwppcore.modChest.WroughtIron_Chest.ItemRendererWroughtIronChest;
import com.gwppcore.modChest.WroughtIron_Chest.TEWroughtIronChest;
import com.gwppcore.modChest.chestAL.ChestAl;
import com.gwppcore.modChest.chestAL.ItemRendererChestAl;
import com.gwppcore.modChest.chestAL.TEChestAl;
import com.gwppcore.modChest.chestHSLA.ChestHSLA;
import com.gwppcore.modChest.chestHSLA.ItemRendererChestHSLA;
import com.gwppcore.modChest.chestHSLA.TEChestHSLA;
import com.gwppcore.modChest.chestTi.ChestTi;
import com.gwppcore.modChest.chestTi.ItemRendererChestTi;
import com.gwppcore.modChest.chestTi.TEChestTi;
import com.gwppcore.modChest.chestW.ChestW;
import com.gwppcore.modChest.chestW.ItemRendererChestW;
import com.gwppcore.modChest.chestW.TEChestW;
import com.gwppcore.oredict.OreDictHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.gwppcore.gwppcore.CoreConfig;

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
        GameRegistry.registerBlock(WroughtIronChest.instance, Item_BaseChest.class, "Wrought Iron Chest");
            GameRegistry.registerTileEntity(TEWroughtIronChest.class, "impact:WroughtIronChest");
        GameRegistry.registerBlock(SteelChest.instance, Item_BaseChest.class, "Steel Chest");
            GameRegistry.registerTileEntity(TESteelChest.class, "impact:SteelChest");
        GameRegistry.registerBlock(ChestAl.instance, Item_BaseChest.class, "Aluminium Chest");
            GameRegistry.registerTileEntity(TEChestAl.class, "impact:AluminiumChest");
        GameRegistry.registerBlock(ChestHSLA.instance, Item_BaseChest.class, "HSLA Chest");
            GameRegistry.registerTileEntity(TEChestHSLA.class, "impact:HSLAChest");
        GameRegistry.registerBlock(ChestTi.instance, Item_BaseChest.class, "Titanium Chest");
            GameRegistry.registerTileEntity(TEChestTi.class, "impact:TitaniumChest");
        GameRegistry.registerBlock(ChestW.instance, Item_BaseChest.class, "Tungsten Steel Chest");
            GameRegistry.registerTileEntity(TEChestW.class, "impact:TungstenSteelChest");


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
        NetworkRegistry.INSTANCE.registerGuiHandler(gwppcore.instance, new GUIHandler());


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
