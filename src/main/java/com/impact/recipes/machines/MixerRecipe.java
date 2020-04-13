package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class MixerRecipe implements Runnable{
    @Override
    public void run(){

/** ================================= start CORE MOD =================================*/
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 8L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 7L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 32L), GT_Utility.getIntegratedCircuit(6), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, GT_Materials.HastelloyC276, 28L), 150*20, 1920);

/* ================================= end CORE MOD =================================*/



    }
}
