package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class AutoclaveRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addAutoclaveSpaceRecipe(CoreItems.getRecipe(36, 64), Materials.Silver.getPlasma(8000L), GT_ModHandler.getModItem("SGCraft", "sgCoreCrystal", 1L), 10000, 3600, 131000, true);
        GT_Values.RA.addAutoclaveSpaceRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DraconiumAwakened, 64L), Materials.Silver.getPlasma(8000L), GT_ModHandler.getModItem("SGCraft", "sgControllerCrystal", 1L), 10000, 3600, 131000, true);

        GT_Values.RA.addAutoclaveRecipe(CoreItems.getRecipe(38, 1), Materials.Water.getFluid(200L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 7000, 2000, 24);
        GT_Values.RA.addAutoclaveRecipe(CoreItems.getRecipe(38, 1), GT_ModHandler.getDistilledWater(1000), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 9000, 1500, 24);

    }
}
