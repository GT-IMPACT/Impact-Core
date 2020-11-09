package com.impact.recipes;

import com.impact.impact;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.signals.EnumSignal;
import mods.railcraft.common.blocks.tracks.EnumTrack;
import mods.railcraft.common.items.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class RailCraftRecipe implements Runnable {


    private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED;

    public void delRecipe() {
        removeRecipeByOutput(EnumTrack.CONTROL.getItem());
        removeRecipeByOutput(EnumTrack.LOCKING.getItem());
        removeRecipeByOutput(EnumTrack.DISEMBARK.getItem());
        removeRecipeByOutput(EnumTrack.LOCOMOTIVE.getItem());
        removeRecipeByOutput(EnumTrack.LIMITER.getItem());
        removeRecipeByOutput(EnumTrack.BUFFER_STOP.getItem());
        removeRecipeByOutput(EnumTrack.ELECTRIC.getItem());
        removeRecipeByOutput(EnumTrack.SWITCH.getItem());
        removeRecipeByOutput(EnumTrack.WYE.getItem());
        removeRecipeByOutput(EnumTrack.ONEWAY.getItem());
        removeRecipeByOutput(EnumTrack.GATED.getItem());
        removeRecipeByOutput(EnumTrack.DISPOSAL.getItem());
        removeRecipeByOutput(EnumTrack.SLOW.getItem());
        removeRecipeByOutput(EnumTrack.SLOW_SWITCH.getItem());
        removeRecipeByOutput(EnumTrack.REINFORCED_JUNCTION.getItem());
        removeRecipeByOutput(EnumTrack.ELECTRIC_SWITCH.getItem());
        removeRecipeByOutput(EnumTrack.SPEED.getItem());
        removeRecipeByOutput(EnumTrack.SPEED_TRANSITION.getItem());
        removeRecipeByOutput(EnumTrack.SPEED_SWITCH.getItem());
        removeRecipeByOutput(EnumTrack.PRIMING.getItem());
        removeRecipeByOutput(new ItemStack(Blocks.rail));
        removeRecipeByOutput(new ItemStack(Blocks.golden_rail));
        removeRecipeByOutput(new ItemStack(Blocks.detector_rail));
        removeRecipeByOutput(new ItemStack(Blocks.activator_rail));

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
        GT_ModHandler.addCraftingRecipe(EnumTrack.CONTROL.getItem(4), recipe);
        // --- Locking Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', new ItemStack(Blocks.stone_pressure_plate),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.LOCKING.getItem(4), recipe);
        // --- Disembarkin Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', EnumDetector.ADVANCED.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.DISEMBARK.getItem(4), recipe);
        // --- Embarking Track
        recipe = new Object[] {
                "SRS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'P', EnumDetector.ADVANCED.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.EMBARKING.getItem(4), recipe);
        // --- Coupler Track
        recipe = new Object[] {
                "SCS", "ITI", "dCh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'C', ItemCrowbarReinforced.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.COUPLER.getItem(4), recipe);
        // --- Whistle Track
        recipe = new Object[] {
                "SWS", "ITI", "dOh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'W', ItemWhistleTuner.getItem(),
                'O', ItemList.Casing_Stripes_A,
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.WHISTLE.getItem(4), recipe);
        // --- Locomotive Track
        recipe = new Object[] {
                "SOS", "ITI", "dOh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'O', EnumSignal.BLOCK_SIGNAL.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.LOCOMOTIVE.getItem(4), recipe);
        // --- Limiter Track
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', EnumDetector.ANY.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.LIMITER.getItem(4), recipe);
        // --- Routing Track
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', ItemTicket.getTicket(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ROUTING.getItem(4), recipe);
        recipe = new Object[] {
                "SPS", "ITI", "dDh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', ItemTicketGold.getTicket(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ROUTING.getItem(4), recipe);
        // --- Buffer Stop
        recipe = new Object[] {
                "SPS", "ITI", "dBh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'B', OrePrefixes.block.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.BUFFER_STOP.getItem(4), recipe);
        // --- One Way Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', new ItemStack(Blocks.stone_pressure_plate),
                'R', new ItemStack(Blocks.piston),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ONEWAY.getItem(4), recipe);
        // --- Directional Detector Track
        recipe = new Object[] {
                "SPS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'R', EnumDetector.ADVANCED.getItem(),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.DETECTOR_DIRECTION.getItem(4), recipe);
        // --- Gated One Way Track
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', new ItemStack(Blocks.fence),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.GATED_ONEWAY.getItem(4), recipe);
        // --- Gated Track
        recipe = new Object[] {
                "SRS", "ITI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'R', new ItemStack(Blocks.fence),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.GATED.getItem(4), recipe);
        // --- Suspended Rail
        recipe = new Object[] {
                "SRS", "IRI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'R', RailcraftItem.tie.getRecipeObject(ItemTie.EnumTie.WOOD),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SUSPENDED.getItem(4), recipe);
        // --- Disposal Rail
        recipe = new Object[] {
                "SRS", "IPI", "dRh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'R', RailcraftItem.tie.getRecipeObject(ItemTie.EnumTie.WOOD),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.DISPOSAL.getItem(4), recipe);
        // --- Wooden Rail
        recipe = new Object[] {
                "S S", "ITI", "d h",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.WOOD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SLOW.getItem(8), recipe);
        // --- Wooden Booster Track
        recipe = new Object[] {
                "SPS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SLOW_BOOSTER.getItem(4), recipe);
        // --- Wooden Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'B', OrePrefixes.bolt.get(Materials.Iron),
                'C', OrePrefixes.stick.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.WOOD),
                'T', EnumTrack.SLOW.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SLOW_SWITCH.getItem(2), recipe);
        // --- Wooden WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'B', OrePrefixes.bolt.get(Materials.Iron),
                'C', OrePrefixes.stick.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.WOOD),
                'T', EnumTrack.SLOW.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SLOW_WYE.getItem(2), recipe);
        // --- Wooden Junction
        recipe = new Object[] {
                "SIS", "ITI", "dIh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.WOOD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SLOW_JUNCTION.getItem(4), recipe);
        // --- Reinforced Track
        recipe = new Object[] {
                "S S", "ITI", "d h",
                'S', OrePrefixes.screw.get(Materials.Birmabright),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.REINFORCED.getItem(8), recipe);
        // --- Reinforced Booster Track
        recipe = new Object[] {
                "SPS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Birmabright),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.REINFORCED_BOOSTER.getItem(4), recipe);
        // --- Reinforced Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Birmabright),
                'B', OrePrefixes.bolt.get(Materials.Birmabright),
                'C', OrePrefixes.stick.get(Materials.Birmabright),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', EnumTrack.REINFORCED.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.REINFORCED_SWITCH.getItem(2), recipe);
        // --- Reinforced WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Birmabright),
                'B', OrePrefixes.bolt.get(Materials.Birmabright),
                'C', OrePrefixes.stick.get(Materials.Birmabright),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', EnumTrack.REINFORCED.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.REINFORCED_WYE.getItem(2), recipe);
        // --- Reinforced Junction
        recipe = new Object[] {
                "SIS", "ITI", "dIh",
                'S', OrePrefixes.screw.get(Materials.Birmabright),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.REINFORCED_JUNCTION.getItem(4), recipe);
        // --- Electric Track
        recipe = new Object[] {
                "S S", "ITI", "d h",
                'S', OrePrefixes.screw.get(Materials.Copper),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ELECTRIC),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ELECTRIC.getItem(8), recipe);
        // --- Electric Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Copper),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'C', OrePrefixes.stick.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ELECTRIC),
                'T', EnumTrack.ELECTRIC.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ELECTRIC_SWITCH.getItem(2), recipe);
        // --- Electric WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Copper),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'C', OrePrefixes.stick.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ELECTRIC),
                'T', EnumTrack.ELECTRIC.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ELECTRIC_WYE.getItem(2), recipe);
        // --- Electric Junction
        recipe = new Object[] {
                "SIS", "ITI", "dIh",
                'S', OrePrefixes.screw.get(Materials.Copper),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ELECTRIC),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.ELECTRIC_JUNCTION.getItem(4), recipe);
        // --- H.S Track
        recipe = new Object[] {
                "S S", "ITI", "d h",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED.getItem(8), recipe);
        // --- H.S Booster Track
        recipe = new Object[] {
                "SPS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_BOOST.getItem(8), recipe);
        // --- H.S Transition Track
        recipe = new Object[] {
                "IPI", "STS", "dPh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_TRANSITION.getItem(4), recipe);
        // --- H.S Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'C', OrePrefixes.stick.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', EnumTrack.SPEED
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_SWITCH.getItem(2), recipe);
        // --- H.S WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'C', OrePrefixes.stick.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', EnumTrack.SPEED
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_WYE.getItem(2), recipe);
        // --- Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'B', OrePrefixes.bolt.get(Materials.Iron),
                'C', OrePrefixes.stick.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', new ItemStack(Blocks.rail)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SWITCH.getItem(2), recipe);
        // --- WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'B', OrePrefixes.bolt.get(Materials.Iron),
                'C', OrePrefixes.stick.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', new ItemStack(Blocks.rail)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.WYE.getItem(2), recipe);
        // --- Junction
        recipe = new Object[] {
                "SIS", "ITI", "dIh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.JUNCTION.getItem(4), recipe);
        // --- Priming Track
        recipe = new Object[] {
                "SDS", "ITI", "dMh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'D', new ItemStack(Blocks.stone_pressure_plate),
                'M', new ItemStack(Items.flint_and_steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.REINFORCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.PRIMING.getItem(4), recipe);
        // --- Launcher Track
        recipe = new Object[] {
                "STS", "PNP", "dBh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'B', OrePrefixes.block.get(Materials.Steel),
                'N', new ItemStack(Blocks.piston),
                'T', EnumTrack.REINFORCED.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.LAUNCHER.getItem(4), recipe);
        // --- Elevator Track
        /*recipe = new Object[] {
                "STS", "PNP", "dBh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'B', OrePrefixes.block.get(Materials.Steel),
                'N', GT_ModHandler.getModItem("minecraft", "piston", 1L),
                'T', EnumTrack.REINFORCED.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack..getItem(4), recipe);*/
        // --- Track
        recipe = new Object[] {
                "S S", "ITI", "d h",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.rail, 8), recipe);
        // --- Golden Track
        recipe = new Object[] {
                "SPS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.ADVANCED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.golden_rail, 8), recipe);
        // --- Detector Track
        recipe = new Object[] {
                "SDS", "ITI", "dAh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'A', OrePrefixes.wireGt01.get(Materials.RedAlloy),
                'D', new ItemStack(Blocks.stone_pressure_plate),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.detector_rail, 8), recipe);
        // --- Activator Track
        recipe = new Object[] {
                "SDS", "ITI", "dAh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'A', OrePrefixes.wireGt01.get(Materials.RedAlloy),
                'D', new ItemStack(Blocks.redstone_torch),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.activator_rail, 8), recipe);
    }

    public void assemblerRecipe() {
        // --- Wooden Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SLOW.getItem(8), 200, 6);
        // --- Reinforced Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Birmabright, 2L)}, EnumTrack.REINFORCED.getItem(8), 200, 6);
        // --- Electric Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2L)}, EnumTrack.ELECTRIC.getItem(8), 200, 6);
        // --- H.S Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SPEED.getItem(8), 200, 6);
        // --- Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.rail, 8), 200, 6);

    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        assemblerRecipe();
    }
}
