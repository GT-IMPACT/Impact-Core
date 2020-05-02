package com.impact.loader;

import codechicken.nei.api.ItemInfo;
import com.impact.block.Core_GlassBlocks;
import com.impact.mods.GTSU.TierHelper;
import com.impact.mods.GTSU.blocks.GTSUBlock;
import com.impact.mods.GTSU.blocks.itemblocks.ItemBlockGTSU;
import com.impact.mods.GTSU.tileentity.TileEntityGTSU;
import com.impact.impact;
import com.impact.mods.GregTech.GTregister.GT_Item_Block_And_Fluid;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import com.impact.mods.modChest.BASE.Item_BaseChest;
import com.impact.mods.modChest.BASE.Renderer_BaseChest;
import com.impact.mods.modChest.Steel_Chest.ItemRendererSteelChest;
import com.impact.mods.modChest.Steel_Chest.SteelChest;
import com.impact.mods.modChest.Steel_Chest.TESteelChest;
import com.impact.mods.modChest.WroughtIron_Chest.WroughtIronChest;
import com.impact.mods.modChest.WroughtIron_Chest.ItemRendererWroughtIronChest;
import com.impact.mods.modChest.WroughtIron_Chest.TEWroughtIronChest;
import com.impact.mods.modChest.chestAL.ChestAl;
import com.impact.mods.modChest.chestAL.ItemRendererChestAl;
import com.impact.mods.modChest.chestAL.TEChestAl;
import com.impact.mods.modChest.chestCr.ChestCr;
import com.impact.mods.modChest.chestCr.ItemRendererChestCr;
import com.impact.mods.modChest.chestCr.TEChestCr;
import com.impact.mods.modChest.chestHSLA.ChestHSLA;
import com.impact.mods.modChest.chestHSLA.ItemRendererChestHSLA;
import com.impact.mods.modChest.chestHSLA.TEChestHSLA;
import com.impact.mods.modChest.chestIr.ChestIr;
import com.impact.mods.modChest.chestIr.ItemRendererChestIr;
import com.impact.mods.modChest.chestIr.TEChestIr;
import com.impact.mods.modChest.chestNt.ChestNt;
import com.impact.mods.modChest.chestNt.ItemRendererChestNt;
import com.impact.mods.modChest.chestNt.TEChestNt;
import com.impact.mods.modChest.chestOs.ChestOs;
import com.impact.mods.modChest.chestOs.ItemRendererChestOs;
import com.impact.mods.modChest.chestOs.TEChestOs;
import com.impact.mods.modChest.chestTi.ChestTi;
import com.impact.mods.modChest.chestTi.ItemRendererChestTi;
import com.impact.mods.modChest.chestTi.TEChestTi;
import com.impact.mods.modChest.chestW.ChestW;
import com.impact.mods.modChest.chestW.ItemRendererChestW;
import com.impact.mods.modChest.chestW.TEChestW;
import com.impact.mods.modSolar.ASP;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

import static com.impact.loader.ItemRegistery.decorateBlock;

public class MainLoader {

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

        ItemInfo.hiddenItems.add(new ItemStack(decorateBlock[2], 1, 0));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 1));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 2));
        ItemInfo.hiddenItems.remove(new ItemStack(decorateBlock[2], 1, 3));
    }

    public static void load() {
        //solar
        ASP.load();
    }

    public static void onPreLoad() {
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
