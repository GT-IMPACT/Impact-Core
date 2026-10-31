package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class GTMTE_SOFC_III extends GTMTE_SOFC<GTMTE_SOFC_III> {
    public int TIER;

    public GTMTE_SOFC_III(int aID, String aNameRegional, int aTier) {
        super(aID, "impact.multimachine.fuelcellmk3", aNameRegional);
        this.TIER = aTier;

    }

    public GTMTE_SOFC_III(String aName, int aTier) {
        super(aName);
        this.TIER = aTier;

    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {

        return new GTMTE_SOFC_III(super.mName, this.TIER);
    }

    @Override
    int getTier() {
        return TIER;
    }

}
