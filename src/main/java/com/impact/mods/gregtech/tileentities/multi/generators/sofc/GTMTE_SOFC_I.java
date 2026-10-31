package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class GTMTE_SOFC_I extends GTMTE_SOFC<GTMTE_SOFC_I> {
    public int TIER;

    public GTMTE_SOFC_I(int aID, String aNameRegional, int aTier) {
        super(aID, "impact.multimachine.fuelcellmk1", aNameRegional);
        this.TIER = aTier;

    }

    public GTMTE_SOFC_I(String aName, int aTier) {
        super(aName);
        this.TIER = aTier;

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {

        return new GTMTE_SOFC_I(super.mName, this.TIER);
    }

    @Override
    int getTier() {
        return TIER;
    }

}
