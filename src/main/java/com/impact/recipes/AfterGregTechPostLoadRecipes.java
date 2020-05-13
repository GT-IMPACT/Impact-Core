package com.impact.recipes;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.*;

public class AfterGregTechPostLoadRecipes implements Runnable {
    @Override
    public void run() {

        //removeRecipe(ItemStack aInput);

        //removeFurnaceSmelting(ItemStack aInput)
        removeFurnaceSmelting(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1));
        removeFurnaceSmelting(new ItemStack(Items.clay_ball, 1, 0));

        //removeRecipeByOutput();
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoHelmet", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoLeggings", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBoots", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumHelmet", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBodyarmor", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumLeggings", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBoots", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "blockTelePad", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "blockTransceiver", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockController", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 260), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 240), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 220), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 460), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarry", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "endMarker", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 9), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 3), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 6), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 7), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 11), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1L, GT_Values.W), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1L, GT_Values.W), true, false, false);

        removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CokeCoal, 1), true, false, false);
    }
}
