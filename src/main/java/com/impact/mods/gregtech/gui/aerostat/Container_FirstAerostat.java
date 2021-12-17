package com.impact.mods.gregtech.gui.aerostat;

import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import com.impact.network.IPacketString;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class Container_FirstAerostat extends GT_ContainerMetaTile_Machine implements IPacketString {
	
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
	public void update(String... str) {
		setLocationName(str[0]);
	}
}