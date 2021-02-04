package com.impact.recipes;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;
import static stevesaddons.registry.BlockRegistry.cableRFNode;
import static stevesaddons.registry.ItemRegistry.duplicator;
import static stevesaddons.registry.ItemRegistry.labeler;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import vswe.stevesfactory.blocks.ModBlocks;

public class SFMRecipe implements Runnable {

  private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED
      | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

  public void delRecipe() {
    removeRecipeByOutput(new ItemStack(ModBlocks.blockManager, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCable, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableBreaker, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableSign, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableCluster, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableCluster, 1, 8), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableOutput, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableInput, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableRelay, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableRelay, 1, 8), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableBUD, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableCamouflage, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableCamouflage, 1, 1), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableCamouflage, 1, 2), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableIntake, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(ModBlocks.blockCableIntake, 1, 8), true, false, false);
    removeRecipeByOutput(new ItemStack(cableRFNode, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(duplicator, 1, 0), true, false, false);
    removeRecipeByOutput(new ItemStack(labeler, 1, 0), true, false, false);
  }

  public void handRecipe() {
    // --- Manager
    GT_ModHandler.addCraftingRecipe(new ItemStack(ModBlocks.blockManager, 1, 0), tBitMask,
        new Object[]{"PTP", "RDR", "PCP", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'R',
            OrePrefixes.plate.get(Materials.RedAlloy), 'T',
            GT_ModHandler.getModItem("PracticalLogistics", "LargeDisplayScreen", 1L, 0), 'D',
            ItemList.Casing_Processor, 'C', new ItemStack(ModBlocks.blockCable, 1, 0)});
  }

  public void assemblerRecipe() {
    // --- Inventory Cable
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1),
            GT_ModHandler.getModItem("EnderIO", "itemItemConduit", 2L, 0),
            GT_OreDictUnificator.get(OrePrefixes.foil, Materials.BlueSteel, 4)}, null,
        new ItemStack(ModBlocks.blockCable, 1, 0), 200, 480);
    // --- Block Gate
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0), ItemList.Robot_Arm_HV.get(1),
            GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(288),
        new ItemStack(ModBlocks.blockCableBreaker, 1, 0), 200, 480);
    // --- Sign Updater
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCableBreaker, 1, 0),
            GT_ModHandler.getModItem("IC2", "itemToolPainter", 1L, 0),
            GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(576),
        new ItemStack(ModBlocks.blockCableSign, 1, 0), 300, 480);
    // --- Cable Cluster
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2),
            GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(288),
        new ItemStack(ModBlocks.blockCableCluster, 1, 0), 200, 480);
    // --- Advanced Cable Cluster
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 8, 0),
            GT_Utility.getIntegratedCircuit(1)}, null, new ItemStack(ModBlocks.blockCableCluster, 1, 8),
        200, 480);
    // --- Redstone Emitter
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0), ItemList.Emitter_HV.get(1),
            GT_Utility.getIntegratedCircuit(2)}, Materials.Electrum.getMolten(288),
        new ItemStack(ModBlocks.blockCableOutput, 1, 0), 200, 480);
    // --- Redstone Reciever
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0), ItemList.Sensor_HV.get(1),
            GT_Utility.getIntegratedCircuit(2)}, Materials.Electrum.getMolten(288),
        new ItemStack(ModBlocks.blockCableInput, 1, 0), 200, 480);
    // --- Inventory Relay
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            ItemList.Cover_ActivityDetector.get(1), GT_Utility.getIntegratedCircuit(2)},
        Materials.Redstone.getMolten(288), new ItemStack(ModBlocks.blockCableRelay, 1, 0), 200,
        480);
    // --- Advanced Inventory Relay
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            ItemList.Cover_ItemDetector.get(1), GT_Utility.getIntegratedCircuit(2)},
        Materials.Redstone.getMolten(576), new ItemStack(ModBlocks.blockCableRelay, 1, 8), 300,
        480);
    // --- Block Detector
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            GT_ModHandler.getModItem("ExtraUtilities", "budoff", 1L, 3),
            GT_Utility.getIntegratedCircuit(2)}, Materials.RedAlloy.getMolten(288),
        new ItemStack(ModBlocks.blockCableBUD, 1, 0), 200, 480);
    // --- Cable Camouflage
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0), ItemList.Cover_Screen.get(2),
            GT_Utility.getIntegratedCircuit(2)}, Materials.RedAlloy.getMolten(288),
        new ItemStack(ModBlocks.blockCableCamouflage, 1, 0), 200, 480);
    // --- Double Sided Cable Camouflage
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCableCamouflage, 1, 0),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
            GT_Utility.getIntegratedCircuit(1)}, Materials.RedAlloy.getMolten(576),
        new ItemStack(ModBlocks.blockCableCamouflage, 1, 1), 300, 480);
    // --- Transforming Cable Camouflage
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{new ItemStack(ModBlocks.blockCableCamouflage, 1, 1),
            ItemList.Field_Generator_LV.get(1), GT_Utility.getIntegratedCircuit(1)},
        Materials.RedAlloy.getMolten(864), new ItemStack(ModBlocks.blockCableCamouflage, 1, 2), 400,
        480);
    // --- Item Valve
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            GT_ModHandler.getModItem("minecraft", "hopper", 2L, 0), GT_Utility.getIntegratedCircuit(2)},
        Materials.RedAlloy.getMolten(288), new ItemStack(ModBlocks.blockCableIntake, 1, 0), 200,
        480);
    // --- Rapid Item Valve
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCableIntake, 1, 0),
            ItemList.Conveyor_Module_HV.get(1), GT_Utility.getIntegratedCircuit(1)},
        Materials.RedAlloy.getMolten(576), new ItemStack(ModBlocks.blockCableIntake, 1, 8), 300,
        480);
    // --- RF Node
    GT_Values.RA.addAssemblerRecipe(new ItemStack[]{new ItemStack(ModBlocks.blockCable, 1, 0),
            GT_ModHandler.getModItem("EnderIO", "itemPowerConduit", 1L, 0),
            GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(288),
        new ItemStack(cableRFNode, 1, 0), 200, 480);
    // --- Manager Dublicator
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{ItemList.Tool_Scanner.get(1), ItemList.Tool_DataStick.get(1),
            GT_Utility.getIntegratedCircuit(1)}, Materials.Redstone.getMolten(144),
        new ItemStack(duplicator, 1, 0), 150, 480);
    // --- Factory Labeler
    GT_Values.RA.addAssemblerRecipe(
        new ItemStack[]{GT_ModHandler.getModItem("PracticalLogistics", "DisplayScreenItem", 1L, 0),
            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2),
            GT_Utility.getIntegratedCircuit(2)}, Materials.Redstone.getMolten(144),
        new ItemStack(labeler, 1, 0), 150, 480);
  }

  @Override
  public void run() {
    delRecipe();
    handRecipe();
    assemblerRecipe();
  }
}
