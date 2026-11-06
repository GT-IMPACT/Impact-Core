package com.impact.mods.gregtech.gui.aerostat;

import com.google.common.io.ByteArrayDataInput;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import org.jetbrains.annotations.NotNull;
import space.impact.packet_network.network.packets.IStreamPacketReceiver;

public class Container_FirstAerostat extends GT_ContainerMetaTile_Machine implements IStreamPacketReceiver {

    public Container_FirstAerostat(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
        super(inventoryPlayer, te, false);
    }

    @Override
    public void receive(@NotNull ByteArrayDataInput data) {
        EntityPlayer player = mPlayerInventory.player;
        if (player.openContainer != this || !canInteractWith(player)) {
            return;
        }
        IMetaTileEntity machine = mTileEntity.getMetaTileEntity();
        if (machine instanceof GTMTE_Aerostat) {
            GTMTE_Aerostat aerostat = (GTMTE_Aerostat) machine;
            String name = data.readUTF();
            if (aerostat.mMachine && !name.trim().isEmpty() && name.length() <= 16) {
                aerostat.setLocationName(name);
                aerostat.markDirty();
            }
        }
    }
}
