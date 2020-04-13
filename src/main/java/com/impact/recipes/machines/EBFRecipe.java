package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class EBFRecipe implements Runnable{
    @Override
    public void run(){

/** ================================= start CORE MOD =================================*/
        GT_Values.RA.addBlastRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, GT_Materials.HastelloyC276, 1L), GT_Utility.getIntegratedCircuit(1), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.ingotHot, GT_Materials.HastelloyC276, 1L), null, 60*20, 1920, 3601);
/* ================================= end CORE MOD =================================*/



    }
}
