package com.gwppcore.gthandler.tileentities.multi;

import com.gwppcore.main.ConfigHandler;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_VacuumFreezer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.HashSet;

import static gregtech.api.enums.GT_Values.V;

public class GT_TileEntity_MegaVacuumFreezer extends GT_MetaTileEntity_VacuumFreezer {



    public GT_TileEntity_MegaVacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_TileEntity_MegaVacuumFreezer(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        String[] dsc = StatCollector.translateToLocal("tooltip.tile.mvf.0.name").split(";");
        String[] fdsc = new String[dsc.length + 1];
        for (int i = 0; i < dsc.length; i++) {
            fdsc[i] = dsc[i];
            fdsc[dsc.length] = StatCollector.translateToLocal("tooltip.bw.1.name");
        }
        return fdsc;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_TileEntity_MegaVacuumFreezer(this.mName);
    }

    public boolean drainEnergyInput(long aEU) {
        if (aEU <= 0)
            return true;
        long allTheEu = 0;
        int hatches = 0;
        for (GT_MetaTileEntity_Hatch_Energy tHatch : this.mEnergyHatches)
            if (GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity(tHatch)) {
                allTheEu += tHatch.getEUVar();
                hatches++;
            }
        if (allTheEu < aEU)
            return false;
        long euperhatch = aEU / hatches;
        HashSet<Boolean> returnset = new HashSet<Boolean>();
        for (GT_MetaTileEntity_Hatch_Energy tHatch : this.mEnergyHatches)
            if (tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(euperhatch, false))
                returnset.add(true);
            else
                returnset.add(false);
        return returnset.size() > 0 && !returnset.contains(false);
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        ItemStack[] tInputs = this.getStoredInputs().toArray(new ItemStack[0]);
        ArrayList<ItemStack> outputItems = new ArrayList<ItemStack>();

        long tVoltage = this.getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));

        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sVacuumRecipes.findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], null, tInputs);
        boolean found_Recipe = false;
        int processed = 0;
        long nominalV = getnominalVoltage(this);
        while (this.getStoredInputs().size() > 0 && processed < 16) { // 16 Параллель
            if (tRecipe != null && (tRecipe.mEUt * (processed + 1)) < nominalV && tRecipe.isRecipeInputEqual(true, null, tInputs)) {
                found_Recipe = true;
                for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                    outputItems.add(tRecipe.getOutput(i));
                }
                ++processed;
            } else
                break;
        }

        if (found_Recipe) {
            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
            this.mEfficiencyIncrease = 10000;
            long actualEUT = (long) (tRecipe.mEUt) * processed;
            if (actualEUT > Integer.MAX_VALUE) {
                byte divider = 0;
                while (actualEUT > Integer.MAX_VALUE) {
                    actualEUT = actualEUT / 2;
                    divider++;
                }
                calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)), tRecipe.mDuration * (divider * 2), 1, nominalV, this);
            } else
                calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
            //In case recipe is too OP for that machine
            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1)
                return false;
            if (this.mEUt > 0) {
                this.mEUt = (-this.mEUt);
            }
            this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
            this.mOutputItems = new ItemStack[outputItems.size()];
            this.mOutputItems = outputItems.toArray(this.mOutputItems);
            this.updateSlots();
            return true;
        }
        return false;
    }

    public static long getnominalVoltage(GT_MetaTileEntity_MultiBlockBase base) {
        long rVoltage = 0L;
        long rAmperage = 0L;

        for (GT_MetaTileEntity_Hatch_Energy tHatch : base.mEnergyHatches) {
            if (GT_MetaTileEntity_MultiBlockBase.isValidMetaTileEntity(tHatch)) {
                if (rVoltage == 0 || rVoltage > tHatch.getBaseMetaTileEntity().getInputVoltage())
                    rVoltage = tHatch.getBaseMetaTileEntity().getInputVoltage();
                rAmperage += tHatch.getBaseMetaTileEntity().getInputAmperage();
            }
        }

        return rVoltage * rAmperage;
    }

    public static void calculateOverclockedNessMulti( int aEUt,  int aDuration,  int mAmperage,  long maxInputVoltage, GT_MetaTileEntity_MultiBlockBase base) {
        byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
        if (mTier == 0) {
            //Long time calculation
            long xMaxProgresstime = ((long) aDuration) << 1;
            if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
                //make impossible if too long
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = aEUt >> 2;
                base.mMaxProgresstime = (int) xMaxProgresstime;
            }
        } else {
            //Long EUt calculation
            long xEUt = aEUt;
            //Isnt too low EUt check?
            long tempEUt = Math.max(xEUt, V[1]);

            base.mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier - 1] * mAmperage) {
                tempEUt <<= 2;//this actually controls overclocking
                //xEUt *= 4;//this is effect of everclocking
                base.mMaxProgresstime >>= 1;//this is effect of overclocking
                xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1 : xEUt << 2;//U know, if the time is less than 1 tick make the machine use less power
            }

            while (xEUt > maxInputVoltage) {
                //downclock one notch until we are good again, we have overshot.
                xEUt >>= 2;
                base.mMaxProgresstime <<= 1;
            }

            if (xEUt > Integer.MAX_VALUE - 1) {
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = (int) xEUt;
                if (base.mEUt == 0)
                    base.mEUt = 1;
                if (base.mMaxProgresstime <= 0)
                    base.mMaxProgresstime = 1;//set time to 1 tick
            }
        }
    }

    public static void calculateOverclockedNessMultiPefectOC( int aEUt,  int aDuration,  int mAmperage,  long maxInputVoltage, GT_MetaTileEntity_MultiBlockBase base) {
        byte mTier = (byte) Math.max(0, GT_Utility.getTier(maxInputVoltage));
        if (mTier == 0) {
            //Long time calculation
            long xMaxProgresstime = ((long) aDuration) << 1;
            if (xMaxProgresstime > Integer.MAX_VALUE - 1) {
                //make impossible if too long
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = aEUt >> 2;
                base.mMaxProgresstime = (int) xMaxProgresstime;
            }
        } else {
            long xEUt = aEUt;
            //Isnt too low EUt check?
            long tempEUt = Math.max(xEUt, V[1]);

            base.mMaxProgresstime = aDuration;

            while (tempEUt <= V[mTier - 1] * mAmperage) {
                tempEUt <<= 1;//this actually controls overclocking
                base.mMaxProgresstime >>= 1;//this is effect of overclocking
                xEUt = base.mMaxProgresstime <= 0 ? xEUt >> 1 : xEUt << 1;//U know, if the time is less than 1 tick make the machine use less power
            }

            while (xEUt > maxInputVoltage) {
                //downclock one notch until we are good again, we have overshot.
                xEUt >>= 1;
                base.mMaxProgresstime <<= 1;
            }
            if (xEUt > Integer.MAX_VALUE - 1) {
                base.mEUt = Integer.MAX_VALUE - 1;
                base.mMaxProgresstime = Integer.MAX_VALUE - 1;
            } else {
                base.mEUt = (int) xEUt;
                if (base.mEUt == 0)
                    base.mEUt = 1;
                if (base.mMaxProgresstime <= 0)
                    base.mMaxProgresstime = 1;//set time to 1 tick
            }
        }
    }
}