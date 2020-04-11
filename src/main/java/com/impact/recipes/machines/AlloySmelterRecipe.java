package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AlloySmelterRecipe implements Runnable {
    public void run() {
        GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.clay_ball, 1, 0), new ItemStack(Blocks.sand, 1, 0), GT_ItemList.CokeOvenBrick.get(2L), 200, 8);

    }
}
