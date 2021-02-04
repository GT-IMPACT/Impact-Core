package com.impact.mods.NEI.OrePugin;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIPluginConfig implements IConfigureNEI {

  @Override
  public String getName() {
    return "GregTech Ore Plugin";
  }

  @Override
  public String getVersion() {
    return "1.0";
  }

  @Override
  public void loadConfig() {
    PluginGT5VeinStat pluginVeinStat = new PluginGT5VeinStat();
    PluginGT5SmallOreStat pluginSmallOreStat = new PluginGT5SmallOreStat();
    API.registerRecipeHandler(pluginVeinStat);
    API.registerUsageHandler(pluginVeinStat);
    API.registerRecipeHandler(pluginSmallOreStat);
    API.registerUsageHandler(pluginSmallOreStat);
  }
}
