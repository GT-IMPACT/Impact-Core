package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;

public class CompessorRecipe implements Runnable {
    public void run() {
        GT_Values.RA.addCompressorRecipe(GT_ItemList.CokeOvenBrick.get(4L), ItemList.Casing_CokeOvenBrick.get(1L), 200, 8);
    }
}
