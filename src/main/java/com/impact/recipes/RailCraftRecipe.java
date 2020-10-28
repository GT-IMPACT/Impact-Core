package com.impact.recipes;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import mods.railcraft.common.blocks.tracks.EnumTrack;
import mods.railcraft.common.items.ItemRail;
import mods.railcraft.common.items.ItemRailbed;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.removeRecipeByOutput;

public class RailCraftRecipe implements Runnable {

    private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.BUFFERED;


    public void delRecipe() {
        removeRecipeByOutput(EnumTrack.CONTROL.getItem(), true, false, false);

    }

    public void handRecipe() {
        // --- Control Track
        GT_ModHandler.addCraftingRecipe(EnumTrack.CONTROL.getItem(8), bits, new Object[]{"SRS", "ITI", "dRh", 'S', OrePrefixes.screw.get(Materials.Steel), 'R', OrePrefixes.stick.get(Materials.RedAlloy), 'I', ItemRail.EnumRail.ADVANCED, 'T', ItemRailbed.EnumRailbed.WOOD});

    }

    @Override
    public void run() {
        delRecipe();
        handRecipe();
    }
}
