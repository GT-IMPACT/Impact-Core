package com.impact.recipes.machines;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;

public class FusionRecipe implements Runnable {

  @Override
  public void run() {
    GT_Values.RA
        .addFusionReactorRecipe(Materials.Tartarite.getFluid(16), Materials.Europium.getMolten(16),
            Materials.DraconiumPlasma.getFluid(16), 32, 32768, 300000000);
    GT_Values.RA.addFusionReactorRecipe(Materials.EnrichedTartarite.getFluid(16),
        Materials.Neutronium.getMolten(16), Materials.DraconiumAwakenedPlasma.getFluid(16), 64,
        262144, 1000000000);

  }
}
