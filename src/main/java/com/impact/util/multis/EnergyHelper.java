package com.impact.util.multis;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class EnergyHelper {
	
	public static int getInjected(long aEU, int injected, boolean validMetaTileEntity, long l, long l2, IGregTechTileEntity baseMetaTileEntity) {
		long leftToInject;
		long aVoltage;
		int aAmpsToInject;
		int aRemainder;
		int ampsOnCurrentHatch;
		if (validMetaTileEntity) {
			leftToInject = aEU - injected;
			aVoltage = l;
			aAmpsToInject = (int) (leftToInject / aVoltage);
			aRemainder = (int) (leftToInject - (aAmpsToInject * aVoltage));
			ampsOnCurrentHatch = (int) Math.min(l2, aAmpsToInject);
			for (int i = 0; i < ampsOnCurrentHatch; i++) {
				baseMetaTileEntity.increaseStoredEnergyUnits(aVoltage, false);
			}
			injected += aVoltage * ampsOnCurrentHatch;
			if (aRemainder > 0 && ampsOnCurrentHatch < l2) {
				baseMetaTileEntity.increaseStoredEnergyUnits(aRemainder, false);
				injected += aRemainder;
			}
		}
		return injected;
	}
}
