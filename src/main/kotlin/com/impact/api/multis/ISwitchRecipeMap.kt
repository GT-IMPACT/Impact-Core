package com.impact.api.multis

import gregtech.api.util.GT_Recipe.GT_Recipe_Map
import net.minecraft.entity.player.EntityPlayer

interface ISwitchRecipeMap {
    fun getRecipesMap(): List<GT_Recipe_Map>
    fun onChangeRecipeMap(map: GT_Recipe_Map, player: EntityPlayer)
    fun hasSwitchMap(): Boolean
    fun getMapName(): String
}