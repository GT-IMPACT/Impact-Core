package com.impact.recipes;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AssemblerRecipe implements Runnable{
    @Override
    public void run(){

        GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 8L), ItemList.Casing_Tank_0.get(1L) , GT_ModHandler.getModItem("impact", "WroughtIronChest", 1L), 200, 16);


    }
}
