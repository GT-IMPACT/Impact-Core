package com.impact.addon.gt.api.security

import com.google.common.io.ByteArrayDataInput
import com.impact.network.NetworkPackets
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.inventory.Container
import net.minecraft.nbt.NBTTagCompound
import space.impact.packet_network.network.NetworkHandler.sendToPlayer
import space.impact.packet_network.network.packets.IStreamPacketReceiver

class SecurityContainer(val player: EntityPlayer) : Container(), IStreamPacketReceiver {

    var currentKey: String? = null

    override fun canInteractWith(player: EntityPlayer): Boolean {
        if (player is EntityPlayerMP) {
            currentKey = player.currentEquippedItem?.tagCompound?.getString("security_key")
            player.sendToPlayer(NetworkPackets.StreamPacket.transaction(currentKey ?: ""))
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

    override fun receive(data: ByteArrayDataInput) {
        setSecurityKey(data.readUTF())
    }
}