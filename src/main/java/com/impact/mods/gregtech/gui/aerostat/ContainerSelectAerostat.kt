package com.impact.mods.gregtech.gui.aerostat

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import gregtech.api.gui.GT_ContainerMetaTile_Machine
import gregtech.api.gui.GT_Slot_Holo
import gregtech.api.interfaces.tileentity.IGregTechTileEntity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ICrafting
import net.minecraft.item.ItemStack

class ContainerSelectAerostat(aInventoryPlayer: InventoryPlayer?, aTileEntity: IGregTechTileEntity?)
    : GT_ContainerMetaTile_Machine(aInventoryPlayer, aTileEntity, false) {

    @JvmField
    var idLocation: Int = 1
    @JvmField
    var curBuffer: Int = 0
    @JvmField
    var distance: Int = 0

    override fun addSlots(aPlayerInventory: InventoryPlayer) {
        addSlotToContainer(GT_Slot_Holo(this.mTileEntity, 2, 8, 8, false, false, 1))
        addSlotToContainer(GT_Slot_Holo(this.mTileEntity, 2, 8, 26, false, false, 1))
        addSlotToContainer(GT_Slot_Holo(this.mTileEntity, 2, 8, 55, false, false, 1))

        if (mTileEntity.isServerSide) (mTileEntity.metaTileEntity as? GTMTE_Aerostat)?.also { aerostat ->
            aerostat.updateSelected(aerostat.indexSelect)
        }
    }

    override fun slotClick(aSlotIndex: Int, aMouseclick: Int, aShifthold: Int, aPlayer: EntityPlayer): ItemStack? {
        if (aSlotIndex < 0) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer)
        try {
            (mTileEntity.metaTileEntity as? GTMTE_Aerostat)?.also { aerostat ->
                val list = aerostat.currentLocationPlatforms
                if (mTileEntity.isServerSide && aerostat.name.isNotEmpty()) {
                    when (aSlotIndex) {
                        0 -> {
                            if (aShifthold == 1)
                                aerostat.indexSelect = list.lastIndex
                            else
                                aerostat.indexSelect += if ((aerostat.indexSelect + 1 > list.lastIndex)) 0 else 1

                            aerostat.updateSelected(aerostat.indexSelect)
                            return null
                        }

                        1 -> {
                            if (aShifthold == 1)
                                aerostat.indexSelect = 0
                            else
                                aerostat.indexSelect -= if ((aerostat.indexSelect - 1 < 0)) 0 else 1

                            aerostat.updateSelected(aerostat.indexSelect)
                            return null
                        }

                        2 -> aerostat.toTravel(aPlayer)
                    }
                }
            }
        } catch (ignored: Exception) {
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer)
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()

        if ((mTileEntity.isClientSide) || (mTileEntity.metaTileEntity == null)) return

        (mTileEntity.metaTileEntity as? GTMTE_Aerostat)?.also { te ->
            idLocation = te.indexSelect
            curBuffer = te.curBuffer
            distance = te.selectedDistance

            for (crafter in this.crafters) {
                (crafter as? ICrafting)?.also {
                    it.sendProgressBarUpdate(this, 100, idLocation)
                    it.sendProgressBarUpdate(this, 101, curBuffer)
                    it.sendProgressBarUpdate(this, 102, distance)
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override fun updateProgressBar(key: Int, value: Int) {
        super.updateProgressBar(key, value)
        when (key) {
            100 -> idLocation = value
            101 -> curBuffer = value
            102 -> distance = value
        }
    }
}
