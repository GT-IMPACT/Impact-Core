package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MixerRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();

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

        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neutronium, 16L), GT_Values.NI, GT_Utility.getIntegratedCircuit(1), GT_Values.NI, Materials.Helium.getPlasma(2304L), GT_Values.NF, CoreItems.getRecipe(34,16), 3600, 122880);

/** ================================= start GT =================================*/
        GT_Values.RA.addMixerRecipe(CoreItems.getRecipe(2,1), CoreItems.getRecipe(10,1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ledox, 1L), GT_Values.NI, new FluidStack(FluidRegistry.getFluid("ic2coolant"), 3000), Materials.SuperCoolant.getFluid(3000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ice, 3L), 300, 480);
        GT_Values.RA.addMixerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 2L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Potassium, 3L), GT_Utility.getIntegratedCircuit(2), GT_Values.NI, GT_Values.NF, Materials.SodiumPotassium.getFluid(1000), GT_Values.NI, 300, 480);

        /* ================================= end GT =================================*/
    }
}
