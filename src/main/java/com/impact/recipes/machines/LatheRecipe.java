package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LatheRecipe implements Runnable {

  final Core_Items CoreItems = Core_Items.getInstance();
  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {
    GT_Values.RA.addLatheRecipe(
        GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 2L, 1),
        CoreItems2.getRecipe(153, 2), CoreItems.getRecipe(38, 1), 980, 16);

    GT_Values.RA
        .addLatheRecipe(new ItemStack(Blocks.cobblestone), CoreItems2.getRecipe(177, 2), null, 100,
            16);
    GT_Values.RA
        .addLatheRecipe(new ItemStack(Blocks.sandstone), CoreItems2.getRecipe(178, 2), null, 100,
            16);

  }
}
