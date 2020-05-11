package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.loaders.oreprocessing.ProcessingCrafting;
import net.minecraft.item.ItemStack;

public class LaserEngraverRecipe implements gregtech.api.interfaces.IOreRecipeRegistrator  {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public LaserEngraverRecipe() {
        OrePrefixes.crafting.add(this);
    }
    /*@Override
    public void run() {

    }*/
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aOreDictName) {
            case "craftingLensRed":
                GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ItemList.RedstoneRedChipset.get(1L), 50, 120, false);

                break;
            case "craftingLensGreen":
                GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1L, new Object[0]), GT_Utility.copyAmount(0L, new Object[]{aStack}), CoreItems2.getRecipe(46, 1), 600, 480, true);
                GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_EnergyCrystal.getWildcard(1L, new Object[0]), GT_Utility.copyAmount(0L, new Object[]{aStack}), CoreItems2.getRecipe(100, 1), 300, 120, true);

                break;
        }
    }
}
