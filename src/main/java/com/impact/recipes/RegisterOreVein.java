package com.impact.recipes;

import com.impact.core.Impact_API;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public class RegisterOreVein implements Runnable{

    public void registerOres() {
        Impact_API.sBiomeOres.put(BiomeGenBase.ocean, new ItemStack[]{typeOre(1), typeOre(2)});
    }

    public static ItemStack typeOre(int aMeta){
        return GT_ModHandler.getModItem("gregtech", "gt.blockores", 1, aMeta);
    }

    /** Overworld Biomes 36
        BiomeGenBase.ocean
        BiomeGenBase.plains
        BiomeGenBase.desert
        BiomeGenBase.extremeHills
        BiomeGenBase.forest
        BiomeGenBase.taiga
        BiomeGenBase.swampland
        BiomeGenBase.river
        BiomeGenBase.frozenOcean
        BiomeGenBase.frozenRiver
        BiomeGenBase.icePlains
        BiomeGenBase.iceMountains
        BiomeGenBase.beach
        BiomeGenBase.desertHills
        BiomeGenBase.forestHills
        BiomeGenBase.taigaHills
        BiomeGenBase.extremeHillsEdge
        BiomeGenBase.jungle
        BiomeGenBase.jungleHills
        BiomeGenBase.jungleEdge
        BiomeGenBase.deepOcean
        BiomeGenBase.stoneBeach
        BiomeGenBase.coldBeach
        BiomeGenBase.birchForest
        BiomeGenBase.birchForestHills
        BiomeGenBase.roofedForest
        BiomeGenBase.coldTaiga
        BiomeGenBase.coldTaigaHills
        BiomeGenBase.megaTaiga
        BiomeGenBase.megaTaigaHills
        BiomeGenBase.extremeHillsPlus
        BiomeGenBase.savanna
        BiomeGenBase.savannaPlateau
        BiomeGenBase.mesa
        BiomeGenBase.mesaPlateau_F
        BiomeGenBase.mesaPlateau
    */

    @Override
    public void run() {
        registerOres();
    }
}
