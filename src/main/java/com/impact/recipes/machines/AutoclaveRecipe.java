package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AutoclaveRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addAutoclaveSpaceRecipe(CoreItems.getRecipe(36, 64), Materials.Silver.getPlasma(8000L), GT_ModHandler.getModItem("SGCraft", "sgCoreCrystal", 1L), 10000, 3600, 131000, true);

        GT_Values.RA.addAutoclaveRecipe(CoreItems.getRecipe(38, 1), Materials.Water.getFluid(200L), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 7000, 2000, 24);
        GT_Values.RA.addAutoclaveRecipe(CoreItems.getRecipe(38, 1), GT_ModHandler.getDistilledWater(1000), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1), 9000, 1500, 24);

    }
}
