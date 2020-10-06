package com.impact.mods.ASP.client;


import codechicken.nei.api.IConfigureNEI;

public class NEIASPConfig implements IConfigureNEI {
    public NEIASPConfig() {
    }

    public void loadConfig() {
        System.out.println("[Advanced Solar Panels]: Loading NEI compatibility...");
    }

    public String getName() {
        return "Advanced Solar Panels";
    }

    public String getVersion() {
        return "v1.0";
    }
}
