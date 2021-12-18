package com.impact.mods.gregtech.enums;

import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines.GTMTE_RailAssembler;
import net.minecraft.item.ItemStack;

public class RecipeAdder implements IRecipeAdder {

    @Override
    public boolean addTrackAssemblerRecipe(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt) {
        GTMTE_RailAssembler.sTrackAssemblerRecipes.addRecipe(true, aInputs, new ItemStack[]{aOutput}, null,
                null, null, null, aDuration, aEUt, 0);
        return true;
    }

    @Override
    public boolean addDryingOvenRecipe(ItemStack aInput, ItemStack[] aOutputs, int aDuration, int aEUt) {
        GT_RecipeMaps.sDryingOven.addRecipe(true,
                new ItemStack[]{aInput}, aOutputs, null, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    @Override
    public boolean addMPContainer(ItemStack[] aInputs, ItemStack[] aOutputs, int aDuration, int aEUt, int aMPAmount) {
        GT_RecipeMaps.sMPContainer.addRecipe(true, aInputs, aOutputs, null,
                null, null, null, aDuration, aEUt, aMPAmount);
        return true;
    }

    @Override
    public boolean addMESPRecipes(ItemStack[] aInputs, ItemStack aOutput, int aDuration, int aEUt, int aMPAmount) {
        GT_RecipeMaps.sMESystemProvider.addRecipe(true, aInputs, new ItemStack[]{aOutput}, null,
                null, null, null, aDuration, aEUt, aMPAmount);
        return true;
    }
    
    @Override
    public boolean addTheMillRecipes(ItemStack aInput, ItemStack[] aOutput, int[] chance, int aDuration) {
        GT_RecipeMaps.sTheMill.addRecipe(true, new ItemStack[]{aInput}, aOutput, null,
                chance, null, null, aDuration, 0, 0);
        return true;
    }
}