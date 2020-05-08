package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

import static com.impact.item.Core_List_Items.CokeOvenBrick;


public class CompessorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance(); //компоненты

    public void run() {
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 4), ItemList.Casing_CokeOvenBrick.get(1L), 200, 8);
        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CokeCoal, 9L), GregTech_API.getStackofAmountFromOreDict("blockCokeCoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(CoreItems.getRecipe(33, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L), 200, 2);

    }
}
