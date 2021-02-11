package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;

public class PackagerRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();

    @Override
    public void run() {
        GT_Values.RA.
                addBoxingRecipe(CoreItems.getRecipe(29, 4), ItemList.Schematic_Dust.get(0L),
                        GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L), 100, 4);
        GT_Values.RA.
                addBoxingRecipe(CoreItems.getRecipe(30, 9), ItemList.Schematic_Dust.get(0L),
                        GT_ModHandler.getModItem("IC2", "itemFertilizer", 1L), 100, 4);
    }
}
