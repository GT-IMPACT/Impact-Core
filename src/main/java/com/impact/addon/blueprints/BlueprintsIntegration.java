package com.impact.addon.blueprints;

import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ComputerRack;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Input;
import com.impact.mods.gregtech.tileentities.multi.parallelsystem.GTMTE_ParallelHatch_Output;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_DynamoMulti;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_EnergyMulti;
import gregtech.api.util.GTStructureUtility;
import java.util.function.ToIntFunction;
import space.impact.iblueprints.api.SceneAlternativeGroups;

public final class BlueprintsIntegration {

    private BlueprintsIntegration() {
    }

    public static void register() {
        // Laser hatches extend the multi-amp classes; their more specific groups take priority.
        for (int amperage : new int[] { 256, 1024, 4096, 16384, 65536, 262144, 1048576 }) {
            registerAmperageGroup(
                    "impact.blueprints.group.laser_energy_input." + amperage,
                    GTMTE_LaserEnergy_In.class,
                    amperage,
                    hatch -> hatch.Amp
            );
            registerAmperageGroup(
                    "impact.blueprints.group.laser_energy_output." + amperage,
                    GTMTE_LaserEnergy_Out.class,
                    amperage,
                    hatch -> hatch.Amp
            );
        }
        for (int amperage : new int[] { 2, 4, 16, 64 }) {
            registerAmperageGroup(
                    "impact.blueprints.group.multi_energy_input." + amperage,
                    GT_MetaTileEntity_Hatch_EnergyMulti.class,
                    amperage,
                    hatch -> hatch.Amp
            );
            registerAmperageGroup(
                    "impact.blueprints.group.multi_energy_output." + amperage,
                    GT_MetaTileEntity_Hatch_DynamoMulti.class,
                    amperage,
                    hatch -> hatch.Amp
            );
        }
        SceneAlternativeGroups.register(
                "impact.blueprints.group.racks",
                stack -> GTStructureUtility.getMetaTileEntity(stack) instanceof GTMTE_ComputerRack
        );
        SceneAlternativeGroups.register(
                "impact.blueprints.group.parallel_transmitters",
                stack -> GTStructureUtility.getMetaTileEntity(stack) instanceof GTMTE_ParallelHatch_Output
        );
        SceneAlternativeGroups.register(
                "impact.blueprints.group.parallel_receivers",
                stack -> GTStructureUtility.getMetaTileEntity(stack) instanceof GTMTE_ParallelHatch_Input
        );
    }

    private static <T extends IMetaTileEntity> void registerAmperageGroup(
            String translationKey,
            Class<T> hatchType,
            int amperage,
            ToIntFunction<T> hatchAmperage
    ) {
        SceneAlternativeGroups.register(translationKey, stack -> {
            IMetaTileEntity hatch = GTStructureUtility.getMetaTileEntity(stack);
            return hatchType.isInstance(hatch) && hatchAmperage.applyAsInt(hatchType.cast(hatch)) == amperage;
        });
    }
}
