package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ElectrolyzerRecipe implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.addElectrolyzerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Naqlatigon, 56), null, null, Materials.Radon.getGas(4000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Naquadria, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.NaquadahEnriched, 3), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Naquadah, 13), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 16), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 19), null, null, 1000, 30720);
        GT_Values.RA.addElectrolyzerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Osmirinigon, 53), null, null, Materials.Argon.getGas(8000), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Osmium, 2), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iridium, 2), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Niobium, 7), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 13), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 17), null, null, 600, 1024);
        GT_Values.RA.addElectrolyzerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Mithril, 5), null, null, null, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 2), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 2), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.MeteoricIron, 1), null, null, null, null, 600, 480);

    }
}
