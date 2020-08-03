package com.impact.recipes.machines;

import com.impact.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;


public class CuttingRecipe implements Runnable {
    final Core_Items2 CoreItems2 = Core_Items2.getInstance();
    @Override
    public void run() {

        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot10.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer10.get(64), ItemList.Circuit_Silicon_Wafer10.get(64), 6400, 122880, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot9.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer9.get(40), null, 1000, 240, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot8.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer8.get(64), ItemList.Circuit_Silicon_Wafer8.get(64), 3200, 7680, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot7.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer7.get(64), ItemList.Circuit_Silicon_Wafer7.get(32), 2400, 1920, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot6.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer6.get(64), ItemList.Circuit_Silicon_Wafer6.get(64), 4800, 122880, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot5.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer5.get(64), null, 3700, 7680, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot4.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer4.get(48), null, 1200, 240, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot3.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer3.get(64), null, 1600, 480, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot2.get(1), GT_Values.NI, ItemList.Circuit_Silicon_Wafer2.get(32), null, 800, 120, true);
        GT_Values.RA.addCutterRecipe(ItemList.Circuit_Silicon_Ingot.get(1), ItemList.Circuit_Silicon_Wafer.get(16), null, 400, 16);

        // --- Carpenter's Button
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersPressurePlate", 1L, 0), GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersButton", 4L, 0), GT_Values.NI, 100, 30);
        // --- Carpenter's Stairs
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSlope", 1L, 0), GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersStairs", 1L, 0), GT_Values.NI, 100, 30);
        // --- Carpenter's Slope
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersBlock", 1L, 0), GT_ModHandler.getModItem("CarpentersBlocks", "blockCarpentersSlope", 4L, 0), GT_Values.NI, 100, 30);

        // --- Frame Sheet
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("gregtech", "gt.metaitem.02", 1L, 32470), GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 4L, 0), GT_Values.NI, 50, 4);
        // --- Frame Board
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("BiblioCraft", "item.FramingSheet", 1L, 0), GT_ModHandler.getModItem("BiblioCraft", "item.FramingBoard", 4L, 0), GT_Values.NI,  50, 4);

        GT_Values.RA.addCutterRecipe(CoreItems2.getRecipe(158, 1), GT_ModHandler.getModItem("OpenComputers", "item", 16L, 28), null, 400, 16);
        GT_Values.RA.addCutterRecipe(CoreItems2.getRecipe(157, 1), GT_ModHandler.getModItem("OpenComputers", "item", 16L, 27), null, 400, 16);

        /** ==== START ZTONES ==== */
        // --- Flat Lamp light
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 16), GT_ModHandler.getModItem("Ztones", "lampf", 4L, 0), GT_Values.NI,  200, 4);
        // --- Flat Lamp white
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 24), GT_ModHandler.getModItem("Ztones", "lampt", 4L, 0), GT_Values.NI,  200, 4);
        // --- Flat Lamp black
        GT_Values.RA.addCutterRecipe(GT_ModHandler.getModItem("ProjRed|Illumination", "projectred.illumination.lamp", 1L, 23), GT_ModHandler.getModItem("Ztones", "lampb", 4L, 0), GT_Values.NI,  200, 4);

        /* ==== END ZTONES ==== */
    }
}
