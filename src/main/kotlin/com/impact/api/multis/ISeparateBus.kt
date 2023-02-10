package com.impact.api.multis

import net.minecraft.entity.player.EntityPlayer

interface ISeparateBus {
    fun hasSeparate(): Boolean
    fun onChangeSeparateMode(isSeparate: Boolean, player: EntityPlayer)
    fun isSeparated(): Boolean
}