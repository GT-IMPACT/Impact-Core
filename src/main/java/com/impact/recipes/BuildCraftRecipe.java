package com.impact.recipes;

import com.impact.common.item.Core_Items2;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static com.impact.common.item.Core_List_Items.*;
import static gregtech.api.GregTech_API.getStackofAmountFromOreDict;
import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class BuildCraftRecipe implements Runnable {

    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void delRecipe() {
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "fillerBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "builderBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "architectBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "libraryBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "markerBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "constructionMarkerBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "pathMarkerBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "blueprintItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "templateItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Builders", "machineBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "engineBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "engineBlock", 1L, 1));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "engineBlock", 1L, 2));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "woodenGearItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "stoneGearItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "ironGearItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "goldGearItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Core", "diamondGearItem", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "miningWellBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "autoWorkbenchBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "tankBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "pumpBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "floodGateBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "refineryBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "blockHopper", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "zonePlan", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Factory", "requester", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 1));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 2));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 3));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 4));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "laserTableBlock", 1L, 5));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Silicon", "packagerBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "filteredBufferBlock", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Robotics", "zonePlan", 1L, 0));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Robotics", "requester", 1L, 0));

        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemerald", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsiron", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsgold", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsobsidian", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemslapis", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdaizuli", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsvoid", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemzuli", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstripes", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsclay", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidswood", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidscobblestone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsquartz", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsiron", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsgold", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsemerald", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsdiamond", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidssandstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsvoid", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsclay", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerwood", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowercobblestone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerquartz", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepoweriron", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowergold", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowerdiamond", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepoweremerald", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipepowersandstone", 1L, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipestructurecobblestone", 1L, GT_Values.W));
    }

    public void handRecipe() {
        // --- Filler
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "fillerBlock", 1L, 0), tBitMask, new Object[]{"PMP", "CHC", "GIG", 'P', ItemList.Electric_Piston_MV, 'M', GT_ModHandler.getModItem("BuildCraft|Core", "markerBlock", 1L, 0), 'C', ItemList.Conveyor_Module_MV, 'H', ItemList.MACHINE_HULLS[2], 'G', GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Gold, 1), 'I', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1)});
        // --- Builder
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "builderBlock", 1L, 0), tBitMask, new Object[]{"PMP", "CHC", "GIG", 'P', ItemList.Robot_Arm_MV, 'M', GT_ModHandler.getModItem("BuildCraft|Core", "markerBlock", 1L, 0), 'C', ItemList.Conveyor_Module_MV, 'H', ItemList.MACHINE_HULLS[2], 'G', GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Birmabright, 1), 'I', GT_ModHandler.getModItem("chestup", "Blockchestup", 1L, 1)});
        // --- Architect
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "architectBlock", 1L, 0), tBitMask, new Object[]{"PMP", "CHC", "GIG", 'P', ItemList.Robot_Arm_MV, 'M', GT_ModHandler.getModItem("BuildCraft|Core", "markerBlock", 1L, 0), 'C', ItemList.Conveyor_Module_MV, 'H', ItemList.MACHINE_HULLS[2], 'G', GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Birmabright, 1), 'I', ItemList.Cover_Screen});
        // --- Library
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "libraryBlock", 1L, 0), tBitMask, new Object[]{"PGP", "CHC", "PIP", 'P', OrePrefixes.plate.get(Materials.Iron), 'C', OrePrefixes.circuit.get(Materials.Basic), 'H', ItemList.MACHINE_HULLS[2], 'G', GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Birmabright, 1), 'I', ItemList.Cover_Screen});
        // --- Marker Blue
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Core", "markerBlock", 1L, 0), tBitMask, new Object[]{" S ", " L ", 'L', OrePrefixes.stick.get(Materials.Wood), 'S', GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Lapis, 1)});
        // --- Marker Yellow
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Core", "constructionMarkerBlock", 1L, 0), tBitMask, new Object[]{" S ", " L ", 'L', OrePrefixes.stick.get(Materials.Wood), 'S', getStackofAmountFromOreDict("dyeYellow", 1)});
        // --- Marker Green
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Core", "pathMarkerBlock", 1L, 0), tBitMask, new Object[]{" S ", " L ", 'L', OrePrefixes.stick.get(Materials.Wood), 'S', getStackofAmountFromOreDict("dyeGreen", 1)});
        // --- BluePrint
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "blueprintItem", 1L, 0), tBitMask, new Object[]{"LS", 'L', OrePrefixes.plate.get(Materials.Paper), 'S', GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Lapis, 1)});
        // --- BlackPrint
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "templateItem", 1L, 0), tBitMask, new Object[]{"LS", 'L', OrePrefixes.plate.get(Materials.Paper), 'S', getStackofAmountFromOreDict("dyeBlack", 1)});
        // --- Redstone Engine
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Core", "engineBlock", 1L, 0), tBitMask, new Object[]{"PPP", "SIS", "GHG", 'P', OrePrefixes.plank.get(Materials.Wood), 'S', OrePrefixes.spring.get(Materials.Iron), 'G', OrePrefixes.gear.get(Materials.Iron), 'I', new ItemStack(Blocks.piston), 'H', ItemList.Hull_Bronze});
        // --- Auto Workbench
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Factory", "autoWorkbenchBlock", 1L, 0), tBitMask, new Object[]{"PGP", "GCG", "PMP", 'P', OrePrefixes.itemCasing.get(Materials.Iron), 'G', OrePrefixes.gearGtSmall.get(Materials.Iron), 'M', GT_ItemList.ULVMotor, 'C', GT_ModHandler.getModItem("Forestry", "factory2", 1L, 2)});
        // --- Flood Gate
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Factory", "floodGateBlock", 1L, 0), tBitMask, new Object[]{"PUP", "GCG", "PGP", 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'G', CoreItems2.getRecipe(SteelBars.getMetaID(), 1), 'U', ItemList.Electric_Pump_LV, 'C', GT_ModHandler.getModItem("extracells", "certustank", 1L, 0)});
        // --- Quarry
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Builders", "machineBlock", 1L, 0), tBitMask, new Object[]{"PHP", "COC", "PMP", 'P', OrePrefixes.itemCasing.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_MV, 'O', ItemList.Machine_MV_Miner, 'C', OrePrefixes.circuit.get(Materials.Good), 'H', OrePrefixes.toolHeadDrill.get(Materials.StainlessSteel)});

        // --- Wooden Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood", 1L, 0), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', OrePrefixes.stick.get(Materials.Wood), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
        // --- Cobblestone Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone", 1L, 0), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', CoreItems2.getRecipe(CobblestoneRod.getMetaID(), 1), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
        // --- Stone Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone", 1L, 0), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 1), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
        // --- Quartz Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz", 1L, 0), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', OrePrefixes.stick.get(Materials.NetherQuartz), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
        // --- Sandstone Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone", 1L, 0), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', CoreItems2.getRecipe(SandstoneRod.getMetaID(), 1), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L)});
        // --- Void Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsvoid", 1L, 0), tBitMask, new Object[]{"SGS", "GOG", "SGS", 'S', OrePrefixes.stick.get(Materials.Obsidian), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), 'O', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L)});
        // --- Void Fluid Pipe
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsvoid", 1L, 0), tBitMask, new Object[]{"SGS", "GOG", "SGS", 'S', OrePrefixes.stick.get(Materials.Rubber), 'G', GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), 'O', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L)});

        for (int i = 0; i < 16; i++) {
            // --- Wooden Pipe (Colorfull)
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood", 1L, i + 1), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', OrePrefixes.stick.get(Materials.Wood), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i)});
            // --- Cobblestone Pipe (Colorfull)
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone", 1L, i + 1), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', CoreItems2.getRecipe(CobblestoneRod.getMetaID(), 1), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i)});
            // --- Stone Pipe (Colorfull)
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone", 1L, i + 1), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', OrePrefixes.stick.get(Materials.Stone), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i)});
            // --- Quartz Pipe (Colorfull)
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz", 1L, i + 1), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', OrePrefixes.stick.get(Materials.NetherQuartz), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i)});
            // --- Sandstone Pipe (Colorfull)
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone", 1L, i + 1), tBitMask, new Object[]{" f ", "SGS", " s ", 'S', CoreItems2.getRecipe(SandstoneRod.getMetaID(), 1), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i)});
            // --- Void Pipe
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsvoid", 1L, i + 1), tBitMask, new Object[]{"SGS", "GOG", "SGS", 'S', OrePrefixes.stick.get(Materials.Obsidian), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i), 'O', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L)});
            // --- Void Fluid Pipe
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipefluidsvoid", 1L, i + 1), tBitMask, new Object[]{"SGS", "GOG", "SGS", 'S', OrePrefixes.stick.get(Materials.Rubber), 'G', new ItemStack(Blocks.stained_glass_pane, 1, i), 'O', GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1L)});

        }

    }

    public void assemblerRecipe() {
        // --- Wooden Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 2L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood", 4L), 100, 16);
        // --- Cobblestone Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(CobblestoneRod.getMetaID(), 4), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone", 4L), 100, 16);
        // --- Stone Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "toolRod", 4L, 1), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone", 4L), 100, 16);
        // --- Quartz Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NetherQuartz, 2L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz", 4L), 100, 16);
        // --- Sandstone Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(SandstoneRod.getMetaID(), 4), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone", 4L), 100, 16);
        // --- Iron Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 2L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsiron", 4L), 100, 24);
        // --- Gold Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Gold, 2L), GT_ModHandler.getModItem("minecraft", "glass_pane", 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsgold", 4L), 100, 48);
        // --- Diamond Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Diamond, 4L), GT_ModHandler.getModItem("minecraft", "glass_pane", 2L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond", 8L), 200, 64);
        // --- Emerald Pipe
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Emerald, 4L), GT_ModHandler.getModItem("minecraft", "glass_pane", 2L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemerald", 8L), 200, 64);

        for (int i = 0; i < 16; i++) {
            // --- Wooden Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Wood, 2L), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemswood", 4L, i + 1), 100, 16);
            // --- Cobblestone Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(CobblestoneRod.getMetaID(), 4), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemscobblestone", 4L, i + 1), 100, 16);
            // --- Stone Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "toolRod", 4L, 1), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsstone", 4L, i + 1), 100, 16);
            // --- Quartz Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NetherQuartz, 2L), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsquartz", 4L, i + 1), 100, 16);
            // --- Sandstone Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(SandstoneRod.getMetaID(), 4), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemssandstone", 4L, i + 1), 100, 16);
            // --- Iron Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Iron, 2L), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsiron", 4L, i + 1), 100, 24);
            // --- Gold Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Gold, 2L), new ItemStack(Blocks.stained_glass_pane, 1, i), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsgold", 4L, i + 1), 100, 48);
            // --- Diamond Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Diamond, 4L), new ItemStack(Blocks.stained_glass_pane, 2, i), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsdiamond", 8L, i + 1), 200, 64);
            // --- Emerald Pipe
            GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Emerald, 4L), new ItemStack(Blocks.stained_glass_pane, 2, i), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L), GT_Utility.getIntegratedCircuit(3)}, null, GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipeitemsemerald", 8L, i + 1), 200, 64);

        }

    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        assemblerRecipe();
    }
}
