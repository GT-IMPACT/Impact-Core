package com.impact.api.satellite.gui

import com.impact.api.satellite.IConnection
import com.impact.api.satellite.ISatellite
import com.impact.api.satellite.ISatelliteNetwork
import com.impact.util.PositionObject
import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.gui.GT_Slot_Holo
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import gregtech.api.util.GT_Utility
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ICrafting
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting

class SatelliteNetworkContainer(inv: InventoryPlayer, te: IGregTechTileEntity) : GT_ContainerMetaTile_Machine(inv, te) {

    var currentFrequency: Int = 0
    var isConnected: Boolean = false

    override fun addSlots(aPlayerInventory: InventoryPlayer?) {
        addSlotToContainer(GT_Slot_Holo(mTileEntity, 2, 8, 8, false, false, 1))
        addSlotToContainer(GT_Slot_Holo(mTileEntity, 2, 8, 26, false, false, 1))
        addSlotToContainer(GT_Slot_Holo(mTileEntity, 2, 8, 55, false, false, 1))
    }

    override fun slotClick(id: Int, mouseclick: Int, shift: Int, p: EntityPlayer): ItemStack? {
        if (id < 0) {
            return super.slotClick(id, mouseclick, shift, p)
        }
        val slot = inventorySlots[id] as? Slot

        if (mTileEntity.isServerSide) {
            val te = mTileEntity.metaTileEntity
            if (slot != null && te != null) {
                when (te) {
                    is ISatellite -> satelliteSlotClick(te, id, shift, p)
                    is ISatelliteNetwork -> towerSlotClick(te, id, shift, p)
                }
                return null
            }
        }
        return super.slotClick(id, mouseclick, shift, p)
    }

    private fun satelliteSlotClick(te: ISatellite, id: Int, shift: Int, p: EntityPlayer) {
        when (id) {
            0 -> changeFrequency(te, false, shift)
            1 -> changeFrequency(te, true, shift)
            2 -> {
                te.updateFrequency(currentFrequency)
                GT_Utility.sendChatToPlayer(p, "Connection created! ${EnumChatFormatting.GREEN}Frequency: $currentFrequency")
            }
        }
    }

    private fun changeFrequency(te: IConnection, isMinus: Boolean, shift: Int) {
        var current = currentFrequency
        val dig = if (isMinus) -1 else 1
        val coefficient = if (shift == 1) 10 else 1
        current += coefficient * dig
        te.updateFrequency(current)
    }

    private fun towerSlotClick(te: ISatelliteNetwork, id: Int, shift: Int, p: EntityPlayer){
        when (id) {
            0 -> changeFrequency(te, false, shift)
            1 -> changeFrequency(te, true, shift)
            2 -> {
                te.updateFrequency(currentFrequency)
                val tag = p.currentEquippedItem.tagCompound?.getCompoundTag("satellite") ?: return
                val pos = PositionObject.loadFromNBT(tag)
                te.onFirstConnect(pos)
                GT_Utility.sendChatToPlayer(p, "Connection confirm! ${EnumChatFormatting.GREEN}Frequency: $currentFrequency")
            }
        }
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()

        if (mTileEntity.isClientSide || mTileEntity.metaTileEntity == null) {
            return
        }

        val te = mTileEntity?.metaTileEntity as? IConnection
        if (te != null) {
            isConnected = te.getConnectionStatus()
            currentFrequency = te.getFrequency()
        }

        for (crafter in crafters) {
            val c = crafter as ICrafting
            c.sendProgressBarUpdate(this, 100, currentFrequency and 0xFFFF)
            c.sendProgressBarUpdate(this, 101, currentFrequency ushr 16)
            c.sendProgressBarUpdate(this, 102, if (isConnected) 1 else 0)
        }
    }

    override fun updateProgressBar(par1: Int, par2: Int) {
        super.updateProgressBar(par1, par2)
        when(par1) {
            100 -> currentFrequency = currentFrequency and -0x10000 or par2
            101 -> currentFrequency = currentFrequency  and 0xFFFF or (par2 shl 16)
            102 -> isConnected = par2 == 1
        }
    }
}