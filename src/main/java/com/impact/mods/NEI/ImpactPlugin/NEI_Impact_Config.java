package com.impact.mods.nei.impactplugin;

import codechicken.nei.api.IConfigureNEI;
import com.impact.mods.GregTech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Optional;
import gregtech.api.util.GT_Recipe;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEI_Impact_Config implements IConfigureNEI {

  public static boolean sIsAdded = true;

  @Optional.Method(modid = "NotEnoughItems")
  public String getName() {
    return "Impact NEI Plugin";
  }

  @Optional.Method(modid = "NotEnoughItems")
  public String getVersion() {
    return "1.0";
  }

  @Override
  public void loadConfig() {
    sIsAdded = false;

    if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
      new GT_NEI_NaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator);
      new GT_NEI_EnrichedNaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator);
      new GT_NEI_HyperGen(GT_Recipe.GT_Recipe_Map.sHyperGenerator);
      new GT_NEI_HeavyMetalCyclone(GT_Recipe.GT_Recipe_Map.sCyclonRecipes);

      new NEI_MoonMiner();
      new NEI_RocketFuelSingle();
      //new NEI_OxygenSuppSingle();
      new NEI_Impact_RailAssembler(GTMTE_RailAssembler.sTrackAssemblerRecipes);
    }

    sIsAdded = true;
  }
}
