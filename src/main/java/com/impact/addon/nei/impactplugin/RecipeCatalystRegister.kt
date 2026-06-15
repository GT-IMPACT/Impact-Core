package com.impact.addon.nei.impactplugin

import codechicken.nei.api.API
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.GT_RecipeMaps
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler
import gregtech.api.enums.ItemList
import gregtech.api.util.GT_ModHandler
import gregtech.api.util.GT_Recipe

object RecipeCatalystRegister {

    @JvmStatic
    fun register() {
        listOf(
            ItemList.OilDrill1,
            ItemList.OilDrill2,
            ItemList.OilDrill3,
        ).forEach { item ->
            API.addRecipeCatalyst(item.get(1), "virtual_world_fluids_dim")
            API.addRecipeCatalyst(item.get(1), "virtual_world_fluids_all")
        }
        listOf(
            GT_ItemList.OreProbe,
            GT_ItemList.CoalMiner,
            GT_ItemList.OreSamplingMachine,
            GT_ItemList.BasicMiner,
            GT_ItemList.AdvancedMiner,
        ).forEach { item ->
            API.addRecipeCatalyst(item.get(1), "virtual_world_ores_dim")
            API.addRecipeCatalyst(item.get(1), "virtual_world_ores_all")
        }
        listOf(
            ItemList.Machine_LV_WireAssembler,
            ItemList.Machine_MV_WireAssembler,
            ItemList.Machine_HV_WireAssembler,
            ItemList.Machine_EV_WireAssembler,
            ItemList.Machine_IV_WireAssembler,
            GT_ItemList.Machine_Wire,
        ).forEach {
            API.addRecipeCatalyst(it.get(1), GT_Recipe.GT_Recipe_Map.sWireAssemblerRecipes.mUnlocalizedName)
        }
        listOf(
            GT_ItemList.Machine_LV_ComponentAssembler,
            GT_ItemList.Machine_MV_ComponentAssembler,
            GT_ItemList.Machine_HV_ComponentAssembler,
            GT_ItemList.Machine_EV_ComponentAssembler,
            GT_ItemList.Machine_IV_ComponentAssembler,
            GT_ItemList.Machine_Assembler,
        ).forEach {
            API.addRecipeCatalyst(it.get(1), GT_Recipe.GT_Recipe_Map.sComponentAssemblerRecipes.mUnlocalizedName)
        }
        listOf(
            GT_ItemList.Drying_Oven_LV,
            GT_ItemList.Drying_Oven_MV,
            GT_ItemList.Drying_Oven_HV,
            GT_ItemList.Drying_Oven_EV,
            GT_ItemList.Drying_Oven_IV,
            GT_ItemList.Machine_ArcFurnace,
        ).forEach {
            API.addRecipeCatalyst(it.get(1), GT_RecipeMaps.sDryingOven.mUnlocalizedName)
        }
        listOf(
            GT_ItemList.Generator_Semi_Turbine_ULV,
            GT_ItemList.Generator_Semi_Turbine_LV,
            GT_ItemList.Generator_Semi_Turbine_MV,
            GT_ItemList.Generator_Semi_Turbine_HV,
            GT_ItemList.Generator_Semi_Turbine_EV,
        ).forEach {
            API.addRecipeCatalyst(it.get(1), GT_Recipe.GT_Recipe_Map.sSemifluidFuels.mUnlocalizedName)
        }

        API.addRecipeCatalyst(GT_ItemList.Machine_Wire.get(1), GT_Recipe.GT_Recipe_Map.sWiremillRecipes.mUnlocalizedName, -1)

        API.addRecipeCatalyst(GT_ItemList.SawMill.get(1), GT_Recipe.GT_Recipe_Map.sSawMillVisual.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Cutting.get(1), GT_Recipe.GT_Recipe_Map.sSawMillVisual.mUnlocalizedName)

        API.addRecipeCatalyst(GT_ItemList.The_Mill.get(1), GT_RecipeMaps.sTheMill.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_CokeOven.get(1), GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_ChemicalReactor.get(1), GT_Recipe.GT_Recipe_Map.sMultiblockChemicalRecipes.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.ME_System_Provider.get(1), GT_RecipeMaps.sMESystemProvider.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_DDDPrinter.get(1), GT_Recipe.GT_Recipe_Map.sPrimitiveLine.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_AdvDDDPrinter.get(1), GT_Recipe.GT_Recipe_Map.sBasicline.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Multi_Farm.get(1), GT_Recipe.GT_Recipe_Map.sFarmRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_IndustrialPulverizer.get(1), GT_Recipe.GT_Recipe_Map.sIndustrialPulverizerRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_FreezSolidifier.get(1), GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_BlastSmelter.get(1), GT_Recipe.GT_Recipe_Map.sBlastSmelterRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_FlotationUnit.get(1), GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_PBE.get(1), GT_Recipe.GT_Recipe_Map.sPressRecipes.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.Rail_Assembler.get(1), GTMTE_RailAssembler.sTrackAssemblerRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Heavy_Metal_Cyclone.get(1), GT_Recipe.GT_Recipe_Map.sCyclonRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Antimatter_Reactor.get(1), GT_Recipe.GT_Recipe_Map.sAntimatterReactorFuels.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_MultiblockTesseract.get(1), GT_Recipe.GT_Recipe_Map.sTesseractRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_MultiblockTinyWormHole.get(1), GT_Recipe.GT_Recipe_Map.sTinyWormHoleRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_ULV_Assembler.get(1), GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_ElectricImplosion.get(1), GT_Recipe.GT_Recipe_Map.sImplosionRecipes.mUnlocalizedName)

        API.addRecipeCatalyst(GT_ItemList.Naquadah_multi.get(1), GT_Recipe.GT_Recipe_Map.sHyperGenerator.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Naquadah_Liquid_multi.get(1), GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Naquadah_Liquid_Enriched.get(1), GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator.mUnlocalizedName)

        API.removeRecipeCatalyst(ItemList.Machine_Multi_DieselEngine.get(1), GT_Recipe.GT_Recipe_Map.sDieselFuels.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Generator_Diesel_ULV.get(1), GT_Recipe.GT_Recipe_Map.sDieselFuels.mUnlocalizedName, 1)
        API.addRecipeCatalyst(GT_ItemList.Generator_Diesel_EV.get(1), GT_Recipe.GT_Recipe_Map.sDieselFuels.mUnlocalizedName, -1)
        API.addRecipeCatalyst(ItemList.Machine_Multi_DieselEngine.get(1), GT_Recipe.GT_Recipe_Map.sDieselFuels.mUnlocalizedName, -1)
        API.addRecipeCatalyst(ItemList.Machine_Multi_DieselEngine2.get(1), GT_Recipe.GT_Recipe_Map.sDieselFuels.mUnlocalizedName, -1)

        API.addRecipeCatalyst(GT_ItemList.Generator_Gas_Turbine_ULV.get(1), GT_Recipe.GT_Recipe_Map.sTurbineFuels.mUnlocalizedName, 1)
        API.addRecipeCatalyst(GT_ItemList.SOFC_Low.get(1), GT_Recipe.GT_Recipe_Map.sTurbineFuels.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.SOFC_Medium.get(1), GT_Recipe.GT_Recipe_Map.sTurbineFuels.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.SOFC_Huge.get(1), GT_Recipe.GT_Recipe_Map.sTurbineFuels.mUnlocalizedName, -1)

        API.addRecipeCatalyst(GT_ItemList.Pyrolyse.get(1), GT_RecipeMaps.sPyrolyseOven.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.AdvPyrolyse.get(1), GT_RecipeMaps.sPyrolyseOven.mUnlocalizedName)

        API.addRecipeCatalyst(ItemList.Machine_MultiblockCentrifuge .get(1), GT_Recipe.GT_Recipe_Map.sMultiblockCentrifugeRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Centrifuge .get(1), GT_Recipe.GT_Recipe_Map.sMultiblockCentrifugeRecipes.mUnlocalizedName)

        API.addRecipeCatalyst(ItemList.Machine_MultiblockElectrolyzer.get(1), GT_Recipe.GT_Recipe_Map.sMultiblockElectrolyzerRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Electrolyzer.get(1), GT_Recipe.GT_Recipe_Map.sMultiblockElectrolyzerRecipes.mUnlocalizedName)
    }
}
