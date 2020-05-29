package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;

public class FluidSolidifierRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.Sunnarium.getMolten(144L), CoreItems2.getRecipe(125,1), 200, 1920);
        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L), Materials.DraconiumPlasma.getFluid(144L), GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.Draconium, 1L), 200, 7680);
        GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ingot.get(0L), Materials.DraconiumAwakenedPlasma.getFluid(144L), GT_OreDictUnificator.get(OrePrefixes.ingotHot, Materials.DraconiumAwakened, 1L), 400, 500000);
    }
}