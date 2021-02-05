package com.impact.recipes.machines;

import static com.impact.common.item.Core_List_Items.TCetiESeaweedExtract;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;

public class ExtractorRecipe implements Runnable {

  final Core_Items CoreItems = Core_Items.getInstance();
  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {
    for (byte i = 0; i < 6; ++i) {
      GT_Values.RA
          .addExtractorRecipe(GT_ModHandler.getModItem("GalaxySpace", "tcetiedandelions", 64L, i),
              CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1), 400, 262144);
    }

    GT_Values.RA
        .addExtractorRecipe(CoreItems2.getRecipe(151, 64), CoreItems.getRecipe(35, 1), 400, 262144);
    GT_Values.RA
        .addExtractorRecipe(ItemList.Casing_CokeOvenBrick.get(1L), CoreItems2.getRecipe(65, 4), 300,
            2);
    GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2),
        GT_ModHandler.getModItem("TConstruct", "materials", 4L, 2), 300, 2);
    GT_Values.RA
        .addExtractorRecipe(GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L),
            GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 5L, 7), 300, 2);
    GT_Values.RA.addExtractorRecipe(
        GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 1),
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 8L, 7), 300, 2);
    GT_Values.RA.addExtractorRecipe(
        GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 2),
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 7L, 7), 300, 2);
    GT_Values.RA.addExtractorRecipe(
        GT_ModHandler.getModItem("GalacticraftMars", "item.thermalPadding", 1L, 3),
        GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 4L, 7), 300, 2);

  }
}