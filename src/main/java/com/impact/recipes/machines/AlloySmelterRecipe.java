package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AlloySmelterRecipe implements Runnable {
    public void run() {
        GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.clay_ball, 3, 0), new ItemStack(Blocks.sand, 5, 0), GT_ItemList.CokeOvenBrick.get(3L), 130, 3);

        GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.clay_ball, 1, 0), ItemList.Shape_Mold_Ingot.get(0), GT_ModHandler.getModItem("minecraft", "brick", 1L, 0), 130, 3);

        GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1), ItemList.Shape_Mold_Ingot.get(0), GT_ModHandler.getModItem("TConstruct", "materials", 1L, 2), 130, 3);

    }
}
