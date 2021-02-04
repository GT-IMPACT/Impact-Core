package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class ArcFurnaceRecipe implements Runnable {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.spaceship", 1L, 0),
            new ItemStack[]{ItemList.Ingot_Heavy1.get(21), CoreItems2.getRecipe(97, 3),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 12)}, null, 1200, 384);
    GT_Values.RA.addArcFurnaceRecipe(
        GT_ModHandler.getModItem("GalacticraftMars", "item.spaceshipTier2", 1L, 0),
        new ItemStack[]{ItemList.Ingot_Heavy2.get(32), ItemList.Ingot_Heavy1.get(26),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 18)}, null, 1400, 1536);
    GT_Values.RA.addArcFurnaceRecipe(
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemTier3Rocket", 1L, 0),
        new ItemStack[]{ItemList.Ingot_Heavy3.get(25), ItemList.Ingot_Heavy2.get(36),
            GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titanium, 12)}, null, 1600, 6144);
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier4Rocket", 1L, 0),
            new ItemStack[]{CoreItems2.getRecipe(16, 64), CoreItems2.getRecipe(16, 3),
                ItemList.Ingot_Heavy3.get(28),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HastelloyN, 24)}, null, 1800,
            24576);
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier5Rocket", 1L, 0),
            new ItemStack[]{CoreItems2.getRecipe(17, 64), CoreItems2.getRecipe(17, 17),
                CoreItems2.getRecipe(16, 30),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Europium, 30)}, null, 2000,
            98304);
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier6Rocket", 1L, 0),
            new ItemStack[]{CoreItems2.getRecipe(18, 64), CoreItems2.getRecipe(18, 43),
                CoreItems2.getRecipe(17, 40),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Americium, 40)}, null, 2200,
            393216);
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier7Rocket", 1L, 0),
            new ItemStack[]{CoreItems2.getRecipe(19, 64), CoreItems2.getRecipe(19, 64),
                CoreItems2.getRecipe(18, 50),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Neutronium, 54)}, null, 2400,
            1572864);
    GT_Values.RA
        .addArcFurnaceRecipe(GT_ModHandler.getModItem("GalaxySpace", "item.Tier8Rocket", 1L, 0),
            new ItemStack[]{CoreItems2.getRecipe(20, 64), CoreItems2.getRecipe(20, 64),
                CoreItems2.getRecipe(19, 60),
                GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Phoenixite, 64)}, null, 2800,
            6291456);

    GT_Values.RA.addArcFurnaceRecipe(ItemList.Casing_BronzePlatedBricks.get(1),
        new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1)}, null, 160, 96);
  }
}
