package com.impact.mods.GregTech.TecTech;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;

import java.util.List;

public interface ITecTechEnabledMulti {
    List<GT_MetaTileEntity_Hatch_Energy> getVanillaEnergyHatches();

    List<GT_MetaTileEntity_Hatch_EnergyTunnel> getTecTechEnergyTunnels();

    List<GT_MetaTileEntity_Hatch_EnergyMulti> getTecTechEnergyMultis();
}