package com.impact.mods.gregtech.gui.regulatechest;

import com.impact.mods.gregtech.tileentities.basic.GTMTE_RegulateDigitalChest;
import com.impact.mods.gregtech.tileentities.multi.units.GTMTE_Aerostat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;

public class Container_ValueRegulateChest extends GT_ContainerMetaTile_Machine {
	
	public int mMaxCapacity;
	
	public Container_ValueRegulateChest(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
		super(inventoryPlayer, te, false);
		
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
			return;
		}
		mMaxCapacity = ((GTMTE_RegulateDigitalChest) this.mTileEntity.getMetaTileEntity()).mMaxItemCount;
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, this.mMaxCapacity & 0xFFFF);
			var1.sendProgressBarUpdate(this, 101, this.mMaxCapacity >>> 16);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);
		switch (par1) {
			case 100:
				this.mMaxCapacity = (this.mMaxCapacity & 0xFFFF0000 | par2);
				break;
			case 101:
				this.mMaxCapacity = (this.mMaxCapacity & 0xFFFF | par2 << 16);
				break;
		}
	}
}