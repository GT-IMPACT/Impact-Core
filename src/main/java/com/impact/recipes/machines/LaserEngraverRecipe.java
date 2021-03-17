package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import com.impact.mods.gregtech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class LaserEngraverRecipe implements gregtech.api.interfaces.IOreRecipeRegistrator {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  public LaserEngraverRecipe() {
    OrePrefixes.crafting.add(this);
  }

  public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName,
      String aModName, ItemStack aStack) {
    switch (aOreDictName) {
      case "craftingLensRed":
        GT_Values.RA.addLaserEngraverRecipe(
            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L),
            GT_Utility.copyAmount(0L, aStack), GT_ItemList.RedstoneRedChipset.get(1L), 50, 120,
            false);
        GT_Values.RA.addLaserEngraverRecipe(ItemList.EnergyCrystal.get(1L),
            GT_Utility.copyAmount(0L, aStack), GT_ItemList.EngravedEnergyChip.get(1L), 600, 4096,
            true);
        GT_Values.RA
            .addLaserEngraverRecipe(CoreItems2.getRecipe(154, 1), GT_Utility.copyAmount(0L, aStack),
                GT_ModHandler.getModItem("OpenComputers", "item", 16L, 24), 600, 120, false);

        break;
      case "craftingLensGreen":
        GT_Values.RA.addLaserEngraverRecipe(ItemList.LapotronCrystal.get(1L),
            GT_Utility.copyAmount(0L, aStack), CoreItems2.getRecipe(46, 1), 600, 480, true);
        GT_Values.RA.addLaserEngraverRecipe(ItemList.EnergyCrystal.get(1L),
            GT_Utility.copyAmount(0L, aStack), CoreItems2.getRecipe(100, 1), 300, 120, false);
        GT_Values.RA
            .addLaserEngraverRecipe(CoreItems2.getRecipe(155, 1), GT_Utility.copyAmount(0L, aStack),
                GT_ModHandler.getModItem("OpenComputers", "item", 9L, 25), 600, 256, false);
        break;
      case "craftingLensWhite":
        GT_Values.RA.addLaserEngraverRecipe(
            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Diamond, 4L),
            GT_Utility.copyAmount(0L, aStack), GT_ItemList.EngravedDiamondCrystalChip.get(1L), 400,
            1920, true);
        break;
      case "craftingLensYellow":
        GT_Values.RA.addLaserEngraverRecipe(
            GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.GarnetYellow, 1L),
            GT_Utility.copyAmount(0L, aStack), GT_ItemList.EngravedQuantumChip.get(1L), 1200, 30720,
            true);
        GT_Values.RA
            .addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Gold, 4L),
            GT_Utility.copyAmount(0L, aStack), GT_ItemList.EngravedGoldChip.get(1L), 300, 480,
            false);
        GT_Values.RA
            .addLaserEngraverRecipe(CoreItems2.getRecipe(156, 1), GT_Utility.copyAmount(0L, aStack),
            GT_ModHandler.getModItem("OpenComputers", "item", 6L, 26), 600, 480, true);
        break;
    }
  }
}
