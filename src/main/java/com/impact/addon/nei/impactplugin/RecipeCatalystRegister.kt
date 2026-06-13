package com.impact.addon.nei.impactplugin

import codechicken.nei.api.API
import com.impact.mods.gregtech.GT_ItemList
import com.impact.mods.gregtech.GT_RecipeMaps
import gregtech.api.enums.ItemList
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

        API.addRecipeCatalyst(GT_ItemList.Machine_Wire.get(1), GT_Recipe.GT_Recipe_Map.sWiremillRecipes.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.SawMill.get(1), GT_Recipe.GT_Recipe_Map.sSawMillVisual.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Cutting.get(1), GT_Recipe.GT_Recipe_Map.sSawMillVisual.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.The_Mill.get(1), GT_RecipeMaps.sTheMill.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_CokeOven.get(1), GT_Recipe.GT_Recipe_Map.sCokeOvenRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_ChemicalReactor.get(1), GT_Recipe.GT_Recipe_Map.sMultiblockChemicalRecipes.mUnlocalizedName, -1)
        API.addRecipeCatalyst(GT_ItemList.ME_System_Provider.get(1), GT_RecipeMaps.sMESystemProvider.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_DDDPrinter.get(1), GT_Recipe.GT_Recipe_Map.sPrimitiveLine.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_AdvDDDPrinter.get(1), GT_Recipe.GT_Recipe_Map.sBasicline.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Pyrolyse.get(1), GT_RecipeMaps.sPyrolyseOven.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.AdvPyrolyse.get(1), GT_RecipeMaps.sPyrolyseOven.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_Multi_Farm.get(1), GT_Recipe.GT_Recipe_Map.sFarmRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_IndustrialPulverizer.get(1), GT_Recipe.GT_Recipe_Map.sIndustrialPulverizerRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_FreezSolidifier.get(1), GT_Recipe.GT_Recipe_Map.sFreezerSolidficationRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(GT_ItemList.Machine_BlastSmelter.get(1), GT_Recipe.GT_Recipe_Map.sBlastSmelterRecipes.mUnlocalizedName)
        API.addRecipeCatalyst(ItemList.Machine_FlotationUnit.get(1), GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes.mUnlocalizedName)
    }
}
