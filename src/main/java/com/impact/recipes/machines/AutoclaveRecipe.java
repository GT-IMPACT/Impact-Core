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

    }
}
