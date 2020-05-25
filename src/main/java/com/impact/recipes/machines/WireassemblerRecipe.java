package com.impact.recipes.machines;

import gregtech.api.enums.*;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class WireassemblerRecipe implements Runnable {

    @Override
    public void run() {
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 64L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 64L)}, Materials.Trinium.getMolten(5760L), ItemList.Casing_Coil_Superconductor.get(1L), 3000, 7680, true);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorLuV, 32L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 32L)}, Materials.Trinium.getMolten(4320L), ItemList.Casing_Coil_Superconductor.get(1L), 2000, 30720, true);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorZPM, 16L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 16L)}, Materials.Trinium.getMolten(2880L), ItemList.Casing_Coil_Superconductor.get(1L), 1500, 122880, true);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUV, 8L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 8L)}, Materials.Trinium.getMolten(1440L), ItemList.Casing_Coil_Superconductor.get(1L), 750, 500000, true);
        GT_Values.RA.addWireAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 8L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 4L)}, Materials.Trinium.getMolten(720L), ItemList.Casing_Coil_Superconductor.get(1L), 375, 2000000, true);

    }
}
