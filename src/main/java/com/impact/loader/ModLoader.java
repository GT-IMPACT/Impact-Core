package com.impact.loader;

import com.impact.item.Core_Items;
import com.impact.recipes.GTplusplusRecipe;
import com.impact.recipes.TecTechRecipe;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.item.Core_List_Items.TCetiESeaweedExtract;

public class ModLoader implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    @Override
    public void run() {
        // --- GT++
        if (Loader.isModLoaded("miscutils")) {
            new GTplusplusRecipe().run();
        }

        // --- TecTech
        if (Loader.isModLoaded("tectech")) {
            new TecTechRecipe().run();
        }

        // --- GalaxySpace
        if (Loader.isModLoaded("GalaxySpace"))
        {
            for (byte i = 0; i < 6; ++i)
                GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("GalaxySpace", "tcetiedandelions", 64L, i), CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1), 3600, 262144);

            GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{ItemList.Circuit_Chip_RPico.get(1L), Materials.MysteriousCrystal.getDust(2), GT_OreDictUnificator.get(OrePrefixes.dustTiny,Materials.InfinityCatalyst,1L).copy().splitStack(0), CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1)},new FluidStack[]{Materials.Neutronium.getMolten(100L)},new FluidStack[]{GT_Values.NF},new ItemStack[]{ItemList.Circuit_Chip_Pico.get(1L)},3000,(int)(GT_Values.V[9]-(GT_Values.V[9]/10)));

        }else {

            GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{ItemList.Circuit_Chip_RPico.get(1L),Materials.MysteriousCrystal.getDust(2),GT_OreDictUnificator.get(OrePrefixes.dustTiny,Materials.InfinityCatalyst,1L).copy().splitStack(0),Materials.AlienBiomass.getDust(1)},new FluidStack[]{Materials.Neutronium.getFluid(100L)},new FluidStack[]{GT_Values.NF},new ItemStack[]{ItemList.Circuit_Chip_Pico.get(1L)},3000,(int)(GT_Values.V[9]-(GT_Values.V[9]/10)));
        }

    }
}
