package com.impact.addon.nei.impactplugin

import codechicken.nei.api.API
import com.impact.mods.gregtech.GT_ItemList
import gregtech.api.enums.ItemList

object RecipeCatalystRegister {

    @JvmStatic
    fun register() {
        listOf(ItemList.OilDrill1, ItemList.OilDrill2, ItemList.OilDrill3).forEach { item ->
            API.addRecipeCatalyst(item.get(1), "virtual_world_fluids_dim")
            API.addRecipeCatalyst(item.get(1), "virtual_world_fluids_all")
        }
        listOf(GT_ItemList.CoalMiner, GT_ItemList.BasicMiner, GT_ItemList.AdvancedMiner).forEach { item ->
            API.addRecipeCatalyst(item.get(1), "virtual_world_ores_dim")
            API.addRecipeCatalyst(item.get(1), "virtual_world_ores_all")
        }
    }
}