package com.impact.mods.gregtech;

import com.impact.loader.ItemRegistery;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ImpactTooltipChecks {
    private static final String ROOT = "com.impact.mods.gregtech.tileentities.multi.";

    static void check() throws Exception {
        ItemRegistery.CeramicBlock = Blocks.wool;
        String[][] dimensions = {
                { "generators.nuclear.GTMTE_NuclearReactorI", "5 x 4 x 5" },
                { "generators.nuclear.GTMTE_NuclearReactorII", "7 x 6 x 7" },
                { "generators.nuclear.GTMTE_NuclearReactorIII", "9 x 6 x 9" },
                { "storage.GTMTE_LapPowerStation", "5 x 4-18 x 5" },
                { "parallelsystem.GTMTE_ParallelComputer", "2 x 4 x 4-18" },
                { "processing.defaultmachines.GTMTE_ElectricImplosionCompressor", "7 x 3 x 3" },
                { "processing.parallel.GTMTE_AdvancedVacuumFreezer", "5 x 4 x 5" },
                { "processing.parallel.GTMTE_ElectromagneticInductionFurnace", "5 x 5 x 5" },
                { "processing.defaultmachines.GTMTE_SawMill", "3 x 3 x 5" },
                { "ores.GTMTE_AdvancedMiner", "7 x 14 x 7" },
                { "units.GTMTE_BasicWaterPump", "4 x 3 x 3" }
        };
        for (String[] entry : dimensions) {
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = machine(entry[0]);
            String structure = plain(machine.getSecondaryDescription());
            assertTrue(structure.contains(entry[1]), entry[0] + "\n" + structure);
            long maintenanceRows = Arrays.stream(machine.getSecondaryDescription())
                    .map(EnumChatFormatting::getTextWithoutFormattingCodes)
                    .filter(line -> line.trim().startsWith("Maintenance Hatch:")).count();
            assertTrue(maintenanceRows <= 1, entry[0] + " repeats maintenance hatch");
            checkSeparators(machine);
        }
        String pump = plain(machine("units.GTMTE_BasicWaterPump").getSecondaryDescription());
        assertTrue(pump.contains("10 Wood Frame Box"));
        assertTrue(pump.contains("Pump Output Hatch"));
        String miner = plain(machine("ores.GTMTE_AdvancedMiner").getSecondaryDescription());
        assertTrue(miner.contains("1 Miner Drill Hatch:"));

        String[] tiers = { "I", "II", "III" };
        int[] power = { 2048, 8192, 32768 };
        int[] oxygen = { 400, 1200, 2000 };
        int[] steam = { 28640, 57340, 229360 };
        for (int tier = 0; tier < tiers.length; tier++) {
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class.forName(ROOT + "generators.sofc.GTMTE_SOFC_" + tiers[tier])
                    .getConstructor(String.class, int.class).newInstance("tooltip-review.sofc." + tier, tier);
            String info = plain(machine.getPrimaryDescription());
            assertTrue(info.contains(power[tier] + " EU/t"), info);
            assertTrue(info.contains(oxygen[tier] + " L/s"), info);
            assertTrue(info.contains(steam[tier] + " L/s"), info);
            String structure = plain(machine.getSecondaryDescription());
            assertFalse(structure.contains("I/O Hatch:"));
            assertTrue(structure.contains("exactly 1"));
            assertTrue(structure.contains("at least 2 fluid inputs"));
        }
        for (String tank : new String[] { "storage.GTMTE_SingleTank", "storage.GTMTE_MultiTank" }) {
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = machine(tank);
            String info = plain(machine.getPrimaryDescription());
            assertTrue(info.contains("AE2"));
            assertFalse(info.contains("EC2"));
            checkSeparators(machine);
        }
        assertFalse(plain(machine("processing.parallel.GTMTE_FreezerSolidifier").getPrimaryDescription())
                .contains("Single-block machine equivalent"));
        assertFalse(plain(machine("generators.nq.GTMTE_LiquidNqGenerator").getSecondaryDescription())
                .toLowerCase().contains("muffler"));
    }

    private static GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine(String name) throws Exception {
        return (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class.forName(ROOT + name)
                .getConstructor(String.class).newInstance("tooltip-review." + name);
    }

    private static String plain(String[] lines) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(String.join("\n", lines));
    }

    private static void checkSeparators(GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine) {
        String[] info = machine.getPrimaryDescription();
        boolean previousSeparator = false;
        for (String line : info) {
            boolean separator = EnumChatFormatting.getTextWithoutFormattingCodes(line).matches("-{3,}");
            assertFalse(separator && previousSeparator, "Repeated separator");
            previousSeparator = separator;
        }
        assertEquals(info.length, machine.getPrimaryDescription().length);
    }
}
