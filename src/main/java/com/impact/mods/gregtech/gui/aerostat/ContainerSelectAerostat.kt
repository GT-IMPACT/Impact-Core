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

class ContainerSelectAerostat(
    inventory: InventoryPlayer,
    tile: IGregTechTileEntity,
) : GT_ContainerMetaTile_Machine(
    inventory,
    tile,
    false,
) {

    var selectedIndex: Int = 0
        private set
    var fuel: Int = 0
        private set
    var distance: Int = 0
        private set

    override fun addSlots(inventory: InventoryPlayer) {
        for (y in intArrayOf(
            8,
            26,
            55,
        )) {
            addSlotToContainer(
                GT_Slot_Holo(
                    mTileEntity,
                    2,
                    8,
                    y,
                    false,
                    false,
                    1,
                ),
            )
        }
        if (mTileEntity.isServerSide) {
            val aerostat = mTileEntity.metaTileEntity as GTMTE_Aerostat
            if (aerostat.currentLocationPlatforms.isNotEmpty()) {
                aerostat.updateSelected(
                    aerostat.indexSelect.coerceIn(
                        0,
                        aerostat.currentLocationPlatforms.lastIndex,
                    ),
                )
            }
        }
    }

    override fun slotClick(
        slot: Int,
        button: Int,
        mode: Int,
        player: EntityPlayer,
    ): ItemStack? {
        if (slot !in 0..2) {
            return super.slotClick(
                slot,
                button,
                mode,
                player,
            )
        }
        if (mTileEntity.isClientSide || player.openContainer !== this || !canInteractWith(player)) {
            return null
        }
        val aerostat = mTileEntity.metaTileEntity as? GTMTE_Aerostat ?: return null
        if (!aerostat.mMachine || aerostat.name.isEmpty()) {
            return null
        }
        when (slot) {
            0 -> {
                aerostat.selectStation(
                    direction = 1,
                    end = mode == 1,
                )
            }
            1 -> {
                aerostat.selectStation(
                    direction = -1,
                    end = mode == 1,
                )
            }
            2 -> {
                aerostat.toTravel(player)
            }
        }
        return null
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        if (mTileEntity.isClientSide) {
            return
        }
        val aerostat = mTileEntity.metaTileEntity as? GTMTE_Aerostat ?: return
        selectedIndex = aerostat.indexSelect
        fuel = aerostat.curBuffer
        distance = aerostat.selectedDistance

        for (listener in crafters) {
            val crafter = listener as ICrafting
            crafter.sendProgressBarUpdate(
                this,
                100,
                selectedIndex,
            )
            crafter.sendProgressBarUpdate(
                this,
                101,
                fuel,
            )
            crafter.sendProgressBarUpdate(
                this,
                102,
                distance,
            )
        }
    }

    @SideOnly(Side.CLIENT)
    override fun updateProgressBar(
        key: Int,
        value: Int,
    ) {
        super.updateProgressBar(
            key,
            value,
        )
        when (key) {
            100 -> {
                selectedIndex = value
            }
            101 -> {
                fuel = value and 0xffff
            }
            102 -> {
                distance = value
            }
        }
    }
}
