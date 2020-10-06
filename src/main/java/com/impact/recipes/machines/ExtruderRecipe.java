package com.impact.recipes.machines;

import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class ExtruderRecipe implements Runnable{

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(69, 1), ItemList.Shape_Extruder_Plate.get(0L), CoreItems2.getRecipe(74, 1), 200, 120);
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(71, 1), ItemList.Shape_Extruder_Plate.get(0L), CoreItems2.getRecipe(75, 1), 200, 120);
        GT_Values.RA.addExtruderRecipe(CoreItems2.getRecipe(72, 1), ItemList.Shape_Extruder_Plate.get(0L), CoreItems2.getRecipe(76, 1), 300, 480);

        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1), ItemList.Shape_Extruder_Shaft.get(0L), GT_ModHandler.getModItem("IC2", "itemRecipePart", 1L, 11), 2000, 120);
        GT_Values.RA.addExtruderRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Steel, 1), ItemList.Shape_Extruder_Shaft.get(0L), GT_ModHandler.getModItem("IC2", "itemRecipePart", 1L, 12), 2000, 480);

    }
}
