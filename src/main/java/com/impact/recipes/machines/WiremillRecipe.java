package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

public class WiremillRecipe implements Runnable {



  public void run() {
    GT_Values.RA
        .addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.RedAlloy, 1L),
            GT_ModHandler.getModItem("ProjRed|Transmission", "projectred.transmission.wire", 4L, 0),
            400, 2);
  }
}
