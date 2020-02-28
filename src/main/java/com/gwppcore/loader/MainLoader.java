package com.gwppcore.loader;

import com.gwppcore.gthandler.GT_CustomLoader;
import com.gwppcore.gtsu.TierHelper;
import com.gwppcore.gtsu.blocks.GTSUBlock;
import com.gwppcore.gtsu.blocks.itemblocks.ItemBlockGTSU;
import com.gwppcore.gtsu.tileentity.TileEntityGTSU;
import com.gwppcore.guihandler.GUIHandler;
import com.gwppcore.gwppcore;
import com.gwppcore.item.ItemRegistery;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.oredict.OreDictHandler;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
        ProgressManager.ProgressBar progressBarLoad = ProgressManager.push(Refstrings.MODID +" Pre Init", 2);

        progressBarLoad.step("GT Pump");
        ItemRegistery.GregtechPump();

        progressBarLoad.step("GT Circuit Programmer");
        ItemRegistery.CircuitProgrammer();

        ProgressManager.pop(progressBarLoad);
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
        new GT_CustomLoader().run();

        ProgressManager.pop(progressBarPostLoad);
    }
    public static void postInit() {
        ProgressManager.ProgressBar progressBarPostLoad = ProgressManager.push(Refstrings.MODID +" Post Init", 1);

        progressBarPostLoad.step("GUI Circuit Programmer");
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
