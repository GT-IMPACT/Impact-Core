package com.impact.util;

import gregtech.api.util.GT_LanguageManager;

public class Language {

    // Language.translate(aKey, aEnglish)

    public static String translateImpact(String aKey, String aEnglish, boolean writeFile) {
        return GT_LanguageManager.addStringLocalization("impact_" + aKey, aEnglish, writeFile);
    }

    public static String translate(String aKey, String aEnglish) {
        return translateImpact(aKey, aEnglish, true);
    }

    public static String translate(String aKey, String aEnglish, boolean writeFile) {
        return translateImpact(aKey, aEnglish, writeFile);
    }

    public static String transDesc(String aKey, String aEnglish) {
        return translateImpact("description_" + aKey, aEnglish, true);
    }

    public static String transMTB(String aKey, String aEnglish) {
        return translateImpact("tooltip_mtb_" + aKey, aEnglish, true);
    }

    public static String transMTB(String aKey, String aEnglish, boolean writeFile) {
        return translateImpact("tooltip_mtb_" + aKey, aEnglish, writeFile);
    }

    public static void registerStaticLanguage() {
        transMTB("multi_amperes_generator", "Multi-Amperes generator");
        transMTB("parallel.point.basic", "basic");
        transMTB("parallel.point", "Parallel Point");
        transMTB("per_second", "per second");
        transMTB("pollution", "Pollution");
        transMTB("screw_type", "Select type machine with Screwdriver click in Controller");
        transMTB("screw_type_different", "with Screwdriver click in Controller");
        transMTB("select", "Select");
        transMTB("separated_bus", "Select Separated Buses mod with Shift + Screwdriver click in Controller");
        transMTB("parallel.hatch", "Parallel Hatch");
        transMTB("begin_structure_block", "Description of the structure in development");
        transMTB("structure", "Structure");
        transMTB("blueprint.0", "Follow the");
        transMTB("blueprint.1", "blueprint to build the main structure");
        transMTB("at_least", "at least");
        transMTB("hatch.energy", "Energy Hatch");
        transMTB("hatch.dynamo", "Dynamo Hatch");
        transMTB("hatch.muffler", "Muffler Hatch");
        transMTB("hatch.maintenance", "Maintenance Hatch");
        transMTB("hatch.io", "I/O Hatch");
        transMTB("hatch.nuclear", "Nuclear Rod Hatch");
        transMTB("hatch.bus.in", "Input Bus");
        transMTB("hatch.in", "Input Hatch");
        transMTB("hatch.bus.out", "Output Bus");
        transMTB("hatch.out", "Output Hatch");
        transMTB("max", "max");
        transMTB("min", "min");
        transMTB("to_structure", "to structure");
        transMTB("to_more_info", "to more info");
        transMTB("left_ctrl", "LCTRL");
        transMTB("left_shift", "LSHIFT");
        transMTB("hold", "Hold");
        transMTB("any_case", "Any casing");
        transMTB("get_eu_t", "Outputs %s EU/t");
        transMTB("generator", "Generator");
        transMTB("steam_producer", "Steam Producer");
        transMTB("single_analog", "One-block machine analog");
    }

    public static String langGetEUTick(String tick) {
        return String.format(transMTB("get_eu_t", "Outputs %s EU/t", false), tick);
    }
}