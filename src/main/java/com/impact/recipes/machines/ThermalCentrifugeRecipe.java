package com.impact.recipes.machines;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ThermalCentrifugeRecipe implements Runnable {

    public void run() {
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorMOXSimpledepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 1L),
                GT_ModHandler.getModItem("IC2", "itemPlutonium", 3L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 1L));
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorMOXDualdepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 2L),
                GT_ModHandler.getModItem("IC2", "itemPlutonium", 6L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 3L));
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorMOXQuaddepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 4L),
                GT_ModHandler.getModItem("IC2", "itemPlutonium", 12L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 6L));
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorUraniumSimpledepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 1L),
                GT_ModHandler.getModItem("IC2", "itemUran238", 4L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 1L));
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorUraniumDualdepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 2L),
                GT_ModHandler.getModItem("IC2", "itemUran238", 8L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 3L));
        GT_ModHandler.addThermalCentrifugeRecipe(GT_ModHandler.getModItem("IC2", "reactorUraniumQuaddepleted", 1L),
                5000, GT_ModHandler.getModItem("IC2", "itemPlutoniumSmall", 4L),
                GT_ModHandler.getModItem("IC2", "itemUran238", 16L),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zirconium, 6L));
    }
}
