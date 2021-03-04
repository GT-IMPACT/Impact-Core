package com.impact.recipes;

import static com.impact.loader.ItemRegistery.registerFluidBlocks;
import static gregtech.api.util.GT_ModHandler.removeFurnaceSmelting;
import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

import com.impact.recipes.debug.DEBUG_Recipe;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AfterGregTechPostLoadRecipes implements Runnable {

  @Override
  public void run() {
    registerFluidBlocks();
    new DEBUG_Recipe().run();
    //removeRecipe(ItemStack aInput);

    //removeFurnaceSmelting(ItemStack aInput)
    removeFurnaceSmelting(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1));
    removeFurnaceSmelting(new ItemStack(Items.clay_ball, 1, 0));

    removeFurnaceSmelting(
        GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 4));
    removeFurnaceSmelting(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 4));
    removeFurnaceSmelting(GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 2));
    removeFurnaceSmelting(GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L));

    removeFurnaceSmelting(GT_ModHandler.getModItem("Backpack", "boundLeather", 1L));

    removeFurnaceSmelting(new ItemStack(Blocks.stone));

    removeFurnaceSmelting(new ItemStack(Blocks.gravel));

    //removeRecipeByOutput();
    removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoHelmet", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoLeggings", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBoots", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumHelmet", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBodyarmor", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumLeggings", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBoots", 1, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemBatChargeRE", 1L, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemBatChargeAdv", 1L, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemBatChargeCrystal", 1L, GT_Values.W));
    removeRecipeByOutput(
        GT_ModHandler.getModItem("IC2", "itemBatChargeLamaCrystal", 1L, GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorReflectorThick", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorPlating", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorPlatingHeat", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorPlatingExplosive", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorVent", 1L, 1), true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorVentCore", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorVentGold", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorVentSpread", 1L), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorVentDiamond", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorHeatSwitch", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchCore", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchSpread", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorHeatSwitchDiamond", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorCoolantSimple", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorCoolantTriple", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorCoolantSix", 1L, 1), true, false,
        true);
    // --- Recrafting Iron Scaffold
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockIronScaffold", 1L), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemTreetapElectric",1,GT_Values.W), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemToolHoe",1,GT_Values.W), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2","itemRecipePart",1,3), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockChargepad", 1L), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockChargepad", 1L, 1), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockChargepad", 1L, 2), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockChargepad", 1L, 3), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemMOX", 1L), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "itemUran", 1L), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorUraniumSimple", 1L), true, false,
            true);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "reactorMOXSimple", 1L), true, false,
            true);


    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advJetpack", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advLappack", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "relocator", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "vajra", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "graviTool", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advChainsaw", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advDDrill", GT_Values.W));
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 4), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 5), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 7), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 6), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1L, 3), true,
        false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 1), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 2), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 4), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 5), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 3), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 4), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 5), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 6), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 3), true,
        false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarry", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "endMarker", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 5), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 9), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 4), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 1),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 2),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 3),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 4),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 5),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 6),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 7),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 11),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1L, GT_Values.W),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1L, GT_Values.W),
        true, false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 15),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 1),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 2),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 3),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 4),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 5),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 11),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 12),
        true, false, false);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 1L, 0), true,
        false, false);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicLogistics", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidBasic", 1L, 0),
        true, false, false);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("LogisticsPipes", "item.PipeBlockRequestTable", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.remoteOrdererItem", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.itemModule", 1L, GT_Values.W),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.itemUpgrade", 1L, GT_Values.W),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.pipeController", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRequestLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsProviderLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSatelliteLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSupplierLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk1", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk2", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk3", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk4", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeLogisticsChassiMk5", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogisticsMk2", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRequestLogisticsMk2", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsRemoteOrdererLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsProviderLogisticsMk2", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsApiaristAnalyser", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsApiaristSink", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsInvSysConnector", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSystemEntranceLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsSystemDestinationLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsCraftingLogisticsMk3", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsFirewall", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsFluidSupplier", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidBasic", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidInsertion", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidProvider", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidRequestLogistics", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidExtractor", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidSatellite", 1L, 0),
            true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidSupplierMk2", 1L, 0),
            true, false, false);


    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "crafting_table", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "chest", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "trapped_chest", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "dispenser", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "dropper", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "sticky_piston", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "noteblock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "jukebox", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "trapdoor", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "wooden_pressure_plate", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "glass_pane", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "torch", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "fence", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "fence_gate", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "tripwire_hook", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "anvil", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "wooden_door", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "iron_door", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_sword", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_shovel", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_pickaxe", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_axe", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_hoe", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_helmet", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_chestplate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_leggings", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "diamond_boots", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "saddle", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "item_frame", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "bed", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "fishing_rod", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "ladder", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "stone_pressure_plate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "wooden_pressure_plate", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "enchanting_table", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "brewing_stand", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "lit_pumpkin", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "redstone_lamp", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "daylight_detector", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "comparator", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "repeater", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "ender_eye", 1L, 0), true, false,
        false);
    removeRecipeByOutput(new ItemStack(Blocks.carpet, 1, GT_Values.W), true, false, false);
    removeRecipeByOutput(new ItemStack(Blocks.stained_glass_pane, 1, GT_Values.W), true, false,
        false);
    removeRecipeByOutput(new ItemStack(Items.enchanted_book, 1, 0), true, false,
            false);


    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "iron_trapdoor", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "sliding_trapdoor", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "player_sensor", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 1), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 2), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "vanishing_block", 1L, 3), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "block_mixer", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "door_factory", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "rustyHatch", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "garage_door", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.rustyHandle", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "rustyLadder", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.door_acacia", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.door_birch", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.door_dark_oak", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.door_jungle", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.door_spruce", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.wood_sliding_door", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.iron_sliding_door", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.jail_door", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.laboratory_door", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.factory_door", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.shoji_door", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_purple", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_yellow", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_magenta", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_pink", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_white", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_blue", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_gray", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_cyan", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_red", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_brown", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_lime", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_orange", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_silver", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_green", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_light_blue", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.curtain_black", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.saloon", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "item.forcefieldItem", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "acaciaFenceGate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "birchFenceGate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "darkOakFenceGate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "jungleFenceGate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "spruceFenceGate", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "trapdoor_acacia", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "trapdoor_birch", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "trapdoor_dark_oak", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "trapdoor_jungle", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("malisisdoors", "trapdoor_spruce", 1L, 0), true,
        false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersCollapsibleBlock", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersChisel", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersHammer", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSafe", 1, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersDaylightSensor", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersBed", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersTorch", 1, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGarageDoor", 1, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLadder", 1, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBarrier", 1, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersButton", 1, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersFlowerPot", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersGate", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersHatch", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersLever", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSlope", 1, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersStairs", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersTile", 1, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("CarpentersBlocks", "itemCarpentersDoor", 1, 0),
        true, false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "enderThermicPump", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "chestMini", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "chestFull", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "trashcan", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "watering_can", 1, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "timer", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "angelBlock", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "golden_bag", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 5), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 7), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 8), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 6), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 9), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 10),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock2", 1, 11),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 9), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 7), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 6), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 10),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 13),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "decorativeBlock1", 1, 8),
            true, false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("ArchitectureCraft", "sawbench", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ArchitectureCraft", "sawblade", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ArchitectureCraft", "largePulley", 1, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ArchitectureCraft", "hammer", 1, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ArchitectureCraft", "chisel", 1, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "Printing Press", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioChase", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "Typesetting Machine", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "tile.BiblioframeGtdChest", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioPaintPress", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioLantern", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioIronLantern", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioLamp", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioIronLamp", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "Armor Stand", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioBell", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioStuffs", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioStuffs", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.FramingSaw", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.FramingBoard", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioMapTool", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("BiblioCraft", "item.BiblioWayPointCompass", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioGlasses", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioGlasses", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.PlumbLine", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.HandDrill", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioDrill", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.tape", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.tapeMeasure", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.BiblioClipboard", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.SlottedBook", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "item.AtlasBook", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("BiblioCraft", "BiblioClock", 1L, GT_Values.W),
        true, false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "Wireless", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "WirelessKit", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "Visualiser", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "Encoder", 1L, 0), true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "Grower", 1L, 0), true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2stuff", "Inscriber", 1L, 0), true, false,
        true);

    removeRecipeByOutput(
        GT_ModHandler.getModItem("compactkineticgenerators", "IridiumRotor", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("compactkineticgenerators", "IridiumBlade", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "cable", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "wrench", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "case1", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "case2", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "case3", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "rack", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "adapter", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "assembler", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "disassembler", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "capacitor", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "charger", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "diskDrive", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "geolyzer", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "hologram1", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "hologram2", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "motionSensor", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "netSplitter", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "printer", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "powerConverter", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "powerDistributor", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "raid", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "redstone", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "relay", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "transposer", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "waypoint", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "keyboard", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "chameliumBlock", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "screen1", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "screen2", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "screen3", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "eeprom", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "hoverBoots", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 20), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 19), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 82), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 86), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 83), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 87), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 33), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 4), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 5), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 6), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 7), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 109), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 94), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 48), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 51), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 98), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 107), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 45), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 46), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 40), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 74), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 92), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 41), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 108), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 96), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 27), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 28), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 29), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 42), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 43), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 8), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 9), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 10), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 44), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 101), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 102), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 50), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 2), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 3), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 38), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 39), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 24), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 25), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 26), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 11), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 113), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 89), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 66), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 12), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 104), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 105), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 106), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 49), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 99), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 100), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 63), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 64), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 65), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 62), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 76), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 77), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 57), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 58), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 59), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 54), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 55), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 56), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 70), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 71), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 72), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 78), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 79), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 80), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 52), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 14), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 15), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 85), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 112), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 36), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 75), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 35), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 34), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 67), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenComputers", "item", 1L, 110), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("openglasses", "openglassesterminal", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("openglasses", "openglasses", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "keyboard", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "glassesbridge", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "glasses", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "sensor", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "pim", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("OpenPeripheral", "selector", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "magreader", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "rfidreader", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "rfidwriter", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "alarm", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "entitydetector", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "doorcontroller", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "opensecurity.DataBlock", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.SwitchableHub", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "opensecurity.BlockKVM", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "energyTurretBlock", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "keypadLock", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "biometricScanner", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "opensecurity.magCard", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("opensecurity", "opensecurity.rfidCard", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.rfidReaderCard", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.secureNetworkCard", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.securityDoor", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.securityDoorPrivate", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.damageUpgrade", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.cooldownUpgrade", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.energyUpgrade", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("opensecurity", "opensecurity.movementUpgrade", 1L, 0), true,
        false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.cipher", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.speechBox", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("computronics", "computronics.ironNoteBlock", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.radar", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.gt_parts", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 2),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 3),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 4),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 5),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 6),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 7),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 8),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 9),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 10),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 11),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 12),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 13),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("computronics", "computronics.ocParts", 1L, 14),
        true, false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "apiaristBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "lepidopteristBag", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "minerBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "diggerBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "foresterBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "hunterBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "adventurerBag", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("Forestry", "builderBag", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "sgChevronUpgrade", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "stargateRing", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "stargateBase", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "stargateController", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "ic2PowerUnit", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "sgIrisUpgrade", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "sgIrisBlade", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "ocInterface", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("SGCraft", "ic2Capacitor", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.arclamp", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenPipe", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenMask", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.steelPole", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 7), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.glowstoneTorch", 1L),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.oilCanisterPartial", 1L, 1001), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.hydrogenPipe", 1L),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 2),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 3),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankLightFull", 1L, 1800), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankMedFull", 1L, 3600), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenTankHeavyFull", 1L, 5400), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.methaneCanisterPartial", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.landingPad", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.landingPad", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.sensorGlasses", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCollector", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCompressor", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenCompressor", 1L, 4), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine2", 1L, 8), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.distributor", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.sealer", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.oxygenDetector", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.refinery", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.fuelLoader", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.cargo", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.cargo", 1L, 4), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "tile.rocketWorkbench", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 3),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 4),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 9),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 10),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 11),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.gcBlockCore", 1L, 12),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.airLockFrame", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.airLockFrame", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.solar", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.solar", 1L, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 12),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine", 1L, 12), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine2", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machine2", 1L, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machineTiered", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machineTiered", 1L, 4),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machineTiered", 1L, 8),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.machineTiered", 1L, 12),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.spinThruster", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.viewScreen", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.telemetry", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenGear", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.airFan", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.airVent", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.oxygenConcentrator", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.engine", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.noseCone", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.oilExtractor", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.buggymat", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.standardWrench", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 15),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 16),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 17),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 18),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 19),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_pickaxe", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_axe", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_hoe", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_shovel", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_sword", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_helmet", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_chestplate", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_leggings", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.titanium_boots", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 8), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachineT2", 1L, 4),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachineT2", 1L, 8),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.walkway", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.walkwayWire", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "tile.walkwayOxygenPipe", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.atmosphericValve", 1L, 0), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 2), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 1), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.heavyNoseCone", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 8), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.beamReflector", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.beamReceiver", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.telepadShort", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L, 4),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "tile.marsMachine", 1L, 8),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.grapple", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.carbonFragments", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshPick", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshAxe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshHoe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshSpade", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshSword", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.rocketFins", 1L, 0),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1L, 2), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.canvas", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "item.parachute", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshHelmet", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshChestplate", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshLeggings", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.deshBoots", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftMars", "item.orionDrive", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.enclosed", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.enclosed", 1L, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.enclosed", 1L, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.enclosed", 1L, 5), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.enclosed", 1L, 6), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "tile.aluminumWire", 1L, 1),
        true, false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalacticraftCore", "item.battery", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 4), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalacticraftCore", "slabGCHalf", 1L, 5), true,
        false, true);

    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalClothT2", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 1),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 2),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.ThermalPaddingT2", 1L, 3),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "futureglass", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_boots", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_plate", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_helmet", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_leg", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.plasmasword", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.plasmapickaxe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.plasmaaxe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.plasmahoe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.plasmashovel", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "solarwindpanel", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.QuantBow", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.JetPack", 1L, GT_Values.W),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "fuelgenerator", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "assemblymachine", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "convertersurface", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "dungeonglowstone", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "dungeonglowstone", 1L, 1), true, false,
        true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.RocketParts", 1L, 0), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.RocketParts", 1L, 1), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.RocketParts", 1L, 2), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.RocketParts", 1L, 3), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 0), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 1), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 2), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 3), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 4), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 5), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 6), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 7), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 8), true, false, true);
    //removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "metalsblock", 1L, 9), true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "machineframes", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "solarPanel", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "storagemoduleT3", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "oxstoragemoduleT2", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "machineframes", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "advFuelLoader", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "advlandingpad", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "fluidtank", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "gravitationmoduleon", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "hydroponicbase", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "hydroponicfarm", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "liquidextractor", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "liquidseparator", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "modificationtable", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "oxygenfiller", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "portablenuclearreactor", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "radiationstabiliser", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "recycler", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "rocketAssembly", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "windTurbine", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 3), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 14), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.BasicItems", 1L, 15), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_sword", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_pickaxe", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_axe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_shovel", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.cobalt_hoe", 1L, 0), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.AdvancedBattery", 1L, GT_Values.W), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.ModernBattery", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.ExtraBattery", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.UltraBattery", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("GalaxySpace", "item.Modules", 1L, 2), true,
        false, true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t4", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t5", 1L, GT_Values.W), true, false,
        true);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("GalaxySpace", "item.oxygentank_t6", 1L, GT_Values.W), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("ae2wct", "wirelessCraftingTerminal", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("ae2wct", "magnetCard", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "obsidianStick", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "stoneBowl", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "retherPearl", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "wirelessTransceiver", 1L, 0),
        true, false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "blazeTransceiver", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "recieverDish", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Core", "blazeRecieverDish", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 1), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Logic", "wirelessLogic", 1L, 2), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "triangulator", 1L, 0), true,
        false, true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "remote", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "sniffer", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "map", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "tracker", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "rep", 1L, 0), true, false,
        true);
    removeRecipeByOutput(GT_ModHandler.getModItem("WR-CBE|Addons", "psniffer", 1L, 0), true, false,
        true);

    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "stoneTile", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "cleanDirt", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "booster", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "lampf", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "lampt", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "lampb", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "auroraBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "minicharcoal", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "minicoal", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "ofanix", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "diamondZane", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "splatAxe", 1L, 0), true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "terrainEater", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.korpBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.tankBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.solsBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.ztylBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.vectBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.jeltBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.reedBlock", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Ztones", "tile.venaBlock", 1L, 0), true, false,
        false);

    removeRecipeByOutput(GT_ModHandler.getModItem("ChickenChunks", "chickenChunkLoader", 1L, 0),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("ChickenChunks", "chickenChunkLoader", 1L, 1),
        true, false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("Backpack", "workbenchbackpack", 1L, GT_Values.W),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Backpack", "boundLeather", 1L), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("Backpack", "backpack", 1L, GT_Values.W), true,
        false, false);

    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "IndustrialApiary", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "MutagenProducer", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Mutatron", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Imprinter", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Sampler", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "MutatronAdv", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Liquifier", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Extractor", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Transposer", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Replicator", 1L, 0), true, false,
        false);
    /*removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "GeneSampleBlank", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "GeneTemplate", 1L, 0), true, false,
        false);
        */
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "Labware", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "BeeReceptacle", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "PowerModule", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "MutagenTank", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "GeneticsProcessor", 1L, 0), true,
        false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "EnvProcessor", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "UpgradeFrame", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "ClimateModule", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "ApiaryUpgrade", 1L, GT_Values.W),
        true, false, false);
    removeRecipeByOutput(
        GT_ModHandler.getModItem("gendustry", "IndustrialGrafter", 1L, GT_Values.W), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "IndustrialScoop", 1L, GT_Values.W),
        true, false, false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "EjectCover", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "ImportCover", 1L, 0), true, false,
        false);
    removeRecipeByOutput(GT_ModHandler.getModItem("gendustry", "ErrorSensorCover", 1L, 0), true,
        false, false);

    removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CokeCoal, 1), true,
        false, false);
    removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Desh, 1), true,
        false, false);
    removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.MeteoricIron, 1),
        true, false, false);

  }
}
