package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.impact.item.Core_List_Items.IridiumAlloyItemCasing;

public class AlloySmelterRecipe implements Runnable {
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addAlloySmelterRecipe(new ItemStack(Items.clay_ball, 1, 0), new ItemStack(Blocks.sand, 1, 0), GT_ItemList.CokeOvenBrick.get(2L), 200, 8);
        GT_Values.RA.addAlloySmelterRecipe(GT_ModHandler.getModItem("IC2", "itemPartIridium", 2L), ItemList.Shape_Mold_Casing.get(0), CoreItems2.getRecipe(IridiumAlloyItemCasing.getMetaID(), 3), 1200, 256);

    }
}
