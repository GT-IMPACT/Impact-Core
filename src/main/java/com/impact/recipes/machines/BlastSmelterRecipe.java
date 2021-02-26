package com.impact.recipes.machines;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class BlastSmelterRecipe implements Runnable {

  @Override
  public void run() {

    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 7),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 32)}, null, null,
        Materials.HastelloyC276.getMoltenHot(144L * 50), 500 * 20, 7680, 3666);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Niobium, 23),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tantalum, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 2)}, null, null,
        Materials.GumMetal.getMoltenHot(144L * 27), 270 * 20, 7680, 3777);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Niobium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tantalum, 1)}, null, null,
        Materials.Titaniolum.getMoltenHot(144L * 3), 30 * 20, 7680, 3888);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Manganese, 2)}, null, null,
        Materials.Mangalloy.getMoltenHot(144L * 7), 70 * 20, 7680, 3940);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 1)}, null, null,
        Materials.Zamak.getMoltenHot(144L * 8), 80 * 20, 7680, 3999);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 2)}, null, null,
        Materials.Kovar.getMoltenHot(144L * 10), 80 * 20, 7680, 4002);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Manganese, 1)}, null, null,
        Materials.Duraluminium.getMoltenHot(144L * 8), 64 * 20, 7680, 4111);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Niobium, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nichrome, 1)}, null, null,
        Materials.Inconel690.getMoltenHot(144L * 6), 48 * 20, 7680, 4222);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1)}, null, null,
        Materials.Nitinol.getMoltenHot(144L * 2), 16 * 20, 7680, 4333);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Niobium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nichrome, 1)}, null, null,
        Materials.Inconel792.getMoltenHot(144L * 6), 48 * 20, 7680, 4444);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Vanadium, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 6),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 4)}, null, null,
        Materials.TiBetaC.getMoltenHot(144L * 23), 161 * 20, 7680, 4555);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 9),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 9),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Kalium, 9),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 9),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 9)}, null, null,
        Materials.Grisium.getMoltenHot(144L * 45), 315 * 20, 7680, 4601);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 16),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 2)}, null, null,
        Materials.MaragingSteel250.getMoltenHot(144L * 24), 168 * 20, 7680, 4666);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphor, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 1)}, null, null,
        Materials.Talonite.getMoltenHot(144L * 10), 70 * 20, 7680, 4770);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 2)}, null, null,
        Materials.Nitinol60.getMoltenHot(144L * 5), 35 * 20, 7680, 4701);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 3L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 16),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 2)}, null, null,
        Materials.MaragingSteel300.getMoltenHot(144L * 24), 192 * 20, 7680, 4300);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 7),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphor, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 1)}, null, null,
        Materials.Stellite.getMoltenHot(144L * 13), 104 * 20, 7680, 4121);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 26),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 6),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 20),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 40)}, null, null,
        Materials.Zeron100.getMoltenHot(144L * 100), 600 * 20, 7680, 5100);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zeron100, 16),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gadolinium, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 12),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Osmiridium, 6)}, null, null,
        Materials.CinobiteA243.getMoltenHot(144L * 44), 264 * 20, 122880, 7350);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 3L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Yttrium, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 16),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 60)}, null, null,
        Materials.HastelloyN.getMoltenHot(144L * 100), 800 * 20, 7680, 4350);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HastelloyN, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Samarium, 2),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 6),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 2)}, null, null,
        Materials.Lafium.getMoltenHot(144L * 30), 180 * 20, 30720, 6105);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stellite, 15),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnergeticAlloy, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gallium, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Americium, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Palladium, 5)}, null, null,
        Materials.Quantum.getMoltenHot(144L * 40), 240 * 20, 2000000, 12000);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 2L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stellite, 15),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Inconel792, 16),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EglinSteel, 10),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NaquadahEnriched, 8),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cerium, 6),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Antimony, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 4),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.TungstenSteel, 8)}, null, null,
        Materials.Pikyonium64B.getMoltenHot(144L * 71), 336 * 20, 500000, 9900);
    GT_Values.RA.addBlastSmelterRecipe(
        new ItemStack[]{ItemList.Circuit_Integrated.getWithDamage(0L, 1L),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 3),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Vanadium, 5),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titan, 40),
            GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 6)}, null, null,
        Materials.BT6.getMoltenHot(144L * 55), 300 * 20, 1920, 3200);
  }
}
