package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

public class FormingPressRecipe implements Runnable {
    @Override
    public void run() {

/** ================================= start CORE MOD =================================*/
        //Spatial Chip
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstonePulsatingChipset.get(1L), GT_ItemList.PulsatingSpatialCoreChip.get(1L), 300, 7680);
        //Fluid Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneGoldChipset.get(1L), GT_ItemList.GoldFluidCoreChip.get(1L), 300, 1920);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.DiamondFluidCoreChip.get(1L), 300, 4096);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), 300, 7680);
        //Item Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedGoldChip.get(1L)), GT_ItemList.RedstoneGoldChipset.get(1L), GT_ItemList.GoldCoreChip.get(1L), 300, 480);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedDiamondCrystalChip.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.DiamondCoreChip.get(1L), 300, 1920);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedEnergyChip.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), 300, 4096);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedQuantumChip.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), 300, 7680);
        //Gendustry Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L)), GT_ItemList.RedstonePulsatingChipset.get(1L), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), 300, 7680);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.EnvironmentalCircuit.get(1L), 300, 7680);
        //Redstone Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstonePulsatingChipset.get(2L), 200, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneQuartzChipset.get(1L), 300, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneIronChipset.get(1L), 100, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneGoldChipset.get(1L), 200, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneDiamondChipset.get(1L), 100, 480);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneEmeraldChipset.get(1L), 150, 480);
        /* ================================= end CORE MOD =================================*/

    }
}
