package com.impact.api.security

import com.impact.network.IPacketString
import com.impact.network.ToClient_String
import cpw.mods.fml.common.FMLCommonHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.inventory.Container
import net.minecraft.nbt.NBTTagCompound

class SecurityContainer(val player: EntityPlayer) : Container(), IPacketString {

    var currentKey: String? = null

    override fun canInteractWith(player: EntityPlayer): Boolean {
        if (player is EntityPlayerMP) {
            currentKey = player.currentEquippedItem?.tagCompound?.getString("security_key")
            ToClient_String(currentKey ?: "").sendToPlayer(player)
        }
        return player.currentEquippedItem != null
    }

    private fun setSecurityKey(key: String?) {
        if (key == null) return
        val stack = player.currentEquippedItem ?: return
        var nbtStack = stack.tagCompound
        if (nbtStack == null) {
            nbtStack = NBTTagCompound()
            stack.tagCompound = nbtStack
        }
        nbtStack.setString("security_key", key)
    }

    override fun update(vararg str: String) {
        setSecurityKey(str.firstOrNull())
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
//        if (FMLCommonHandler.instance().side.isClient || player.currentEquippedItem == null) {
//            return
//        }
//
//        currentKey = player.currentEquippedItem?.tagCompound?.getString("security_key")
//
//        if (player is EntityPlayerMP) {
//            ToClient_String(currentKey ?: "").sendToPlayer(player)
//        }
    }
}