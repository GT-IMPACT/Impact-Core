package com.impact.recipes.debug;


import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class DEBUG_Recipe implements Runnable{

	@Override
    public void run(){

	    /** ТОЛЬКО ДЛЯ ТЕСТОВ*/
        //GT_Values.RA.addNuclearReactorRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1),GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Thorium, 2), null, null, 512*20);
        //GT_Values.RA.addFreezerSolidifierRecipe(GT_ItemRegister.Shape_Mold_Ingot.get(0L), Materials.Water.getFluid(100), Materials.NaquadahAlloy.getMolten(144), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahAlloy, 1L), 100, 7680);

    }
}
