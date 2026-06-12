package com.impact.client.gui.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container

abstract class FullScreenContainer : Container() {

    abstract override fun canInteractWith(player: EntityPlayer): Boolean
}
