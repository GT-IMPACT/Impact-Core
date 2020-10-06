package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class WiremillRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Quartzite, 8L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), 80, 120);
        GT_Values.RA.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NetherQuartz, 4L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), 80, 120);
        GT_Values.RA.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CertusQuartz, 2L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), 80, 120);
        GT_Values.RA.addWiremillRecipe(CoreItems2.getRecipe(153,1), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), 80, 120);

    }
}
