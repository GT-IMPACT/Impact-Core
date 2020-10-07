package com.impact.recipes.machines;

import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_ModHandler;

public class CannerRecipe implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.addCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 0), GT_ModHandler.getModItem("minecraft", "apple", 6L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 15), null, 800, 1);
        GT_Values.RA.addCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 0), GT_ModHandler.getModItem("minecraft", "carrot", 8L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 16), null, 800, 1);
        GT_Values.RA.addCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 0), GT_ModHandler.getModItem("minecraft", "melon", 8L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 17), null, 800, 1);
        GT_Values.RA.addCannerRecipe(GT_ModHandler.getModItem("GalacticraftCore", "item.canister", 1L, 0), GT_ModHandler.getModItem("minecraft", "potato", 16L, 0), GT_ModHandler.getModItem("GalacticraftCore", "item.basicItem", 1L, 18), null, 800, 1);

    }
}
