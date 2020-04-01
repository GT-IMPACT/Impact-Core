package com.impact.recipes.machines;

import com.impact.mods.GregTech.GTregister.GT_ItemList;
import com.impact.mods.GregTech.GTregister.GT_Materials;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AssemblerRecipe implements Runnable{
    @Override
    public void run(){

GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 8L), ItemList.Casing_Tank_0.get(1L) , GT_ModHandler.getModItem("impact", "WroughtIronChest", 1L), 200, 16);


/** ================================= start IMPACT MOD =================================*/
//Casings
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.GumMetal, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Titaniolum, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.PBECasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Zamak, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Duraluminium, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.EngraverCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel690, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Nitinol, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.AssemblerCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Inconel792, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.TiBetaC, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.CentrifugeCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Zamak, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ElectrolyzerCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Inconel690, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.WireFactoryCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel250, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Inconel792, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.SupplyProductionCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.HastelloyC276, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.UtilityMachineCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Talonite, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Grisium, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.BrewmenterCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Inconel690, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ElectromagneticCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Nitinol60, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Kovar, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ArcCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.TiBetaC, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Talonite, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.ExtradificationCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.Titaniolum, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.MaragingSteel250, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.MixingCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.HastelloyC276, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Mangalloy, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.MacerationCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, GT_Materials.MaragingSteel300, 1), GT_OreDictUnificator.get(OrePrefixes.plate, GT_Materials.Stellite, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.HSSG, 16)}, Materials.HSSE.getMolten(288L), GT_ItemList.CuttingCasing.get(1), 200, 1920);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("OpenComputers", "printer", 1L, 0), ItemList.Cover_Crafting.get(1L), ItemList.Conveyor_Module_HV.get(2L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4), GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.BlackSteel, 8), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.StainlessSteel, 16)}, Materials.SolderingAlloy.getMolten(288L), GT_ItemList.Machine_DDDPrinter.get(1), 200, 480);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.VanadiumSteel, 6), GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.RedSteel, 8)}, null, GT_ItemList.DDDPrinterCasing.get(1), 100, 120);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 340), ItemList.Cover_ItemDetector.get(4L), GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.VanadiumSteel, 4), ItemList.Robot_Arm_HV.get(4L), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 8), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.BlackSteel, 16), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 24), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.BlueSteel, 32)}, null, GT_ItemList.DDDPrinterCasing3x3.get(9), 800, 120);
        /* ================================= end IMPACT MOD =================================*/


    }
}
