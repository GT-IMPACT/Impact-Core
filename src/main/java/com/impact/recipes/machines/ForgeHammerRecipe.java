package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_ModHandler;

public class ForgeHammerRecipe implements Runnable{
    @Override
    public void run(){

        final Core_Items CoreItems = Core_Items.getInstance();

        GT_Values.RA.addForgeHammerRecipe(GT_ModHandler.getModItem("TConstruct", "GlassBlock", 1L, 0), CoreItems.getRecipe(33, 1), 40, 16);

        // --- Mine Charcoal
        GT_Values.RA.addForgeHammerRecipe(GT_ModHandler.getModItem("minecraft", "coal", 1L, 1), GT_ModHandler.getModItem("Ztones", "minicharcoal", 9L, 0), 50, 8);
        // --- Mine Charcoal
        GT_Values.RA.addForgeHammerRecipe(GT_ModHandler.getModItem("minecraft", "coal", 1L, 0), GT_ModHandler.getModItem("Ztones", "minicoal", 9L, 0), 50, 8);

    }
}
