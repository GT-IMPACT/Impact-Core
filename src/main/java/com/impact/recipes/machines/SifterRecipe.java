package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class SifterRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    public void run() {
        GT_Values.RA.addSifterRecipe(GT_ModHandler.getModItem("GalaxySpace", "barnardaCdirt", 1L), new ItemStack[] {GT_ModHandler.getModItem("minecraft", "sand", 1L), GT_ModHandler.getModItem("GalaxySpace", "barnardaCdandelions", 1L, 0), GT_ModHandler.getModItem("GalaxySpace", "barnardaCdandelions", 1L, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Gallium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Yttrium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Niobium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Neutronium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.InfinityCatalyst, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DraconiumAwakened, 1L)}, new int[] {5000,2000,2000,2500,2000,1500,900,250,250}, 100, 2000000);

    }
}
