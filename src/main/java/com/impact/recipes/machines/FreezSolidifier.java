package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

public class FreezSolidifier implements Runnable{
    @Override
    public void run(){
        GT_Values.RA.addFreezerSolidifierRecipe(null, Materials.Water.getFluid(100), GT_Materials.HastelloyC276.getMoltenHot(144L), GT_OreDictUnificator.get(OrePrefixes.ingot, GT_Materials.HastelloyC276, 1L), 20*20, 1920);
    }
}
