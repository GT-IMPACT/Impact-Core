package com.impact.addon.gt.api.multis

import net.minecraft.entity.player.EntityPlayer

interface ISeparateBus {
    fun hasSeparate(): Boolean
    fun onChangeSeparateMode(isSeparate: Boolean, player: EntityPlayer)
    fun isSeparated(): Boolean
}