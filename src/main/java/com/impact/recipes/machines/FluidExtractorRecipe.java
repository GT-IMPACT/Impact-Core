package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;

import static com.impact.item.Core_List_Items.*;

public class FluidExtractorRecipe implements Runnable{
    final Core_Items CoreItems = Core_Items.getInstance();

    @Override
    public void run(){
        GT_Values.RA.addFluidExtractionRecipe(CoreItems.getRecipe(33, 1), null, Materials.Glass.getMolten(144), 10000, 50, 30);

    }
}
