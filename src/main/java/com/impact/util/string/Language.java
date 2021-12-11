package com.impact.util.string;

import gregtech.api.util.GT_LanguageManager;

import static com.impact.util.string.Lang.*;

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
	
	public static String langGetEUTick(String tick) {
		return String.format(get_eu_t.get(), tick);
	}
}