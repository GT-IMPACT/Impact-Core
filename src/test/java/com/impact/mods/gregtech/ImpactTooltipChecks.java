package com.impact.mods.gregtech;

import com.impact.loader.ItemRegistery;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import java.util.Arrays;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import gregtech.api.util.GTStructureChannels;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TooltipMultiBlockBase;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ImpactTooltipChecks {
    private static final String ROOT = "com.impact.mods.gregtech.tileentities.multi.";

    static void check() throws Exception {
        checkChannelTranslations();
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

    private static void checkChannelTranslations() throws Exception {
        GT_MetaTileEntity_EnhancedMultiBlockBase<?> lap = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class.forName(ROOT + "storage.GTMTE_LapPowerStation")
                .getConstructor(String.class).newInstance("impact.multimachine.supercapacitor");
        for (String language : new String[] { "ru_RU", "en_US" }) {
            loadLanguage(language);
            GT_MetaTileEntity_TooltipMultiBlockBase.invalidateTooltipCache();
            String header = StatCollector.translateToLocal("gt.tooltip.channels");
            assertEquals(language.equals("ru_RU") ? "Каналы структуры" : "Structure channels", header);
            for (String[] lines : new String[][] { lap.getSecondaryDescription(), lap.getStructureDescription(null) }) {
                String description = plain(lines);
                int section = description.indexOf(header);
                assertTrue(section > description.indexOf("I-Glass"), language);
                assertEquals(1, Arrays.stream(lines)
                        .filter(line -> EnumChatFormatting.getTextWithoutFormattingCodes(line).equals(header)).count());
                assertTrue(String.join("\n", lines).contains(EnumChatFormatting.AQUA
                        + GTStructureChannels.getLabel(GTStructureChannels.TIER) + EnumChatFormatting.DARK_GRAY));
                assertEquals(1, Arrays.stream(lines).filter(line -> line.contains("(tier)")).count());
                for (int key = 1; key <= 3; key++) {
                    String text = StatCollector.translateToLocal("TileEntity_DESCRIPTION_impact.multimachine.supercapacitor_Channel_Index_" + key);
                    assertTrue(description.indexOf(text) > section, language + " misplaced " + key);
                }
            }
            assertFalse(plain(lap.getPrimaryDescription()).contains(header));
            String capacitorDescription = plain(lap.getSecondaryDescription());
            assertTrue(capacitorDescription.contains("5: IV"), language);
            assertFalse(capacitorDescription.contains("Ultimate"), language);
            for (String tank : new String[] { "Single", "Multi" }) {
                String registeredName = "impact.multimachine." + tank.toLowerCase() + "fluidtank";
                GT_MetaTileEntity_EnhancedMultiBlockBase<?> machine = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class
                        .forName(ROOT + "storage.GTMTE_" + tank + "Tank").getConstructor(String.class)
                        .newInstance(registeredName);
                String key = "TileEntity_DESCRIPTION_" + registeredName + "_Channel_Index_1";
                String dimensions = tank.equals("Single") ? "3 x 3 x 7" : "5 x 5 x 9";
                assertTrue(plain(machine.getSecondaryDescription()).replace('×', 'x').contains(dimensions), "Tank dimensions " + tank);
                assertTrue(StatCollector.canTranslate(key), language + " tank tier translation");
                String hatchLimitKey = "TileEntity_DESCRIPTION_" + registeredName + "_Location_Index_3";
                assertTrue(StatCollector.canTranslate(hatchLimitKey), language + " tank hatch limit translation");
                assertTrue(plain(machine.getSecondaryDescription()).contains(StatCollector.translateToLocal(hatchLimitKey)));
                for (String[] lines : new String[][] { machine.getSecondaryDescription(), machine.getStructureDescription(null) }) {
                    String description = plain(lines);
                    assertTrue(description.contains("(tier)"));
                    assertTrue(description.indexOf(StatCollector.translateToLocal(key)) > description.indexOf(header));
                }
            }
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> mill = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class
                    .forName(ROOT + "processing.defaultmachines.GTMTE_TheMill").getConstructor(String.class)
                    .newInstance("impact.multis.the_mill");
            String clearanceKey = "TileEntity_DESCRIPTION_impact.multis.the_mill_Structure_Index_0";
            assertTrue(StatCollector.canTranslate(clearanceKey), language + " mill clearance translation");
            assertTrue(plain(mill.getSecondaryDescription()).contains(StatCollector.translateToLocal(clearanceKey)), language);
            GT_MetaTileEntity_EnhancedMultiBlockBase<?> computer = (GT_MetaTileEntity_EnhancedMultiBlockBase<?>) Class.forName(ROOT + "parallelsystem.GTMTE_ParallelComputer")
                    .getConstructor(String.class).newInstance("impact.multis.parallelcomputer");
            String computerStructure = plain(computer.getSecondaryDescription());
            for (String section : new String[] { "Part", "Location", "Hint" }) {
                int first = section.equals("Part") ? 2 : section.equals("Location") ? 3 : 1;
                int last = section.equals("Part") ? 4 : section.equals("Location") ? 5 : 2;
                for (int index = first; index <= last; index++) {
                    String key = "TileEntity_DESCRIPTION_impact.multis.parallelcomputer_" + section + "_Index_" + index;
                    assertTrue(computerStructure.contains(StatCollector.translateToLocal(key)), language + " missing " + key);
                }
            }
        }
    }

    static void loadLanguage(String language) throws Exception {
        for (String mod : new String[] { "gregtech", "impact" }) {
            // Addons can contribute another file under the same language resource path.
            Enumeration<URL> resources = ImpactTooltipChecks.class.getClassLoader()
                    .getResources("assets/" + mod + "/lang/" + language + ".lang");
            assertTrue(resources.hasMoreElements(), mod + " missing language " + language);
            while (resources.hasMoreElements()) {
                try (InputStream stream = resources.nextElement().openStream()) {
                    StringTranslate.inject(stream);
                }
            }
        }
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
