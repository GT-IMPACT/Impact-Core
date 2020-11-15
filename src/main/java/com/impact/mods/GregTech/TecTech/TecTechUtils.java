package com.impact.mods.GregTech.TecTech;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.tileentities.multi.GT_MetaTileEntity_MultiParallelBlockBase;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;

public class TecTechUtils {

    public static boolean addEnergyInputToMachineList(ITecTechEnabledMulti baseTE, IGregTechTileEntity te, int aBaseCasingIndex) {
        if (te == null || !(te.getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch))
            return false;
        else {
            GT_MetaTileEntity_Hatch mte = (GT_MetaTileEntity_Hatch) te.getMetaTileEntity();

            if (mte instanceof GT_MetaTileEntity_Hatch_Energy)
                baseTE.getVanillaEnergyHatches().add((GT_MetaTileEntity_Hatch_Energy) mte);
            else if (mte instanceof GT_MetaTileEntity_Hatch_EnergyTunnel)
                baseTE.getTecTechEnergyTunnels().add((GT_MetaTileEntity_Hatch_EnergyTunnel) mte);
            else if (mte instanceof GT_MetaTileEntity_Hatch_EnergyMulti)
                baseTE.getTecTechEnergyMultis().add((GT_MetaTileEntity_Hatch_EnergyMulti) mte);
            else
                return false;

            mte.updateTexture(aBaseCasingIndex);
            return true;
        }
    }

    public static boolean drainEnergyMEBFTecTech(ITecTechEnabledMulti multi, long aEU) {
        if (aEU <= 0)
            return true;

        long allTheEu = 0;
        int hatches = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : multi.getVanillaEnergyHatches())
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                allTheEu += tHatch.getEUVar();
                hatches++;
            }

        for (GT_MetaTileEntity_Hatch_EnergyTunnel tHatch : multi.getTecTechEnergyTunnels())
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                allTheEu += tHatch.getEUVar();
                hatches++;
            }

        for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : multi.getTecTechEnergyMultis())
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                allTheEu += tHatch.getEUVar();
                hatches++;
            }

        if (allTheEu < aEU)
            return false;

        long euperhatch = aEU / hatches;

        boolean hasDrained = false;

        for (GT_MetaTileEntity_Hatch_Energy tHatch : multi.getVanillaEnergyHatches())
            hasDrained |= tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(euperhatch, false);

        for (GT_MetaTileEntity_Hatch_EnergyTunnel tHatch : multi.getTecTechEnergyTunnels())
            hasDrained |= tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(euperhatch, false);

        for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : multi.getTecTechEnergyMultis())
            hasDrained |= tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(euperhatch, false);

        return hasDrained && (multi.getVanillaEnergyHatches().size() > 0 || multi.getTecTechEnergyTunnels().size() > 0 || multi.getTecTechEnergyMultis().size() > 0);
    }

    public static long getnominalVoltageTT(ITecTechEnabledMulti base) {
        long rVoltage = 0L;
        long rAmperage = 0L;

        for (GT_MetaTileEntity_Hatch_Energy tHatch : base.getVanillaEnergyHatches()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage = Math.max(tHatch.getBaseMetaTileEntity().getInputVoltage(), rVoltage);
                rAmperage += tHatch.getBaseMetaTileEntity().getInputAmperage();
            }
        }

        for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : base.getTecTechEnergyMultis()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage = Math.max(tHatch.getBaseMetaTileEntity().getInputVoltage(), rVoltage);
                rAmperage += tHatch.Amperes;
            }
        }

        for (GT_MetaTileEntity_Hatch_EnergyTunnel tHatch : base.getTecTechEnergyTunnels()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage = Math.max(getEUPerTickFromLaser(tHatch), rVoltage);
                rAmperage += 1;
            }
        }

        return rVoltage * rAmperage;
    }

    public static long getMaxInputVoltage(ITecTechEnabledMulti base) {
        long rVoltage = 0L;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : base.getVanillaEnergyHatches()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tHatch : base.getTecTechEnergyMultis()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getInputVoltage();
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyTunnel tHatch : base.getTecTechEnergyTunnels()) {
            if (GT_MetaTileEntity_MultiParallelBlockBase.isValidMetaTileEntity(tHatch)) {
                rVoltage += getEUPerTickFromLaser(tHatch);
            }
        }
        return rVoltage;
    }

    public static long getEUPerTickFromLaser(GT_MetaTileEntity_Hatch_EnergyTunnel tHatch) {
        return tHatch.Amperes * tHatch.maxEUInput() - (tHatch.Amperes / 20);
    }
}