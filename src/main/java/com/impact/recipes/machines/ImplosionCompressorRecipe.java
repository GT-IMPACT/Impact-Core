package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ImplosionCompressorRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Aluminium, 2L), 1, GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualAluminium", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Bronze, 2L), 1, GT_ModHandler.getModItem("GalaxySpace", "item.CompressedDualBronze", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(97, 1), 5, GT_ModHandler.getModItem("GalaxySpace", "item.CompressedSDHD120", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.StainlessSteel, 1L));
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 1L), 1, GT_ModHandler.getModItem("GalaxySpace", "item.CompressedCoal", 1L, 0), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(74, 3), 1, CoreItems2.getRecipe(78, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(75, 3), 1, CoreItems2.getRecipe(80, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(76, 3), 1, CoreItems2.getRecipe(83, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(GT_ModHandler.getModItem("IC2", "itemPartIridium", 3L), 2, CoreItems2.getRecipe(81, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L));
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Desh, 2L), 1, CoreItems2.getRecipe(90, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(80, 2), 1, CoreItems2.getRecipe(91, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(81, 2), 2, CoreItems2.getRecipe(92, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L));
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.MeteoricIron, 2L), 1, CoreItems2.getRecipe(93, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(83, 2), 1, CoreItems2.getRecipe(94, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(CoreItems2.getRecipe(85, 2), 1, CoreItems2.getRecipe(95, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
        GT_Values.RA.addImplosionRecipe(GT_OreDictUnificator.get(OrePrefixes.compressed, Materials.Titanium, 2L), 1, CoreItems2.getRecipe(96, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));

    }
}
