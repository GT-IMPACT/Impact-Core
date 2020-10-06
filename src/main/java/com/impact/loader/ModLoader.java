package com.impact.loader;

import com.impact.common.item.Core_Items;
import com.impact.mods.GregTech.TecTech.Holo_Vanila_GregTech;
import com.impact.mods.GregTech.TecTech.Holo_Impact;
import com.impact.recipes.TecTechRecipe;
import cpw.mods.fml.common.Loader;

public class ModLoader implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    @Override
    public void run() {

        // --- TecTech
        if (Loader.isModLoaded("tectech")) {
            new TecTechRecipe().run();
            new Holo_Impact().run();
            new Holo_Vanila_GregTech().run();
        }

    }
}
