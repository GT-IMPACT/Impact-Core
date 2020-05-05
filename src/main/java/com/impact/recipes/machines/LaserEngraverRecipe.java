package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class LaserEngraverRecipe implements Runnable {
    @Override
    public void run() {

/** ================================= start SPARTAK CORE =================================*/
        //Redstone Chips
        GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0, GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Ruby, 1)), GT_ItemList.RedstoneRedChipset.get(1L), 50, 120, false);
        /* ================================= end SPARTAK CORE =================================*/

    }
}
