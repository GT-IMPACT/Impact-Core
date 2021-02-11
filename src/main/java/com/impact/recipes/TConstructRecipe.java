package com.impact.recipes;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;
import static tconstruct.armor.TinkerArmor.*;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.crafting.DryingRackRecipes;
import tconstruct.weaponry.TinkerWeaponry;

public class TConstructRecipe implements Runnable {

  private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED
      | GT_ModHandler.RecipeBits.NOT_REMOVABLE/* | GT_ModHandler.RecipeBits.REVERSIBLE*/;

  public void delRecipe() {
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelGoggles", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelWings", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelVest", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelBoots", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelGlove", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "travelBelt", 1L, 0));
    removeRecipeByOutput(GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 1));

  }

  public void handRecipe() {
    ItemStack leather = GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0);
    GT_ModHandler
            .addCraftingRecipe(travelGoggles.getDefaultItem(), tBitMask,
            new Object[]{"LSL", "RBR", "DHD", 'L', leather, 'S', OrePrefixes.screw.get(Materials.Electrum), 'R',
                    OrePrefixes.ring.get(Materials.Electrum), 'B', OrePrefixes.bolt.get(Materials.Electrum), 'D',
                    OrePrefixes.lens.get(Materials.Diamond), 'H', new ItemStack(Items.diamond_helmet)});
    GT_ModHandler
            .addCraftingRecipe(travelWings.getDefaultItem(), tBitMask,
            new Object[]{"SLS", "FPF", "FHF", 'L', leather, 'S', OrePrefixes.screw.get(Materials.Electrum), 'P',
                    new ItemStack(Blocks.piston), 'F', new ItemStack(TinkerWeaponry.fletching, 1, 0),
                    'H', new ItemStack(Items.diamond_leggings)});
    GT_ModHandler
            .addCraftingRecipe(travelVest.getDefaultItem(), tBitMask,
            new Object[]{"LEL", "PHP", "LPL", 'L', leather, 'E', OrePrefixes.plate.get(Materials.Electrum), 'P',
                OrePrefixes.plate.get(Materials.Obsidian), 'H', new ItemStack(Items.diamond_chestplate)});
    GT_ModHandler
            .addCraftingRecipe(travelBoots.getDefaultItem(), tBitMask,
            new Object[]{"LSL", "LHL", "PdP", 'L', leather, 'S', OrePrefixes.screw.get(Materials.Electrum), 'P',
                    new ItemStack(Blocks.piston), 'H', new ItemStack(Items.diamond_boots)});
    GT_ModHandler
            .addCraftingRecipe(travelGlove.getDefaultItem(), tBitMask,
            new Object[]{"SLS", "LLL", "dLD", 'L', leather, 'S', OrePrefixes.screw.get(Materials.Electrum), 'D',
                OrePrefixes.plate.get(Materials.Diamond)});
    GT_ModHandler
            .addCraftingRecipe(travelBelt.getDefaultItem(), tBitMask,
            new Object[]{"S S", "LDL", "LdL", 'L', leather, 'S', OrePrefixes.screw.get(Materials.Electrum), 'D',
                OrePrefixes.plate.get(Materials.Diamond)});
    GT_ModHandler
        .addCraftingRecipe(GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 1), tBitMask,
            new Object[]{"f ", " S", 'S', new ItemStack(Blocks.stone)});

  }

  public void alloySmelterRecipe() {
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 2), 200, 16);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 2L, 0),
        ItemList.Shape_Mold_Ball.get(0), GT_ModHandler.getModItem("TConstruct", "materials", 1L, 1),
        400, 16);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 2L, 2),
        ItemList.Shape_Mold_Ball.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 17), 400, 16);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 32),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15), 200, 8);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 27),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 18), 200, 4);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 24),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 14), 200, 2);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 6),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 37), 200, 16);
    GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1),
        ItemList.Shape_Mold_Ingot.get(0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 2), 130, 3);
    GT_Values.RA
        .addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 3L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1L),
            GT_ModHandler.getModItem("TConstruct", "materials", 4L, 14), 200, 16);
    GT_Values.RA
        .addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 3L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L),
            GT_ModHandler.getModItem("TConstruct", "materials", 4L, 14), 200, 16);
    GT_Values.RA
        .addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Aluminium, 3L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1L),
            GT_ModHandler.getModItem("TConstruct", "materials", 4L, 14), 200, 16);
    GT_Values.RA
        .addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Aluminium, 3L),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L),
            GT_ModHandler.getModItem("TConstruct", "materials", 4L, 14), 200, 16);
  }

  public void assemblerRecipe() {
    // --- Green SDX TNT
    GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TConstruct", "slime.gel", 1L, 1),
        GT_ModHandler.getModItem("IC2", "blockITNT", 1L),
        GT_ModHandler.getModItem("TConstruct", "explosive.slime", 1L), 600, 30);
    // --- Blue SDX TNT
    GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TConstruct", "slime.gel", 1L, 1),
        GT_ModHandler.getModItem("TConstruct", "explosive.slime", 1L),
        GT_ModHandler.getModItem("TConstruct", "explosive.slime", 1L, 2), 600, 64);
    // --- Empty Canister
    GT_Values.RA.addAssemblerRecipe(
        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Aluminium, 1),
        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 4),
        GT_ModHandler.getModItem("TConstruct", "heartCanister", 1L), 2400, 480);
  }

  public void chemicalBathRecipe() {
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < Dyes.VALUES[i].getSizeOfFluidList(); j++) {
        GT_Values.RA
            .addChemicalBathRecipe(GT_ModHandler.getModItem("TConstruct", "GlassBlock", 1L, 0),
                Dyes.VALUES[i].getFluidDye(j, 36L),
                GT_ModHandler.getModItem("TConstruct", "GlassBlock.StainedClear", 1L, 15 - i),
                GT_Values.NI, GT_Values.NI, null, 20, 2);
      }
      GT_Values.RA.addChemicalBathRecipe(
          GT_ModHandler.getModItem("TConstruct", "GlassBlock.StainedClear", 1L, i),
          Materials.Chlorine.getGas(50L),
          GT_ModHandler.getModItem("TConstruct", "GlassBlock", 1L, 0), GT_Values.NI, GT_Values.NI,
          null, 20, 2);
    }
  }

  public void chemicalReactorRecipe() {
    GT_Values.RA
        .addChemicalRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Diamond, 8L),
            new ItemStack(Items.golden_apple, 1, 1), null, null,
            GT_ModHandler.getModItem("TConstruct", "diamondApple", 1L, 0), 3600);
  }

  public void compressorRecipe() {
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 14),
        GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 7), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 5),
        GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 2), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 15),
        GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 8), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 9L, 4),
        GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 1), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 4L, 2),
        GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 4L, 0),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 1), 300, 2);
    GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 4L, 2),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 17), 300, 2);
    GT_Values.RA.addCompressorRecipe(new ItemStack(Items.paper, 64),
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 0), 300, 2);
  }

  public void cutterRecipe() {
    for (int i = 0; i < 16; i++) {
      GT_Values.RA
          .addCutterRecipe(GT_ModHandler.getModItem("TConstruct", "GlassBlock.StainedClear", 3L, i),
              GT_ModHandler.getModItem("TConstruct", "GlassPaneClearStained", 8L, i), GT_Values.NI,
              50, 8);
    }
  }

  public void EBFRecipe() {
    GT_Values.RA.addBlastRecipe(new ItemStack(Blocks.glass, 1), GT_Utility.getIntegratedCircuit(1),
        GT_Values.NF, GT_Values.NF, GT_ModHandler.getModItem("TConstruct", "GlassBlock", 1L),
        GT_Values.NI, 100, 120, 1000);
    GT_Values.RA
        .addBlastRecipe(new ItemStack(Blocks.glass_pane, 1), GT_Utility.getIntegratedCircuit(1),
            GT_Values.NF, GT_Values.NF, GT_ModHandler.getModItem("TConstruct", "GlassPane", 1L),
            GT_Values.NI, 100, 120, 1000);
    GT_Values.RA.addBlastRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 41),
        GT_Utility.getIntegratedCircuit(1), GT_Values.NF, GT_Values.NF,
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5), GT_Values.NI, 1800, 120, 1900);
    GT_Values.RA.addBlastRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 38),
        GT_Utility.getIntegratedCircuit(1), GT_Values.NF, GT_Values.NF,
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4), GT_Values.NI, 1200, 120, 1200);
  }

  public void extractorRecipe() {
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2),
        GT_ModHandler.getModItem("TConstruct", "materials", 4L, 2), 300, 2);
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "slime.sapling", 1L),
        GT_ModHandler.getModItem("TConstruct", "strangeFood", 1L), 300, 2);
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "slime.gel", 1L),
        GT_ModHandler.getModItem("TConstruct", "strangeFood", 4L), 300, 2);
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "slime.gel", 1L, 1),
        GT_ModHandler.getModItem("TConstruct", "slime_ball", 4L), 300, 2);
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "slime.leaves", 1L, 16),
        Materials.RawRubber.getDust(1), 300, 2);
  }

  public void extruderRecipe() {
    //Tool Rods
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 7), 120, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 1L, 17), 666, 30);

    //Pickaxe Heads
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "pickaxeHead", 1L, 17), 1333, 30);

    //Shovel Heads
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "shovelHead", 1L, 17), 1333, 30);

    //Axe Heads
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 4),
        GT_ModHandler.getModItem("TConstruct", "hatchetHead", 1L, 17), 1333, 30);

    //Sword Blade
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 5),
        GT_ModHandler.getModItem("TConstruct", "swordBlade", 1L, 17), 1333, 30);

    //Wide Guards
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 6),
        GT_ModHandler.getModItem("TConstruct", "wideGuard", 1L, 17), 666, 30);

    //Hand Guards
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 7),
        GT_ModHandler.getModItem("TConstruct", "handGuard", 1L, 17), 666, 30);

    //Crossbars
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 8),
        GT_ModHandler.getModItem("TConstruct", "crossbar", 1L, 17), 666, 30);

    //Bindings
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 9),
        GT_ModHandler.getModItem("TConstruct", "binding", 1L, 17), 666, 30);

    //Fryingpan Heads (Time for Dream to duck!)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 10),
        GT_ModHandler.getModItem("TConstruct", "frypanHead", 1L, 17), 1333, 30);

    //Sign Heads (To write on a wall)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 6), 179, 30);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 11),
        GT_ModHandler.getModItem("TConstruct", "signHead", 1L, 17), 1333, 30);

    //Knife Blades (There are no winners in a knife fight)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 6), 90, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 12),
        GT_ModHandler.getModItem("TConstruct", "knifeBlade", 1L, 17), 666, 30);

    //Chisel Heads (Stonework galore)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 6), 90, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 13),
        GT_ModHandler.getModItem("TConstruct", "chiselHead", 1L, 17), 666, 30);

    //Tough Tool Rods (The Tougher the Better)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 2), 1503, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 6), 537, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 3, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 7), 735, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 10), 4800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 11), 3639, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 12), 7200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 13), 1080, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 14), 2280, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 15), 3303, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 16), 2400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 14),
        GT_ModHandler.getModItem("TConstruct", "toughRod", 1L, 17), 3999, 30);

    //Tough Bindings (To keep the toughness together)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 2), 1503, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 6), 537, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 3, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 7), 735, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 10), 4800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 11), 3639, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 12), 7200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 13), 1080, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 14), 2280, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 15), 3303, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 16), 2400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 15),
        GT_ModHandler.getModItem("TConstruct", "toughBinding", 1L, 17), 3999, 30);

    //Large Plates (Different from Small Plates)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 16),
        GT_ModHandler.getModItem("TConstruct", "heavyPlate", 1L, 17), 10664, 30);

    //Broad Axe Heads (Cutting down a tree with every strike)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 17),
        GT_ModHandler.getModItem("TConstruct", "broadAxeHead", 1L, 17), 10664, 30);

    //Scythe Heads (Cutting down a a farm with every strike, or Deaths favorite weapon)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 18),
        GT_ModHandler.getModItem("TConstruct", "scytheBlade", 1L, 17), 10664, 30);

    //Excavator Heads (Getting Dirty digging a hole)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 19),
        GT_ModHandler.getModItem("TConstruct", "excavatorHead", 1L, 17), 10664, 30);

    //Large Sword Blades (Who has the largest sword?)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 20),
        GT_ModHandler.getModItem("TConstruct", "largeSwordBlade", 1L, 17), 10664, 30);

    //Hammer Heads (It's Clobbering Time!)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 2), 4008, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 6), 1432, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 8, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 7), 1960, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 10), 12800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 11), 9704, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 12), 19200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 13), 2880, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 14), 7680, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 15), 8808, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(8),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 16), 6400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 8L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 21),
        GT_ModHandler.getModItem("TConstruct", "hammerHead", 1L, 17), 10664, 30);

    //Full Guards (To guard your grip)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 2), 1503, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 6), 537, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 3, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 7), 735, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 10), 4800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 11), 3639, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 12), 7200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 13), 1080, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 14), 2280, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 15), 3303, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(3),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 16), 2400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 3L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 22),
        GT_ModHandler.getModItem("TConstruct", "fullGuard", 1L, 17), 3999, 30);

    //Arrowheads (Hey, flying metal)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 2), 501, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 6), 179, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 7), 245, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 10), 1600, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 11), 1213, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 12), 2400, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 13), 360, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 14), 760, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 15), 1101, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 16), 800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "metalPattern", 0L, 25),
        GT_ModHandler.getModItem("TConstruct", "arrowhead", 1L, 17), 1333, 30);

    //Shurikens (Hey, Oriental flying metal)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 2), 250, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 6), 90, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 1, 0),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 7), 122, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 10), 800, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 11), 606, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 12), 1200, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 13), 180, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 14), 380, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 15),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 15), 550, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(1),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 16), 400, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 34),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 0),
        GT_ModHandler.getModItem("TConstruct", "ShurikenPart", 1L, 17), 666, 30);

    //Crossbow Limbs (The Dragon's worst enemy)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 2), 2004, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 6), 716, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 4, 0),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 7), 980, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 10), 6400, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 4L, 4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 11), 4852, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 4L, 5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 12), 9600, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 13), 1440, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 14), 3040, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 4L, 15),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 15), 4404, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 16), 3200, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 4L, 34),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 1),
        GT_ModHandler.getModItem("TConstruct", "CrossbowLimbPart", 1L, 17), 5332, 30);

    //Crossbow Body (The Dragon's worst enemy, part 2)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 2), 2505, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 6), 895, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 5, 0),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 7), 1225, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 10), 8000, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 5L, 4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 11), 6065, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 5L, 5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 12), 12000, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 13), 1800, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 14), 3800, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 5L, 15),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 15), 5505, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 16), 4000, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 5L, 34),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 2),
        GT_ModHandler.getModItem("TConstruct", "CrossbowBodyPart", 1L, 17), 6665, 30);

    //Bow Limbs (A skeletons favorite weapon, in pieces.)
    GT_Values.RA.addExtruderRecipe(Materials.Iron.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 2), 752, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Obsidian.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 6), 269, 120);
    GT_Values.RA.addExtruderRecipe(new ItemStack(Items.netherbrick, 2, 0),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 7), 368, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Cobalt.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 10), 2400, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 2L, 4),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 11), 1820, 120);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 2L, 5),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 12), 3600, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Copper.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 13), 540, 30);
    GT_Values.RA.addExtruderRecipe(Materials.Bronze.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 14), 1140, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 2L, 15),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 15), 1652, 120);
    GT_Values.RA.addExtruderRecipe(Materials.Steel.getIngots(2),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 16), 1200, 30);
    GT_Values.RA.addExtruderRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 2L, 34),
        GT_ModHandler.getModItem("TConstruct", "Cast", 0L, 3),
        GT_ModHandler.getModItem("TConstruct", "BowLimbPart", 1L, 17), 2000, 30);
  }

  public void forgeHammerRecipe() {
    GT_Values.RA.addForgeHammerRecipe(GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2),
        GT_ModHandler.getModItem("TConstruct", "materials", 3L, 2), 16, 20);
  }

  public void latheRecipe() {
    GT_Values.RA.addLatheRecipe(new ItemStack(Blocks.stone),
        GT_ModHandler.getModItem("TConstruct", "toolRod", 2L, 1), null, 100, 16);
  }

  public void mixerRecipe() {
    // --- Grout
    GT_Values.RA
        .addMixerRecipe(new ItemStack(Blocks.sand, 1, GT_Values.W), new ItemStack(Blocks.gravel, 3),
            new ItemStack(Items.clay_ball, 2), GT_Values.NI, GT_Values.NI,
            GT_Utility.getIntegratedCircuit(1), Materials.Water.getFluid(2000L), GT_Values.NF,
            GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 8L, 1), 120, 16);
    // --- Slime Soil
    GT_Values.RA
        .addMixerRecipe(new ItemStack(Items.nether_wart, 1), new ItemStack(Blocks.soul_sand, 1),
            new ItemStack(Blocks.gravel, 1), new ItemStack(Blocks.sand, 1, GT_Values.W),
            GT_Values.NI, GT_Utility.getIntegratedCircuit(1), Materials.Water.getFluid(2000L),
            GT_Values.NF, GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 2L, 6), 200, 16);
    // --- Manyullyn Dust
    GT_Values.RA.addMixerRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 38),
        GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 1L), GT_Values.NI,
        GT_Values.NI, GT_Values.NI, GT_Utility.getIntegratedCircuit(1), GT_Values.NF, GT_Values.NF,
        GT_ModHandler.getModItem("TConstruct", "materials", 1L, 41), 200, 16);
  }

  public void pulveriserRecipe() {
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 4),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 1L, 38)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 1),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 9L, 38)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "SearedBrick", 1L, 2),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 2L, 38),
            GT_ModHandler.getModItem("TConstruct", "materials", 1L, 38)}, new int[]{10000, 1000},
        400, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 14),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 1L, 42)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 7),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 9L, 42)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 5),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 1L, 41)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 2),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 9L, 41)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "MetalBlock", 1L, 0),
        new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 9L)},
        new int[]{10000}, 300, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "SearedBrick", 1L, 1),
        new ItemStack[]{GT_ModHandler.getModItem("TConstruct", "materials", 2L, 38),
            GT_ModHandler.getModItem("TConstruct", "materials", 1L, 38)}, new int[]{10000, 1000},
        400, 2);
    GT_Values.RA.addPulveriserRecipe(GT_ModHandler.getModItem("TConstruct", "materials", 1L, 12),
        new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L)},
        new int[]{10000}, 300, 2);
  }

  public void dryingRackRecipe() {
    if (Loader.isModLoaded("Backpack")) {
      DryingRackRecipes
          .addDryingRecipe(GT_ModHandler.getModItem("Backpack", "boundLeather", 1L, 0), 4000,
              GT_ModHandler.getModItem("Backpack", "tannedLeather", 1L, 0));
    }
  }

  @Override
  public void run() {
    delRecipe();
    handRecipe();
    alloySmelterRecipe();
    assemblerRecipe();
    chemicalBathRecipe();
    chemicalReactorRecipe();
    compressorRecipe();
    cutterRecipe();
    EBFRecipe();
    extractorRecipe();
    extruderRecipe();
    forgeHammerRecipe();
    latheRecipe();
    mixerRecipe();
    pulveriserRecipe();
    dryingRackRecipe();
  }
}
