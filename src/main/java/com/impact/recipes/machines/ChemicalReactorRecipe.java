package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.item.Core_List_Items.TCetiESeaweedExtract;
import static com.impact.util.Utilits.Itemstack;

public class ChemicalReactorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{ItemList.Circuit_Chip_RPico.get(1L), Materials.MysteriousCrystal.getDust(2), GT_OreDictUnificator.get(OrePrefixes.dustTiny,Materials.InfinityCatalyst,1L).copy().splitStack(0), CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1)},new FluidStack[]{Materials.Neutronium.getMolten(100L)},new FluidStack[]{GT_Values.NF},new ItemStack[]{ItemList.Circuit_Chip_Pico.get(1L)},3000,500000);
        GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{ItemList.Circuit_Chip_RPico.get(1L), Materials.MysteriousCrystal.getDust(2), GT_OreDictUnificator.get(OrePrefixes.dustTiny,Materials.InfinityCatalyst,1L).copy().splitStack(0), CoreItems.getRecipe(35, 1)},new FluidStack[]{Materials.Neutronium.getMolten(100L)},new FluidStack[]{GT_Values.NF},new ItemStack[]{ItemList.Circuit_Chip_Pico.get(1L)},3000,500000);

        GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1), GT_Utility.getIntegratedCircuit(1)}, new FluidStack[]{Materials.Radon.getGas(1000)}, new FluidStack[]{Materials.OilHeavy.getFluid(500), Materials.Ethanol.getFluid(300), Materials.EnrichedBacterialSludge.getFluid(200)}, new ItemStack[]{Materials.AlienOrganic.getDust(1)}, 500, 500000);
        GT_Values.RA.addMultiblockChemicalRecipe(new ItemStack[]{CoreItems.getRecipe(35, 1), GT_Utility.getIntegratedCircuit(1)}, new FluidStack[]{Materials.Radon.getGas(1000)}, new FluidStack[]{Materials.OilMedium.getFluid(500), Materials.Methanol.getFluid(350), Materials.EnrichedBacterialSludge.getFluid(150)}, new ItemStack[]{Materials.AlienOrganic.getDust(1)}, 500, 500000);

        GT_Values.RA.addChemicalRecipe(CoreItems.getRecipe(38, 3), Materials.Sodium.getDust(1), Materials.Water.getFluid(1000L), null, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 3L, 1), 500);

        GT_Values.RA.addChemicalRecipe(Materials.Yttrium.getDust(2), GT_Utility.getIntegratedCircuit(6), Materials.Oxygen.getGas(3000), null, Itemstack(Core_Items.getInstance(), 1, 40), null, 400, 30);
    }
}
