package com.impact.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ALL")
public class impactLog {
	
	// Logging Functions
	public static final Logger modLog = impactLog.makeLogger();
	private static final boolean enabled = !Config.disableLogger;
	
	public impactLog(String string) {
	}
	
	// [IMPACT] Logger
	public static Logger makeLogger() {
		final Logger impactLogger = LogManager.getLogger("IMPACT");
		return impactLogger;
	}
	
	public static final Logger getLogger() {
		return modLog;
	}
	
	public static void INFO(final String s) {
		if (enabled) {
			modLog.info(s);
		}
	}
	
	public static void WARNING(final String s) {
		if (enabled) {
			modLog.warn(s);
		}
	}
	
	public static void ERROR(final String s) {
		if (enabled) {
			modLog.fatal(s);
		}
	}
}
