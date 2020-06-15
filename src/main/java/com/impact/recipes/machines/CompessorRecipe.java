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
import net.minecraft.item.ItemStack;

import static com.impact.item.Core_List_Items.CokeOvenBrick;
import static com.impact.loader.ItemRegistery.decorateBlock;
import static com.impact.util.Utilits.Blockstack;


public class CompessorRecipe implements Runnable {

    final Core_Items CoreItems = Core_Items.getInstance();
    final Core_Items2 CoreItems2 = Core_Items2.getInstance(); //компоненты

    public void run() {
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(CokeOvenBrick.getMetaID(), 4), ItemList.Casing_CokeOvenBrick.get(1L), 200, 8);
        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CokeCoal, 9L), Blockstack(decorateBlock[0], 1, 1), 200, 8);
        GT_Values.RA.addCompressorRecipe(CoreItems.getRecipe(33, 1), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L), 200, 2);
        GT_Values.RA.addCompressorRecipe(CoreItems2.getRecipe(125, 9), CoreItems2.getRecipe(126, 1), 400, 1920);

        GT_Values.RA.addCompressorRecipe(GT_ModHandler.getModItem("GalaxySpace", "barnardaCsapling", 8L), CoreItems2.getRecipe(151, 1), 200, 2000000);

        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 1),  Blockstack(decorateBlock[0], 1, 11), 100, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 11), Blockstack(decorateBlock[0], 1, 12), 100, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 12), Blockstack(decorateBlock[0], 1, 13), 100, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 13), Blockstack(decorateBlock[0], 1, 14), 100, 1920);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Charcoal, 9L), Blockstack(decorateBlock[0], 1, 3), 120, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 3), Blockstack(decorateBlock[0], 1, 4), 120, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 4), Blockstack(decorateBlock[0], 1, 5), 120, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 5), Blockstack(decorateBlock[0], 1, 6), 120, 1920);

        GT_Values.RA.addCompressorRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Coal, 9L), Blockstack(decorateBlock[0], 1, 7), 110, 20);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 7), Blockstack(decorateBlock[0], 1, 8), 110, 120);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 8), Blockstack(decorateBlock[0], 1, 9), 110, 480);
        GT_Values.RA.addCompressorRecipe(Blockstack(decorateBlock[0], 9, 9), Blockstack(decorateBlock[0], 1, 10), 110, 1920);


    }
}
