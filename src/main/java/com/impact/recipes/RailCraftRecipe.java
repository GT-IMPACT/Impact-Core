package com.impact.recipes;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.machine.alpha.EnumMachineAlpha;
import mods.railcraft.common.blocks.tracks.EnumTrack;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.items.*;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipe;
import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;
import static mods.railcraft.common.plugins.forge.CraftingPlugin.addShapedRecipe;

public class RailCraftRecipe implements Runnable {

    private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED;

    public void delRecipe() {
        removeRecipeByOutput(EnumTrack.CONTROL.getItem());
    }

    public void handRecipe() {
        Object[] recipe;
        // --- Control Track
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.CONTROL.getItem(8), recipe);
        // --- Locking Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', Blocks.stone_pressure_plate,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.LOCKING.getItem(8), recipe);
        // --- Disembarkin Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', EnumDetector.ADVANCED,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.DISEMBARK.getItem(8), recipe);
        // --- Embarking Track
        recipe = new Object[] {
                "SRS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', EnumDetector.ADVANCED,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.EMBARKING.getItem(8), recipe);
        // --- Coupler Track
        recipe = new Object[] {
                "SCS", "ITI", "dCh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'C', new ItemStack(new ItemCrowbarReinforced()),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.COUPLER.getItem(8), recipe);
        // --- Whistle Track
        recipe = new Object[] {
                "SWS", "ITI", "dOh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'W', new ItemStack(new ItemWhistleTuner()),
                'O', ItemList.Casing_Stripes_A,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.WHISTLE.getItem(8), recipe);
        // --- Locomotive Track
        recipe = new Object[] {
                "SOS", "ITI", "dOh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'O', RailcraftItem.signalLamp.getRecipeObject(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.LOCOMOTIVE.getItem(8), recipe);
        // --- Limiter Track
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', EnumDetector.ANY,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.LIMITER.getItem(8), recipe);
        // --- Routing Track
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', new ItemStack(new ItemTicket()),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.ROUTING.getItem(8), recipe);
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', new ItemStack(new ItemTicketGold()),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.ROUTING.getItem(8), recipe);
        // --- Buffer Stop
        recipe = new Object[] {
                "SPS", "ITI", "dBh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'B', OrePrefixes.block.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.BUFFER_STOP.getItem(8), recipe);
        // --- One Way Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', Blocks.stone_pressure_plate,
                'R', Blocks.piston,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.ONEWAY.getItem(8), recipe);
        // --- Directional Detector Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'R', EnumDetector.ADVANCED,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.DETECTOR_DIRECTION.getItem(8), recipe);
        // --- Gated One Way Track
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', GT_ModHandler.getModItem("minecraft", "fence", 1L),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.GATED_ONEWAY.getItem(8), recipe);
        // --- Gated Track
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'R', GT_ModHandler.getModItem("minecraft", "fence", 1L),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.GATED.getItem(8), recipe);
        // --- Suspended Rail
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'R', GT_ModHandler.getModItem("minecraft", "fence", 1L),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        addShapedRecipe(EnumTrack.SUSPENDED.getItem(8), recipe);
    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
    }
}
