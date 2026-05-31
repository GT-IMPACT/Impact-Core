package com.impact.addon.gt.api.other

import net.minecraft.item.ItemStack

interface IIndicatorProvider {
    fun hasIndicator(): Boolean

    // unused -> deprecated
    fun getLabel(): String
    fun getStack(): ItemStack
    fun playersRecipients(): List<String>
}
