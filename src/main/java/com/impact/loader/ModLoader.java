package com.impact.loader;

import com.impact.item.Core_Items;
import com.impact.mods.GregTech.GTregister.TecTech_BuildGuide_GregTech;
import com.impact.mods.GregTech.GTregister.TecTech_BuildGuide_Impact;
import com.impact.recipes.TecTechRecipe;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.impact.item.Core_List_Items.TCetiESeaweedExtract;

public class ModLoader implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    @Override
    public void run() {

        // --- TecTech
        if (Loader.isModLoaded("tectech")) {
            new TecTechRecipe().run();
            new TecTech_BuildGuide_Impact().run();
            new TecTech_BuildGuide_GregTech().run();
        }

    }
}
