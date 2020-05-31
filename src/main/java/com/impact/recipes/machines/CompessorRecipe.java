package com.impact.recipes.machines;

import com.impact.item.Core_Items;
import com.impact.item.Core_Items2;
import com.impact.mods.GregTech.GTregister.GT_ItemList;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

import static com.impact.item.Core_List_Items.CokeOvenBrick;


public class CompessorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance(); //компоненты

    public void run() {
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 4), ItemList.Casing_CokeOvenBrick.get(1L), 200, 8);
        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CokeCoal, 9L), GregTech_API.getStackofAmountFromOreDict("blockCokeCoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(CoreItems.getRecipe(33, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L), 200, 2);
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(125, 9), CoreItems2.getRecipe(126, 1), 400, 1920);

        GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("GalaxySpace", "barnardaCsapling", 8L), CoreItems2.getRecipe(151, 1), 200, 2000000);

        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("blockCokeCoal", 9), GregTech_API.getStackofAmountFromOreDict("CompressedCoalCoke", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("CompressedCoalCoke", 9), GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCoalCoke", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCoalCoke", 9), GregTech_API.getStackofAmountFromOreDict("TripleCompressedCoalCoke", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("TripleCompressedCoalCoke", 9), GregTech_API.getStackofAmountFromOreDict("QuadrupleCompressedCoalCoke", 1), 200, 8);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 9L), GregTech_API.getStackofAmountFromOreDict("CompressedCharcoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("CompressedCharcoal", 9), GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCharcoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCharcoal", 9), GregTech_API.getStackofAmountFromOreDict("TripleCompressedCharcoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("TripleCompressedCharcoal", 9), GregTech_API.getStackofAmountFromOreDict("QuadrupleCompressedCharcoal", 1), 200, 8);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 9L), GregTech_API.getStackofAmountFromOreDict("CompressedCoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("CompressedCoal", 9), GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("DoubleCompressedCoal", 9), GregTech_API.getStackofAmountFromOreDict("TripleCompressedCoal", 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(GregTech_API.getStackofAmountFromOreDict("TripleCompressedCoal", 9), GregTech_API.getStackofAmountFromOreDict("QuadrupleCompressedCoal", 1), 200, 8);


    }
}
