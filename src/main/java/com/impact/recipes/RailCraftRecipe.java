package com.impact.recipes;

import com.impact.common.item.Core_Items2;
import com.impact.impact;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import mods.railcraft.common.blocks.machine.alpha.EnumMachineAlpha;
import mods.railcraft.common.blocks.signals.ItemSignalBlockSurveyor;
import mods.railcraft.common.blocks.signals.ItemSignalTuner;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.machine.delta.EnumMachineDelta;
import mods.railcraft.common.blocks.machine.epsilon.EnumMachineEpsilon;
import mods.railcraft.common.blocks.machine.gamma.EnumMachineGamma;
import mods.railcraft.common.blocks.signals.EnumSignal;
import mods.railcraft.common.blocks.tracks.EnumTrack;
import mods.railcraft.common.carts.EnumCart;
import mods.railcraft.common.items.*;
import net.dragon.computery.cart.ModEnumCart;
import net.dragon.computery.cart.render.ModRenderType;
import net.dragon.computery.item.Objects;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import pl.asie.computronics.Computronics;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class RailCraftRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

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
        removeRecipeByOutput(EnumDetector.ITEM.getItem());
        removeRecipeByOutput(EnumDetector.ANY.getItem());
        removeRecipeByOutput(EnumDetector.EMPTY.getItem());
        removeRecipeByOutput(EnumDetector.MOB.getItem());
        removeRecipeByOutput(EnumDetector.POWERED.getItem());
        removeRecipeByOutput(EnumDetector.PLAYER.getItem());
        removeRecipeByOutput(EnumDetector.EXPLOSIVE.getItem());
        removeRecipeByOutput(EnumDetector.ANIMAL.getItem());
        removeRecipeByOutput(EnumDetector.TANK.getItem());
        removeRecipeByOutput(EnumDetector.ADVANCED.getItem());

        //todo не удаляется рецепт у энерджи детектора
        removeRecipeByOutput(EnumDetector.ENERGY.getItem());

        removeRecipeByOutput(EnumDetector.AGE.getItem());
        removeRecipeByOutput(EnumDetector.TRAIN.getItem());
        removeRecipeByOutput(EnumDetector.SHEEP.getItem());
        removeRecipeByOutput(EnumDetector.VILLAGER.getItem());
        removeRecipeByOutput(EnumDetector.LOCOMOTIVE.getItem());
        removeRecipeByOutput(EnumDetector.ROUTING.getItem());
        removeRecipeByOutput(EnumMachineGamma.ITEM_LOADER.getItem());
        removeRecipeByOutput(EnumMachineGamma.ITEM_UNLOADER.getItem());
        removeRecipeByOutput(EnumMachineGamma.ITEM_LOADER_ADVANCED.getItem());
        removeRecipeByOutput(EnumMachineGamma.ITEM_UNLOADER_ADVANCED.getItem());
        removeRecipeByOutput(EnumMachineGamma.FLUID_LOADER.getItem());
        removeRecipeByOutput(EnumMachineGamma.FLUID_UNLOADER.getItem());

        //todo отображается рецепт из ic2
        removeRecipeByOutput(EnumMachineGamma.ENERGY_LOADER.getItem());
        removeRecipeByOutput(EnumMachineGamma.ENERGY_UNLOADER.getItem());

        removeRecipeByOutput(EnumMachineGamma.DISPENSER_CART.getItem());
        removeRecipeByOutput(EnumMachineGamma.DISPENSER_TRAIN.getItem());
        removeRecipeByOutput(EnumMachineGamma.RF_LOADER.getItem());
        removeRecipeByOutput(EnumMachineGamma.RF_UNLOADER.getItem());
        removeRecipeByOutput(EnumMachineEpsilon.ELECTRIC_FEEDER.getItem());
        removeRecipeByOutput(EnumMachineEpsilon.FORCE_TRACK_EMITTER.getItem());
        removeRecipeByOutput(EnumMachineEpsilon.FLUX_TRANSFORMER.getItem());
        removeRecipeByOutput(EnumSignal.BOX_INTERLOCK.getItem());
        removeRecipeByOutput(EnumSignal.DUAL_HEAD_BLOCK_SIGNAL.getItem());
        removeRecipeByOutput(EnumSignal.SWITCH_MOTOR.getItem());
        removeRecipeByOutput(EnumSignal.BLOCK_SIGNAL.getItem());
        removeRecipeByOutput(EnumSignal.SWITCH_LEVER.getItem());
        removeRecipeByOutput(EnumSignal.BOX_SEQUENCER.getItem());
        removeRecipeByOutput(EnumSignal.BOX_CAPACITOR.getItem());
        removeRecipeByOutput(EnumSignal.BOX_RECEIVER.getItem());
        removeRecipeByOutput(EnumSignal.BOX_CONTROLLER.getItem());
        removeRecipeByOutput(EnumSignal.BOX_ANALOG_CONTROLLER.getItem());
        removeRecipeByOutput(EnumSignal.DISTANT_SIGNAL.getItem());
        removeRecipeByOutput(EnumSignal.DUAL_HEAD_DISTANT_SIGNAL.getItem());
        removeRecipeByOutput(EnumSignal.BOX_BLOCK_RELAY.getItem());
        removeRecipeByOutput(EnumCart.WORK.getCartItem());
        removeRecipeByOutput(EnumCart.TANK.getCartItem());
        removeRecipeByOutput(EnumCart.CARGO.getCartItem());
        removeRecipeByOutput(EnumCart.TRACK_RELAYER.getCartItem());
        removeRecipeByOutput(EnumCart.UNDERCUTTER.getCartItem());
        removeRecipeByOutput(EnumCart.TRACK_LAYER.getCartItem());
        removeRecipeByOutput(EnumCart.TRACK_REMOVER.getCartItem());
        removeRecipeByOutput(EnumCart.REDSTONE_FLUX.getCartItem());
        removeRecipeByOutput(EnumCart.LOCO_ELECTRIC.getCartItem());
        removeRecipeByOutput(EnumCart.LOCO_STEAM_SOLID.getCartItem());
        removeRecipeByOutput(EnumCart.BORE.getCartItem());
        removeRecipeByOutput(EnumCart.ENERGY_BATBOX.getCartItem());
        removeRecipeByOutput(EnumCart.ENERGY_CESU.getCartItem());
        removeRecipeByOutput(EnumCart.ENERGY_MFE.getCartItem());
        removeRecipeByOutput(EnumMachineAlpha.STEAM_TRAP_AUTO.getItem());
        removeRecipeByOutput(EnumMachineAlpha.STEAM_TRAP_MANUAL.getItem());
        removeRecipeByOutput(EnumMachineAlpha.FEED_STATION.getItem());
        removeRecipeByOutput(EnumMachineAlpha.TRADE_STATION.getItem());
        removeRecipeByOutput(ItemCrowbar.getItem());
        removeRecipeByOutput(ItemCrowbarReinforced.getItem());
        removeRecipeByOutput(ItemWhistleTuner.getItem());
        removeRecipeByOutput(ItemElectricMeter.getItem());
        removeRecipeByOutput(ItemSignalTuner.getItem());
        removeRecipeByOutput(ItemSignalBlockSurveyor.getItem());
        removeRecipeByOutput(RailcraftItem.signalLamp.getStack());

        //todo
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "tool.magnifying.glass", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "armor.goggles", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "upgrade.lapotron", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "borehead.iron", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "borehead.steel", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "borehead.diamond", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "frame", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit.controller", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit.signal", 1L));
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "part.circuit.receiver", 1L));

        //todo не удаляется рецепт у кабеля
        removeRecipeByOutput(GT_ModHandler.getModItem("Railcraft", "machine.delta", 1L));

        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.WOOD));

        //todo не удаляются рецепты
        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.STANDARD));
        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.ADVANCED));
        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.ELECTRIC));
        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.REINFORCED));
        removeRecipeByOutput(RailcraftItem.rail.getStack(1, ItemRail.EnumRail.SPEED));
        removeRecipeByOutput(RailcraftItem.rebar.getStack());
        removeRecipeByOutput(new ItemStack(Computronics.railcraft.digitalControllerBox, 1, 0));
        removeRecipeByOutput(new ItemStack(Computronics.railcraft.digitalReceiverBox, 1, 0));
        removeRecipeByOutput(new ItemStack(Computronics.railcraft.locomotiveRelay, 1, 0));

        removeRecipeByOutput(new ItemStack(Blocks.rail));
        removeRecipeByOutput(new ItemStack(Blocks.golden_rail));
        removeRecipeByOutput(new ItemStack(Blocks.detector_rail));
        removeRecipeByOutput(new ItemStack(Blocks.activator_rail));

        removeRecipeByOutput(new ItemStack(Objects.metaItem, 1, 0));
        removeRecipeByOutput(new ItemStack(Objects.metaItem, 1, 1));
        removeRecipeByOutput(new ItemStack(Objects.metaItem, 1, 2));
        removeRecipeByOutput(ModRenderType.DIESEL.getItemWithRenderer("railcraft:default", ModEnumCart.LOCO_DIESEL.getCartItem()));
        removeRecipeByOutput(new ItemStack(Objects.nfcTrack, 1, 0));
        removeRecipeByOutput(new ItemStack(Objects.metaItem, 1, 3));
        removeRecipeByOutput(new ItemStack(Objects.ocComponents, 4, 0));
    }

    public void handRecipe() {
        Object[] recipe;
        //todo не работает рецепт
        // --- Wire Support Frame
        recipe = new Object[] {
                "RPR", "IwI", "III",
                'P', OrePrefixes.plate.get(Materials.Iron),
                'R', OrePrefixes.plate.get(Materials.Rubber),
                'I', RailcraftItem.rebar
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "frame", 2L), recipe);

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
                'S', OrePrefixes.screw.get(Materials.Aluminium),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED.getItem(8), recipe);
        // --- H.S Booster Track
        recipe = new Object[] {
                "SPS", "ITI", "dPh",
                'S', OrePrefixes.screw.get(Materials.Aluminium),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_BOOST.getItem(8), recipe);
        // --- H.S Transition Track
        recipe = new Object[] {
                "IPI", "STS", "dPh",
                'S', OrePrefixes.screw.get(Materials.Aluminium),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.STONE)
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_TRANSITION.getItem(4), recipe);
        // --- H.S Switch
        recipe = new Object[] {
                "SIS", "BTC", "d h",
                'S', OrePrefixes.screw.get(Materials.Aluminium),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'C', OrePrefixes.stick.get(Materials.Steel),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.SPEED),
                'T', EnumTrack.SPEED
        };
        GT_ModHandler.addCraftingRecipe(EnumTrack.SPEED_SWITCH.getItem(2), recipe);
        // --- H.S WYE
        recipe = new Object[] {
                "SIS", "BTC", "dIh",
                'S', OrePrefixes.screw.get(Materials.Aluminium),
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
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.golden_rail, 4), recipe);
        // --- Detector Track
        recipe = new Object[] {
                "SDS", "ITI", "dAh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'A', OrePrefixes.wireGt01.get(Materials.RedAlloy),
                'D', new ItemStack(Blocks.stone_pressure_plate),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.detector_rail, 4), recipe);
        // --- Activator Track
        recipe = new Object[] {
                "SDS", "ITI", "dAh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'A', OrePrefixes.wireGt01.get(Materials.RedAlloy),
                'D', new ItemStack(Blocks.redstone_torch),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.activator_rail, 4), recipe);
        // --- NFC Track
        recipe = new Object[] {
                "SDS", "ITI", "dAh",
                'S', OrePrefixes.screw.get(Materials.Iron),
                'A', OrePrefixes.plate.get(Materials.RedAlloy),
                'D', new ItemStack(Objects.metaItem, 1, 3),
                'I', RailcraftItem.rail.getRecipeObject(ItemRail.EnumRail.STANDARD),
                'T', RailcraftItem.railbed.getRecipeObject(ItemRailbed.EnumRailbed.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Objects.nfcTrack, 4, 0), recipe);
        // --- Item Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', OrePrefixes.log.get(Materials.Wood),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Blocks.rail)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ITEM.getItem(1), recipe);


        // --- Any Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "stone",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ANY.getItem(1), recipe);
        // --- Empty Detector
        recipe = new Object[] {
                "L L", "PIP", "LPL",
                'L', "stoneBricks",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.EMPTY.getItem(1), recipe);
        // --- Powered Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "stoneCobble",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.POWERED.getItem(1), recipe);
        // --- Player Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "slabStoneBrick",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', GT_ModHandler.getModItem("malisisdoors", "player_sensor", 1L)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.PLAYER.getItem(1), recipe);
        // --- Explosive Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "slabWood",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Blocks.tnt)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.EXPLOSIVE.getItem(1), recipe);
        // --- Animal Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "logWood",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Items.leather)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ANIMAL.getItem(1), recipe);
        // --- Tank Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', new ItemStack(Blocks.brick_block),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', GT_ModHandler.getModItem("extracells", "certustank", 1L)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.TANK.getItem(1), recipe);
        // --- Advanced Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "logWood",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', EnumDetector.ANY.getItem(1)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ADVANCED.getItem(1), recipe);
        // --- Energy Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', OrePrefixes.plate.get(Materials.Tin),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', OrePrefixes.cableGt01.get(Materials.Tin)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ENERGY.getItem(1), recipe);
        // --- Age Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "logWood",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', "cropWheat"
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.AGE.getItem(1), recipe);
        // --- Train Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', new ItemStack(Blocks.brick_block),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.TRAIN.getItem(1), recipe);
        // --- Sheep Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "blockWool",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Items.shears)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.SHEEP.getItem(1), recipe);
        // --- Villager Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', new ItemStack(Items.leather),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', OrePrefixes.plate.get(Materials.Emerald)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.VILLAGER.getItem(1), recipe);
        // --- Locomotive Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.LOCOMOTIVE.getItem(1), recipe);
        // --- Routing Detector
        recipe = new Object[] {
                "LTL", "PIP", "LPL",
                'L', "marble",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', new ItemStack(Blocks.heavy_weighted_pressure_plate),
                'T', EnumSignal.SWITCH_LEVER.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumDetector.ROUTING.getItem(1), recipe);
        // --- Item Loader
        recipe = new Object[] {
                "LTL", "PIP", "LCL",
                'L', "cobblestone",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ITEM.getItem(),
                'C', new ItemStack(Blocks.chest),
                'T', new ItemStack(Blocks.hopper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ITEM_LOADER.getItem(1), recipe);
        // --- Item Unloader
        recipe = new Object[] {
                "LCL", "PIP", "LTL",
                'L', "cobblestone",
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ITEM.getItem(),
                'C', new ItemStack(Blocks.chest),
                'T', new ItemStack(Blocks.hopper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ITEM_UNLOADER.getItem(1), recipe);
        // --- Advanced Item Loader
        recipe = new Object[] {
                "LTL", "PIP", "LCL",
                'L', OrePrefixes.plate.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ANY.getItem(),
                'C', new ItemStack(Blocks.chest),
                'T', new ItemStack(Blocks.hopper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ITEM_LOADER_ADVANCED.getItem(1), recipe);
        // --- Advanced Item Unloader
        recipe = new Object[] {
                "LCL", "PIP", "LTL",
                'L', OrePrefixes.plate.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ANY.getItem(),
                'C', new ItemStack(Blocks.chest),
                'T', new ItemStack(Blocks.hopper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ITEM_UNLOADER_ADVANCED.getItem(1), recipe);
        // --- Fluid Loader
        recipe = new Object[] {
                "LTL", "UIM", "LCL",
                'L', "paneGlass",
                'T', OrePrefixes.pipeMedium.get(Materials.Steel),
                'I', EnumDetector.TANK.getItem(),
                'C', GT_ModHandler.getModItem("extracells", "certustank", 1L),
                'U', ItemList.Electric_Pump_LV,
                'M', ItemList.Electric_Motor_LV
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.FLUID_LOADER.getItem(1), recipe);
        // --- Fluid Unloader
        recipe = new Object[] {
                "LCL", "UIM", "LTL",
                'L', "paneGlass",
                'T', OrePrefixes.pipeMedium.get(Materials.Steel),
                'I', EnumDetector.TANK.getItem(),
                'C', GT_ModHandler.getModItem("extracells", "certustank", 1L),
                'U', ItemList.Electric_Pump_LV,
                'M', ItemList.Electric_Motor_LV
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.FLUID_UNLOADER.getItem(1), recipe);
        // --- Energy Loader
        recipe = new Object[] {
                "LTL", "PIP", "LCL",
                'L', OrePrefixes.plate.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ENERGY.getItem(),
                'C', ItemList.Battery_RE_MV_Lithium,
                'T', OrePrefixes.cableGt01.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ENERGY_LOADER.getItem(1), recipe);
        // --- Energy Unloder
        recipe = new Object[] {
                "LCL", "PIP", "LTL",
                'L', OrePrefixes.plate.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ENERGY.getItem(),
                'C', ItemList.Battery_RE_MV_Lithium,
                'T', OrePrefixes.cableGt01.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.ENERGY_UNLOADER.getItem(1), recipe);
        // --- Cart Dispenser
        recipe = new Object[] {
                "DMT", " C ",
                'D', new ItemStack(Blocks.dispenser),
                'M', new ItemStack(Items.minecart),
                'T', new ItemStack(Blocks.rail),
                'C', ItemCrowbar.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.DISPENSER_CART.getItem(1), recipe);
        // --- Train Dispenser
        recipe = new Object[] {
                "CPC", "PDP", "CPC",
                'D', EnumMachineGamma.DISPENSER_CART.getItem(),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'C', ItemCrowbar.getItem()
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineGamma.DISPENSER_TRAIN.getItem(1), recipe);
        // --- Electric Feeder Unit
        recipe = new Object[] {
                "LCL", "PIP", "LCL",
                'L', OrePrefixes.plate.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ENERGY.getItem(),
                'C', OrePrefixes.wireGt02.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineEpsilon.ELECTRIC_FEEDER.getItem(1), recipe);
        // --- Flux Transformer
        recipe = new Object[] {
                "LCL", "PIP", "LTL",
                'L', OrePrefixes.plate.get(Materials.Bronze),
                'P', OrePrefixes.plate.get(Materials.RedAlloy),
                'I', EnumDetector.ENERGY.getItem(),
                'T', EnumMachineDelta.WIRE.getItem(),
                'C', OrePrefixes.wireGt01.get(Materials.Gold)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineEpsilon.FLUX_TRANSFORMER.getItem(1), recipe);
        // --- Force Track Emitter
        recipe = new Object[] {
                "LCL", "EIE", "WCW",
                'L', OrePrefixes.lens.get(Materials.Diamond),
                'W', OrePrefixes.cableGt01.get(Materials.Gold),
                'E', ItemList.Emitter_HV,
                'I', ItemList.Hull_HV,
                'C', OrePrefixes.circuit.get(Materials.Advanced)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineEpsilon.FORCE_TRACK_EMITTER.getItem(1), recipe);
        // --- Switch Lever
        recipe = new Object[] {
                "SRT", "LPS", "AAA",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'T', OrePrefixes.stick.get(Materials.Tin),
                'L', new ItemStack(Blocks.lever),
                'P', new ItemStack(Blocks.sticky_piston),
                'A', OrePrefixes.plate.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.SWITCH_LEVER.getItem(1), recipe);
        // --- Switch Motor
        recipe = new Object[] {
                "SRT", "LPC", "AAA",
                'S', OrePrefixes.screw.get(Materials.Steel),
                'R', OrePrefixes.stick.get(Materials.RedAlloy),
                'T', OrePrefixes.stick.get(Materials.Tin),
                'L', ItemList.Electric_Motor_LV,
                'P', ItemList.Electric_Piston_LV,
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'A', OrePrefixes.plate.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.SWITCH_MOTOR.getItem(1), recipe);
        // --- Signal Block
        recipe = new Object[] {
                "LCP", "dSW",
                'S', OrePrefixes.stick.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'L', RailcraftItem.signalLamp.getStack(),
                'W', OrePrefixes.screw.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BLOCK_SIGNAL.getItem(1), recipe);
        // --- Distant Block
        recipe = new Object[] {
                "LCP", "WSd",
                'S', OrePrefixes.stick.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'L', RailcraftItem.signalLamp.getStack(),
                'W', OrePrefixes.screw.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.DISTANT_SIGNAL.getItem(1), recipe);
        // --- Dual Head Block Signal
        recipe = new Object[] {
                "LCW", "PPP", "LCd",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'L', RailcraftItem.signalLamp.getStack(),
                'W', OrePrefixes.screw.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.DUAL_HEAD_BLOCK_SIGNAL.getItem(1), recipe);
        // --- Dual Head Distant Signal
        recipe = new Object[] {
                "LCd", "PPP", "LCW",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'L', RailcraftItem.signalLamp.getStack(),
                'W', OrePrefixes.screw.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.DUAL_HEAD_DISTANT_SIGNAL.getItem(1), recipe);
        // --- Signal Block Relay
        recipe = new Object[] {
                "  d", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', OrePrefixes.plate.get(Materials.RedAlloy)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_BLOCK_RELAY.getItem(1), recipe);
        // --- Signal Controll Box
        recipe = new Object[] {
                " d ", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', OrePrefixes.plate.get(Materials.RedAlloy)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_CONTROLLER.getItem(1), recipe);
        // --- Signal Receiver Box
        recipe = new Object[] {
                "d  ", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', OrePrefixes.plate.get(Materials.RedAlloy)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_RECEIVER.getItem(1), recipe);
        // --- Analog Signal Controll Box
        recipe = new Object[] {
                "  d", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_ANALOG_CONTROLLER.getItem(1), recipe);
        // --- Signal Capacitor Box
        recipe = new Object[] {
                " d ", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 10)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_CAPACITOR.getItem(1), recipe);
        // --- Signal Sequencer Box
        recipe = new Object[] {
                "d  ", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', GT_ModHandler.getModItem("ProjRed|Integration", "projectred.integration.gate", 1L, 26)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_SEQUENCER.getItem(1), recipe);
        // --- Signal Interlock Box
        recipe = new Object[] {
                "PRP", "PCP", "PRP",
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'R', OrePrefixes.plate.get(Materials.RedAlloy)
        };
        GT_ModHandler.addCraftingRecipe(EnumSignal.BOX_INTERLOCK.getItem(1), recipe);
        // --- Work Cart
        recipe = new Object[] {
                "hWw", " C ", " d ",
                'W', new ItemStack(Blocks.crafting_table),
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.WORK.getCartItem(), recipe);
        // --- Tank Cart
        recipe = new Object[] {
                "hWw", " C ", " d ",
                'W', GT_ModHandler.getModItem("extracells", "certustank", 1L),
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.TANK.getCartItem(), recipe);
        // --- Cargo Cart
        recipe = new Object[] {
                "hWw", "PCP", " d ",
                'W', new ItemStack(Blocks.chest),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.CARGO.getCartItem(), recipe);
        // --- Track Relayer Cart
        recipe = new Object[] {
                "hWw", "OTR", "MCD",
                'W', GT_ModHandler.getModItem("IC2NuclearControl", "blockNuclearControlLight", 1L),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'D', OrePrefixes.toolHeadDrill.get(Materials.Steel),
                'O', ItemList.Conveyor_Module_LV,
                'R', ItemList.Robot_Arm_LV,
                'M', ItemList.Electric_Motor_LV,
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.TRACK_RELAYER.getCartItem(), recipe);
        // --- Undercutter Cart
        recipe = new Object[] {
                "hWw", "PTR", "MCO",
                'W', GT_ModHandler.getModItem("IC2NuclearControl", "blockNuclearControlLight", 1L),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'O', ItemList.Conveyor_Module_LV,
                'P', ItemList.Electric_Piston_LV,
                'R', ItemList.Robot_Arm_LV,
                'M', ItemList.Electric_Motor_LV,
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.UNDERCUTTER.getCartItem(), recipe);
        // --- Track Layer Cart
        recipe = new Object[] {
                "hWw", "PTP", "MCR",
                'W', GT_ModHandler.getModItem("IC2NuclearControl", "blockNuclearControlLight", 1L),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'P', ItemList.Electric_Piston_LV,
                'R', ItemList.Robot_Arm_LV,
                'M', ItemList.Electric_Motor_LV,
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.TRACK_LAYER.getCartItem(), recipe);
        // --- Track Remover Cart
        recipe = new Object[] {
                "hWw", "PTP", "MCR",
                'W', GT_ModHandler.getModItem("IC2NuclearControl", "blockNuclearControlLight", 1L),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'P', ItemCrowbar.getItem(),
                'R', ItemList.Robot_Arm_LV,
                'M', ItemList.Electric_Motor_LV,
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.TRACK_REMOVER.getCartItem(), recipe);
        // --- Track Remover Cart
        recipe = new Object[] {
                "hWw", "PTP", "MCR",
                'W', GT_ModHandler.getModItem("IC2NuclearControl", "blockNuclearControlLight", 1L),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'P', ItemCrowbar.getItem(),
                'R', ItemList.Robot_Arm_LV,
                'M', ItemList.Electric_Motor_LV,
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.TRACK_REMOVER.getCartItem(), recipe);
        // --- Redstone Flux Cart
        recipe = new Object[] {
                "PdP", "OTO", "SCS",
                'O', GT_ModHandler.getModItem("EnderIO", "blockCapBank", 1L, 1),
                'T', OrePrefixes.circuit.get(Materials.Basic),
                'P', OrePrefixes.plate.get(Materials.Lead),
                'S', OrePrefixes.screw.get(Materials.Steel),
                'C', new ItemStack(Items.minecart)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.REDSTONE_FLUX.getCartItem(), recipe);
        // --- Steam Locomotive
        recipe = new Object[] {
                " wX", "TPT", "WZW",
                'T', ItemList.Casing_SolidSteel,
                'Z', new ItemStack(Items.chest_minecart, 1),
                'P', new ItemStack(Blocks.piston, 1),
                'X', ItemList.Machine_Steel_Boiler,
                'W', ItemList.Component_Minecart_Wheels_Steel
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.LOCO_STEAM_SOLID.getCartItem(), recipe);
        // --- Electric Locomotive
        recipe = new Object[] {
                "LFB", "MCM", "WTW",
                'L', GT_ModHandler.getModItem("computronics", "computronics.colorfulLamp", 1L, 0),
                'F', EnumMachineEpsilon.ELECTRIC_FEEDER.getItem(),
                'B', ItemList.Casing_SolidSteel,
                'M', ItemList.Electric_Motor_MV,
                'C', ItemList.MV_Coil,
                'W', ItemList.Component_Minecart_Wheels_Steel,
                'T', new ItemStack(Items.minecart, 1, 0)
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.LOCO_ELECTRIC.getCartItem(), recipe);
        // --- Diesel Locomotive
        recipe = new Object[] {
                "LFB", "MCM", "WTW",
                'L', GT_ModHandler.getModItem("computronics", "computronics.colorfulLamp", 1L, 0),
                'F', ItemList.Generator_Diesel_MV,
                'B', ItemList.Casing_SolidSteel,
                'M', ItemList.Electric_Motor_MV,
                'C', ItemList.MV_Coil,
                'W', new ItemStack(Objects.metaItem, 1, 0),
                'T', new ItemStack(Objects.metaItem, 1, 1)
        };
        GT_ModHandler.addCraftingRecipe(ModRenderType.DIESEL.getItemWithRenderer("railcraft:default", ModEnumCart.LOCO_DIESEL.getCartItem()), recipe);
        // --- Wheels
        recipe = new Object[] {
                " h ", "RSR", " w ",
                'R', OrePrefixes.ring.get(Materials.BlueSteel),
                'S', OrePrefixes.stick.get(Materials.BlueSteel)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Objects.metaItem, 1, 0), recipe);
        // --- Cart
        recipe = new Object[] {
                " h ", "PwP", "WPW", 'P',
                OrePrefixes.plate.get(Materials.BlueSteel),
                'W', new ItemStack(Objects.metaItem, 1, 0)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Objects.metaItem, 1, 1), recipe);
        // --- Tunnel Bore
        recipe = new Object[] {
                "FPF", "BCB", "hTw",
                'C', new ItemStack(Items.minecart, 1),
                'T', new ItemStack(Items.chest_minecart, 1),
                'P', new ItemStack(Blocks.piston, 1),
                'F', ItemList.Hull_Steel,
                'B', ItemList.Machine_Steel_Boiler
        };
        GT_ModHandler.addCraftingRecipe(EnumCart.BORE.getCartItem(), recipe);
        // --- Crowbar
        recipe = new Object[] {
                "hDS", "DSD", "SDf",
                'S', OrePrefixes.stick.get(Materials.Iron),
                'D', Dyes.dyeRed
        };
        GT_ModHandler.addCraftingRecipe(ItemCrowbar.getItem(), recipe);
        // --- Steel Crowbar
        recipe = new Object[] {
                "hDS", "DSD", "SDf",
                'S', OrePrefixes.stick.get(Materials.Steel),
                'D', Dyes.dyeRed
        };
        GT_ModHandler.addCraftingRecipe(ItemCrowbarReinforced.getItem(), recipe);
        // --- Whistle
        recipe = new Object[] {
                "SfS", "SSS", " Sh",
                'S', OrePrefixes.stick.get(Materials.Iron)
        };
        GT_ModHandler.addCraftingRecipe(ItemWhistleTuner.getItem(), recipe);
        // --- Trade Station
        recipe = new Object[] {
                "SGS", "EDE", "SGS",
                'E', OrePrefixes.plate.get(Materials.Emerald),
                'S', OrePrefixes.plate.get(Materials.Steel),
                'G', new ItemStack(Blocks.glass_pane, 1, 32767),
                'D', new ItemStack(Blocks.dispenser, 1, 0)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineAlpha.TRADE_STATION.getItem(1), recipe);
        // --- Manual Steam Trap
        recipe = new Object[] {
                "PBP", "PDP", "PPP",
                'B', new ItemStack(Blocks.iron_bars, 1, 0),
                'P', OrePrefixes.plate.get(Materials.Steel),
                'D', new ItemStack(Blocks.dispenser, 1, 0)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineAlpha.STEAM_TRAP_MANUAL.getItem(1), recipe);
        // --- Automated Steam Trap
        recipe = new Object[] {
                " h ", "SHS", "PDP",
                'S', OrePrefixes.stick.get(Materials.RedAlloy),
                'H', EnumMachineAlpha.STEAM_TRAP_MANUAL.getItem(1),
                'D', EnumDetector.ANY.getItem(),
                'P', OrePrefixes.plate.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineAlpha.STEAM_TRAP_AUTO.getItem(1), recipe);
        // --- Feed Station
        recipe = new Object[] {
                "PCP", "CSC", "PCP",
                'P', OrePrefixes.plate.get(Materials.Wood),
                'S', OrePrefixes.plate.get(Materials.Steel),
                'C', new ItemStack(Items.golden_carrot, 1, 0)
        };
        GT_ModHandler.addCraftingRecipe(EnumMachineAlpha.FEED_STATION.getItem(1), recipe);

        //todo
        // --- Trackmans Goggle
        recipe = new Object[] {
                "LBL", "RSR", "PCP",
                'L', OrePrefixes.lens.get(Materials.Glass),
                'R', OrePrefixes.ring.get(Materials.Steel),
                'S', OrePrefixes.screw.get(Materials.Steel),
                'B', OrePrefixes.bolt.get(Materials.Steel),
                'P', new ItemStack(Items.leather),
                'C', OrePrefixes.circuit.get(Materials.Basic)
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "armor.goggles", 1L), recipe);
        //todo
        // --- Magnifying Glass
        recipe = new Object[] {
                "  L", " R ", "S  ",
                'L', OrePrefixes.lens.get(Materials.Glass),
                'R', OrePrefixes.ring.get(Materials.Steel),
                'S', OrePrefixes.stick.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "tool.magnifying.glass", 1L), recipe);

        // --- Signal Lamp
        recipe = new Object[] {
                "GIP", "GOP", "GNP",
                'G', "paneGlass",
                'P', OrePrefixes.plate.get(Materials.Iron),
                'I', GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 29),
                'O', GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 30),
                'N', GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 31)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.signalLamp.getStack(), recipe);

        //todo
        // --- Lapatron Loader Upgrade
        recipe = new Object[] {
                "PCP", "WLW", "PCP",
                'W', OrePrefixes.itemCasing.get(Materials.Aluminium),
                'C', OrePrefixes.circuit.get(Materials.Advanced),
                'P', OrePrefixes.cableGt02.get(Materials.Platinum),
                'L', GT_ModHandler.getModItem("IC2", "itemBatLamaCrystal", 1L,  GT_Values.W)
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "upgrade.lapotron", 1L), recipe);
        //todo
        // --- Borehead Iron
        recipe = new Object[] {
                "GPG", "PBP", "GPG",
                'B', OrePrefixes.block.get(Materials.Iron),
                'G', OrePrefixes.gear.get(Materials.Iron),
                'P', OrePrefixes.plate.get(Materials.Iron)
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "borehead.iron", 1L), recipe);
        //todo
        // --- Borehead Steel
        recipe = new Object[] {
                "GPG", "PBP", "GPG",
                'B', OrePrefixes.block.get(Materials.Steel),
                'G', OrePrefixes.gear.get(Materials.Steel),
                'P', OrePrefixes.plate.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "borehead.steel", 1L), recipe);
        //todo
        // --- Borehead Diamond
        recipe = new Object[] {
                "DD ",
                'D', ItemList.Component_Grinder_Diamond
        };
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("Railcraft", "borehead.diamond", 1L), recipe);


        // --- Wooden Rail
        recipe = new Object[] {
                "ITW",
                'I', OrePrefixes.stick.get(Materials.Iron),
                'W', OrePrefixes.stick.get(Materials.Wood),
                'T', RailcraftItem.tie.getRecipeObject(ItemTie.EnumTie.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(2, ItemRail.EnumRail.WOOD), recipe);
        recipe = new Object[] {
                "ITW","ITW",
                'I', OrePrefixes.stick.get(Materials.Iron),
                'W', OrePrefixes.stick.get(Materials.Wood),
                'T', RailcraftItem.tie.getRecipeObject(ItemTie.EnumTie.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(4, ItemRail.EnumRail.WOOD), recipe);
        recipe = new Object[] {
                "ITW","ITW","ITW",
                'I', OrePrefixes.stick.get(Materials.Iron),
                'W', OrePrefixes.stick.get(Materials.Wood),
                'T', RailcraftItem.tie.getRecipeObject(ItemTie.EnumTie.WOOD)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(6, ItemRail.EnumRail.WOOD), recipe);
        // --- Extruder Shape (Rail)
        recipe = new Object[] {
                "f","P","x",
                'P', ItemList.Shape_Empty
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(166, 1), recipe);
        // --- Standart Rail
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', OrePrefixes.stickLong.get(Materials.Iron)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), recipe);
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', OrePrefixes.stickLong.get(Materials.WroughtIron)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(5, ItemRail.EnumRail.STANDARD), recipe);
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', OrePrefixes.stickLong.get(Materials.Steel)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(8, ItemRail.EnumRail.STANDARD), recipe);
        // --- Advanced Rail
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', CoreItems2.getRecipe(167, 1)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), recipe);
        // --- Electric Rail
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', CoreItems2.getRecipe(168, 1)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ELECTRIC), recipe);
        // --- Reinforced Rail
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', CoreItems2.getRecipe(169, 1)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(6, ItemRail.EnumRail.REINFORCED), recipe);
        // --- H.S. Rail
        recipe = new Object[] {
                " I ","hIf"," I ",
                'I', CoreItems2.getRecipe(170, 1)
        };
        GT_ModHandler.addCraftingRecipe(RailcraftItem.rail.getStack(6, ItemRail.EnumRail.SPEED), recipe);

        // --- Advanced Metal Mix Ingot
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Iron),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Gold)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Iron),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Gold)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Gold)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Gold)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Electrum)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Electrum)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Aluminium),
                'G', OrePrefixes.plate.get(Materials.Electrum)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(167, 4), recipe);
        // --- Electric Metal Mix Ingot
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Iron),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Iron),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Copper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.AnnealedCopper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.AnnealedCopper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Aluminium),
                'G', OrePrefixes.plate.get(Materials.AnnealedCopper)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(168, 4), recipe);
        // --- Reinforced Metal Mix Ingot
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.WroughtIron),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Lead)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.WroughtIron),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Lead)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Invar),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Lead)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Invar),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Lead)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Invar),
                'T', OrePrefixes.plate.get(Materials.Tin),
                'G', OrePrefixes.plate.get(Materials.Obsidian)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Invar),
                'T', OrePrefixes.plate.get(Materials.Zinc),
                'G', OrePrefixes.plate.get(Materials.Obsidian)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Invar),
                'T', OrePrefixes.plate.get(Materials.Aluminium),
                'G', OrePrefixes.plate.get(Materials.Obsidian)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(169, 4), recipe);
        // --- H.S. Metal Mix Ingot
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.Bronze)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.Steel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.Brass)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 1), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.BlackSteel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.Bronze)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.BlackSteel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.Brass)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 2), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.BlackSteel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.ConductiveIron)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 3), recipe);
        recipe = new Object[] {
                "I","T","G",
                'I', OrePrefixes.plate.get(Materials.BlackSteel),
                'T', OrePrefixes.plate.get(Materials.Silver),
                'G', OrePrefixes.plate.get(Materials.EnergeticAlloy)
        };
        GT_ModHandler.addCraftingRecipe(CoreItems2.getRecipe(170, 4), recipe);


        // --- Computronics
        // --- Digital Signal Controller Box
        recipe = new Object[] {
                "PSP","PBP","PCP",
                'P', OrePrefixes.plate.get(Materials.Iron),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'S', ItemList.Sensor_LV,
                'B', EnumSignal.BOX_CONTROLLER.getItem(1)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Computronics.railcraft.digitalControllerBox, 1, 0), recipe);
        // --- Digital Signal Receiver Box
        recipe = new Object[] {
                "PSP","PBP","PCP",
                'P', OrePrefixes.plate.get(Materials.Iron),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'S', ItemList.Sensor_LV,
                'B', EnumSignal.BOX_RECEIVER.getItem(1)
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Computronics.railcraft.digitalReceiverBox, 1, 0), recipe);
        // --- Digital Locomotive Relay
        recipe = new Object[] {
                "SCE","AHM","hCw",
                'M', ItemElectricMeter.getItem(),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'A', OrePrefixes.cableGt01.get(Materials.Tin),
                'S', ItemList.Sensor_LV,
                'E', ItemList.Emitter_LV,
                'H', ItemList.Hull_LV
        };
        GT_ModHandler.addCraftingRecipe(new ItemStack(Computronics.railcraft.locomotiveRelay, 1, 0), recipe);


        // --- RailAssembler Casing
        recipe = new Object[] {
                "PhP","GBG","PwP",
                'P', OrePrefixes.plate.get(Materials.WroughtIron),
                'G', OrePrefixes.gearGtSmall.get(Materials.WroughtIron),
                'B', OrePrefixes.frameGt.get(Materials.WroughtIron)
        };
        GT_ModHandler.addCraftingRecipe(GT_ItemList.RailAssemblerCasing.get(2L), recipe);
        // --- RailAssembler
        recipe = new Object[] {
                "RwR","CAC","GOG",
                'R', ItemList.Robot_Arm_LV,
                'O', ItemList.Conveyor_Module_LV,
                'A', GT_ItemList.RailAssemblerCasing.get(1L),
                'C', OrePrefixes.circuit.get(Materials.Basic),
                'G', OrePrefixes.gearGt.get(Materials.WroughtIron)
        };
        GT_ModHandler.addCraftingRecipe(GT_ItemList.Rail_Assembler.get(1L), recipe);
    }

    public void assemblerRecipe() {
        // --- BatBox Cart
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 0), EnumCart.ENERGY_BATBOX.getCartItem(), 100, 16);
        // --- CESU Cart
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 7), EnumCart.ENERGY_CESU.getCartItem(), 100, 16);
        // --- MFE Cart
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.getModItem("IC2", "blockElectric", 1L, 1), EnumCart.ENERGY_MFE.getCartItem(), 100, 16);
        // --- Work Cart
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.minecart, 1), new ItemStack(Blocks.crafting_table), EnumCart.WORK.getCartItem(), 100, 16);
        // --- Tank Cart
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Items.minecart, 1), GT_ModHandler.getModItem("extracells", "certustank", 1L), EnumCart.TANK.getCartItem(), 100, 16);
        // --- Wheels
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.BlueSteel, 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.BlueSteel, 2L), new ItemStack(Objects.metaItem, 1, 0), 300, 2);
        // --- Cart
        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlueSteel, 3L), new ItemStack(Objects.metaItem, 2, 0), new ItemStack(Objects.metaItem, 1, 1), 300, 2);
        // --- Wooden Railbed
        GT_Values.RA.addAssemblerRecipe(RailcraftItem.tie.getStack(4, ItemTie.EnumTie.WOOD), ItemList.Circuit_Integrated.getWithDamage(0L, 4), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), 100, 2);
        // --- Stone Railbed
        GT_Values.RA.addAssemblerRecipe(RailcraftItem.tie.getStack(4, ItemTie.EnumTie.STONE), ItemList.Circuit_Integrated.getWithDamage(0L, 4), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), 100, 4);
        // --- Concrete Railbed
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{CoreItems2.getRecipe(164, 4), new ItemStack(GregTech_API.sBlockConcretes, 1, 8), ItemList.Circuit_Integrated.getWithDamage(0L, 4)}, null, CoreItems2.getRecipe(165, 1), 200, 8);
        // --- Electric Meter
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("IC2", "itemToolMEter", 1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), ItemElectricMeter.getItem(), 300, 30);
        // --- Signal Tuner
        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("IC2", "itemFreq", 1L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L), ItemSignalTuner.getItem(), 300, 30);
        // --- Signal Block Surveyor
        GT_Values.RA.addAssemblerRecipe(ItemSignalTuner.getItem(), new ItemStack(Items.compass), ItemSignalBlockSurveyor.getItem(), 300, 30);
        //todo
        // --- Electric Shunting Wire
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Lead, 2L)}, null, GT_ModHandler.getModItem("Railcraft", "machine.delta", 4L), 100, 8);
        // --- Stone Tie
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.stone_slab, 3, 0), RailcraftItem.rebar.getStack(1), RailcraftItem.tie.getStack(1, ItemTie.EnumTie.STONE), 128, 8);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.stone_slab, 3, 7), RailcraftItem.rebar.getStack(1), RailcraftItem.tie.getStack(1, ItemTie.EnumTie.STONE), 128, 8);
        // --- Wooden Rail
        GT_Values.RA.addAssemblerRecipe(RailcraftItem.tie.getStack(3, ItemTie.EnumTie.WOOD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 3L), RailcraftItem.rail.getStack(12, ItemRail.EnumRail.WOOD), 400, 4);
        GT_Values.RA.addAssemblerRecipe(RailcraftItem.tie.getStack(3, ItemTie.EnumTie.WOOD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 3L), RailcraftItem.rail.getStack(12, ItemRail.EnumRail.WOOD), 400, 4);
    }

    public void benderRecipe() {
        // --- Rebar
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 12L), RailcraftItem.rebar.getStack(4), 200, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 12L), RailcraftItem.rebar.getStack(8), 400, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 12L), RailcraftItem.rebar.getStack(10), 400, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Bronze, 12L), RailcraftItem.rebar.getStack(8), 400, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 12L), RailcraftItem.rebar.getStack(16), 800, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 12L), RailcraftItem.rebar.getStack(24), 1200, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 12L), RailcraftItem.rebar.getStack(32), 1600, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 12L), RailcraftItem.rebar.getStack(48), 2400, 15);
        GT_Values.RA.addBenderRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 8L), RailcraftItem.rebar.getStack(64), 2400, 15);
    }

    public void chemicalBathRecipe() {
        // --- Wooden Tie
        GT_Values.RA.addChemicalBathRecipe(GT_ModHandler.getModItem("minecraft", "wooden_slab", 1L, GT_Values.W), Materials.Creosote.getFluid(500), RailcraftItem.tie.getStack(1, ItemTie.EnumTie.WOOD), GT_Values.NI, GT_Values.NI, new int[]{10000}, 160, 5);
        // --- Concrete Tie
        GT_Values.RA.addChemicalBathRecipe(RailcraftItem.rebar.getStack(1), Materials.Concrete.getMolten(72L), CoreItems2.getRecipe(164, 1), GT_Values.NI, GT_Values.NI, new int[]{10000}, 160, 5);
    }

    public void circuitAssemblerRecipe() {
        for (Materials tMat : Materials.values()) {
            if (tMat.mStandardMoltenFluid != null && tMat.contains(SubTag.SOLDERING_MATERIAL) && !(GregTech_API.mUseOnlyGoodSolderingMaterials && !tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD))) {
                int tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1 : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

                // --- Configuration Circuit
                GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1), ItemList.Circuit_Parts_Resistor.get(2), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 2)}, tMat.getMolten(144L * tMultiplier / 2L), new ItemStack(Objects.metaItem, 1, 3), 200, 16);
                GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{ItemList.Circuit_Board_Phenolic_Good.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1), ItemList.Circuit_Parts_ResistorSMD.get(2), GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 2)}, tMat.getMolten(144L * tMultiplier / 2L), new ItemStack(Objects.metaItem, 1, 3), 200, 16);
                // --- Locomotive Card
                GT_Values.RA.addCircuitAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("OpenComputers", "item", 1L, 104), ItemList.Sensor_LV.get(1), GT_ModHandler.getModItem("OpenComputers", "item", 2, 11), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Electrum, 8L), GT_Utility.getIntegratedCircuit(1)}, tMat.getMolten(144L * tMultiplier / 2L), new ItemStack(Objects.ocComponents, 1, 0), 300, 120);
            }
        }
    }

    public void extruderRecipe() {
        // --- Standard Rail
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Aluminium, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), 100, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), 120, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.WroughtIron, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(5, ItemRail.EnumRail.STANDARD), 130, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(3, ItemRail.EnumRail.STANDARD), 110, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(8, ItemRail.EnumRail.STANDARD), 160, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.StainlessSteel, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(12, ItemRail.EnumRail.STANDARD), 200, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titanium, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(16, ItemRail.EnumRail.STANDARD), 240, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenSteel, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(20, ItemRail.EnumRail.STANDARD), 280, 24);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iridium, 3L), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(24, ItemRail.EnumRail.STANDARD), 320, 24);
        // --- Advanced Rail
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(167, 3), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), 100, 24);
        // --- Electric Rail
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(168, 3), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ELECTRIC), 100, 24);
        // --- Reinforced Rail
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(169, 3), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.REINFORCED), 100, 24);
        // --- H.S. Rail
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(170, 3), CoreItems2.getRecipe(166, 0), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.SPEED), 100, 24);
    }

    public void trackAssemblerRecipe() {
        // --- Wooden Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SLOW.getItem(8), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SLOW.getItem(16), 400, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SLOW.getItem(32), 600, 12);
        // --- Wooden Booster Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SLOW_BOOSTER.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SLOW_BOOSTER.getItem(8), 400, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SLOW_BOOSTER.getItem(16), 600, 12);
        // --- Wooden Switch
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(10), EnumTrack.SLOW.getItem(1), RailcraftItem.rail.getStack(1, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1L)}, EnumTrack.SLOW_SWITCH.getItem(2), 200, 6);
        // --- Wooden WYE
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(15), EnumTrack.SLOW.getItem(1), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1L)}, EnumTrack.SLOW_WYE.getItem(2), 200, 6);
        // --- Wooden Junction
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SLOW_JUNCTION.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(8, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SLOW_JUNCTION.getItem(8), 400, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(10, ItemRail.EnumRail.WOOD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.SLOW_JUNCTION.getItem(16), 600, 12);

        // --- Reinforced Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Birmabright, 2L)}, EnumTrack.REINFORCED.getItem(8), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 2L)}, EnumTrack.REINFORCED.getItem(20), 300, 8);
        // --- Reinforced Booster Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Birmabright, 2L)}, EnumTrack.REINFORCED_BOOSTER.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 2L)}, EnumTrack.REINFORCED_BOOSTER.getItem(10), 300, 8);
        // --- Reinforced Switch
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(10), EnumTrack.REINFORCED.getItem(1), RailcraftItem.rail.getStack(1, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Birmabright, 1L)}, EnumTrack.REINFORCED_SWITCH.getItem(2), 200, 6);
        // --- Reinforced WYE
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(15), EnumTrack.REINFORCED.getItem(1), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Birmabright, 1L)}, EnumTrack.REINFORCED_WYE.getItem(2), 200, 6);
        // --- Reinforced Junction
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Birmabright, 2L)}, EnumTrack.REINFORCED_JUNCTION.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(8, ItemRail.EnumRail.REINFORCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 2L)}, EnumTrack.REINFORCED_JUNCTION.getItem(10), 300, 8);

        // --- Electric Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2L)}, EnumTrack.ELECTRIC.getItem(8), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2L)}, EnumTrack.ELECTRIC.getItem(20), 300, 8);
        // --- Electric Switch
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(10), EnumTrack.ELECTRIC.getItem(1), RailcraftItem.rail.getStack(1, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1L)}, EnumTrack.ELECTRIC_SWITCH.getItem(2), 200, 6);
        // --- Electric WYE
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(15), EnumTrack.ELECTRIC.getItem(1), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1L)}, EnumTrack.ELECTRIC_WYE.getItem(2), 200, 6);
        // --- Electric Junction
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Copper, 2L)}, EnumTrack.ELECTRIC_JUNCTION.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(8, ItemRail.EnumRail.ELECTRIC), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 2L)}, EnumTrack.ELECTRIC_JUNCTION.getItem(10), 300, 8);

        // --- H.S Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 2L)}, EnumTrack.SPEED.getItem(8), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.SPEED.getItem(8), 200, 6);
        // --- H.S Booster Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 2L)}, EnumTrack.SPEED_BOOST.getItem(4), 200, 6);
        // --- H.S Switch
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(10), EnumTrack.SPEED.getItem(1), RailcraftItem.rail.getStack(1, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1L)}, EnumTrack.SPEED_SWITCH.getItem(2), 200, 6);
        // --- H.S WYE
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(15), EnumTrack.SPEED.getItem(1), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1L)}, EnumTrack.SPEED_WYE.getItem(2), 200, 6);
        // --- H.S Transition Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.SPEED), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Aluminium, 2L)}, EnumTrack.SPEED_TRANSITION.getItem(4), 200, 6);

        // --- Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.rail, 8), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.rail, 16), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(1), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.rail, 20), 400, 12);
        // --- Golden Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.golden_rail, 4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.golden_rail, 8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(5), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, new ItemStack(Blocks.golden_rail, 10), 400, 12);
        // --- Switch
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(10), new ItemStack(Blocks.rail), RailcraftItem.rail.getStack(1, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1L)}, EnumTrack.SWITCH.getItem(2), 200, 6);
        // --- WYE
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(15), new ItemStack(Blocks.rail), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1L)}, EnumTrack.WYE.getItem(2), 200, 6);
        // --- Junction
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.JUNCTION.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(8, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.JUNCTION.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(20), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(10, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.JUNCTION.getItem(12), 400, 12);

        // --- Control Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.CONTROL.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.CONTROL.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.CONTROL.getItem(16), 400, 12);
        // --- Locking Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.LOCKING.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.LOCKING.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.LOCKING.getItem(16), 400, 12);
        // --- Disembarking Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.DISEMBARK.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.DISEMBARK.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.DISEMBARK.getItem(16), 400, 12);
        // --- Embarking Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(8), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.EMBARKING.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.EMBARKING.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.EMBARKING.getItem(16), 400, 12);
        // --- Coupler Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), ItemCrowbarReinforced.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.COUPLER.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), ItemCrowbarReinforced.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.COUPLER.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), ItemCrowbarReinforced.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.COUPLER.getItem(16), 400, 12);
        // --- Whistle Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), ItemWhistleTuner.getItem(), ItemList.Casing_Stripes_A.get(1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.WHISTLE.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), ItemWhistleTuner.getItem(), ItemList.Casing_Stripes_A.get(1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.WHISTLE.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), ItemWhistleTuner.getItem(), ItemList.Casing_Stripes_A.get(1), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.WHISTLE.getItem(16), 400, 12);
        // --- Locomotive Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), EnumSignal.BLOCK_SIGNAL.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.LOCOMOTIVE.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), EnumSignal.BLOCK_SIGNAL.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.LOCOMOTIVE.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), EnumSignal.BLOCK_SIGNAL.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.LOCOMOTIVE.getItem(16), 400, 12);
        // --- Limiter Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), EnumDetector.ANY.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.LIMITER.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), EnumDetector.ANY.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.LIMITER.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), EnumDetector.ANY.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.LIMITER.getItem(16), 400, 12);
        // --- Routing Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), ItemTicket.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ROUTING.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), ItemTicketGold.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ROUTING.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), ItemTicket.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ROUTING.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), ItemTicketGold.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ROUTING.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), ItemTicket.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.ROUTING.getItem(16), 400, 12);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), ItemTicketGold.getTicket(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.ROUTING.getItem(16), 400, 12);
        // --- Buffer Stop
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.BUFFER_STOP.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.BUFFER_STOP.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.BUFFER_STOP.getItem(16), 400, 12);
        // --- One Way Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.piston), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ONEWAY.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.piston), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.ONEWAY.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.piston), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.ONEWAY.getItem(16), 400, 12);
        // --- Directional Detector
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.DETECTOR_DIRECTION.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.DETECTOR_DIRECTION.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), EnumDetector.ADVANCED.getItem(), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.DETECTOR_DIRECTION.getItem(12), 400, 12);
        // --- Gated One Way Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.GATED_ONEWAY.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.GATED_ONEWAY.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.ADVANCED), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.GATED_ONEWAY.getItem(16), 400, 12);
        // --- Gated Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.GATED.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.GATED.getItem(8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.fence), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.GATED.getItem(16), 400, 16);
        // --- Suspended Rail
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.tie.getStack(3, ItemTie.EnumTie.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SUSPENDED.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.tie.getStack(3, ItemTie.EnumTie.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.SUSPENDED.getItem(8), 300, 8);
        // --- Disposal Rail
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(8), RailcraftItem.tie.getStack(2, ItemTie.EnumTie.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.DISPOSAL.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(8), RailcraftItem.tie.getStack(2, ItemTie.EnumTie.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, EnumTrack.DISPOSAL.getItem(8), 300, 8);
        // --- Priming Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.REINFORCED), new ItemStack(Items.flint_and_steel), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.PRIMING.getItem(4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.REINFORCED), new ItemStack(Items.flint_and_steel), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSLA, 2L)}, EnumTrack.PRIMING.getItem(10), 300, 8);
        // --- Launcher Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), EnumTrack.REINFORCED.getItem(), new ItemStack(Blocks.piston), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedAlloy, 1L), GT_OreDictUnificator.get(OrePrefixes.block, Materials.Steel, 1L), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, EnumTrack.LAUNCHER.getItem(4), 200, 6);
        // --- Detector Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.detector_rail, 4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.detector_rail, 8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.stone_pressure_plate), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.detector_rail, 16), 400, 12);
        // --- Activator Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.redstone_torch), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.activator_rail, 4), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.redstone_torch), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Blocks.activator_rail, 8), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), new ItemStack(Blocks.redstone_torch), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Blocks.activator_rail, 16), 400, 12);
        // --- NFC Track
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.WOOD), RailcraftItem.rail.getStack(2, ItemRail.EnumRail.STANDARD), new ItemStack(Objects.metaItem, 1, 3), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Objects.nfcTrack, 4, 0), 200, 6);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), RailcraftItem.railbed.getStack(1, ItemRailbed.EnumRailbed.STONE), RailcraftItem.rail.getStack(4, ItemRail.EnumRail.STANDARD), new ItemStack(Objects.metaItem, 1, 3), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Iron, 2L)}, new ItemStack(Objects.nfcTrack, 8, 0), 300, 8);
        impact.I_RA.addTrackAssemblerRecipe(new ItemStack[]{GT_Utility.getIntegratedCircuit(7), CoreItems2.getRecipe(165, 1), RailcraftItem.rail.getStack(6, ItemRail.EnumRail.STANDARD), new ItemStack(Objects.metaItem, 1, 3), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Steel, 2L)}, new ItemStack(Objects.nfcTrack, 16, 0), 400, 12);
    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
        assemblerRecipe();
        benderRecipe();
        chemicalBathRecipe();
        circuitAssemblerRecipe();
        extruderRecipe();
        trackAssemblerRecipe();
    }
}
