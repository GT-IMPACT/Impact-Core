package com.impact.core;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;

public class Config {
	
	public static boolean loadConfig;
	public static Configuration config;
	
	public static boolean disableLogger;
	public static boolean hideBackground;
	public static boolean toolTips;
	public static boolean DisableNether;
	public static boolean DisableTheEnd;
	public static boolean downloadOnlyOnce;
	public static boolean mainMenu;
	public static boolean placedItems;
	public static int saveTime;
	public static int MiningWorldID;
	
	public static int MAX_TICK_RATE;
	
	public Config(File file) {
		if (!loadConfig) {
			config = new Configuration(file);
			syncConfig(true);
		}
	}
	
	public static void syncConfig(boolean load) {
		ArrayList<String> General = new ArrayList<>();
		ArrayList<String> Debug = new ArrayList<>();
		
		try {
			if (!config.isChild && load) {
				config.load();
			}
			
			Property cfg;
			//GENERAL
			cfg            = config.get("GENERAL", "Hide Background", true);
			cfg.comment    = "[NEI Ore Plugin] Hides the Background when the tooltip for the Dimensions is renderedr. [Default: true]";
			hideBackground = cfg.getBoolean(true);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "DimTooltip", true);
			cfg.comment = "[NEI Ore Plugin] Activates Dimensison Tooltips. [Default: true]";
			toolTips    = cfg.getBoolean(true);
			General.add(cfg.getName());
			
			cfg           = config.get("GENERAL", "Disable Nether", false);
			cfg.comment   = "Disable Nether. [Default: false]";
			DisableNether = cfg.getBoolean(false);
			General.add(cfg.getName());
			
			cfg           = config.get("GENERAL", "Disable The End", false);
			cfg.comment   = "Disable The End. [Default: false]";
			DisableTheEnd = cfg.getBoolean(false);
			General.add(cfg.getName());
			
			cfg              = config.get("GENERAL", "Download Mods Only Once", true);
			cfg.comment      = "Download Mods Only Once. [Default: true]";
			downloadOnlyOnce = cfg.getBoolean(true);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "Enable Impact Main Menu", true);
			cfg.comment = "Enable Impact Main Menu. [Default: true]";
			mainMenu    = cfg.getBoolean(true);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "Enable Impact Placed Items", true);
			cfg.comment = "Enable Impact Placed Items. [Default: true]";
			placedItems = cfg.getBoolean(true);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "Impact saves files timer", 30);
			cfg.comment = "SaveTimer. [Default: 30min]";
			saveTime = cfg.getInt(30);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "Mining World (Copy OverWorld)", 45);
			cfg.comment = "MiningWorldID. [Default: 45]";
			MiningWorldID = cfg.getInt(45);
			General.add(cfg.getName());
			
			cfg         = config.get("GENERAL", "Max tickrate multiblocks (only IMPACT)", 2);
			cfg.comment = "MaxTickRate. [Default: 2]";
			MAX_TICK_RATE = cfg.getInt(2);
			General.add(cfg.getName());
			
			//DEBUG
			cfg           = config.get("DEBUG", "disableLogger", true);
			cfg.comment   = "Disabled Logger. [Default: true]";
			disableLogger = cfg.getBoolean(true);
			Debug.add(cfg.getName());
			
			config.setCategoryPropertyOrder("GENERAL", General);
			config.setCategoryPropertyOrder("DEBUG", Debug);
			
			if (config.hasChanged()) {
				config.save();
			}
			
			FMLLog.log(Level.INFO, "[IMPACT] Logger: " + disableLogger);
			
		} catch (Exception e) {
			FMLLog.log(Level.ERROR, e, "[IMPACT] Error load config!");
		}
	}
}