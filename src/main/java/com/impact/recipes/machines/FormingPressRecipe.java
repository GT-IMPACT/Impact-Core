package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FormingPressRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {

/** ================================= start CORE MOD =================================*/
        //Spatial Chip
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstonePulsatingChipset.get(1L), GT_ItemList.PulsatingSpatialCoreChip.get(1L), 300, 7680);
        //Fluid Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneGoldChipset.get(1L), GT_ItemList.GoldFluidCoreChip.get(1L), 300, 1024);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.DiamondFluidCoreChip.get(1L), 300, 1920);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Master.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldAdvancedFluidCoreChip.get(1L), 300, 4096);
        //Item Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedGoldChip.get(1L)), GT_ItemList.RedstoneGoldChipset.get(1L), GT_ItemList.GoldCoreChip.get(1L), 300, 480);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedDiamondCrystalChip.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.DiamondCoreChip.get(1L), 300, 1920);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedEnergyChip.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldAdvancedCoreChip.get(1L), 300, 4096);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(GT_ItemList.EngravedQuantumChip.get(1L)), GT_ItemList.RedstoneEmeraldChipset.get(1L), GT_ItemList.EmeraldHighAdvancedCoreChip.get(1L), 300, 7680);
        //Gendustry Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L)), GT_ItemList.RedstonePulsatingChipset.get(1L), GT_ItemList.GeneticCircuit.get(1L), 300, 7680);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L)), GT_ItemList.RedstoneDiamondChipset.get(1L), GT_ItemList.EnvironmentalCircuit.get(1L), 300, 7680);
        //Redstone Chips
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstonePulsatingChipset.get(2L), 200, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneQuartzChipset.get(1L), 300, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneIronChipset.get(1L), 100, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneGoldChipset.get(1L), 200, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneDiamondChipset.get(1L), 100, 480);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_ItemList.RedstoneRedChipset.get(1L), GT_ItemList.RedstoneEmeraldChipset.get(1L), 150, 480);

        GT_Values.RA.addFormingPressRecipe(CoreItems.getRecipe(2, 4), ItemList.Shape_Extruder_Ingot.get(0L), CoreItems2.getRecipe(70, 1), 200, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MysteriousCrystal, 4L), ItemList.Shape_Extruder_Ingot.get(0L), CoreItems2.getRecipe(73, 1), 300, 480);

        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(101, 1), 600, 120);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(102, 1), 600, 480);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tungsten, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(103, 1), 600, 1920);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(104, 1), 600, 7680);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Chrome, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(105, 1), 600, 30720);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(106, 1), 600, 122880);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Naquadria, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(107, 1), 600, 500000);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 4L), CoreItems2.getRecipe(108, 1), 600, 2000000);

        // --- Leather
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Rubber, 4L), ItemList.Shape_Mold_Plate.get(0), new ItemStack(Items.leather, 1), 800, 16);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.StyreneButadieneRubber, 4L), ItemList.Shape_Mold_Plate.get(0), new ItemStack(Items.leather, 1), 800, 16);
        GT_Values.RA.addFormingPressRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 4L), ItemList.Shape_Mold_Plate.get(0), new ItemStack(Items.leather, 1), 800, 16);

        /* ================================= end CORE MOD =================================*/

    }
}
