package com.impact.mods.gregtech.gui.aerostat;

import com.google.common.io.ByteArrayDataInput;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import org.jetbrains.annotations.NotNull;
import space.impact.packet_network.network.packets.IStreamPacketReceiver;

public class Container_FirstAerostat extends GT_ContainerMetaTile_Machine implements IStreamPacketReceiver {
	
	public Container_FirstAerostat(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
		super(inventoryPlayer, te, false);
	}
	
	public void setLocationName(String name) {
		IMetaTileEntity mte = this.mTileEntity.getMetaTileEntity();
		if (mte instanceof GTMTE_Aerostat) {
			GTMTE_Aerostat te = (GTMTE_Aerostat) mte;
			te.setLocationName(name);
		}
	}
	
	@Override
	public void receive(@NotNull ByteArrayDataInput data) {
		setLocationName(data.readUTF());
	}
}
