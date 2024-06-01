package com.impact.util.energy;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class EnergyUtil {

    public static long transferEnergy(IGregTechTileEntity source, IGregTechTileEntity target, long amperage, long voltage) {
        if (source == null || target == null || amperage <= 0 || voltage <= 0) {
            return 0;
        }

        long totalEUToTransfer = amperage * voltage;
        long availableEnergy = source.getStoredEU();
        if (availableEnergy < totalEUToTransfer) {
            return 0; // Not enough energy to transfer the desired amperage
        }

        long totalEnergyInjected = 0;
        for (int i = 0; i < amperage; i++) {
            long energyToInject = target.injectEnergyUnits((byte) 6, voltage, 1);
            if (energyToInject > 0) {
                totalEnergyInjected += voltage;
            } else {
                break;
            }
        }

        if (totalEnergyInjected > 0) {
            source.decreaseStoredEnergyUnits(totalEnergyInjected, true);
        }

        return totalEnergyInjected;
    }
}
