package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;

import static com.impact.common.item.Core_List_Items.TCetiESeaweedExtract;

public class ExtractorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        for (byte i = 0; i < 6; ++i)
            GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("GalaxySpace", "tcetiedandelions", 64L, i), CoreItems.getRecipe(TCetiESeaweedExtract.getMetaID(), 1), 400, 262144);

        GT_Values.RA.addExtractorRecipe(CoreItems2.getRecipe(151, 64), CoreItems.getRecipe(35, 1), 400, 262144);
        GT_Values.RA.addExtractorRecipe(ItemList.Casing_CokeOvenBrick.get(1L), CoreItems2.getRecipe(65, 4), 300, 2);
        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("TConstruct", "Smeltery", 1L, 2), GT_ModHandler.getModItem("TConstruct", "materials", 4L, 2), 300, 2);

        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 1), ItemList.Energy_LapotronicOrb.get(1L), 200, 7680);
        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 2), ItemList.Energy_LapotronicOrb2.get(1L), 300, 30720);
        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 3), ItemList.Energy_Module.get(1L), 400, 122880);
        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 4), ItemList.Energy_Cluster.get(1L), 500, 500000);
        GT_Values.RA.addExtractorRecipe(GT_ModHandler.getModItem("impact", "impact_lapotronicenergyunit_block", 1, 5), ItemList.ZPM2.get(1L), 600, 2000000);

    }
}
