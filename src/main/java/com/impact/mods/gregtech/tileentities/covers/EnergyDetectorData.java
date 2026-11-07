package com.impact.mods.gregtech.tileentities.covers;

import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicBatteryBuffer;
import gregtech.api.util.GT_ModHandler;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;

final class EnergyDetectorData {

    final long capacity;
    final long stored;

    private EnergyDetectorData(long capacity, long stored) {
        this.capacity = capacity;
        this.stored = stored;
    }

    static EnergyDetectorData read(ICoverable tile) {
        long maxCapacity = 0;
        long tStored = 0;
        if (tile instanceof IGregTechTileEntity) {
            IGregTechTileEntity tTileEntity = (IGregTechTileEntity) tile;
            IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();

            if (mTileEntity instanceof GTMTE_LapPowerStation) {
                GTMTE_LapPowerStation buffer = (GTMTE_LapPowerStation) mTileEntity;
                maxCapacity = buffer.capacity;
                tStored = buffer.stored;
            } else if (mTileEntity instanceof GT_MetaTileEntity_BasicBatteryBuffer) {
                maxCapacity = tile.getEUCapacity();
                tStored = tile.getStoredEU();
                GT_MetaTileEntity_BasicBatteryBuffer buffer = (GT_MetaTileEntity_BasicBatteryBuffer) mTileEntity;
                if (buffer.mInventory != null) {
                    for (ItemStack aStack : buffer.mInventory) {
                        if (GT_ModHandler.isElectricItem(aStack)) {
                            if (aStack.getItem() instanceof GT_MetaBase_Item) {
                                Long[] stats = ((GT_MetaBase_Item) aStack.getItem()).getElectricStats(aStack);
                                if (stats != null) {
                                    maxCapacity += stats[0];
                                    tStored = tStored + ((GT_MetaBase_Item) aStack.getItem()).getRealCharge(aStack);
                                }
                            } else if (aStack.getItem() instanceof IElectricItem) {
                                tStored += (long) ic2.api.item.ElectricItem.manager.getCharge(aStack);
                                maxCapacity += (long) ((IElectricItem) aStack.getItem()).getMaxCharge(aStack);
                            }
                        }
                    }
                }
            }
        }
        return new EnergyDetectorData(maxCapacity, tStored);
    }
}
