package com.impact.mods.gregtech.gui.nuclear;

import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.GTMTE_NuclearReactorBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class GT_Container_NuclearReactor extends GT_ContainerMetaTile_Machine {
	
	public int mRodUp;
	public int mTemp;
	public int mInput;
	public int mOutput;
	public int mMaxTemp;
	public boolean isFastDecay;
	public boolean isMoxFuel;
	public boolean stopTemp;
	public int[] mHatchesRodPosition;
	GTMTE_NuclearReactorBase mte;
	
	public GT_Container_NuclearReactor(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
		super(aInventoryPlayer, aTileEntity, false);
		mte = (GTMTE_NuclearReactorBase<?>) mTileEntity.getMetaTileEntity();
	}
	
	public GT_Container_NuclearReactor(InventoryPlayer aInventoryPlayer,
									   IGregTechTileEntity aTileEntity, GTMTE_NuclearReactorBase<?> base) {
		super(aInventoryPlayer, aTileEntity);
		mte = base;
	}
	
	@Override
	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 5, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 37, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 55, false, false, 1));
		addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 155, 73, false, false, 1));
	}
	
	@Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold,
							   EntityPlayer aPlayer) {
		if (aSlotIndex < 0 || aSlotIndex > 5) {
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		Slot tSlot = (Slot) inventorySlots.get(aSlotIndex);
		if (tSlot != null && mTileEntity.getMetaTileEntity() != null) {
			try {
				int rodZero = mte.getRodPosition().length > 0 ? mte.getRodPosition()[0] : 0;
				if (aSlotIndex == 0) {
					if (mte.getBaseMetaTileEntity().isAllowedToWork()) {
						GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Disabled");
						//mte.setRodPosition(10);
						GT_Utility.sendChatToPlayer(aPlayer, "" + mte.getRodPosition()[0]);
						mte.getBaseMetaTileEntity().disableWorking();
						mte.mFirstStart = false;
					} else {
						GT_Utility.sendChatToPlayer(aPlayer, "Machine Processing: Enabled");
						GT_Utility.sendChatToPlayer(aPlayer, "" + mte.getRodPosition()[0]);
						mte.getBaseMetaTileEntity().enableWorking();
						mte.mFirstStart = true;
					}
				}
				if (aSlotIndex == 1) {
					mte.setRodPosition(rodZero - 1);
					GT_Utility.sendChatToPlayer(aPlayer, "All Rods Up 10%");
					GT_Utility.sendChatToPlayer(aPlayer, "" + mte.getRodPosition()[0]);
				}
				if (aSlotIndex == 2) {
					mte.setRodPosition(rodZero + 1);
					GT_Utility.sendChatToPlayer(aPlayer, "All Rods Down 10%");
					GT_Utility.sendChatToPlayer(aPlayer, "" + mte.getRodPosition()[0]);
				}
				if (aSlotIndex == 3 && !mte.isFastDecay) {
					mte.stopTemp = !mte.stopTemp;
					GT_Utility.sendChatToPlayer(aPlayer, "Stop temperature: " + (mte.stopTemp ? "Enabled" : "Disabled"));
				}
			} catch (Exception ignored) {
			}
		}
		return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
		GTMTE_NuclearReactorBase<?> reactor = ((GTMTE_NuclearReactorBase<?>) mTileEntity.getMetaTileEntity());
		this.mTemp = (int) reactor.mCurrentTemp;
		this.mMaxTemp = reactor.maxTemperature();
		this.mInput = (int) Math.ceil(reactor.mCurrentInput);
		this.mOutput = (int) Math.ceil(reactor.mCurrentOutput);
		this.mHatchesRodPosition = reactor.getRodPosition();
		this.isFastDecay = reactor.isFastDecay;
		this.isMoxFuel = reactor.isMoxFuel;
		this.stopTemp = reactor.stopTemp;
		
		for (Object crafter : this.crafters) {
			ICrafting var1 = (ICrafting) crafter;
			var1.sendProgressBarUpdate(this, 100, mTemp & 65535);
			var1.sendProgressBarUpdate(this, 101, mTemp >>> 16);
			var1.sendProgressBarUpdate(this, 102, mInput & 65535);
			var1.sendProgressBarUpdate(this, 103, mInput >>> 16);
			var1.sendProgressBarUpdate(this, 104, isFastDecay ? 1 : 0);
			var1.sendProgressBarUpdate(this, 105, isMoxFuel ? 1 : 0);
			var1.sendProgressBarUpdate(this, 106, mOutput & 65535);
			var1.sendProgressBarUpdate(this, 107, mOutput >>> 16);
			var1.sendProgressBarUpdate(this, 108, mMaxTemp & 65535);
			var1.sendProgressBarUpdate(this, 109, mMaxTemp >>> 16);
			var1.sendProgressBarUpdate(this, 110, stopTemp ? 1 : 0);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		switch (id) {
			case 100:
				mTemp = mTemp & -65536 | data;
				break;
			case 101:
				mTemp = mTemp & 65535 | data << 16;
				break;
			case 102:
				mInput = mInput & -65536 | data;
				break;
			case 103:
				mInput = mInput & 65535 | data << 16;
				break;
			case 104:
				isFastDecay = (data != 0);
				break;
			case 105:
				isMoxFuel = (data != 0);
				break;
			case 106:
				mOutput = mOutput & -65536 | data;
				break;
			case 107:
				mOutput = mOutput & 65535 | data << 16;
				break;
			case 108:
				mMaxTemp = mMaxTemp & -65536 | data;
				break;
			case 109:
				mMaxTemp = mMaxTemp & 65535 | data << 16;
				break;
			case 110:
				stopTemp = (data != 0);
				break;
		}
	}
	
}
