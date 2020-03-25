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
        GT_Values.RA.addNuclearReactorRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1),GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Thorium, 2), null, null, 512*20);
        //GT_Values.RA.addFreezerSolidifierRecipe(GT_ItemRegister.Shape_Mold_Ingot.get(0L), Materials.Water.getFluid(100), Materials.NaquadahAlloy.getMolten(144), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahAlloy, 1L), 100, 7680);
        GT_Values.RA.addPrimitiveLineRecipe(new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.WroughtIron, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Flint, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Quartz, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ruby, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gallium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Beryllium, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Caesium, 1),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Galena, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Naquadah, 1)
        }, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Uranium, 2), null, 909, 546*20);

        GT_Values.RA.addBasicLineRecipe(new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Apatite, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.WroughtIron, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Flint, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Quartz, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ruby, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gallium, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Beryllium, 1),
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Caesium, 1),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Galena, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Naquadah, 1)
        }, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Aluminium, 2), null, 999, 512*20);

    }
}
