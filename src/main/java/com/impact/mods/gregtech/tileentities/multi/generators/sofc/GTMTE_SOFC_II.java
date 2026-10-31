package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class GTMTE_SOFC_II extends GTMTE_SOFC<GTMTE_SOFC_II> {
    public int TIER;

    public GTMTE_SOFC_II(int aID, String aNameRegional, int aTier) {
        super(aID, "impact.multimachine.fuelcellmk2", aNameRegional);
        this.TIER = aTier;

    }

    public GTMTE_SOFC_II(String aName, int aTier) {
        super(aName);
        this.TIER = aTier;

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {

        return new GTMTE_SOFC_II(super.mName, this.TIER);
    }

    @Override
    int getTier() {
        return TIER;
    }

}
