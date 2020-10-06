package com.impact.recipes.machines;

import com.impact.common.item.Core_Items;
import com.impact.common.item.Core_Items2;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;

import static com.impact.common.item.Core_List_Items.CokeOvenBrick;
import static com.impact.loader.ItemRegistery.CoalBlock;
import static com.impact.util.Utilits.Blockstack;


public class CompessorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance(); //компоненты

    public void run() {
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 4), ItemList.Casing_CokeOvenBrick.get(1L), 200, 8);
        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CokeCoal, 9L), Blockstack(CoalBlock, 1, 0), 200, 8);
        GT_Values.RA.addCompressorRecipe(CoreItems.getRecipe(33, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L), 200, 2);
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(125, 9), CoreItems2.getRecipe(126, 1), 400, 1920);

        GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("GalaxySpace", "barnardaCsapling", 8L), CoreItems2.getRecipe(151, 1), 200, 8);

        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 0),  Blockstack(CoalBlock, 1, 1), 100, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 1), Blockstack(CoalBlock, 1, 2), 100, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 2), Blockstack(CoalBlock, 1, 3), 100, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 3), Blockstack(CoalBlock, 1, 4), 100, 1920);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 9L), Blockstack(CoalBlock, 1, 5), 120, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 5), Blockstack(CoalBlock, 1, 6), 120, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 6), Blockstack(CoalBlock, 1, 7), 120, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 7), Blockstack(CoalBlock, 1, 8), 120, 1920);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 9L), Blockstack(CoalBlock, 1, 9), 110, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 9), Blockstack(CoalBlock, 1, 10), 110, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 10), Blockstack(CoalBlock, 1, 11), 110, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(CoalBlock, 9, 11), Blockstack(CoalBlock, 1, 12), 110, 1920);


    }
}
