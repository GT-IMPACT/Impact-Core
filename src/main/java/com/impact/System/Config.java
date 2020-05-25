package com.impact.System;

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

    public Config(File file) {
        if (!loadConfig) {
            config = new Configuration(file);
            syncConfig(true);
        }
    }

    public static void syncConfig(boolean load) {
        ArrayList<String> General = new ArrayList<String>();
        ArrayList<String> Debug = new ArrayList<String>();

        try {
            if (!config.isChild && load) {
                config.load();
            }

            Property cfg;
            //todo GENERAL
            //cfg = config.get("General", "Example", true);
            //cfg.comment = "Example. [Default: true]";
            //disableLogger = cfg.getBoolean(true);
            //General.add(cfg.getName());

            //todo DEBUG
            cfg = config.get("DEBUG", "disableLogger", true);
            cfg.comment = "Disabled Logger. [Default: true]";
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
