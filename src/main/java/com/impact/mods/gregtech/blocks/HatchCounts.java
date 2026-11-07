package com.impact.mods.gregtech.blocks;

import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;

/** Counts physical hatch variants against the same machine limits. */
public final class HatchCounts {

    private HatchCounts() {
    }

    public static int inputBuses(GT_MetaTileEntity_MultiBlockBase machine) {
        return machine.mInputBusses.size() + machine.mInputBusHatches.size();
    }

    public static int inputHatches(GT_MetaTileEntity_MultiBlockBase machine) {
        // A combined bus/hatch occupies one position but uses both input allowances.
        return machine.mInputHatches.size() + machine.mInputBusHatches.size();
    }

    public static int energyHatches(GT_MetaTileEntity_MultiBlockBase machine) {
        int lasers = machine instanceof GTMTE_Impact_BlockBase ? ((GTMTE_Impact_BlockBase<?>) machine).mLaserIn.size() : 0;
        return machine.mEnergyHatches.size() + machine.mEnergyHatchesMulti.size() + lasers;
    }

    public static int dynamoHatches(GT_MetaTileEntity_MultiBlockBase machine) {
        int lasers = machine instanceof GTMTE_Impact_BlockBase ? ((GTMTE_Impact_BlockBase<?>) machine).mLaserOut.size() : 0;
        return machine.mDynamoHatches.size() + machine.mDynamoHatchesMulti.size() + lasers;
    }
    /** Applies the input tier floor to regular, combined, multi-amp and laser inputs. */
    public static boolean inputsAtLeastTier(GTMTE_Impact_BlockBase<?> machine, int minimum) {
        return machine.mEnergyHatches.stream().allMatch(hatch -> hatch.mTier >= minimum)
                && machine.mEnergyHatchesMulti.stream().allMatch(hatch -> hatch.mTier >= minimum)
                && machine.mLaserIn.stream().allMatch(hatch -> hatch.mTier >= minimum)
                && machine.mInputHatches.stream().allMatch(hatch -> hatch.mTier >= minimum)
                && machine.mInputBusses.stream().allMatch(hatch -> hatch.mTier >= minimum)
                && machine.mInputBusHatches.stream().allMatch(hatch -> hatch.mTier >= minimum);
    }

}
