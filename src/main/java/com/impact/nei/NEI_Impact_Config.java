package com.impact.nei;

import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.util.GT_Recipe;

public class NEI_Impact_Config implements IConfigureNEI {

    public static boolean sIsAdded = true;

    @Override
    public void loadConfig() {
        sIsAdded = false;

        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            new GT_NEI_NaquadahGen(GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator);
        }

        sIsAdded = true;
    }

    @Override
    public String getName() {
        return "Impact NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "(5.03a)";
    }
}