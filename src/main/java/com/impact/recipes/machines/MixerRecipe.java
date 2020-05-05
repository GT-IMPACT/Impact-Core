package com.impact.recipes.machines;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MixerRecipe implements Runnable {
    @Override
    public void run() {

/** ================================= start CORE MOD =================================*/
        //HastelloyC276
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 32L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Molybdenum, 8L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 7L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cobalt, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1L), GT_Utility.getIntegratedCircuit(6), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HastelloyC276, 28L), 150 * 20, 1920);
        //Potin
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 6L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lead, 1L), GT_Values.NI, GT_Values.NI, GT_Utility.getIntegratedCircuit(3), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Potin, 9L), 20 * 20, 8);
        //EglinSteel
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 5L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Invar, 5L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1L), GT_Utility.getIntegratedCircuit(5), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EglinSteel, 16L), 30 * 20, 16);
        //Birmabright
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Manganese, 1L), GT_Values.NI, GT_Values.NI, GT_Utility.getIntegratedCircuit(3), GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Birmabright, 3L), 10 * 20, 48);
        /* ================================= end GT =================================*/

/** ================================= start GT =================================*/
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ice, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ledox, 1L), GT_Values.NI, GT_Values.NI, new FluidStack(FluidRegistry.getFluid("ic2coolant"), 2000), Materials.SuperCoolant.getFluid(2000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lapis, 2L), 200, 480);
        /* ================================= end GT =================================*/
    }
}
